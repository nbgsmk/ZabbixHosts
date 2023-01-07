package cc.kostic.zabbixhosts.metadata;

import cc.kostic.zabbixhosts.Config;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class CsvEL {
	protected Map<String, String> elementi;
	protected @Nullable String csvHeader;
	protected @Nullable String csvValue;
	
	
	public CsvEL(Map<String, String> elementi, String csvHeader) {
		this.elementi = elementi;
		this.csvHeader = csvHeader;
		this.csvValue = elementi.get(csvHeader);
	}
	

	
	@Override
	public String toString() {
		return csvHeader + ", " + csvValue;
	}
	

	
	@VisibleForTesting
	public String stripYu(String s){
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
		
		s = s.replaceAll("['()']", "");

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
