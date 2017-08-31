package ku.sci.cs.calendar;

import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/views/MainView.fxml"));
		
		primaryStage.setTitle("Schedule");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		
		MainController controller = new MainController();
		controller.startApp();
	}
}
