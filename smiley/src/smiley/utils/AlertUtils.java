package smiley.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.scene.control.Alert.AlertType;

public class AlertUtils {

	public static void alertSemPrivelegio() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Sem privelegio");
		alert.setContentText("O usuario nao tem acesso a esta funcao do sistema.");
		alert.setHeaderText(null);
		alert.showAndWait();
		
	}

	public static void alertErroInsercaoDados() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro na inserção de dados");
		alert.setContentText("Alguma das informações importantes, não estão certas");
		alert.setHeaderText(null);
		alert.showAndWait();
		
	}

	public static void alertSucesso(String string) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Adição com sucesso");
		alert.setContentText("O "+string+" foi adicionado com sucesso");
		alert.setHeaderText(null);
		alert.showAndWait();
		
	}

	public static void alertErroSelecionar(String string) {
		// TODO Auto-generated method stub
		
	}

	public static void pesquisaVazia() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Pesquisa voltou sem nenhum resultado");
		alert.setContentText("Pesquisa voltou sem nenhum resultado");
		alert.setHeaderText(null);
		alert.showAndWait();
		
	}

	public static void alertDataDeEnvioAutomatico(int dIA_ENVIO_MENSAGEM) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Envio de mensagens automatico");
		alert.setContentText("Hoje, dia "+dIA_ENVIO_MENSAGEM+", é o dia de envio de mensagens das quotas. ");
		alert.setHeaderText(null);
		alert.showAndWait();
		
	}
	
	public static void alertErro(String context, String title, Control component) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(context);
		alert.setHeaderText(null);
		alert.showAndWait();
		component.setStyle("-fx-border-color:#ff0000;");
		component.requestFocus();
		
	}

}
