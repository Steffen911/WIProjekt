package application;

import javafx.beans.property.StringProperty;

public class SpielForTable {
	private String SpielIDCol,SpielGegnerCol,SpielSiegerCol,SpielPunkteCol;
	
	public SpielForTable(String ID,String gegner,String sieger,String punkte){
		SpielIDCol = ID;
		SpielGegnerCol = gegner;
		SpielSiegerCol = sieger;
		SpielPunkteCol = punkte;
	}
	
}
