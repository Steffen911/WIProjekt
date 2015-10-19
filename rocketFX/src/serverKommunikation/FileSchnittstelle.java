package serverKommunikation;

import java.io.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class FileSchnittstelle {
	private String spielerwahl;
	
	//Muss folgendes Muster erfuellen: C:\\test\\
	private String dateipfad;
	
	private String freigabe;
	private String satzStatus;
	private String gegnerZug;
	private String sieger;
	
	//TimerMethode wird initialisiert
	TimerStart ts = new TimerStart();
	
	//Spielzugdauer wird festgelegt
	int centisekunden;
	
	//Uebergabewert (spielerwahl) gibt an, welcher Spieler zugewiesen wurde
	//Uebergabewert (dateipfad) gibt an, welcher der Kommunikationspfad ist
	//Uebergabewert (sekunden) gibt an, wie lange ein Spielzug dauern darf
	public FileSchnittstelle (String spielerwahl, String dateipfad, int centisekunden)
	{
		this.spielerwahl = spielerwahl;
		this.dateipfad = dateipfad;
		this.centisekunden = centisekunden;
	}
	
	//Communicate Methode sendet spielzug an Server und gibt StringArray vom Server zurueck
	public String[] communicate(int spielzug) {
		writer(spielzug);
		return reader();
	}
	
	//File-Schnittstelle: AgentFile erstellen
	//Uebergabewert (spielzug) gibt an, in welche Spalte gelegt werden soll
	public void writer (int spielzug) {
	
		//Auswahl, ob Spieler x oder o gewaehlt wurde
		//"x" = Spieler x
		//"o" = Spieler o
		
		//Dateischreibobjekt deklarieren und erzeugen
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(dateipfad + "spieler" + spielerwahl + "2server.txt");
		
			//Spaltenwahl in eine Datei schreiben
			fileWriter.write(Integer.toString(spielzug));
		
			System.out.println("Datei auf Server abgelegt.");
		
			//Stream schliessen
			fileWriter.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//File-Schnittstelle: ServerFile lesen
	//Gibt ein String-Array zurueck
	public String[] reader () {
		
		String[] returnString = new String[4];
		//String[0] = freigabe
		//String[1] = satzstatus
		//String[2] = gegnerzug
		//String[3] = sieger
		
		//Prueft alle 500ms ob ein file vorhanden ist
		while(true){
				
			if(xmlExtraction()){
				returnString[0] = freigabe;
				returnString[1] = satzStatus;
				returnString[2] = gegnerZug;
				returnString[3] = sieger;
				return returnString;
			}else{
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}	
		}		
	}
	
	public boolean xmlExtraction(){
		String[] returnString = new String[4];
		
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
			
			Document document = null;
			//Dokument wird ausgelesen vom Ordnerpfad
			try{
				FileInputStream fs = 
					new FileInputStream(dateipfad + "server2spieler" + spielerwahl + ".xml");
				document = builder.parse(fs);
				
				fs.close();
			}catch(FileNotFoundException e){
				return false;
			}
			
			//Loeschen der XML nach Gebrauch
			File f = new File(dateipfad + "server2spieler" + spielerwahl + ".xml");
			Boolean fileDeleted = f.delete();
			
			System.out.println("File wurde geloescht: " + fileDeleted);
			
			//Startet den Timer, der die Spielzugzeit bestimmt und gibt nach Ablauf eine Meldung in der Konsole aus
			ts.startTimer(centisekunden);
			
			//root-Element wird ausgelesen
			Element element = document.getDocumentElement();
			
			//alle Kinder des root-Elements werden gelesen
			NodeList nodes = element.getChildNodes();
			
			int j = 0; //Zaehler fuer die 4 zu lesenden Attribute im returnstring
			
			for(int i=0; i<nodes.getLength(); i++){
				Node node = nodes.item(i);
				
				if(node instanceof Element){
					//a child element to process
					Element child = (Element) node;
					
					//Fuegt dem returnString den Inhalt des aktuellen Kinds des root-Elements hinzu
					returnString[j] = child.getTextContent();
					j++;
				}
			}   
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		freigabe = returnString[0];
		satzStatus = returnString[1];
		gegnerZug = returnString[2];
		sieger = returnString[3];
		
		//Loeschen der XML nach Gebrauch
//		File f = new File(dateipfad + "server2spieler" + spielerwahl + ".xml");
//		f.delete();
		
		//Rueckgabe des returnString
		return true;
	}
}
