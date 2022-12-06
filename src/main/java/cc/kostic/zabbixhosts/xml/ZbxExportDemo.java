package cc.kostic.zabbixhosts.xml;

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


public class ZbxExportDemo {
	private String fajl = "D:\\Instalacije_nove\\ETV\\Zabbix\\zxdemo.xml";
	
	public void sad() {
		
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
			
			
			Element groups;
			groups = doc.createElement("groups");
			rootElement.appendChild(groups);
//			groups.setAttribute("id", "1");		// <groups id="1">
			
			
				Element group;
				group = doc.createElement("group");
				group.appendChild(doc.createTextNode("TODO group 3G"));		// TODO
				groups.appendChild(group);
				
				// firstname elements
				group = doc.createElement("group");
				group.appendChild(doc.createTextNode("TODO group DVB-T2"));		// TODO
				groups.appendChild(group);
				
			
			
			Element hosts;
			hosts = doc.createElement("hosts");
			rootElement.appendChild(hosts);
			
				Element host;
				host = doc.createElement("host");
				host.appendChild(doc.createTextNode("TODO host 1"));
				hosts.appendChild(host);
			
				host = doc.createElement("host");
				host.appendChild(doc.createTextNode("TODO host 2"));
				hosts.appendChild(host);
			
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
