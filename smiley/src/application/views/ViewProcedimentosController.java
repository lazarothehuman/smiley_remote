package application.views;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import smiley.managers.ProcessManager;
import smiley.managers.ProcessManagerImp;
import smiley.models.Procedimento;
import smiley.utils.AlertUtils;

public class ViewProcedimentosController implements Initializable {
	
	@FXML
	TableView<Procedimento> tableProcedimentos;
	
	@FXML
	TableColumn<Procedimento, String> nomeColumn;
	
	@FXML
	TableColumn<Procedimento, String> codigoColumn;
	
	@FXML
	TableColumn<Procedimento, String> precoColumn;
	

	
	@FXML
	TextField nome;
	
	@FXML
	TextField codigo;
	
	@FXML
	CheckBox actives;
	
	ProcessManager processManager = new ProcessManagerImp();
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nomeColumn.setCellValueFactory(new PropertyValueFactory<Procedimento, String>("nome"));
		codigoColumn.setCellValueFactory(new PropertyValueFactory<Procedimento, String>("codigo"));
		nomeColumn.setCellValueFactory(new PropertyValueFactory<Procedimento, String>("valor"));
	}

	public void pesquisar() {
		String selectedNome = nome.getText();
		String selectedCodigo = codigo.getText();
		List<Procedimento> procedimentos = processManager.findProcedimentos(null, selectedNome, selectedCodigo, !actives.isSelected());
		if (procedimentos != null) {
			tableProcedimentos.setItems(FXCollections.observableArrayList(procedimentos));
		}else {
			AlertUtils.pesquisaVazia();
		}
	}
}
