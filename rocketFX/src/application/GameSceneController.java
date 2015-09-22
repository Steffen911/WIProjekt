package application;

import java.net.URL;
import java.util.ResourceBundle;

import datenbank.DBConnector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class GameSceneController implements  Initializable{
	@FXML Button saveBtn;
	
	private DBConnector dbConn;
	private ReusableControllerFunctions reuse;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	
		reuse = new ReusableControllerFunctions();
		
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				reuse.setNewScene("SavingScene.fxml");
			}
		});	
	}

}
