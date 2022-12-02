package cc.kostic.zabbixhosts.datamodel;

import org.jetbrains.annotations.VisibleForTesting;

public abstract class Element {
	protected String csvHeader;
	protected String csvValue;
	
	public Element(String csvHeader, String csvValue) {
		this.csvHeader = csvHeader;
		this.csvValue = csvValue;
	}
	
	public String toString(){
		String s =
				"<tag>" +
						"<tag>" + this.csvHeader + "</tag>" +
						"<value>" + this.csvValue + "</value>" +
						"</tag>"
				;
		return s;
		
	};

//	public String toTag(String k, String v){
//		String s =
//				"<tag>" +
//						"<tag>" + k + "</tag>" +
//						"<value>" + v + "</value>" +
//						"</tag>"
//				;
//		return s;
//	}
	
	
	
	@VisibleForTesting
	protected String stripYu(String s){
		s = s.replaceAll("Š", "S");
		s = s.replaceAll("š", "s");
		
		s = s.replaceAll("Đ", "Dj");
		s = s.replaceAll("đ", "dj");
		
		s = s.replaceAll("Č", "C");
		s = s.replaceAll("č", "c");
		
		s = s.replaceAll("Ć", "C");
		s = s.replaceAll("ć", "c");
		
		s = s.replaceAll("Ž", "Z");
		s = s.replaceAll("ž", "z");
		
		s = cleanup(s);
		return s;
	}
	
	private String cleanup(String s){
		if (s.startsWith(".")) {
			s = s.substring(1);
		}
		if (s.endsWith(".")) {
			s = s.substring(0, s.length() - 1);
		}
		return s;
	}

}
