package cc.kostic.zabbixhosts.metadata;

public class Grupe {
	
	public enum HOSTGRUPE {
		grp_3G,
		grp_IPLink,
		grp_HCLink,
		grp_Agregati,
		grp_UPS,
		grp_DVBT,
		grp_Velikih11,
		grp_NemaPristup
		
	}
	private String name;
	private String uuid;
	
	public Grupe(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
