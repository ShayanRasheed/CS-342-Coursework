
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.WindowEvent;

import java.io.Serializable;
import java.util.Objects;


public class Main extends Application {
	Client clientConnection;
	ListView<String> listItems;
	ListView<String> gameResults;
	Stage primary;
	Button imageOne, imageTwo, imageThree, imageFour, imageFive, exitBtn, continueBtn;
	HBox bottomBtns;
	BorderPane bp;

	private int selectedNum;
	private int totalGuess;

	@Override
	public void start(Stage primaryStage) {
		 try {
			 // TITLE SCREEN:
			 // Contains text for the title and prompts, a button to start
			 // as well as text fields for the port and IP address

			 Text title = new Text("The Ancient Game of Morra");
			 Text prompt = new Text("Enter and IP address and a Port to connect to:");
			 TextField IP = new TextField();
			 IP.setPromptText("Enter IP address...");
			 TextField port = new TextField();
			 port.setPromptText("Enter port number...");
			 Button start = new Button("Start Game!");

			 VBox root = new VBox(title, prompt, IP, port, start);
			 root.setAlignment(Pos.CENTER);
			 root.setSpacing(50);

			 primaryStage.setTitle("Server Application");
             Scene s1 = new Scene(root, 500,500);
             //s1.getStylesheets().add("/styles/style1.css");
			primaryStage.setScene(s1);
			primaryStage.show();
			primary = primaryStage;

			// When start button is pressed, create a new client
			start.setOnAction((ActionEvent a) -> {
				listItems = new ListView<String>();
				gameResults = new ListView<>();

				clientConnection = new Client(IP.getText(), Integer.parseInt(port.getText()), data->{
					Platform.runLater(()->{listItems.getItems().add(data.toString());
					});
				}, data->{
					Platform.runLater(()->{changeGui(data);});
				}, data->{
					Platform.runLater(()->{displayResults((MorraInfo) data);});
				}, data-> {
					Platform.runLater(() -> {
						gameResults.getItems().add(data.toString());
					});
				});
				clientConnection.start();

				// Set GUI to the waiting room
				changeGui("One");
			});
	         
	        } catch(Exception e) {
	            e.printStackTrace();
	            System.exit(1);
	        }

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});
	}

	// Change GUI: Calls functions depending on data sent from client thread
	public void changeGui(Serializable data) {
		if(data == "One") {
			try {
				waitingRoom();
			} catch (Exception e) {
			}
		}
		else if(data == "Two") {
			try {
				gameRoom();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(data == "Three") {
			exitRoom();
		}
	}

	// Exit Room: Scene that is displayed when too many clients are connected
	public void exitRoom() {
		Text errorMsg = new Text("Currently, there is already a game in progress. Please try again later.");
		Button exit = new Button("Exit Program");

		exit.setOnAction((ActionEvent a) -> {
			System.exit(1);
		});

		VBox message = new VBox(errorMsg, exit);
		message.setSpacing(30);
		message.setAlignment(Pos.CENTER);

		Scene exitScene = new Scene(message, 500, 500);
		primary.setScene(exitScene);
		primary.show();
	}

	// Waiting Room: Scene that is displayed when waiting for another player
	public void waitingRoom() {
		Text title = new Text("The Ancient Game of Morra");
		Text waiting = new Text("Waiting for Another Player...");
		VBox root = new VBox(title, waiting);
		root.setSpacing(30);
		root.setAlignment(Pos.CENTER);
		Scene s2 = new Scene(root, 500, 500);
		primary.setScene(s2);
		primary.show();
	}

	// Game Room: The Scene in which the game is played
	public void gameRoom() {
		HBox buttons = createBtns();
		Text title = new Text("Welcome to the Game Room");
		Text prompt = new Text("Select a number 1-5:");
		Text prompt2 = new Text("Enter a guess for the total (Must be a value 2-10):");
		TextField total = new TextField();
		total.setPromptText("Enter total (2-10)");
		Button submit = new Button("Submit");

		continueBtn = new Button("Next Round");
		continueBtn.setVisible(false);

		// Call playGame() in the client thread when submit is pressed
		submit.setOnAction((ActionEvent a) -> {
			if(!Objects.equals(total.getText(), "")) {
				totalGuess = Integer.parseInt(total.getText());
			}
			if(selectedNum != 0 && totalGuess > 1 && totalGuess < 11) {
				submit.setDisable(true);
				imageOne.setDisable(true);
				imageTwo.setDisable(true);
				imageThree.setDisable(true);
				imageFour.setDisable(true);
				imageFive.setDisable(true);
				total.setDisable(true);
				clientConnection.playGame(selectedNum, totalGuess);
			}
		});

		VBox game = new VBox(title, prompt, buttons, prompt2, total, submit);
		game.setAlignment(Pos.CENTER);
		game.setSpacing(20);

		bp = new BorderPane();
		bp.setTop(game);

		Button serverBtn = new Button("Display Server Messages");
		serverBtn.setOnAction((ActionEvent a) -> {
			Scene prevScene = primary.getScene();
			BorderPane serverPane = new BorderPane();
			serverPane.setTop(listItems);
			Button back = new Button("Go Back");
			back.setAlignment(Pos.CENTER);
			serverPane.setBottom(back);
			Scene server = new Scene(serverPane, 500, 500);
			primary.setScene(server);
			primary.show();

			back.setOnAction((ActionEvent ae) -> {
				primary.setScene(prevScene);
				primary.show();
			});
		});

		continueBtn.setOnAction((ActionEvent a) -> {
			gameResults.getItems().clear();
			selectedNum = 0;
			totalGuess = 0;
			gameRoom();
		});

		exitBtn = new Button("Exit Game");
		exitBtn.setVisible(false);

		exitBtn.setOnAction((ActionEvent a) -> {
			System.exit(1);
		});

		bottomBtns = new HBox(serverBtn, continueBtn, exitBtn);
		bottomBtns.setAlignment(Pos.CENTER);
		bottomBtns.setSpacing(20);
		bp.setBottom(bottomBtns);

		Scene s3 = new Scene(bp, 750, 750);
		primary.setScene(s3);
		primary.show();
	}

	// Display results: Called once client thread receives results
	public void displayResults(MorraInfo data) {
		if(data.p1Points == 2 || data.p2Points == 2) {
			exitBtn.setVisible(true);

			Button playAgain = new Button("Play Again");
			playAgain.setOnAction((ActionEvent a) -> {
				clientConnection.send(data);
				gameResults.getItems().clear();
				selectedNum = 0;
				totalGuess = 0;
				waitingRoom();
			});

			bottomBtns.getChildren().remove(continueBtn);
			bottomBtns.getChildren().add(1, playAgain);
		}
		continueBtn.setVisible(true);

		ImageView opponentNum = getPic(data);
		Text opponentText = new Text("Opponent selected:");
		VBox opponent = new VBox(opponentText, opponentNum);
		opponent.setSpacing(10);

		HBox results = new HBox(opponent, gameResults);
		results.setAlignment(Pos.CENTER);
		results.setSpacing(20);

		bp.setCenter(results);
	}

	// GetPic: Returns the image corresponding to the opposing players choice
	public ImageView getPic(MorraInfo data) {
		if(data.isPlayerOne) {
			if(data.p2Num == 1) {
				return new ImageView(new Image("images/fingersOne.jpg"));
			}
			else if(data.p2Num == 2) {
				return new ImageView(new Image("images/fingersTwo.jpg"));
			}
			else if(data.p2Num == 3) {
				return new ImageView(new Image("images/fingersThree.jpg"));
			}
			else if(data.p2Num == 4) {
				return new ImageView(new Image("images/fingersFour.jpg"));
			}
			else {
				return new ImageView(new Image("images/fingersFive.jpg"));
			}
		}
		else {
			if(data.p1Num == 1) {
				return new ImageView(new Image("images/fingersOne.jpg"));
			}
			else if(data.p1Num == 2) {
				return new ImageView(new Image("images/fingersTwo.jpg"));
			}
			else if(data.p1Num == 3) {
				return new ImageView(new Image("images/fingersThree.jpg"));
			}
			else if(data.p1Num == 4) {
				return new ImageView(new Image("images/fingersFour.jpg"));
			}
			else {
				return new ImageView(new Image("images/fingersFive.jpg"));
			}
		}
	}

	// Create buttons: Creates the image buttons on the game screen
	public HBox createBtns() {
		imageOne = new Button();
		imageOne.setGraphic(new ImageView(new Image("images/fingersOne.jpg")));
		imageTwo = new Button();
		imageTwo.setGraphic(new ImageView(new Image("images/fingersTwo.jpg")));
		imageThree = new Button();
		imageThree.setGraphic(new ImageView(new Image("images/fingersThree.jpg")));
		imageFour = new Button();
		imageFour.setGraphic(new ImageView(new Image("images/fingersFour.jpg")));
		imageFive = new Button();
		imageFive.setGraphic(new ImageView(new Image("images/fingersFive.jpg")));
		HBox buttons = new HBox(imageOne, imageTwo, imageThree, imageFour, imageFive);

		imageOne.setOnAction((ActionEvent a) -> {
			imageOne.setDisable(true);
			imageTwo.setDisable(false);
			imageThree.setDisable(false);
			imageFour.setDisable(false);
			imageFive.setDisable(false);
			selectedNum = 1;
		});

		imageTwo.setOnAction((ActionEvent a) -> {
			imageOne.setDisable(false);
			imageTwo.setDisable(true);
			imageThree.setDisable(false);
			imageFour.setDisable(false);
			imageFive.setDisable(false);
			selectedNum = 2;
		});

		imageThree.setOnAction((ActionEvent a) -> {
			imageOne.setDisable(false);
			imageTwo.setDisable(false);
			imageThree.setDisable(true);
			imageFour.setDisable(false);
			imageFive.setDisable(false);
			selectedNum = 3;
		});

		imageFour.setOnAction((ActionEvent a) -> {
			imageOne.setDisable(false);
			imageTwo.setDisable(false);
			imageThree.setDisable(false);
			imageFour.setDisable(true);
			imageFive.setDisable(false);
			selectedNum = 4;
		});

		imageFive.setOnAction((ActionEvent a) -> {
			imageOne.setDisable(false);
			imageTwo.setDisable(false);
			imageThree.setDisable(false);
			imageFour.setDisable(false);
			imageFive.setDisable(true);
			selectedNum = 5;
		});

		buttons.setSpacing(10);
		buttons.setAlignment(Pos.CENTER);
		return buttons;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
