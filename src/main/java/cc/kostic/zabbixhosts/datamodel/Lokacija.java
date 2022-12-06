package cc.kostic.zabbixhosts.datamodel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Lokacija extends CsvEL {
	
	private String nameAscii;
	private String nameUtf;
	
	public Lokacija(Map<String, String> elementi, String csvHeader) {
		super(elementi, csvHeader);
		this.nameUtf = super.getValue();
		this.nameAscii = stripYu(nameUtf);
	}
	

	
//	@Override
	public List<Element> getElements(Document doc) {
		List<Element> tmp = new ArrayList<>();
		
		Element lokacijaAsc = doc.createElement("host");				// ASCII naziv lokacije
		lokacijaAsc.appendChild(doc.createTextNode(this.nameAscii));
		tmp.add(lokacijaAsc);
		
		if ( ! nameUtf.equalsIgnoreCase(nameAscii) ) {
			Element lokacijaUtf = doc.createElement("name");			// UTF naziv lokacije, ako postoji
			lokacijaUtf.appendChild(doc.createTextNode(nameUtf));
			tmp.add(lokacijaUtf);
		}
		return tmp;
	}
}
