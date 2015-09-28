package application;

import java.net.URL;
import java.util.ResourceBundle;

import datenbank.DBConnector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class SavingSceneController implements  Initializable{
	@FXML Button saveBtn;
	@FXML ImageView helpBtn;
	@FXML Button deleteBtn;
	@FXML TextField gewinnerEdit, starterEdit, zuegeEdit;
	
	private DBConnector dbConn;
	private ReusableControllerFunctions reuse;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	
		reuse = new ReusableControllerFunctions();
		// Edits vorbelegen
		Satz satz = (reuse.getSpiel()).getCurrentSatz();
		gewinnerEdit.setText(satz.getSIEGER());
		starterEdit.setText(satz.getSTARTER());
		zuegeEdit.setText("rocket: "+ satz.getZUEGEICH()+"/n"+
						"Gegner: "+ satz.getZUEGEGEGNER());
		
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				// Editeingaben uebernehmen
				satz.setSIEGER(gewinnerEdit.getText());
				satz.setSTARTER(starterEdit.getText());
				//ToDo: Zuege speichern (String schneiden)
				String zuege = zuegeEdit.getText().substring(zuegeEdit.getText().indexOf(" ")+1);
				zuege = zuege.substring(0, zuege.indexOf("/n"));
				satz.setZUEGEICH(zuege);
				zuege = zuegeEdit.getText().substring(zuegeEdit.getText().indexOf("Gegner: ")+1);
				satz.setZUEGEGEGNER(zuege); 
				// in DB speichern
				satz.saveSatzInDB();
				reuse.setNewScene("GameScene.fxml");
			}
		});	
		
		deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				reuse.setNewScene("GameScene.fxml");
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
