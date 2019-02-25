package smiley.utils;

import java.io.IOException;

import application.forms.ModifyClientController;
import application.forms.ModifyMedicoController;
import application.forms.ModifyProfileController;
import application.forms.ModifyUserController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.models.Cliente;
import smiley.models.Consulta;
import smiley.models.Medico;
import smiley.models.Procedimento;
import smiley.models.Profile;
import smiley.models.Transaccao;
import smiley.models.User;

public class FrameManager {

	// adds serao 100
	// modifys serao 200
	// views//300

	DataManager dataManager = new DataManagerImp();

	public void mainController() {
		Stage primaryStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/views/Main.fxml"));
			loader.load();
			Parent root = loader.getRoot();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);
			primaryStage.setOnCloseRequest(e -> {
				Platform.exit();
				System.exit(0);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void mainController2() {
		Stage primaryStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/views/Main2.fxml"));
			loader.load();
			Parent root = loader.getRoot();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Brilho Dental");
			primaryStage.getIcons().add(new Image("brilhodental.png"));
			primaryStage.show();
			primaryStage.setResizable(false);
			primaryStage.setOnCloseRequest(e -> {
				Platform.exit();
				System.exit(0);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	private void load(String url) {
		Stage primaryStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(url));
			loader.load();
			Parent root = loader.getRoot();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Smiley");
			//primaryStage.getIcons().add(new Image("frelimo.jpg"));// change to brilho dental
			primaryStage.show();
			primaryStage.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void login() {
		Stage primaryStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/login/Login.fxml"));
			loader.load();
			Parent root = loader.getRoot();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);
			primaryStage.setOnCloseRequest(e -> {
				Platform.exit();
				System.exit(0);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public AnchorPane addProcedimento(User user) {
		AnchorPane content = null;
		if (user != null) {
			Profile profile = user.getProfile();
			Transaccao transaction = dataManager.findTransaccao(104l);
			if (transaction.getProfiles().contains(profile))
				content = loadContent(transaction.getUrl());
			else
				AlertUtils.alertSemPrivelegio();
		}
		return content;
	}

	public AnchorPane viewProcedimentos(User user) {
		AnchorPane content = null;
		if (user != null) {
			Profile profile = user.getProfile();
			Transaccao transaction = dataManager.findTransaccao(304l);
			if (transaction.getProfiles().contains(profile))
				content = loadContent(transaction.getUrl());
			else
				AlertUtils.alertSemPrivelegio();
		}
		return content;
	}

	public AnchorPane loadContent(String url) {
		AnchorPane content = null;
		try {
			content = (AnchorPane) FXMLLoader.load(getClass().getResource(url));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return content;
	}

	public AnchorPane addClient(User user) {
		AnchorPane content = null;
		if (user != null) {
			Profile profile = user.getProfile();
			Transaccao transaction = dataManager.findTransaccao(104l);
			if (transaction.getProfiles().contains(profile))
				content = loadContent(transaction.getUrl());
			else
				AlertUtils.alertSemPrivelegio();
		}
		return content;
		
	}

	public AnchorPane addMedico(User user) {
		AnchorPane content = null;
		if (user != null) {
			Profile profile = user.getProfile();
			Transaccao transaction = dataManager.findTransaccao(104l);
			if (transaction.getProfiles().contains(profile))
				content = loadContent(transaction.getUrl());
			else
				AlertUtils.alertSemPrivelegio();
		}
		return content;
		
	}

	public AnchorPane addUser(User user) {
		AnchorPane content = null;
		if (user != null) {
			Profile profile = user.getProfile();
			Transaccao transaction = dataManager.findTransaccao(104l);
			if (transaction.getProfiles().contains(profile))
				content = loadContent(transaction.getUrl());
			else
				AlertUtils.alertSemPrivelegio();
		}
		return content;
		
	}

	public AnchorPane modifyProcedimento(Procedimento selectedProcedimento, User user) {
		AnchorPane content = null;
		if (user != null) {
			Profile profile = user.getProfile();
			Transaccao transaction = dataManager.findTransaccao(104l);
			if (transaction.getProfiles().contains(profile))
				content = loadContent(transaction.getUrl());
			else
				AlertUtils.alertSemPrivelegio();
		}
		return content;
		
	}

	public AnchorPane addConsulta(User user) {
		AnchorPane content = null;
		if (user != null) {
			Profile profile = user.getProfile();
			Transaccao transaction = dataManager.findTransaccao(105l);
			if (transaction.getProfiles().contains(profile))
				content = loadContent(transaction.getUrl());
			else
				AlertUtils.alertSemPrivelegio();
		}
		return content;
	}


	public AnchorPane viewConsultas(User user) {
		AnchorPane content = null;
		if (user != null) {
			Profile profile = user.getProfile();
			Transaccao transaction = dataManager.findTransaccao(305l);
			if (transaction.getProfiles().contains(profile))
				content = loadContent(transaction.getUrl());
			else
				AlertUtils.alertSemPrivelegio();
		}
		return content;
	}

	public AnchorPane modifyConsulta(Consulta selectedConsulta, User user) {
		AnchorPane content = null;
		if (user != null) {
			Profile profile = user.getProfile();
			Transaccao transaction = dataManager.findTransaccao(201l);
			if (transaction.getProfiles().contains(profile))
				content = loadContent(transaction.getUrl());
			else
				AlertUtils.alertSemPrivelegio();
		}
		return content;
		
	}

	public AnchorPane viewClientes(User user) {
		AnchorPane content = null;
		if (user != null) {
			Profile profile = user.getProfile();
			Transaccao transaction = dataManager.findTransaccao(105l);
			if (transaction.getProfiles().contains(profile))
				content = loadContent(transaction.getUrl());
			else
				AlertUtils.alertSemPrivelegio();
		}
		return content;
	}
}
