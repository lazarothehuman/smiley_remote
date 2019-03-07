package application.forms;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.models.Profile;
import smiley.models.User;
import smiley.utils.AlertUtils;
import smiley.utils.ApplicationUtils;

public class ModifyUserController implements Initializable {

	User user;

	@FXML
	ComboBox<Profile> comboPerfil;

	@FXML
	TextField nomeTf = new TextField();

	@FXML
	TextField usernameTf = new TextField();

	@FXML
	TextField passwordTf = new TextField();

	@FXML
	TextField repeatPasswordTf = new TextField();

	@FXML
	CheckBox isActive = new CheckBox();

	@FXML
	ImageView add;
	

	DataManager dataManager = new DataManagerImp();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		user = (User) ApplicationUtils.getObject("selectedUser");
		comboPerfil.setItems(FXCollections.observableArrayList(dataManager.findProfiles(Boolean.TRUE)));
		setValues();
	}

	

	public void setValues() {
		if (this.user != null) {
			nomeTf.setText(user.getName());
			usernameTf.setText(user.getUsername());
			comboPerfil.setValue(user.getProfile());
			isActive.setSelected(user.isActive());
		}
	}

	public void modify() {// fazer controle de permissoes
		Alert alert;
		String nome = nomeTf.getText();
		String username = usernameTf.getText();
		String password = passwordTf.getText();
		String repeat = repeatPasswordTf.getText();
		Profile profile = comboPerfil.getSelectionModel().getSelectedItem();
			if (nome != null && username != null && password != null && repeat != null && profile != null) {
				if (password.equals(repeat)) {
					this.user.setActive(true);
					this.user.setName(nome);
					this.user.setProfile(profile);
					if (isActive.isSelected()) {
						this.user.setUsername(username);
						this.user.setPassword(password);
					}
					try {
						dataManager.updateUser(this.user);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (GeneralSecurityException e) {
						e.printStackTrace();
					}
					AlertUtils.alertSucesso("User modificado com sucesso");
				} else {
					// alert que passwords tao diferentes
					alert = new Alert(AlertType.ERROR);
					alert.setContentText(
							"Campos principais não ou mal preenchidos, impossível modificar o usuário " + this.user.getName());
					alert.showAndWait();
				}

			} else {
				AlertUtils.alertErroSelecionar("Campos principais não preenchidos, impossível modificar o usuário " + this.user.getName());
				// alert.setTitle("");
			}
		
	}

	public void selectActive() {
		if (isActive.isSelected()) {
			passwordTf.setDisable(false);
			repeatPasswordTf.setDisable(false);
		} else {
			passwordTf.setDisable(true);
			repeatPasswordTf.setDisable(true);
		}
	}

}
