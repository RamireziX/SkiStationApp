import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception
	{

		//TODO zrób ile się da w src (dodawanie i tak dalej ale już z wymaganiami, jakieś mockupy innych funkcji) +
		// dopisz co
		// potrzebne w
		// functional requirements
		//InitializerUtils.run();
		Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
		primaryStage.setTitle("Stacja narciarska");
		primaryStage.setScene(new Scene(root, 300, 275));
		primaryStage.show();
		//primaryStage.setMaximized(true);
		//primaryStage.setFullScreen(true);
	}


	public static void main(String[] args)
	{
		launch(args);
	}
}
