import com.awrzosek.ski_station.basic.BasicUtils;
import com.awrzosek.ski_station.tables.person.client.Client;
import com.awrzosek.ski_station.tables.person.client.ClientDao;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controller implements Initializable {

	//TODO może można te pola opakować w klasy i tutaj robić kontrolę, albo nawet tylko metody wołać
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
		setClientsTableViewCellValues();
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
}
