package cc.kostic.zabbixhosts.datamodel;

public class Alot extends Element{
	
	
	public Alot(String header, String value) {
		super(header, value);
	}
	
	@Override
	public String toString() {
		String s  =
				"<host>" + this.csvHeader + "</host>" +
				"<name>" + this.csvValue + "</name>";
		return s;
	}
}
