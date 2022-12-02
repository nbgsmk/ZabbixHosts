package cc.kostic.zabbixhosts.datamodel;

public class Lokacija extends Element{
	public Lokacija(String header, String value) {
		super(header, value);
	}
	
	
	@Override
	public String toString(){
		String rez;
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
