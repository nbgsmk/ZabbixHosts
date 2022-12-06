package cc.kostic.zabbixhosts.datamodel;

import java.util.Map;

public class LocID extends CsvEL {
	
	public LocID(Map<String, String> elementi, String csvHeader) {
		super(elementi, csvHeader);
	}
	
	public String getNum(){
		return csvValue;
	}

}
