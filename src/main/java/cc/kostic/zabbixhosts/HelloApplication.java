package cc.kostic.zabbixhosts;

import cc.kostic.zabbixhosts.metadata.*;
import cc.kostic.zabbixhosts.metadata.Record;
import cc.kostic.zabbixhosts.xml.ZbxExport;
import javafx.application.Application;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.CharacterCodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class HelloApplication extends Application {

	@Override
	public void start(Stage stage) throws IOException {
//		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//		Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//		stage.setTitle("Hello!");
//		stage.setScene(scene);
//		stage.show();
		
		
		String csvFile = "D:\\Instalacije_nove\\ETV\\Zabbix\\vazne informacije - 21.11.2022.csv";
		 // String csvFile = "D:\\Instalacije_nove\\ETV\\Zabbix\\vazne informacije - 21.11.2022-DEMO.csv";
		String delimiter = ",";
		List<Record> rekordi = readCSV(csvFile, delimiter);
		List<ZbxHost> hostovi = new ArrayList<>();
		

	
		for (Record r : rekordi) {
			
			if ( (r.lat.getValue() == null) || (r.lat.getValue().isBlank()) ) {
				// Ovde smo jos na nivou CSV recorda. Nepostojece koordinate ce biti null ili prazan string
				System.out.println("Lokacija " + r.lokacija.getValue() + " bez koordinata -> postavljena na \"" + Config.defaultCoord + ", " + Config.defaultCoord + "\"");
			}
			
			List<IPinterfejs> svi = r.getInterfaces();
			
			if ( (svi == null) && (Config.CREATE_HOSTS_WITHOUT_INTERFACES) ) {
				ZbxHost h = new ZbxHost(r);
				h.setName(Config.HOST_SUFFIX_BEZ_INTERFEJSA);
				// h.setTemplejt(ip.getTemplejt());
				hostovi.add(h);
			} else {
				for (IPinterfejs ip : svi) {
					ZbxHost h = new ZbxHost(r);
					h.setName(ip.getNaziv());
					h.addInterface(ip);
					h.setTemplejt(ip.getTemplejt());
					for (HostGrupa hg : ip.getHostGrpSet()){
						if (hg.isDisabled()) {
							h.setDisabled(true);
						}
						if (hg.isSkipExport()) {
							h.setSkipExport(true);
						}
					}
					hostovi.add(h);
				}
			}
		}
		
		ZbxExport ze = new ZbxExport();
		ze.sad(hostovi);
		
	}
	
	public static void main(String[] args) {
		launch();
	}
	
	
	
	
	private List<Record> readCSV(String fajl, String delimiter) {
		
		Path path = Paths.get(fajl);
		try (Reader reader = Files.newBufferedReader(path)) {
			int c = reader.read();
			if (c == 0xfeff) {
				System.out.println("Cini se kao UTF-8. Valjda. Idemo dalje.");
			} else if (c >= 0) {
				System.out.println("Fajl BOM = " + c + ", to ne lici na UTF-8, ali probacemo da idemo dalje.");
				reader.transferTo(Writer.nullWriter());
			}
		} catch (CharacterCodingException e) {
			System.out.println("NIJE UTF-8 ! " + fajl);
			System.out.println("izlazim!");
			System.exit(1);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		List<Record> dataset = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fajl));

			// prva linija je header
			String linija = br.readLine();
			String[] header = linija.split(delimiter, -1);

			// druga i sve ostale linije sadrze podatke
			while ((linija = br.readLine()) != null) {
				String[] fields = linija.split(delimiter, -1);
				
				if (fields.length < header.length) {
					// ako je splitovana linija kraca od headera, mora da je to prazna linija na kraju reda
					break;
				}
				
				
				Map<String, String> keyval = new LinkedHashMap<>();
				for (int i = 0; i < header.length; i++) {
					String k;
					k = stripQuote(header[i]);
					k = stripLeadingTrailingDots(k);
					
					String v;
					v = stripQuote(fields[i]);
					v = stripLeadingTrailingDots(v);
					keyval.put(k, v);
					
					// if ( (k==null) || (v==null) ) {
					// 	String msg = "NUll k=" + k + ", v=" + v + ", linija=\"" +  linija + "\"";
					// 		System.getLogger("null").log(System.Logger.Level.INFO, msg);
					// }
					
					
				}
				Record r = new Record(keyval);

				dataset.add(r);
			}
		} catch (FileNotFoundException ex) {
			System.getLogger("bla").log(System.Logger.Level.ERROR, "nema ga", ex);
		} catch (IOException ex) {
			System.getLogger("bla").log(System.Logger.Level.ERROR, "io exception", ex);
		}

		return dataset;
	}
	
	
	
	private String stripQuote(String s){
		if (s.contains("\"")) {
			s = s.replaceAll("\"", "");
		}
		return s;
	}
	
	
	private @Nullable String stripLeadingTrailingDots(String s){
		if (s == null) {
			return null;
		}
		
		
		if (s.startsWith(".")) {
			s = s.substring(1);
		}
		if (s.endsWith(".")) {
			s = s.substring(0, s.length() - 1);
		}
		return s;
	}
}


// TODO IP linkovi - bolje objasnjenje umesto A i B
// TODO sa Zabbix-a preuzeti koji su linkovi CAMBIUM
// TODO za Zabbix-a konfigurisati sve Ubiquiti da salju SNMP
// TODO za Zabbix-a konfigurisati sve CAMBIUM da salju SNMP
// TODO dovrsiti tpl_Cambium
// TODO dovrsiti tpl_IPLink (Ubiquity). Mozda promeniti lokaciju u naziv objekta (umesto Markove ip adrese jer to nije nikakva adresa)
// TODO Zabbix - discovery jos jednom za sve IP linkove - uporediti sa mojim excelom
// TODO Zabbix - discovery jos jednom za sve 3G modeme - uporediti sa mojim excelom


