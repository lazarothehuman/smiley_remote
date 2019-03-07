package application.views.settings;

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
import smiley.models.Transaccao;
import smiley.models.User;
import smiley.utils.AlertUtils;
import smiley.utils.ApplicationUtils;
import smiley.utils.FrameManager;

public class ViewTransaccoesController implements Initializable {
	
	@FXML
	AnchorPane ContentPane;

	@FXML
	Label lblTotal;

	@FXML
	TableView<Transaccao> tableTransaccoes;


	@FXML
	TableColumn<Transaccao, String> nomeColumn;

	@FXML
	TableColumn<Transaccao, String> codigoColumn;

	@FXML
	TableColumn<Transaccao, String> urlColumn;

	@FXML
	TextField nome;

	@FXML
	TextField codigo;

	@FXML
	CheckBox actives;

	List<Transaccao> transacaosModelList = new ArrayList<>();

	FrameManager frameManager = new FrameManager();
	DataManager dataManager = new DataManagerImp();
	User user;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nomeColumn.setCellValueFactory(new PropertyValueFactory<Transaccao, String>("name"));
		codigoColumn.setCellValueFactory(new PropertyValueFactory<Transaccao, String>("code"));
		urlColumn.setCellValueFactory(new PropertyValueFactory<Transaccao, String>("url"));
		user = dataManager.findCurrentUser();

	}

	public void pesquisar() {
		String selectedNome = nome.getText();
		String selectedCodigo = codigo.getText();
		transacaosModelList = dataManager.findTransaccaos(null, selectedNome, selectedCodigo,
				!actives.isSelected());
		if (transacaosModelList != null)
			refreshItems();
		else
			AlertUtils.pesquisaVazia();

	}

	public void addTransaccao() {
		AnchorPane content = frameManager.addTransaccao();
		if (content != null) {
			setContent(content);
		}

	}

	public void modify() {
		Transaccao selectedTransaccao = null;
		selectedTransaccao = tableTransaccoes.getSelectionModel().getSelectedItem();
		if (selectedTransaccao != null) {
			ApplicationUtils.add("transacao", selectedTransaccao);
			AnchorPane content = frameManager.modifyTransaccao();
			if (content != null) 
				setContent(content);
			
		}

	}

	public void remover() {
		Transaccao selectedTransaccao = null;
		selectedTransaccao = tableTransaccoes.getSelectionModel().getSelectedItem();
		if (selectedTransaccao != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação de remoção");
			alert.setHeaderText(null);
			alert.setContentText("Tem certeza que deseja remover o transacao?" + selectedTransaccao.getCode());
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				selectedTransaccao.setActive(false);
				transacaosModelList.remove(selectedTransaccao);
				dataManager.updateTransaccao(selectedTransaccao);
				refreshItems();
			}
		}
	}

	private void refreshItems() {
		if (transacaosModelList != null) {
			tableTransaccoes.setItems(FXCollections.observableArrayList(transacaosModelList));
			lblTotal.setText(transacaosModelList.size() + "");
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
