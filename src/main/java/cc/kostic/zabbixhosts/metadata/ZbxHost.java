package cc.kostic.zabbixhosts.metadata;

import cc.kostic.zabbixhosts.datamodel.*;
import cc.kostic.zabbixhosts.metadata.Geo;
import cc.kostic.zabbixhosts.metadata.IPinterfejs;
import cc.kostic.zabbixhosts.metadata.Record;
import cc.kostic.zabbixhosts.metadata.XmlTag;
import cc.kostic.zabbixhosts.metadata.Templejt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ZbxHost {
	public cc.kostic.zabbixhosts.metadata.Record record;
	private String localName;
	private final List<IPinterfejs> interfejsi = new ArrayList<>();
	private final List<XmlTag> tagovi = new ArrayList<>();
	private int intRef = 1;
	private Templejt templejt;
	
	public MapID mapID;
	public Lokacija lokacija;
	public Region region;
	public Reg reg;
	public Zona zona;
	public Alot alot;
	public LocID locID;
	public Pristup pristup;
	public MfnSfn mfnSfn;
	public TipUredjaja tipUredjaja;
	public PW pw;
	public SIM sim;
	public Opstina opstina;
	public EPS eps;
	public LAT lat;
	public LON lon;
	public Geo geo;
	
	public ZbxHost(Record record) {
		this.record = record;
		Map<String, String> keyval = record.keyval;
		
		mapID = new MapID(keyval, "MapID");
		lokacija = new Lokacija(keyval, "Lokacija");
		region = new Region(keyval, "Region");
		reg = new Reg(keyval, "Reg");
		zona = new Zona(keyval, "Zona");
		alot = new Alot(keyval, "Alot");
		locID = new LocID(keyval, "LocID");
		pristup = new Pristup(keyval, "Pristup");
		mfnSfn = new MfnSfn(keyval, "MFN/SFN");
		tipUredjaja = new TipUredjaja(keyval, "Tip");
		pw = new PW(keyval, "P(W)");
		sim = new SIM(keyval, "SIM");
		opstina = new Opstina(keyval, "Opština / MZ");
		eps = new EPS(keyval, "EPS");
		
		
		tagovi.add(alot);
//		tagovi.add(eps);
		tagovi.add(locID);
//		tagovi.add(mapID);
		tagovi.add(mfnSfn);
		tagovi.add(opstina);
		tagovi.add(pristup);
//		tagovi.add(pw);
		tagovi.add(reg);
		tagovi.add(region);
//		tagovi.add(sim);
		tagovi.add(tipUredjaja);
		tagovi.add(zona);
		
		lat = new LAT(keyval, "Geografska širina");
		lon = new LON(keyval, "Geografska dužina");
		geo = new Geo(lat.getValue(), lon.getValue());

	}
	
	
	
	public void addInterface(IPinterfejs intf){
		intf.setInterfaceRef(intRef);
		intRef++;
		interfejsi.add(intf);
	}
	
	public List<IPinterfejs> getInterfejsi() {
		return interfejsi;
	}
	
	public Templejt getTemplejt() {
		return templejt;
	}
	
	public void setTemplejt(Templejt templejt) {
		this.templejt = templejt;
	}
	
	public String getNameAscii() {
		return lokacija.getNameAscii() + " " + localName;
	}
	public String getNameUtf() {
		return lokacija.getNameUtf() + " " + localName;
	}
	
	public void setName(String localName) {
		this.localName = localName;
	}
	
	public List<XmlTag> getTagovi() {
		return tagovi;
	}
	
	public Geo getGeo() {
		return geo;
	}
}
