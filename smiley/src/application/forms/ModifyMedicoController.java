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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.models.Medico;
import smiley.models.Sexo;
import smiley.utils.FormatUtils;
import smiley.utils.Validations;

public class ModifyMedicoController implements Initializable {

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
	private CheckBox isActive = new CheckBox();

	DataManager dataManager = new DataManagerImp();

	Medico medico;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		comboSex.setItems(FXCollections.observableArrayList(Sexo.values()));
		
	}

	public void setMedico(Medico medico) {
		if (medico != null) {
			this.medico = medico;
			setValues();
		}
	}

	public void setValues() {
		if (medico != null) {
			nameTF.setText(this.medico.getName());
			if(this.medico.getEmail()!=null)
				emailTF.setText(this.medico.getEmail());
			if(this.medico.getTelefone()!= null)
				phoneTF.setText(this.medico.getTelefone());
			comboSex.setValue(this.medico.getSexo());
			isActive.setSelected(this.medico.getActive());
			LocalDate localDate = FormatUtils.formatDateToBox(this.medico.getDataNascimento());
			dateNasc.setValue(localDate);
		}
		
	}
	
	public void modify() {
		Alert alert;
		String nome = nameTF.getText();
		String email = emailTF.getText();
		String telefone = phoneTF.getText();
		Sexo sexo = comboSex.getSelectionModel().getSelectedItem();
		LocalDate localDate = dateNasc.getValue();
		Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		Date birthDate = Date.from(instant);

		if (nome != null && birthDate != null && sexo != null) {
			this.medico.setName(nome);
			this.medico.setDataNascimento(birthDate);
			this.medico.setSexo(sexo);
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
			if(isActive.isSelected())
				this.medico.setActive(true);
			else
				this.medico.setActive(false);
			dataManager.updateMedico(this.medico);
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Modificado com sucesso");
			alert.setHeaderText(null);
			alert.setContentText("O médico " + nome + " foi modificado com sucesso");
			alert.showAndWait();
			Stage stage = (Stage) add.getScene().getWindow();
			stage.close();
		} else {
			alert = new Alert(AlertType.ERROR);
			alert.setContentText("Campos principais não preenchidos, impossível adicionar um novo cliente");
			alert.setHeaderText(null);
			alert.showAndWait();
			// alert.setTitle("");
		}
	}

}
