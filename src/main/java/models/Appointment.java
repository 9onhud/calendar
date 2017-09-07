package models;

public class Appointment {
	private String beginDate, finishDate, annotation;

	public Appointment(String beginDate, String finishDate, String annotation) {
		this.beginDate = beginDate;
		this.finishDate = finishDate;
		this.annotation = annotation;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public String getAnnotation() {
		return annotation;
	}
	
	
}
