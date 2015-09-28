package application;

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
		currentSatzIndex = 0;
		db = new DBConnector();
	}

	
	public void addNewSatz(){
		satzList.add(new Satz(ID,satzList.size()));
		currentSatzIndex = satzList.size() -1;
	}
	
	public Satz getCurrentSatz(){
		return satzList.get(currentSatzIndex);
	}
	
	public void saveSpielInDB(){
		int id = db.insertNewSpiel(GEGNER, SIEGER, PUNKTE);
		ID = id;
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
