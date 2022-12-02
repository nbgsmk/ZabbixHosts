package cc.kostic.zabbixhosts;

import cc.kostic.zabbixhosts.datamodel.*;

import java.util.Map;

public class Record {
	private final Map<String, String> elementi;
	LocID locID;
	Pristup p;
	public Record(Map<String, String> elementi) {
		this.elementi = elementi;
	}
	
	public MapID getMapID(String s){
		return new MapID(s, elementi.get(s));
	}

	public Lokacija getLokacija(String s){
		return new Lokacija(s, elementi.get(s));
	}

	public Region getRegion(String s){
		return new Region(s, elementi.get(s));
	}
	
	public Reg getReg(String s){
		return new Reg(s, elementi.get(s));
	}
	public Zona getZona(String s){
		return new Zona(s, elementi.get(s));
	}
	public Alot getAlot(String s){
		return new Alot(s, elementi.get(s));
	}

	public LocID getLocID(String s){
		this.locID = new LocID(s, elementi.get(s));
		return locID;
	}
	
	public Pristup getPristup(String s){
		this.p = new Pristup(s, elementi.get(s));
		return p;
	}
	
	
	
	
	public Object getObj(String s){
		Object obj;
		try {
			Class cls = Class.forName("cc.kostic.zabbixhosts.datamodel." + s);
			obj = cls.newInstance();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		return obj;
	}

	
	public String intf(String s){
		String adresa = "172.16." + locID.getNum();
		switch (p.getTyp()){
			case NEMA:
				adresa = "";
				break;
				
			case pristup3G:
				adresa = adresa + ".1";
				break;
			
			case IPLink:
				adresa = adresa + ".100";
				break;
			
			case HCLink:
				adresa = "";
				break;
				
			case "MUX1":
			
		}
		return adresa;
	}
	
	
	

	

	
}
