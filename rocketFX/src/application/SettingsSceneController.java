package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import datenbank.DBConnector;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SettingsSceneController implements  Initializable{
	@FXML Button playBtn;
	@FXML ImageView helpBtn;
	@FXML RadioButton radioBtnO;
	@FXML RadioButton radioBtnX;
	@FXML RadioButton radioBtnPush;
	@FXML RadioButton radioBtnFile;
	@FXML TextField PfadEdit;
	@FXML TextField KeyEdit;
	@FXML TextField SecretEdit;
	@FXML TextField Edit;
	@FXML TextField GegnerEdit;
	@FXML Label PfadKeyLabel;
	@FXML Label SecretLabel;
	@FXML Button chooserBtn;
	@FXML Slider zeitslider;
	@FXML Label zeitlabel;
	@FXML Button saveBtn;
	
	private String spielerwahl;
	private boolean pushSchnittstelle;
	private int zeit;
	
	private ReusableControllerFunctions reuse;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		reuse = new ReusableControllerFunctions();
// TODO: back Btn zum Spiel Starten Screen
		playBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				// SpielObjekt
				Spiel spiel = reuse.getSpiel();
				spiel.setGEGNER(GegnerEdit.getText());
				spiel.saveSpielInDB();
				spiel.addNewSatz();
				// ServerObjekt
				if(pushSchnittstelle){
					reuse.createServer(KeyEdit.getText(), SecretEdit.getText(), zeit, spielerwahl);
				}else{
					reuse.createServer(spielerwahl, EditPfad(PfadEdit.getText()), zeit);
				}
				reuse.setNewScene("GameScene.fxml");
			}
		});
		
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
		//ToDO:saveBtn speichern und zurueck
			reuse.setNewScene(reuse.getLastScene());
			}
		});
		

		
		ToggleGroup pushFileToggle = new ToggleGroup();
		radioBtnFile.setToggleGroup(pushFileToggle);
		radioBtnPush.setToggleGroup(pushFileToggle);
		
		ToggleGroup playerXOToggle = new ToggleGroup();
		radioBtnO.setToggleGroup(playerXOToggle);
		radioBtnX.setToggleGroup(playerXOToggle);
		
		// Vorbelegung
		spielerwahl = "o";
		pushSchnittstelle = true;
		
		radioBtnPush.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				PfadKeyLabel.setText("Key:");
				SecretEdit.setVisible(true);
				SecretLabel.setVisible(true);
				chooserBtn.setVisible(false);
				pushSchnittstelle = true;
				KeyEdit.setVisible(true);
				PfadEdit.setVisible(false);
			}
		});
		radioBtnFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				PfadKeyLabel.setText("Pfad:");
				SecretEdit.setVisible(false);
				SecretLabel.setVisible(false);
				chooserBtn.setVisible(true);
				pushSchnittstelle = false;
				PfadEdit.setVisible(true);
				KeyEdit.setVisible(false);
			}
		});
		
		radioBtnO.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				spielerwahl = "o";
			}
		});	
		radioBtnX.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				spielerwahl = "x";
			}
		});	
		
		chooserBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
		
				DirectoryChooser directoryChooser = new DirectoryChooser();
				Stage stage = new Stage();
				File selectedDirectory = directoryChooser.showDialog(stage);
				if(selectedDirectory != null){
					selectedDirectory.getAbsolutePath();
				}
				PfadEdit.setText(selectedDirectory.getPath());
			}
		});
		
		helpBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		     @Override
		     public void handle(MouseEvent event) {
		    	 reuse.setNewScene("HelpScene.fxml");
		     }
		});

		
		zeitslider.valueProperty().addListener(new ChangeListener<Number>() {
		      @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
		        if (newValue == null) {
		          zeitlabel.setText("");
		          return;
		        }
		        zeit = (int) Math.round(newValue.doubleValue()*10);
		        zeitlabel.setText(String.format( "%.1f sec",zeit*0.1));
		      }
		    });		
		
	}
	
	private String EditPfad(String pfad){
		pfad = pfad.replaceAll("/", "//");
		return pfad += "//";
	}
	
	

}
