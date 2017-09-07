package views;

import controllers.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import models.Appointment;

import java.time.LocalDate;

public class EditView {
	@FXML private DatePicker beginDayEdit, finishDayEdit;
	@FXML private ComboBox<Integer> beginHourEdit, beginMinuteEdit, finishHourEdit, finishMinuteEdit;
	@FXML private TextArea annotationTextAreaEdit;
	@FXML private Button applyEditBtn;

	private MainController controller;
	private MainView mainView;
	private Appointment editAppointment;

	@FXML
	public void onClickApplyEdit() {
		String beginDateString = String.format("%02d/%02d/%02d %02d:%02d", beginDayEdit.getValue().getDayOfMonth(),
				beginDayEdit.getValue().getMonthValue(), beginDayEdit.getValue().getYear(),
				beginHourEdit.getValue(), beginMinuteEdit.getValue());

		String finishDateString = String.format("%02d/%02d/%02d %02d:%02d", finishDayEdit.getValue().getDayOfMonth(),
				finishDayEdit.getValue().getMonthValue(), finishDayEdit.getValue().getYear(),
				finishHourEdit.getValue(), finishMinuteEdit.getValue());

		String annotation = annotationTextAreaEdit.getText();

		Appointment newAppointment = new Appointment(beginDateString, finishDateString, annotation);
		controller.getDbController().editEvent(editAppointment, newAppointment);

		Stage stage = (Stage) applyEditBtn.getScene().getWindow();
		stage.close();
	}
	private void setComboBox() {
		for (int i=0; i<24; i++) {
			beginHourEdit.getItems().add(i);
			finishHourEdit.getItems().add(i);
		}

		for (int i=0; i<60; i++) {
			beginMinuteEdit.getItems().add(i);
			finishMinuteEdit.getItems().add(i);
		}
		Integer defaultBeginHour = Integer.parseInt((editAppointment.getBeginDate().split(" ")[1]).split(":")[0]);
		Integer defaultFinishHour = Integer.parseInt((editAppointment.getFinishDate().split(" ")[1]).split(":")[0]);
		beginHourEdit.setValue(defaultBeginHour);
		finishHourEdit.setValue(defaultFinishHour);

		Integer defaultBeginMinute = Integer.parseInt((editAppointment.getBeginDate().split(" ")[1]).split(":")[1]);
		Integer defaultFinishMinute = Integer.parseInt((editAppointment.getFinishDate().split(" ")[1]).split(":")[1]);
		beginMinuteEdit.setValue(defaultBeginMinute);
		finishMinuteEdit.setValue(defaultFinishMinute);
	}
	private void setDatePicker() {
		Integer defaultBeginDay = Integer.parseInt((editAppointment.getBeginDate().split(" ")[0]).split("/")[0]);
		Integer defaultBeginMonth = Integer.parseInt((editAppointment.getBeginDate().split(" ")[0]).split("/")[1]);
		Integer defaultBeginYear = Integer.parseInt((editAppointment.getBeginDate().split(" ")[0]).split("/")[2]);
		beginDayEdit.setValue(LocalDate.of(defaultBeginYear, defaultBeginMonth, defaultBeginDay));

		Integer defaultFinishDay = Integer.parseInt((editAppointment.getFinishDate().split(" ")[0]).split("/")[0]);
		Integer defaultFinishMonth = Integer.parseInt((editAppointment.getFinishDate().split(" ")[0]).split("/")[1]);
		Integer defaultFinishYear = Integer.parseInt((editAppointment.getFinishDate().split(" ")[0]).split("/")[2]);
		finishDayEdit.setValue(LocalDate.of(defaultFinishYear, defaultFinishMonth, defaultFinishDay));
	}
	private void setTextArea() {
		annotationTextAreaEdit.setText(editAppointment.getAnnotation());
	}

	private void setBeginScene() {
		setComboBox();
		setDatePicker();
		setTextArea();
	}

	public void setController(MainController controller) {
		this.controller = controller;

		setBeginScene();
	}

	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}

	public void setEditAppointment(Appointment editAppointment) {
		this.editAppointment = editAppointment;
	}
}
