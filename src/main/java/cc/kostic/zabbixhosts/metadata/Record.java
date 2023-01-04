package cc.kostic.zabbixhosts.metadata;

import cc.kostic.zabbixhosts.Config;
import cc.kostic.zabbixhosts.datamodel.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Record {
	public final Map<String, String> keyval;
	
	private List<IPinterfejs> interfejsi = new ArrayList<>();
	private Set<HostGrupe> hostGrupe = new HashSet<>();
	
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
		Templejt tpl = Templejt.Normalni;
		String adr = "172.16." + locID.getNum() + ".";
		
		Pristup.TIP p = pristup.getTip();
		
		switch (p){
			case NEMA:
				// TODO ako nema interfejsa ceo nod <interfaces> se ne pojavljuje
				hostGrupe.add(HostGrupe.grp_NemaPristup);
				return interfejsi;
//				break;
			
			case pristup3G:
				tpl = Templejt.Pristup3G;
				hostGrupe.add(HostGrupe.grp_3G);
				break;
				
			case IPLink:
				tpl = Templejt.PristupIPLink;
				hostGrupe.add(HostGrupe.grp_IPLink);
				break;
				
			case HCLink:
				tpl = Templejt.PristupHCLink;
				hostGrupe.add(HostGrupe.grp_HCLink);
				break;
				
		}
		
		if (Config.velikih11.contains(lokacija.getValue())) {
			tpl = Templejt.Velikih11;
			hostGrupe = new HashSet<>();
			hostGrupe.add(HostGrupe.grp_Velikih11);
		}
		
		// naknadno pregaziti ovaj zajednicki template sa specificnim
		
		for (String kolona : Config.interfejsKolone) {
			String esa = null;
			String val = keyval.get(kolona);
			
			if (kolona.equalsIgnoreCase("Pristup")) {
				esa = switch_case_byKolonaPristup(p);
				if ( (esa != null) && ( ! esa.isBlank()) ) {
					IPinterfejs intf = new IPinterfejs(adr + esa);
					intf.setNaziv(p.name());
					intf.setTemplejt(tpl);
					interfejsi.add(intf);
				}
				
			} else if ( ! val.isBlank()){
				esa = switch_case_byOstaleKolone(kolona);
				IPinterfejs intf = new IPinterfejs(adr + esa);
				intf.setNaziv(kolona);
				intf.setTemplejt(tpl);
				interfejsi.add(intf);
			}
			
		}
		return interfejsi;
	}
	

	private @Nullable String switch_case_byKolonaPristup(Pristup.TIP p){
		String esa = null;
		switch (p){
			case NEMA:
				esa = null;
//				hostGrupe.add(HostGrupe.grp_NemaPristup);
				break;
			
			case pristup3G:
				esa = IPadrese.ip3G.getNum();
//				hostGrupe.add(HostGrupe.grp_3G);
				break;
			
			case IPLink:
				// TODO a gde je adresa 101?
				esa = IPadrese.ipLinkBlizi.getNum();
//				hostGrupe.add(HostGrupe.grp_IPLink);
				break;
			
			case HCLink:
				esa = null;
//				hostGrupe.add(HostGrupe.grp_HCLink);
				break;
			default:
				throw new RuntimeException("nepoznata vrsta pristupa sajtu u klasi " + this.getClass());
		}
		return esa;
	}
	
	private @Nullable String switch_case_byOstaleKolone(String k){
		Pattern p = Pattern.compile("([89]k)|(mlx)");
		Matcher m = p.matcher(keyval.get("Tip"));
		String esa = null;
		switch (k){
			case "MUX1":
				esa = IPadrese.ipMUX_1.getNum();
				hostGrupe.add(HostGrupe.grp_DVBT);
				break;
			
			case "MUX2":
				esa = IPadrese.ipMUX_2.getNum();
				if (m.matches()) {
					esa = IPadrese.ipMUX_1.getNum();
				}
				hostGrupe.add(HostGrupe.grp_DVBT);
				break;
			
			case "MUX3":
				esa = IPadrese.ipMUX_3.getNum();
				if (m.matches()) {
					esa = IPadrese.ipMUX_1.getNum();
				}
				hostGrupe.add(HostGrupe.grp_DVBT);
				break;
			
			case "Agregat":
				esa = IPadrese.ipAgregat.getNum();
				hostGrupe.add(HostGrupe.grp_Agregati);
				break;
				
			case "UPS":
				esa = IPadrese.ipUPS.getNum();;
				hostGrupe.add(HostGrupe.grp_UPS);
				break;
				
		}
		return esa;
	}
	

	
	public Set<HostGrupe> getGrupe() {
		return hostGrupe;
	}
}
