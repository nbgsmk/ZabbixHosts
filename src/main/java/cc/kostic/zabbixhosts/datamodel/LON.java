package cc.kostic.zabbixhosts.datamodel;

import cc.kostic.zabbixhosts.metadata.CsvEL;
import cc.kostic.zabbixhosts.metadata.Geo;

import java.util.Map;

public class LON extends CsvEL {
	
	public LON(Map<String, String> elementi, String csvHeader) {
		super(elementi, csvHeader);
	}
}
