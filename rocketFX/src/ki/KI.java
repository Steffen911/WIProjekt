package ki;

public class KI {
	
	//Spielerwahl
	private String eigenerStein;
	private String gegnerStein;
	
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
		
		int spielzug = -1;
		
		//Falls eigener Agent startet wird -1 uebergeben
		if(gegnerZug >= 0){
			
			for(int i=0; i<6; i++) {
				if(spielfeld[gegnerZug][i] == "_"){
					spielfeld[gegnerZug][i] = gegnerStein;
					break;
				} //end if
			} //end for
			
		} 
		
		//Siegmuster erkennen
		for(int i=0; i<7;i++){
			for(int j=0; j<6; j++) {
				if(spielfeld[i][j] == "_"){
					
					//Pruefe ob Spieler gewinnen kann:
					//Pruefe waagerecht links -3 
					try{
						if(spielfeld[i-3][j] == eigenerStein && spielfeld[i-2][j] == eigenerStein && spielfeld[i-1][j] == eigenerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe waagerecht links -2 und rechts +1
					try{
						if(spielfeld[i-2][j] == eigenerStein && spielfeld[i-1][j] == eigenerStein && spielfeld[i+1][j] == eigenerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe waagerecht links -1 und rechts +2
					try{
						if(spielfeld[i-1][j] == eigenerStein && spielfeld[i+1][j] == eigenerStein && spielfeld[i+2][j] == eigenerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe waagerecht rechts +3
					try{
						if(spielfeld[i+1][j] == eigenerStein && spielfeld[i+2][j] == eigenerStein && spielfeld[i+3][j] == eigenerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe senkrecht
					try{
						if(spielfeld[i][j-1] == eigenerStein && spielfeld[i][j-2] == eigenerStein && spielfeld[i][j-3] == eigenerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe diagonal links runter -3 
					try{
						if(spielfeld[i-3][j-3] == eigenerStein && spielfeld[i-2][j-2] == eigenerStein && spielfeld[i-1][j-1] == eigenerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe diagonal links runter -2
					try{
						if(spielfeld[i-2][j-2] == eigenerStein && spielfeld[i-1][j-1] == eigenerStein && spielfeld[i+1][j+1] == eigenerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe diagonal links runter -1
					try{
						if(spielfeld[i-1][j-1] == eigenerStein && spielfeld[i+1][j+1] == eigenerStein && spielfeld[i+2][j+2] == eigenerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe diagonal rechts hoch +3
					try{
						if(spielfeld[i+1][j+1] == eigenerStein && spielfeld[i+2][j+2] == eigenerStein && spielfeld[i+3][j+3] == eigenerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe diagonal rechts runter -3 
					try{
						if(spielfeld[i+3][j-3] == eigenerStein && spielfeld[i+2][j-2] == eigenerStein && spielfeld[i+1][j-1] == eigenerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe diagonal rechts runter -2
					try{
						if(spielfeld[i+2][j-2] == eigenerStein && spielfeld[i+1][j-1] == eigenerStein && spielfeld[i-1][j+1] == eigenerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe diagonal rechts runter -1
					try{
						if(spielfeld[i+1][j-1] == eigenerStein && spielfeld[i-1][j+1] == eigenerStein && spielfeld[i-2][j+2] == eigenerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe diagonal links hoch +3
					try{
						if(spielfeld[i-1][j+1] == eigenerStein && spielfeld[i-2][j+2] == eigenerStein && spielfeld[i-3][j+3] == eigenerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					
					
					//Pruefe ob Gegner gewinnen kann:
					//Pruefe waagerecht links -3 
					try{
						if(spielfeld[i-3][j] == gegnerStein && spielfeld[i-2][j] == gegnerStein && spielfeld[i-1][j] == gegnerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe waagerecht links -2 und rechts +1
					try{
						if(spielfeld[i-2][j] == gegnerStein && spielfeld[i-1][j] == gegnerStein && spielfeld[i+1][j] == gegnerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe waagerecht links -1 und rechts +2
					try{
						if(spielfeld[i-1][j] == gegnerStein && spielfeld[i+1][j] == gegnerStein && spielfeld[i+2][j] == gegnerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe waagerecht rechts +3
					try{
						if(spielfeld[i+1][j] == gegnerStein && spielfeld[i+2][j] == gegnerStein && spielfeld[i+3][j] == gegnerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe senkrecht
					try{
						if(spielfeld[i][j-1] == gegnerStein && spielfeld[i][j-2] == gegnerStein && spielfeld[i][j-3] == gegnerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe diagonal links runter -3 
					try{
						if(spielfeld[i-3][j-3] == gegnerStein && spielfeld[i-2][j-2] == gegnerStein && spielfeld[i-1][j-1] == gegnerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe diagonal links runter -2
					try{
						if(spielfeld[i-2][j-2] == gegnerStein && spielfeld[i-1][j-1] == gegnerStein && spielfeld[i+1][j+1] == gegnerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe diagonal links runter -1
					try{
						if(spielfeld[i-1][j-1] == gegnerStein && spielfeld[i+1][j+1] == gegnerStein && spielfeld[i+2][j+2] == gegnerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe diagonal rechts hoch +3
					try{
						if(spielfeld[i+1][j+1] == gegnerStein && spielfeld[i+2][j+2] == gegnerStein && spielfeld[i+3][j+3] == gegnerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe diagonal rechts runter -3 
					try{
						if(spielfeld[i+3][j-3] == gegnerStein && spielfeld[i+2][j-2] == gegnerStein && spielfeld[i+1][j-1] == gegnerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe diagonal rechts runter -2
					try{
						if(spielfeld[i+2][j-2] == gegnerStein && spielfeld[i+1][j-1] == gegnerStein && spielfeld[i-1][j+1] == gegnerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe diagonal rechts runter -1
					try{
						if(spielfeld[i+1][j-1] == gegnerStein && spielfeld[i-1][j+1] == gegnerStein && spielfeld[i-2][j+2] == gegnerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//Pruefe diagonal links hoch +3
					try{
						if(spielfeld[i-1][j+1] == gegnerStein && spielfeld[i-2][j+2] == gegnerStein && spielfeld[i-3][j+3] == gegnerStein){
							spielzug = i;
							break;
						}
					}catch(ArrayIndexOutOfBoundsException e){}
				} //end if
				
			} //end for
		}
		
		//Spalte ist voll Erkennung
		while(true){
			if(spielzug == -1){
				spielzug = (int)(Math.random() * 7);				
			}
			if (spielfeld[spielzug][5] == "_"){
				break;
			}
		}
		
		
		for(int i=0; i<6; i++) {
			if(spielfeld[spielzug][i] == "_"){
				spielfeld[spielzug][i] = eigenerStein;
				break;
			}
		}
		
		return spielzug;
	}
	
	//Gibt das aktuelle Spielfeld aus
	public String[][] arrayAusgabe(){
		return spielfeld;
	}

}
