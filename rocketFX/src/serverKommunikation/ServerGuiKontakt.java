package serverKommunikation;

public class ServerGuiKontakt {
	private boolean fileSchnittstelle;
	private FileSchnittstelle fileS;
	private PushSchnittstelle pushS;
	private String spielerwahl;
	
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
		pushS = new PushSchnittstelle(apiKey, apiSecret, centisekunden); //, spielerwahl); spielerwahl für Pusher nicht interessant
		pushS.connect();
	}
	
	public String[] leseVomServer(){
		if (fileSchnittstelle){
			return fileS.reader();
		}else{
			return pushS.reader();
		}
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

}
