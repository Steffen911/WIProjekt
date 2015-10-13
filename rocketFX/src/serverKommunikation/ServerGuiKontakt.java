package serverKommunikation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ServerGuiKontakt {
	private boolean fileSchnittstelle;
	private FileSchnittstelle fileS;
	private PushSchnittstelle pushS;
	private String spielerwahl = "o";
	//private String pfad = "..//"; //TODO: Definiere den Pfad fuer die server.ini
	
	//variablen fuer die infos aus der server.ini
	private String dateipfad = "//Users//Steffen//Dropbox//Test//";
	private int centisekunden = 500;
	private String apiKey = "c216d52b4b4db2df78b2";
	private String apiSecret = "54f848263f22144e49f4";
	private String schnittstelle = "file";
	
	
	public ServerGuiKontakt(String spielerwahl, String dateipfad, int centisekunden){
		// Konstruktor wenn FileSchnittstelle gewaehlt
		fileSchnittstelle = true;
		this.spielerwahl = spielerwahl;
		fileS = new FileSchnittstelle(spielerwahl, dateipfad, centisekunden);
	}
	
	public ServerGuiKontakt(String apiKey, String apiSecret, int centisekunden, String spielerwahl){
		// Konstruktor wenn PushSchnittstelle gewaehlt
		fileSchnittstelle = false;
		this.spielerwahl = spielerwahl;
		pushS = new PushSchnittstelle(apiKey, apiSecret, centisekunden, spielerwahl);
		pushS.connect();
	}
	
	public String[] leseVomServer(){
		if (fileSchnittstelle){
			return fileS.reader();
		}else{
			return pushS.reader();
		}
	}
	
	public void disconnect(){
		pushS.disconnect();
	}
	
	public String[] sendZugAnServer(int spielzug){
		if (fileSchnittstelle){
			return fileS.communicate(spielzug);
		}else{
			return pushS.communicate(spielzug);
		}
	}
	
	public String getSpielerwahl(){
		return spielerwahl;
	}
	
	public void schreibIni(){
		
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try{
			builder = builderFactory.newDocumentBuilder();	
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
		
		//root element
		Document document = builder.newDocument();
		Element root = document.createElement("initialisierung");
		document.appendChild(root);
		
		Element elDateipfad = document.createElement("dateipfad");
		elDateipfad.appendChild(document.createTextNode(dateipfad));
		root.appendChild(elDateipfad);
		
		Element elCentisekunden = document.createElement("centisekunden");
		elCentisekunden.appendChild(document.createTextNode(Integer.toString(centisekunden)));
		root.appendChild(elCentisekunden);
		
		Element elSpielerwahl = document.createElement("spielerwahl");
		elSpielerwahl.appendChild(document.createTextNode(spielerwahl));
		root.appendChild(elSpielerwahl);
		
		Element elApiKey = document.createElement("apiKey");
		elApiKey.appendChild(document.createTextNode(apiKey));
		root.appendChild(elApiKey);
		
		Element elApiSecret = document.createElement("apiSecret");
		elApiSecret.appendChild(document.createTextNode(apiSecret));
		root.appendChild(elApiSecret);
		
		Element elSchnittstelle = document.createElement("schnittstelle");
		elSchnittstelle.appendChild(document.createTextNode(schnittstelle));
		root.appendChild(elSchnittstelle);
		
		//write the content into ini file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;		
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(new File("server.ini"));
		
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Datei gespeichert.");
	}
	
	public void leseIni(){
		
		/*
		 * 0 = dateipfad
		 * 1 = centisekunden
		 * 2 = spielerwahl
		 * 3 = apiKey
		 * 4 = apiSecret
		 * 5 = schnittstelle
		 */
		String[] zwischenSpeicher = new String[6];
		
		//Bereitet die Extraktion des XML-Files vor
				DocumentBuilderFactory builderFactory =
				        DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = null;
				try {
				    builder = builderFactory.newDocumentBuilder();
				} catch (ParserConfigurationException e) {
				    e.printStackTrace();  
				}
				
				try {
					//Dokument wird ausgelesen vom Ordnerpfad
				    Document document = builder.parse(new FileInputStream("server.ini"));
	    
				    //root-Element wird ausgelesen
				    Element element = document.getDocumentElement();
				    
				    //alle Kinder des root-Elements werden gelesen
				    NodeList nodes = element.getChildNodes();
				    
				    int j = 0;
				    
				    for(int i=0; i<nodes.getLength(); i++){
				      Node node = nodes.item(i);

				      if(node instanceof Element){
				        //a child element to process
				        Element child = (Element) node;
				        
				        zwischenSpeicher[j] = child.getTextContent();
				        j++;
				      }
				    }   
				} catch (SAXException e) {
				    e.printStackTrace();
				} catch (IOException e) {
				    e.printStackTrace();
				}
				
				dateipfad = zwischenSpeicher[0];
				centisekunden = Integer.parseInt(zwischenSpeicher[1]);
				spielerwahl = zwischenSpeicher[2];
				apiKey = zwischenSpeicher[3];
				apiSecret = zwischenSpeicher[4];
				schnittstelle = zwischenSpeicher[5];
	}

	public String getDateipfad() {
		leseIni();
		return dateipfad;
	}

	public void setDateipfad(String dateipfad) {
		this.dateipfad = dateipfad;
		schreibIni();
	}

	public int getCentisekunden() {
		leseIni();
		return centisekunden;
	}

	public void setCentisekunden(int centisekunden) {
		this.centisekunden = centisekunden;
		schreibIni();
	}

	public String getApiKey() {
		leseIni();
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
		schreibIni();
	}

	public String getApiSecret() {
		leseIni();
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
		schreibIni();
	}

	public String getSchnittstelle() {
		leseIni();
		return schnittstelle;
	}

	public void setSchnittstelle(String schnittstelle) {
		this.schnittstelle = schnittstelle;
		schreibIni();
	}

	public void setSpielerwahl(String spielerwahl) {
		this.spielerwahl = spielerwahl;
		schreibIni();
	}
	
}
