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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import ki.*;
import serverKommunikation.ServerGuiKontakt;

public class GameSceneController implements  Initializable{
	@FXML Button saveBtn, spielBtn;
	@FXML ImageView helpBtn;
	@FXML GridPane GameGrid;
	@FXML Label GegnerGameScene, gewonnenLabel;
	
	private ReusableControllerFunctions reuse;
	private ServerGuiKontakt server;
	private KI ki;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		reuse = new ReusableControllerFunctions();
		
		GegnerGameScene.setText(reuse.getSpiel().getGEGNER());
		
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				server.disconnect(); //Nach dem Spiel wird die Verbinudng zum Pusher beendet
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
		    	 spielBtn.setVisible(false);
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
//					
//					if(rueckgabe[3] == "Spieler O" || rueckgabe[3] == "Spieler X"){
//						ki.setzeGegnerStein(Integer.parseInt(rueckgabe[2]));
//						ki.setWinner(rueckgabe[3]);
//					}
					
					//Starte von vorn
				}
				System.out.println("letzer zug: "+rueckgabe[2]);
				
				if(!rueckgabe[2].equals("-1")){
					ki.setzeGegnerStein(Integer.parseInt(rueckgabe[2]));
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
							satz.setSIEGER(reuse.getSpiel().getGEGNER());// Gegner gewonnen
							System.out.println("Thread durch.");
							gewonnenLabel.setText("Spiel verloren.");
							gewonnenLabel.setVisible(true);
						}
					});
					
					
				}else{
					System.out.println("Wir haben gewonnen.");
					reuse.getSpiel().getCurrentSatz().setSIEGER("rocket");
					
					Platform.runLater(new Runnable() {
						@Override public void run() {
							gewonnenLabel.setText("Spiel gewonnen! :-)");
							gewonnenLabel.setVisible(true);
						}
					});
				}
		
				Satz satz = reuse.getSpiel().getCurrentSatz();
				// Starter speichern
				if(ki.getEigenerStein().equals(ki.getStarter())){
					satz.setSTARTER("rocket");
				}else{
					satz.setSTARTER(reuse.getSpiel().getGEGNER());
				}
				
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
		
		Circle yellowCircle = new Circle(14.0, Color.YELLOW);
		Circle redCircle = new Circle(14.0, Color.RED);
		
		Text oText = new Text("o");
		Text xText = new Text("x");
		
		StackPane yellowStack = new StackPane();
		StackPane redStack = new StackPane();
		
		if(server.getSpielerwahl() == "o"){
			yellowStack.getChildren().addAll(yellowCircle, oText);
			redStack.getChildren().addAll(redCircle, xText);
		}else{
			yellowStack.getChildren().addAll(yellowCircle, xText);
			redStack.getChildren().addAll(redCircle, oText);
		}
		
			// Verknuepfung zu GUI: Zuege anzeigen
			if(wir.x >= 0){
				try {
					GameGrid.add(yellowStack, wir.x, 5-wir.y);
				} catch (Exception e) {
					System.out.println("Eigener punkt exisitiert schon in GUI.");
					//e.printStackTrace();
				}
			}
			if(gegner.x >= 0){
				try {
					GameGrid.add(redStack, gegner.x, 5-gegner.y);
				} catch (Exception e) {
					System.out.println("Gegner Punkt existiert schon in GUI.");
					//e.printStackTrace();
				}
			}
	}

}
