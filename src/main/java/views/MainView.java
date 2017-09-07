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
	@FXML private DatePicker beginDay, finishDay;
	@FXML private ComboBox<Integer> beginHour, beginMinute, finishHour, finishMinute;
	@FXML private TextArea annotationTextArea;
	
	@FXML private TableView<Appointment> scheduleTable;
	@FXML private TableColumn<Appointment, String> beginDateColumn, finishDateColumn, annotationColumn;

	private MainController controller;
	
	public void initialize(URL arg0, ResourceBundle arg1) {	}

	// add 0-59 to minute comboBox and 0-23 to hours comboBox
	private void setComboBox() {
		for (int i=0; i<24; i++) {
			beginHour.getItems().add(i);
			finishHour.getItems().add(i);
		}
		
		for (int i=0; i<60; i++) {
			beginMinute.getItems().add(i);
			finishMinute.getItems().add(i);
		}
		beginHour.setValue(0);
		finishHour.setValue(0);

		beginMinute.setValue(0);
		finishMinute.setValue(0);
	}
	private void setTableView() {
		beginDateColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("beginDate"));
		finishDateColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("finishDate"));
		annotationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("annotation"));
	}
	private void setDatePicker() {
		beginDay.setValue(LocalDate.of(2017, 9, 1));
		finishDay.setValue(LocalDate.of(2017, 9, 1));
	}
	private void setTextArea() {
		annotationTextArea.setText("");
	}
	
	public void showEventInSchedule() {
		ArrayList<Appointment> appointments = controller.getDbController().loadEvent();
		scheduleTable.setItems(FXCollections.observableArrayList(appointments));
	}
	
	@FXML
	public void onClickAdd() {
		String beginDateString = String.format("%02d/%02d/%02d %02d:%02d", beginDay.getValue().getDayOfMonth(),
				beginDay.getValue().getMonthValue(), beginDay.getValue().getYear(), 
				beginHour.getValue(), beginMinute.getValue());
		
		String finishDateString = String.format("%02d/%02d/%02d %02d:%02d", finishDay.getValue().getDayOfMonth(),
				finishDay.getValue().getMonthValue(), finishDay.getValue().getYear(), 
				finishHour.getValue(), finishMinute.getValue());
		
		String annotation = annotationTextArea.getText();
		controller.getDbController().addEvent(beginDateString, finishDateString, annotation);

		showEventInSchedule();
		setBeginScene();
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
				editView.setMainView(this);
				editView.setController(controller);

				Scene scene = new Scene(editLayout);
				editStage.setScene(scene);
				editStage.setResizable(false);
				editStage.setTitle("Edit Appointment");
				editStage.initModality(Modality.APPLICATION_MODAL);
				editStage.showAndWait();

				showEventInSchedule();

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

			showEventInSchedule();
		}
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

		showEventInSchedule();
	}
}
