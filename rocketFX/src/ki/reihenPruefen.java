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
					
					if(spielfeld[i][j].equals(spieler)){
						
					//Pruefe waagerecht
						//Pruefe links -3
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i-3][j].equals(spieler) && spielfeld[i-2][j].equals(spieler) && spielfeld[i-1][j].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
						//Pruefe links -2 rechts +1
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i-2][j].equals(spieler) && spielfeld[i-1][j].equals(spieler) && spielfeld[i+1][j].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
						//Pruefe links -1 rechts +2
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i-1][j].equals(spieler) && spielfeld[i+1][j].equals(spieler) && spielfeld[i+2][j].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
						//Pruefe rechts +3
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i+1][j].equals(spieler) && spielfeld[i+2][j].equals(spieler) && spielfeld[i+3][j].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
					//Pruefe senkrecht
						//Pruefe unten -3
						try{
							if(spielfeld[i][j-1].equals(spieler) && spielfeld[i][j-2].equals(spieler) && spielfeld[i][j-3].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
					//Pruefe diagonal vierer
						//Pruefe diagonal links runter -3
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i-3][j-3].equals(spieler) && spielfeld[i-2][j-2].equals(spieler) && spielfeld[i-1][j-1].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
						//Pruefe diagonal links runter -2
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i-2][j-2].equals(spieler) && spielfeld[i-1][j-1].equals(spieler) && spielfeld[i+1][j+1].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
						//Pruefe diagonal links runter -1
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i-1][j-1].equals(spieler) && spielfeld[i+1][j+1].equals(spieler) && spielfeld[i+2][j+2].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
						//Pruefe diagonal rechts hoch
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i+1][j+1].equals(spieler) && spielfeld[i+2][j+2].equals(spieler) && spielfeld[i+3][j+3].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
						//Pruefe diagonal rechts runter -3
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i+3][j-3].equals(spieler) && spielfeld[i+2][j-2].equals(spieler) && spielfeld[i+1][j-1].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
						//Pruefe diagonal rechts runter -2
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i+2][j-2].equals(spieler) && spielfeld[i+1][j-1].equals(spieler) && spielfeld[i-1][j+1].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
						//Pruefe diagonal rechts runter -1
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i+1][j-1].equals(spieler) && spielfeld[i-1][j+1].equals(spieler) && spielfeld[i-2][j+2].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
						//Pruefe diagonal rechts links hoch
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i-3][j+3].equals(spieler) && spielfeld[i-2][j+2].equals(spieler) && spielfeld[i-1][j+1].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
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
							
					if(spielfeld[i][j].equals(spieler)){
								
					//Pruefe waagerecht
						//Pruefe links -2
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i-2][j].equals(spieler) && spielfeld[i-1][j].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
								
						//Pruefe links -1 rechts +1
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i-1][j].equals(spieler) && spielfeld[i+1][j].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
								
						//Pruefe rechts +2
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i+1][j].equals(spieler) && spielfeld[i+2][j].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
								
					//Pruefe senkrecht
						//Pruefe unten -2
						try{
							if(spielfeld[i][j-1].equals(spieler) && spielfeld[i][j-2].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
								
					//Pruefe diagonal dreier
						
						//Pruefe diagonal links runter -2
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i-2][j-2].equals(spieler) && spielfeld[i-1][j-1].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
						//Pruefe diagonal links runter -1
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i-1][j-1].equals(spieler) && spielfeld[i+1][j+1].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
						//Pruefe diagonal rechts hoch
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i+1][j+1].equals(spieler) && spielfeld[i+2][j+2].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
						//Pruefe diagonal rechts runter -2
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i+2][j-2].equals(spieler) && spielfeld[i+1][j-1].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
						//Pruefe diagonal rechts runter -1
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i+1][j-1].equals(spieler) && spielfeld[i-1][j+1].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
						//Pruefe diagonal rechts links hoch
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i-2][j+2].equals(spieler) && spielfeld[i-1][j+1].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
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
									
					if(spielfeld[i][j].equals(spieler)){
										
					//Pruefe waagerecht
						//Pruefe links -1
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i-1][j].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
										
						//Pruefe rechts +1
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i+1][j].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
									
					//Pruefe senkrecht
						//Pruefe unten -1
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i][j-1].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
										
					//Pruefe diagonal zweier
						//Pruefe links unten
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i-1][j-1].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
						//Pruefe rechts unten
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i+1][j-1].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
						//Pruefe links oben
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i-1][j+1].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
						//Pruefe rechts oben
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i+1][j+1].equals(spieler)){
								return true;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
						
					}			
									
				}//end zeilen for
			}//end spalten for
							
			return false;
		} //end of zweierReihe
		
		//Prueft ob spieler in dieser runde gewinnen kann und setzt den siegstein
		//Prueft im anschluss ob gegner in dieser runde gewinnen kann und verhindert sieg
		public int siegMusterErkennung(String[][] spielfeld, String eigenerStein, String gegnerStein) {
			int spielzug = -1;
			
			//Siegmuster erkennen
			for(int i=0; i<7;i++){
				for(int j=0; j<6; j++) {
					if(spielfeld[i][j].equals("_")){
							
						//Pruefe ob Spieler gewinnen kann:
						//Pruefe waagerecht links -3 
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i-3][j].equals(eigenerStein) && spielfeld[i-2][j].equals(eigenerStein) && spielfeld[i-1][j].equals(eigenerStein)){
								spielzug = i;
								return spielzug;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
								
						//Pruefe waagerecht links -2 und rechts +1
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i-2][j].equals(eigenerStein) && spielfeld[i-1][j].equals(eigenerStein) && spielfeld[i+1][j].equals(eigenerStein)){
								spielzug = i;
								return spielzug;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
								
						//Pruefe waagerecht links -1 und rechts +2
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i-1][j].equals(eigenerStein) && spielfeld[i+1][j].equals(eigenerStein) && spielfeld[i+2][j].equals(eigenerStein)){
								spielzug = i;
								return spielzug;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
								
						//Pruefe waagerecht rechts +3
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i+1][j].equals(eigenerStein) && spielfeld[i+2][j].equals(eigenerStein) && spielfeld[i+3][j].equals(eigenerStein)){
								spielzug = i;
								return spielzug;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
								
						//Pruefe senkrecht
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i][j-1].equals(eigenerStein) && spielfeld[i][j-2].equals(eigenerStein) && spielfeld[i][j-3].equals(eigenerStein)){
								spielzug = i;
								return spielzug;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
								
						//Pruefe diagonal links runter -3 
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i-3][j-3].equals(eigenerStein) && spielfeld[i-2][j-2].equals(eigenerStein) && spielfeld[i-1][j-1].equals(eigenerStein)){
								spielzug = i;
								return spielzug;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
								
						//Pruefe diagonal links runter -2
						try{
							if(!spielfeld[i][j-1].equals("_") && spielfeld[i-2][j-2].equals(eigenerStein) && spielfeld[i-1][j-1].equals(eigenerStein) && spielfeld[i+1][j+1].equals(eigenerStein)){
								spielzug = i;
								return spielzug;
							}
						}catch(ArrayIndexOutOfBoundsException e){}
								
								//Pruefe diagonal links runter -1
								try{
									if(!spielfeld[i][j-1].equals("_") && spielfeld[i-1][j-1].equals(eigenerStein) && spielfeld[i+1][j+1].equals(eigenerStein) && spielfeld[i+2][j+2].equals(eigenerStein)){
										spielzug = i;
										return spielzug;
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								
								//Pruefe diagonal rechts hoch +3
								try{
									if(!spielfeld[i][j-1].equals("_") && spielfeld[i+1][j+1].equals(eigenerStein) && spielfeld[i+2][j+2].equals(eigenerStein) && spielfeld[i+3][j+3].equals(eigenerStein)){
										spielzug = i;
										return spielzug;
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								
								//Pruefe diagonal rechts runter -3 
								try{
									if(!spielfeld[i][j-1].equals("_") && spielfeld[i+3][j-3].equals(eigenerStein) && spielfeld[i+2][j-2].equals(eigenerStein) && spielfeld[i+1][j-1].equals(eigenerStein)){
										spielzug = i;
										return spielzug;
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								
								//Pruefe diagonal rechts runter -2
								try{
									if(!spielfeld[i][j-1].equals("_") && spielfeld[i+2][j-2].equals(eigenerStein) && spielfeld[i+1][j-1].equals(eigenerStein) && spielfeld[i-1][j+1].equals(eigenerStein)){
										spielzug = i;
										return spielzug;
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								
								//Pruefe diagonal rechts runter -1
								try{
									if(!spielfeld[i][j-1].equals("_") && spielfeld[i+1][j-1].equals(eigenerStein) && spielfeld[i-1][j+1].equals(eigenerStein) && spielfeld[i-2][j+2].equals(eigenerStein)){
										spielzug = i;
										return spielzug;
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								
								//Pruefe diagonal links hoch +3
								try{
									if(!spielfeld[i][j-1].equals("_") && spielfeld[i-1][j+1].equals(eigenerStein) && spielfeld[i-2][j+2].equals(eigenerStein) && spielfeld[i-3][j+3].equals(eigenerStein)){
										spielzug = i;
										return spielzug;
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								
								
								
								//Pruefe ob Gegner gewinnen kann:
								//Pruefe waagerecht links -3 
								try{
									if(!spielfeld[i][j-1].equals("_") && spielfeld[i-3][j].equals(eigenerStein) && spielfeld[i-2][j].equals(eigenerStein) && spielfeld[i-1][j].equals(eigenerStein)){
										spielzug = i;
										return spielzug;
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								
								//Pruefe waagerecht links -2 und rechts +1
								try{
									if(!spielfeld[i][j-1].equals("_") && spielfeld[i-2][j].equals(eigenerStein) && spielfeld[i-1][j].equals(eigenerStein) && spielfeld[i+1][j].equals(eigenerStein)){
										spielzug = i;
										return spielzug;
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								
								//Pruefe waagerecht links -1 und rechts +2
								try{
									if(!spielfeld[i][j-1].equals("_") && spielfeld[i-1][j].equals(eigenerStein) && spielfeld[i+1][j].equals(eigenerStein) && spielfeld[i+2][j].equals(eigenerStein)){
										spielzug = i;
										return spielzug;
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								
								//Pruefe waagerecht rechts +3
								try{
									if(!spielfeld[i][j-1].equals("_") && spielfeld[i+1][j].equals(eigenerStein) && spielfeld[i+2][j].equals(eigenerStein) && spielfeld[i+3][j].equals(eigenerStein)){
										spielzug = i;
										return spielzug;
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								
								//Pruefe senkrecht
								try{
									if(!spielfeld[i][j-1].equals("_") && spielfeld[i][j-1].equals(eigenerStein) && spielfeld[i][j-2].equals(eigenerStein) && spielfeld[i][j-3].equals(eigenerStein)){
										spielzug = i;
										return spielzug;
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								
								//Pruefe diagonal links runter -3 
								try{
									if(!spielfeld[i][j-1].equals("_") && spielfeld[i-3][j-3].equals(eigenerStein) && spielfeld[i-2][j-2].equals(eigenerStein) && spielfeld[i-1][j-1].equals(eigenerStein)){
										spielzug = i;
										return spielzug;
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								
								//Pruefe diagonal links runter -2
								try{
									if(!spielfeld[i][j-1].equals("_") && spielfeld[i-2][j-2].equals(eigenerStein) && spielfeld[i-1][j-1].equals(eigenerStein) && spielfeld[i+1][j+1].equals(eigenerStein)){
										spielzug = i;
										return spielzug;
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								
								//Pruefe diagonal links runter -1
								try{
									if(!spielfeld[i][j-1].equals("_") && spielfeld[i-1][j-1].equals(eigenerStein) && spielfeld[i+1][j+1].equals(eigenerStein) && spielfeld[i+2][j+2].equals(eigenerStein)){
										spielzug = i;
										return spielzug;
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								
								//Pruefe diagonal rechts hoch +3
								try{
									if(!spielfeld[i][j-1].equals("_") && spielfeld[i+1][j+1].equals(eigenerStein) && spielfeld[i+2][j+2].equals(eigenerStein) && spielfeld[i+3][j+3].equals(eigenerStein)){
										spielzug = i;
										return spielzug;
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								
								//Pruefe diagonal rechts runter -3 
								try{
									if(!spielfeld[i][j-1].equals("_") && spielfeld[i+3][j-3].equals(eigenerStein) && spielfeld[i+2][j-2].equals(eigenerStein) && spielfeld[i+1][j-1].equals(eigenerStein)){
										spielzug = i;
										return spielzug;
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								
								//Pruefe diagonal rechts runter -2
								try{
									if(!spielfeld[i][j-1].equals("_") && spielfeld[i+2][j-2].equals(eigenerStein) && spielfeld[i+1][j-1].equals(eigenerStein) && spielfeld[i-1][j+1].equals(eigenerStein)){
										spielzug = i;
										return spielzug;
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								
								//Pruefe diagonal rechts runter -1
								try{
									if(!spielfeld[i][j-1].equals("_") && spielfeld[i+1][j-1].equals(eigenerStein) && spielfeld[i-1][j+1].equals(eigenerStein) && spielfeld[i-2][j+2].equals(eigenerStein)){
										spielzug = i;
										return spielzug;
									}
								}catch(ArrayIndexOutOfBoundsException e){}
								
								//Pruefe diagonal links hoch +3
								try{
									if(!spielfeld[i][j-1].equals("_") && spielfeld[i-1][j+1].equals(eigenerStein) && spielfeld[i-2][j+2].equals(eigenerStein) && spielfeld[i-3][j+3].equals(eigenerStein)){
										spielzug = i;
										return spielzug;
									}
								}catch(ArrayIndexOutOfBoundsException e){}
							} //end if
							
						} //end for
					} //end for
			
			return spielzug;
		} //end of musterErkennung
}
