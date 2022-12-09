package cc.kostic.zabbixhosts.datamodel;

import cc.kostic.zabbixhosts.metadata.XmlTag;

import java.util.Map;

public class MapID extends XmlTag {
	
	public MapID(Map<String, String> elementi, String csvHeader) {
		super(elementi, csvHeader);
	}
}
