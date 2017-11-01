package client.controllers;

import server.controllers.DatabaseController;

public class MainController {
	private DatabaseController dbController;

	public MainController() {
		dbController = new DatabaseController();
	}

	public DatabaseController getDbController() {
		return dbController;
	}

}
