package cc.kostic.zabbixhosts.datamodel;

import java.util.Map;

public class LON extends CsvEL {
	
	public LON(Map<String, String> elementi, String csvHeader) {
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
