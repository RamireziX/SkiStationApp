package edit_windows.client;

import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
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

	//TODO dodać kolumny
	@FXML
	private TableView<Equipment> equipmentTableView;
	@FXML
	private TableColumn<Equipment, Long> idEquipmentTableColumn;

	@FXML
	private Button acceptButton;
	@FXML
	private Button cancelButton;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{

	}
}
