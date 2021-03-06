package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import Querys.DBConnect;
import extra.ClientConnection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.ServerConnection;

public class ServerUIFController {
	public static ServerUIFController serveruifconroller;
	public ArrayList<ClientConnection> clients=new ArrayList<>();
	final public static int DEFAULT_PORT = 5555;
	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	public static boolean flagon = false;
	Alert a = new Alert(AlertType.ERROR);

	@FXML
	private URL location;

	@FXML
	private TableView<ClientConnection> ClientTable;

	@FXML
	private TableColumn<ClientConnection, String> HostCol;

	@FXML
	private TableColumn<ClientConnection, String> IpCol;

	@FXML
	private TextArea ServerLogTxt;

	@FXML
	private TableColumn<ClientConnection, String> StatusCol;

	@FXML
	private Label Statuslbl;

	@FXML
	private Button btnClose;

	@FXML
	private Button connectBtn;

	@FXML
	private Button disconnectBtn;

	@FXML
	private Label ipLabel;

	@FXML
	private Button ClearLogBtn;

	@FXML
	private TextField usertxt;

	@FXML
	private TextField Passtxt;
	
	
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader load = new FXMLLoader();
		primaryStage.setTitle("ZerLi");
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/ServerUIF.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);
		primaryStage.getIcons().add(new Image("/fxml/servericon.png"));
		primaryStage.show();
	}

	@FXML
	void ConnectServer(ActionEvent event) {
		ServerConnection.startServer(null, this);
		String username, password;
		username = usertxt.getText();
		password = Passtxt.getText();
		Connection connection = DBConnect.connect(username,password);
		if (flagon == true) {
			Statuslbl.setText("ON");
			Statuslbl.setStyle("-fx-text-fill: green");
			addToTextArea("Server listening for connections on port: " + DEFAULT_PORT);
			ClientTable.refresh();
		}
	}

	@FXML
	void StopServer(ActionEvent event) {
		ServerConnection.stopServer(this);
		Statuslbl.setText("OFF");
		Statuslbl.setStyle("-fx-text-fill: red");
		addToTextArea("Server has stopped listening for connections on port: " + DEFAULT_PORT);
	 	ClientTable.getItems().clear();
		ClientTable.refresh();
		flagon = false;
	}

	/** This method add message to the log area */
	public void addToTextArea(String msg) {
		String timeStamp = new SimpleDateFormat("[dd.MM.yyyy]  [HH:mm:ss]  ").format(Calendar.getInstance().getTime());
		Platform.runLater(() -> ServerLogTxt.appendText(timeStamp + msg + "\n"));
	}

	public void Close(ActionEvent event) {
		this.StopServer(event);
		Stage stage = (Stage) btnClose.getScene().getWindow();
		stage.close();
	}

	@FXML
	void clearLog(ActionEvent event) {
		ServerLogTxt.clear();
	}

	public Label getLabelStatusServer() {
		return Statuslbl;
	}

	/** This method will update the table */
	public void Update(ArrayList<ClientConnection> client) {
		addToTextArea("New connection: " + client);
		ObservableList<ClientConnection> data = FXCollections.observableArrayList(client);
		ClientTable.setItems(data);
		ClientTable.refresh();
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		serveruifconroller = this;
		IpCol.setCellValueFactory(new PropertyValueFactory<ClientConnection, String>("ipAddress"));
		HostCol.setCellValueFactory(new PropertyValueFactory<ClientConnection, String>("hostName"));
		StatusCol.setCellValueFactory(new PropertyValueFactory<ClientConnection, String>("status"));
	}

}
