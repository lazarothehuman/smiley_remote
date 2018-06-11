package application.views;

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
import javafx.stage.Stage;
import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.models.Profile;
import smiley.models.User;
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

	List<User> listUsers = new ArrayList<User>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Profile> profiles = dataManager.findProfiles(true);
		List<String> strings = new ArrayList<>();
		for (Profile profile : profiles) {
			strings.add(profile.getProfilename());
		}
		comboProfile.setItems(FXCollections.observableArrayList(strings));
		User user = dataManager.findCurrentUser();
		if (user != null) {
			lblUser.setText(user.getName().toLowerCase());
			lblProfile.setText(user.getProfile().getProfilename());
		}
		nomeColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
		usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		profileColumn.setCellValueFactory(new PropertyValueFactory<User, String>("profile"));
		activeColumn.setCellValueFactory(new PropertyValueFactory<User, Boolean>("active"));
	}

	public void pesquisar() {
		String name = nomeTf.getText();
		String username = usernameTf.getText();
		// Profile profile = comboProfile.getValue();
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
			if (user.getProfile().getId() == 1l || user.getProfile().getId() == 2l)
				frameManager.addUser();
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Erro de permissão");
				alert.setContentText("O usuário corrente não tem permissão para adicionar a tarefa em causa");
				alert.showAndWait();
			}

		}
	}

	public void modifyUser() {// fazer controle de permissoes

		User selectedUser = null;
		selectedUser = tableUser.getSelectionModel().getSelectedItem();

		User user = dataManager.findCurrentUser();
		if (user != null) {
			if (user.getProfile().getId() == 1l || user.getProfile().getId() == 2l) {
				if (selectedUser != null)
					frameManager.modifyUser(selectedUser);
				else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Operação inválida");
					alert.setContentText("Para modificar um usuario é necessário selecionar um!");
					alert.setHeaderText(null);
					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Erro de permissão");
				alert.setContentText("O usuário corrente não tem permissão para adicionar a tarefa em causa");
				alert.showAndWait();
			}

		}
	}

	public void removerUser() throws UnsupportedEncodingException, GeneralSecurityException {
		User user = dataManager.findCurrentUser();
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
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Erro de permissão");
				alert.setContentText("O usuário corrente não tem permissão para adicionar a tarefa em causa");
				alert.showAndWait();

			}

		}

	}

	private void refreshItems() {
		if (listUsers != null)
			tableUser.setItems(FXCollections.observableArrayList(listUsers));

	}

	public void goHome() {
		Stage stage = (Stage) adicionarUser.getScene().getWindow();
		stage.close();
	}

	public void about() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Sobre esta janela");
		alert.setContentText("Esta janela tem objectivo de ajudar a visualizar todos "
				+ "os usuários gravados no sistema. Do lado direito da tela vais encontrar um conjunto de filtros, "
				+ "preencha os para uma busca mais refinada. Para sair desta tela, apenas prima voltar ou "
				+ "no canto superior direito para tirar a janela. Nesta janela também é possível adicionar, modificar e remover um usuário. "
				+ "Para voltar para janela principal, é necessário clicar no  HOME ou no x no canto superior a direita");
		alert.setHeaderText(null);
		alert.showAndWait();
	}
	
	public void doubleClickOnUser(MouseEvent event) {
		if (event.getClickCount() == 2) {
			modifyUser();
		}
	}
	
	public void enterKeyPressed(KeyEvent event) {
		if(event.getCode()== KeyCode.ENTER)
			pesquisar();
	}

}
