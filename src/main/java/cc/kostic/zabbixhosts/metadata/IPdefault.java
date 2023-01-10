package cc.kostic.zabbixhosts.metadata;

public enum IPdefault {
	ip3G(1),
	ipLinkBlizi(100),
	ipLinkDalji(101),
	ipMUX_1(161),
	ipMUX_2(162),
	ipMUX_3(163),
	ipAgregat(93),
	// ipUPS(90),
			;
	
	private String adr;

	IPdefault(int adr) {
		this.adr = String.valueOf(adr);
	}

	public String getNum() {
		return String.valueOf(adr);
	}


	public static Templejt getCplxTemplate(String cplx){
		String[] t = cplx.split(";");
		String tpl = t[0];
		switch (tpl){
			case "Galaxy 3500" -> {
				return Templejt.tpl_Ups_Galaxy;
			}
			case "NetBotz" -> {
				return Templejt.tpl_Ups_NetBots;
			}
			case "NetVision" -> {
				return Templejt.tpl_Ups_NetVision;
			}
			case "Symmetra" ->{
				return Templejt.tpl_Ups_Symmetra;
			}
			default -> {
				return Templejt.tpl_Ups_generic;
			}
		}
	}

	public static String getCplxAdr(String cplx){
		String[] s = cplx.split(";");
		return s[1].trim();
	}
	
}
