package client;

import client.controllers.MainController;
import common.DataBaseService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import client.views.MainView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class App extends Application{
	private MainController controller;
	private MainView view;
	private Pane mainLayout;

	public static void main(String[] args) { launch(args); }

	@Override
	public void start(Stage primaryStage) {
		try {
			ApplicationContext bf = new ClassPathXmlApplicationContext("client.xml");
			DataBaseService service = (DataBaseService) bf.getBean("dbService");

			controller = new MainController(service);

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/MainView.fxml"));
			mainLayout = (AnchorPane) loader.load();
			view = loader.getController();
			view.setController(controller);

			Scene scene = new Scene(mainLayout);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Schedule");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
