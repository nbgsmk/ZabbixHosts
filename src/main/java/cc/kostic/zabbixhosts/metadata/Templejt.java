package cc.kostic.zabbixhosts.metadata;

public enum Templejt {
	
	tpl_3G("tpl_ICMP Ping 12h"),
	tpl_Agregat("tpl_Agregat"),
	
	tpl_Default("tpl_Default"),
	
	tpl_HCLink("tpl_HCLink"),
	tpl_IPLink("tpl_IPLink_Ubiquity"),
	tpl_IPLinkCAMB("tpl_IPLinkCambium"),
	
	tpl_RohdeSchwarz("tpl_RohdeSchwarz"),
	
	tpl_Ups_generic("tpl_Ups_generic"),
	tpl_Ups_Galaxy("tpl_Ups_Galaxy"),
	tpl_Ups_NetBots("tpl_Ups_NetBotz"),
	tpl_Ups_NetVision("tpl_Ups_NetVision"),
	tpl_Ups_Symmetra("tpl_Ups_Symmetra"),
	
	tpl_Velikih11("tpl_Velikih11"),
	;
	
	public final String naziv;
	
	Templejt(String naziv) {
		this.naziv = naziv;
	}
	
	public String getNaziv() {
		return naziv;
	}
}
