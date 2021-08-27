package edit_windows.employee;

import com.awrzosek.ski_station.tables.person.employee.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
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

	}

	public void setParentTableView(TableView<Employee> parentTableView)
	{
		this.parentTableView = parentTableView;
	}
}
