package cc.kostic.zabbixhosts.metadata;

import cc.kostic.zabbixhosts.Config;
import org.jetbrains.annotations.VisibleForTesting;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Geo {
	
	private String latStr;
	private String lonStr;
	
	public Geo(String lat, String lon) {
		this.latStr = lat;
		this.lonStr = lon;
//		if (lat == null) {
//			this.latStr = Config.defaultCoord.latStr;
//		}
//		if (lon == null) {
//			this.lonStr = Config.defaultCoord.lonStr;
//		}
//
	}
	
	
	public List<Element> getElement(Document doc) {
		Element latEl = doc.createElement("location_lat");
		Element lonEl = doc.createElement("location_lon");
		
		latEl.appendChild(doc.createTextNode(convert(latStr).toString()));
		lonEl.appendChild(doc.createTextNode(convert(lonStr).toString()));
		
		List<Element> tmp = new ArrayList<>();
		tmp.add(latEl);
		tmp.add(lonEl);
		return tmp;
	}
	
	public Float convert(String coord){
		Float dec = null;
		try {
			dec = null;
			String[] s = coord.trim().split("(°)|(')|(\")");
			Float[] decimal = new Float[3];
			for (int i = 0; i < s.length; i++) {
				decimal[i] = Float.valueOf(s[i].strip());
			}
			dec = decimal[0];
			dec += decimal[1] / 60.0f;
			dec += decimal[2] / (60.0f * 60.0f);
		} catch (NumberFormatException e) {
			dec = 0f;
			System.out.println("Hostovi sa koordinatama 0.0 postoje!!");
		}
		return dec;
	}
	
	
}
