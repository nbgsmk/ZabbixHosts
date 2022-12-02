package cc.kostic.zabbixhosts;

import cc.kostic.zabbixhosts.datamodel.*;
import cc.kostic.zabbixhosts.metadata.IPinterfejs;
import cc.kostic.zabbixhosts.metadata.TEMPLEJT;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Record {
	private final Map<String, String> elementi;
	private Map<String, IPinterfejs> sviInterfejsi;
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
	
	String klasa;
	
	public Record(Map<String, String> elementi) {
		this.elementi = elementi;
		mapID = new MapID(elementi, "MapID");
		lokacija = new Lokacija(elementi, "Lokacija");
		region = new Region(elementi, "Region");
		reg = new Reg(elementi, "Reg");
		zona = new Zona(elementi, "Zona");
		alot = new Alot(elementi, "Alot");
		locID = new LocID(elementi, "LocID");
		pristup = new Pristup(elementi, "Pristup");
		mfnSfn = new MfnSfn(elementi, "MFN/SFN");
		tipUredjaja = new TipUredjaja(elementi, "Tip");
		pw = new PW(elementi, "P(W)");
		sim = new SIM(elementi, "SIM");
		opstina = new Opstina(elementi, "Opština / MZ");
		eps = new EPS(elementi, "EPS");
		lat = new LAT(elementi, "Geografska širina");
		lon = new LON(elementi, "Geografska dužina");
		
		sviInterfejsi = createInterfaces();
	}

	
	

	public Map<String, IPinterfejs> getInterfaces(){
		return sviInterfejsi;
	}

	
	private Map<String, IPinterfejs> createInterfaces(){
		Map<String, IPinterfejs> interfejsi = new HashMap<>();
		TEMPLEJT.PROFIL ipProfil = TEMPLEJT.PROFIL.normalni;
		String loc = locID.getNum();
		String adr = "172.16." + locID.getNum();
		
		Pristup.TIP p = pristup.getTyp();
		if (p == Pristup.TIP.NEMA) {
			return interfejsi;		// TODO mozda zabbix ocekuje null ako nema interfejsa
		} else if (p == Pristup.TIP.pristup3G) {
			ipProfil = TEMPLEJT.PROFIL.pristup3G;
		} else if (Config.velikih11.contains(lokacija.getValue())) {
			ipProfil = TEMPLEJT.PROFIL.velikih11;
		}
		
		
		for (String kolona : Config.interfejsKolone) {
			String esa = null;
			String val = elementi.get(kolona);
			
			if (kolona.equalsIgnoreCase("Pristup")) {
				esa = switchByKolonaPristup(p);
				if ( (esa != null) && ( ! esa.isBlank()) ) {
					IPinterfejs intf = new IPinterfejs(ipProfil, adr + esa);
					interfejsi.put(val, intf);
				}
				
			} else if ( ! val.isBlank()){
				esa = switchByOstaleKolone(kolona);
				IPinterfejs intf = new IPinterfejs(ipProfil, adr + esa);
				interfejsi.put(kolona, intf);
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
				if (elementi.get("Tip").equals("Slx9k")) {
					esa = ".161";
				} else {
					esa = ".162";
				}
				break;
			
			case "MUX3":
				if (elementi.get("Tip").equals("Slx9k")) {
					esa = ".161";
				} else {
					esa = ".163";
				}
				break;
		}
		return esa;
	}

	
}
