package cc.kostic.zabbixhosts;

import cc.kostic.zabbixhosts.datamodel.Pristup;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class HelloApplication extends Application {
	private enum MODE {SVAKI_MUX_POSEBAN_HOST,SVI_MUX_U_ISTI_HOST};
	private MODE currentMode = MODE.SVAKI_MUX_POSEBAN_HOST;
	public static final Set<String> interfejsi = new HashSet<>(Arrays.asList("Pristup", "MUX1", "MUX2", "MUX3"));
	
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 320, 240);
		stage.setTitle("Hello!");
		stage.setScene(scene);
		stage.show();
		
		
		String csvFile = "D:\\Instalacije_nove\\ETV\\Zabbix\\vazne informacije - 21.11.2022.csv";
		String delimiter = ",";
		List<Record> rekordi = readCSV(csvFile, delimiter);

		List<ZbxHost> hostovi = new ArrayList<>();
		
		Record r = rekordi.get(1);
		if (currentMode == MODE.SVI_MUX_U_ISTI_HOST) {
			ZbxHost h = new ZbxHost(r);
			for (String intf : interfejsi) {
				h.addInterface(intf);
			}
			hostovi.add(h);
		}
		if (currentMode == MODE.SVAKI_MUX_POSEBAN_HOST) {
			for (String intf : interfejsi) {
				ZbxHost h = new ZbxHost(r);
				h.addInterface(intf);
				hostovi.add(h);
			}
		}
		
		
		
		
	}
	
	public static void main(String[] args) {
		launch();
	}
	
	
	
	
	private List<Record> readCSV(String fajl, String delimiter) {
		
		List<Record> dataset = new ArrayList<>();
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(fajl));
			
			String linija = br.readLine();
			String[] header = linija.split(delimiter, -1);
			
			while ((linija = br.readLine()) != null) {
				String[] field = linija.split(delimiter, -1);
				
//				Record r = new Record();
//				for (int i = 0; i < header.length; i++) {
//					r.add(header[i], field[i]);
//				}
				
				Map<String, String> elementi = new LinkedHashMap<>();
				for (int i = 0; i < header.length; i++) {
					String k = stripQuote(header[i]);
					String v = stripQuote(field[i]);
					elementi.put(k, v);
				}
				Record r = new Record(elementi);
				
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
	

}