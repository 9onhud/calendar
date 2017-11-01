package common;

import common.models.Appointment;

import java.util.ArrayList;

/**
 * Created by PC301 on 1/11/2560.
 */
public interface DataBaseService {

    ArrayList<Appointment> loadEvent();
    void addEvent(String beginDate, String annotation, String repeatType);
    void deleteEvent(Appointment deleteAppointment);
    void editEvent(Appointment editAppointment, Appointment newAppointment);

}
