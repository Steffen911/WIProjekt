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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StartSceneController implements  Initializable{
	@FXML Button spielStartenBtn;
	@FXML ImageView helpBtn;
	@FXML ImageView statistikBtn;

	private DBConnector dbConn;
	private ReusableControllerFunctions reuse;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Verbindung zur DB herstellen
		dbConn = new DBConnector();
		reuse = new ReusableControllerFunctions();
		
		spielStartenBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				reuse.neuesSpiel();
				reuse.setNewScene("SettingsScene.fxml");
			}
		});
		
		statistikBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		     @Override
		     public void handle(MouseEvent event) {
		    	 reuse.setNewScene("StatistikScene.fxml");
		     }
		});

		helpBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		     @Override
		     public void handle(MouseEvent event) {
		    	 reuse.setNewScene("HelpScene.fxml");
		     }
		});
		
	}
	
	

}
