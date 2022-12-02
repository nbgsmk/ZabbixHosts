package cc.kostic.zabbixhosts.metadata;

import java.util.Map;

public class IPinterfejs {
	
	private String adresa;
	private TEMPLEJT.PROFIL templejt;
	String naziv;
	private int interfaceRef;
	
	
	public IPinterfejs(TEMPLEJT.PROFIL templejt, String adresa) {
		this.templejt = templejt;
		this.adresa = adresa;
	}
	
	public IPinterfejs(String adresa, String naziv) {
		this.adresa = adresa;
		this.naziv = naziv;
	}
	
	public String getAdresa() {
		return adresa;
	}
	
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	
	public TEMPLEJT.PROFIL getTemplejt() {
		return templejt;
	}
	
	public void setTemplejt(TEMPLEJT.PROFIL templejt) {
		this.templejt = templejt;
	}
	
	public int getInterfaceRef() {
		return interfaceRef;
	}
	
	public String getNaziv() {
		return naziv;
	}
	
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	public void setInterfaceRef(int interfaceRef) {
		this.interfaceRef = interfaceRef;
	}
}
