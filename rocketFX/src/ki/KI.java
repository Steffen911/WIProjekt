package ki;

import java.awt.*;

public class KI {
	
	//Starter
	private String hatAngefangen;
	
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
	private int[] moeglicheZuege = new int[7];
	
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
		
		hatAngefangen = gegnerStein;
		
		for(int i=0; i<7; i++) { //initialisiert spielfeld
			for(int j=0; j<6; j++){				
				spielfeld[i][j] = "_";
			}
			moeglicheZuege[i] = -1; //initialisiert die moeglichen zuege
		}
		
	}
	
	//Nimmt eine Spalte zwischen 0 und 6 entgegen
	//Gibt einen integer mit dem eigenen Spielzug zurueck
	public int zugBerechnen(int gegnerZug){
		int spielzug;
		
		setzeGegnerStein(gegnerZug);
		
		if(gegnerZug == -1){
			hatAngefangen = eigenerStein;
			setzeEigenenStein(3);
			return 3;
		}
		
		keineZuegeMehr();
		
		int spalte = (int)(Math.random()*6);
		for(int i = 0; i < moeglicheZuege.length; i++){
			spalte = (i+spalte%6);
			if(moeglicheZuege[spalte] < 6){
				setzeEigenenStein(moeglicheZuege[spalte]);
				return moeglicheZuege[spalte];
			}
		}
		
		return -1; //Kein Spielzug mehr moeglich
	}
	
	//Nimmt eine Spalte zwischen 0 und 6 entgegen
	//Gibt einen integer mit dem eigenen Spielzug zurueck
	public int gutenZugBerechnen(int gegnerZug) {
		reihenPruefen pruefen = new reihenPruefen();
		
		setzeGegnerStein(gegnerZug);
		
		if (gegnerZug == -1){
			hatAngefangen = eigenerStein;
			setzeEigenenStein(3);
			return 3;
		}
		
		gespeicherterZug = pruefen.siegMusterErkennung(spielfeld, eigenerStein, gegnerStein);
		
		if(gespeicherterZug != -1){
			setzeEigenenStein(gespeicherterZug);
			return gespeicherterZug;
		}
		
		hauptProgramm(6);
		
		return gespeicherterZug;

	} //end of zug berechnen
	
	//MinMax Wikipedia Pseudocode
	public void hauptProgramm(int gewuenschteTiefe){
		gespeicherterZug = -1;
		this.gewuenschteTiefe = gewuenschteTiefe;
		
		max(eigenerStein, gewuenschteTiefe);
		if (gespeicherterZug == -1) {
			//Es gab keine weiteren Zuege mehr
			System.out.println("Das Spiel ist beendet.");
		} else {
			//gespeicherterZug ausfuehren
			if(spielfeld[gespeicherterZug][5] == "o" || spielfeld[gespeicherterZug][5] == "x"){
				System.out.println("Spalte ist eigentlich voll..."); //TODO: Steffen: Verhindern, dass dies ueberhaupt passiert
				hauptProgramm(2);
				return;
			}
			System.out.println("Der Stein wird in Spalte " + gespeicherterZug + " geworfen.");
			setzeEigenenStein(gespeicherterZug);
		}
	}

	public int max(String spieler, int tiefe){
		
		if(tiefe == 0 || keineZuegeMehr() || getWinner()!=null){ 
			return bewerten(spieler);
		}
		int maxWert = -100;
		
		int startSpalte = (int)(Math.random()*6);
		
		//generiereMoeglicheZuege();
		for(int i=0; i<7; i++){
			int spalte = (i+startSpalte)%6;
			if(moeglicheZuege[spalte] != -1){
				int zeile = moeglicheZuege[spalte];
				fuehreNaechstenZugAus(spalte, zeile, spieler);
				int wert = min(gegnerStein, tiefe-1); //wert hat einen wert von 1 bis 4. 4 ist ideal
				macheZugRueckgaengig(spalte, zeile, spieler);
			
				if(wert > maxWert){
					maxWert = wert;
					if(tiefe == gewuenschteTiefe){
						gespeicherterZug = spalte;
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
				int spalte = i;
				int zeile = moeglicheZuege[i];
				fuehreNaechstenZugAus(spalte, zeile, spieler);
				int wert = max(eigenerStein, tiefe-1); //wert hat einen wert von 1 bis 4. 4 ist ideal
				macheZugRueckgaengig(spalte, zeile, spieler);
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
		String gegner;
		
		if(spieler == "o"){
			gegner = "x";
		}else{
			gegner = "o";
		}
		
		if (pruefen.viererReihe(spielfeld, spieler)){
			return 4;
		}
		
		if (pruefen.viererReihe(spielfeld, gegner)){
			return -4;
		}
		
		if (pruefen.dreierReihe(spielfeld, spieler)){
			return 3;
		}
		
		if (pruefen.dreierReihe(spielfeld, gegner)){
			return -3;
		}
		
		if (pruefen.zweierReihe(spielfeld, spieler)){
			return 2;
		}
			
		if (pruefen.zweierReihe(spielfeld, gegner)){
			return -2;
		}
		
		return 0;
	}
	
	//prueft ob noch ein spielzug moeglich ist
	public boolean keineZuegeMehr(){
		boolean keinZugMoeglich = true;
		
		for(int i=0; i<7; i++){
			for(int j=0; j<6; j++){
				if(spielfeld[i][j]=="_"){
					moeglicheZuege[i] = j;
					keinZugMoeglich = false;
					break;
				}
			}
		}
			
		return keinZugMoeglich;
	}
	
	//Setzt den uebergebenen Stein ins Spielfeld
	public void fuehreNaechstenZugAus(int spalte, int zeile, String spieler){
		
		if(spielfeld[spalte][zeile] == "_"){
			spielfeld[spalte][zeile] = spieler;
		}
		
	}
	
	//Nimmt den uebergebenen Stein aus dem Spielfeld
	public void macheZugRueckgaengig(int spalte, int zeile, String spieler){
		
		if(spielfeld[spalte][zeile] == spieler){
			spielfeld[spalte][zeile] = "_";
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
	
	//Gibt an wer angefangen hat
	public String getStarter(){
		return hatAngefangen;
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

	public String getEigenerStein() {
		return eigenerStein;
	}

	public String getGegnerStein() {
		return gegnerStein;
	}

}
