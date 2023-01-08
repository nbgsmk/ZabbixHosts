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
	private Set<HostGrupa> hostGrupa = new HashSet<>();
	
	public MapID mapID;
	public Lokacija lokacija;
	public Region region;
	public Reg reg;
	public Zona zona;
	// public Alot alot;
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
		// alot = new Alot(keyval, "Alot");
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
	
	
	public List<IPinterfejs> getInterfaces() {
		return interfejsi;
	}
	
	
	private @Nullable List<IPinterfejs> createInterfaces() {
		List<IPinterfejs> tmpIntf = new ArrayList<>();
		
		Templejt tpl = Templejt.tpl_Normal;
		IPinterfejs intf = null;
		String adr = "172.16." + locID.getNum() + ".";
		String esa = "";
		
		Pristup.TIP p = pristup.getTip();
		
		switch (p) {
			case NEMA:
				return null;
				// break;
			
			case pristup3G:
				esa = IPdefault.ip3G.getNum();
				intf = new IPinterfejs(adr + esa, p.name());
				intf.setTemplejt(Templejt.tpl_3G);
				intf.setHostGrp(HostGrupa.grp_3G);
				intf.setTip(IPinterfejs.TIP.PING);
				tmpIntf.add(intf);
				break;
			
			case IPLink:
				if (Config.CambiumIPlink.contains(this.lokacija.getNameUtf())) {
					// Cambium = SNMPv2
					esa = IPdefault.ipLinkBlizi.getNum();
					intf = new IPinterfejs(adr + esa, p.name() + " A");
					intf.setHostGrp(HostGrupa.grp_IPLink);
					intf.setTemplejt(Templejt.tpl_IPLinkCAMB);
					intf.setTip(IPinterfejs.TIP.SNMPv2_CAMBIUM);
					tmpIntf.add(intf);

					esa = IPdefault.ipLinkDalji.getNum();
					intf = new IPinterfejs(adr + esa, p.name() + " B");
					intf.setHostGrp(HostGrupa.grp_IPLink);
					intf.setTemplejt(Templejt.tpl_IPLinkCAMB);
					intf.setTip(IPinterfejs.TIP.SNMPv2_CAMBIUM);
					tmpIntf.add(intf);

				} else {
					// Ubiquity = SNMPv1
					esa = IPdefault.ipLinkBlizi.getNum();
					intf = new IPinterfejs(adr + esa, p.name() + " A");
					intf.setHostGrp(HostGrupa.grp_IPLink);
					intf.setTemplejt(Templejt.tpl_IPLink);
					intf.setTip(IPinterfejs.TIP.SNMPv1);
					tmpIntf.add(intf);

					esa = IPdefault.ipLinkDalji.getNum();
					intf = new IPinterfejs(adr + esa, p.name() + " B");
					intf.setHostGrp(HostGrupa.grp_IPLink);
					intf.setTemplejt(Templejt.tpl_IPLink);
					intf.setTip(IPinterfejs.TIP.SNMPv1);
					tmpIntf.add(intf);
				}
				break;
			
			case HCLink:
				// tpl = Templejt.tpl_HCLink;
				// hostGrupa.add(HostGrupa.grp_HCLink);
				break;
			
			default:
				throw new RuntimeException("nepoznata vrsta pristupa sajtu u klasi " + this.getClass());
			
			
		}
		
		if (Config.velikih11.contains(lokacija.getValue())) {
			tpl = Templejt.tpl_Velikih11;
			hostGrupa = new HashSet<>();
			hostGrupa.add(HostGrupa.grp_Velikih11);
		}
		
		// naknadno pregaziti ovaj zajednicki template sa specificnim
		
		for (String kolona : Config.interfejsKolone) {
			String sadrzajKolone = keyval.get(kolona);
			
			if (!sadrzajKolone.isBlank()) {
				intf = switch_case_by_Kolone(adr, kolona);
				tmpIntf.add(intf);
			}
			
		}
		return tmpIntf;
	}
	
	
	private IPinterfejs switch_case_by_Kolone(String adr, String kolona) {
		Pattern pt = Pattern.compile("([89]k)|(mlx)");
		Matcher m = pt.matcher(keyval.get("Tip"));
		
		IPinterfejs intf = null;
		String esa = null;
		switch (kolona) {
			case "MUX1":
				esa = IPdefault.ipMUX_1.getNum();
				intf = new IPinterfejs(adr + esa, kolona);
				intf.setTemplejt(Templejt.tpl_RohdeSchwarz);
				intf.setHostGrp(HostGrupa.grp_DVBT);
				break;
			
			case "MUX2":
				esa = IPdefault.ipMUX_2.getNum();
				if (m.find()) {
					esa = IPdefault.ipMUX_1.getNum();
				}
				intf = new IPinterfejs(adr + esa, kolona);
				intf.setTemplejt(Templejt.tpl_RohdeSchwarz);
				intf.setHostGrp(HostGrupa.grp_DVBT);
				break;
			
			case "MUX3":
				esa = IPdefault.ipMUX_3.getNum();
				if (m.find()) {
					esa = IPdefault.ipMUX_1.getNum();
				}
				intf = new IPinterfejs(adr + esa, kolona);
				intf.setTemplejt(Templejt.tpl_RohdeSchwarz);
				intf.setHostGrp(HostGrupa.grp_DVBT);
				break;
			
			case "Agregat":
				esa = IPdefault.ipAgregat.getNum();
				intf = new IPinterfejs(adr + esa, kolona);
				intf.setTemplejt(Templejt.tpl_Agregat);
				intf.setHostGrp(HostGrupa.grp_Agregati);
				break;
			
			case "UPS":
				esa = IPdefault.ipUPS.getNum();
				intf = new IPinterfejs(adr + esa, kolona);
				intf.setTemplejt(Templejt.tpl_Ups);
				intf.setHostGrp(HostGrupa.grp_UPS);
				break;
			
		}
		return intf;
	}
	
	
	public Set<HostGrupa> getGrupe() {
		return hostGrupa;
	}
}
