package datenbank;

class DBDef {
	// Datenbank Definition enthaelt Aufbau der DB Struktur
	
	// Credentials
	String url = "jdbc:hsqldb:MyDB";
	String user = "sa";
	String password = "";
	// Tabellen
	String[] tables = {"SPIELE","SAETZE"};
	String createGamesTableStatement = "create table SPIELE (ID integer generated by default as identity (start with 1), GEGNER varchar(20), SIEGER varchar(20), PUNKTE integer)"; 
	String createSatzTableStatement =  "create table SAETZE (SPIELID integer, SATZID integer, STARTER varchar(20), SIEGER varchar(20), "+
										"ZUEGEICH varchar(50), ZUEGEGEGNER varchar(50), constraint PK PRIMARY KEY (SPIELID,SATZID))"; 
	String testIfTablesExistStmt = "select TABLE_SCHEMA, TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA = 'PUBLIC'";
	String[] tableCreateStatements = {createGamesTableStatement,createSatzTableStatement};
	// Testdaten

}
