package cc.kostic.zabbixhosts.datamodel;

import java.text.Normalizer;
import java.util.Map;

public class Lokacija extends Element{
	
	public Lokacija(Map<String, String> elementi, String csvHeader) {
		super(elementi, csvHeader);
	}
	
	@Override
	public String toXml(){
		String rez;
		String a = stripYu(this.csvValue);
		String b = this.csvValue;
		if ( stripYu(this.csvValue).equals(this.csvValue) ) {
			rez =
				"<host>" + this.csvValue + "</host>"				// host podrzava samo ASCII
			;
		} else {
			rez =
				"<host>" + stripYu(this.csvValue) + "</host>" +	// ASCII naziv
				"<name>" + this.csvValue + "</name>"				// UTF yu bazuv
			;
		}
		return rez;
	}
	
}
