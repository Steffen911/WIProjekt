package ki;

public class reihenPruefen {
		
		//Prueft ob im aktuellen Spielfeld vier Steine einer Sorte in einer Reihe liegen
		//Falls kein Parameter uebergeben wird werden beide Arten geprueft
		public boolean viererReihe(String[][] spielfeld, String spieler){
			
			//Wenn einer der beiden spieler eine viererreihe hat wird true zurueckgegeben
			if(spieler==null){
				if(viererReihe(spielfeld, "o") || viererReihe(spielfeld, "x")){
					return true;
				}
			}
			
			//Laufe spalten entlang
			for(int i=0; i<7; i++){
				//Laufe zeilen hoch
				for(int j=0; j<6; j++){
					
					if(spielfeld[i][j]==spieler){
						
					//Pruefe waagerecht
						//Pruefe links -3
						try{
							if(spielfeld[i-3][j] == spieler && spielfeld[i-2][j] == spieler && spielfeld[i-1][j] == spieler){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
						//Pruefe links -2 rechts +1
						try{
							if(spielfeld[i-2][j] == spieler && spielfeld[i-1][j] == spieler && spielfeld[i+1][j] == spieler){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
						//Pruefe links -1 rechts +2
						try{
							if(spielfeld[i-1][j] == spieler && spielfeld[i+1][j] == spieler && spielfeld[i+2][j] == spieler){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
						//Pruefe rechts +3
						try{
							if(spielfeld[i+1][j] == spieler && spielfeld[i+2][j] == spieler && spielfeld[i+3][j] == spieler){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
					//Pruefe senkrecht
						//Pruefe unten -3
						try{
							if(spielfeld[i][j-1] == spieler && spielfeld[i][j-2] == spieler && spielfeld[i][j-3] == spieler){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
					// TODO: Steffen: Pruefe diagonal vierer
					}			
					
				}//end zeilen for
			}//end spalten for
			
			return false;
		} //end of viererReihe
		
		//Prueft ob im aktuellen Spielfeld drei Steine einer Sorte in einer Reihe liegen
		//Falls kein Parameter uebergeben wird werden beide Arten geprueft
		public boolean dreierReihe(String[][] spielfeld, String spieler){
					
			//Wenn einer der beiden spieler eine dreierreihe hat wird true zurueckgegeben
			if(spieler==null){
				if(dreierReihe(spielfeld, "o") || dreierReihe(spielfeld, "x")){
					return true;
				}
			}
					
			//Laufe spalten entlang
			for(int i=0; i<7; i++){
				//Laufe zeilen hoch
				for(int j=0; j<6; j++){
							
					if(spielfeld[i][j]==spieler){
								
					//Pruefe waagerecht
						//Pruefe links -2
						try{
							if(spielfeld[i-2][j] == spieler && spielfeld[i-1][j] == spieler){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
								
						//Pruefe links -1 rechts +1
						try{
							if(spielfeld[i-1][j] == spieler && spielfeld[i+1][j] == spieler){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
								
						//Pruefe rechts +2
						try{
							if(spielfeld[i+1][j] == spieler && spielfeld[i+2][j] == spieler){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
								
					//Pruefe senkrecht
						//Pruefe unten -2
						try{
							if(spielfeld[i][j-1] == spieler && spielfeld[i][j-2] == spieler){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
								
					// TODO: Steffen: Pruefe diagonal dreier
					}			
							
				}//end zeilen for
			}//end spalten for
					
			return false;
		} //end of dreierReihe
		
		//Prueft ob im aktuellen Spielfeld zwei Steine einer Sorte in einer Reihe liegen
		//Falls kein Parameter uebergeben wird werden beide Arten geprueft
		public boolean zweierReihe(String[][] spielfeld, String spieler){
							
			//Wenn einer der beiden spieler eine dreierreihe hat wird true zurueckgegeben
			if(spieler==null){
				if(zweierReihe(spielfeld, "o") || zweierReihe(spielfeld, "x")){
					return true;
				}
			}
							
			//Laufe spalten entlang
			for(int i=0; i<7; i++){
				//Laufe zeilen hoch
				for(int j=0; j<6; j++){
									
					if(spielfeld[i][j]==spieler){
										
					//Pruefe waagerecht
						//Pruefe links -1
						try{
							if(spielfeld[i-1][j] == spieler){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
										
						//Pruefe rechts +1
						try{
							if(spielfeld[i+1][j] == spieler){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
									
					//Pruefe senkrecht
						//Pruefe unten -1
						try{
							if(spielfeld[i][j-1] == spieler){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
										
					// TODO: Steffen: Pruefe diagonal zweier
					}			
									
				}//end zeilen for
			}//end spalten for
							
			return false;
		} //end of zweierReihe
}
