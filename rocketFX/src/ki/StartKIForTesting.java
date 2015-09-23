package ki;

public class StartKIForTesting {
	
	public static void main(String[] args) {
		KI ki = new KI("o");
		
		ki.zugBerechnen(-1);
		ki.zugBerechnen(4);
		ki.zugBerechnen(2);
		ki.zugBerechnen(3);
		
		//Array in Konsole ausgeben zum Test
		String[][] ausgabe = ki.arrayAusgabe();
	
		for(int i=5; i>=0; i--){	
			for(int j=0; j<7; j++){			
				System.out.print(ausgabe[j][i] + " ");
			}
			System.out.print("\n");
		}
		
	} //end of main
	
} //end of class
