package edit_windows.client.edit;

import com.awrzosek.ski_station.basic.BasicUtils;
import com.awrzosek.ski_station.database_management.ClientManager;
import com.awrzosek.ski_station.tables.person.client.Client;
import com.awrzosek.ski_station.tables.ski.skipass.map.Duration;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassType;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassTypeDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddSkipassWindowController implements Initializable {
	@FXML
	private Button acceptButton;
	@FXML
	private Button cancelButton;
	//TODO buttony
	@FXML
	private ChoiceBox<Integer> durationChoiceBox;
	@FXML
	private ComboBox<SkipassType> discountTypeComboBox;

	private Client currentClient;
	private ClientEditWindowController parentWindowController;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		setDurationComboBox();
		setDiscountTypeComboBoxes();
		setCancelButtonAction();
		setAcceptButtonAction();
	}

	public void setCurrentClient(Client currentClient)
	{
		this.currentClient = currentClient;
	}

	public void setParentWindow(ClientEditWindowController parentWindowController)
	{
		this.parentWindowController = parentWindowController;
	}

	private void setAcceptButtonAction()
	{
		acceptButton.setOnAction(e -> {
			Duration duration = Duration.valueOf(durationChoiceBox.getValue());
			SkipassType skipassType = discountTypeComboBox.getValue();
			Stage stage = ClientEditWindowController.getStage(acceptButton);
			try (Connection connection = BasicUtils.getConnection())
			{
				ClientManager clientManager = new ClientManager(connection);
				if (!clientManager.initialiseSkipass(currentClient, skipassType, duration))
					new Alert(Alert.AlertType.ERROR,
							"Brak dostępnych skipassów!").showAndWait();
				else
				{
					parentWindowController.refreshSkipassesTableView(connection);
					stage.close();
				}
			} catch (SQLException exception)
			{
				exception.printStackTrace();//TODO
				stage.close();
			}
		});
	}

	private void setDurationComboBox()
	{
		List<Integer> durationIntValues = new ArrayList<>();
		for (Duration d : Duration.values())
			durationIntValues.add(d.getDays());

		durationChoiceBox.getItems().setAll(durationIntValues);
	}

	private void setDiscountTypeComboBoxes()
	{
		try (Connection connection = BasicUtils.getConnection())
		{
			SkipassTypeDao skipassTypeDao = new SkipassTypeDao(connection);
			List<SkipassType> skipassTypes = skipassTypeDao.getAll();
			Callback<ListView<SkipassType>, ListCell<SkipassType>> factory = lv -> new ListCell<>() {
				@Override
				protected void updateItem(SkipassType skipassType, boolean empty)
				{
					super.updateItem(skipassType, empty);
					setText(empty ? "" : skipassType.getDiscountDescription());
				}
			};
			discountTypeComboBox.setCellFactory(factory);
			discountTypeComboBox.setButtonCell(factory.call(null));
			discountTypeComboBox.getItems().setAll(skipassTypes);
		} catch (SQLException exception)
		{
			exception.printStackTrace();//TODO
		}
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
