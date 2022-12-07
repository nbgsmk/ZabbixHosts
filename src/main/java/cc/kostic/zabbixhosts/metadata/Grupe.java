package cc.kostic.zabbixhosts.metadata;

public class Grupe {
	
	public enum HOSTGRUPE {
		grp3G,
		grpVelikih11,
		grpAgregati,
		grpNemaPristup
		
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
