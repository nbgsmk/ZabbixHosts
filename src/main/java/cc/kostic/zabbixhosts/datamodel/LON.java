package cc.kostic.zabbixhosts.datamodel;

import java.util.Map;

public class LON extends Element{
	
	public LON(Map<String, String> elementi, String csvHeader) {
		super(elementi, csvHeader);
	}
	
	@Override
	public String toXml() {
		return "<location_lon>" + csvValue.trim() + "</location_lon>";
		
	}
}
