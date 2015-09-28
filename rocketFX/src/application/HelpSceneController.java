package application;

import java.net.URL;
import java.util.ResourceBundle;

import datenbank.DBConnector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class HelpSceneController implements  Initializable{
	@FXML ImageView playBtn;
	@FXML Button backToScreen;
	
	private DBConnector dbConn;
	private ReusableControllerFunctions reuse;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		reuse = new ReusableControllerFunctions();
		
		playBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		     @Override
		     public void handle(MouseEvent event) {
		    	 reuse.setNewScene("StartScene.fxml");
		     }
		});
		
		
		

		
		
	}

}