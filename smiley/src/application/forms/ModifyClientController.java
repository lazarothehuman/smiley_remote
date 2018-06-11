package application.forms;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.models.Cliente;
import smiley.models.Encarregado;
import smiley.models.Sexo;
import smiley.utils.FormatUtils;
import smiley.utils.Validations;

public class ModifyClientController implements Initializable {

	/**
	
	 */

	private Cliente cliente;

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

	@FXML
	private CheckBox isActive = new CheckBox();

	DataManager dataManager = new DataManagerImp();

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		setValues();
	}

	public void setValues() {

		if (cliente != null) {
			String nome = cliente.getNome();
			String naturalidade = cliente.getNaturalidade();
			String morada = cliente.getMorada();
			String telefone = cliente.getTelefone();
			String email = cliente.getEmail();
			Sexo sexo = cliente.getSexo();
			Date dataNascimento = cliente.getDataNascimento();
			String notasImportantes = cliente.getNotasImportantes();
			Encarregado encarregado = cliente.getEncarregado();
			Boolean active = cliente.isActive();

			nameTF.setText(nome);
			if (naturalidade != null)
				naturalidadeTf.setText(naturalidade);
			if (morada != null)
				moradaTf.setText(morada);
			if (telefone != null)
				phoneTF.setText(telefone);
			if (email != null)
				emailTF.setText(email);
			if (notasImportantes != null)
				notasImportantesTf.setText(notasImportantes);
			if (encarregado != null) {
				encarregadoCb.setSelected(true);
				selectCheckBox();
				encarregadoNomeTf.setText(encarregado.getNome());
				encarregadoTelefoneTf.setText(encarregado.getTelefone());
			}

			comboSex.setValue(sexo);
			isActive.setSelected(active);
			LocalDate localDate = FormatUtils.formatDateToBox(dataNascimento);
			dateNasc.setValue(localDate);

		} else
			System.out.println("Its null");
	}

	public void modify() {
		Alert alert;
		Encarregado encarregado = null;
		Date birthDate = null;
		String nome = nameTF.getText();
		String email = emailTF.getText();
		String telefone = phoneTF.getText();
		String morada = moradaTf.getText();
		String naturalidade = naturalidadeTf.getText();
		String notasImportantes = notasImportantesTf.getText();
		if (encarregadoCb.isSelected()) {
			if (!cliente.getHasEncarregado()) {
				encarregado = new Encarregado();
			} else {
				encarregado = cliente.getEncarregado();
			}
		}
		Sexo sexo = comboSex.getSelectionModel().getSelectedItem();
		LocalDate localDate = dateNasc.getValue();
		if (localDate != null) {
			Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			birthDate = Date.from(instant);
		}
		if (nome != null && birthDate != null && sexo != null && naturalidade != null) {
			this.cliente.setNome(nome);
			this.cliente.setNaturalidade(naturalidade);
			if (morada != null && !morada.isEmpty())
				this.cliente.setMorada(morada);
			if (notasImportantes != null && !notasImportantes.isEmpty())
				this.cliente.setNotasImportantes(notasImportantes);
			this.cliente.setDataNascimento(birthDate);
			this.cliente.setSexo(sexo);
			if (email != null && !email.isEmpty()) {
				boolean valid = Validations.isValidForEmailNotification(email);
				if (valid) {
					this.cliente.setEmail(email);
				} else {
					alert = new Alert(AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("O email digitado não é válido!");
					alert.setTitle("Email errado");
					alert.showAndWait();
				}
			}
			if (telefone != null && !telefone.isEmpty()) {
				boolean valid = Validations.isValidForSMSNotification(telefone);
				if (valid) {
					this.cliente.setTelefone(telefone);
				} else {
					alert = new Alert(AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("O numero de telefone digitado não é válido!");
					alert.setTitle("Número de telefone errado");
					alert.showAndWait();
				}
			}
			if (isActive.isSelected())
				this.cliente.setActive(true);
			else
				this.cliente.setActive(false);
			if (encarregadoCb.isSelected()) {
				String nomeEncarregado = encarregadoNomeTf.getText();
				String telefoneEncarregado = encarregadoTelefoneTf.getText();
				if (nomeEncarregado != null && telefoneEncarregado != null && !nomeEncarregado.isEmpty() && !telefoneEncarregado.isEmpty()) {
					encarregado.setTelefone(telefoneEncarregado);
					encarregado.setNome(nomeEncarregado);
					encarregado.addEducando(cliente);
					this.cliente.setEncarregado(encarregado);
					this.cliente.setHasEncarregado(true);
					dataManager.updateCliente(this.cliente);
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Modificado com sucesso");
					alert.setHeaderText(null);
					alert.setContentText("O cliente " + nome + " foi modificado com sucesso");
					alert.showAndWait();
					Stage stage = (Stage) add.getScene().getWindow();
					stage.close();

				}else {
					alert = new Alert(AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("Informações do encarregado inválidas");
					alert.setTitle("Operação inválida");
					alert.showAndWait();
				}
			} else {
				this.cliente.setHasEncarregado(false);
				this.cliente.setEncarregado(null);
				dataManager.updateCliente(this.cliente);
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Modificado com sucesso");
				alert.setHeaderText(null);
				alert.setContentText("O cliente " + nome + " foi modificado com sucesso");
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
	}

}
