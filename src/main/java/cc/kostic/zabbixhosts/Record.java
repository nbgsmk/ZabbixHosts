package cc.kostic.zabbixhosts;

import cc.kostic.zabbixhosts.datamodel.*;
import cc.kostic.zabbixhosts.metadata.IPinterfejs;
import cc.kostic.zabbixhosts.metadata.Templejt;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Record {
	public final Map<String, String> keyval;
	
	private List<IPinterfejs> interfejsi = new ArrayList<>();
	private Templejt.TPL templejtZajednicki;
	
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
	
	
	public Record(Map<String, String> keyval) {
		this.keyval = keyval;
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
		lat = new LAT(keyval, "Geografska širina");
		lon = new LON(keyval, "Geografska dužina");
		
		interfejsi = createInterfaces();
	}

	
	

	public List<IPinterfejs> getInterfaces(){
		return interfejsi;
	}

	
	private List<IPinterfejs> createInterfaces(){
		List<IPinterfejs> interfejsi = new ArrayList<>();
		Templejt.TPL tpl = Templejt.TPL.normalni;
		String loc = locID.getNum();
		String adr = "172.16." + locID.getNum();
		
		Pristup.TIP p = pristup.getTip();
		if (p == Pristup.TIP.NEMA) {
			tpl = Templejt.TPL.nema;		// TODO ako nema interfejsa ceo nod <interfaces> se ne pojavljuje
			return interfejsi;
		} else if (p == Pristup.TIP.pristup3G) {
			tpl = Templejt.TPL.pristup3G;
		} else if (Config.velikih11.contains(lokacija.getValue())) {
			tpl = Templejt.TPL.velikih11;
		}
		
		setTemplejtZajednicki(tpl);
		
		for (String kolona : Config.interfejsKolone) {
			String esa = null;
			String val = keyval.get(kolona);
			
			if (kolona.equalsIgnoreCase("Pristup")) {
				esa = switchByKolonaPristup(p);
				if ( (esa != null) && ( ! esa.isBlank()) ) {
					IPinterfejs intf = new IPinterfejs(adr + esa);
					intf.setNaziv(p.name());
					intf.setTemplejt(tpl);
					interfejsi.add(intf);
				}
				
			} else if ( ! val.isBlank()){
				esa = switchByOstaleKolone(kolona);
				IPinterfejs intf = new IPinterfejs(adr + esa);
				intf.setNaziv(kolona);
				intf.setTemplejt(tpl);
				interfejsi.add(intf);
			}
			
		}
		return interfejsi;
	}
	

	private @Nullable String switchByKolonaPristup(Pristup.TIP p){
		String esa = null;
		switch (p){
			case NEMA:
				esa = null;
				break;
			
			case pristup3G:
				esa = ".1";
				break;
			
			case IPLink:
				esa = ".100";
				break;
			
			case HCLink:
				esa = null;
				break;
			default:
				throw new RuntimeException("nepoznata vrsta pristupa sajtu u klasi " + this.getClass());
		}
		return esa;
	}
	
	private @Nullable String switchByOstaleKolone(String k){
		String esa = null;
		switch (k){
			case "MUX1":
				esa  = ".161";
				break;
			
			case "MUX2":
				if (keyval.get("Tip").equals("Slx9k")) {
					esa = ".161";
				} else {
					esa = ".162";
				}
				break;
			
			case "MUX3":
				if (keyval.get("Tip").equals("Slx9k")) {
					esa = ".161";
				} else {
					esa = ".163";
				}
				break;
		}
		return esa;
	}
	
	public Templejt.TPL getTemplejtZajednicki() {
		return this.templejtZajednicki;
	}
	
	public void setTemplejtZajednicki(Templejt.TPL templejtZajednicki) {
		this.templejtZajednicki = templejtZajednicki;
	}
}
