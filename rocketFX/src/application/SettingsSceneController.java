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
import javafx.scene.control.Menu;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SettingsSceneController implements  Initializable{
	@FXML Button playBtn;

	private DBConnector dbConn;
	private ReusableControllerFunctions reuse;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		reuse = new ReusableControllerFunctions();
		
		playBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				reuse.setNewScene("GameScene.fxml");
			}
		});
		

		
	}
	
	

}
