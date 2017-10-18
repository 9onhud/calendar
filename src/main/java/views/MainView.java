package views;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;


import controllers.MainController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Appointment;

public class MainView implements Initializable{
	@FXML private DatePicker beginDay, dateSearch;
	@FXML private ComboBox<Integer> beginHour, beginMinute;
	@FXML private ComboBox<String> repeatType;
	@FXML private TextArea annotationTextArea;
	
	@FXML private TableView<Appointment> scheduleTable;
	@FXML private TableColumn<Appointment, String> beginDateColumn, annotationColumn, repeatTypeColumn;

	private ArrayList<Appointment> appointments;

	private MainController controller;
	
	public void initialize(URL arg0, ResourceBundle arg1) {	}

	private void setComboBox() {
		for (int i=0; i<24; i++)
			beginHour.getItems().add(i);
		
		for (int i=0; i<60; i++)
			beginMinute.getItems().add(i);

		repeatType.getItems().add("Never");
		repeatType.getItems().add("Daily");
		repeatType.getItems().add("Weekly");
		repeatType.getItems().add("Monthly");
		repeatType.getItems().add("Yearly");
	}
	private void setDefualt() {
		beginHour.setValue(0);

		beginMinute.setValue(0);

		repeatType.setValue("Never");

		setDatePicker();
		setTextArea();
	}
	private void setTableView() {
		beginDateColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("beginDate"));
		annotationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("annotation"));
		repeatTypeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("repeatType"));
	}
	private void setDatePicker() {
		beginDay.setValue(LocalDate.of(2017, 9, 1));
	}
	private void setTextArea() {
		annotationTextArea.setText("");
	}
	
	public void showEventInSchedule(ArrayList<Appointment> appointments) {
		scheduleTable.setItems(FXCollections.observableArrayList(appointments));
	}

	@FXML
	public void onSearch() {
		String searchDayString = Integer.toString(dateSearch.getValue().getDayOfMonth());
		String searchMonthString = Integer.toString(dateSearch.getValue().getMonthValue());
		String searchYearString = Integer.toString(dateSearch.getValue().getYear());

		ArrayList<Appointment> appointmentsOnSearch = new ArrayList<>();
		for (Appointment appointment: appointments) {
			String dayString = (appointment.getBeginDate().split(" "))[0].split("/")[0];
			String monthString = (appointment.getBeginDate().split(" "))[0].split("/")[1];
			String yearString = (appointment.getBeginDate().split(" "))[0].split("/")[2];

			if (appointment.getRepeatType().equals("Daily"))
				appointmentsOnSearch.add(appointment);
			else if (appointment.getRepeatType().equals("Weekly") ) {}

		}

		showEventInSchedule(appointmentsOnSearch);
	}

	@FXML
	public void onResetSearch() {
		loadEvent();
		showEventInSchedule(appointments);
	}

	@FXML
	public void onClickAdd() {
		String beginDateString = String.format("%02d/%02d/%02d %02d:%02d", beginDay.getValue().getDayOfMonth(),
				beginDay.getValue().getMonthValue(), beginDay.getValue().getYear(), 
				beginHour.getValue(), beginMinute.getValue());
		
		String annotation = annotationTextArea.getText();

		String repeatTypeString = repeatType.getValue();

		controller.getDbController().addEvent(beginDateString, annotation, repeatTypeString);

		loadEvent();
		showEventInSchedule(appointments);
		setDefualt();
	}
	
	@FXML 
	public void onClickEdit() {
		Appointment editedAppointment = scheduleTable.getSelectionModel().getSelectedItem();
		if (editedAppointment != null) {
			try {
				Stage editStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainView.class.getResource("/EditView.fxml"));
				Pane editLayout = (AnchorPane) loader.load();
				EditView editView = loader.getController();
				editView.setEditAppointment(editedAppointment);
				editView.setController(controller);

				Scene scene = new Scene(editLayout);
				editStage.setScene(scene);
				editStage.setResizable(false);
				editStage.setTitle("Edit Appointment");
				editStage.initModality(Modality.APPLICATION_MODAL);
				editStage.showAndWait();

				loadEvent();
				showEventInSchedule(appointments);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML 
	public void onClickDelete() {
		Appointment deleteAppointment = scheduleTable.getSelectionModel().getSelectedItem();
		if (deleteAppointment != null) {
			controller.getDbController().deleteEvent(deleteAppointment);

			loadEvent();
			showEventInSchedule(appointments);
		}
	}

	private void loadEvent() {
		appointments = controller.getDbController().loadEvent();
	}
	public void setController(MainController controller) {
		this.controller = controller;

		setBeginScene();
	}

	private void setBeginScene() {
		setComboBox();
		setTableView();
		setDatePicker();
		setTextArea();

		setDefualt();

		loadEvent();
		showEventInSchedule(appointments);
	}
}
