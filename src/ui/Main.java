package ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.InfrastructureDepartment;

public class Main extends Application{
	
	private InfrastructureDepartment infrastructureDepartment;
	private InfrastructureDepartmentGUI infrastructureDepartmentGUI;
	
	public Main() {
		
		boolean loadBillboard;
		
		infrastructureDepartment = new InfrastructureDepartment();
		infrastructureDepartmentGUI = new InfrastructureDepartmentGUI(infrastructureDepartment);
		
		try {
			loadBillboard = infrastructureDepartment.loadData();
		}catch(ClassNotFoundException | IOException e) {
			e.printStackTrace();
			loadBillboard = false;
		}
		
		if(!loadBillboard) {
			
			Alert alert = new Alert(AlertType.INFORMATION);
			
		    alert.setTitle("Billboards");
		    alert.setContentText("Error loading data from file");
		    alert.showAndWait();
		}
	}

	public static void main(String[] args) {
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("welcome.fxml"));
		
		fxmlLoader.setController(infrastructureDepartmentGUI);
		
		Parent root = fxmlLoader.load();
		
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Billboards");
		primaryStage.show();
		
	}

}
