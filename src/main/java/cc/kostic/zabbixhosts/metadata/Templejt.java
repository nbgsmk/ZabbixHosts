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

	tpl_Ups_generic("tpl_generic"),
	tpl_Ups_Galaxy("tpl_Ups_Galaxy"),
	tpl_Ups_NetBots("tpl_Ups_NetBotz"),
	tpl_Ups_NetVision("tpl_Ups_NetVision"),
	tpl_Ups_Symmetra("tpl_Ups_Symmetra"),
	;
	
	public final String naziv;
	
	Templejt(String naziv) {
		this.naziv = naziv;
	}
	
	public String getNaziv() {
		return naziv;
	}
}
