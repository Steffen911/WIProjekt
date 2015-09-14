package schnittstellen;

import java.io.*;

public class schnittstellen {
	
	private String spielerwahl;
	
	//Muss folgendes Muster erfüllen: C:\\test\\
	private String dateipfad;
	
	//Übergabewert (spielerwahl) gibt an, welcher Spieler zugewiesen wurde
	//Übergabewert (dateipfad) gibt an, welcher der Kommunikationspfad ist
	public schnittstellen (String spielerwahl, String dateipfad)
	{
		this.spielerwahl = spielerwahl;
		this.dateipfad = dateipfad;
	}
	
	//File-Schnittstelle: AgentFile erstellen
	//Übergabewert (spielzug) gibt an, in welche Spalte gelegt werden soll
	public void fileWriter (int spielzug) throws IOException {
	
		//Auswahl, ob Spieler X oder O gewählt wurde
		//"X" = Spieler X
		//"O" = Spieler O
		
		//Switch Case für jeweilige Erstellung des AgentFiles
		switch (spielerwahl) {
		case "X":
			//Dateischreibobjekt deklarieren und erzeugen
			FileWriter fileWriterX = new FileWriter(dateipfad + "spielerx2server.txt");
			// Spaltenwahl in eine Datei schreiben
			fileWriterX.write(Integer.toString(spielzug));
			//Stream schließen
			fileWriterX.close();
			break;
		case "O":
			//Dateischreibobjekt deklarieren und erzeugen
			FileWriter fileWriterO = new FileWriter("C:\\eclipse\\spielero2server.txt");
			// Spaltenwahl in eine Datei schreiben
			fileWriterO.write(Integer.toString(spielzug));
			//Stream schließen
			fileWriterO.close();
			break;
		default:
				System.out.println("Spielerwahl falsch!");
		}
}
}
