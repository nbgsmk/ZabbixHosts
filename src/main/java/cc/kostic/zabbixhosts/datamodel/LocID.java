package cc.kostic.zabbixhosts.datamodel;

public class LocID extends Element{
	public LocID(String header, String value) {
		super(header, value);
	}

	public String getNum(){
		return csvValue;
	}

}
