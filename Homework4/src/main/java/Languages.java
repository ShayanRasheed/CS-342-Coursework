import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicReference;

public class Languages extends Application {
	int chosenLang;
	Language lang;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Homework Four - Shayan Rasheed");

		setup(primaryStage);
	}

	public void setup(Stage primaryStage) {
		BorderPane bp = new BorderPane();
		bp.setStyle("-fx-background-color: LIGHTSKYBLUE");
		bp.setPadding(new Insets(15));

		Text title = new Text("Select a Language");
		title.setStyle("-fx-font-size: 3.5em; -fx-font-weight: BOLD;");
		title.setFill(Color.MEDIUMBLUE);
		Button frenchBtn = new Button("French");
		Button spanishBtn = new Button("Spanish");
		Button hindiBtn = new Button("Hindi");

		frenchBtn.setOnAction((ActionEvent a) -> {
			frenchBtn.setDisable(true);
			spanishBtn.setDisable(false);
			hindiBtn.setDisable(false);
			chosenLang = 1;
		});

		spanishBtn.setOnAction((ActionEvent a) -> {
			frenchBtn.setDisable(false);
			spanishBtn.setDisable(true);
			hindiBtn.setDisable(false);
			chosenLang = 2;
		});

		hindiBtn.setOnAction((ActionEvent a) -> {
			frenchBtn.setDisable(false);
			spanishBtn.setDisable(false);
			hindiBtn.setDisable(true);
			chosenLang = 3;
		});

		Button showGreeting = new Button("Show Greeting");

		HBox langBtns = new HBox(frenchBtn, spanishBtn, hindiBtn);
		langBtns.setAlignment(Pos.CENTER);
		langBtns.setSpacing(15);

		VBox top = new VBox(title, langBtns, showGreeting);
		top.setAlignment(Pos.CENTER);
		top.setSpacing(20);
		bp.setTop(top);

		showGreeting.setOnAction((ActionEvent a) -> {
			if(chosenLang != 0) {
				if (chosenLang == 1) {
					lang = new French();
				} else if (chosenLang == 2) {
					lang = new Spanish();
				} else {
					lang = new Hindi();
				}

				frenchBtn.setDisable(true);
				spanishBtn.setDisable(true);
				hindiBtn.setDisable(true);

				Text greeting = new Text(lang.greeting());
				greeting.setStyle("-fx-font-size: 1.5em; -fx-font-weight: BOLD;");
				greeting.setFill(Color.MEDIUMBLUE);
				Button showQuestion = new Button("Show Question");

				Text question = new Text(lang.question());
				question.setVisible(false);
				question.setStyle("-fx-font-size: 1.5em; -fx-font-weight: BOLD;");
				question.setFill(Color.MEDIUMBLUE);

				Button showStatement = new Button("Show Statement");
				showStatement.setVisible(false);

				Text statement = new Text(lang.statement());
				statement.setVisible(false);
				statement.setStyle("-fx-font-size: 1.5em; -fx-font-weight: BOLD;");
				statement.setFill(Color.MEDIUMBLUE);

				Button showFarewell = new Button("Show Farewell");
				showFarewell.setVisible(false);

				Text farewell = new Text(lang.farewell());
				farewell.setVisible(false);
				farewell.setStyle("-fx-font-size: 1.5em; -fx-font-weight: BOLD;");
				farewell.setFill(Color.MEDIUMBLUE);

				Button reset = new Button("Reset");
				reset.setVisible(false);

				showQuestion.setOnAction((ActionEvent ae) -> {
					question.setVisible(true);
					showStatement.setVisible(true);
				});

				showStatement.setOnAction((ActionEvent ae) -> {
					statement.setVisible(true);
					showFarewell.setVisible(true);
				});

				showFarewell.setOnAction((ActionEvent ae) -> {
					farewell.setVisible(true);
					reset.setVisible(true);
				});

				VBox center = new VBox(greeting, showQuestion, question, showStatement, statement, showFarewell, farewell, reset);
				center.setSpacing(20);
				center.setAlignment(Pos.CENTER);

				bp.setCenter(center);

				reset.setOnAction((ActionEvent ae) -> {
					setup(primaryStage);
				});
			}
		});

		Scene scene = new Scene(bp, 700,700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
