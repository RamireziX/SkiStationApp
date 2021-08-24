package edit_windows.equipment;

import com.awrzosek.ski_station.tables.ski.equipment.Condition;
import com.awrzosek.ski_station.tables.ski.equipment.EquipmentType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEquipmentWindowController implements Initializable {
	//TODO poniższe
	@FXML
	private TextField serialNumberTextField;
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField rentPriceTextField;
	@FXML
	private ComboBox<EquipmentType> typeComboBox;
	@FXML
	private ComboBox<Condition> conditionComboBox;

	@FXML
	private Button acceptButton;
	@FXML
	private Button cancelButton;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{

	}
}
