package application;

import java.awt.Point;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
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
	private Thread guiTh;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		reuse = new ReusableControllerFunctions();
//		guiTh = new Thread(guiTask);
//		guiTh.setDaemon(true);
		
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
	
	
//	Task<Void> guiTask = new Task<Void>() {
//
//		@Override
//		protected Void call() throws Exception {
//			showZug();
//			return null;
//		}
//		
//	};
	
	public void playGame(){
		
		new Thread( new Runnable() {
			
			@Override
			public void run() {
				int spielzug;
				
				String[] rueckgabe = new String[4];
				
				ki = new KI(server.getSpielerwahl());
				rueckgabe = server.leseVomServer();
				while(!rueckgabe[1].equals("beendet")){
					
					//Berechne neuen Spielzug auf Grundlage des gegnerzugs
					spielzug = ki.zugBerechnen(Integer.parseInt(rueckgabe[2]));

					// Spielzug Anzeigen
					//Gib aktuelles Array aus
					arrayAusgebenConsole(ki.arrayAusgabe());
					//showZug();
					Platform.runLater(new Runnable() {
			            @Override public void run() {
			                showZug(ki.getGegnerPunkt(), ki.getEigenerPunkt());
			            }
			        });
					//guiTh.start();
					
					//Sende errechneten Spielzug an Server und warte auf XML
					rueckgabe = server.sendZugAnServer(spielzug);
					
					//Starte von vorn
				}	
				
			}
		}).start();
	
		
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
	private void showZug(Point gegner, Point wir){
		// Verknuepfung zu GUI: Zuege anzeigen
		if(wir.x >= 0){
		  GameGrid.add(new Circle(14.0, Color.YELLOW), wir.x, 5-wir.y);
		}
		if(gegner.x >= 0){
		  GameGrid.add(new Circle(14.0, Color.RED), gegner.x, 5-gegner.y);
		}
//		Point zugP;
//		zugP = ki.getGegnerPunkt();
//		if(zugP.x >= 0){
//			GameGrid.add(new Circle(14.0, Color.YELLOW), zugP.x, 5-zugP.y);
//		}
//		zugP = ki.getEigenerPunkt();
//		if(zugP.x >= 0){
//			GameGrid.add(new Circle(14.0, Color.RED), zugP.x, 5-zugP.y);
//		}
		
	}
	

}
