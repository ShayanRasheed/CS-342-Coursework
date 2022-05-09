import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MyController implements Initializable {
    CoffeeBuilder order = new CoffeeBuilder();

    @FXML
    ListView<String> orderTxt;

    @FXML
    Button startBtn;

    @FXML
    Button deleteBtn;

    @FXML
    Button submitBtn;

    @FXML
    Button sugarBtn;

    @FXML
    Button creamBtn;

    @FXML
    Button shotBtn;

    @FXML
    Button vanillaBtn;

    @FXML
    Button whippedCreamBtn;

    @FXML
    Text prompt;

//    @FXML
//    Text orderTxt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prompt.getStyleClass().add("text");
        orderTxt.getStyleClass().add("text");
    }

    public void start() {
        orderTxt.getItems().clear();
        orderTxt.getItems().add("Your Order:");
        orderTxt.getItems().add("Black Coffee: $3.99:");
        startBtn.setDisable(true);
        deleteBtn.setDisable(false);
        submitBtn.setDisable(false);
        sugarBtn.setDisable(false);
        creamBtn.setDisable(false);
        shotBtn.setDisable(false);
        vanillaBtn.setDisable(false);
        whippedCreamBtn.setDisable(false);
    }

    public void delete() {
        orderTxt.getItems().clear();
        reset();
    }

    public void reset() {
        order = new CoffeeBuilder();
        startBtn.setDisable(false);
        deleteBtn.setDisable(true);
        submitBtn.setDisable(true);
        sugarBtn.setDisable(true);
        creamBtn.setDisable(true);
        shotBtn.setDisable(true);
        vanillaBtn.setDisable(true);
        whippedCreamBtn.setDisable(true);
    }

    public void submit() {
        orderTxt.getItems().add(order.getOrder());
        reset();
    }

    public void addSugar() {
        order.addSugar();
        orderTxt.getItems().add("sugar: $.50");
    }

    public void addCream() {
        order.addCream();
        orderTxt.getItems().add("cream: $.50");
    }

    public void addShot() {
        order.addExtraShot();
        orderTxt.getItems().add("extra shot: $1.20");
    }

    public void addVanilla() {
        order.addVanilla();
        orderTxt.getItems().add("vanilla: $.35");
    }

    public void addWhippedCream() {
        order.addWhippedCream();
        orderTxt.getItems().add("whipped cream: $.25");
    }
}
