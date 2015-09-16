package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;

public class StartSceneController implements  Initializable{
	@FXML Slider zeitslider;
	@FXML Label zeitlabel;
	@FXML Button spielStartenBtn;
	@FXML RadioButton radioBtnO;
	@FXML RadioButton radioBtnX;
	


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		spielStartenBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				zeitlabel.setText("Test");		
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

}
