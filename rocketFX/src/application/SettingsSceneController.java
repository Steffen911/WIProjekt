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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SettingsSceneController implements  Initializable{
	@FXML Button playBtn;
	@FXML ImageView helpBtn;
	@FXML RadioButton radioBtnO;
	@FXML RadioButton radioBtnX;
	@FXML RadioButton radioBtnPush;
	@FXML RadioButton radioBtnFile;
	@FXML TextField PfadKeyEdit;
	@FXML TextField SecretEdit;
	@FXML TextField Edit;
	@FXML TextField GegnerEdit;
	@FXML Label PfadKeyLabel;
	@FXML Label SecretLabel;

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
		
		ToggleGroup pushFileToggle = new ToggleGroup();
		radioBtnFile.setToggleGroup(pushFileToggle);
		radioBtnPush.setToggleGroup(pushFileToggle);
		
		ToggleGroup playerXOToggle = new ToggleGroup();
		radioBtnO.setToggleGroup(playerXOToggle);
		radioBtnX.setToggleGroup(playerXOToggle);
		
		radioBtnPush.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				PfadKeyLabel.setText("Key");
				SecretEdit.setVisible(true);
				SecretLabel.setVisible(true);
			}
		});
		radioBtnFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				PfadKeyLabel.setText("Pfad");
				SecretEdit.setVisible(false);
				SecretLabel.setVisible(false);
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
