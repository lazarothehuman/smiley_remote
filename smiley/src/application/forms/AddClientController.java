package application.forms;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import smiley.exception.DuplicateEntryException;
import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.models.Cliente;
import smiley.models.Encarregado;
import smiley.models.Sexo;
import smiley.utils.Validations;

public class AddClientController implements Initializable {

	/**
	 * 
	 */

	@FXML
	private ImageView add;

	@FXML
	private TextField nameTF = new TextField();

	@FXML
	private TextField emailTF = new TextField();

	@FXML
	private TextField phoneTF = new TextField();

	@FXML
	private DatePicker dateNasc = new DatePicker(LocalDate.now());

	@FXML
	private ComboBox<Sexo> comboSex;

	@FXML
	private Label nome_lbl = new Label();

	@FXML
	private Label sexo_lbl = new Label();

	@FXML
	private Label data_lbl = new Label();

	@FXML
	private TextField naturalidadeTf = new TextField();

	@FXML
	private TextField moradaTf = new TextField();

	@FXML
	private TextField notasImportantesTf = new TextField();

	@FXML
	private CheckBox encarregadoCb = new CheckBox();

	@FXML
	private TextField encarregadoNomeTf = new TextField();

	@FXML
	private TextField encarregadoTelefoneTf = new TextField();

	DataManager dataManager = new DataManagerImp();

	public void add() {
		// needs to catch duplicates
		Alert alert;
		String nome = nameTF.getText();
		String email = emailTF.getText();
		String telefone = phoneTF.getText();
		String naturalidade = naturalidadeTf.getText();
		String morada = moradaTf.getText();
		String notas = notasImportantesTf.getText();
		String nomeEncarregado = null;
		String telefoneEncarregado = null;
		Sexo sexo = comboSex.getSelectionModel().getSelectedItem();
		LocalDate localDate = dateNasc.getValue();
		boolean duplicate = false;
		Date birthDate = null;
		if (localDate != null) {
			Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			birthDate = Date.from(instant);
		}

		List<Cliente> clientes = dataManager.findClientes(null, nome, null, null, null, null, null, null);
		if (clientes != null) {
			for (Cliente cliente : clientes) {
				if (cliente.getNome().equalsIgnoreCase(nome))
					duplicate = true;
			}
		}

		if (nome != null && birthDate != null && sexo != null && !duplicate && naturalidade != null) {
			Cliente cliente = new Cliente();
			cliente.setNome(nome);
			cliente.setDataNascimento(birthDate);
			cliente.setSexo(sexo);
			cliente.setNaturalidade(naturalidade);
			if (morada != null)
				cliente.setMorada(morada);
			if (notas != null)
				cliente.setNotasImportantes(notas);
			if (email != null && !email.isEmpty()) {
				boolean valid = Validations.isValidForEmailNotification(email);
				if (valid) {
					cliente.setEmail(email);
				} else {
					alert = new Alert(AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("O email digitado não é válido!");
					alert.setTitle("Email inválido");
					alert.showAndWait();
				}
			}
			if (telefone != null && !telefone.isEmpty()) {
				boolean valid = Validations.isValidForSMSNotification(telefone);
				if (valid) {
					cliente.setTelefone(telefone);
				} else {
					alert = new Alert(AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("O numero de telefone digitado não é válido!");
					alert.setTitle("Número de telefone errado");
					alert.showAndWait();
				}
			}
			if (encarregadoCb.isSelected()) {
				nomeEncarregado = encarregadoNomeTf.getText();
				telefoneEncarregado = encarregadoTelefoneTf.getText();
				if (nomeEncarregado != null && telefoneEncarregado != null && !nomeEncarregado.isEmpty() && !telefoneEncarregado.isEmpty()) {
					boolean valid = Validations.isValidForSMSNotification(telefoneEncarregado);
					if (valid) {
						cliente.setHasEncarregado(true);
						Encarregado encarregado = new Encarregado();
						encarregado.setNome(nomeEncarregado);
						encarregado.setTelefone(telefoneEncarregado);
						encarregado.addEducando(cliente);
						cliente.setEncarregado(encarregado);
						try {
							dataManager.createCliente(cliente);
						} catch (DuplicateEntryException a) {
							alert = new Alert(AlertType.ERROR);
							alert.setTitle("Erro na inserção do cliente");
							alert.setHeaderText(null);
							alert.setContentText("Já existe esse cliente");
							alert.showAndWait();
						}
						alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Adicionado com sucesso");
						alert.setHeaderText(null);
						alert.setContentText("O cliente " + nome + " foi adicionado com sucesso");
						alert.showAndWait();
						Stage stage = (Stage) add.getScene().getWindow();
						stage.close();

					} else {
						alert = new Alert(AlertType.ERROR);
						alert.setHeaderText(null);
						alert.setContentText("Informações do encarregado inválidas!");
						alert.setTitle("Informações inválidas");
						alert.showAndWait();
					}
				}else {
					alert = new Alert(AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("Informações do encarregado inválidas!");
					alert.setTitle("Informações inválidas");
					alert.showAndWait();
				}
			} else {
				try {
				dataManager.createCliente(cliente);
				} catch (DuplicateEntryException a) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Erro na inserção do cliente");
					alert.setHeaderText(null);
					alert.setContentText("Já existe esse cliente");
					alert.showAndWait();
				}
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Adicionado com sucesso");
				alert.setHeaderText(null);
				alert.setContentText("O cliente " + nome + " foi adicionado com sucesso");
				alert.showAndWait();
				Stage stage = (Stage) add.getScene().getWindow();
				stage.close();
			}
		} else {
			alert = new Alert(AlertType.ERROR);
			alert.setContentText("Campos principais não preenchidos, impossível adicionar um novo cliente");
			alert.setHeaderText(null);
			alert.showAndWait();
			// alert.setTitle("");
		}

	}

	public void selectCheckBox() {
		if (encarregadoCb.isSelected()) {
			encarregadoNomeTf.setDisable(false);
			encarregadoTelefoneTf.setDisable(false);
		} else {
			encarregadoNomeTf.setDisable(true);
			encarregadoTelefoneTf.setDisable(true);
			encarregadoTelefoneTf.clear();
			encarregadoNomeTf.clear();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		comboSex.setItems(FXCollections.observableArrayList(Sexo.values()));
		Tooltip tool = new Tooltip("Campo não pode estar vazio");
		tool.setStyle("-fx-background-color: #FF0000;");
		nome_lbl.setTooltip(tool);
		sexo_lbl.setTooltip(tool);
		data_lbl.setTooltip(tool);

	}

}
