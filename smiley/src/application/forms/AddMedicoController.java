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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import smiley.exception.DuplicateEntryException;
import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.models.Medico;
import smiley.models.Sexo;
import smiley.utils.Validations;

public class AddMedicoController implements Initializable {

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
	private Label nome_lbl = new Label();

	@FXML
	private Label sexo_lbl = new Label();

	@FXML
	private Label data_lbl = new Label();

	@FXML
	private ComboBox<Sexo> comboSex;

	DataManager dataManager = new DataManagerImp();

	public void add() {
		Alert alert;
		Date birthDate = null;
		String nome = nameTF.getText();
		String email = emailTF.getText();
		String telefone = phoneTF.getText();
		Sexo sexo = comboSex.getSelectionModel().getSelectedItem();
		LocalDate localDate = dateNasc.getValue();
		if (localDate != null) {
			Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			birthDate = Date.from(instant);
		}
		if (nome != null && birthDate != null && sexo != null) {
			Medico medico = new Medico();
			medico.setName(nome);
			medico.setDataNascimento(birthDate);
			medico.setSexo(sexo);
			if (email != null && !email.isEmpty()) {
				boolean valid = Validations.isValidForEmailNotification(email);
				if (valid) {
					medico.setEmail(email);
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
					medico.setTelefone(telefone);
				} else {
					alert = new Alert(AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("O numero de telefone digitado não é válido!");
					alert.setTitle("Número de telefone errado");
					alert.showAndWait();
				}
			}
			try {
				dataManager.createMedico(medico);
			} catch (DuplicateEntryException a) {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erro na inserção do médico");
				alert.setHeaderText(null);
				alert.setContentText("Já existe esse médico");
				alert.showAndWait();
			}
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Adicionado com sucesso");
			alert.setHeaderText(null);
			alert.setContentText("O medico " + nome + " foi adicionado com sucesso");
			alert.showAndWait();
			Stage stage = (Stage) add.getScene().getWindow();
			stage.close();
		} else {
			alert = new Alert(AlertType.ERROR);
			alert.setContentText("Campos principais não preenchidos, impossível adicionar um novo medico");
			alert.setHeaderText(null);
			alert.setTitle("Dados incompletos");
			alert.showAndWait();
			// alert.setTitle("");
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
