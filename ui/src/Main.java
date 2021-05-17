import com.awrzosek.ski_station.basic.BasicConsts;
import com.awrzosek.ski_station.basic.BasicUtils;
import com.awrzosek.ski_station.database_management.ClientManager;
import com.awrzosek.ski_station.initializers.InitializerUtils;
import com.awrzosek.ski_station.tables.person.client.Client;
import com.awrzosek.ski_station.tables.person.client.ClientDao;
import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.equipment.EquipmentDao;
import com.awrzosek.ski_station.tables.ski.equipment.rent.RentType;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassType;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassTypeDao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception
	{

		//TODO zrób ile się da w src (dodawanie i tak dalej ale już z wymaganiami, jakieś mockupy innych
		// funkcji) + dopisz co potrzebne w functional requirements
		//TODO jak już będzie interfejs graficzny to ogarnąć pokazywanie błędów
		InitializerUtils.run();
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample.fxml")));
		primaryStage.setTitle("Stacja narciarska");
		primaryStage.setScene(new Scene(root, 300, 275));
		primaryStage.show();
		//primaryStage.setMaximized(true);
		//primaryStage.setFullScreen(true);


		try (Connection connection = BasicUtils.getConnection())
		{
			ClientDao clientDao = new ClientDao(connection);
			Client client = clientDao.get(1L).orElse(null);
			new ClientManager(connection).removeClient(client);
		}

	}

	private void addClientMockup() throws SQLException
	{
		try (Connection connection = BasicUtils.getConnection())
		{
			ClientManager clientManager = new ClientManager(connection);
			Client client =
					new Client("Alexander", BasicConsts.EMPTY_STRING, "Kowal", null, "123", "321", "phone",
							"email", null);
			List<Equipment> equipments =
					new EquipmentDao(connection).listByQuery("select * from EQUIPMENT limit 2");

			SkipassType skipassType = (new SkipassTypeDao(connection).getByQuery("select * from " +
					"SKIPASS_TYPE" +
					" limit 1")).orElse(null);

			clientManager.addClient(client, null, skipassType, 1, RentType.STAY);
		}
	}


	public static void main(String[] args)
	{
		launch(args);
	}
}
