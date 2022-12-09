package cc.kostic.zabbixhosts.datamodel;

import cc.kostic.zabbixhosts.metadata.XmlTag;

import java.util.Map;

public class LocID extends XmlTag {
	
	public LocID(Map<String, String> elementi, String csvHeader) {
		super(elementi, csvHeader);
	}
	
	public String getNum(){
		return csvValue;
	}

}
