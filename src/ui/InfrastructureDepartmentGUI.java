package ui;

import java.io.File;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import model.Billboard;
import model.InfrastructureDepartment;

public class InfrastructureDepartmentGUI {

	public final static String YES = "Si";
	public final static String NO = "No";

	@FXML
	private BorderPane mainPanel;

	@FXML
	private TextField txtBrand;

	@FXML
	private TextField txtHeight;

	@FXML
	private TextField txtWidth;

	@FXML
	private ComboBox<String> inUseSelected;

	@FXML
	private TableView<Billboard> tableViewBillboard;

	@FXML
	private TableColumn<Billboard, Double> tableViewWidth;

	@FXML
	private TableColumn<Billboard, Double> tableViewHeight;

	@FXML
	private TableColumn<Billboard, String> tableViewInUse;

	@FXML
	private TableColumn<Billboard, String> tableViewBrand;

	private InfrastructureDepartment infrastructureDepartment;

	public InfrastructureDepartmentGUI(InfrastructureDepartment id) {
		infrastructureDepartment = id;
	}

	public void initializateTableView() {

		ObservableList<Billboard> observableList;

		observableList = FXCollections.observableArrayList(infrastructureDepartment.getBillboards());

		tableViewBillboard.setItems(observableList);
		tableViewWidth.setCellValueFactory(new PropertyValueFactory<Billboard,Double>("width"));
		tableViewHeight.setCellValueFactory(new PropertyValueFactory<Billboard,Double>("heigth"));
		tableViewInUse.setCellValueFactory(new PropertyValueFactory<Billboard,String>("inUse"));
		tableViewBrand.setCellValueFactory(new PropertyValueFactory<Billboard,String>("brand"));
	}

	@FXML
	public void aboutProgram(ActionEvent event) {

		Alert alert = new Alert(AlertType.INFORMATION);

		alert.setTitle("Billboard");
		alert.setHeaderText("Credits:");
		alert.setContentText("Luis Miguel Ossa Arias\nAlgorithms II");

		alert.showAndWait();
	}

	@FXML
	public void addBillboard(ActionEvent event) throws IOException {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-billboard.fxml"));

		fxmlLoader.setController(this);

		Parent addBillboardPane = fxmlLoader.load();

		mainPanel.getChildren().clear();
		mainPanel.setTop(addBillboardPane);

		inUseSelected.setPromptText("Select");
		inUseSelected.getItems().addAll(YES,NO);
	}

	@FXML
	public void exportDangerousBillboardReport(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File f = fileChooser.showSaveDialog(mainPanel.getScene().getWindow());

		if(f != null) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Import Contacts");

			try {

				infrastructureDepartment.exportData(f.getAbsolutePath());

				alert.setContentText("The contact list was exported succesfully");
				alert.showAndWait();
			}catch(IOException e) {

				alert.setContentText("The contact list was not exported. An error ocurred.");
				alert.showAndWait();
			}
		}
	}

	@FXML
	public void importData(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File f = fileChooser.showOpenDialog(mainPanel.getScene().getWindow());

		if(f != null) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Import Contacts");

			try {

				infrastructureDepartment.importData(f.getAbsolutePath());

				alert.setContentText("The billboard list was imported succesfully");
				alert.showAndWait();
			}catch(IOException e) {

				alert.setContentText("The billboard list was not imported. An error ocurred.");
				alert.showAndWait();
			}
		}
	}

	@FXML
	public void showBillboard(ActionEvent event) throws IOException {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("billboard-list.fxml"));

		fxmlLoader.setController(this);

		Parent billboardListPane = fxmlLoader.load();

		mainPanel.getChildren().clear();
		mainPanel.setCenter(billboardListPane);

		initializateTableView();
	}

	@FXML
	public void saveBillboard(ActionEvent event) throws IOException {

		if(txtWidth.getText().equals("") || txtHeight.getText().equals("") || txtBrand.getText().equals("") || inUseSelected.getValue() == null) {

			Alert alert = new Alert(AlertType.INFORMATION);

			alert.setTitle("Validaton Error");
			alert.setHeaderText(null);
			alert.setContentText("You must fill each field in the form");

			alert.showAndWait();
		}
		else if(!txtWidth.getText().equals("") || !txtHeight.getText().equals("") || !txtBrand.getText().equals("") || inUseSelected.getValue() != null) {

			try {
				String strWidth = txtWidth.getText();
				String strHeight = txtHeight.getText();

				double width = Double.parseDouble(strWidth);
				double height = Double.parseDouble(strHeight);
				boolean inUse = false;

				if(inUseSelected.getValue().toString().equals(YES)) {
					inUse = true;
				}
				else if(inUseSelected.getValue().toString().equals(NO)) {
					inUse = false;
				}

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Billboard Created");
				alert.setHeaderText(null);
				alert.setContentText("The new billboard has been add");

				alert.showAndWait();

				infrastructureDepartment.addBillboard(width,height,inUse,txtBrand.getText());

				txtBrand.setText("");
				txtWidth.setText("");
				txtHeight.setText("");
				inUseSelected.setValue(null);

			}catch(NumberFormatException nfe) {
				Alert alert = new Alert(AlertType.INFORMATION);

				alert.setTitle("Validaton Error");
				alert.setHeaderText(null);
				alert.setContentText("The value width/height is not numeric, please correct it");

				alert.showAndWait();
			}

		}
	}
}
