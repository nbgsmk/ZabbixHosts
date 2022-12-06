package cc.kostic.zabbixhosts.datamodel;

import java.util.Map;

public class MUX extends CsvEL {
	
	public MUX(Map<String, String> elementi, String csvHeader) {
		super(elementi, csvHeader);
	}
	
	@Override
	public String toXml() {
		return "TODO";
	}
}
