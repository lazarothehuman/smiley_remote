package application.views;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.managers.ProcessManager;
import smiley.managers.ProcessManagerImp;
import smiley.models.Cliente;
import smiley.models.Consulta;
import smiley.models.Medico;
import smiley.utils.SessionHelper;

public class ViewConsultaController implements Initializable {

	@FXML
	Label lblTotal;

	@FXML
	TableView<Consulta> tableConsultas;

	@FXML
	TableColumn<Consulta, Date> dataRealizacaoColumn;

	@FXML
	TableColumn<Consulta, String> clienteNameColumn;

	@FXML
	TableColumn<Consulta, String> medicoNameColumn;

	@FXML
	TableColumn<Consulta, String> userNameColumn;

	@FXML
	TableColumn<Consulta, String> idColumn;

	@FXML
	TextField medicoTf = new TextField();

	@FXML
	TextField clienteTf = new TextField();

	@FXML
	TextField usuarioTf = new TextField();

	@FXML
	TextField idTf = new TextField();

	DataManager dataManager = new DataManagerImp();
	ProcessManager processManager = new ProcessManagerImp();

	List<Consulta> consultaListModel = new ArrayList<>();

	SessionHelper session = dataManager.getSessionHelper();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		medicoNameColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Consulta, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Consulta, String> param) {
						return new SimpleStringProperty(param.getValue().getMedico().getName());
					}
				});

		clienteNameColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Consulta, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Consulta, String> param) {
						return new SimpleStringProperty(param.getValue().getCliente().getNome());
					}
				});

		userNameColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Consulta, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Consulta, String> param) {
						Consulta consulta = param.getValue();
						return new SimpleStringProperty(consulta.getUser().getName());
					}
				});

		idColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Consulta, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Consulta, String> param) {
						Consulta consulta = param.getValue();
						return new SimpleStringProperty(consulta.getId() + "");
					}
				});

	}

	public void adicionarConsulta() {

	}

	private void refreshItems() {
		if (!consultaListModel.isEmpty()) {
			tableConsultas.setItems(FXCollections.observableArrayList(consultaListModel));
			lblTotal.setText(consultaListModel.size() + "");
		}

	}
	
	public void pesquisar() {
		String selectedCliente = clienteTf.getText();
		String selectedMedico = medicoTf.getText();
		String selectedUsuario = usuarioTf.getText();
		String selectedId = idTf.getText();
		//consultaListModel  = processManager.findConsultas(selectedCliente, selectedMedico, selectedUsuario, selectedId, true );
	}

	public void remove() {
		Consulta selectedItem = tableConsultas.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			consultaListModel.remove(selectedItem);
			refreshItems();
		}
	}

}
