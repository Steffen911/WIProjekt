package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import datenbank.DBConnector;

public class Spiel {

	private int ID;
	private String GEGNER;
	private String SIEGER;
	private int PUNKTE;
	private ArrayList<Satz> satzList = new ArrayList<Satz>();
	private int currentSatzIndex;
	private static DBConnector db;


	public Spiel() {
		super();
		GEGNER = "";
		SIEGER = "";
		PUNKTE = 0;
		ID = 0;
		currentSatzIndex = 0;
		db = new DBConnector();
	}

	
	public void addNewSatz(){
		satzList.add(new Satz(ID,satzList.size()));
		currentSatzIndex = satzList.size() -1;
		System.out.println("New Satz "+ currentSatzIndex);
	}
	
	public Satz getCurrentSatz(){
		return satzList.get(currentSatzIndex);
	}
	
	public void saveSpielInDB(){
		if(ID == 0){ // bei Spiel Fortsetzen ist ID schon gesetzt
			int id = db.insertNewSpiel(GEGNER, SIEGER, PUNKTE);
			ID = id;
		}
	}
	
	public void updateSpielInDB(){
		// Punkte aus Sätzen aufaddieren
		PUNKTE = 0;
		for (int i = 0; i < satzList.size()-1; i++) {
			PUNKTE += satzList.get(i).getPUNKTE();
		}
		// Sieger ermitteln
		if(satzList.size() < PUNKTE){
			SIEGER = "rocket";
		}else if(satzList.size() == PUNKTE){
			SIEGER = "unentschieden";
		}else{
			SIEGER = getGEGNER();
		}
		db.updateSpiel(ID, SIEGER, PUNKTE);
	}
	
	public void spielFortsetzen(int id){
		// SpielID setzen
		ID = id;
		// Saetze in satzList speichern
		ResultSet rs = db.getSaetzeOfSpiel(String.valueOf(ID));
		if(rs != null){
			try {
				while(rs.next()){
					satzList.add(new Satz(ID, rs.getInt("SATZID")));
					System.out.println("Satz "+ rs.getInt("SATZID")+" ist Eintrag "+(satzList.size()-1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*** Getter and Setter ***/
	public String getSIEGER() {
		return SIEGER;
	}
	public void setSIEGER(String sIEGER) {
		SIEGER = sIEGER;
	}
	public int getPUNKTE() {
		return PUNKTE;
	}
	public void setPUNKTE(int pUNKTE) {
		PUNKTE = pUNKTE;
	}
	public int getID() {
		return ID;
	}
	public String getGEGNER() {
		return GEGNER;
	}

	public void setGEGNER(String gEGNER) {
		GEGNER = gEGNER;
	}
	
	
}
