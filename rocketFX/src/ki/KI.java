package ki;

public class KI {
	
	//Spielerwahl
	private String eigenerStein;
	private String gegnerStein;
	
	//Spielfeld anlegen
	private String[][] spielfeld = new String[7][6];
	
	public KI(String spielerwahl){
		eigenerStein = spielerwahl;
		
		switch (spielerwahl) {
		case "o":
			gegnerStein = "x";
			break;
		case"x":
			gegnerStein = "o";
			break;
		default:
			System.out.println("Ungültige Spielerwahl.");
			break;
		}
		
		for(int i=0; i<7; i++) {
			for(int j=0; j<6; j++){				
				spielfeld[i][j] = "_";
			}
		}
	}
	
	//Nimmt eine Spalte zwischen 0 und 6 entgegen
	public int zugBerechnen(int gegnerZug) {
		
		int spielzug;
		
		for(int i=0; i<6; i++) {
			if(spielfeld[gegnerZug][i] == "_"){
				spielfeld[gegnerZug][i] = gegnerStein;
				break;
			}
		}
		
		//Spalte ist voll Erkennung
		while(true){
			spielzug = (int)(Math.random() * 6);
			if (spielfeld[spielzug][5] == "_"){
				break;
			}
		}
		
		//TODO: Siegmuster erkennen
		
		for(int i=0; i<6; i++) {
			if(spielfeld[spielzug][i] == "_"){
				spielfeld[spielzug][i] = eigenerStein;
				break;
			}
		}
		
		return spielzug;
	}
	
	public String[][] arrayAusgabe(){
		return spielfeld;
	}

}
