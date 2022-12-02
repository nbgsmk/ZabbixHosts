package cc.kostic.zabbixhosts.datamodel;

import java.util.Map;

public class LocID extends Element{
	
	public LocID(Map<String, String> elementi, String csvHeader) {
		super(elementi, csvHeader);
	}
	
	public String getNum(){
		return csvValue;
	}

}
