package application;

import java.net.URL;
import java.util.ResourceBundle;

import datenbank.DBConnector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StartSceneController implements  Initializable{
	@FXML Slider zeitslider;
	@FXML Label zeitlabel;
	@FXML Button spielStartenBtn;
	@FXML RadioButton radioBtnO;
	@FXML RadioButton radioBtnX;
	
	public DBConnector dbConn;
	


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Verbindung zur DB herstellen
		//dbConn = new DBConnector();
		
		spielStartenBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				zeitlabel.setText("Test");	
				setNewScene("GameScene.fxml");
			}
		});
		
		ToggleGroup choosePlayerToggleGrp = new ToggleGroup();
		radioBtnO.setToggleGroup(choosePlayerToggleGrp);
		radioBtnX.setToggleGroup(choosePlayerToggleGrp);

		
// Kopiervorlage für Events		
//		.setOnAction(new EventHandler<ActionEvent>() {
//			@Override public void handle(ActionEvent event) {
//						
//			}
//		});
		
	}
	
	private void setNewScene(String dateiname){
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
