package cc.kostic.zabbixhosts.datamodel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Lokacija extends CsvEL {
	
	private String nameAscii;
	private String nameUtf;
	
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
