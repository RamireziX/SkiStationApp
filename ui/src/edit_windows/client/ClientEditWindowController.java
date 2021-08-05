package edit_windows.client;

import com.awrzosek.ski_station.tables.person.client.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientEditWindowController implements Initializable {
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField secondNameTextField;
	@FXML
	private TextField surnameTextField;
	@FXML
	private DatePicker dateOfBirthDatePicker;
	@FXML
	private TextField peselTextField;
	@FXML
	private TextField personalIdNumberTextField;
	@FXML
	private TextField phoneTextField;
	@FXML
	private TextField emailTextField;
	@FXML
	private DatePicker dateEnteredDatePicker;

	private Client client;

	public void setClient(Client client)
	{
		this.client = client;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		//TODO ok cancel button i edycja klienta (update w database)
		fillEditWindowWithExistingValues();
	}

	private void fillEditWindowWithExistingValues()
	{
		Platform.runLater(() -> {
			nameTextField.setText(client.getFirstName());
			secondNameTextField.setText(client.getSecondName());
			surnameTextField.setText(client.getSurname());
			if (client.getDateOfBirth() != null)
				dateOfBirthDatePicker.setValue(client.getDateOfBirth());
			peselTextField.setText(client.getPesel());
			personalIdNumberTextField.setText(client.getPersonalIdNumber());
			phoneTextField.setText(client.getPhone());
			emailTextField.setText(client.getEMail());
			dateEnteredDatePicker.setValue(client.getDateEntered());
		});
	}
}
