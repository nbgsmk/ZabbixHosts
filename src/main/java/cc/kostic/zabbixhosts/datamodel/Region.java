package cc.kostic.zabbixhosts.datamodel;

import cc.kostic.zabbixhosts.metadata.XmlTag;

import java.util.Map;

public class Region extends XmlTag {
	

	public Region(Map<String, String> elementi, String csvHeader) {
		super(elementi, csvHeader);
	}
}
