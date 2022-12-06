package cc.kostic.zabbixhosts.datamodel;

import cc.kostic.zabbixhosts.Config;
import org.jetbrains.annotations.VisibleForTesting;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class CsvEL {
	protected Map<String, String> elementi;
	protected String csvHeader;
	protected String csvValue;
	
	
	public CsvEL(Map<String, String> elementi, String csvHeader) {
		this.elementi = elementi;
		this.csvHeader = csvHeader;
		this.csvValue = elementi.get(csvHeader);
	}
	
	public String toXml(){
		String T = "\t";
		String N = "\n";
		if ( ! Config.PRETTY_PRINT ) {
			T = "";
			N = "";
		}
		
		
		String s =
			"<tag>" + N +
				T + "<tag>" + this.csvHeader + "</tag>" + N +
				T + "<value>" + this.csvValue + "</value>" + N +
			"</tag>"
			;
		return s;
	};
	
	@Override
	public String toString() {
		return csvHeader + ", " + csvValue;
	}
	

//	protected abstract List<Element> getElements(Document doc);
	
	
	
	@VisibleForTesting
	protected String stripYu(String s){
//		return removeDiacriticalMarks(s);
		s = s.replace("Š", "S");
		s = s.replace("š", "s");

		s = s.replaceAll("Đ", "Dj");
		s = s.replaceAll("đ", "dj");

		s = s.replaceAll("Č", "C");
		s = s.replaceAll("č", "c");

		s = s.replaceAll("Ć", "C");
		s = s.replaceAll("ć", "c");

		s = s.replaceAll("Ž", "Z");
		s = s.replaceAll("ž", "z");

		s = stripLeadingTrailingDots(s);
		return s;
	}
	
	private String stripLeadingTrailingDots(String s){
		if (s.startsWith(".")) {
			s = s.substring(1);
		}
		if (s.endsWith(".")) {
			s = s.substring(0, s.length() - 1);
		}
		return s;
	}
	
	public String removeDiacriticalMarks(String string) {
		return Normalizer.normalize(string, Normalizer.Form.NFD)
				.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}
	
	public String getHeader() {
		return csvHeader;
	}
	
	public String getValue() {
		return csvValue;
	}
}
