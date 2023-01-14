package cc.kostic.zabbixhosts.metadata;

public enum HostGrupa {
	
	grp_3G("grp_3G",                    false, false),
	grp_Agregati("grp_Agregati",        false, false),
	grp_DVBT("grp_DVBT",                true, false),
	grp_HCLink("grp_HCLink",            false, false),
	grp_IPLink("grp_IPLink",            false, false),
	grp_NemaPristup("grp_NemaPristup",  false, false),
	grp_UPS("grp_UPS",                  false, false),
	grp_Velikih11("grp_Velikih11",      false, false),
	
	grp_MUX1("grp_MUX1", false, false),
	grp_MUX2("grp_MUX2", false, false),
	grp_MUX3("grp_MUX3", false, false),
	;
		
	
	private final String name;
	private final boolean disabled;     // host ce biti eksportovan kao <status>DISABLED
	private final boolean skipExport;   // host ce biti potpuno iskljucen iz eksporta
	private String uuid;
	
	HostGrupa(String name, boolean disabled, boolean skipExport) {
		this.name = name;
		this.disabled = disabled;
		this.skipExport = skipExport;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isDisabled() {
		return disabled;
	}
	
	public boolean isSkipExport() {
		return skipExport;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
