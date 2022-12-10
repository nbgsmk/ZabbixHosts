package cc.kostic.zabbixhosts.metadata;

public class Templejt {
	
	public enum TPL {
		tpl_Normalni,
		tpl_Pristup3G,
		tpl_Velikih11,
		tpl_Nema
	}
	
	public TPL naziv;
	
	public Templejt(TPL naziv) {
		this.naziv = naziv;
	}
}
