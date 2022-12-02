package cc.kostic.zabbixhosts.datamodel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElementTest {
	
	// fejk objekat za testiranje
	MapID m = new MapID("a", "b");

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
		
	}
}