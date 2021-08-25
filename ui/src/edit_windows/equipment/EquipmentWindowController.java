package edit_windows.equipment;

import com.awrzosek.ski_station.basic.BasicUtils;
import com.awrzosek.ski_station.database_management.EquipmentManager;
import com.awrzosek.ski_station.tables.ski.equipment.Condition;
import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.equipment.Type;
import edit_windows.client.edit.ClientEditWindowController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EquipmentWindowController implements Initializable {
	@FXML
	private TextField serialNumberTextField;
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField rentPriceTextField;
	@FXML
	private ComboBox<Type> typeComboBox;
	@FXML
	private ComboBox<Condition> conditionComboBox;

	@FXML
	private Button acceptButton;
	@FXML
	private Button cancelButton;

	private TableView<Equipment> parentTableView;
	private Equipment currentEquipment;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		setTypeComboBoxValues();
		setConditionComboBoxValues();
		setCancelButtonAction();
		acceptButton.setDefaultButton(true);
		Platform.runLater(() -> {
			if (currentEquipment != null)
			{
				fillEditWindowWithCurrentValues();
				setAcceptOnEditButtonAction();
			} else
				setAcceptOnAddButtonAction();
		});
	}

	public void setParentTableView(TableView<Equipment> parentTableView)
	{
		this.parentTableView = parentTableView;
	}

	public void setCurrentEquipment(Equipment currentEquipment)
	{
		this.currentEquipment = currentEquipment;
	}

	private void fillEditWindowWithCurrentValues()
	{
		serialNumberTextField.setText(currentEquipment.getSerialNumber());
		nameTextField.setText(currentEquipment.getName());
		typeComboBox.setValue(currentEquipment.getType());
		rentPriceTextField.setText(
				currentEquipment.getRentPrice().setScale(2, RoundingMode.HALF_UP).toString());
		conditionComboBox.setValue(currentEquipment.getCondition());
	}

	private void setAcceptOnEditButtonAction()
	{
		acceptButton.setOnAction(e -> {
			currentEquipment.setSerialNumber(serialNumberTextField.getText());
			currentEquipment.setName(nameTextField.getText());
			currentEquipment.setRentPrice(new BigDecimal(rentPriceTextField.getText()));
			currentEquipment.setCondition(conditionComboBox.getValue());
			currentEquipment.setType(typeComboBox.getValue());
			Stage stage = ClientEditWindowController.getStage(acceptButton);
			try (Connection connection = BasicUtils.getConnection())
			{
				EquipmentManager equipmentManager = new EquipmentManager(connection);
				equipmentManager.editEquipment(currentEquipment);
				stage.close();
				parentTableView.getItems().setAll(equipmentManager.getEquipmentDao().getAll());
			} catch (SQLException exception)
			{
				exception.printStackTrace();
				stage.close();
			}
		});
	}

	private void setAcceptOnAddButtonAction()
	{
		acceptButton.setOnAction(e -> {
			Equipment equipment = new Equipment();
			equipment.setSerialNumber(serialNumberTextField.getText());
			equipment.setName(nameTextField.getText());
			equipment.setRentPrice(new BigDecimal(rentPriceTextField.getText()));
			equipment.setCondition(conditionComboBox.getValue());
			equipment.setType(typeComboBox.getValue());
			Stage stage = ClientEditWindowController.getStage(acceptButton);
			try (Connection connection = BasicUtils.getConnection())
			{
				EquipmentManager equipmentManager = new EquipmentManager(connection);
				equipmentManager.addEquipment(equipment);
				stage.close();
				parentTableView.getItems().setAll(equipmentManager.getEquipmentDao().getAll());
			} catch (SQLException exception)
			{
				exception.printStackTrace();
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

	private void setTypeComboBoxValues()
	{
		Callback<ListView<Type>, ListCell<Type>> factory = lv -> new ListCell<>() {
			@Override
			protected void updateItem(Type equipmentType, boolean empty)
			{
				super.updateItem(equipmentType, empty);
				setText(empty ? "" : equipmentType.toString());
			}
		};
		typeComboBox.setCellFactory(factory);
		typeComboBox.setButtonCell(factory.call(null));
		typeComboBox.getItems().setAll(Type.values());
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
