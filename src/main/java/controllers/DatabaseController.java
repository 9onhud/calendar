package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Appointment;

public class DatabaseController {

	public ArrayList<Appointment> loadEvent() {
		try {
			Class.forName("org.sqlite.JDBC");
			String dbURL = "jdbc:sqlite:event.db";
			Connection conn = DriverManager.getConnection(dbURL);
			
			if (conn != null) {
				String query = "Select * from event";
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(query);
				
				ArrayList<Appointment> appointments = new ArrayList<Appointment>();
				
				while (resultSet.next()) {
					String beginDate = resultSet.getString(1);
					String finishDate = resultSet.getString(2);
					String annotation = resultSet.getString(3);
					
					appointments.add(new Appointment(beginDate, finishDate, annotation));
				}
				conn.close();
				return appointments;
			}
			return null;
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void addEvent(String beginDate, String finishDate, String annotation) {
		try {
			Class.forName("org.sqlite.JDBC");
			String dbURL = "jdbc:sqlite:event.db";
			Connection conn = DriverManager.getConnection(dbURL);
			
			if (conn != null) {
				String query = "INSERT INTO event(beginDate, finishDate, annotation) VALUES(?,?,?)";
				PreparedStatement statement = conn.prepareStatement(query);
				
				statement.setString(1, beginDate);
				statement.setString(2, finishDate);
				statement.setString(3, annotation);
				
				statement.executeUpdate();
				conn.close();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteEvent(Appointment deleteAppointment) {
		try {
			Class.forName("org.sqlite.JDBC");
			String dbURL = "jdbc:sqlite:event.db";
			Connection conn = DriverManager.getConnection(dbURL);

			if (conn != null) {
				String query = "DELETE FROM event WHERE beginDate = ? AND finishDate = ? AND annotation = ?";
				PreparedStatement statement = conn.prepareStatement(query);

				statement.setString(1, deleteAppointment.getBeginDate());
				statement.setString(2, deleteAppointment.getFinishDate());
				statement.setString(3, deleteAppointment.getAnnotation());

				statement.executeUpdate();
				conn.close();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void editEvent(Appointment editAppointment, Appointment newAppointment) {
		try {
			Class.forName("org.sqlite.JDBC");
			String dbURL = "jdbc:sqlite:event.db";
			Connection conn = DriverManager.getConnection(dbURL);

			if (conn != null) {
				String query = "UPDATE event set beginDate = ?, finishDate = ?, annotation = ? " +
						"where beginDate = ? AND finishDate = ? AND annotation = ?";
				PreparedStatement statement = conn.prepareStatement(query);

				statement.setString(1, newAppointment.getBeginDate());
				statement.setString(2, newAppointment.getFinishDate());
				statement.setString(3, newAppointment.getAnnotation());

				statement.setString(4, editAppointment.getBeginDate());
				statement.setString(5, editAppointment.getFinishDate());
				statement.setString(6, editAppointment.getAnnotation());

				statement.executeUpdate();
				conn.close();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
