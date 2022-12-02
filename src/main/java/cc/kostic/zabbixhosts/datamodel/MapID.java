package cc.kostic.zabbixhosts.datamodel;

import java.util.Map;

public class MapID extends Element{
	
	public MapID(Map<String, String> elementi, String csvHeader) {
		super(elementi, csvHeader);
	}
}
