import com.awrzosek.ski_station.basic.BasicConsts;
import com.awrzosek.ski_station.basic.BasicUtils;
import com.awrzosek.ski_station.cong_prize_management.QueueManager;
import com.awrzosek.ski_station.cong_prize_management.SkipassPriceManager;
import com.awrzosek.ski_station.cong_prize_management.StabilityException;
import com.awrzosek.ski_station.database_management.ClientManager;
import com.awrzosek.ski_station.database_management.EmployeeManager;
import com.awrzosek.ski_station.database_management.EquipmentManager;
import com.awrzosek.ski_station.hardware_connection.HardwareConnectionManager;
import com.awrzosek.ski_station.tables.person.client.Client;
import com.awrzosek.ski_station.tables.person.client.ClientConsts;
import com.awrzosek.ski_station.tables.person.client.ClientDao;
import com.awrzosek.ski_station.tables.person.employee.Employee;
import com.awrzosek.ski_station.tables.person.employee.EmployeeDao;
import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.equipment.EquipmentDao;
import com.awrzosek.ski_station.tables.ski.skipass.Skipass;
import com.awrzosek.ski_station.tables.ski.skipass.SkipassDao;
import edit_windows.client.add.ClientAddWindowController;
import edit_windows.client.edit.ClientEditWindowController;
import edit_windows.employee.EmployeeWindowController;
import edit_windows.equipment.EquipmentWindowController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
	//TODO browsery
	@FXML
	private TextField nameClientBrowserTextField;
	@FXML
	private TextField surnameClientBrowserTextField;
	@FXML
	private TextField peselClientBrowserTextField;
	@FXML
	private TextField phoneClientBrowserTextField;
	@FXML
	private TextField emailClientBrowserTextField;
	@FXML
	private DatePicker dateEnteredClientBrowserDatePicker;
	@FXML
	private Button clientBrowserFilterButton;
	@FXML
	private Button clientBrowserClearButton;
	//TODO dodawanie dowolnej ilości pustych skipassów i max liczbe klientów na stacji od tego uzależnić
	//TODO dodawanie ulg - i to można wrzucić w funkcje dodatkowe
	@FXML
	private TableView<Skipass> activeSkipassesTableView;
	@FXML
	private TableColumn<Skipass, Long> activeSkipassIdTableColumn;
	@FXML
	private Label numberOfActiveSkipasses;
	@FXML
	private Label waitTimeLabel;
	@FXML
	private ComboBox<Skipass> skipassComboBox;
	@FXML
	private Button simulateEntryButton;
	@FXML
	private Button simulateExitButton;
	@FXML
	private Button simulateLiftButton;
	//TODO jak zdążę to można by dać 2 wyciągi np zamiast 1
	@FXML
	private TextField cParameterTextField;
	@FXML
	private TextField aParameterTextField;
	@FXML
	private TextField bParameterTextField;
	@FXML
	private TextField priceFromTextField;
	@FXML
	private TextField priceToTextField;
	@FXML
	private TextField oneDayPriceTextField;
	@FXML
	private TextField threeDaysPriceTextField;
	@FXML
	private TextField oneWeekPriceTextField;
	@FXML
	private TextField twoWeeksPriceTextField;
	@FXML
	private Button calculateSkipassPriceButton;
	@FXML
	private Button acceptSkipassPriceButton;

	@FXML
	private TableView<Employee> employeeTableView;
	@FXML
	private TableColumn<Employee, Long> employeeIdColumn;
	@FXML
	private TableColumn<Employee, String> employeeLoginColumn;
	@FXML
	private TableColumn<Employee, String> employeeFirstNameColumn;
	@FXML
	private TableColumn<Employee, String> employeeSurnameColumn;
	@FXML
	private TableColumn<Employee, String> employeePhoneColumn;
	@FXML
	private TableColumn<Employee, String> employeeEmailColumn;
	@FXML
	private Button deleteEmployeeButton;
	@FXML
	private Button editEmployeeButton;
	@FXML
	private Button addEmployeeButton;

	//TODO może też kolumna czy jest wypożyczony
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
	private Button deleteEquipmentButton;
	@FXML
	private Button editEquipmentButton;
	@FXML
	private Button addEquipmentButton;

	@FXML
	private TableView<Client> clientTableView;
	@FXML
	private TableColumn<Client, Long> clientIdColumn;
	@FXML
	private TableColumn<Client, String> clientFirstNameColumn;
	@FXML
	private TableColumn<Client, String> clientSurnameColumn;
	@FXML
	private TableColumn<Client, String> clientPeselColumn;
	@FXML
	private TableColumn<Client, LocalDate> clientDateEnteredColumn;
	@FXML
	private TableColumn<Client, String> clientPhoneColumn;
	@FXML
	private TableColumn<Client, String> clientEmailColumn;
	@FXML
	private Button deleteClientButton;
	@FXML
	private Button editClientButton;
	@FXML
	private Button addClientButton;

	private Employee loggedInEmployee;

	//TODO olać customowe inicjalizatory i pisanie o nich w pracy

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		//TODO pogrupować te calle metod w metody
		setClientsTableViewCellValues();
		setEquipmentTableViewCellValues();
		setEmployeesTableViewCellValues();

		clientTableViewDoubleClickOpenEditWindow();
		setAddClientButtonAction();
		setEditClientButtonAction();
		setDeleteClientButtonAction();
		setClientBrowserFilterButton();
		setClientBrowserClearButton();

		setCalculateSkipassPriceButtonAction();
		setAcceptSkipassPriceButtonAction();

		setSkipassComboBoxValues();
		setSimulateEntryButtonAction();
		setSimulateExitButtonAction();
		setSimulateLiftButtonAction();
		setActiveSkipassesTableViewCellValues();

		setWaitTimeLabelValue();
		setNumberOfActiveSkipassesValue();

		setAddEquipmentButtonAction();
		setEditEquipmentButtonAction();
		equipmentTableViewDoubleClickOpenEditWindow();
		setDeleteEquipmentButtonAction();

		setAddEmployeeButtonAction();
		setEditEmployeeButtonAction();
		employeeTableViewDoubleClickOpenEditWindow();
		setDeleteEmployeeButtonAction();
	}

	public void setLoggedInEmployee(Employee loggedInEmployee)
	{
		this.loggedInEmployee = loggedInEmployee;
	}

	private void setClientBrowserFilterButton()
	{
		clientBrowserFilterButton.setOnAction(e -> {
			String firstName = nameClientBrowserTextField.getText();
			String surname = surnameClientBrowserTextField.getText();
			String pesel = peselClientBrowserTextField.getText();
			String phone = phoneClientBrowserTextField.getText();
			String email = emailClientBrowserTextField.getText();
			LocalDate dateEntered = dateEnteredClientBrowserDatePicker.getValue();
			//@formatter:off
			String query =//TODO layout
					"select * from " + ClientConsts.TAB_NAME +
					" where 1=1" +
					(!firstName.equals(BasicConsts.EMPTY_STRING) ?
					 	" and " + ClientConsts.FLD_FIRST_NAME + " = '" + firstName + "'" :
						BasicConsts.EMPTY_STRING) +
					(!surname.equals(BasicConsts.EMPTY_STRING) ?
						" and " + ClientConsts.FLD_SURNAME + " = '" + surname + "'" :
						BasicConsts.EMPTY_STRING) +
					(!pesel.equals(BasicConsts.EMPTY_STRING) ?
						" and " + ClientConsts.FLD_PESEL + " = '" + pesel + "'" :
						BasicConsts.EMPTY_STRING) +
					(!phone.equals(BasicConsts.EMPTY_STRING) ?
						" and " + ClientConsts.FLD_PHONE + " = '" + phone + "'" :
						BasicConsts.EMPTY_STRING) +
					(!email.equals(BasicConsts.EMPTY_STRING) ?
						" and " + ClientConsts.FLD_E_MAIL + " = '" + email + "'" :
						BasicConsts.EMPTY_STRING) +
					(!(dateEntered == null) ?
						" and " + ClientConsts.FLD_DATE_ENTERED + " = " + Date.valueOf(dateEntered).getTime() + "" :
						BasicConsts.EMPTY_STRING);
			//@formatter:on
			try (Connection connection = BasicUtils.getConnection())
			{
				clientTableView.getItems().setAll(new ClientDao(connection).listByQuery(query));
			} catch (SQLException exception)
			{
				exception.printStackTrace();//TODO
			}
		});
	}

	private void setClientBrowserClearButton()
	{
		clientBrowserClearButton.setOnAction(e -> {
			try (Connection connection = BasicUtils.getConnection())
			{
				nameClientBrowserTextField.setText(BasicConsts.EMPTY_STRING);
				surnameClientBrowserTextField.setText(BasicConsts.EMPTY_STRING);
				peselClientBrowserTextField.setText(BasicConsts.EMPTY_STRING);
				phoneClientBrowserTextField.setText(BasicConsts.EMPTY_STRING);
				emailClientBrowserTextField.setText(BasicConsts.EMPTY_STRING);
				dateEnteredClientBrowserDatePicker.setValue(null);
				clientTableView.getItems().setAll(new ClientDao(connection).getAll());
			} catch (SQLException exception)
			{
				exception.printStackTrace();
			}
		});

	}

	private void setActiveSkipassesTableViewCellValues()
	{
		activeSkipassesTableView.setPlaceholder(new Label(BasicConsts.EMPTY_STRING));
		activeSkipassIdTableColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getId()));
		try (Connection connection = BasicUtils.getConnection())
		{
			SkipassDao skipassDao = new SkipassDao(connection);
			activeSkipassesTableView.getItems().setAll(skipassDao.getAllActive());
		} catch (SQLException e)
		{
			e.printStackTrace();//TODO pokazanie błędu
		}
	}

	private void setNumberOfActiveSkipassesValue()
	{
		numberOfActiveSkipasses.setText(String.valueOf(BasicConsts.ACTIVE_NO_OF_CLIENTS));
	}

	private void setWaitTimeLabelValue()
	{
		QueueManager queueManager = new QueueManager();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss");
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(60), e -> {
			try
			{
				Long waitTimeSeconds = queueManager.calculateWaitTime();
				LocalTime waitTime = LocalTime.ofSecondOfDay(waitTimeSeconds);
				waitTimeLabel.setText(waitTime.format(formatter));
				waitTimeLabel.setTextFill(Color.BLACK);
			} catch (StabilityException ex)
			{
				waitTimeLabel.setText(ex.getMessage());
				waitTimeLabel.setTextFill(Color.RED);
			}
			QueueManager.CLIENTS_IN_MINUTE = 0;
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	//TODO te information są dziwne
	private void setSimulateLiftButtonAction()
	{
		simulateLiftButton.setOnAction(e -> {
			try (Connection connection = BasicUtils.getConnection())
			{
				HardwareConnectionManager hcm = new HardwareConnectionManager(connection);
				Skipass skipass = skipassComboBox.getValue();
				if (!hcm.registerLift(skipass))
					new Alert(Alert.AlertType.ERROR,
							"Skipass jest nieaktywny, proszę skontaktować się z obsługą!").showAndWait();
				else
					new Alert(Alert.AlertType.INFORMATION,
							"Wjazd zarejestrowany").showAndWait();
			} catch (SQLException exception)
			{
				exception.printStackTrace();
			}
		});

	}

	private void setSimulateExitButtonAction()
	{
		simulateExitButton.setOnAction(e -> {
			try (Connection connection = BasicUtils.getConnection())
			{
				HardwareConnectionManager hcm = new HardwareConnectionManager(connection);
				Skipass skipass = skipassComboBox.getValue();
				if (!hcm.registerExit(skipass))
					new Alert(Alert.AlertType.ERROR,
							"Skipass jest nieaktywny, proszę skontaktować się z obsługą!").showAndWait();
				else
				{
					new Alert(Alert.AlertType.INFORMATION,
							"Wyjście zarejestrowane").showAndWait();
					setNumberOfActiveSkipassesValue();
					activeSkipassesTableView.getItems().setAll(hcm.getSkipassDao().getAllActive());
				}
			} catch (SQLException exception)
			{
				exception.printStackTrace();
			}
		});
	}


	private void setSimulateEntryButtonAction()
	{
		simulateEntryButton.setOnAction(e -> {
			try (Connection connection = BasicUtils.getConnection())
			{
				HardwareConnectionManager hcm = new HardwareConnectionManager(connection);
				Skipass skipass = skipassComboBox.getValue();
				if (!hcm.registerEntry(skipass))
					new Alert(Alert.AlertType.ERROR,
							"Skipass jest już aktywny, proszę skontaktować się z obsługą!").showAndWait();
				else
				{
					new Alert(Alert.AlertType.INFORMATION,
							"Wejście zarejestrowane").showAndWait();
					setNumberOfActiveSkipassesValue();
					activeSkipassesTableView.getItems().setAll(hcm.getSkipassDao().getAllActive());
				}
			} catch (SQLException exception)
			{
				exception.printStackTrace();
			}
		});
	}

	private void setSkipassComboBoxValues()
	{
		try (Connection connection = BasicUtils.getConnection())
		{
			SkipassDao skipassDao = new SkipassDao(connection);
			List<Skipass> skipasses = skipassDao.getAllRented();
			Callback<ListView<Skipass>, ListCell<Skipass>> factory = lv -> new ListCell<>() {
				@Override
				protected void updateItem(Skipass skipass, boolean empty)
				{
					super.updateItem(skipass, empty);
					setText(empty ? "" : skipass.getId().toString());
				}
			};
			skipassComboBox.setCellFactory(factory);
			skipassComboBox.setButtonCell(factory.call(null));
			skipassComboBox.getItems().setAll(skipasses);

		} catch (SQLException exception)
		{
			exception.printStackTrace();
		}
	}

	private void setAcceptSkipassPriceButtonAction()
	{
		//TODO zabezpieczenia (format, brak uzupełnionego pola)
		acceptSkipassPriceButton.setOnAction(e -> {
			BasicConsts.ONE_DAY_SKIPASS_PRICE = new BigDecimal(oneDayPriceTextField.getText());
			BasicConsts.THREE_DAYS_SKIPASS_PRICE = new BigDecimal(threeDaysPriceTextField.getText());
			BasicConsts.ONE_WEEK_SKIPASS_PRICE = new BigDecimal(oneWeekPriceTextField.getText());
			BasicConsts.TWO_WEEKS_SKIPASS_PRICE = new BigDecimal(twoWeeksPriceTextField.getText());
		});
	}

	private void setCalculateSkipassPriceButtonAction()
	{
		//TODO zabezpieczenia (format, brak uzupełnionego pola) + jakieś większe info że zaakceptowanie ok
		calculateSkipassPriceButton.setOnAction(e -> {
			SkipassPriceManager skipassPriceManager = new SkipassPriceManager();
			BigDecimal c = new BigDecimal(cParameterTextField.getText());
			BigDecimal a = new BigDecimal(aParameterTextField.getText());
			BigDecimal b = new BigDecimal(bParameterTextField.getText());
			BigDecimal priceFrom = new BigDecimal(priceFromTextField.getText());
			BigDecimal priceTo = new BigDecimal(priceToTextField.getText());

			BigDecimal oneDayPrice = skipassPriceManager.calculateSkipassPrice(c, a, b, priceFrom, priceTo);
			BigDecimal threeDaysPrice = skipassPriceManager.calculateThreeDaysPrice(oneDayPrice);
			BigDecimal oneWeekPrice = skipassPriceManager.calculateOneWeekDaysPrice(oneDayPrice);
			BigDecimal twoWeeksPrice = skipassPriceManager.calculateTwoWeeksDaysPrice(oneDayPrice);

			oneDayPriceTextField.setText(oneDayPrice.toString());
			threeDaysPriceTextField.setText(threeDaysPrice.toString());
			oneWeekPriceTextField.setText(oneWeekPrice.toString());
			twoWeeksPriceTextField.setText(twoWeeksPrice.toString());
		});
	}

	private void setDeleteEmployeeButtonAction()
	{
		deleteEmployeeButton.setOnAction(e -> {
			try (Connection connection = BasicUtils.getConnection())
			{
				EmployeeManager employeeManager = new EmployeeManager(connection);
				Employee currentEmployee = employeeTableView.getSelectionModel().getSelectedItem();
				if (!employeeManager.delete(currentEmployee, loggedInEmployee))
					new Alert(Alert.AlertType.ERROR,
							"Pracownik " + currentEmployee.getLogin() + " jest teraz zalogowany!").showAndWait();
				else
					employeeTableView.getItems().setAll(employeeManager.getEmployeeDao().getAll());
			} catch (SQLException exception)
			{
				exception.printStackTrace();
			}
		});

	}

	private void setEditEmployeeButtonAction()
	{
		editEmployeeButton.setOnAction(e -> {
			try
			{
				Employee currentEmployee = employeeTableView.getSelectionModel().getSelectedItem();
				showEditEmployeeWindow(currentEmployee);
			} catch (IOException ex)
			{
				ex.printStackTrace();//TODO
			}
		});
	}

	private void employeeTableViewDoubleClickOpenEditWindow()
	{
		employeeTableView.setRowFactory(tv -> {
			TableRow<Employee> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty())
				{
					try
					{
						Employee employee = row.getItem();
						showEditEmployeeWindow(employee);
					} catch (Exception e)
					{
						e.printStackTrace();//TODO
					}
				}
			});
			return row;
		});
	}

	private void showEditEmployeeWindow(Employee currentEmployee) throws IOException
	{
		FXMLLoader fxmlLoader =
				new FXMLLoader(getClass().getResource("edit_windows/employee/employee_window.fxml"));
		Parent parent = fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(parent));
		stage.setTitle("Edycja pracownika " + currentEmployee.getFullName());
		EmployeeWindowController employeeWindowController = fxmlLoader.getController();
		employeeWindowController.setParentTableView(employeeTableView);
		employeeWindowController.setCurrentEmployee(currentEmployee);
		stage.show();
	}

	private void setAddEmployeeButtonAction()
	{
		addEmployeeButton.setOnAction(e -> {
			try
			{
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
						"edit_windows/employee/employee_window.fxml"));
				Parent parent = fxmlLoader.load();
				Stage stage = new Stage();
				stage.setScene(new Scene(parent));
				stage.setTitle("Dodawanie pracownika");
				EmployeeWindowController employeeWindowController = fxmlLoader.getController();
				employeeWindowController.setParentTableView(employeeTableView);
				stage.show();
			} catch (IOException ex)
			{
				ex.printStackTrace();//TODO
			}
		});
	}

	private void setAddEquipmentButtonAction()
	{
		addEquipmentButton.setOnAction(e -> {
			try
			{
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
						"edit_windows/equipment/equipment_window.fxml"));
				Parent parent = fxmlLoader.load();
				Stage stage = new Stage();
				stage.setScene(new Scene(parent));
				stage.setTitle("Dodawanie sprzętu");
				EquipmentWindowController equipmentWindowController = fxmlLoader.getController();
				equipmentWindowController.setParentTableView(equipmentTableView);
				stage.show();
			} catch (IOException ex)
			{
				ex.printStackTrace();//TODO
			}
		});
	}

	private void setEditEquipmentButtonAction()
	{
		editEquipmentButton.setOnAction(e -> {
			try
			{
				Equipment currentEquipment = equipmentTableView.getSelectionModel().getSelectedItem();
				showEditEquipmentWindow(currentEquipment);
			} catch (IOException ex)
			{
				ex.printStackTrace();//TODO
			}
		});
	}

	private void equipmentTableViewDoubleClickOpenEditWindow()
	{
		equipmentTableView.setRowFactory(tv -> {
			TableRow<Equipment> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty())
				{
					try
					{
						Equipment equipment = row.getItem();
						showEditEquipmentWindow(equipment);
					} catch (Exception e)
					{
						e.printStackTrace();//TODO
					}
				}
			});
			return row;
		});
	}

	private void showEditEquipmentWindow(Equipment currentEquipment) throws IOException
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"edit_windows/equipment/equipment_window.fxml"));
		Parent parent = fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(parent));
		stage.setTitle("Edycja sprzętu " + currentEquipment.getName());
		EquipmentWindowController equipmentWindowController = fxmlLoader.getController();
		equipmentWindowController.setParentTableView(equipmentTableView);
		equipmentWindowController.setCurrentEquipment(currentEquipment);
		stage.show();
	}

	private void setDeleteEquipmentButtonAction()
	{
		deleteEquipmentButton.setOnAction(e -> {
			Equipment equipment = equipmentTableView.getSelectionModel().getSelectedItem();
			try (Connection connection = BasicUtils.getConnection())
			{
				EquipmentManager equipmentManager = new EquipmentManager(connection);
				if (!equipmentManager.removeEquipment(equipment))
					new Alert(Alert.AlertType.ERROR,
							"Sprzęt jest wypożyczony przez klienta, nie można usunąć!").showAndWait();
				else
					equipmentTableView.getItems().setAll(equipmentManager.getEquipmentDao().getAll());
			} catch (SQLException exception)
			{
				exception.printStackTrace();//TODO
			}
		});
	}

	private void setAddClientButtonAction()
	{
		addClientButton.setOnAction(e -> {
			try
			{
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
						"edit_windows/client/add/client_add_window.fxml"));
				Parent parent = fxmlLoader.load();
				Stage stage = new Stage();
				stage.setScene(new Scene(parent));
				stage.setTitle("Dodawanie klienta");
				ClientAddWindowController clientAddWindowController = fxmlLoader.getController();
				clientAddWindowController.setParentTableView(clientTableView);
				stage.show();
			} catch (IOException ex)
			{
				ex.printStackTrace();//TODO
			}
		});
	}

	private void setDeleteClientButtonAction()
	{
		deleteClientButton.setOnAction(e -> {
			Client client = clientTableView.getSelectionModel().getSelectedItem();
			try (Connection connection = BasicUtils.getConnection())
			{
				ClientManager clientManager = new ClientManager(connection);
				if (!clientManager.removeClient(client))
					new Alert(Alert.AlertType.ERROR,
							"Klient ma wypożyczony sprzęt, który należy zwrócić!").showAndWait();
				else
					ClientEditWindowController.refreshClientsTableView(clientManager.getClientDao(), clientTableView);
			} catch (SQLException exception)
			{
				exception.printStackTrace();//TODO
			}
		});
	}

	private void setEditClientButtonAction()
	{
		editClientButton.setOnAction(e -> {
			try
			{
				Client client = clientTableView.getSelectionModel().getSelectedItem();
				showEditClientWindow(client);
			} catch (Exception ex)
			{
				ex.printStackTrace();//TODO
			}
		});
	}

	private void clientTableViewDoubleClickOpenEditWindow()
	{
		clientTableView.setRowFactory(tv -> {
			TableRow<Client> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty())
				{
					try
					{
						Client client = row.getItem();
						showEditClientWindow(client);
					} catch (Exception e)
					{
						e.printStackTrace();//TODO
					}
				}
			});
			return row;
		});
	}

	private void showEditClientWindow(Client client) throws IOException
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"edit_windows/client/edit/client_edit_window.fxml"));
		Parent parent = fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(parent));
		ClientEditWindowController clientEditWindowController = fxmlLoader.getController();
		clientEditWindowController.setParentTableView(clientTableView);
		clientEditWindowController.setCurrentClient(client);
		stage.setTitle("Edycja klienta: " + client.getFullName());
		stage.show();
	}

	private void setClientsTableViewCellValues()
	{
		clientIdColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getId()));
		clientFirstNameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFirstName()));
		clientSurnameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getSurname()));
		clientPeselColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getPesel()));
		clientDateEnteredColumn
				.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getDateEntered()));
		clientPhoneColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getPhone()));
		clientEmailColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEMail()));

		try (Connection connection = BasicUtils.getConnection())
		{
			ClientDao clientDao = new ClientDao(connection);
			clientTableView.getItems().setAll(clientDao.getAll());
		} catch (SQLException e)
		{
			e.printStackTrace();//TODO pokazanie błędu
		}
	}

	private void setEquipmentTableViewCellValues()
	{
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
			equipmentTableView.getItems().setAll(equipmentDao.getAll());
		} catch (SQLException e)
		{
			e.printStackTrace();//TODO pokazanie błędu
		}
	}

	private void setEmployeesTableViewCellValues()
	{
		employeeIdColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getId()));
		employeeLoginColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getLogin()));
		employeeFirstNameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFirstName()));
		employeeSurnameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getSurname()));
		employeePhoneColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getPhone()));
		employeeEmailColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEMail()));

		try (Connection connection = BasicUtils.getConnection())
		{
			EmployeeDao employeeDao = new EmployeeDao(connection);
			employeeTableView.getItems().setAll(employeeDao.getAll());
		} catch (SQLException e)
		{
			e.printStackTrace();//TODO pokazanie błędu
		}
	}
}
