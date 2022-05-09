import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.stage.Stage;

public class JavaFXTemplate extends Application {
	private Button b1;
	private Button b2;
	private TextField t1;
	private TextField t2;
	private VBox box;
	private BorderPane pane;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Shayan Rasheed Homework 3");

		b1 = new Button("Button #1");
		b2 = new Button("Button #2");

		t1 = new TextField();
		t1.setPromptText("enter text here then press button 1");

		t2 = new TextField("final string goes here");
		t2.setEditable(false);
		t2.setPrefWidth(200);

		b1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				t2.setText(t1.getText() + " : from the center text field!");
				b1.setDisable(true);
				b1.setText("pressed");
			}
		});
		b1.setMinSize(50, 50);

		b2.setOnAction(actionEvent -> {
			t1.clear();
			t2.clear();
			t2.setText("final string goes here");
			b1.setText("Button #1");
			b1.setDisable(false);
		});
		b2.setMinSize(50, 50);

		Text t = new Text(10, 50, "Homework Three");
		t.setFont(new Font(30));

		box = new VBox(8);
		box.getChildren().addAll(b1, b2);
		Insets in = new Insets(20);
		Insets in2 = new Insets(0, 0, 0, 40);
		Insets in3 = new Insets(50, 20, 20, 20);

		pane = new BorderPane();
		pane.setCenter(t1);
		pane.setRight(t2);
		pane.setLeft(box);
		pane.setTop(t);
		BorderPane.setAlignment(t, Pos.CENTER);
		BorderPane.setMargin(box, in);
		BorderPane.setMargin(t2, in3);
		BorderPane.setMargin(t1, in2);
		pane.setBackground(new Background(new BackgroundFill(Color.POWDERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

		Insets inset = new Insets(20);
		pane.setPadding(inset);
				
		Scene scene = new Scene(pane, 700,700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
