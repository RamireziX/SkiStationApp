package edit_windows.equipment;

import com.awrzosek.ski_station.tables.ski.equipment.Condition;
import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.equipment.EquipmentType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class EquipmentAddWindowController implements Initializable {
	//TODO poniższe
	@FXML
	private TextField serialNumberTextField;
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField rentPriceTextField;
	@FXML
	private ComboBox<EquipmentType> typeComboBox;//TODO zapełnić combo boxy
	@FXML
	private ComboBox<Condition> conditionComboBox;

	@FXML
	private Button acceptButton;
	@FXML
	private Button cancelButton;

	private TableView<Equipment> parentTableView;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		setTypeComboBoxValues();
		setConditionComboBoxValues();
	}

	public void setParentTableView(TableView<Equipment> parentTableView)
	{
		this.parentTableView = parentTableView;
	}

	private void setTypeComboBoxValues()
	{
		Callback<ListView<EquipmentType>, ListCell<EquipmentType>> factory = lv -> new ListCell<>() {
			@Override
			protected void updateItem(EquipmentType equipmentType, boolean empty)
			{
				super.updateItem(equipmentType, empty);
				setText(empty ? "" : equipmentType.toString());
			}
		};
		typeComboBox.setCellFactory(factory);
		typeComboBox.setButtonCell(factory.call(null));
		typeComboBox.getItems().setAll(EquipmentType.values());
	}

	private void setConditionComboBoxValues()
	{
		Callback<ListView<Condition>, ListCell<Condition>> factory = lv -> new ListCell<>() {
			@Override
			protected void updateItem(Condition condition, boolean empty)
			{
				super.updateItem(condition, empty);
				setText(empty ? "" : condition.toString());
			}
		};
		conditionComboBox.setCellFactory(factory);
		conditionComboBox.setButtonCell(factory.call(null));
		conditionComboBox.getItems().setAll(Condition.values());
	}
}
