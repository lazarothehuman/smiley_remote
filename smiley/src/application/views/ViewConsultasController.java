package application.views;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.managers.ProcessManager;
import smiley.managers.ProcessManagerImp;
import smiley.models.Cliente;
import smiley.models.Consulta;
import smiley.models.Medico;
import smiley.models.User;
import smiley.utils.AlertUtils;
import smiley.utils.ApplicationUtils;
import smiley.utils.FrameManager;

public class ViewConsultasController implements Initializable {

	@FXML
	Label lblTotal;

	@FXML
	TableView<Consulta> tableConsultas;

	@FXML
	AnchorPane ContentPane;

	@FXML
	TableColumn<Consulta, String> clienteColumn;

	@FXML
	TableColumn<Consulta, String> usuarioColumn;

	@FXML
	TableColumn<Consulta, String> medicoColumn;

	@FXML
	TableColumn<Consulta, Long> idColumn;

	@FXML
	TableColumn<Consulta, Date> dataRealizacaoColumn;

	@FXML
	TextField clienteTf = new TextField();

	@FXML
	TextField medicoTf = new TextField();

	@FXML
	TextField userTf = new TextField();

	@FXML
	TextField idTf = new TextField();

	@FXML
	CheckBox actives;

	List<Consulta> consultasList = new ArrayList<>();

	ProcessManager processManager = new ProcessManagerImp();
	FrameManager frameManager = new FrameManager();
	DataManager dataManager = new DataManagerImp();
	User user;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		user = dataManager.findCurrentUser();

		dataRealizacaoColumn.setCellValueFactory(new PropertyValueFactory<>("dataRealizacao"));
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

		clienteColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Consulta, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Consulta, String> param) {
						return new SimpleStringProperty(param.getValue().getCliente().getNome());
					}
				});
		medicoColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Consulta, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Consulta, String> param) {
						return new SimpleStringProperty(param.getValue().getMedico().getName());
					}
				});
		usuarioColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Consulta, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Consulta, String> param) {
						return new SimpleStringProperty(param.getValue().getUser().getName());
					}
				});

	}

	public void pesquisar() {
		Cliente cliente = null;
		Medico medico = null;
		User user = null;
		Long id = null;

		String selectedCliente = clienteTf.getText();
		String selectedMedico = medicoTf.getText();
		String selectedUsuario = userTf.getText();
		String selectedId = idTf.getText();
		if (selectedId != null && !selectedId.isEmpty())
			id = Long.parseLong(selectedId);

		cliente = dataManager.findCliente(null, selectedCliente);
		medico = dataManager.findMedico(null, selectedMedico);
		user = dataManager.findUser(null, selectedUsuario);

		consultasList = processManager.findConsultas(cliente, medico, user, id, !actives.isSelected());
		if (consultasList != null)
			refreshItems();
		else
			AlertUtils.pesquisaVazia();

	}

	public void addConsulta() {
		AnchorPane content = frameManager.addConsulta(user);
		if (content != null)
			setContent(content);

	}

	public void modify() {
		Consulta selectedConsulta = null;
		selectedConsulta = tableConsultas.getSelectionModel().getSelectedItem();
		if (selectedConsulta != null) {
			ApplicationUtils.add("consulta", selectedConsulta);
			AnchorPane content = frameManager.modifyConsulta(selectedConsulta, user);
			if (content != null)
				setContent(content);
		}

	}

	public void remover() {
		Consulta selectedConsulta = null;
		selectedConsulta = tableConsultas.getSelectionModel().getSelectedItem();
		if (selectedConsulta != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação de remoção");
			alert.setHeaderText(null);
			alert.setContentText("Tem certeza que deseja remover a consulta?" + selectedConsulta.getId());
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				selectedConsulta.setActive(false);
				consultasList.remove(selectedConsulta);
				processManager.updateConsulta(selectedConsulta);
				refreshItems();
			}
		}
	}

	private void refreshItems() {
		if (consultasList != null) {
			tableConsultas.setItems(FXCollections.observableArrayList(consultasList));
			lblTotal.setText(consultasList.size() + "");
		}

	}

	public void setContent(AnchorPane content) {
		if (content != null) {
			ContentPane.setTopAnchor(content, 0.0);
			ContentPane.setLeftAnchor(content, 0.0);
			ContentPane.setBottomAnchor(content, 0.0);
			ContentPane.setRightAnchor(content, 0.0);
			ContentPane.getChildren().setAll(content);
		}
	}
}
