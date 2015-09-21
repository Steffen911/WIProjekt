package serverKommunikation;

import java.io.*;

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
	public String[] fileWriter (int spielzug) throws IOException {
	
		//Auswahl, ob Spieler x oder o gewaehlt wurde
		//"x" = Spieler x
		//"o" = Spieler o
		
		//Dateischreibobjekt deklarieren und erzeugen
		FileWriter fileWriter = new FileWriter(dateipfad + "spieler" + spielerwahl + "2server.txt");
		
		//Spaltenwahl in eine Datei schreiben
		fileWriter.write(Integer.toString(spielzug));
		
		//Stream schliessen
		fileWriter.close();
		
		String returnString[] = fileReader();
		return returnString;
		
	}
	
	//File-Schnittstelle: ServerFile lesen
	public String[] fileReader () {
		
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
		
		//Loeschen der XML nach Gebrauch
		File f = new File(dateipfad + "server2spieler" + spielerwahl + ".xml");
		f.delete();
		
		//Rueckgabe des returnString
		return returnString;
	}
}
