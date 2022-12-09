package cc.kostic.zabbixhosts.metadata;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Map;

public class XmlTag extends CsvEL {
	
	
	public XmlTag(Map<String, String> elementi, String csvHeader) {
		super(elementi, csvHeader);
	}
	
	public Element getElement(Document doc) {
		Element el = doc.createElement("tag");
		Element t = doc.createElement("tag");
		Element v = doc.createElement("value");
		
		t.appendChild(doc.createTextNode(getHeader()));
		v.appendChild(doc.createTextNode(csvValue));
		
		el.appendChild(t);
		el.appendChild(v);
		return el;
	}
}
