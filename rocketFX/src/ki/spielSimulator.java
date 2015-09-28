package ki;

import serverKommunikation.*;

public class spielSimulator {
	
	private static String spielerwahl = "o";

	public static void main(String[] args) {
		
		KI ki = new KI(spielerwahl);
		
		//Fileschnittstelle (Einkommentieren fuer File)
		FileSchnittstelle sst = new FileSchnittstelle(spielerwahl, "//Users//Steffen//Dropbox//Test//", 50);
		
		//Pushschnittstelle (Einkommentieren fuer Push)
		//PushSchnittstelle sst = new PushSchnittstelle("c216d52b4b4db2df78b2", "54f848263f22144e49f4", 50);
		
		String[] rueckgabe = new String[4];
		
		//Lies Server XML das erste mal aus, berechne den ersten Spielzug und springe im Anschluss in die Dialogschleife
		rueckgabe = sst.fileReader();
		
		int spielzug;
		
		while(!rueckgabe[1].equals("beendet")){
			//Berechne neuen Spielzug auf Grundlage des gegnerzugs
			spielzug = ki.zugBerechnen(Integer.parseInt(rueckgabe[2]));
			
			//Sende errechneten Spielzug an Server und warte auf XML
			rueckgabe = sst.communicate(spielzug);
			
			//Gib aktuelles Array aus
			String[][] ausgabe = ki.arrayAusgabe();
			
			for(int i=5; i>=0; i--){	
				for(int j=0; j<7; j++){			
					System.out.print(ausgabe[j][i] + " ");
				}
				System.out.print("\n");
			}
			
			//Starte von vorn
		}
		
		System.out.println("Jemand hat gewonnen.");
	}

}
