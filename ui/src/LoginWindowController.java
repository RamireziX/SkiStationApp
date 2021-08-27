import com.awrzosek.ski_station.basic.BasicUtils;
import com.awrzosek.ski_station.database_management.EmployeeManager;
import com.awrzosek.ski_station.tables.person.employee.Employee;
import edit_windows.client.edit.ClientEditWindowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginWindowController implements Initializable {
	@FXML
	private TextField loginTextField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Button loginButton;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		loginButton.setDefaultButton(true);
		loginButton.setOnAction(e -> {
			try (Connection connection = BasicUtils.getConnection())
			{
				Stage mainStage = ClientEditWindowController.getStage(loginButton);
				EmployeeManager employeeManager = new EmployeeManager(connection);
				Employee employee = employeeManager.authenticateLogin(loginTextField.getText(),
						passwordField.getText());
				if (employee == null)
					new Alert(Alert.AlertType.ERROR,
							"Nieprawidłowy login lub hasło!").showAndWait();
				else
				{
					FXMLLoader fxmlLoader =
							new FXMLLoader(getClass().getResource("main_window.fxml"));
					Parent parent = fxmlLoader.load();
					Stage stage = new Stage();
					stage.setScene(new Scene(parent));
					stage.setTitle("Stacja narciarska; zalogowany pracownik: " + employee.getLogin());
					stage.setMaximized(true);
					MainWindowController mainWindowController = fxmlLoader.getController();
					mainWindowController.setLoggedInEmployee(employee);
					stage.show();
					mainStage.close();
				}
			} catch (SQLException | IOException exception)
			{
				exception.printStackTrace();//TODO
			}
		});
	}
}
