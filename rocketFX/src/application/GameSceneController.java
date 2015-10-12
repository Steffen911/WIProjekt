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
import javafx.scene.control.Label;
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
	@FXML Label GegnerGameScene;
	
	private ReusableControllerFunctions reuse;
	private ServerGuiKontakt server;
	private KI ki;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		reuse = new ReusableControllerFunctions();
		
		GegnerGameScene.setText(reuse.getSpiel().getGEGNER());
		
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

					System.out.println("GegnerZug ist " + rueckgabe[2]);
					System.out.println("EigenerZug ist " + spielzug);
					
					// Spielzug Anzeigen
					//Gib aktuelles Array aus
					arrayAusgebenConsole(ki.arrayAusgabe());
					Platform.runLater(new Runnable() {
			            @Override public void run() {
			            	Point gegnerP, wirP;
			            	gegnerP = ki.getGegnerPunkt();
			            	wirP = ki.getEigenerPunkt();
			                showZug(gegnerP,wirP);
			                Satz satz = reuse.getSpiel().getCurrentSatz();
			                satz.addZugGEGNER(gegnerP);
			                satz.addZugIch(wirP);
			            }
			        });

					//Sende errechneten Spielzug an Server und warte auf XML
					rueckgabe = server.sendZugAnServer(spielzug);
					
					
					//Starte von vorn
				}
	
				showZug(ki.getGegnerPunkt(), ki.getEigenerPunkt());
				 
				System.out.println("Jemand hat gewonnen.");
				
			}
		}).start();
	
		
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
	}
	

}
