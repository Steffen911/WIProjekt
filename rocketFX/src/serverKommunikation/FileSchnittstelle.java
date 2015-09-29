package serverKommunikation;

import java.io.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class FileSchnittstelle {
	private String spielerwahl;
	
	//Muss folgendes Muster erfuellen: C:\\test\\
	private String dateipfad;
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//File-Schnittstelle: ServerFile lesen
	//Gibt ein String-Array zurueck
	public String[] reader () {
		
		//String[0] = freigabe
		//String[1] = satzstatus
		//String[2] = gegnerzug
		//String[3] = sieger
		String returnString[] = new String[4];
		
		//Prueft alle 300ms ob ein file vorhanden ist
		while(true){
			
			File f = new File(dateipfad + "server2spieler" + spielerwahl + ".xml");
			if(f.exists() && !f.isDirectory()) { break; }
			
			try {
				Thread.sleep(300);
				//System.out.println("Warten...");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
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
		    Document document = builder.parse(
		        new FileInputStream(dateipfad + "server2spieler" + spielerwahl + ".xml"));
		    
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
		
		//Loeschen der XML nach Gebrauch
		File f = new File(dateipfad + "server2spieler" + spielerwahl + ".xml");
		f.delete();
		
		//Rueckgabe des returnString
		return returnString;
	}
}
