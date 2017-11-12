package client.controllers;

import common.DataBaseService;
import org.junit.Before;
import org.junit.Test;
import server.controllers.DatabaseController;

import static org.junit.Assert.*;

public class MainControllerTest {
    private MainController controller;
    private DataBaseService dataBaseService;

    @Before
    public void setUp() throws Exception {
        dataBaseService = new DatabaseController();
        controller = new MainController(dataBaseService);
    }

    @Test
    public void getDbController() throws Exception {
        assertEquals(controller.getDbController(), this.dataBaseService);
    }

}