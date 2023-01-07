package cc.kostic.zabbixhosts.datamodel;

import cc.kostic.zabbixhosts.metadata.CsvEL;

import java.util.Map;

public class Lokacija extends CsvEL {
	
	private final String nameAscii;
	private final String nameUtf;
	
	public Lokacija(Map<String, String> elementi, String csvHeader) {
		super(elementi, csvHeader);
		this.nameUtf = super.getValue();
		this.nameAscii = stripYu(nameUtf);
	}
	
	
	
	public String getNameAscii() {
		return nameAscii;
	}
	
	public String getNameUtf() {
		return nameUtf;
	}
}
