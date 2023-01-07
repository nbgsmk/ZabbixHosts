package cc.kostic.zabbixhosts.metadata;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class IPinterfejs {

	public enum TIP{
		SNMPv2,
		SNMPv1,
		PING()
	}

	
	
	public TIP tip;
	public String adresa;
	public String port;
	public String details_SNMPcommunity;
	public String details_SNMPversion;
	public int interfaceRef;
	public String naziv;
	private Templejt templejt;
	private HostGrupa grp;
	
	public IPinterfejs(String adresa) {
		this.adresa = adresa;
	}
	
	public IPinterfejs(String adresa, String naziv) {
		this.adresa = adresa;
		this.naziv = naziv;
	}
	
	
	public TIP getTip() {
		return tip;
	}
	
	public String getPort() {
		return port;
	}
	
	public void setTip(TIP tip) {
		this.tip = tip;
		if (tip == TIP.SNMPv1) {
			this.port = "161";
			this.details_SNMPcommunity = "{$SNMP_COMMUNITY_PUBLIC}";
			this.details_SNMPversion = "SNMPV1";
		}
		if (tip == TIP.SNMPv2) {
			this.port = "161";
			this.details_SNMPcommunity = "{$SNMP_COMMUNITY_PUBLIC}";
		}

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
	
	public Templejt getTemplejt() {
		return templejt;
	}
	
	public void setTemplejt(Templejt templejt) {
		this.templejt = templejt;
	}
	
	
	public HostGrupa getHostGrp() {
		return grp;
	}
	
	public void setHostGrp(HostGrupa grp) {
		this.grp = grp;
	}
	
	public List<Element> getTemplates(Document doc){
		List<Element> tmp = new ArrayList<>();
		
		Element tmpl = doc.createElement("template");
		Element naziv = doc.createElement("name");
		
		tmpl.appendChild(naziv);
		naziv.appendChild(doc.createTextNode(getTemplejt().getNaziv()));
		
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
		
		
		// SNMP priprema
		Element type = doc.createElement("type");
		type.appendChild(doc.createTextNode("SNMP"));

		Element port = doc.createElement("port");
		port.appendChild(doc.createTextNode(this.getPort()));
		
		Element detalji = doc.createElement("details");
		Element community = doc.createElement("community");
		detalji.appendChild(community);
		community.appendChild(doc.createTextNode(this.details_SNMPcommunity));
		
		if (this.getTip() == TIP.SNMPv1) {
			Element ver = doc.createElement("version");
			ver.appendChild(doc.createTextNode(this.details_SNMPversion));
			detalji.appendChild(ver);
			intf.appendChild(type);
			intf.appendChild(port);
			intf.appendChild(detalji);
		}
		if (this.getTip() == TIP.SNMPv2) {
			intf.appendChild(type);
			intf.appendChild(port);
			intf.appendChild(detalji);
		}
		
			

		
		
		//		sviIntf.appendChild(intf);
		intf.appendChild(adresa);
		intf.appendChild(ref);
		
//		tmp.add(sviIntf);
		tmp.add(intf);
//		tmp.add(naziv);
		return tmp;
	}
}
