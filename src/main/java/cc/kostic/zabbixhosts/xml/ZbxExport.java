package cc.kostic.zabbixhosts.xml;

import cc.kostic.zabbixhosts.Config;
import cc.kostic.zabbixhosts.metadata.ZbxHost;
import cc.kostic.zabbixhosts.metadata.HostGrupa;
import cc.kostic.zabbixhosts.metadata.IPinterfejs;
import cc.kostic.zabbixhosts.metadata.XmlTag;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class ZbxExport {
	private String fajl = "D:\\Instalacije_nove\\ETV\\Zabbix\\zx.xml";
	
	public void sad(List<ZbxHost> hostovi) {
		ZbxHost htmp = null;
		try {
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("zabbix_export");
			doc.appendChild(rootElement);
			
			// verzija
			Element ver = doc.createElement("version");
			ver.appendChild(doc.createTextNode("6.0"));
			rootElement.appendChild(ver);
			
			// datum
			Instant utc = Instant.now().truncatedTo(ChronoUnit.SECONDS);
			Element datum = doc.createElement("date");
			datum.appendChild(doc.createTextNode(Instant.now().truncatedTo(ChronoUnit.SECONDS).toString()));    // UTC vreme
//			datum.appendChild(doc.createTextNode(ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString()));	// zona Europe/Budapest, lokalno vreme
			rootElement.appendChild(datum);
			
			if (Config.EXPORT_GROUPS_SUMMARY) {
				// groups
				Element groups;
				groups = doc.createElement("groups");
				rootElement.appendChild(groups);
				
				boolean grpUuidPrint = true;
				Element group, grpuuid, grpname;
				for (HostGrupa grp : HostGrupa.values()) {
					group = doc.createElement("group");
					if (grpUuidPrint) {
						grpuuid = doc.createElement("uuid");
						grpuuid.appendChild(doc.createTextNode(UUID.randomUUID().toString().replace("-", "")));
						group.appendChild(grpuuid);
					}
					grpname = doc.createElement("name");
					grpname.appendChild(doc.createTextNode(grp.getName()));
					group.appendChild(grpname);
					
					groups.appendChild(group);
				}
			}
			
			
			// hosts = svi hostovi
			Element hosts;
			hosts = doc.createElement("hosts");
			rootElement.appendChild(hosts);
			
			// host
			for (ZbxHost h : hostovi) {
				htmp = h;
				Element jedanHost = doc.createElement("host");
				
				// lokacija tj naziv hosta - specijalan slucaj zbog ascii/utf kojeg zabbix prihvata
				Element hostAscii = doc.createElement("host");
				hostAscii.appendChild(doc.createTextNode(h.getNameAscii()));
				jedanHost.appendChild(hostAscii);
				
				String nazivA = h.getNameAscii();
				String nazivU = h.getNameUtf();
				if (!nazivA.equalsIgnoreCase(nazivU)) {
					Element hostUtf = doc.createElement("name");
					hostUtf.appendChild(doc.createTextNode(h.getNameUtf()));
					jedanHost.appendChild(hostUtf);
				}
				
				
				// templates
				Element templejts = doc.createElement("templates");
				List<IPinterfejs> interfejsi = h.getInterfejsi();
				for (IPinterfejs ip : interfejsi) {
					for (Element el : ip.getTemplates(doc)) {
						templejts.appendChild(el);
					}
				}
				jedanHost.appendChild(templejts);
				
				
				// groups
				Element groups = doc.createElement("groups");
				if (h.getInterfejsi() != null) {
					Element grp = doc.createElement("group");
					Element grpName = doc.createElement("name");
					grp.appendChild(grpName);
					grpName.appendChild(doc.createTextNode(HostGrupa.grp_NemaPristup.getName()));
					groups.appendChild(grp);
				} else {
					for (IPinterfejs ip : h.getInterfejsi()) {
						Element grp = doc.createElement("group");
						Element grpName = doc.createElement("name");
						grp.appendChild(grpName);
						grpName.appendChild(doc.createTextNode(ip.getHostGrp().getName()));
						groups.appendChild(grp);
					}
				}
				jedanHost.appendChild(groups);
				
				
				// interfaces
				Element interfaces = doc.createElement("interfaces");
				for (IPinterfejs ip : h.getInterfejsi()) {
					for (Element el : ip.getInterfaces(doc)) {
						interfaces.appendChild(el);
					}
				}
				jedanHost.appendChild(interfaces);
				
				
				// tagovi
				Element tagovi = doc.createElement("tags");
				for (XmlTag jedanTag : h.getTagovi()) {
					tagovi.appendChild(jedanTag.getElement(doc));
				}
				jedanHost.appendChild(tagovi);
				
				// inventory = coordinates
				Element inventar = doc.createElement("inventory");
				for (Element geoEl : h.getGeo().getElement(doc)) {
					inventar.appendChild(geoEl);
				}
				jedanHost.appendChild(inventar);
				
				hosts.appendChild(jedanHost);
				
			}
			
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(fajl));
			
			// Output to console for testing
//			 StreamResult result2 = new StreamResult(System.out);
//			 System.out.println(result2);
			
			transformer.transform(source, result);
			
			System.out.println("File saved!");
			
			
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (RuntimeException re) {
			re.printStackTrace();
			System.out.println(htmp.lokacija.getNameUtf() + " " + re.getMessage());
			System.out.println(re.getClass());
			System.out.println(htmp.record.keyval);
		}
	}
	
}

// TODO utf ulazni fajl
// TODO ? u imenu
// TODO ip adrese 15*, 86*
