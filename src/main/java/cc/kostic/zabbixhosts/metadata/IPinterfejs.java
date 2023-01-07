package cc.kostic.zabbixhosts.metadata;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;

public class IPinterfejs {

	public enum TIP{
		SNMPv1,
		SNMPv2,
		SNMPv2_CAMBIUM,
		PING()
	}

	private final Map<String, String> snmp_props = new HashMap<String, String>();
	private final Map<String, String> snmp_details = new HashMap<String, String>();

	
	public TIP tip;
	public String adresa;
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

	
	public void setTip(TIP tip) {
		switch (tip) {
			case SNMPv1 -> {
				snmp_props.put("type", "SNMP");
				snmp_props.put("port", "161");
				snmp_details.put("version", "SNMPV1");
				snmp_details.put("community", "{$SNMP_COMMUNITY_PUBLIC}");
			}
			case SNMPv2 -> {
				snmp_props.put("type", "SNMP");
				snmp_props.put("port", "161");
				snmp_details.put("community", "{$SNMP_COMMUNITY_PUBLIC}");
			}
			case SNMPv2_CAMBIUM -> {
				snmp_props.put("type", "SNMP");
				snmp_props.put("port", "161");
				snmp_details.put("community", "{$SNMP_COMMUNITY_CAMBIUM_PUBLIC}");
			}

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

		intf.appendChild(adresa);

		// SNMP
		for	(Map.Entry<String, String> entry : snmp_props.entrySet()) {
			// direktno ispod taga <interface>
			// 		<type>snmp</type>
			// 		<port>161</port>
			Element pr = doc.createElement(entry.getKey());
			pr.appendChild(doc.createTextNode(entry.getValue()));
			intf.appendChild(pr);
		}

		if ( ! snmp_details.isEmpty()) {
			Element detalji = doc.createElement("details");
			for	(Map.Entry<String, String> entry : snmp_details.entrySet()) {
				// ispod taga <interface> / <details>
				// 		<community>snmp</>
				// 		<version>SNMPv1</>
				Element dt = doc.createElement(entry.getKey());
				dt.appendChild(doc.createTextNode(entry.getValue()));
				detalji.appendChild(dt);
			}
			intf.appendChild(detalji);
		}

		intf.appendChild(ref);
		
		tmp.add(intf);
		return tmp;
	}
}
