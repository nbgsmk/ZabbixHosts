package cc.kostic.zabbixhosts.metadata;

public enum Templejt {
	
	Pristup3G("tpl_3G"),
	Agregat("tpl_agregat"),
	PristupHCLink("tpl_HCLink"),
	PristupIPLink("tpl_IPLink"),
	Nema("tpl_Nema"),
	Normalni("tpl_Norm"),
	Velikih11("tpl_Velikih11"),
	Ups("tpl_Ups");
	
	public final String naziv;
	
	Templejt(String naziv) {
		this.naziv = naziv;
	}
	
	public String getNaziv() {
		return naziv;
	}
}
