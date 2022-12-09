package cc.kostic.zabbixhosts;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Config {
	public static enum MODE {
		SVAKI_MUX_POSEBAN_HOST,
		SVI_MUX_U_ISTI_HOST
	};
	
	public enum TEMPLEJT {
	
	}
	
	public static MODE currentMode = MODE.SVAKI_MUX_POSEBAN_HOST;
//	public static MODE currentMode = MODE.SVI_MUX_U_ISTI_HOST;
	
	public static final Set<String> interfejsKolone = new HashSet<>(Arrays.asList(
			"Pristup",
			"MUX1",
			"MUX2",
			"MUX3"
	));
	
	public static boolean CREATE_EMPTY_HOSTS = true;

	
	public static final Set<String> velikih11 = new HashSet<>(Arrays.asList(
			"Avala",
			"Cer",
			"Crveni Cot",	// TODO yu slova
			"Crni Vrh"
	));
}
