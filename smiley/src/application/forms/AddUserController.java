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
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import smiley.exception.DuplicateEntryException;
import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.models.Profile;
import smiley.models.User;

public class AddUserController implements Initializable {

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
	ImageView add;

	DataManager dataManager = new DataManagerImp();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		comboPerfil.setItems(FXCollections.observableArrayList(dataManager.findProfiles(Boolean.TRUE)));
	}

	public void add() {
		Alert alert;
		String nome = nomeTf.getText();
		String username = usernameTf.getText();
		String password = passwordTf.getText();
		String repeat = repeatPasswordTf.getText();
		Profile profile = comboPerfil.getSelectionModel().getSelectedItem();
		boolean isUnique = true;

		if (nome != null && username != null && password != null && repeat != null && profile != null) {
			username = username.trim();
			username = username.toLowerCase();
			User usuario = dataManager.findUser(username);
			if (usuario != null)
				isUnique = false;
			if (password.equals(repeat) && isUnique == true) {
				User user = new User();
				user.setActive(true);
				user.setName(nome);
				user.setProfile(profile);
				user.setUsername(username);
				user.setPassword(password);
				try {
					dataManager.createUser(user);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
				} catch (DuplicateEntryException a) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Erro na inserção do usuário");
					alert.setHeaderText(null);
					alert.setContentText("Já existe esse usuário");
					alert.showAndWait();
				}
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Adicionado com sucesso");
				alert.setHeaderText(null);
				alert.setContentText("O usuário " + nome + " foi adicionado com sucesso");
				alert.showAndWait();
				Stage stage = (Stage) add.getScene().getWindow();
				stage.close();
			} else {
				String message = "";
				if (!isUnique) {
					message = "Já existe um usuário com esse username";
					usernameTf.setStyle("-fx-border-color:#ff0000;");
				} else
					message = "As palavras passes inseridas são diferentes";
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERRO!");
				alert.setHeaderText(null);
				alert.setContentText(message);
				alert.showAndWait();
			}

		} else {
			alert = new Alert(AlertType.ERROR);
			alert.setContentText("Campos principais não preenchidos, impossível adicionar um novo user");
			alert.showAndWait();
			// alert.setTitle("");
		}

	}

}
