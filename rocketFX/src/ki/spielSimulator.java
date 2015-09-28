package ki;

import serverKommunikation.*;

public class spielSimulator {
	
	private String spielerwahl = "o";

	public static void main(String[] args) {
		
		KI ki = new KI("spielerwahl");
		
		//Fileschnittstelle (Einkommentieren für File)
		FileSchnittstelle sst = new FileSchnittstelle("spielerwahl", "//Users//Steffen//Dropbox//", 50);
		
		//Pushschnittstelle (Einkommentieren für Push)
//		PushSchnittstelle sst = new PushSchnittstelle"c216d52b4b4db2df78b2", "54f848263f22144e49f4", 50);
		
		String[] rueckgabe = new String[4];
		
		while(rueckgabe[1] != "beendet"){
			
		}
	}

}
