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
import smiley.utils.ApplicationUtils;

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

	@FXML
	private TextField profilename;

	DataManager dataManager = new DataManagerImp();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		// selectedColumn.setCellValueFactory(selectedFactory);
		selectedColumn.setCellValueFactory(new PropertyValueFactory<Transaccao, String>("select"));
		profile = (Profile) ApplicationUtils.getObject("profile");
		profilename.setText(profile.getProfilename());
		// if(t)

		codeColumn.setCellValueFactory(new PropertyValueFactory<Transaccao, Long>("code"));
		urlColumn.setCellValueFactory(new PropertyValueFactory<Transaccao, String>("url"));
		activeColumn.setCellValueFactory(new PropertyValueFactory<Transaccao, Boolean>("active"));
		transaccoesTable.setItems(FXCollections.observableArrayList(profile.getTransaccoes()));
	}

}
