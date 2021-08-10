package edit_windows.client;

import com.awrzosek.ski_station.basic.BasicUtils;
import com.awrzosek.ski_station.tables.person.client.Client;
import com.awrzosek.ski_station.tables.person.client.ClientDao;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ClientEditWindowController implements Initializable {
	//TODO fieldy dla kolumn tabeli i zapełnić
	@FXML
	private TableView<SkipassInfo> skipassesTableView;
	@FXML
	private TableColumn<SkipassInfo, String> discountSkipassTableColumn;
	@FXML
	private TableColumn<SkipassInfo, LocalDate> dateFromSkipassTableColumn;
	@FXML
	private TableColumn<SkipassInfo, LocalDate> dateToSkipassTableColumn;
	@FXML
	private TableColumn<SkipassInfo, Boolean> activeSkipassTableColumn;//TODO zobaczyc może tick i x

	@FXML
	private TableView<RentalInfo> rentalsTableView;
	@FXML
	private TableColumn<RentalInfo, String> nameRentalsTableColumn;
	@FXML
	private TableColumn<RentalInfo, String> typeRentalsTableColumn;
	@FXML
	private TableColumn<RentalInfo, LocalDate> rentDateRentalsTableColumn;
	@FXML
	private TableColumn<RentalInfo, LocalDate> returnDateRentalsTableColumn;
	@FXML
	private TableColumn<RentalInfo, String> rentTypeRentalsTableColumn;

	@FXML
	private Button acceptButton;
	@FXML
	private Button cancelButton;

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

	private Client existingClient;
	private TableView<Client> clientsTableView;

	public void setExistingClient(Client existingClient)
	{
		this.existingClient = existingClient;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		fillEditWindowWithExistingValues();
		setAcceptButtonAction();
		setCancelButtonAction();
	}

	public void setParentTableView(TableView<Client> clientsTableView)
	{
		this.clientsTableView = clientsTableView;
	}

	private void fillEditWindowWithExistingValues()
	{
		Platform.runLater(() -> {
			nameTextField.setText(existingClient.getFirstName());
			secondNameTextField.setText(existingClient.getSecondName());
			surnameTextField.setText(existingClient.getSurname());
			if (existingClient.getDateOfBirth() != null)
				dateOfBirthDatePicker.setValue(existingClient.getDateOfBirth());
			peselTextField.setText(existingClient.getPesel());
			personalIdNumberTextField.setText(existingClient.getPersonalIdNumber());
			phoneTextField.setText(existingClient.getPhone());
			emailTextField.setText(existingClient.getEMail());
			dateEnteredDatePicker.setValue(existingClient.getDateEntered());
		});
	}

	private void setAcceptButtonAction()
	{
		acceptButton.setDefaultButton(true);
		acceptButton.setOnAction(e -> {
			Client updatedClient = new Client();
			updatedClient.setId(existingClient.getId());
			updatedClient.setFirstName(nameTextField.getText());
			updatedClient.setSecondName(secondNameTextField.getText());
			updatedClient.setSurname(surnameTextField.getText());
			updatedClient.setDateOfBirth(dateOfBirthDatePicker.getValue());
			updatedClient.setPesel(peselTextField.getText());
			updatedClient.setPersonalIdNumber(personalIdNumberTextField.getText());
			updatedClient.setPhone(phoneTextField.getText());
			updatedClient.setEMail(emailTextField.getText());
			updatedClient.setDateEntered(dateEnteredDatePicker.getValue());
			Stage stage = getStage(acceptButton);
			try (Connection connection = BasicUtils.getConnection())
			{
				//TODO jakieś zapytanie czy na pewno
				ClientDao clientDao = new ClientDao(connection);
				clientDao.update(updatedClient);
				stage.close();
				refreshClientsTableView(clientDao);
			} catch (SQLException exception)
			{
				exception.printStackTrace();//TODO
				stage.close();
			}
		});
	}

	private void setCancelButtonAction()
	{
		cancelButton.setCancelButton(true);
		cancelButton.setOnAction(e -> {
			//TODO spytać czy porzucić edycję
			Stage stage = getStage(cancelButton);
			stage.close();
		});
	}

	private Stage getStage(Button button)
	{
		return (Stage) button.getScene().getWindow();
	}

	private void refreshClientsTableView(ClientDao clientDao) throws SQLException
	{
		clientsTableView.getItems().setAll(clientDao.getAll());
	}
}
