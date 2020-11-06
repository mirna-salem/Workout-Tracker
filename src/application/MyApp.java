package application;

import java.sql.Date;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class MyApp extends Application {
	
	private static final MyModel mm = new MyModel();
	
	private static Scene scene1,scene2,signUpPage;
	String username, password, confirm_password;
	boolean flag;

	DatePicker dateInput;
	TextField exerciseInput, setInput, repInput, weightInput; 
	
	private static final TableView<Workout> workout_table = new TableView<>();
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Track My Gains");
		primaryStage.setScene(getScene1(primaryStage));
		primaryStage.show();
	}
	
	// Scene 1
	public Scene getScene1(Stage primaryStage) {	
		// Create text elements
	    Label login_header = new Label();
	    login_header.setText("User Login");

	    final TextField username_text = new TextField();
	    username_text.setPromptText("Username");
	    final PasswordField password_field = new PasswordField();
	    password_field.setPromptText("Password");
	    Button login_button = new Button("Login");
	    final Label message = new Label();	    
	    
	    Hyperlink link = new Hyperlink();
	    link.setText("Create account");
	    link.setOnAction(e -> {
	    	primaryStage.setScene(signUpPage(primaryStage));
	    });
		
		// Action - Click on button to log in
		login_button.setOnAction(e -> {
			username = username_text.getText().toString();
	        password = password_field.getText().toString();
			flag = mm.validate(username, password);
	
	        if (!flag) {
	        	message.setText("Incorrect username or password.");
	            message.setTextFill(Color.RED);
	        } 
	        else {
	        	message.setText("");
	        	primaryStage.setScene(getScene2(primaryStage));
	        }
	                
	        // Clear fields after login
	        username_text.setText("");
	        password_field.setText("");
		});
        
		// Page Layout
		VBox vb = new VBox();
	    vb.getChildren().addAll(login_header,username_text,password_field,login_button,link, message);
	    
        BorderPane bp = new BorderPane();
	    bp.setCenter(vb);
	    
	    // Add ID to edit with CSS
	    bp.setId("bp");
	    login_button.setId("my_button");
	    login_header.setId("header");
	    vb.setId("vb");
	    
		scene1 = new Scene(bp);
		scene1.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		
		return scene1;
	}
	
	public Scene signUpPage(Stage primaryStage) {		
		// Create text elements
	    Label sign_up_header = new Label();
	    sign_up_header.setText("Create an Account");

	    final TextField username_text = new TextField();
	    username_text.setPromptText("Username");
	    final PasswordField password_field = new PasswordField();
	    password_field.setPromptText("Password");
	    final PasswordField confirm_password_field = new PasswordField();
	    confirm_password_field.setPromptText("Confirm Password");
	    Button sign_up_button = new Button("Sign up");
	    final Label message = new Label();	    
	    
	    Hyperlink link = new Hyperlink();
	    link.setText("Already have an account? Log in");
	    link.setOnAction(e -> {
	    	primaryStage.setScene(getScene1(primaryStage));
	    });
	    	    
	    // Action - Click on sign up button to sign up
 		sign_up_button.setOnAction(e -> {
 			username = username_text.getText().toString();
 	        password = password_field.getText().toString();
 	        confirm_password = confirm_password_field.getText().toString();
 	        
 	        if(!password.equals(confirm_password)) {
				message.setText("Please make sure your passwords match.");
				message.setTextFill(Color.RED);
			}
 	        else {
 	        	try {
 	 				flag = mm.createNewAccount(username, password,confirm_password);
 	 			} catch (SQLException e1) {
 	 				e1.printStackTrace();
 	 			}
 	 			
 	 			if (!flag) {
 	 	        	message.setText("Username is not available.");
 	 	            message.setTextFill(Color.RED);
 	 	        } 
 	 	        else {
 	 	        	message.setText("Your account was successfully created!");
 	 	            message.setTextFill(Color.GREEN);
 	 	        }	
 	        }
 	        username_text.setText("");
 	        password_field.setText("");
 	        confirm_password_field.setText("");
 		});
 		
 		// Page Layout
		VBox vb = new VBox();
	    vb.getChildren().addAll(sign_up_header,username_text,password_field,confirm_password_field,sign_up_button,link, message);
	    
        BorderPane bp = new BorderPane();
	    bp.setCenter(vb);
	    
	    // Add ID to edit with CSS
	    bp.setId("bp");
	    sign_up_button.setId("my_button");
	    sign_up_header.setId("header");
	    vb.setId("vb");
 		    
		signUpPage= new Scene(bp);
		
		signUpPage.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		
		return signUpPage;
	}
	
	// Scene 2
	@SuppressWarnings("unchecked")
	public Scene getScene2(Stage primaryStage) {
		
		// Create table 	
		TableColumn<Workout,Date> col_seshDate = new TableColumn<>("Date");
		col_seshDate.setMinWidth(100);
		col_seshDate.setCellValueFactory(new PropertyValueFactory<>("sesh_date"));
		
		TableColumn<Workout,String> col_exercise = new TableColumn<>("Exercise");
		col_exercise.setMinWidth(150);
		col_exercise.setCellValueFactory(new PropertyValueFactory<>("exercise"));
		
		TableColumn<Workout,Integer> col_sets = new TableColumn<>("Sets");
		col_sets.setMinWidth(50);
		col_sets.setCellValueFactory(new PropertyValueFactory<>("sets"));
		
		TableColumn<Workout,Integer> col_reps = new TableColumn<>("Reps");
		col_reps.setMinWidth(50);
		col_reps.setCellValueFactory(new PropertyValueFactory<>("reps"));
		
		TableColumn<Workout,Integer> col_weight = new TableColumn<>("Weight");
		col_weight.setMinWidth(50);
		col_weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
		
		workout_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		workout_table.getColumns().addAll(col_seshDate, col_exercise, col_sets, col_reps, col_weight);
		workout_table.setItems(mm.getWorkoutData(username));
	    
		// Get inputs from user
		dateInput = new DatePicker();
		dateInput.setPromptText("Date");
		dateInput.setMinWidth(100);		
		
		exerciseInput = new TextField();
		exerciseInput.setPromptText("Name of exercise");
		exerciseInput.setMinWidth(100);

		setInput = new TextField();
		setInput.setPromptText("# of sets");
		setInput.setPrefWidth(75);
		setInput.setMinWidth(50);
		
		repInput = new TextField();
		repInput.setPromptText("# of reps");
		repInput.setPrefWidth(75);
		repInput.setMinWidth(50);
		
		weightInput = new TextField();
		weightInput.setPromptText("weight(lb)");
		weightInput.setPrefWidth(75);
		weightInput.setMinWidth(50);
		
		// Add button
		Button addButton = new Button("Add");
		addButton.setOnAction(e -> {
			if(mm.isCompleted(dateInput,exerciseInput,setInput,repInput)) {
				mm.addButtonClicked(username,dateInput,exerciseInput, setInput, repInput,weightInput);
			}			
			dateInput.getEditor().clear();
			exerciseInput.clear();
			setInput.clear();
			repInput.clear();
			weightInput.clear();
		});
		
		// Delete button
		Button deleteButton = new Button("Delete");
		deleteButton.setOnAction(e -> {
			Workout workoutSelected = workout_table.getSelectionModel().getSelectedItem();
			if(workoutSelected != null)
				workout_table.getItems().remove(workoutSelected);
				mm.deleteButtonClicked(workoutSelected.getSesh_id());
				workout_table.getSelectionModel().clearSelection();
		});
		
		// Sign out button to take user back to login page.
		Button button2= new Button("Sign out");
		button2.setOnAction(e -> {
			workout_table.getColumns().clear();
			workout_table.getItems().clear();
			primaryStage.setScene(scene1);
		});
		
		// HBox will be used to display the TextFields, add button, and delete button in a horizontal row.
		HBox hb = new HBox();
		hb.getChildren().addAll(dateInput, exerciseInput, setInput, repInput, weightInput, addButton, deleteButton, button2);
		
		// Put everything together
		BorderPane bp = new BorderPane();
	    bp.setCenter(workout_table);
	    bp.setBottom(hb);
	    
	    
		// Add ID to edit with CSS
	    hb.setId("hb");
	    workout_table.setId("table");
	    bp.setId("bp2");
	    addButton.setId("but");
	    deleteButton.setId("but");
	    button2.setId("but");

		scene2= new Scene(bp,900,450);
		
		scene2.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		
		return scene2;
	}
}