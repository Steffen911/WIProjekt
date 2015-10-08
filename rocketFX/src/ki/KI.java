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
		
		for(int i=0; i<7; i++) {
			for(int j=0; j<6; j++){				
				spielfeld[i][j] = "_";
			}
		}
	}
	
	//Nimmt eine Spalte zwischen 0 und 6 entgegen
	//Gibt einen integer mit dem eigenen Spielzug zurueck
	public int zugBerechnen(int gegnerZug) {
		
		return -1;

	} //end of zug berechnen
	
	//MinMax Wikipedia Pseudocode
	public void hauptProgramm(){
		int gespeicherterZug = -1;
		int gewuenschteTiefe = 2;
		
		int bewertung = max(1, gewuenschteTiefe);
		if (gespeicherterZug == -1) {
			//Es gab keine weiteren Zuege mehr
		} else {
			//gespeicherterZug ausfuehren
		}
	}

/*
	public int max(int spieler, int tiefe){
		
		if(tiefe == -1 or keineZuegeMehr(spieler)){
			return bewerten();
		}
		int maxWert = -100;
		
		generiereMoeglicheZuege(spieler);
		while(nochZugDa){
			fuehreNaechstenZugAus();
			int wert = min(-spieler, tiefe-1);
			macheZugRueckgaengig();
			
			if(wert > maxWert){
				maxWert = wert;
				if(tiefe == gewuenschteTiefe){
					gespeicherterZug = zug;
				}
			}
		}
		
		return maxWert;
	}
*/	
	
	public int min(int spieler, int tiefe){
		if(tiefe == 0 or keineZuegeMehr(spieler)){
			
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
	
	//Prueft ob spieler in dieser runde gewinnen kann und setzt den siegstein
	//Prueft im anschluss ob gegner in dieser runde gewinnen kann und verhindert sieg
	public int musterErkennung() {
		int spielzug = -1;
		
		//Siegmuster erkennen
		for(int i=0; i<7;i++){
			for(int j=0; j<6; j++) {
				if(spielfeld[i][j] == "_"){
						
					//Pruefe ob Spieler gewinnen kann:
					//Pruefe waagerecht links -3 
					try{
						if(spielfeld[i-3][j] == eigenerStein && spielfeld[i-2][j] == eigenerStein && spielfeld[i-1][j] == eigenerStein){
							spielzug = i;
							eigenerPunkt.setLocation(spielzug, j);
							return spielzug;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
							
					//Pruefe waagerecht links -2 und rechts +1
					try{
						if(spielfeld[i-2][j] == eigenerStein && spielfeld[i-1][j] == eigenerStein && spielfeld[i+1][j] == eigenerStein){
							spielzug = i;
							eigenerPunkt.setLocation(spielzug, j);
							return spielzug;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
							
					//Pruefe waagerecht links -1 und rechts +2
					try{
						if(spielfeld[i-1][j] == eigenerStein && spielfeld[i+1][j] == eigenerStein && spielfeld[i+2][j] == eigenerStein){
							spielzug = i;
							eigenerPunkt.setLocation(spielzug, j);
							return spielzug;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
							
					//Pruefe waagerecht rechts +3
					try{
						if(spielfeld[i+1][j] == eigenerStein && spielfeld[i+2][j] == eigenerStein && spielfeld[i+3][j] == eigenerStein){
							spielzug = i;
							eigenerPunkt.setLocation(spielzug, j);
							return spielzug;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
							
					//Pruefe senkrecht
					try{
						if(spielfeld[i][j-1] == eigenerStein && spielfeld[i][j-2] == eigenerStein && spielfeld[i][j-3] == eigenerStein){
							spielzug = i;
							eigenerPunkt.setLocation(spielzug, j);
							return spielzug;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
							
					//Pruefe diagonal links runter -3 
					try{
						if(spielfeld[i-3][j-3] == eigenerStein && spielfeld[i-2][j-2] == eigenerStein && spielfeld[i-1][j-1] == eigenerStein){
							spielzug = i;
							eigenerPunkt.setLocation(spielzug, j);
							return spielzug;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
							
					//Pruefe diagonal links runter -2
					try{
						if(spielfeld[i-2][j-2] == eigenerStein && spielfeld[i-1][j-1] == eigenerStein && spielfeld[i+1][j+1] == eigenerStein){
							spielzug = i;
							eigenerPunkt.setLocation(spielzug, j);
							return spielzug;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
							
							//Pruefe diagonal links runter -1
							try{
								if(spielfeld[i-1][j-1] == eigenerStein && spielfeld[i+1][j+1] == eigenerStein && spielfeld[i+2][j+2] == eigenerStein){
									spielzug = i;
									eigenerPunkt.setLocation(spielzug, j);
									return spielzug;
								}
							}catch(ArrayIndexOutOfBoundsException e){}
							
							//Pruefe diagonal rechts hoch +3
							try{
								if(spielfeld[i+1][j+1] == eigenerStein && spielfeld[i+2][j+2] == eigenerStein && spielfeld[i+3][j+3] == eigenerStein){
									spielzug = i;
									eigenerPunkt.setLocation(spielzug, j);
									return spielzug;
								}
							}catch(ArrayIndexOutOfBoundsException e){}
							
							//Pruefe diagonal rechts runter -3 
							try{
								if(spielfeld[i+3][j-3] == eigenerStein && spielfeld[i+2][j-2] == eigenerStein && spielfeld[i+1][j-1] == eigenerStein){
									spielzug = i;
									eigenerPunkt.setLocation(spielzug, j);
									return spielzug;
								}
							}catch(ArrayIndexOutOfBoundsException e){}
							
							//Pruefe diagonal rechts runter -2
							try{
								if(spielfeld[i+2][j-2] == eigenerStein && spielfeld[i+1][j-1] == eigenerStein && spielfeld[i-1][j+1] == eigenerStein){
									spielzug = i;
									eigenerPunkt.setLocation(spielzug, j);
									return spielzug;
								}
							}catch(ArrayIndexOutOfBoundsException e){}
							
							//Pruefe diagonal rechts runter -1
							try{
								if(spielfeld[i+1][j-1] == eigenerStein && spielfeld[i-1][j+1] == eigenerStein && spielfeld[i-2][j+2] == eigenerStein){
									spielzug = i;
									eigenerPunkt.setLocation(spielzug, j);
									return spielzug;
								}
							}catch(ArrayIndexOutOfBoundsException e){}
							
							//Pruefe diagonal links hoch +3
							try{
								if(spielfeld[i-1][j+1] == eigenerStein && spielfeld[i-2][j+2] == eigenerStein && spielfeld[i-3][j+3] == eigenerStein){
									spielzug = i;
									eigenerPunkt.setLocation(spielzug, j);
									return spielzug;
								}
							}catch(ArrayIndexOutOfBoundsException e){}
							
							
							
							//Pruefe ob Gegner gewinnen kann:
							//Pruefe waagerecht links -3 
							try{
								if(spielfeld[i-3][j] == gegnerStein && spielfeld[i-2][j] == gegnerStein && spielfeld[i-1][j] == gegnerStein){
									spielzug = i;
									eigenerPunkt.setLocation(spielzug, j);
									return spielzug;
								}
							}catch(ArrayIndexOutOfBoundsException e){}
							
							//Pruefe waagerecht links -2 und rechts +1
							try{
								if(spielfeld[i-2][j] == gegnerStein && spielfeld[i-1][j] == gegnerStein && spielfeld[i+1][j] == gegnerStein){
									spielzug = i;
									eigenerPunkt.setLocation(spielzug, j);
									return spielzug;
								}
							}catch(ArrayIndexOutOfBoundsException e){}
							
							//Pruefe waagerecht links -1 und rechts +2
							try{
								if(spielfeld[i-1][j] == gegnerStein && spielfeld[i+1][j] == gegnerStein && spielfeld[i+2][j] == gegnerStein){
									spielzug = i;
									eigenerPunkt.setLocation(spielzug, j);
									return spielzug;
								}
							}catch(ArrayIndexOutOfBoundsException e){}
							
							//Pruefe waagerecht rechts +3
							try{
								if(spielfeld[i+1][j] == gegnerStein && spielfeld[i+2][j] == gegnerStein && spielfeld[i+3][j] == gegnerStein){
									spielzug = i;
									eigenerPunkt.setLocation(spielzug, j);
									return spielzug;
								}
							}catch(ArrayIndexOutOfBoundsException e){}
							
							//Pruefe senkrecht
							try{
								if(spielfeld[i][j-1] == gegnerStein && spielfeld[i][j-2] == gegnerStein && spielfeld[i][j-3] == gegnerStein){
									spielzug = i;
									eigenerPunkt.setLocation(spielzug, j);
									return spielzug;
								}
							}catch(ArrayIndexOutOfBoundsException e){}
							
							//Pruefe diagonal links runter -3 
							try{
								if(spielfeld[i-3][j-3] == gegnerStein && spielfeld[i-2][j-2] == gegnerStein && spielfeld[i-1][j-1] == gegnerStein){
									spielzug = i;
									eigenerPunkt.setLocation(spielzug, j);
									return spielzug;
								}
							}catch(ArrayIndexOutOfBoundsException e){}
							
							//Pruefe diagonal links runter -2
							try{
								if(spielfeld[i-2][j-2] == gegnerStein && spielfeld[i-1][j-1] == gegnerStein && spielfeld[i+1][j+1] == gegnerStein){
									spielzug = i;
									eigenerPunkt.setLocation(spielzug, j);
									return spielzug;
								}
							}catch(ArrayIndexOutOfBoundsException e){}
							
							//Pruefe diagonal links runter -1
							try{
								if(spielfeld[i-1][j-1] == gegnerStein && spielfeld[i+1][j+1] == gegnerStein && spielfeld[i+2][j+2] == gegnerStein){
									spielzug = i;
									eigenerPunkt.setLocation(spielzug, j);
									return spielzug;
								}
							}catch(ArrayIndexOutOfBoundsException e){}
							
							//Pruefe diagonal rechts hoch +3
							try{
								if(spielfeld[i+1][j+1] == gegnerStein && spielfeld[i+2][j+2] == gegnerStein && spielfeld[i+3][j+3] == gegnerStein){
									spielzug = i;
									eigenerPunkt.setLocation(spielzug, j);
									return spielzug;
								}
							}catch(ArrayIndexOutOfBoundsException e){}
							
							//Pruefe diagonal rechts runter -3 
							try{
								if(spielfeld[i+3][j-3] == gegnerStein && spielfeld[i+2][j-2] == gegnerStein && spielfeld[i+1][j-1] == gegnerStein){
									spielzug = i;
									eigenerPunkt.setLocation(spielzug, j);
									return spielzug;
								}
							}catch(ArrayIndexOutOfBoundsException e){}
							
							//Pruefe diagonal rechts runter -2
							try{
								if(spielfeld[i+2][j-2] == gegnerStein && spielfeld[i+1][j-1] == gegnerStein && spielfeld[i-1][j+1] == gegnerStein){
									spielzug = i;
									eigenerPunkt.setLocation(spielzug, j);
									return spielzug;
								}
							}catch(ArrayIndexOutOfBoundsException e){}
							
							//Pruefe diagonal rechts runter -1
							try{
								if(spielfeld[i+1][j-1] == gegnerStein && spielfeld[i-1][j+1] == gegnerStein && spielfeld[i-2][j+2] == gegnerStein){
									spielzug = i;
									eigenerPunkt.setLocation(spielzug, j);
									return spielzug;
								}
							}catch(ArrayIndexOutOfBoundsException e){}
							
							//Pruefe diagonal links hoch +3
							try{
								if(spielfeld[i-1][j+1] == gegnerStein && spielfeld[i-2][j+2] == gegnerStein && spielfeld[i-3][j+3] == gegnerStein){
									spielzug = i;
									eigenerPunkt.setLocation(spielzug, j);
									return spielzug;
								}
							}catch(ArrayIndexOutOfBoundsException e){}
						} //end if
						
					} //end for
				} //end for
		
		return spielzug;
	} //end of musterErkennung
	
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
