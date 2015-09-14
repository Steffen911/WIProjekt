package schnittstellen;

import java.io.*;

public class schnittstellen {
	
	private String spielerwahl;
	
	//Muss folgendes Muster erf�llen: C:\\test\\
	private String dateipfad;
	
	//�bergabewert (spielerwahl) gibt an, welcher Spieler zugewiesen wurde
	//�bergabewert (dateipfad) gibt an, welcher der Kommunikationspfad ist
	public schnittstellen (String spielerwahl, String dateipfad)
	{
		this.spielerwahl = spielerwahl;
		this.dateipfad = dateipfad;
	}
	
	//File-Schnittstelle: AgentFile erstellen
	//�bergabewert (spielzug) gibt an, in welche Spalte gelegt werden soll
	public void fileWriter (int spielzug) throws IOException {
	
		//Auswahl, ob Spieler X oder O gew�hlt wurde
		//"X" = Spieler X
		//"O" = Spieler O
		
		//Switch Case f�r jeweilige Erstellung des AgentFiles
		switch (spielerwahl) {
		case "X":
			//Dateischreibobjekt deklarieren und erzeugen
			FileWriter fileWriterX = new FileWriter(dateipfad + "spielerx2server.txt");
			// Spaltenwahl in eine Datei schreiben
			fileWriterX.write(Integer.toString(spielzug));
			//Stream schlie�en
			fileWriterX.close();
			break;
		case "O":
			//Dateischreibobjekt deklarieren und erzeugen
			FileWriter fileWriterO = new FileWriter("C:\\eclipse\\spielero2server.txt");
			// Spaltenwahl in eine Datei schreiben
			fileWriterO.write(Integer.toString(spielzug));
			//Stream schlie�en
			fileWriterO.close();
			break;
		default:
				System.out.println("Spielerwahl falsch!");
		}
}
}
