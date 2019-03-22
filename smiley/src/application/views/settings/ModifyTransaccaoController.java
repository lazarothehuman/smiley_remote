package application.views.settings;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.models.Transaccao;
import smiley.utils.AlertUtils;
import smiley.utils.ApplicationUtils;

public class ModifyTransaccaoController implements Initializable {

	@FXML
	TextField url;

	@FXML
	TextField codigo;
	
	Transaccao transaccao;


	DataManager dataManager = new DataManagerImp();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		transaccao = (Transaccao) ApplicationUtils.getObject("selectedTransaccao");
		codigo.setText(transaccao.getCode()+"");
		url.setText(transaccao.getUrl());

	}

	public void gravar() {
		String selectedUrl = url.getText();
		String code = codigo.getText();
		if (!selectedUrl.isEmpty() && selectedUrl != null && !code.isEmpty() && code != null) {
			transaccao.setUrl(selectedUrl);
			transaccao.setCode(Long.valueOf(code));
			dataManager.updateTransaccao(transaccao);
			AlertUtils.alertSucesso("Transação adicionada com sucesso");
		}
	}

}
