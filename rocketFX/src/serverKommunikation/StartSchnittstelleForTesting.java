package serverKommunikation;

public class StartSchnittstelleForTesting {

	public static void main(String[] args) throws InterruptedException {

/*
		//FileSchnittstelle für MacOS
		FileSchnittstelle sst = new FileSchnittstelle("x", "//Users//Steffen//Dropbox//");
		
		//Sendet eine 4 als Spielzug und erhält die Antwort vom Server im StringArray serverAntwort
		String[] serverAntwort = sst.communicate(4);
		
		//Gibt Testweise das Resultat der ServerAnfrage aus
		System.out.println("serverAntwort ist gleich: ");
		for(int i = 0; i < serverAntwort.length; i++){
			System.out.println(serverAntwort[i]);
		}
*/		
// /*
		//PushSchnittstelle mit Test-Pusherdaten
		//Using steffen's apiKey and apiSecret
		PushSchnittstelle sst = new PushSchnittstelle("c216d52b4b4db2df78b2", "54f848263f22144e49f4");
		
		//Sendet eine 4 als Spielzug und erhält die Antwort vom Server im StringArray serverAntwort
		String[] serverAntwort = sst.communicate(4);
		
		//Gibt Testweise das Resultat der ServerAnfrage aus
		System.out.println("serverAntwort ist gleich: ");
		for(int i = 0; i < serverAntwort.length; i++){
			System.out.println(serverAntwort[i]);
		}
// */
	
	}

}
