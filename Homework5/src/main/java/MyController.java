

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MyController implements Initializable {
	
	@FXML
	private BorderPane root;

    @FXML
    private Text title;

    @FXML
    private Button b1;

    @FXML
    private Button b2;
    
    @FXML
    private TextField input;
    
    @FXML
    private TextField output;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
	public void b1Method(ActionEvent e) throws IOException{
        output.setText(input.getText() + " : from the center text field!");
        b1.setDisable(true);
        b1.setText("Pressed");
	}

    public void b2Method(ActionEvent e) throws IOException{
        input.clear();
        output.setText("final string goes here");
        b1.setDisable(false);
        b1.setText("button 1");
    }

}
