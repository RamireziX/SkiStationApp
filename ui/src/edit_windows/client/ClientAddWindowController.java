package edit_windows.client;

import com.awrzosek.ski_station.basic.BasicUtils;
import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.equipment.EquipmentDao;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
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

	//TODO 1 - tak jak ten podstawowy eq table view
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

	//TODO 2 akcje buttonów
	@FXML
	private Button acceptButton;
	@FXML
	private Button cancelButton;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		Platform.runLater(this::setEquipmentTableViewCellValues);
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
