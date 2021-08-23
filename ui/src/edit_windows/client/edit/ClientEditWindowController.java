package edit_windows.client.edit;

import com.awrzosek.ski_station.basic.BasicUtils;
import com.awrzosek.ski_station.database_management.ClientManager;
import com.awrzosek.ski_station.tables.person.client.Client;
import com.awrzosek.ski_station.tables.person.client.ClientDao;
import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.equipment.EquipmentDao;
import com.awrzosek.ski_station.tables.ski.equipment.rent.EquipmentRent;
import com.awrzosek.ski_station.tables.ski.equipment.rent.EquipmentRentDao;
import com.awrzosek.ski_station.tables.ski.equipment.rent.RentType;
import com.awrzosek.ski_station.tables.ski.skipass.Skipass;
import com.awrzosek.ski_station.tables.ski.skipass.SkipassDao;
import com.awrzosek.ski_station.tables.ski.skipass.map.SkipassSkipassTypeMap;
import com.awrzosek.ski_station.tables.ski.skipass.map.SkipassSkipassTypeMapDao;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassType;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassTypeDao;
import edit_windows.client.EquipmentRentTypeWindowController;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class ClientEditWindowController implements Initializable {
	@FXML
	private TableView<Equipment> availableEquipmentTableView;
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
	private Button rentEquipmentButton;
	//TODO dodawanie skipassu z poziomu edycji

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
	private Button addSkipassButton;
	//TODO te buttony - moze dodać metody w client manager //potem do fr bedzie można dopisać
	@FXML
	private Button unlinkSkipassesButton;

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
	private Button returnEquipmentButton;
	@FXML
	private Button returnAllRentedEquipmentButton;

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

	//TODO potem te metody można do jakiejś klasy utils dać
	public static Stage getStage(Button button)
	{
		return (Stage) button.getScene().getWindow();
	}

	public static void refreshClientsTableView(ClientDao clientDao, TableView<Client> clientsTableView)
			throws SQLException
	{
		clientsTableView.getItems().setAll(clientDao.getAll());
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		setAcceptButtonAction();
		setCancelButtonAction();
		setReturnEquipmentButtonAction();
		setReturnAllRentedEquipmentButtonAction();
		setRentEquipmentButtonAction();
		setUnlinkSkipassesButtonAction();
		Platform.runLater(() -> {
			fillEditWindowWithCurrentValues();
			setSkipassesTableViewCellValues();
			setRentalsTableViewColumns();
			setAvailableEquipmentTableViewCellValues();
		});
	}

	public void setCurrentClient(Client currentClient)
	{
		this.currentClient = currentClient;
	}

	public void setParentTableView(TableView<Client> clientsTableView)
	{
		this.clientsTableView = clientsTableView;
	}

	public void addRentedEquipment(HashMap<Equipment, RentType> equipmentToRentType)
	{
		try (Connection connection = BasicUtils.getConnection())
		{
			ClientManager clientManager = new ClientManager(connection);
			clientManager.addRentedEquipment(currentClient, equipmentToRentType);
			refreshRentalsTableView(connection);
		} catch (SQLException exception)
		{
			exception.printStackTrace();//TODO
		}
	}

	private void setReturnEquipmentButtonAction()
	{
		returnEquipmentButton.setOnAction(e -> {
			try (Connection connection = BasicUtils.getConnection())
			{
				RentalInfo rentalInfo = rentalsTableView.getSelectionModel().getSelectedItem();
				ClientManager clientManager = new ClientManager(connection);
				clientManager.removeRentedEquipment(rentalInfo.getEquipmentRent());
				refreshRentalsTableView(connection);
			} catch (SQLException exception)
			{
				exception.printStackTrace();//TODO
			}
		});
	}

	private void setReturnAllRentedEquipmentButtonAction()
	{
		returnAllRentedEquipmentButton.setOnAction(e -> {
			try (Connection connection = BasicUtils.getConnection())
			{
				ClientManager clientManager = new ClientManager(connection);
				clientManager.removeAllRentedEquipment(currentClient);
				refreshRentalsTableView(connection);
			} catch (SQLException exception)
			{
				exception.printStackTrace();//TODO
			}
		});
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
				refreshClientsTableView(clientDao, clientsTableView);
			} catch (SQLException exception)
			{
				exception.printStackTrace();//TODO
				stage.close();
			}
		});
	}

	private void setSkipassesTableViewCellValues()
	{
		skipassesTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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
			refreshSkipassesTableView(connection);
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
			refreshRentalsTableView(connection);
		} catch (SQLException exception)
		{
			exception.printStackTrace();//TODO
		}
	}

	private void refreshRentalsTableView(Connection connection) throws SQLException
	{
		List<EquipmentRent> rentals = new EquipmentRentDao(connection).listByClient(currentClient);
		EquipmentDao equipmentDao = new EquipmentDao(connection);
		List<RentalInfo> rentalInfos = new ArrayList<>();
		for (EquipmentRent r : rentals)
			rentalInfos.add(new RentalInfo(equipmentDao.get(r.getEquipmentId()).orElse(null), r));
		rentalsTableView.getItems().setAll(rentalInfos);
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

	private void setAvailableEquipmentTableViewCellValues()
	{
		availableEquipmentTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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
			availableEquipmentTableView.getItems().setAll(equipmentDao.getNotRentedAndNotBroken());
		} catch (SQLException e)
		{
			e.printStackTrace();//TODO pokazanie błędu
		}
	}

	private void setRentEquipmentButtonAction()
	{
		rentEquipmentButton.setOnAction(e -> {
			try
			{
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
						"../equipment_rent_type_window.fxml"));
				Parent parent = fxmlLoader.load();
				Stage stage = new Stage();
				stage.setScene(new Scene(parent));
				stage.setTitle("Typ wypożyczenia");
				EquipmentRentTypeWindowController equipmentRentTypeWindowController = fxmlLoader.getController();
				equipmentRentTypeWindowController.setEquipmentTableView(availableEquipmentTableView);
				equipmentRentTypeWindowController.setParentEditWindowController(this);
				stage.show();
			} catch (IOException ex)
			{
				ex.printStackTrace();//TODO
			}
		});
	}

	private void setUnlinkSkipassesButtonAction()
	{
		unlinkSkipassesButton.setOnAction(e -> {
			try (Connection connection = BasicUtils.getConnection())
			{
				ClientManager clientManager = new ClientManager(connection);
				List<SkipassInfo> skipassInfos = skipassesTableView.getSelectionModel().getSelectedItems();
				List<Skipass> skipasses = new ArrayList<>();
				for (SkipassInfo si : skipassInfos)
					skipasses.add(si.getSkipass());

				if (!clientManager.unlinkSelectedSkipasses(currentClient, skipasses))
					new Alert(Alert.AlertType.ERROR,
							"Nie można usunąć skipassu/ów!\nKlient musi posiadać przynajmniej jeden skipass!").showAndWait();
				else
					refreshSkipassesTableView(connection);

			} catch (SQLException ex)
			{
				ex.printStackTrace();//TODO pokazanie błędu
			}
		});
	}

	private void refreshSkipassesTableView(Connection connection) throws SQLException
	{
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
	}
}
