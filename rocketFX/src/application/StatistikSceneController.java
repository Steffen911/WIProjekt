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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class StatistikSceneController implements  Initializable{
	@FXML ImageView playBtn;
	@FXML ImageView helpBtn;
	@FXML ImageView settingsBtn;
	@FXML Button SpielFortsetzenBtn;
	@FXML TableView<ObservableList> SpielTable;
	@FXML TableView<ObservableList> SatzTable;
	@FXML TableColumn<ObservableList, String> SpielGegnerCol,SpielIDCol,SpielSiegerCol,SpielPunkteCol;
	@FXML TableColumn<ObservableList, String> SatzSpielIDCol, SatzSatzIDCol, SatzStarterCol,SatzSiegerCol,SatzZuegeIchCol,SatzZuegeGegnerCol,SatzPunkteCol;
	
	private DBConnector dbConn;
	private ReusableControllerFunctions reuse;
	private String selectedStr = "";
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		reuse = new ReusableControllerFunctions();
		dbConn = new DBConnector();
		
		SpielFortsetzenBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		     @Override
		     public void handle(MouseEvent event) {
		    	 String id = selectedStr.substring(1,selectedStr.indexOf(","));
		    	 String gegner = selectedStr.substring(selectedStr.indexOf(",")+2, selectedStr.indexOf(",", 5));
		    	 reuse.SpielFortsetzen(Integer.parseInt(id), gegner);
		    	 System.out.println("Spiel "+id+" gegen "+gegner+" fortsetzen.");
		    	 reuse.setNewScene("SettingsScene.fxml");
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
		    	 selectedStr =  table.getSelectionModel().getSelectedItems().get(0).toString();
		    	 String id = selectedStr.substring(1,selectedStr.indexOf(","));
		    	 System.out.println(id + " geklickt");
		    	 // Saetze von Spiel in DB suchen
		    	 ResultSet rs = dbConn.getSaetzeOfSpiel(id);
		    	 // Saetze anzeigen
		    	 showSatzTable(rs);
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
	
	private void showSatzTable(ResultSet rs){
		SatzTable.getItems().clear();
		SatzSpielIDCol.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
            public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                return new SimpleStringProperty(param.getValue().get(0).toString());                        
            }                    
        });
		SatzSatzIDCol.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
            public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                return new SimpleStringProperty(param.getValue().get(1).toString());                        
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
		SatzPunkteCol.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
            public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                return new SimpleStringProperty(param.getValue().get(6).toString());                        
            }                    
        }); 
		
		if(rs != null){
			ObservableList<ObservableList> data = FXCollections.observableArrayList();
			data.clear();
			// Add Data to Observable List
			try {
				while(rs.next()){
				    //Iterate Row
				    ObservableList<String> row = FXCollections.observableArrayList();
				    row.clear();
				    for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
				        //Iterate Column
				        row.add(rs.getString(i));
				    }
				    if(row != null){
				    	data.add(row);
				    }
				}
				try {
					if(data != null){
						SatzTable.setItems(data);
					}else{ System.out.println("Keine Satzdaten.");}
				} catch (Exception e) {
					//e.printStackTrace();
					System.out.println("NullPointer Eception");
				}
			} catch (SQLException e) {
				System.out.println("Dateneinsortieren fuer Tabelle hat nicht geklappt.");
				e.printStackTrace();
			}
		}	
	}

}
