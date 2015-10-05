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
	@FXML Button saveBtn;
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
		
		// starte das Spiel und spiele
		startServerConnection();
		playGame();
		
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
			// Gegnerzug in GUI anzeigen
			showZug(true);
			
			//Berechne neuen Spielzug auf Grundlage des gegnerzugs
			spielzug = ki.zugBerechnen(gegnerZug);
			// Spielzug Anzeigen
			showZug(false);
			//Sende errechneten Spielzug an Server und warte auf XML
			rueckgabe = server.sendZugAnServer(spielzug);
			
			
			//Gib aktuelles Array aus
			arrayAusgebenConsole(ki.arrayAusgabe());
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
	
	private void showZug(boolean gegner){
		// Verknuepfung zu GUI: Zuege anzeigen
		Point zugP;
		Circle circle = new Circle(14.0);
		if(gegner){
			zugP = ki.getGegnerPunkt();
			circle.setFill(Color.YELLOW);
		}else{
			zugP = ki.getEigenerPunkt();
			circle.setFill(Color.RED);
		}
		GameGrid.add(circle, zugP.x, zugP.y);

	}

}
