package common.models;

public class Appointment {
	private String beginDate, annotation, repeatType;

	public Appointment(String beginDate, String annotation, String repeatType) {
		this.beginDate = beginDate;
		this.annotation = annotation;
		this.repeatType = repeatType;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public String getAnnotation() {
		return annotation;
	}

	public String getRepeatType() { return repeatType; }
	
}
