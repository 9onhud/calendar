package common.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppointmentTest {
    private Appointment app;

    @Before
    public void setUp() throws Exception {
        app = new Appointment("19/09/2017 07:20", "Test", "Weekly");
    }

    @Test
    public void getBeginDate() throws Exception {
        assertEquals(app.getBeginDate(), "19/09/2017 07:20");
    }

    @Test
    public void getAnnotation() throws Exception {
        assertEquals(app.getAnnotation(), "Test");
    }

    @Test
    public void getRepeatType() throws Exception {
        assertEquals(app.getRepeatType(), "Weekly");
    }

}