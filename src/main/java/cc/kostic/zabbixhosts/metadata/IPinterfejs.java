package cc.kostic.zabbixhosts.metadata;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IPinterfejs {
	
	public String type;
	public String adresa;
	public String port;
	public String detailsCommunity;
	public int interfaceRef;
	public String naziv;
	private Templejt.TPL templejt;
	
	public IPinterfejs(String adresa) {
		this.adresa = adresa;
	}
	
	public IPinterfejs(String adresa, String naziv) {
		this.adresa = adresa;
	}
	
	public String getAdresa() {
		return adresa;
	}
	
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	
	
	
	public int getInterfaceRef() {
		return interfaceRef;
	}
	
	public String getNaziv() {
		return naziv;
	}
	
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	public void setInterfaceRef(int interfaceRef) {
		this.interfaceRef = interfaceRef;
	}
	
	public Templejt.TPL getTemplejt() {
		return templejt;
	}
	
	public void setTemplejt(Templejt.TPL templejt) {
		this.templejt = templejt;
	}
	
	public List<Element> getTemplates(Document doc){
		List<Element> tmp = new ArrayList<>();
		
		Element tmpl = doc.createElement("template");
		Element naziv = doc.createElement("name");
		
		tmpl.appendChild(naziv);
		naziv.appendChild(doc.createTextNode(getTemplejt().name()));
		
		tmp.add(tmpl);
		return tmp;
	}
	
	
	public List<Element> getInterfaces(Document doc){
		List<Element> tmp = new ArrayList<>();
		
//		Element sviIntf = doc.createElement("interfaces");
		Element intf = doc.createElement("interface");
		Element adresa = doc.createElement("ip");
		adresa.appendChild(doc.createTextNode(this.getAdresa()));
		Element ref = doc.createElement("interface_ref");
		ref.appendChild(doc.createTextNode( "if" + String.valueOf(this.getInterfaceRef())));
		
//		sviIntf.appendChild(intf);
		intf.appendChild(adresa);
		intf.appendChild(ref);
		
//		tmp.add(sviIntf);
		tmp.add(intf);
//		tmp.add(naziv);
		return tmp;
	}
}
