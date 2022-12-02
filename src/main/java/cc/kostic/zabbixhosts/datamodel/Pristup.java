package cc.kostic.zabbixhosts.datamodel;

import cc.kostic.zabbixhosts.Record;

import java.util.Set;

public class Pristup extends Element{
	
	public enum PRISTUP {NEMA, pristup3G, IPLink, HCLink, nepoznato}
	private Record rec;
	Set<String> interfejsi;
	
	public Pristup(String csvHeader, String csvValue) {
		super(csvHeader, csvValue);
	}
	
	public PRISTUP getTyp(){
		switch (csvValue){
			case "": 		return PRISTUP.NEMA;
			case "3G": 		return PRISTUP.pristup3G;
			case "IP Lnk":	return PRISTUP.IPLink;
			case "Lnk":		return PRISTUP.HCLink;
			default:		return PRISTUP.nepoznato;
		}
	}
	
	@Override
	public String toString() {
		String s = "";
		PRISTUP pr = getTyp();
		switch (pr){
			case NEMA:
				break;
			case pristup3G:
				s =
					"<interfaces>" +
					"  <interface>" +
					"    <ip>172.16.84.1</ip>" +
					"    <interface_ref>if1</interface_ref>" +
					"  </interface>" +
					"</interfaces>";
			
			case IPLink:
				s =
					"<interfaces>" +
					"  <interface>" +
					"    <ip>172.16.84.100</ip>" +
					"    <interface_ref>if1</interface_ref>" +
					"  </interface>" +
					"</interfaces>";
			
		}
		
		return s;
	}
	

}
