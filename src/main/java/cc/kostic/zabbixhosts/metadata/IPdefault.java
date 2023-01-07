package cc.kostic.zabbixhosts.metadata;

public enum IPdefault {
	ip3G(1),
	ipLinkBlizi(100),
	ipLinkDalji(101),
	ipMUX_1(161),
	ipMUX_2(162),
	ipMUX_3(163),
	ipAgregat(93),
	ipUPS(90),
			;
	
	private final String adr;
	
	IPdefault(int adr) {
		this.adr = String.valueOf(adr);
	}
	
	public String getNum() {
		return String.valueOf(adr);
	}

	
}
