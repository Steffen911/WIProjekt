package serverKommunikation;

import java.io.*;
import java.nio.file.Files;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class FileSchnittstelle {
	private String spielerwahl;
	
	//Muss folgendes Muster erfuellen: C:\\test\\
	private String dateipfad;
	
	//Uebergabewert (spielerwahl) gibt an, welcher Spieler zugewiesen wurde
	//Uebergabewert (dateipfad) gibt an, welcher der Kommunikationspfad ist
	public FileSchnittstelle (String spielerwahl, String dateipfad)
	{
		this.spielerwahl = spielerwahl;
		this.dateipfad = dateipfad;
	}
	
	//File-Schnittstelle: AgentFile erstellen
	//Uebergabewert (spielzug) gibt an, in welche Spalte gelegt werden soll
	public void fileWriter (int spielzug) throws IOException {
	
		//Auswahl, ob Spieler x oder o gewaehlt wurde
		//"x" = Spieler x
		//"o" = Spieler o
		
		//Switch Case fuer jeweilige Erstellung des AgentFiles
		switch (spielerwahl) {
		case "x":
			//Dateischreibobjekt deklarieren und erzeugen
			FileWriter fileWriterX = new FileWriter(dateipfad + "spielerx2server.txt");
			// Spaltenwahl in eine Datei schreiben
			fileWriterX.write(Integer.toString(spielzug));
			//Stream schliessen
			fileWriterX.close();
			break;
		case "o":
			//Dateischreibobjekt deklarieren und erzeugen
			FileWriter fileWriterO = new FileWriter("C:\\eclipse\\spielero2server.txt");
			// Spaltenwahl in eine Datei schreiben
			fileWriterO.write(Integer.toString(spielzug));
			//Stream schliessen
			fileWriterO.close();
			break;
		default:
				System.out.println("Spielerwahl falsch!");
		}
	}
	
	//File-Schnittstelle: ServerFile lesen
	public String[] fileReader () {
		
		//String[0] = freigabe
		//String[1] = satzstatus
		//String[2] = gegnerzug
		//String[3] = sieger
		String returnString[] = new String[4];
		
		//Prüft alle 300ms ob ein file vorhanden ist
		while(true){
			
			File f = new File(dateipfad + "server2spieler" + spielerwahl + ".xml");
			if(f.exists() && !f.isDirectory()) { break; }
			
			try {
				Thread.sleep(300);
				System.out.println("Warten...");
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
		    
		    //root-Element wird ausgelesen
		    Element element = document.getDocumentElement();
		    
		    //alle Kinder des root-Elements werden gelesen
		    NodeList nodes = element.getChildNodes();

		    int j = 0; //Zähler für die 4 zu lesenden Attribute im returnstring
		    
		    for(int i=0; i<nodes.getLength(); i++){
		      Node node = nodes.item(i);

		      if(node instanceof Element){
		        //a child element to process
		        Element child = (Element) node;
		        
		        //Fügt dem returnString den Inhalt des aktuellen Kinds des root-Elements hinzu
		        returnString[j] = child.getTextContent();
		        j++;
		      }
		    }   
		} catch (SAXException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		//Testweise Ausgabe des Returnstrings in der Konsole
		for (int i=0; i<returnString.length; i++){
			System.out.println(returnString[i]);
		}
		
		//Löschen der XML nach Gebrauch
		File f = new File(dateipfad + "server2spieler" + spielerwahl + ".xml");
		f.delete();
		
		//Rückgabe des returnString
		return returnString;
	}
}
