package application.forms;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.models.Profile;
import smiley.models.Transaccao;

public class ModifyProfileController implements Initializable {

	private Profile profile;

	@FXML
	Label profileNameLbl;

	@FXML
	TextField profileNameTxt;

	@FXML
	TableView<Transaccao> transaccoesTable;

	@FXML
	TableColumn<Transaccao, Long> codeColumn;

	@FXML
	TableColumn<Transaccao, String> urlColumn;

	@FXML
	TableColumn<Transaccao, Boolean> activeColumn;

	@FXML
	TableColumn<Transaccao, String> selectedColumn;


	DataManager dataManager = new DataManagerImp();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//selectedColumn.setCellValueFactory(selectedFactory);
		selectedColumn.setCellValueFactory(new PropertyValueFactory<Transaccao, String>("select"));
		List<Transaccao> transaccoes = dataManager.findAllTransaccoes();
		// if(t)

		codeColumn.setCellValueFactory(new PropertyValueFactory<Transaccao, Long>("code"));
		urlColumn.setCellValueFactory(new PropertyValueFactory<Transaccao, String>("url"));
		activeColumn.setCellValueFactory(new PropertyValueFactory<Transaccao, Boolean>("active"));
		transaccoesTable.setItems(FXCollections.observableArrayList(transaccoes));
	}

	public void setProfile(Profile profile) {
		if (profile != null) {
			this.profile = profile;
			setValues();
		}
	}

	private void setValues() {
		if (this.profile != null) {
			profileNameLbl.setText(profile.getProfilename());
		}

	}

}
