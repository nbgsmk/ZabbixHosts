package cc.kostic.zabbixhosts.datamodel;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class CsvELTest {
	
	// fejk objekat za testiranje
	MapID m = new MapID(new HashMap<>(), "b");

	@Test
	void stripYu() {
		assertEquals("s S", m.stripYu("š Š"));
		assertEquals("dj Dj", m.stripYu("đ Đ"));
		assertEquals("cC", m.stripYu("čČ"));
		assertEquals("cC", m.stripYu("ćĆ"));
		assertEquals("zZ", m.stripYu("žŽ"));

		assertEquals("pasteta", m.stripYu("pašteta"));
		assertEquals("ZJez jez", m.stripYu("ŽJež jež"));
		assertEquals("djak", m.stripYu("đak"));
		assertEquals("kaDj", m.stripYu("kaĐ"));
		assertEquals("sSabac", m.stripYu("šŠabac"));
		
		String a = "patkabczec";
		assertEquals("patk-x-zec", a.replaceAll("abc", "-x-"));
		
		
		
	}
	
	@Test
	void removeDiacriticalMarksTest() {
		assertEquals("Gracisce", m.removeDiacriticalMarks("Gračišće"));
	}
	
	
	@Test
	void patkaZecTest(){
		Pattern p = Pattern.compile(".s");//. represents single character
		Matcher m = p.matcher("as");
		boolean b1 = m.matches();
  
		//2nd way
		boolean b2=Pattern.compile(".s").matcher("as").matches();
		
		//3rd way
		boolean b3 = Pattern.matches(".s", "as");
		
		System.out.println(b1+" "+b2+" "+b3);
		
	}
}