package application;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class MyModel {

	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/workoutTracker";
	private static final String DATABASE_USERNAME = "root";
	private static final String DATABASE_PASSWORD = "1234";
	private static final ObservableList<Workout> list = FXCollections.observableArrayList();

	public static Connection connectDb() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean validate(String username, String password) {
		if(username.isEmpty())
			return false;
		Connection conn = connectDb();
		try {

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM registration WHERE username = ? and password = ?");
			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean createNewAccount(String username, String password, String confirm_password) throws SQLException {
		Connection conn = connectDb();
		
		try {
			// Make sure username is available
			PreparedStatement ps1 = conn.prepareStatement("select * from registration where username = ?");
			ps1.setString(1, username);
			ResultSet rs1 = ps1.executeQuery();
			if(rs1.next()) {
				return false;
			}
			
			// Once we know that username is available and passwords match, create new username and password entry.
			PreparedStatement ps2 = conn.prepareStatement("insert into registration(username,password) values(?,?)");
			ps2.setString(1, username);
			ps2.setString(2, password);
			ps2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public ObservableList<Workout> getWorkoutData(String username) {
		Connection conn = connectDb();
		try {
			PreparedStatement ps = conn.prepareStatement(
					"select * from workout_sesh as w,registration as r where r.username = ? and w.user_id = r.id");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(new Workout(rs.getLong("sesh_id"), rs.getLong("user_id"), rs.getDate("sesh_date"), rs.getString("exercise"),
						rs.getInt("sets"), rs.getInt("reps"), rs.getInt("weight")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void addButtonClicked(String username, DatePicker dateInput, TextField exerciseInput, TextField setInput, TextField repInput, TextField weightInput) {
		Connection conn = connectDb();
		// First make user data into preferred data types
		try {
			Date newDateInput = Date.valueOf(dateInput.getValue());
			String newExerciseInput = exerciseInput.getText().toString();
			int newSetInput = Integer.parseInt(setInput.getText().toString());
			int newRepInput = Integer.parseInt(repInput.getText().toString());
			int newWeightInput = 0;
			if(!weightInput.getText().trim().isEmpty()) {
				newWeightInput = Integer.parseInt(weightInput.getText().toString());
			} 			
			
			PreparedStatement ps1 = conn.prepareStatement("select id from registration where username = ?");
			PreparedStatement ps2 = conn.prepareStatement("insert into workout_sesh(user_id,sesh_date,exercise,sets,reps,weight) values (?,?,?,?,?,?)");

			ps1.setString(1, username);
			ResultSet rs1 = ps1.executeQuery();
			Long user_id = null;
			if(rs1.next()) 
				user_id = rs1.getLong("id");
			
			ps2.setLong(1, user_id); 
			ps2.setDate(2, newDateInput); 
			ps2.setString(3,newExerciseInput); 
			ps2.setInt(4, newSetInput); 
			ps2.setInt(5, newRepInput);
			ps2.setInt(6, newWeightInput); 
			ps2.executeUpdate();
			
			list.add(new Workout(newDateInput, newExerciseInput,newSetInput, newRepInput, newWeightInput));
		}catch(NumberFormatException ex) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid number");
            alert.showAndWait();
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void deleteButtonClicked(Long sesh_id) {
		Connection conn = connectDb();
		try {
			PreparedStatement ps = conn.prepareStatement("delete from workout_sesh as w where w.sesh_id = ?");
			ps.setLong(1, sesh_id);
			ps.execute();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isCompleted(DatePicker dateInput, TextField exerciseInput, TextField setInput, TextField repInput) {

        StringBuilder errors = new StringBuilder();

        // Make sure none of the textfields are empty
        if(dateInput.getValue() == null) {
        	errors.append("- Please enter a date.\n");
		}
		if (exerciseInput.getText().trim().isEmpty()) {
			errors.append("- Please enter name of exercise.\n");
		}	
        if (setInput.getText().trim().isEmpty()) {
            errors.append("- Please enter number of sets.\n");
        }
        if (repInput.getText().trim().isEmpty()) {
            errors.append("- Please enter number of reps.\n");
        }

        // Display error message if any of the textfields are empty 
        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Required Fields Empty");
            alert.setContentText(errors.toString());
            alert.showAndWait();
            
            return false;
        }

        // If there are no errors
        return true;
    }
}