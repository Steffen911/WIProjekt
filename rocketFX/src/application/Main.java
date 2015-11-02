package application;
	

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import datenbank.DBConnector;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;


public class Main extends Application {

	private static Stage primStage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primStage = primaryStage;
			InputStream icon = new BufferedInputStream(new FileInputStream("pictures/rocket_icon.png"));
			primStage.getIcons().add(new Image(icon));
			primStage.setTitle("Team rocket - 4 Gewinnt");
			// Lade FXML Datei "StartScene" als erstes Fenster
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("StartScene.fxml"));
			Scene scene = new Scene(root);
			primStage.setScene(scene);
			primStage.show();
			primStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		          public void handle(WindowEvent we) {
		              DBConnector dbConn = new DBConnector();
		              dbConn.closeDB();
		          }
		      });  

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Stage getPrimaryStage(){
		return primStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
