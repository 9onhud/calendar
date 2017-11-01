package client.controllers;

import common.DataBaseService;

public class MainController {
	private DataBaseService dbController;

	public MainController(DataBaseService dbService) {

		dbController = dbService;
	}

	public DataBaseService getDbController() {
		return dbController;
	}

}
