package edit_windows.employee;

import com.awrzosek.ski_station.basic.BasicUtils;
import com.awrzosek.ski_station.database_management.EmployeeManager;
import com.awrzosek.ski_station.tables.person.employee.Employee;
import edit_windows.client.edit.ClientEditWindowController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeWindowController implements Initializable {
	@FXML
	private TextField firstNameTextField;
	@FXML
	private TextField secondNameTextField;
	@FXML
	private TextField surnameTextField;
	@FXML
	private TextField peselTextField;
	@FXML
	private TextField personalIdNumberTextField;
	@FXML
	private TextField phoneTextField;
	@FXML
	private TextField emailTextField;
	@FXML
	private DatePicker dateofBirthDatePicker;

	@FXML
	private TextField streetNameTextField;
	@FXML
	private TextField buildingNrTextField;
	@FXML
	private TextField cityTextField;
	@FXML
	private TextField zipcodeTextField;
	@FXML
	private TextField streetNrTextField;
	@FXML
	private TextField aptNrTextField;
	@FXML
	private TextField voivodeshipTextField;

	@FXML
	private TextField accountNrTextField;
	@FXML
	private TextField bankNameTextField;

	@FXML
	private TextField loginTextField;
	@FXML
	private PasswordField passwdPasswordField;

	@FXML
	private Button acceptButton;
	@FXML
	private Button cancelButton;

	private TableView<Employee> parentTableView;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		setAcceptOnAddButtonAction();
		setCancelButtonAction();
	}

	public void setParentTableView(TableView<Employee> parentTableView)
	{
		this.parentTableView = parentTableView;
	}

	private void setAcceptOnAddButtonAction()
	{
		acceptButton.setDefaultButton(true);
		acceptButton.setOnAction(e -> {
			Employee employee = new Employee();
			setPersonalInfo(employee);
			setAddressInfo(employee);
			setLoginInfo(employee);
			setPaymentInfo(employee);
			Stage stage = ClientEditWindowController.getStage(acceptButton);
			try (Connection connection = BasicUtils.getConnection())
			{
				EmployeeManager employeeManager = new EmployeeManager(connection);
				if (employeeManager.add(employee))
				{
					parentTableView.getItems().setAll(employeeManager.getEmployeeDao().getAll());
					stage.close();
				} else
					new Alert(Alert.AlertType.ERROR,
							"Login \"" + employee.getLogin() + "\" jest już zajęty!").showAndWait();
			} catch (SQLException exception)
			{
				exception.printStackTrace();
				stage.close();
			}
		});
	}

	private void setPaymentInfo(Employee employee)
	{
		employee.setAccountNr(accountNrTextField.getText());
		employee.setBankName(bankNameTextField.getText());
	}

	private void setLoginInfo(Employee employee)
	{
		employee.setLogin(loginTextField.getText());
		employee.setPasswd(passwdPasswordField.getText());
	}

	private void setAddressInfo(Employee employee)
	{
		employee.setBuildingNr(buildingNrTextField.getText());
		employee.setAptNr(aptNrTextField.getText());
		employee.setStreetNr(streetNrTextField.getText());
		employee.setStreetName(streetNameTextField.getText());
		employee.setCity(cityTextField.getText());
		employee.setVoivodeship(voivodeshipTextField.getText());
		employee.setZipcode(zipcodeTextField.getText());
	}

	private void setPersonalInfo(Employee employee)
	{
		employee.setFirstName(firstNameTextField.getText());
		employee.setSecondName(secondNameTextField.getText());
		employee.setSurname(surnameTextField.getText());
		if (dateofBirthDatePicker.getValue() != null)
			employee.setDateOfBirth(dateofBirthDatePicker.getValue());
		employee.setPesel(peselTextField.getText());
		employee.setPersonalIdNumber(personalIdNumberTextField.getText());
		employee.setPhone(phoneTextField.getText());
		employee.setEMail(emailTextField.getText());
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
}
