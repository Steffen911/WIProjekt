package application;


import java.awt.Point;

import datenbank.DBConnector;

public class Satz {
	
	private int SPIELID;
	private int SATZID;
	private String STARTER;
	private String SIEGER;
	private String ZUEGEICH;
	private String ZUEGEGEGNER;
	private int PUNKTE;
	private static DBConnector db;
	
	
	public Satz(int sPIELID, int sATZID) {
		super();
		SPIELID = sPIELID;
		SATZID = sATZID;
		ZUEGEGEGNER = "";
		ZUEGEICH = "";
		PUNKTE = 0;
		db = new DBConnector();
	}
	
	void saveSatzInDB(){
		db.insertNewSatz(SPIELID, SATZID, STARTER, SIEGER, ZUEGEICH, ZUEGEGEGNER,PUNKTE);
	}
	
	
	
	/*** Getter and Setter ***/
	public String getSTARTER() {
		return STARTER;
	}

	public void setSTARTER(String sTARTER) {
		STARTER = sTARTER;
	}

	public String getSIEGER() {
		return SIEGER;
	}

	public void setSIEGER(String sIEGER) {
		SIEGER = sIEGER;
		if(SIEGER.equals("rocket")){
			PUNKTE = 2;
		}else  {
			PUNKTE = 0;
		}
	}

	public String getZUEGEICH() {
		return ZUEGEICH;
	}

	public void addZugIch(Point zug) {
		ZUEGEICH += " "+ zug.x+zug.y;
	}

	public String getZUEGEGEGNER() {
		return ZUEGEGEGNER;
	}

	public void addZugGEGNER(Point zug) {
		ZUEGEGEGNER += " "+ zug.x +zug.y;
	}

	public int getSPIELID() {
		return SPIELID;
	}
	
	public int getSATZID() {
		return SATZID;
	}

	public void setZUEGEICH(String zUEGEICH) {
		ZUEGEICH = zUEGEICH;
	}

	public void setZUEGEGEGNER(String zUEGEGEGNER) {
		ZUEGEGEGNER = zUEGEGEGNER;
	}

	int getPUNKTE() {
		return PUNKTE;
	}

	void setPUNKTE(int pUNKTE) {
		PUNKTE = pUNKTE;
	}
	
}
