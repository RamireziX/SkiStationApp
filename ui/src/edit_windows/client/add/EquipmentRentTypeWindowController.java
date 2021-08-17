package edit_windows.client.add;

import com.awrzosek.ski_station.basic.BasicUtils;
import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.equipment.EquipmentDao;
import com.awrzosek.ski_station.tables.ski.equipment.rent.RentType;
import edit_windows.client.edit.ClientEditWindowController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class EquipmentRentTypeWindowController implements Initializable {
	@FXML
	private Button acceptButton;
	@FXML
	private ComboBox<RentType> rentTypeComboBox;

	private TableView<Equipment> equipmentTableView;
	private ClientAddWindowController parentController;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		setRentTypeComboBox();
		setAcceptButtonAction();
	}

	public void setEquipmentTableView(TableView<Equipment> equipmentTableView)
	{
		this.equipmentTableView = equipmentTableView;
	}

	public void setParentController(ClientAddWindowController parentController)
	{
		this.parentController = parentController;
	}

	private void setAcceptButtonAction()
	{
		acceptButton.setOnAction(e -> {
			RentType rentType = rentTypeComboBox.getValue();
			List<Equipment> selectedEquipment = equipmentTableView.getSelectionModel().getSelectedItems();
			HashMap<Equipment, RentType> equipmentToRentType = new HashMap<>();
			selectedEquipment.forEach(eq -> equipmentToRentType.put(eq, rentType));
			parentController.addEquipmentToRent(equipmentToRentType);
			Stage stage = ClientEditWindowController.getStage(acceptButton);
			try (Connection connection = BasicUtils.getConnection())
			{
				refreshEquipmentTableView(equipmentTableView, selectedEquipment, connection);
			} catch (SQLException exception)
			{
				exception.printStackTrace();//TODO
			}
			stage.close();
		});
	}

	private void setRentTypeComboBox()
	{
		Callback<ListView<RentType>, ListCell<RentType>> factory = lv -> new ListCell<>() {
			@Override
			protected void updateItem(RentType rentType, boolean empty)
			{
				super.updateItem(rentType, empty);
				setText(empty ? "" : rentType.toString());
			}
		};
		rentTypeComboBox.setCellFactory(factory);
		rentTypeComboBox.setButtonCell(factory.call(null));
		rentTypeComboBox.getItems().setAll(RentType.values());
	}

	private void refreshEquipmentTableView(TableView<Equipment> equipmentTableView, List<Equipment> selectedEquipment, Connection connection)
			throws SQLException
	{
		EquipmentDao equipmentDao = new EquipmentDao(connection);
		equipmentTableView.getItems().setAll(equipmentDao.getNotRentedAndNotBroken(selectedEquipment));
	}
}
