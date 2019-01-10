package application.views;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import smiley.models.Cliente;
import smiley.models.Procedimento;

public class ViewProcedimentosController implements Initializable {
	@FXML
	CheckBox active;
	
	@FXML
	TableView<Procedimento> tableProcedimentos;
	
	@FXML
	TableColumn<Procedimento, String> nomeColumn;
	
	@FXML
	TableColumn<Procedimento, String> codigoColumn;
	
	@FXML
	TableColumn<Procedimento, String> precoColumn;
	
	@FXML
	TableColumn<Procedimento, String> activeColumn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nomeColumn.setCellValueFactory(new PropertyValueFactory<Procedimento, String>("nome"));
		codigoColumn.setCellValueFactory(new PropertyValueFactory<Procedimento, String>("codigo"));
		nomeColumn.setCellValueFactory(new PropertyValueFactory<Procedimento, String>("valor"));



		
	}

}
