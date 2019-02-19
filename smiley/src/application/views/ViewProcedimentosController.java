package application.views;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.managers.ProcessManager;
import smiley.managers.ProcessManagerImp;
import smiley.models.Procedimento;
import smiley.models.User;
import smiley.utils.AlertUtils;
import smiley.utils.ApplicationUtils;
import smiley.utils.FrameManager;

public class ViewProcedimentosController implements Initializable {

	@FXML
	Label lblTotal;

	@FXML
	TableView<Procedimento> tableProcedimentos;

	@FXML
	AnchorPane ContentPane;

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

	List<Procedimento> procedimentosModelList = new ArrayList<>();

	ProcessManager processManager = new ProcessManagerImp();
	FrameManager frameManager = new FrameManager();
	DataManager dataManager = new DataManagerImp();
	User user;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nomeColumn.setCellValueFactory(new PropertyValueFactory<Procedimento, String>("nome"));
		codigoColumn.setCellValueFactory(new PropertyValueFactory<Procedimento, String>("codigo"));
		precoColumn.setCellValueFactory(new PropertyValueFactory<Procedimento, String>("valor"));
		user = dataManager.findCurrentUser();

	}

	public void pesquisar() {
		String selectedNome = nome.getText();
		String selectedCodigo = codigo.getText();
		procedimentosModelList = processManager.findProcedimentos(null, selectedNome, selectedCodigo,
				!actives.isSelected());
		if (procedimentosModelList != null)
			refreshItems();
		else
			AlertUtils.pesquisaVazia();

	}

	public void addProcedimento() {
		AnchorPane content = frameManager.addProcedimento(user);
		if (content != null) {
			setContent(content);
		}

	}

	public void modify() {
		Procedimento selectedProcedimento = null;
		selectedProcedimento = tableProcedimentos.getSelectionModel().getSelectedItem();
		if (selectedProcedimento != null) {
			ApplicationUtils.add("procedimento", selectedProcedimento);
			frameManager.modifyProcedimento(selectedProcedimento, user);
		}

	}

	public void remover() {
		Procedimento selectedProcedimento = null;
		selectedProcedimento = tableProcedimentos.getSelectionModel().getSelectedItem();
		if (selectedProcedimento != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação de remoção");
			alert.setHeaderText(null);
			alert.setContentText("Tem certeza que deseja remover o procedimento?" + selectedProcedimento.getNome());
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				selectedProcedimento.setActive(false);
				procedimentosModelList.remove(selectedProcedimento);
				processManager.updateProcedimento(selectedProcedimento);
				refreshItems();
			}
		}
	}

	private void refreshItems() {
		if (procedimentosModelList != null) {
			tableProcedimentos.setItems(FXCollections.observableArrayList(procedimentosModelList));
			lblTotal.setText(procedimentosModelList.size() + "");
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
