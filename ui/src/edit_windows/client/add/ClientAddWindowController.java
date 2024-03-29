package edit_windows.client.add;

import com.awrzosek.ski_station.basic.BasicUtils;
import com.awrzosek.ski_station.database_management.ClientManager;
import com.awrzosek.ski_station.tables.person.client.Client;
import com.awrzosek.ski_station.tables.person.client.ClientDao;
import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.equipment.EquipmentDao;
import com.awrzosek.ski_station.tables.ski.equipment.rent.RentType;
import com.awrzosek.ski_station.tables.ski.skipass.map.Duration;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassType;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassTypeDao;
import edit_windows.client.EquipmentRentTypeWindowController;
import edit_windows.client.edit.ClientEditWindowController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

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
	private ChoiceBox<Integer> firstDiscountNumberChoiceBox;
	@FXML
	private ComboBox<SkipassType> firstDiscountTypeComboBox;
	@FXML
	private ChoiceBox<Integer> secondDiscountNumberChoiceBox;
	@FXML
	private ComboBox<SkipassType> secondDiscountTypeComboBox;
	@FXML
	private ChoiceBox<Integer> thirdDiscountNumberChoiceBox;
	@FXML
	private ComboBox<SkipassType> thirdDiscountTypeComboBox;

	@FXML
	private Button acceptButton;
	@FXML
	private Button cancelButton;
	@FXML
	private Button rentEquipmentButton;

	private TableView<Client> clientsTableView;
	private HashMap<Equipment, RentType> equipmentToRentType;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		equipmentToRentType = new HashMap<>();
		setAcceptButtonAction();
		setCancelButtonAction();
		setRentEquipmentButtonAction();
		dateEnteredDatePicker.setValue(LocalDate.now());
		setEquipmentTableViewCellValues();
		setDurationComboBox();
		setSkipassNumberChoiceBoxes();
		setDiscountTypeComboBoxes();
	}

	public void addEquipmentToRent(HashMap<Equipment, RentType> toAdd)
	{
		toAdd.forEach(equipmentToRentType::putIfAbsent);
	}

	public void setParentTableView(TableView<Client> clientsTableView)
	{
		this.clientsTableView = clientsTableView;
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
				equipmentRentTypeWindowController.setEquipmentTableView(equipmentTableView);
				equipmentRentTypeWindowController.setParentAddWindowController(this);
				stage.show();
			} catch (IOException ex)
			{
				ex.printStackTrace();//TODO
			}
		});
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
			Duration duration = Duration.valueOf(durationChoiceBox.getValue());
			List<SkipassType> skipassTypes = new ArrayList<>();

			for (int i = 0; i < Optional.ofNullable(firstDiscountNumberChoiceBox).map(ChoiceBox::getValue).orElse(0);
				 i++)
				skipassTypes.add(firstDiscountTypeComboBox.getValue());

			for (int i = 0; i < Optional.ofNullable(secondDiscountNumberChoiceBox).map(ChoiceBox::getValue).orElse(0);
				 i++)
				skipassTypes.add(secondDiscountTypeComboBox.getValue());

			for (int i = 0; i < Optional.ofNullable(thirdDiscountNumberChoiceBox).map(ChoiceBox::getValue).orElse(0);
				 i++)
				skipassTypes.add(thirdDiscountTypeComboBox.getValue());

			Stage stage = ClientEditWindowController.getStage(acceptButton);
			try (Connection connection = BasicUtils.getConnection())
			{
				//TODO jakieś zapytanie czy na pewno
				ClientManager clientManager = new ClientManager(connection);
				ClientDao clientDao = clientManager.getClientDao();
				if (clientManager.addClient(client, equipmentToRentType, skipassTypes, duration))
				{
					stage.close();
					ClientEditWindowController.refreshClientsTableView(clientDao, clientsTableView);
				} else
					new Alert(Alert.AlertType.ERROR,
							"Brak dostępnych skipassów!").showAndWait();
			} catch (SQLException exception)
			{
				exception.printStackTrace();//TODO
				stage.close();
			}
		});
	}

	private void setDurationComboBox()
	{
		List<Integer> durationIntValues = new ArrayList<>();
		for (Duration d : Duration.values())
			durationIntValues.add(d.getDays());

		durationChoiceBox.getItems().setAll(durationIntValues);
	}

	private void setSkipassNumberChoiceBoxes()
	{
		Integer[] skipassNumbers = {1, 2, 3, 4, 5, 6};
		firstDiscountNumberChoiceBox.getItems().setAll(Arrays.asList(skipassNumbers));
		secondDiscountNumberChoiceBox.getItems().setAll(Arrays.asList(skipassNumbers));
		thirdDiscountNumberChoiceBox.getItems().setAll(Arrays.asList(skipassNumbers));
	}

	private void setDiscountTypeComboBoxes()
	{
		try (Connection connection = BasicUtils.getConnection())
		{
			SkipassTypeDao skipassTypeDao = new SkipassTypeDao(connection);
			List<SkipassType> skipassTypes = skipassTypeDao.getAll();
			Callback<ListView<SkipassType>, ListCell<SkipassType>> factory = lv -> new ListCell<>() {
				@Override
				protected void updateItem(SkipassType skipassType, boolean empty)
				{
					super.updateItem(skipassType, empty);
					setText(empty ? "" : skipassType.getDiscountDescription());
				}
			};
			firstDiscountTypeComboBox.setCellFactory(factory);
			firstDiscountTypeComboBox.setButtonCell(factory.call(null));
			firstDiscountTypeComboBox.getItems().setAll(skipassTypes);
			secondDiscountTypeComboBox.setCellFactory(factory);
			secondDiscountTypeComboBox.setButtonCell(factory.call(null));
			secondDiscountTypeComboBox.getItems().setAll(skipassTypes);
			thirdDiscountTypeComboBox.setCellFactory(factory);
			thirdDiscountTypeComboBox.setButtonCell(factory.call(null));
			thirdDiscountTypeComboBox.getItems().setAll(skipassTypes);
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
