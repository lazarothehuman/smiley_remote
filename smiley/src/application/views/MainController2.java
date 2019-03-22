package application.views;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

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
	Button brilhoDentalBtn;

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
		
		MenuItem medicosItem = new MenuItem("Medicos");
		MenuItem clientesItem = new MenuItem("Clientes");
		MenuItem procedimentoItem = new MenuItem("Procedimentos");
		final ContextMenu contextBrilhoDental = new ContextMenu();
		contextBrilhoDental.getItems().addAll(medicosItem, clientesItem, procedimentoItem);
		
		medicosItem.setOnAction(event-> viewMedicos());
		clientesItem.setOnAction(event-> viewClientes());
		procedimentoItem.setOnAction(event-> viewProcedimentos());
		
		brilhoDentalBtn.setContextMenu(contextBrilhoDental);
		
		MenuItem usersItem = new MenuItem("Usuarios");
		MenuItem perfisItem = new MenuItem("Perfis");
		MenuItem transacoesItems = new MenuItem("Transaccoes");
		final ContextMenu contextSettings = new ContextMenu();
		contextSettings.getItems().addAll(usersItem, perfisItem, transacoesItems);
		
		usersItem.setOnAction(event -> viewUsers());
		perfisItem.setOnAction(event -> viewPerfis());
		transacoesItems.setOnAction(event -> viewTransaccoes());
		
		settingsButton.setContextMenu(contextSettings);
		
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
	
	public void viewProfiles() {
		AnchorPane content = frameManager.viewProfiles(user);
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
	
	public void viewUsers() {
		AnchorPane content = frameManager.viewUsers(user);
		setContent(content);
		
	}
	
	public void viewPerfis() {
		AnchorPane content = frameManager.viewPerfis(user);
		setContent(content);
		
	}
	
	public void viewTransaccoes() {
		AnchorPane content = frameManager.viewTransaccoes(user);
		setContent(content);	
	}

	public void viewMedicos() {
		AnchorPane content = frameManager.viewMedicos(user);
		setContent(content);
	}
	
	public void viewClientes() {
		
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
