import com.awrzosek.ski_station.basic.BasicConsts;
import com.awrzosek.ski_station.basic.BasicUtils;
import com.awrzosek.ski_station.initializers.InitializerUtils;
import com.awrzosek.ski_station.tables.ski.skipass.SkipassDao;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.Objects;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception
	{

		//TODO zrób ile się da w src (dodawanie i tak dalej ale już z wymaganiami, jakieś mockupy innych
		// funkcji) + dopisz co potrzebne w functional requirements
		//TODO jak już będzie interfejs graficzny to ogarnąć pokazywanie błędów
		//TODO jak już przejdziesz przez func rec to popraw też use cases

		//TODO jakiś progress bar do inicjalizatorów
		InitializerUtils.run();

		try (Connection connection = BasicUtils.getConnection())
		{
			BasicConsts.ACTIVE_NO_OF_CLIENTS = new SkipassDao(connection).getAllActive().size();
		}

		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample.fxml")));
		primaryStage.setTitle("Stacja narciarska");
		primaryStage.setScene(new Scene(root, 300, 275));
		primaryStage.show();
		primaryStage.setMaximized(true);
		primaryStage.onCloseRequestProperty().setValue(e -> Platform.exit());
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
