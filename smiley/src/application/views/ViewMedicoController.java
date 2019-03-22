package application.views;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.models.Medico;
import smiley.models.Sexo;
import smiley.models.User;
import smiley.utils.AlertUtils;
import smiley.utils.ApplicationUtils;
import smiley.utils.FrameManager;

public class ViewMedicoController implements Initializable {

	@FXML
	AnchorPane ContentPane;

	@FXML
	Button pesquisar;

	@FXML
	Button adicionarMedico;

	@FXML
	Button modificarMedico;

	@FXML
	Button removerMedico;

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

	@FXML
	TableView<Medico> tableMedico;

	@FXML
	TableColumn<Medico, String> nomeColumn;

	@FXML
	TableColumn<Medico, String> emailColumn;

	@FXML
	TableColumn<Medico, String> telefoneColumn;

	@FXML
	TableColumn<Medico, String> sexoColumn;

	@FXML
	Label lblTotal = new Label();

	DataManager dataManager = new DataManagerImp();

	List<Medico> medicos = new ArrayList<>();

	FrameManager frameManager = new FrameManager();

	User user = dataManager.findCurrentUser();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sexoCombo.setItems(FXCollections.observableArrayList(Sexo.values()));
		nomeColumn.setCellValueFactory(new PropertyValueFactory<Medico, String>("name"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<Medico, String>("email"));
		telefoneColumn.setCellValueFactory(new PropertyValueFactory<Medico, String>("telefone"));
		sexoColumn.setCellValueFactory(new PropertyValueFactory<Medico, String>("sexo"));
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

		medicos = dataManager.findMedicos(null, nome, email, telefone, selectedStartDate, selectedEndDate, sexo,
				activee);
		if (medicos != null) {
			tableMedico.setItems(FXCollections.observableArrayList(medicos));
		}
		System.out.println(medicos.toString());
		lblTotal.setText(medicos.size() + "");
	}

	public void addMedico() {
		AnchorPane content = frameManager.addMedico();
		setContent(content);
	}

	public void modificarMedico() {
		Medico medico = null;
		medico = tableMedico.getSelectionModel().getSelectedItem();
		if (medico != null) {
			ApplicationUtils.add("medico", medico);
			setContent(frameManager.modifyMedico());
		} else
			AlertUtils.alertErroSelecionar("Para modificar um medico é necessário selecionar um!");

	}

	public void removerMedico() {
		Medico selectedMedico = null;
		selectedMedico = tableMedico.getSelectionModel().getSelectedItem();
		if (selectedMedico != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação de remoção");
			alert.setHeaderText(null);
			alert.setContentText("Tem certeza que deseja remover o medico?" + selectedMedico.getName());

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				selectedMedico.setActive(false);
				medicos.remove(selectedMedico);
				dataManager.updateMedico(selectedMedico);
				refreshItems();
			}
		}

	}

	private void refreshItems() {
		tableMedico.setItems(FXCollections.observableArrayList(medicos));
	}

	public void doubleClickOnMedico(MouseEvent event) {
		if (event.getClickCount() == 2) {
			modificarMedico();
		}
	}

	public void enterKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER)
			pesquisar();
	}

	public void setContent(AnchorPane content) {
		if (content != null) {
			ContentPane.setTopAnchor(content, 0.0);
			ContentPane.setLeftAnchor(content, 0.0);
			ContentPane.setBottomAnchor(content, 0.0);
			ContentPane.setRightAnchor(content, 0.0);
			ContentPane.getChildren().setAll(content);
		} else {
			AlertUtils.alertDeveloper();
		}
	}

}
