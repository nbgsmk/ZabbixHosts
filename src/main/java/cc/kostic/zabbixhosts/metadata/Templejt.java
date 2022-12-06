package cc.kostic.zabbixhosts.metadata;

public class Templejt {
	
	public enum TPL {
		normalni,
		pristup3G,
		velikih11,
		nema
	}
	
	public TPL naziv;
	
	public Templejt(TPL naziv) {
		this.naziv = naziv;
	}
}
