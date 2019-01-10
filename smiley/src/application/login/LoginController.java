package application.login;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.models.User;
import smiley.utils.FrameManager;

public class LoginController {

	/*
	 * catch event for enter to acess Login
	 */

	@FXML
	private TextField username = new TextField();

	@FXML
	private PasswordField password = new PasswordField();

	@FXML
	private Button aceder;

	@FXML
	private Hyperlink forgotPassword;

	private int count = 0;

	private int MAXATTEMPTS = 3;

	private DataManager dataManager = new DataManagerImp();

	public FrameManager frameManagers = new FrameManager();

	public void aceder() {
		String usern = username.getText();
		String pass = password.getText();
		Alert alert = new Alert(AlertType.ERROR);
		if (usern != null && !usern.isEmpty() && pass != null && !pass.isEmpty()) {
			User user = dataManager.findUser(usern.toLowerCase());
			if (user != null) {
				boolean loginOk = false;
				try {
					loginOk = dataManager.checkPassword(user.getPassword(), pass);
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (loginOk) {
					dataManager.setSelectedUser(user);
					frameManagers.mainController2();
					Stage stage = (Stage) aceder.getScene().getWindow();
					stage.close();
				}
				else {
					alert.setContentText("Password ou username errado!");
					alert.setTitle("Erro de autenticacao");
					alert.setHeaderText(null);
					alert.showAndWait();
					if (count < MAXATTEMPTS)
						count++;
					else {
						alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Atingiu limites de tentativas de acesso");
						alert.setHeaderText(null);
						alert.setContentText("Atingiu limite de tentativas de acesso que são 3.");
						alert.showAndWait();
						aceder.setDisable(true);

					}
				}
			} else {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Usuário não encontrado");
				alert.setHeaderText(null);
				alert.setContentText("Usuário com nome : " + username.getText()
						+ " não existe. Tente remover os espaços ou colocar todos os caracteres em minúsculos");
				alert.showAndWait();
			}
		}

	}

	public void forgottenPassword() {
		// notificar a directora que este user esqueceu password
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Enviar a username");
		dialog.setHeaderText(null);
		dialog.setContentText("Insira o username que esqueceu password");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			String username = result.get();
			if (!username.isEmpty()) {
				User user = dataManager.findUser(username);
				Alert alert = new Alert(AlertType.INFORMATION);
				if (user == null) {
					alert.setTitle("Informação sobre user");
					alert.setContentText("O user com o nome : " + username + " não foi encontrado.");
					alert.setHeaderText(null);
					alert.showAndWait();
				} else {
					alert.setTitle("Informação sobre user");
					alert.setContentText(
							"Contacte ao informático ou a directora para alterar a sua palavra passe. Coloque uma palavra passe mais memorizável ;)");
					alert.setHeaderText(null);
					alert.showAndWait();
				}
			}
		}

	}
	
	public void enterKeyPressed(KeyEvent event) {
		if(event.getCode()== KeyCode.ENTER)
			aceder();
	}
}
