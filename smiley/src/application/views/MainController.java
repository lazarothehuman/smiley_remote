package application.views;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.models.Cliente;
import smiley.models.Profile;
import smiley.models.User;
import smiley.utils.AlertUtils;
import smiley.utils.FrameManager;
import smiley.utils.SessionHelper;

public class MainController  {

	/**
	 * Este e' o maincontroller. Fase 1- 08/03, falta lblConsultasDiarias e
	 * lblConsultasSemanais
	 */

	@FXML
	Pane mainTab;

	@FXML
	Label lblGreetings = new Label();
	@FXML
	Label lblWeekConsultas = new Label();
	@FXML
	Label lblDailyConsultas = new Label();
	@FXML
	Label lblRegistro = new Label();
	@FXML
	Label lblProcura = new Label();
	@FXML
	Label lblRelatorios = new Label();
	@FXML
	Label lblMarcacao = new Label();
	@FXML
	Label lblLogout = new Label();
	@FXML
	Label selectedLabel = new Label();
	@FXML
	Label lblUser;
	@FXML
	Label lblProfile;
	@FXML
	Label devMessage;

	@FXML
	TableView<Cliente> listClients = new TableView<>();

	@FXML
	TableColumn<Cliente, String> nomeCliente;
	@FXML
	TableColumn<Cliente, String> dataNascimento;

	@FXML
	ImageView add;
	@FXML
	ImageView closing;
	
	@FXML
	BorderPane mainStage;

	FrameManager frameManagers = new FrameManager();

	DataManager dataManager = new DataManagerImp();

	User user = dataManager.findCurrentUser();
/*
	public void addClient() {
		if (user != null)
			frameManagers.addClient(user);
		else
			AlertUtils.alertSemPrivelegio();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//mainStage.setCenter(value);
		Date date = new Date();
		nomeCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
		dataNascimento.setCellValueFactory(new PropertyValueFactory<Cliente, String>("dataNascimento"));
		putEventsOnImages();
		devMessage.setText(version());

		@SuppressWarnings("deprecation")
		int hora = date.getHours();
		switch (hora) {
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
			lblGreetings.setText("Bom dia!");
			break;
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
			lblGreetings.setText("Boa tarde!");
			break;
		default:
			lblGreetings.setText("Boa noite!");
		}

		lblDailyConsultas.setText("");// still null porque ainda nao tem consultas
		lblWeekConsultas.setText("");// still null porque ainda nao tem consultas
		selectedLabel = lblRegistro;
		List<Cliente> clientes = dataManager.findClientesAniversarientes(new Date(), true);
		user = dataManager.findCurrentUser();
		if (user != null) {
			lblUser.setText(user.getName().toLowerCase());
			lblProfile.setText(user.getProfile().getProfilename());
		}
		if (clientes != null)
			listClients.setItems(FXCollections.observableList(clientes));

	}

	private void putEventsOnImages() {

		mainTab.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mainTab.setStyle("-fx-background-color:#fbb64f;");
			}
		});

		mainTab.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mainTab.setStyle("-fx-background-color:#ea9400;");

			}
		});
		
	}

	public void logout() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmação de logout");
		alert.setHeaderText(null);
		alert.setContentText("Tem certeza que deseja terminar a sessão?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			frameManagers.login();
			// close all windows
			Stage stage = (Stage) lblLogout.getScene().getWindow();
			stage.close();
		} else {

		}

	}

	public void unavailable() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Função não disponível");
		alert.setContentText("A função que está a tentar executar ainda não está disponível mas brevemente estará!");
		alert.setHeaderText(null);
		alert.showAndWait();
		//clearStyles();
	}

	public void marcar() {
		// unavailable
		unavailable();
	}

	public void selectUsuario() {
		if (user != null) {
			if (user.getProfile().getId() != 3) {
				if (selectedLabel == lblRegistro)
					addUsuario();
				else {
					if (selectedLabel == lblProcura)
						procura("usuario");
				}
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Acção bloqueada");
				alert.setContentText("A operação que tenta executar não é permitida");
				alert.setHeaderText(null);
				alert.showAndWait();
			}
		}

	}

	private void procura(String model) {
		switch (model) {
		case "medico":
			frameManagers.searchMedico();
			break;
		case "cliente":
			frameManagers.searchCliente();
			break;
		case "usuario":
			frameManagers.searchUsuario();
			break;
		}

	}

	public void addUsuario() {
		if (user != null)
			frameManagers.addUser(user);
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Acção bloqueada");
			alert.setContentText("A operação que tenta executar não é permitida");
			alert.setHeaderText(null);
			alert.showAndWait();
		}
	}

	public void selectMedico() {
		if (selectedLabel == lblRegistro)
			addMedico();
		else {
			if (selectedLabel == lblProcura)
				procura("medico");
		}
	}

	public void addMedico() {
		frameManagers.addMedico(user);
	}

	public void addProcedimento() {
		frameManagers.addProcedimento(user);
	}

	public void addConsulta() {
		frameManagers.addConsulta(user);
	}

	public void selectCliente() {
		if (selectedLabel == lblRegistro)
			frameManagers.addClient(user);
		else {
			if (selectedLabel == lblProcura)
				procura("cliente");
		}
	}

	public void doubleClickOnBirthDayClient(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Cliente cliente = listClients.getSelectionModel().getSelectedItem();
			String telefone = "";
			String email = "";
			if (cliente.getTelefone() == null)
				telefone = "Sem registro";
			else
				telefone = cliente.getTelefone();
			if (cliente.getEmail() == null)
				email = "Sem registro";
			else
				email = cliente.getEmail();
			if (cliente != null) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Informação sobre cliente");
				alert.setHeaderText(null);
				alert.setContentText("Nome: " + cliente.getNome() + "\nData de nascimento: "
						+ cliente.getDataNascimento().toString() + "\nTelefone: " + telefone + "\nE-mail: " + email);
				alert.showAndWait();
			}
		}

	}

	public void clickOnUserInfo() {
		if (user != null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Informação sobre usuário corrente");
			alert.setHeaderText(null);
			alert.setContentText("Nome: " + user.getName() + "\nUsername: " + user.getUsername() + "\nProfile: "
					+ user.getProfile().toString());
			alert.showAndWait();
		}
	}
	
	public void modificarPerfil() {
		List<Profile> profiles = dataManager.findProfiles(true);
		ChoiceDialog<Profile> dialog = new ChoiceDialog<>(profiles.get(0), profiles);
		dialog.setTitle("Choice Dialog");
		dialog.setHeaderText("Look, a Choice Dialog");
		dialog.setContentText("Choose your letter:");

		// Traditional way to get the response value.
		Optional<Profile> result = dialog.showAndWait();
		if (result.isPresent()){
		    if(result.get()!=null) {
		    	frameManagers.modifyProfile(result.get(), user);
		    }
		}
	}

	public String version() {
		return "Smiley, produzido pela Human's solutions, versão 1.0";
	}
}*/
}
