package cc.kostic.zabbixhosts;

import cc.kostic.zabbixhosts.metadata.Geo;

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
	
	public static Geo defaultCoord = new Geo("45°45'0", "21°21'0");	// BZVZ lokacija u slucaju null
	
	public static final Set<String> interfejsKolone = new HashSet<>(Arrays.asList(
			"Pristup",
			"MUX1",
			"MUX2",
			"MUX3"
	));
	
	public static boolean CREATE_EMPTY_HOSTS = true;

	
	public static final Set<String> velikih11 = new HashSet<>(Arrays.asList(
			"Avala",
			"Besna kobila",
			"Cer",
			"Crni Vrh",
			"Crveni Čot",
			"Deli Jovan",
			"Jastrebac",
			"Kopaonik - Gobelja",
			"Ovčar",
			"Pirotski Crni vrh",
			"Rudnik",
			"Subotica",
			"Tupižnica",
			"Vršac"
	));
}
