package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import datenbank.DBConnector;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class StatistikSceneController implements  Initializable{
	@FXML ImageView playBtn;
	@FXML ImageView helpBtn;
	@FXML ImageView settingsBtn;
	@FXML Button SpielAendernBtn, SatzAendernBtn, SpielFortsetzenBtn;
	@FXML TableView<ObservableList> SpielTable, SatzTable;
	@FXML TableColumn<ObservableList, String> SpielGegnerCol,SpielIDCol,SpielSiegerCol,SpielPunkteCol;
	@FXML TableColumn<ObservableList, String> SatzSpielIDCol, SatzSatzIDCol, SatzStarterCol,SatzSiegerCol,SatzZuegeIchCol,SatzZuegeGegnerCol;
	
	private DBConnector dbConn;
	private ReusableControllerFunctions reuse;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		reuse = new ReusableControllerFunctions();
		dbConn = new DBConnector();
		
		//TODO: Spiel ueberhaupt aenderbar wegen Aufwand in DB?
		// TODO: SpeichernBtn fuer wenn man aendern gedrueckt hat :D
		
		SpielAendernBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		     @Override
		     public void handle(MouseEvent event) {
		    	 SpielTable.setEditable(true);
		     }
		});
		SatzAendernBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		     @Override
		     public void handle(MouseEvent event) {
		    	//TODO SatzTable.setEditable(true);
		     }
		});
		SpielFortsetzenBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		     @Override
		     public void handle(MouseEvent event) {
		    	 //TODO: Spiel aus Zeile in SpielObjekt laden, in SettingsScene neuen Satz starten
		     }
		});

		playBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		     @Override
		     public void handle(MouseEvent event) {
		    	 reuse.setNewScene("StartScene.fxml");
		     }
		});
		
		settingsBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		     @Override
		     public void handle(MouseEvent event) {
		    	 reuse.setNewScene("SettingsScene.fxml");
		     }
		});
		
		helpBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		     @Override
		     public void handle(MouseEvent event) {
		    	 reuse.setNewScene("HelpScene.fxml");
		     }
		});

		SpielTable.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		     @Override
		     public void handle(MouseEvent event) {
		    	 //TODO: Show Satz
		    	 // Welcher Zeile wurde geklickt?
		    	 TableView table = (TableView) event.getSource();
		    	 String selected =  table.getSelectionModel().getSelectedItems().get(0).toString();
		    	 String id = selected.substring(1,3);
		    	 System.out.println(id + " geklickt");
		    	 // Saetze von Spiel in DB suchen
		    	 ResultSet rs = dbConn.getSaetzeOfSpiel(id);
		    	 // Saetze anzeigen
		    	 showSpielTable(rs);
		     }
		});

		fillSpielTable();
		
	}
	
	private void fillSpielTable(){
		//SpielGegnerCol.setCellValueFactory(new PropertyValueFactory<SpielForTable, String>("SpielGegnerCol"));
		SpielIDCol.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
            public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                return new SimpleStringProperty(param.getValue().get(0).toString());                        
            }                    
        });
		SpielGegnerCol.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
            public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                return new SimpleStringProperty(param.getValue().get(1).toString());                        
            }                    
        });
		SpielSiegerCol.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
            public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                return new SimpleStringProperty(param.getValue().get(2).toString());                        
            }                    
        });
		SpielPunkteCol.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
            public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                return new SimpleStringProperty(param.getValue().get(3).toString());                        
            }                    
        });
		ResultSet rs = dbConn.getAllSpiele();
		
		if(rs != null){
			ObservableList<ObservableList> data = FXCollections.observableArrayList();
			// Add Data to Observable List
			try {
				while(rs.next()){
				    //Iterate Row
				    ObservableList<String> row = FXCollections.observableArrayList();
				    for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
				        //Iterate Column
				        row.add(rs.getString(i));
				    }
				    data.add(row);
	
				}
				SpielTable.setItems(data);
			} catch (SQLException e) {
				System.out.println("Dateneinsortieren fuer Tabelle hat nicht geklappt.");
				e.printStackTrace();
			}
		}
	}
	
	private void showSpielTable(ResultSet rs){
		SatzSatzIDCol.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
            public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                return new SimpleStringProperty(param.getValue().get(1).toString());                        
            }                    
        });
		SatzSpielIDCol.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
            public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                return new SimpleStringProperty(param.getValue().get(0).toString());                        
            }                    
        });
		SatzStarterCol.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
            public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                return new SimpleStringProperty(param.getValue().get(2).toString());                        
            }                    
        });
		SatzSiegerCol.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
            public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                return new SimpleStringProperty(param.getValue().get(3).toString());                        
            }                    
        });
		SatzZuegeIchCol.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
            public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                return new SimpleStringProperty(param.getValue().get(4).toString());                        
            }                    
        });
		SatzZuegeGegnerCol.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
            public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                return new SimpleStringProperty(param.getValue().get(5).toString());                        
            }                    
        });

		
		if(rs != null){
			ObservableList<ObservableList> data = FXCollections.observableArrayList();
			// Add Data to Observable List
			try {
				while(rs.next()){
				    //Iterate Row
				    ObservableList<String> row = FXCollections.observableArrayList();
				    for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
				        //Iterate Column
				        row.add(rs.getString(i));
				    }
				    data.add(row);
	
				}
				SpielTable.setItems(data);
			} catch (SQLException e) {
				System.out.println("Dateneinsortieren fuer Tabelle hat nicht geklappt.");
				e.printStackTrace();
			}
		}	
	}

}
