package cc.kostic.zabbixhosts.metadata;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GeoTest {
	
	@Test
	void convertTest() {
		Geo g = new Geo("a", "b");
		// just for the kicks
		assertEquals(44.5f, 44.5f);
		// check zero
		assertEquals(0, g.convert("0°0'0"));
		// leading zeroes or spaces
		assertEquals(44.0f, g.convert("44°0'0.0"));
		assertEquals(44.0f, g.convert("44°00'0.0"));
		assertEquals(44.0f, g.convert("44°00'000.0"));
		assertEquals(44.0f, g.convert("44° 0'0.0"));
		assertEquals(44.0f, g.convert("44° 0' 0.0"));
		
		// check computation
		assertEquals(44.5f, g.convert("44°30'0"));
		assertEquals(44.5f, g.convert("44°30'0.0"));
		assertEquals(44.5f, g.convert("44°30' 0.0"));
		assertEquals(44.008335f, g.convert("44°0'30"));
		assertEquals(44.008335f, g.convert("44°00'30.00"));
		assertEquals(44.000126f, g.convert("44°0'00.45"));
	}
}