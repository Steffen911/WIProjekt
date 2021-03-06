package application;

import java.net.URL;
import java.util.ResourceBundle;

import datenbank.DBConnector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class SavingSceneController implements  Initializable{
	@FXML Button saveBtn;
	@FXML ImageView helpBtn;
	@FXML Button deleteBtn;
	@FXML TextField gewinnerEdit, starterEdit, punkteEdit;
	@FXML TextArea zuegeArea;
	
	private DBConnector dbConn;
	private ReusableControllerFunctions reuse;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO: Steffen: getWinner, getStarter
		//TODO: letzten Zug anzeigen geloest?
		reuse = new ReusableControllerFunctions();
		// Edits vorbelegen
		Satz satz = (reuse.getSpiel()).getCurrentSatz();
		gewinnerEdit.setText(satz.getSIEGER());
		starterEdit.setText(satz.getSTARTER());
		punkteEdit.setText(Integer.toString(satz.getPUNKTE()));
		zuegeArea.setText("rocket: "+ satz.getZUEGEICH()+"\n"+
						"Gegner: "+ satz.getZUEGEGEGNER());
		
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				// Editeingaben uebernehmen
				satz.setSIEGER(gewinnerEdit.getText());
				satz.setSTARTER(starterEdit.getText());
				satz.setPUNKTE(Integer.parseInt(punkteEdit.getText()));// SetPunkte NACH Sieger aufrufen!
				String zuege = zuegeArea.getText().substring(zuegeArea.getText().indexOf(" ")+1);
				zuege = zuege.substring(0, zuege.indexOf("\n"));
				satz.setZUEGEICH(zuege);
				zuege = zuegeArea.getText().substring(zuegeArea.getText().indexOf("Gegner: ")+8);
				satz.setZUEGEGEGNER(zuege); 
				// in DB speichern
				satz.saveSatzInDB();
				(reuse.getSpiel()).updateSpielInDB();
				reuse.setNewScene("SettingsScene.fxml"); // fuer neuen Satz
			}
		});	
		
		deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				reuse.setNewScene("SettingsScene.fxml"); // fuer neuen Satz
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
