package views;


import java.net.URL;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

public class MainView implements Initializable{
	@FXML private DatePicker beginDay, finishDay;
	@FXML private ComboBox<Integer> beginHour, beginMinute, finishHour, finishMinute;
	@FXML private TextArea annotationTextArea;

	public void initialize(URL arg0, ResourceBundle arg1) {
		setComboBox();
	}
	
	private void setComboBox() {
		for (int i=0; i<24; i++) {
			beginHour.getItems().add(i);
			finishHour.getItems().add(i);
		}
		
		for (int i=0; i<60; i++) {
			beginMinute.getItems().add(i);
			finishMinute.getItems().add(i);
		}
	}
	
	@FXML
	private void onClickAdd() throws ParseException {
		String beginDateString = String.format("%02d/%02d/%02d %02d:%02d", beginDay.getValue().getDayOfMonth(),
				beginDay.getValue().getMonthValue(), beginDay.getValue().getYear(), beginHour, beginMinute);
		
		String finishDateString = String.format("%02d/%02d/%02d %02d:%02d", finishDay.getValue().getDayOfMonth(),
				finishDay.getValue().getMonthValue(), finishDay.getValue().getYear(), finishHour, finishMinute);
		
		DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
		Date beginDate = dateTimeFormat.parse(beginDateString);
		Date finishDate = dateTimeFormat.parse(finishDateString);
	}
}
