package cc.kostic.zabbixhosts;

import cc.kostic.zabbixhosts.datamodel.*;
import cc.kostic.zabbixhosts.metadata.IPinterfejs;

import java.util.HashMap;
import java.util.Map;

public class ZbxHost {
	Map<String, IPinterfejs> sviIntf = new HashMap<>();
	private static int intfRef = 0;
	private Record record;

	
	public ZbxHost(Record record) {
		this.record = record;
	}
	
	
	public void addInterface(String naziv, IPinterfejs intf){
		intf.setInterfaceRef(intfRef);
		intfRef++;
		sviIntf.put(naziv, intf);
	}
	
	public void printYourself(){
		System.out.println(record.lokacija.toXml());
		System.out.println(record.mapID.toXml());
		
		// <tags>
		System.out.println(record.region.toXml());
		System.out.println(record.reg.toXml());
		System.out.println(record.zona.toXml());
		System.out.println(record.alot.toXml());
		System.out.println(record.locID.toXml());
//		System.out.println(record.pristup.toXml());
		System.out.println(record.mfnSfn.toXml());
		System.out.println(record.tipUredjaja.toXml());
		System.out.println(record.pw.toXml());
		System.out.println(record.sim.toXml());
		System.out.println(record.opstina.toXml());
		System.out.println(record.eps.toXml());
		// </tags>
		
		// <inventory>
		System.out.println(record.lat.toXml());
		System.out.println(record.lon.toXml());
		// </inventory>
		
		System.out.println("<inventory_mode>AUTOMATIC</inventory_mode>");
		
	}

}
