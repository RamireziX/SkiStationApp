import com.awrzosek.ski_station.basic.BasicUtils;
import com.awrzosek.ski_station.tables.person.client.Client;
import com.awrzosek.ski_station.tables.person.client.ClientDao;
import com.awrzosek.ski_station.tables.person.employee.Employee;
import com.awrzosek.ski_station.tables.person.employee.EmployeeDao;
import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.equipment.EquipmentDao;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controller implements Initializable {
	//TODO może trzeba będzie jakoś trzymać id, może jako niewidzialną kolumnę (i potem get wołać po 2kliku)
	//employees table
	@FXML
	private TableView<Employee> employeesTableView;
	@FXML
	private TableColumn<Employee, String> employeeFirstNameColumn;
	@FXML
	private TableColumn<Employee, String> employeeSurnameColumn;
	@FXML
	private TableColumn<Employee, String> employeePhoneColumn;
	@FXML
	private TableColumn<Employee, String> employeeEmailColumn;
	//equipment table
	@FXML
	private TableView<Equipment> equipmentTableView;
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
	//clients table
	@FXML
	private TableView<Client> clientsTableView;
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

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		//TODO może inicjować dopiero jak user kliknie w tab - zobaczy się
		setClientsTableViewCellValues();
		setEquipmentTableViewCellValues();
		setEmployeesTableViewCellValues();
	}

	private void setClientsTableViewCellValues()
	{
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
			clientsTableView.getItems().setAll(clientDao.getAll());
		} catch (SQLException e)
		{
			e.printStackTrace();//TODO pokazanie błędu
		}
	}

	private void setEquipmentTableViewCellValues()
	{
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
		employeeFirstNameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFirstName()));
		employeeSurnameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getSurname()));
		employeePhoneColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getPhone()));
		employeeEmailColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEMail()));

		try (Connection connection = BasicUtils.getConnection())
		{
			EmployeeDao employeeDao = new EmployeeDao(connection);
			employeesTableView.getItems().setAll(employeeDao.getAll());
		} catch (SQLException e)
		{
			e.printStackTrace();//TODO pokazanie błędu
		}
	}
}
