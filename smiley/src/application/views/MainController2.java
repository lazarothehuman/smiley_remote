package application.views;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import smiley.managers.DataManagerImp;
import smiley.models.Profile;
import smiley.models.User;
import smiley.utils.FrameManager;


public class MainController2 implements Initializable {

	@FXML
	Button procedimentosBtn;

	@FXML
	AnchorPane ContentPane;

	@FXML
	Button userBtn;
	
	@FXML
	Button clienteBtn;

	@FXML
	Button settingsButton;

	@FXML
	JFXButton registar;

	@FXML
	JFXButton visualizarMedicamento;

	@FXML
	JFXButton visualizarMovimentos;

	@FXML
	VBox seccaoSistema;

	AnchorPane content;

	FrameManager frameManager = new FrameManager();
	DataManagerImp dataManager = new DataManagerImp();
	User user;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// por content como venda
		setContextMenu();
		if (content != null) {
			setContent(content);
		}
	}

	public void setContextMenu() {
		user = dataManager.findCurrentUser();
		MenuItem addProcedimento = new MenuItem("Adicionar procedimento");
		MenuItem viewProcedimentos = new MenuItem("Visualizar todos procedimentos");
		final ContextMenu contexMenuProcedimento = new ContextMenu();
		contexMenuProcedimento.getItems().addAll(addProcedimento, viewProcedimentos);
		addProcedimento.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addProcedimento();
			}
		});
		viewProcedimentos.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				viewProcedimentos();
			}
		});
		procedimentosBtn.setContextMenu(contexMenuProcedimento);
		
		MenuItem addCliente = new MenuItem("Adicionar cliente");
		MenuItem viewClientes = new MenuItem("Visualizar todos clientes");
		final ContextMenu contextMenuCliente = new ContextMenu();
		contextMenuCliente.getItems().addAll(addCliente, viewClientes);
		addCliente.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addCliente();
			}
		});
		viewClientes.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				viewCliente();
			}
		});
		clienteBtn.setContextMenu(contextMenuCliente);
/*
		MenuItem addUser = new MenuItem("Adicionar usuário");
		MenuItem viewUser = new MenuItem("Visualizar todos usuários");
		final ContextMenu contextMenuUser = new ContextMenu();
		contextMenuUser.getItems().addAll(addUser, viewUser);
		addUser.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Cut...");
			}
		});
		viewUser.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Cut...");
			}
		});
		userBtn.setContextMenu(contextMenuUser);

		MenuItem addPerfil = new MenuItem("Adicionar perfil");
		MenuItem viewPerfis = new MenuItem("Visualizar todos perfis");
		MenuItem addTransaccao = new MenuItem("Adicionar transa��o");
		MenuItem viewTransaccoes = new MenuItem("Visualizar todas transa��es");
		final ContextMenu contextMenuSettings = new ContextMenu();
		contextMenuSettings.getItems().addAll(addPerfil, viewPerfis, addTransaccao, viewTransaccoes);
		addPerfil.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Cut...");
			}
		});
		viewPerfis.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Cut...");
			}
		});
		addTransaccao.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Cut...");
			}
		});
		viewTransaccoes.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Cut...");
			}
		});

		settingsButton.setContextMenu(contextMenuSettings);*/
	}
/*
	public void viewMedicamento() {
		AnchorPane content = frameManager.searchMedicamento(user);
		setContent(content);
	}

	public void viewMovimento() {
		AnchorPane content = frameManager.searchMovimento(user);
		setContent(content);
	}*/

	public void viewProcedimentos() {
		AnchorPane content = frameManager.viewProcedimentos(user);
		setContent(content);
	}

	public void addProcedimento() {
		AnchorPane content = frameManager.addProcedimento(user);
		setContent(content);
	}
	
	public void addConsulta() {
		AnchorPane content = frameManager.addConsulta(user);
		setContent(content);
	}

	public void addCliente() {
		AnchorPane content = frameManager.addClient(user);
		setContent(content);
	}

	public void viewCliente() {
		AnchorPane content = frameManager.viewClientes(user);
		setContent(content);
	}
	
	public void viewConsultas() {
		AnchorPane content = frameManager.viewConsultas(user);
		setContent(content);
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



	public void refreshContent() {
		if (content != null)
			setContent(content);

	}

	public void setContentFromOtherView(AnchorPane content) {
		if (content != null)
			this.content = content;

	}
}
