package cc.kostic.zabbixhosts.xml;

import cc.kostic.zabbixhosts.Config;
import cc.kostic.zabbixhosts.ZbxHost;
import cc.kostic.zabbixhosts.metadata.Grupe;
import cc.kostic.zabbixhosts.metadata.IPinterfejs;
import org.w3c.dom.Attr;
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
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ZbxExport {
	private String fajl = "D:\\Instalacije_nove\\ETV\\Zabbix\\zx.xml";
	
	public void sad(List<ZbxHost> hostovi) {
		
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
			Element datum = doc.createElement("date");
			datum.appendChild(doc.createTextNode(Instant.now().toString()));		// UTC vreme
//			datum.appendChild(doc.createTextNode(ZonedDateTime.now().toString()));	// zona Europe/Budapest, lokalno vreme
			rootElement.appendChild(datum);
			
			// groups
			Element groups;
			groups = doc.createElement("groups");
			rootElement.appendChild(groups);
//			groups.setAttribute("id", "1");		// <groups id="1">
			
				// groups
				Element group;
				group = doc.createElement("group");
				group.appendChild(doc.createTextNode("TODO group 3G"));		// TODO group uuid
				groups.appendChild(group);
				
				// firstname elements
				group = doc.createElement("group");
				group.appendChild(doc.createTextNode("TODO group DVB-T2"));		// TODO group uuid
				groups.appendChild(group);
				
			
			// hosts = svi hostovi
			Element hosts;
			hosts = doc.createElement("hosts");
			rootElement.appendChild(hosts);
			
			// host
			for (ZbxHost h : hostovi) {
				Element jedanHost = doc.createElement("host");
				
				if (Config.currentMode == Config.MODE.SVAKI_MUX_POSEBAN_HOST) {
					
					// lokacija tj naziv hosta - specijalan slucaj zbog ascii/utf kojeg zabbix prihvata
					Element hostAscii = doc.createElement("host");
					hostAscii.appendChild(doc.createTextNode(h.getNameAscii()));
					jedanHost.appendChild(hostAscii);
					
					String nazivA = h.getNameAscii();
					String nazivU = h.getNameUtf();
					if ( ! nazivA.equalsIgnoreCase(nazivU)) {
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
					// templates
					Element grupe = doc.createElement("groups");
					List<Grupe.HOSTGRUPE> hostGrupe = h.record.getGrupe();
					for (Grupe.HOSTGRUPE grp : hostGrupe) {
						Element el = doc.createElement("group");
						Element name = doc.createElement("name");
						el.appendChild(name);
						name.appendChild(doc.createTextNode(grp.name()));
						grupe.appendChild(el);
					}
					jedanHost.appendChild(grupe);
					
					
					// interfaces
					Element interfaces = doc.createElement("interfaces");
					for (IPinterfejs ip : h.interfejsi) {
						for (Element el : ip.getInterfaces(doc)) {
							interfaces.appendChild(el);
						}
					}
					jedanHost.appendChild(interfaces);
				}
				
				if (Config.currentMode == Config.MODE.SVI_MUX_U_ISTI_HOST) {
					
					// lokacija tj naziv hosta - specijalan slucaj zbog ascii/utf kojeg zabbix prihvata
					Element hostAscii = doc.createElement("host");
					hostAscii.appendChild(doc.createTextNode(h.getNameAscii()));
					jedanHost.appendChild(hostAscii);
					
					String nazivA = h.getNameAscii();
					String nazivU = h.getNameUtf();
					if ( ! nazivA.equalsIgnoreCase(nazivU)) {
						Element hostUtf = doc.createElement("name");
						hostUtf.appendChild(doc.createTextNode(h.getNameUtf()));
						jedanHost.appendChild(hostUtf);
					}

					// templates
					Element templejts = doc.createElement("templates");
					templejts.appendChild(doc.createTextNode(h.record.getTemplejtZajednicki().name()));
					jedanHost.appendChild(templejts);
					
					// interfaces
					Element interfaces = doc.createElement("interfaces");
					for (IPinterfejs ip : h.interfejsi) {
						for (Element el : ip.getInterfaces(doc)) {
							interfaces.appendChild(el);
						}
					}
					jedanHost.appendChild(interfaces);
				}
				
				

				
				
//				Element lokacijaAsc = doc.createElement("host");
//				Element lokacijaUtf = doc.createElement("name");
//				lokacijaAsc.appendChild(doc.createTextNode(h.record.lokacija.getValue()));
//				lokacijaUtf.appendChild(doc.createTextNode(h.record.lokacija.getValue() + "-utf"));
//				host.appendChild("host");
//				host.appendChild(doc.createElement(h.record.lokacija.getValue()));
//				host.appendChild(doc.createTextNode("TODO host 1"));

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
		}
	}
	
}
