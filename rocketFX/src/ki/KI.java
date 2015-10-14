package ki;

import java.awt.*;

public class KI {
	
	//Spielerwahl
	private String eigenerStein;
	private String gegnerStein;
	
	//Gibt positionen der steine wieder
	private Point eigenerPunkt = new Point(-1,-1);
	private Point gegnerPunkt = new Point(-1,-1);
	
	//Spielfeld anlegen
	private String[][] spielfeld = new String[7][6];
	
	//Geplanten Spielzug hinterlegen
	private int gespeicherterZug;
	
	//Tiefe fuer rekursion
	private int gewuenschteTiefe;
	
	//Array fuer moegliche Zuege
	//Index fuer Spalte, Wert fuer Zeile, -1 falls Zeile voll ist
	private int[] moeglicheZuege;
	
	//Konstruktor fuer KI, legt Spielsteine fest, inititalisiert den spielfeldarray
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
			System.out.println("Ungueltige Spielerwahl.");
			break;
		}
		
		for(int i=0; i<7; i++) { //initialisiert spielfeld
			for(int j=0; j<6; j++){				
				spielfeld[i][j] = "_";
			}
			moeglicheZuege[i] = -1; //initialisiert die moeglichen zuege
		}
		
	}
	
	//Nimmt eine Spalte zwischen 0 und 6 entgegen
	//Gibt einen integer mit dem eigenen Spielzug zurueck
	public int zugBerechnen(int gegnerZug) {
		
		setzeGegnerStein(gegnerZug);
		
		if (gegnerZug == -1){
			setzeEigenenStein(3);
			return 3;
		}
		
		hauptProgramm(2);
		
		setzeEigenenStein(gespeicherterZug);
		
		return gespeicherterZug;

	} //end of zug berechnen
	
	//MinMax Wikipedia Pseudocode
	public void hauptProgramm(int gewuenschteTiefe){
		gespeicherterZug = -1;
		this.gewuenschteTiefe = gewuenschteTiefe; // TODO: Steffen: zuweisung entfernen, sobald methode wirklich aufgerufen wird
		
		max(eigenerStein, gewuenschteTiefe);
		if (gespeicherterZug == -1) {
			//Es gab keine weiteren Zuege mehr
			System.out.println("Das Spiel ist beendet.");
		} else {
			//gespeicherterZug ausfuehren
			System.out.println("Der Stein wird in Spalte " + gespeicherterZug + " geworfen.");
			setzeEigenenStein(gespeicherterZug);
		}
	}

	public int max(String spieler, int tiefe){
		
		if(tiefe == 0 || keineZuegeMehr() || getWinner()!=null){ 
			return bewerten(spieler);
		}
		int maxWert = -100;
		
		//generiereMoeglicheZuege();
		for(int i=0; i<7; i++){
			if(moeglicheZuege[i] != -1){
				fuehreNaechstenZugAus(moeglicheZuege[i], spieler);
				int wert = min(gegnerStein, tiefe-1); //wert hat einen wert von 1 bis 4. 4 ist ideal
				macheZugRueckgaengig(moeglicheZuege[i], spieler);
			
				if(wert > maxWert){
					maxWert = wert;
					if(tiefe == gewuenschteTiefe){
						gespeicherterZug = moeglicheZuege[i];
					}
				}	
			}
		}
		
		return maxWert;
	}

	public int min(String spieler, int tiefe){
		
		if(tiefe == 0 || keineZuegeMehr() || getWinner()!=null){
			return bewerten(spieler);
		}
		int minWert = +100;
		
		//generiereMoeglicheZuege();
		for(int i=0; i<7; i++){
			if(moeglicheZuege[i] != -1){
				fuehreNaechstenZugAus(moeglicheZuege[i], spieler);
				int wert = max(eigenerStein, tiefe-1); //wert hat einen wert von 1 bis 4. 4 ist ideal
				macheZugRueckgaengig(moeglicheZuege[i], spieler);
				if(wert < minWert){
					minWert = wert;
				}
			}
		}
		return minWert;
	}
	
	//bewerte die aktuelle Situation des spielers
	//4 entspricht 4 in einer reihe
	//3 entspricht 3 in einer reihe
	//2 entspricht 2 in einer reihe
	public int bewerten(String spieler){
		reihenPruefen pruefen = new reihenPruefen();
		
		if (pruefen.viererReihe(spielfeld, spieler)){
			return 4;
		}
		
		if (pruefen.dreierReihe(spielfeld, spieler)){
			return 3;
		}
		
		if (pruefen.zweierReihe(spielfeld, spieler)){
			return 2;
		}
		
		return 1;
	}
	
	//prueft ob noch ein spielzug moeglich ist
	public boolean keineZuegeMehr(){
		boolean nochEinZugMoeglich = false;
		
		for(int i=0; i<7; i++){
			for(int j=0; j<6; j++){
				if(spielfeld[i][j]=="_"){
					moeglicheZuege[i] = j;
					nochEinZugMoeglich = true;
					break;
				}
			}
		}
			
		return nochEinZugMoeglich;
	}
	
	//Setzt den uebergebenen Stein ins Spielfeld
	public void fuehreNaechstenZugAus(int spielzug, String spieler){
		
		if(spielfeld[spielzug][moeglicheZuege[spielzug]] == "_"){
			spielfeld[spielzug][moeglicheZuege[spielzug]] = spieler;
		}
		
	}
	
	//Nimmt den uebergebenen Stein aus dem Spielfeld
	public void macheZugRueckgaengig(int spielzug, String spieler){
		
		if(spielfeld[spielzug][moeglicheZuege[spielzug]] == spieler){
			spielfeld[spielzug][moeglicheZuege[spielzug]] = "_";
		}
			
	}
	
	//Setze den eigenen Stein ins spielfeld
	public void setzeEigenenStein(int spielzug){		
		//Setze eigenen Stein
		for(int i=0; i<6; i++) {
			if(spielfeld[spielzug][i] == "_"){
				spielfeld[spielzug][i] = eigenerStein;
				eigenerPunkt.setLocation(spielzug, i);
				break;
			}
		}	
	}
	
	//Setze den gegnerischen Stein ins spielfeld
	public void setzeGegnerStein(int gegnerZug){	
		//Falls eigener Agent startet wird -1 uebergeben
		if(gegnerZug >= 0){				
			for(int i=0; i<6; i++) {
				if(spielfeld[gegnerZug][i] == "_"){
					spielfeld[gegnerZug][i] = gegnerStein;
					gegnerPunkt.setLocation(gegnerZug, i);
					break;
				} //end if
			} //end for				
		} 		
	}
	
	//Gibt an ob und wer gewonnen hat
	//null falls noch niemand gewonnen hat
	public String getWinner(){
		reihenPruefen pruefen = new reihenPruefen();
		
		if(pruefen.viererReihe(spielfeld, eigenerStein)){
			return eigenerStein;
		}
		if(pruefen.viererReihe(spielfeld, gegnerStein)){
			return gegnerStein;
		}
		
		return null;
	
	}
	
	//Gibt das aktuelle Spielfeld aus
	public String[][] arrayAusgabe(){
		return spielfeld;
	}

	public Point getEigenerPunkt() {
		return eigenerPunkt;
	}

	public Point getGegnerPunkt() {
		return gegnerPunkt;
	}

}
