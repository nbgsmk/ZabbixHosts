package cc.kostic.zabbixhosts.datamodel;

import cc.kostic.zabbixhosts.metadata.XmlTag;

import java.util.Map;

public class Pristup extends XmlTag {
	
	public enum TIP {
		NEMA,
		pristup3G,
		IPLink,
		HCLink
	}
	
	
	public Pristup(Map<String, String> elementi, String csvHeader) {
		super(elementi, csvHeader);
	}
	
	public TIP getTip(){
		switch (csvValue){
			case "": 		return TIP.NEMA;
			case "3G": 		return TIP.pristup3G;
			case "IP Lnk":	return TIP.IPLink;
			case "Lnk":		return TIP.HCLink;
			default:		return null;
		}
	}
	

	

}
