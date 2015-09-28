package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ReusableControllerFunctions {

	private static String lastSceneOnBack;
	private static String newScene;
	private static Spiel spiel;
	
	
	public ReusableControllerFunctions() {
		newScene = "StartScene.fxml";
		lastSceneOnBack = "";
		spiel = new Spiel();
	}

	public String getLastScene(){
		return lastSceneOnBack;
	}
	
	public void neuesSpiel(){
		spiel = new Spiel();
	}
	
	public Spiel getSpiel(){
		return spiel;
	}

	public void setNewScene(String dateiname){
		try {
			// Lade neue FXML Datei als Anzeige
			Pane root = (Pane) FXMLLoader.load(getClass().getResource(dateiname));
			Scene scene = new Scene(root);
			Stage primaryStage = Main.getPrimaryStage();
			primaryStage.setScene(scene);
			primaryStage.show();
			
			// neuen Screen merken für Zurueck Button
			lastSceneOnBack = newScene;
			newScene = dateiname;

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
