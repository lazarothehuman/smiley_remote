package application.forms;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import smiley.managers.ProcessManager;
import smiley.managers.ProcessManagerImp;
import smiley.models.Procedimento;
import smiley.utils.AlertUtils;

public class AddProcedimentoController implements Initializable {

	@FXML
	TextField nome;

	@FXML
	TextField codigo;

	@FXML
	TextField valor;

	ProcessManager processManager = new ProcessManagerImp();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public void gravar() {
		String name = nome.getText();
		String code = codigo.getText();
		String value = valor.getText();
		if (!value.isEmpty() && value != null && !name.isEmpty() && name != null && !code.isEmpty() && code != null) {
			double valorProcedimento = Double.parseDouble(value);
			Procedimento procedimento = new Procedimento();
			procedimento.setValor(valorProcedimento);
			procedimento.setNome(name);
			procedimento.setCodigo(code);
			processManager.createProcedimento(procedimento);
			AlertUtils.alertSucesso("Procedimento adicionado com sucesso");
		}
	}

}
