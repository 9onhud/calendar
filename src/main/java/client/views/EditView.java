package client.views;

import client.controllers.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import common.models.Appointment;

import java.time.LocalDate;

public class EditView implements ShowView {
	@FXML private DatePicker beginDayEdit;
	@FXML private ComboBox<Integer> beginHourEdit, beginMinuteEdit;
	@FXML private ComboBox<String> repeatTypeEdit;
	@FXML private TextArea annotationTextAreaEdit;
	@FXML private Button applyEditBtn;

	private MainController controller;
	private Appointment editAppointment;

	@FXML
	public void onClickApplyEdit() {
		String beginDateString = String.format("%02d/%02d/%02d %02d:%02d", beginDayEdit.getValue().getDayOfMonth(),
				beginDayEdit.getValue().getMonthValue(), beginDayEdit.getValue().getYear(),
				beginHourEdit.getValue(), beginMinuteEdit.getValue());

		String annotation = annotationTextAreaEdit.getText();

		String repeatTypeString = repeatTypeEdit.getValue();
		Appointment newAppointment = new Appointment(beginDateString, annotation, repeatTypeString);
		controller.getDbController().editEvent(editAppointment, newAppointment);

		Stage stage = (Stage) applyEditBtn.getScene().getWindow();
		stage.close();
	}
	public void setComboBox() {
		for (int i=0; i<24; i++)
			beginHourEdit.getItems().add(i);

		for (int i=0; i<60; i++)
			beginMinuteEdit.getItems().add(i);

		Integer defaultBeginHour = Integer.parseInt((editAppointment.getBeginDate().split(" ")[1]).split(":")[0]);
		beginHourEdit.setValue(defaultBeginHour);

		Integer defaultBeginMinute = Integer.parseInt((editAppointment.getBeginDate().split(" ")[1]).split(":")[1]);
		beginMinuteEdit.setValue(defaultBeginMinute);

		repeatTypeEdit.getItems().add("Never");
		repeatTypeEdit.getItems().add("Daily");
		repeatTypeEdit.getItems().add("Weekly");
		repeatTypeEdit.getItems().add("Monthly");
		repeatTypeEdit.getItems().add("Yearly");

		repeatTypeEdit.setValue(editAppointment.getRepeatType());
	}
	public void setDatePicker() {
		Integer defaultBeginDay = Integer.parseInt((editAppointment.getBeginDate().split(" ")[0]).split("/")[0]);
		Integer defaultBeginMonth = Integer.parseInt((editAppointment.getBeginDate().split(" ")[0]).split("/")[1]);
		Integer defaultBeginYear = Integer.parseInt((editAppointment.getBeginDate().split(" ")[0]).split("/")[2]);
		beginDayEdit.setValue(LocalDate.of(defaultBeginYear, defaultBeginMonth, defaultBeginDay));
	}
	public void setTextArea() {
		annotationTextAreaEdit.setText(editAppointment.getAnnotation());
	}

	public void setBeginScene() {
		setComboBox();
		setDatePicker();
		setTextArea();
	}

	public void setController(MainController controller) {
		this.controller = controller;

		setBeginScene();
	}

	void setEditAppointment(Appointment editAppointment) {
		this.editAppointment = editAppointment;
	}
}
