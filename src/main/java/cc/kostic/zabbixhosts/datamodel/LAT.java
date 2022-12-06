package cc.kostic.zabbixhosts.datamodel;

import java.util.Map;

public class LAT extends CsvEL {
	
	public LAT(Map<String, String> elementi, String csvHeader) {
		super(elementi, csvHeader);
	}
	
	@Override
	public String toXml() {
		if (csvValue != null) {
			return "<location_lat>" + csvValue.trim() + "</location_lat>";
		} else {
			return "";
		}
	}
}
