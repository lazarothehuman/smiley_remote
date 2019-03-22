package application.views.settings;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.models.Profile;
import smiley.utils.AlertUtils;
import smiley.utils.ApplicationUtils;
import smiley.utils.FrameManager;

public class ViewProfilesController implements Initializable {

	@FXML
	Label lblTotal;

	@FXML
	TableView<Profile> tableProfiles;

	@FXML
	AnchorPane ContentPane;

	@FXML
	TableColumn<Profile, String> nomeColumn;

	@FXML
	TableColumn<Profile, String> numeroTransacoes;

	@FXML
	TableColumn<Profile, String> activeColumn;

	@FXML
	TextField nome;

	@FXML
	CheckBox actives;

	List<Profile> profilesModelList = new ArrayList<>();

	FrameManager frameManager = new FrameManager();
	DataManager dataManager = new DataManagerImp();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nomeColumn.setCellValueFactory(new PropertyValueFactory<Profile, String>("profilename"));
		numeroTransacoes.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Profile, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Profile, String> param) {
						return new SimpleStringProperty(param.getValue().getTransaccoes().size() + "");
					}
				});
		activeColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Profile, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Profile, String> param) {
						if(param.getValue().isActive())
							return new SimpleStringProperty("Sim");
						else
							return new SimpleStringProperty("Não");

					}
				});

	}

	public void pesquisar() {
		String selectedNome = nome.getText();
		profilesModelList = dataManager.findProfiles(selectedNome, !actives.isSelected());
		if (profilesModelList != null)
			refreshItems();
		else
			AlertUtils.pesquisaVazia();

	}

	public void addProfile() {
		AnchorPane content = frameManager.addProfile();
		if (content != null) {
			setContent(content);
		}

	}

	public void modify() {
		Profile selectedProfile = null;
		selectedProfile = tableProfiles.getSelectionModel().getSelectedItem();
		if (selectedProfile != null) {
			ApplicationUtils.add("profile", selectedProfile);
			AnchorPane content = frameManager.modifyProfile();
			if (content != null)
				setContent(content);
		}

	}

	public void remover() {
		Profile selectedProfile = null;
		selectedProfile = tableProfiles.getSelectionModel().getSelectedItem();
		if (selectedProfile != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação de remoção");
			alert.setHeaderText(null);
			alert.setContentText("Tem certeza que deseja remover o profile?" + selectedProfile.getProfilename());
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				selectedProfile.setActive(false);
				profilesModelList.remove(selectedProfile);
				dataManager.updateProfile(selectedProfile);
				refreshItems();
			}
		}
	}

	private void refreshItems() {
		if (profilesModelList != null) {
			tableProfiles.setItems(FXCollections.observableArrayList(profilesModelList));
			lblTotal.setText(profilesModelList.size() + "");
		}

	}

	public void setContent(AnchorPane content) {
		if (content != null) {
			ContentPane.setTopAnchor(content, 0.0);
			ContentPane.setLeftAnchor(content, 0.0);
			ContentPane.setBottomAnchor(content, 0.0);
			ContentPane.setRightAnchor(content, 0.0);
			ContentPane.getChildren().setAll(content);
		}
	}
}
