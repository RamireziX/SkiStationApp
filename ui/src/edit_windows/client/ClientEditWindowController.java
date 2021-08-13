package edit_windows.client;

import com.awrzosek.ski_station.basic.BasicUtils;
import com.awrzosek.ski_station.tables.person.client.Client;
import com.awrzosek.ski_station.tables.person.client.ClientDao;
import com.awrzosek.ski_station.tables.ski.equipment.EquipmentDao;
import com.awrzosek.ski_station.tables.ski.equipment.rent.EquipmentRent;
import com.awrzosek.ski_station.tables.ski.equipment.rent.EquipmentRentDao;
import com.awrzosek.ski_station.tables.ski.skipass.Skipass;
import com.awrzosek.ski_station.tables.ski.skipass.SkipassDao;
import com.awrzosek.ski_station.tables.ski.skipass.map.SkipassSkipassTypeMap;
import com.awrzosek.ski_station.tables.ski.skipass.map.SkipassSkipassTypeMapDao;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassType;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassTypeDao;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientEditWindowController implements Initializable {
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

	private Client currentClient;
	private TableView<Client> clientsTableView;

	public void setCurrentClient(Client currentClient)
	{
		this.currentClient = currentClient;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		setAcceptButtonAction();
		setCancelButtonAction();
		Platform.runLater(() -> {
			fillEditWindowWithCurrentValues();
			setSkipassesTableViewCellValues();
			setRentalsTableViewColumns();
		});
	}

	public void setParentTableView(TableView<Client> clientsTableView)
	{
		this.clientsTableView = clientsTableView;
	}

	private void fillEditWindowWithCurrentValues()
	{
		nameTextField.setText(currentClient.getFirstName());
		secondNameTextField.setText(currentClient.getSecondName());
		surnameTextField.setText(currentClient.getSurname());
		if (currentClient.getDateOfBirth() != null)
			dateOfBirthDatePicker.setValue(currentClient.getDateOfBirth());
		peselTextField.setText(currentClient.getPesel());
		personalIdNumberTextField.setText(currentClient.getPersonalIdNumber());
		phoneTextField.setText(currentClient.getPhone());
		emailTextField.setText(currentClient.getEMail());
		dateEnteredDatePicker.setValue(currentClient.getDateEntered());
	}

	private void setAcceptButtonAction()
	{
		acceptButton.setDefaultButton(true);
		acceptButton.setOnAction(e -> {
			Client updatedClient = new Client();
			updatedClient.setId(currentClient.getId());
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

	private void setSkipassesTableViewCellValues()
	{
		activeSkipassTableColumn.setCellValueFactory(
				data -> new ReadOnlyBooleanWrapper(data.getValue().getSkipass().isActive()));
		activeSkipassTableColumn.setCellFactory(tc -> new CheckBoxTableCell<>());
		dateFromSkipassTableColumn.setCellValueFactory(
				data -> new ReadOnlyObjectWrapper<>(data.getValue().getSkipass().getDateFrom()));
		dateToSkipassTableColumn.setCellValueFactory(
				data -> new ReadOnlyObjectWrapper<>(data.getValue().getSkipass().getDateTo()));
		discountSkipassTableColumn.setCellValueFactory(
				data -> new ReadOnlyStringWrapper(data.getValue().getSkipassType().getDiscountDescription()));

		try (Connection connection = BasicUtils.getConnection())
		{
			//TODO burdel w kodzie i optymalizacji poprawić
			SkipassDao skipassDao = new SkipassDao(connection);
			List<Skipass> skipasses = skipassDao.listByClient(currentClient);

			List<SkipassSkipassTypeMap> sstmList = new ArrayList<>();
			SkipassSkipassTypeMapDao sstmDao = new SkipassSkipassTypeMapDao(connection);
			for (Skipass s : skipasses)
				sstmDao.getBySkipass(s).ifPresent(sstmList::add);

			List<SkipassType> skipassTypes = new ArrayList<>();
			SkipassTypeDao skipassTypeDao = new SkipassTypeDao(connection);
			for (SkipassSkipassTypeMap s : sstmList)
				skipassTypeDao.get(s.getSkipassTypeId()).ifPresent(skipassTypes::add);

			List<SkipassInfo> skipassInfos = new ArrayList<>();
			int i = 0;
			for (Skipass s : skipasses)
			{
				skipassInfos.add(new SkipassInfo(s, sstmList.get(i), skipassTypes.get(i)));
				i++;
			}
			skipassesTableView.getItems().setAll(skipassInfos);
		} catch (SQLException e)
		{
			e.printStackTrace();//TODO pokazanie błędu
		}
	}

	private void setRentalsTableViewColumns()
	{
		rentDateRentalsTableColumn.setCellValueFactory(
				data -> new ReadOnlyObjectWrapper<>(data.getValue().getEquipmentRent().getRentDate()));
		returnDateRentalsTableColumn.setCellValueFactory(
				data -> new ReadOnlyObjectWrapper<>(data.getValue().getEquipmentRent().getReturnDate()));
		rentTypeRentalsTableColumn.setCellValueFactory(
				data -> new ReadOnlyObjectWrapper<>(data.getValue().getEquipmentRent().getRentType().toString()));
		nameRentalsTableColumn.setCellValueFactory(
				data -> new ReadOnlyStringWrapper(data.getValue().getEquipment().getName()));
		typeRentalsTableColumn.setCellValueFactory(
				data -> new ReadOnlyStringWrapper(data.getValue().getEquipment().getType().toString()));

		try (Connection connection = BasicUtils.getConnection())
		{
			List<EquipmentRent> rentals = new EquipmentRentDao(connection).listByClient(currentClient);
			EquipmentDao equipmentDao = new EquipmentDao(connection);
			List<RentalInfo> rentalInfos = new ArrayList<>();
			for (EquipmentRent r : rentals)
				rentalInfos.add(new RentalInfo(equipmentDao.get(r.getEquipmentId()).orElse(null), r));
			rentalsTableView.getItems().setAll(rentalInfos);
		} catch (SQLException exception)
		{
			exception.printStackTrace();//TODO
		}
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
