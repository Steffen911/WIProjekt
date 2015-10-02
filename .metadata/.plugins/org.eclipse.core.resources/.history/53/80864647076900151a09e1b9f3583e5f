package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import ki.KI;
import serverKommunikation.ServerGuiKontakt;

public class GameSceneController implements  Initializable{
	@FXML Button saveBtn;
	@FXML ImageView helpBtn;
	@FXML ImageView playBtn;
	
	private ReusableControllerFunctions reuse;
	private ServerGuiKontakt server;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		reuse = new ReusableControllerFunctions();
		
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				reuse.setNewScene("SavingScene.fxml");
			}
		});	
		
		playBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		     @Override
		     public void handle(MouseEvent event) {
		    	 reuse.setNewScene("StartScene.fxml");
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
		KI ki = new KI(server.getSpielerwahl());
		
		// ToDo: mit Steffen abgleichen, ob das so passt; Reader erster Spielzug, Beginn??!
		/*ToDo: Ki Methode getGegner y, Rueckgabe bei eigenenm Zug Position 
		 * => Ki gibt x,y Position der Steine zurueck, sodass GUI nicht auch noch rechnet (Point oder array)*/
		
		rueckgabe = server.leseVomServer();
		
		while(!rueckgabe[1].equals("beendet")){
			gegnerZug = Integer.parseInt(rueckgabe[2]);
			// Gegnerzug in GUI anzeigen
			showZug(gegnerZug,true);
			
			//Berechne neuen Spielzug auf Grundlage des gegnerzugs
			spielzug = ki.zugBerechnen(gegnerZug);
			// Spielzug Anzeigen
			showZug(spielzug, false);
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
	
	private void showZug(int zug, boolean gegner){
		// Verknuepfung zu GUI: Zuege anzeigen
		// ToDo: Spielzug in der GUI anzeigen

	}

}
