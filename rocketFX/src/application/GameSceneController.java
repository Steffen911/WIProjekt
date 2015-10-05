package application;

import java.awt.Point;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import ki.KI;
import serverKommunikation.ServerGuiKontakt;

public class GameSceneController implements  Initializable{
	@FXML Button saveBtn, spielBtn;
	@FXML ImageView helpBtn;
	@FXML GridPane GameGrid;
	
	private ReusableControllerFunctions reuse;
	private ServerGuiKontakt server;
	private KI ki;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		reuse = new ReusableControllerFunctions();
		
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				reuse.setNewScene("SavingScene.fxml");
			}
		});	
		
		
		helpBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		     @Override
		     public void handle(MouseEvent event) {
		    	 reuse.setNewScene("HelpScene.fxml");
		     }
		});
		
		spielBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		     @Override
		     public void handle(MouseEvent event) {
		    	 // starte das Spiel und spiele
		    	 startServerConnection();
		    	 playGame();
		     }
		});
	}
	
	public void startServerConnection(){
		server = reuse.getServer();
	}
	
	public void playGame(){
		int spielzug, gegnerZug;
		String[] rueckgabe = new String[4];
		ki = new KI(server.getSpielerwahl());
		
		// ToDo: mit Steffen abgleichen, ob das so passt; Reader erster Spielzug, Beginn??!
		/*ToDo: Ki Methode getGegner y, Rueckgabe bei eigenenm Zug Position 
		 * => Ki gibt x,y Position der Steine zurueck, sodass GUI nicht auch noch rechnet (Point oder array)*/
		
		rueckgabe = server.leseVomServer();
		
		while(!rueckgabe[1].equals("beendet")){
			gegnerZug = Integer.parseInt(rueckgabe[2]);
			
			//Berechne neuen Spielzug auf Grundlage des gegnerzugs
			spielzug = ki.zugBerechnen(gegnerZug);
			// Spielzug Anzeigen
			//Gib aktuelles Array aus
			arrayAusgebenConsole(ki.arrayAusgabe());
			showZug();
			//Sende errechneten Spielzug an Server und warte auf XML
			rueckgabe = server.sendZugAnServer(spielzug);
			
			//Starte von vorn
		}
		
		System.out.println("Jemand hat gewonnen.");
	}

	private void arrayAusgebenConsole(String[][] ausgabe){
		for(int i=5; i>=0; i--){	
			for(int j=0; j<7; j++){			
				System.out.print(ausgabe[j][i] + " ");
			}
			System.out.print("\n");
		}
	
	}
	private void showZug(){
		// Verknuepfung zu GUI: Zuege anzeigen
		Point zugP;
		zugP = ki.getGegnerPunkt();
		if(zugP.x > 0){
			GameGrid.add(new Circle(14.0, Color.YELLOW), zugP.x, 5-zugP.y);
		}
		zugP = ki.getEigenerPunkt();
		if(zugP.x > 0){
			GameGrid.add(new Circle(14.0, Color.RED), zugP.x, 5-zugP.y);
		}
	}
	

}
