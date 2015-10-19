package serverKommunikation;

import java.io.*;

public class MenschSchnittstelle {

	public MenschSchnittstelle(){
		
	}
	
	public int reader(){
		int spielzug = -1;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("In welche Spalte soll geworfen werden?");
		System.out.print("Eingabe: ");
		try {
			spielzug = Integer.parseInt(br.readLine());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(spielzug <0 || spielzug>6){
			System.out.println("Ungültige Eingabe.");
			return reader();
		}
		
		return spielzug;
	}
	
}
