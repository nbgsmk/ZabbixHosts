package cc.kostic.zabbixhosts.datamodel;

import java.util.Map;

public class LAT extends Element{
	
	public LAT(Map<String, String> elementi, String csvHeader) {
		super(elementi, csvHeader);
	}
	
	@Override
	public String toXml() {
		return "<location_lat>" + csvValue.trim() + "</location_lat>";
	}
}
