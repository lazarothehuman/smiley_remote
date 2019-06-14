package application.views;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
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
import smiley.models.Cliente;
import smiley.models.Sexo;
import smiley.models.User;
import smiley.utils.FrameManager;

public class ViewClientsController implements Initializable {

	List<Cliente> clientes;

	@FXML
	Button pesquisar;

	@FXML
	Button adicionarCliente;

	@FXML
	Button modificarCliente;

	@FXML
	Button removerCliente;

	@FXML
	TextField telefoneTf = new TextField();

	@FXML
	TextField emailTf = new TextField();

	@FXML
	TextField nomeTf = new TextField();

	@FXML
	ComboBox<Sexo> sexoCombo;

	@FXML
	CheckBox active;

	@FXML
	DatePicker dataInicio;

	@FXML
	DatePicker dataFim;

	FrameManager frameManagers = new FrameManager();

	@FXML
	TableView<Cliente> tableCliente;

	@FXML
	TableColumn<Cliente, String> nomeColumn;

	@FXML
	TableColumn<Cliente, String> emailColumn;

	@FXML
	TableColumn<Cliente, String> telefoneColumn;

	@FXML
	TableColumn<Cliente, String> dataColumn;

	@FXML
	TableColumn<Cliente, String> sexoColumn;

	@FXML
	TableColumn<Cliente, String> naturalidadeColumn;
	
	@FXML
	TableColumn<Cliente, String> moradaColumn;
	

	@FXML
	Label lblUser = new Label();

	@FXML
	Label lblProfile = new Label();

	@FXML
	Label lblTotal = new Label();

	@FXML
	Hyperlink home;

	@FXML
	Hyperlink about;

	DataManager dataManager = new DataManagerImp();

	FrameManager frameManager = new FrameManager();
	
	User user = dataManager.findCurrentUser();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sexoCombo.setItems(FXCollections.observableArrayList(Sexo.values()));
		User user = dataManager.findCurrentUser();
		if (user != null) {
			lblUser.setText(user.getName().toLowerCase());
			lblProfile.setText(user.getProfile().getProfilename());
		}
		nomeColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("email"));
		telefoneColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefone"));
		dataColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("dataNascimento"));
		sexoColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("sexo"));
		naturalidadeColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("naturalidade"));
		moradaColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("morada"));
	}

	public void pesquisar() {
		String nome = nomeTf.getText();
		String email = emailTf.getText();
		String telefone = telefoneTf.getText();
		Sexo sexo = sexoCombo.getValue();
		Boolean activee = true;
		if (active.isSelected())
			activee = false;
		LocalDate localDateFim = dataFim.getValue();
		LocalDate localDate = dataInicio.getValue();
		Date selectedStartDate = null;
		Date selectedEndDate = null;
		if (localDate != null) {
			Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			selectedStartDate = Date.from(instant);
		}
		if (localDateFim != null) {
			Instant instant = Instant.from(localDateFim.atStartOfDay(ZoneId.systemDefault()));
			selectedEndDate = Date.from(instant);
		}

		clientes = dataManager.findClientes(null, nome, email, telefone, selectedStartDate, selectedEndDate, sexo,
				activee);
		if (clientes != null) {
			tableCliente.setItems(FXCollections.observableArrayList(clientes));
		}
		lblTotal.setText(clientes.size() + "");
	}

	public void addCliente() {
		frameManager.addClient(dataManager.findCurrentUser());
	}

	public void modificarCliente() {
		Cliente cliente = null;
		cliente = tableCliente.getSelectionModel().getSelectedItem();
		if (cliente != null && user!=null) {
			//frameManager.modifyCliente(cliente, user);
			System.out.println();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Operação inválida");
			alert.setContentText("Para modificar um cliente é necessário selecionar um!");
			alert.setHeaderText(null);
			alert.showAndWait();
		}

	}

	public void removerCliente() {
		Cliente selectedCliente = null;
		selectedCliente = tableCliente.getSelectionModel().getSelectedItem();
		if (selectedCliente != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação de remoção");
			alert.setHeaderText(null);
			alert.setContentText("Tem certeza que deseja remover o cliente?" + selectedCliente.getNome());
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				selectedCliente.setActive(false);
				dataManager.updateCliente(selectedCliente);
				refreshItems();
			}
		}
	}

	private void refreshItems() {
		// TODO Auto-generated method stub
	}

	public void goHome() {
		Stage stage = (Stage) adicionarCliente.getScene().getWindow();
		stage.close();
	}

	public void about() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Sobre esta janela");
		alert.setContentText("Esta janela tem objectivo de ajudar a visualizar todos "
				+ "os clientes gravados no sistema. Do lado direito da tela vais encontrar um conjunto de filtros, "
				+ "preencha os para uma busca mais refinada. Para sair desta tela, apenas prima voltar ou "
				+ "no canto superior direito para tirar a janela. Nesta janela também é possível adicionar, modificar e remover um cliente. "
				+ "Para voltar para janela principal, é necessário clicar no  HOME ou no x no canto superior a direita");
		alert.setHeaderText(null);
		alert.showAndWait();
	}

	public void doubleClickOnClient(MouseEvent event) {
		if (event.getClickCount() == 2) {
			modificarCliente();
		}
	}
	
	public void enterKeyPressed(KeyEvent event) {
		if(event.getCode()== KeyCode.ENTER)
			pesquisar();
	}

}