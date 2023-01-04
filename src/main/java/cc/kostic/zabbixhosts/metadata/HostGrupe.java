package cc.kostic.zabbixhosts.metadata;

public enum HostGrupe {
	
	grp_3G("grp_3G"),
	grp_Agregati("grp_Agregati"),
	grp_DVBT("grp_DVBT"),
	grp_HCLink("grp_HCLink"),
	grp_IPLink("grp_IPLink"),
	grp_NemaPristup("grp_NemaPristup"),
	grp_UPS("grp_UPS"),
	grp_Velikih11("grp_Velikih11")
	;
		
	
	private final String name;
	private String uuid;
	
	HostGrupe(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
