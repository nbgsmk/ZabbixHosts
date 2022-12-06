package cc.kostic.zabbixhosts;

import cc.kostic.zabbixhosts.metadata.IPinterfejs;
import cc.kostic.zabbixhosts.xml.ZbxExport;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HelloApplication extends Application {

	@Override
	public void start(Stage stage) throws IOException {
//		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//		Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//		stage.setTitle("Hello!");
//		stage.setScene(scene);
//		stage.show();
		
		
//		String csvFile = "D:\\Instalacije_nove\\ETV\\Zabbix\\vazne informacije - 21.11.2022.csv";
		String csvFile = "D:\\Instalacije_nove\\ETV\\Zabbix\\vazne informacije - 21.11.2022-DEMO.csv";
		String delimiter = ",";
		List<Record> rekordi = readCSV(csvFile, delimiter);
		List<ZbxHost> hostovi = new ArrayList<>();
		
	
		for (Record r : rekordi) {
			if (Config.currentMode == Config.MODE.SVI_MUX_U_ISTI_HOST) {
				ZbxHost h = new ZbxHost(r);
				List<IPinterfejs> svi = r.getInterfaces();
				for (IPinterfejs ip : svi){
					h.addInterface(ip);
				}
				h.setTemplejt(r.getTemplejtZajednicki());
				hostovi.add(h);
			}
			if (Config.currentMode == Config.MODE.SVAKI_MUX_POSEBAN_HOST) {
				List<IPinterfejs> svi = r.getInterfaces();
				for (IPinterfejs ip : svi){
					ZbxHost h = new ZbxHost(r);
					h.setName(ip.getNaziv());
					h.addInterface(ip);
					h.setTemplejt(ip.getTemplejt());
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
		List<Record> dataset = new ArrayList<>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(fajl));

			String linija = br.readLine();
			String[] header = linija.split(delimiter, -1);

			while ((linija = br.readLine()) != null) {
				String[] field = linija.split(delimiter, -1);

				Map<String, String> keyval = new LinkedHashMap<>();
				for (int i = 0; i < header.length; i++) {
					String k = stripQuote(header[i]);
					String v = stripQuote(field[i]);
					keyval.put(k, v);
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
	

}