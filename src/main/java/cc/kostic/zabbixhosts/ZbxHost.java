package cc.kostic.zabbixhosts;

import cc.kostic.zabbixhosts.datamodel.*;

public class ZbxHost {
	private Record record;
	private String interfaceName;
	private MapID mapID;
	private Lokacija lokacija;
	private Region region;
	private Reg reg;
	private Zona zona;
	private Alot alot;
	private LocID locID;
	private Pristup pristup;
	
	
	public ZbxHost(Record record) {
		this.record = record;
		mapID = (MapID) record.getMapID("MapID");
		lokacija = (Lokacija) record.getLokacija("Lokacija");
		region = (Region) record.getRegion("Region");
		reg = (Reg) record.getReg("Reg");
		zona = (Zona) record.getZona("Zona");
		alot = (Alot) record.getAlot("Alot");
		locID = (LocID) record.getLocID("LocID");
		pristup = (Pristup) record.getPristup("Pristup");
	}
	
	
	public void addInterface(String i){

	}
	

}
