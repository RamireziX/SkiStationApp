package edit_windows.client;

import com.awrzosek.ski_station.basic.BasicUtils;
import com.awrzosek.ski_station.database_management.ClientManager;
import com.awrzosek.ski_station.tables.person.client.Client;
import com.awrzosek.ski_station.tables.person.client.ClientDao;
import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.equipment.EquipmentDao;
import com.awrzosek.ski_station.tables.ski.skipass.map.Duration;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassType;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientAddWindowController implements Initializable {
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
	private TextField nameTextField;
	@FXML
	private TextField phoneTextField;
	@FXML
	private TextField emailTextField;
	@FXML
	private DatePicker dateEnteredDatePicker;

	//TODO wybieranie eq do wypożyczenia - może jakimś buttonem z małym oknem bo rent type sie daje
	//zweryfikować z fr - albo rent type dać gdzieś obok tabeli idk
	@FXML
	private TableView<Equipment> equipmentTableView;
	@FXML
	private TableColumn<Equipment, Long> equipmentIdTableColumn;
	@FXML
	private TableColumn<Equipment, String> equipmentSerialNumberColumn;
	@FXML
	private TableColumn<Equipment, String> equipmentNameColumn;
	@FXML
	private TableColumn<Equipment, String> equipmentTypeColumn;
	@FXML
	private TableColumn<Equipment, BigDecimal> equipmentRentPriceColumn;
	@FXML
	private TableColumn<Equipment, String> equipmentConditionColumn;
	@FXML
	private ChoiceBox<Integer> durationChoiceBox;
	@FXML
	private ChoiceBox<Integer> regularNumberChoiceBox;
	@FXML
	private ChoiceBox<Integer> firstDiscountNumberChoiceBox;
	@FXML
	private ComboBox<SkipassType> firstDiscountTypeComboBox;
	@FXML
	private ChoiceBox<Integer> secondDiscountNumberChoiceBox;
	@FXML
	private ComboBox<SkipassType> secondDiscoundTypeComboBox;
	@FXML
	private ChoiceBox<Integer> thirdDiscountNumberChoiceBox;
	@FXML
	private ComboBox<SkipassType> thirdDiscountTypeComboBox;

	//TODO akcje buttonów
	@FXML
	private Button acceptButton;
	@FXML
	private Button cancelButton;

	private TableView<Client> clientsTableView;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		setAcceptButtonAction();
		setCancelButtonAction();
		dateEnteredDatePicker.setValue(LocalDate.now());
		setEquipmentTableViewCellValues();
		List<Integer> durationIntValues = new ArrayList<>();
		for (Duration d : Duration.values())//TODO może to troszkę inaczej, żeby jednak było 7 dni a nie sama cyferka
			durationIntValues.add(d.getDays());

		durationChoiceBox.getItems().setAll(durationIntValues);
	}

	public void setParentTableView(TableView<Client> clientsTableView)
	{
		this.clientsTableView = clientsTableView;
	}

	private void setAcceptButtonAction()
	{
		acceptButton.setDefaultButton(true);
		acceptButton.setOnAction(e -> {
			Client client = new Client();
			client.setFirstName(nameTextField.getText());
			client.setSecondName(secondNameTextField.getText());
			client.setSurname(surnameTextField.getText());
			client.setDateOfBirth(dateOfBirthDatePicker.getValue());
			client.setPesel(peselTextField.getText());
			client.setPersonalIdNumber(personalIdNumberTextField.getText());
			client.setPhone(phoneTextField.getText());
			client.setEMail(emailTextField.getText());
			client.setDateEntered(dateEnteredDatePicker.getValue());
			//TODO to jakoś inaczej, chyba moge stworzyc duration z liczby dni
			//Duration duration = durationChoiceBox.getValue();
			Stage stage = ClientEditWindowController.getStage(acceptButton);
			try (Connection connection = BasicUtils.getConnection())
			{
				//TODO jakieś zapytanie czy na pewno
				ClientManager clientManager = new ClientManager(connection);
				ClientDao clientDao = clientManager.getClientDao();
				//clientManager.addClient(client, null, );
				stage.close();
				ClientEditWindowController.refreshClientsTableView(clientDao, clientsTableView);
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
			Stage stage = ClientEditWindowController.getStage(cancelButton);
			stage.close();
		});
	}

	private void setEquipmentTableViewCellValues()
	{
		equipmentTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		equipmentIdTableColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getId()));
		equipmentSerialNumberColumn.setCellValueFactory(
				data -> new ReadOnlyStringWrapper(data.getValue().getSerialNumber()));
		equipmentNameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
		equipmentTypeColumn.setCellValueFactory(
				data -> new ReadOnlyStringWrapper(data.getValue().getType().toString()));
		equipmentRentPriceColumn.setCellValueFactory(
				data -> new ReadOnlyObjectWrapper<>(data.getValue().getRentPrice().setScale(2, RoundingMode.HALF_UP)));
		equipmentConditionColumn.setCellValueFactory(
				data -> new ReadOnlyStringWrapper(data.getValue().getCondition().toString()));

		try (Connection connection = BasicUtils.getConnection())
		{
			EquipmentDao equipmentDao = new EquipmentDao(connection);
			equipmentTableView.getItems().setAll(equipmentDao.getNotRentedAndNotBroken());
		} catch (SQLException e)
		{
			e.printStackTrace();//TODO pokazanie błędu
		}
	}
}
