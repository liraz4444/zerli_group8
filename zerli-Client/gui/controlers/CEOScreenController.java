package controlers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class CEOScreenController extends AbstractController implements  Initializable{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text nameText;

    @FXML
    private Button viewOrdersBtn;

    @FXML
    private Button viewRevenueBtn;

    @FXML
    void ordersReports(ActionEvent event) throws IOException {
    	start(event,"CEOOrdersReportsScreen","CEO Orders Reports","");
    }
    @FXML
    void revenueReports(ActionEvent event) throws IOException {
    	start(event,"CEORevenueReportsScreen","CEO revenue Reports","");
    }
    @FXML
    void HistogramOrders(ActionEvent event) throws IOException {
    	start(event,"CEOHistogramOrdersScreen","","");
    }
    @FXML
    void HistogramComplaints(ActionEvent event) throws IOException {
    	start(event,"CEOHistogramComplaintScreen","","");
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(String string) {
		nameText.setText(LoginScreenController.user.getFirstN() + " " + LoginScreenController.user.getLastN());
		
	}
}
