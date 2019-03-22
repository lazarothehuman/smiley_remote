package application.forms;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TextField;
import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.models.Profile;
import smiley.utils.AlertUtils;

public class AddProfileController implements Initializable {

	@FXML
	private TextField profilename;

	DataManager dataManager = new DataManagerImp();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void add() {
		String selectedName = profilename.getText();
		if (selectedName != null) {
			if (!selectedName.isEmpty()) {
				Profile dummy = dataManager.findProfile(selectedName);
				if (dummy == null) {
					Profile profile = new Profile();
					profile.setProfilename(selectedName);
					dataManager.createProfile(profile);
				} else {
					AlertUtils.alertErroSelecionar("Perfil com o nome já existe");
				}
			}
		}
	}

}
