package cc.kostic.zabbixhosts.metadata;

public enum Templejt {
	
	tpl_3G("tpl_3G"),
	tpl_Agregat("tpl_agregat"),
	tpl_RohdeSchwarz("tpl_RohdeSchwarz"),
	tpl_HCLink("tpl_HCLink"),
	tpl_IPLink("tpl_IPLink"),
	tpl_IPLinkCAMB("tpl_IPLinkCambium"),
	tpl_Nema("tpl_Nema"),
	tpl_Normal("tpl_Normal"),
	tpl_Velikih11("tpl_Velikih11"),
	tpl_Ups("tpl_Ups");
	
	public final String naziv;
	
	Templejt(String naziv) {
		this.naziv = naziv;
	}
	
	public String getNaziv() {
		return naziv;
	}
}
