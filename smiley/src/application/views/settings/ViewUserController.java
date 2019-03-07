package application.views.settings;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.models.Profile;
import smiley.models.User;
import smiley.utils.AlertUtils;
import smiley.utils.ApplicationUtils;
import smiley.utils.FrameManager;

public class ViewUserController implements Initializable {

	@FXML
	Button pesquisar;

	@FXML
	Button adicionarUser;

	@FXML
	Button modificarUser;

	@FXML
	Button removerUser;

	@FXML
	TextField usernameTf = new TextField();

	@FXML
	TextField nomeTf = new TextField();

	@FXML
	ComboBox<String> comboProfile;

	@FXML
	CheckBox active = new CheckBox();

	@FXML
	TableView<User> tableUser;

	@FXML
	TableColumn<User, String> nomeColumn;

	@FXML
	TableColumn<User, String> usernameColumn;

	@FXML
	TableColumn<User, String> profileColumn;

	@FXML
	TableColumn<User, Boolean> activeColumn;

	@FXML
	Label lblUser = new Label();

	@FXML
	Label lblProfile = new Label();

	@FXML
	Label lblTotal = new Label();

	DataManager dataManager = new DataManagerImp();

	FrameManager frameManager = new FrameManager();

	@FXML
	Hyperlink home;

	@FXML
	Hyperlink about;

	@FXML
	AnchorPane ContentPane;

	User user;

	List<User> listUsers = new ArrayList<User>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Profile> profiles = dataManager.findProfiles(true);
		List<String> strings = new ArrayList<>();
		for (Profile profile : profiles) {
			strings.add(profile.getProfilename());
		}
		comboProfile.setItems(FXCollections.observableArrayList(strings));
		user = dataManager.findCurrentUser();
		nomeColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
		usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		profileColumn.setCellValueFactory(new PropertyValueFactory<User, String>("profile"));
		activeColumn.setCellValueFactory(new PropertyValueFactory<User, Boolean>("active"));
	}

	public void pesquisar() {
		String name = nomeTf.getText();
		String username = usernameTf.getText();
		String string = comboProfile.getValue();
		Profile profile = dataManager.findProfile(string);
		Boolean active = true;
		if (this.active.isSelected())
			active = false;

		listUsers = dataManager.findUsers(name, username, profile, active);
		if (listUsers != null) {
			tableUser.setItems(FXCollections.observableArrayList(listUsers));
			lblTotal.setText(listUsers.size() + "");
		}

	}

	public void addUser() {// fazer controle de permissoes
		User user = dataManager.findCurrentUser();
		if (user != null) {
			AnchorPane content = frameManager.addUser();
			setContent(content);
		}

	}

	public void modifyUser() {// fazer controle de permissoes

		User selectedUser = null;
		selectedUser = tableUser.getSelectionModel().getSelectedItem();

		if (selectedUser != null) {
			ApplicationUtils.add("selectedUser", selectedUser);
			AnchorPane pane = frameManager.modifyUser();
			setContent(pane);
		} else
			AlertUtils.alertErroSelecionar("Para modificar um usuario é necessário selecionar um!");

	}

	public void removerUser() throws UnsupportedEncodingException, GeneralSecurityException {
		if (user != null) {
			if (user.getProfile().getId() == 1l || user.getProfile().getId() == 2l) {
				User selectedUser = null;
				selectedUser = tableUser.getSelectionModel().getSelectedItem();
				if (selectedUser != null) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmação de remoção");
					alert.setHeaderText(null);
					alert.setContentText("Tem certeza que deseja remover o usuario ?" + selectedUser.getName());

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						selectedUser.setActive(false);
						dataManager.updateUser(selectedUser);
						listUsers.remove(selectedUser);
						refreshItems();
					}

				}
			} else
				AlertUtils.alertSemPrivelegio();
		}

	}

	private void refreshItems() {
		if (listUsers != null)
			tableUser.setItems(FXCollections.observableArrayList(listUsers));

	}

	public void doubleClickOnUser(MouseEvent event) {
		if (event.getClickCount() == 2) {
			modifyUser();
		}
	}

	public void enterKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER)
			pesquisar();
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
