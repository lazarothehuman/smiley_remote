package application.forms;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.managers.ProcessManager;
import smiley.managers.ProcessManagerImp;
import smiley.models.Cliente;
import smiley.models.Consulta;
import smiley.models.Medico;
import smiley.models.Procedimento;
import smiley.models.ProcedimentoConsulta;
import smiley.utils.AlertUtils;

public class AddConsultaController implements Initializable {

	@FXML
	TableView<ProcedimentoConsulta> tableProcedimentos;

	@FXML
	TableColumn<ProcedimentoConsulta, String> quantidadeColumn;

	@FXML
	TableColumn<ProcedimentoConsulta, String> nameColumn;

	@FXML
	TableColumn<ProcedimentoConsulta, String> valorColumn;

	@FXML
	TableColumn<ProcedimentoConsulta, String> valorTotalColumn;

	@FXML
	ComboBox<Medico> medicoCombo;

	@FXML
	ComboBox<Cliente> clienteCombo;

	@FXML
	ComboBox<Procedimento> procedimentoCombo;

	@FXML
	DatePicker dataRealizacao;

	@FXML
	TextField quantidadeTf = new TextField();

	DataManager dataManager = new DataManagerImp();
	ProcessManager processManager = new ProcessManagerImp();

	PropertyValueFactory<ProcedimentoConsulta, String> valorFactory = new PropertyValueFactory<>("valor");
	PropertyValueFactory<ProcedimentoConsulta, String> quantidadeFactory = new PropertyValueFactory<>("quantidade");
	PropertyValueFactory<ProcedimentoConsulta, String> totalFactory = new PropertyValueFactory<>("total");

	List<ProcedimentoConsulta> procedimentosConsultaModel = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		List<Medico> medicos = dataManager.findMedicos(null, null, null, null, null, null, null, true);
		List<Cliente>clientes = dataManager.findClientes(null, null, null, null, null, null, null, true);// VAIII DAAAR STRESS SE FOREMMM MUITOS CLIENTEEEES
		List<Procedimento> procedimentos  = processManager.findProcedimentos(null, null, null, true);
		
		medicoCombo.setItems(FXCollections.observableArrayList(medicos));
		clienteCombo.setItems(FXCollections.observableArrayList(clientes));
		procedimentoCombo.setItems(FXCollections.observableArrayList(procedimentos));
		
		nameColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ProcedimentoConsulta, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<ProcedimentoConsulta, String> param) {
						return new SimpleStringProperty(param.getValue().getProcedimento().getNome());
					}
				});
		
		quantidadeColumn.setCellValueFactory(quantidadeFactory);
		quantidadeColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ProcedimentoConsulta, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<ProcedimentoConsulta, String> param) {
						return new SimpleStringProperty(param.getValue().getQuantidade() + "");
					}
				});
		
		valorColumn.setCellValueFactory(valorFactory);
		valorColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProcedimentoConsulta,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<ProcedimentoConsulta, String> param) {
				ProcedimentoConsulta procedimentoConsulta = param.getValue();
				Procedimento procedimento = procedimentoConsulta.getProcedimento();
				return new SimpleStringProperty(
						 procedimento.getValor() + " MTN");
			}
		});
		
		valorTotalColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ProcedimentoConsulta, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<ProcedimentoConsulta, String> param) {
						ProcedimentoConsulta procedimentoConsulta = param.getValue();
						Procedimento procedimento = procedimentoConsulta.getProcedimento();
						return new SimpleStringProperty(
								procedimentoConsulta.getQuantidade() * procedimento.getValor() + " MTN");
					}
				});

	}

	public void adicionarProcedimento() {
		int selectedQuantidade = 0;
		String quantidade = quantidadeTf.getText();
		Procedimento selectedProcedimento = procedimentoCombo.getValue();
		if (quantidade != null && !quantidade.isEmpty())
			selectedQuantidade = Integer.parseInt(quantidade);

		ProcedimentoConsulta procedimentoConsulta = new ProcedimentoConsulta();
		if (selectedProcedimento != null)
			procedimentoConsulta.setProcedimento(selectedProcedimento);
		if (selectedQuantidade > 0)
			procedimentoConsulta.setQuantidade(selectedQuantidade);
		else
			AlertUtils.alertErro("Insira uma quantidade superior a 0", "Quantidade invalida", quantidadeTf);
		procedimentosConsultaModel.add(procedimentoConsulta);
		refreshItems();

	}

	private void refreshItems() {
		if (!procedimentosConsultaModel.isEmpty())
			tableProcedimentos.setItems(FXCollections.observableArrayList(procedimentosConsultaModel));

	}

	public void addConsulta() {
		Medico selectedMedico = medicoCombo.getValue();
		Cliente selectedCliente = clienteCombo.getValue();
		Date selectedDataRealizacao = null;
		LocalDate selectedDate = dataRealizacao.getValue();
		if (selectedDate != null) {
			Instant instant = Instant.from(selectedDate.atStartOfDay(ZoneId.systemDefault()));
			selectedDataRealizacao = Date.from(instant);
		}

		if (selectedMedico != null && selectedCliente != null && selectedDataRealizacao != null) {
			Consulta consulta = new Consulta();
			consulta.setMedico(selectedMedico);
			consulta.setCliente(selectedCliente);
			consulta.setDataRealizacao(selectedDataRealizacao);
			consulta.setUser(dataManager.findCurrentUser());
			for (ProcedimentoConsulta procedimentoConsulta : procedimentosConsultaModel) {
				procedimentoConsulta.setConsulta(consulta);
				consulta.addProcedimento(procedimentoConsulta);
			}
			processManager.createConsulta(consulta);

		}
	}
	
	public void remove() {
		ProcedimentoConsulta selectedItem = tableProcedimentos.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			procedimentosConsultaModel.remove(selectedItem);
			refreshItems();
		}
	}

}
