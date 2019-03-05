package application.views;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
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
import smiley.models.Consulta;
import smiley.models.ProcedimentoConsulta;
import smiley.utils.ApplicationUtils;
import smiley.utils.SessionHelper;

public class ViewConsultaController implements Initializable {

	@FXML
	Label lblTotal;

	@FXML
	TableView<ProcedimentoConsulta> tableProcedimentoConsulta;

	@FXML
	TableColumn<ProcedimentoConsulta, String> quantidadeColumn;
	
	@FXML
	TableColumn<ProcedimentoConsulta, String> nomeProcedimentoColumn;
	
	@FXML
	TableColumn<ProcedimentoConsulta, String> valorUnitarioColumn;
	
	@FXML
	TableColumn<ProcedimentoConsulta, String> valorTotalColumn;


	@FXML
	TextField medicoTf = new TextField();

	@FXML
	TextField clienteTf = new TextField();

	@FXML
	TextField usuarioTf = new TextField();

	@FXML
	DatePicker dataRealizacao;

	DataManager dataManager = new DataManagerImp();
	ProcessManager processManager = new ProcessManagerImp();

	List<ProcedimentoConsulta> procedimentosConsultaModel = new ArrayList<>();

	SessionHelper session = dataManager.getSessionHelper();
	
	Consulta consulta;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		quantidadeColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ProcedimentoConsulta, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<ProcedimentoConsulta, String> param) {
						return new SimpleStringProperty(param.getValue().getQuantidade()+"");
					}
				});

		nomeProcedimentoColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ProcedimentoConsulta, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<ProcedimentoConsulta, String> param) {
						ProcedimentoConsulta consulta = param.getValue();
						return new SimpleStringProperty(consulta.getProcedimento().getNome());
					}
				});
		
		valorTotalColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProcedimentoConsulta,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<ProcedimentoConsulta, String> param) {
				ProcedimentoConsulta consulta = param.getValue();
				return new SimpleStringProperty(consulta.getProcedimento().getValor()*consulta.getQuantidade()+" MTN");
			}
		});
		
		valorUnitarioColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProcedimentoConsulta,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<ProcedimentoConsulta, String> param) {
				ProcedimentoConsulta consulta = param.getValue();
				return new SimpleStringProperty(consulta.getProcedimento().getValor()+" MTN");
			}
		});
		consulta = (Consulta) ApplicationUtils.take("selectedConsulta");
		medicoTf.setText(consulta.getMedico().getName());
		usuarioTf.setText(consulta.getUser().getName());
		clienteTf.setText(consulta.getCliente().getNome());
		LocalDate date = consulta.getDataRealizacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		dataRealizacao.setValue(date);
		medicoTf.setDisable(true);
		usuarioTf.setDisable(true);
		clienteTf.setDisable(true);
		dataRealizacao.setDisable(true);
		
		procedimentosConsultaModel.addAll(consulta.getProcedimentosConsulta());
		refreshItems();		

	}


	private void refreshItems() {
		if (!procedimentosConsultaModel.isEmpty()) {
			tableProcedimentoConsulta.setItems(FXCollections.observableArrayList(procedimentosConsultaModel));
			lblTotal.setText(procedimentosConsultaModel.size() + "");
		}

	}

	public void remove() {
		ProcedimentoConsulta selectedItem = tableProcedimentoConsulta.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			procedimentosConsultaModel.remove(selectedItem);
			refreshItems();
		}
	}

}
