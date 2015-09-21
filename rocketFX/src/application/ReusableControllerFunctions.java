package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ReusableControllerFunctions {

	
	public void setNewScene(String dateiname){
		try {
			// Lade neue FXML Datei als Anzeige
			Pane root = (Pane) FXMLLoader.load(getClass().getResource(dateiname));
			Scene scene = new Scene(root);
			Stage primaryStage = Main.getPrimaryStage();
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
