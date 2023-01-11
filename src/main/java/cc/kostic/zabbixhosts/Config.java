package cc.kostic.zabbixhosts;

import cc.kostic.zabbixhosts.metadata.Geo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Config {
	
	
	public static Float defaultCoord = 0f;       // BZVZ lokacija u slucaju null
	
	public static final Set<String> interfejsKolone = new HashSet<>(Arrays.asList(
			// "Pristup",   // Podrazumeva se da ovo kreira interfejse
			"MUX1",
			"MUX2",
			"MUX3",
			"Agregat",
			"UPS"
	));
	
	public static boolean CREATE_HOSTS_WITHOUT_INTERFACES = true;
	public static boolean EXPORT_GROUPS_SUMMARY = false;

	public static final String HOST_SUFFIX_BEZ_INTERFEJSA = "n-a";
	
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

	public static final Set<String> CambiumIPlink = new HashSet<>(Arrays.asList(
			"Ljubovija - Nemić"
	));
}
