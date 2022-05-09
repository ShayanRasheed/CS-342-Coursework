import javafx.animation.PauseTransition;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashSet;


public class KenoGame extends Application {
    public int spots;
    public int drawings;
    public int totalMoney;
    public int drawingMoney;
    public HashSet<Integer> chosenNums = new HashSet<>();
    public HashSet<Integer> winningNums = new HashSet<>();
    public Insets padding, inset;
    public Text title;
    public MenuBar menu;
    public PauseTransition ps = new PauseTransition(Duration.seconds(0.25));
    public Background bg = new Background(new BackgroundFill(Color.LIGHTSALMON, CornerRadii.EMPTY, Insets.EMPTY));
    public Background bg2 = new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY));
    public Background bg3 = new Background(new BackgroundFill(Color.THISTLE, CornerRadii.EMPTY, Insets.EMPTY));
    public MenuItem changeLook;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Keno Game");

        // BUTTONS FOR HOME PAGE
        MenuItem rules = new MenuItem("Rules");
        MenuItem odds = new MenuItem("Odds of Winning");
        MenuItem play = new MenuItem("Play Game");
        MenuItem exit = new MenuItem("Exit Game");
        changeLook = new MenuItem("Change Look");

        Menu option = new Menu("Menu");
        option.getItems().addAll(rules, odds, play, exit);

        menu = new MenuBar(option);

        exit.setOnAction((ActionEvent event) -> {
            Platform.exit();
        });

        // TEXT FOR RULES AND ODDS
        title = new Text("The Keno Game");
        title.setStyle("-fx-font-size: 2.5em; -fx-font-weight: BOLD");
        title.setFill(Color.DARKRED);

        Text instructions = new Text("Click \"Play Game\" under \"Menu\" to begin");
        instructions.setStyle("-fx-font-size: 1.5em; -fx-font-weight: BOLD");
        instructions.setFill(Color.DARKRED);

        VBox home = new VBox(title, instructions);
        home.setSpacing(20);
        home.setAlignment(Pos.CENTER);

        Text oddsOne = new Text("Prize Chart:\n\n");
        oddsOne.setStyle("-fx-font-size: 1.5em; -fx-font-weight: BOLD");
        oddsOne.setFill(Color.DARKRED);
        Text oddsTwo = new Text("1 Spot Game:\n1 Match -> $2\nOdds: 1 in 4.00\n\n4 Spot Game:\n2 Matches -> $1\n3 Matches -> $5\n4 Matches -> $75\nOdds: 1 in 3.86\n\n8 Spot Game:\n4 Matches -> $2\n5 Matches -> $12\n6 Matches -> $50\n7 Matches -> 750\n8 Matches -> $10,000\nOdds: 1 in 9.77\n");
        Text oddsThree = new Text("10 Spot Game:\n0 Matches -> $5\n5 Matches -> $2\n6 Matches -> $15\n7 Matches -> $40\n8 Matches -> $450\n9 Matches -> $4,250\n10 Matches -> $100,000\nOdds: 1 in 9.05\n\n");
        oddsTwo.setStyle("-fx-font-size: 1.5em; -fx-font-family: Gill Sans, sans-serif; -fx-font-weight: BOLD");
        oddsTwo.setFill(Color.DARKRED);
        oddsThree.setStyle("-fx-font-size: 1.5em; -fx-font-family: Gill Sans, sans-serif; -fx-font-weight: BOLD");
        oddsThree.setFill(Color.DARKRED);

        HBox oddsText = new HBox(oddsTwo, oddsThree);
        oddsText.setAlignment(Pos.CENTER);
        oddsText.setSpacing(20);
        VBox allOddsText = new VBox(oddsOne, oddsText);
        allOddsText.setAlignment(Pos.CENTER);

        Text rulestxt = new Text("How to Play: \nFirst, select a number of spots (1, 4, 8, or 10). \nThen, select that amount of numbers for the drawing.\nFinally, you will select the number of times\n you want to use these numbers in drawing (up to a maximum of 4).\nOnce you press submit, 20 numbers will be drawn in each drawing\nand you will receive an amount of money\n based on the number of numbers you correctly guessed.\n");
        rulestxt.setStyle("-fx-font-size: 1.5em; -fx-font-family: Gill Sans, sans-serif; -fx-font-weight: BOLD");
        rulestxt.setFill(Color.DARKRED);

        TextFlow rulesText = new TextFlow(rulestxt);
        rulesText.setTextAlignment(TextAlignment.CENTER);
        rulesText.setLineSpacing(5);

        // INSETS FOR PADDING
        inset = new Insets(0, 0, 20, 0);
        padding = new Insets(20, 0, 0, 0);

        // BUTTON EVENT HANDLERS FOR RULES AND ODDS
        rules.setOnAction((ActionEvent event) -> {
            // SETUP NEW SCENE FOR RULES PAGE
            BorderPane rulesPane = new BorderPane();
            rulesPane.setPadding(inset);
            VBox rulesCenter = new VBox(rulesText);
            rulesCenter.setAlignment(Pos.CENTER);
            rulesPane.setCenter(rulesCenter);
            rulesPane.setBackground(bg);

            Button backBtn = new Button("Back");
            backBtn.setMinSize(70, 35);
            VBox bottom = new VBox(backBtn);
            bottom.setAlignment(Pos.CENTER);
            rulesPane.setBottom(bottom);

            Scene prev = primaryStage.getScene();
            Scene rulesScene = new Scene(rulesPane, 700,700);
            primaryStage.setScene(rulesScene);
            primaryStage.show();

            // BACK BUTTON EVENT
            backBtn.setOnAction((ActionEvent actionEvent) -> {
                primaryStage.setScene(prev);
                primaryStage.show();
            });
        });

        odds.setOnAction((ActionEvent event) -> {
            // SETUP SCENE FOR ODDS PAGE
            BorderPane oddsPane = new BorderPane();
            oddsPane.setBackground(bg);
            oddsPane.setPadding(inset);
            VBox oddsCenter = new VBox(allOddsText);
            oddsCenter.setAlignment(Pos.CENTER);
            oddsPane.setCenter(oddsCenter);

            Button backBtn = new Button("Back");
            backBtn.setMinSize(70, 35);
            VBox bottom = new VBox(backBtn);
            bottom.setAlignment(Pos.CENTER);
            oddsPane.setBottom(bottom);

            Scene prev = primaryStage.getScene();
            Scene oddsScene = new Scene(oddsPane, 700,700);
            primaryStage.setScene(oddsScene);
            primaryStage.show();

            // BACK BUTTON EVENT
            backBtn.setOnAction((ActionEvent actionEvent) -> {
                primaryStage.setScene(prev);
                primaryStage.show();
            });
        });

        // BORDERPANE FOR HOME PAGE
        BorderPane pane = new BorderPane();
        pane.setTop(menu);
        pane.setCenter(home);
        pane.setBackground(bg);

        BorderPane.setAlignment(rulesText, Pos.CENTER);

        pane.setPadding(inset);
        BorderPane.setAlignment(title, Pos.CENTER);
        title.setTextAlignment(TextAlignment.CENTER);

        // HOME SCREEN DISPLAY
        Scene scene = new Scene(pane, 700,700);
        primaryStage.setScene(scene);
        primaryStage.show();

        // PLAY BUTTON EVENT HANDLER
        play.setOnAction((ActionEvent event) -> {
            option.getItems().add(changeLook);
            play.setDisable(true);
            // CALL SETUP FUNCTION
            setUp(primaryStage);
        });
    }

    // SETUP FUNCTION
    // PREPARES THE SCENE FOR "PLAY" SCREEN
    // WHERE USER WILL SELECT NUMBERS
    public void setUp(Stage primaryStage) {
        // MAKE NEW BORDERPANE
        BorderPane playBoard = new BorderPane();
        playBoard.setBackground(bg);
        playBoard.setPadding(inset);
        VBox top = new VBox(menu, title);
        top.setAlignment(Pos.CENTER);
        top.setSpacing(10);
        playBoard.setTop(top);

        // SETUP THE GRID PANE WITH 80 BUTTONS
        GridPane gp = new GridPane();
        Button[] buttons = new Button[80];

        for(int i = 0; i < 80; i++) {
            buttons[i] = new Button("" + (i+1));
            gp.add(buttons[i], i % 10, i / 10);
            int finalI = i;
            // EVENT HANDLER FOR EACH BUTTON
            buttons[i].setOnAction((ActionEvent aEvent) -> {
                if(chosenNums.size() < spots) {
                    chosenNums.add(Integer.parseInt(buttons[finalI].getText()));
                    buttons[finalI].setDisable(true);
                }
            });
        }
        gp.setVgap(5);
        gp.setHgap(5);

        // TEXT FOR QUESTIONS
        Text spotText = new Text("Now, choose which numbers you'd like to use. Press \"reset\" if you want to start over.");
        spotText.setStyle("-fx-font-size: 1.2em; -fx-font-family: Gill Sans, sans-serif; -fx-font-weight: BOLD");
        spotText.setFill(Color.DARKRED);

        // BUTTONS FOR QUESTIONS
        Button submitBtn = new Button("Submit");
        Button resetBtn = new Button("Reset");
        Button pickForMe = new Button("Choose for me");

        // EVENT HANDLER FOR RESET AND PICK FOR ME
        resetBtn.setOnAction((ActionEvent aEvent) -> {
            // Clear ChosenNums and enable buttons
            chosenNums.clear();
            for(Button b : buttons) {
                b.setDisable(false);
            }
        });

        pickForMe.setOnAction((ActionEvent aEvent) -> {
            // clear chosenNums and enable buttons
            chosenNums.clear();
            for(Button b : buttons) {
                b.setDisable(false);
            }
            // Randomly choose spot amount of numbers and set them as chosenNums
            for(int i = 0; i < spots; i++) {
                int rand = (int)Math.floor(Math.random() * 80);
                while(chosenNums.contains(rand) || rand == 0) {
                    rand = (int)Math.floor(Math.random() * 80);
                }
                chosenNums.add(rand);
                // Disable the corresponding buttons
                buttons[rand].setDisable(true);
            }
        });

        // DISPLAY TOTAL MONEY EARNED SO FAR
        Text moneyDisplay = new Text("Total money earned: $" + totalMoney);
        moneyDisplay.setStyle("-fx-font-size: 1.5em; -fx-font-family: Gill Sans, sans-serif; -fx-font-weight: BOLD");
        moneyDisplay.setFill(Color.DARKRED);

        // SET ALL THE COMPONENTS TO THE CENTER OF THE SCENE
        HBox spotBtns = new HBox(resetBtn, pickForMe, submitBtn);
        spotBtns.setAlignment(Pos.CENTER);
        spotBtns.setSpacing(10);

        VBox gridBox = new VBox(spotText, gp, spotBtns);
        gridBox.setVisible(false);
        gridBox.setAlignment(Pos.CENTER);
        gridBox.setSpacing(20);
        Text[] questions = new Text[2];
        // CALL QUESTION VBOX FUNCTION
        VBox center = questionVbox(gridBox, questions);

        VBox finalCenter = new VBox(center, gridBox, moneyDisplay);
        finalCenter.setPadding(padding);
        gp.setAlignment(Pos.CENTER);
        finalCenter.setSpacing(30);
        finalCenter.setAlignment(Pos.CENTER);
        playBoard.setCenter(finalCenter);

        Scene playScene = new Scene(playBoard, 700,700);
        primaryStage.setScene(playScene);
        primaryStage.show();

        // CHANGE LOOK EVENT HANDLER -- CHANGES BACKGROUND AND TEXT COLOR, FONT
        changeLook.setOnAction((ActionEvent aEvent) -> {
            if(playBoard.getBackground() == bg2) {
                playBoard.setBackground(bg3);

                spotText.setStyle("-fx-font-size: 1.2em; -fx-font-family: 'Palatino Linotype'; -fx-font-weight: BOLD");
                spotText.setFill(Color.DARKMAGENTA);

                moneyDisplay.setStyle("-fx-font-size: 1.5em; -fx-font-family: 'Palatino Linotype'; -fx-font-weight: BOLD");
                moneyDisplay.setFill(Color.DARKMAGENTA);

                questions[0].setStyle("-fx-font-size: 1.5em; -fx-font-family: 'Palatino Linotype'; -fx-font-weight: BOLD");
                questions[0].setFill(Color.DARKMAGENTA);

                questions[1].setStyle("-fx-font-size: 1.5em; -fx-font-family: 'Palatino Linotype'; -fx-font-weight: BOLD");
                questions[1].setFill(Color.DARKMAGENTA);

                title.setStyle("-fx-font-size: 2.5em; -fx-font-family: 'Palatino Linotype'; -fx-font-weight: BOLD");
                title.setFill(Color.DARKMAGENTA);
            }
            else {
                playBoard.setBackground(bg2);

                spotText.setStyle("-fx-font-size: 1.2em; -fx-font-family: 'Ink Free'; -fx-font-weight: BOLD;");
                spotText.setFill(Color.STEELBLUE);

                moneyDisplay.setStyle("-fx-font-size: 1.5em; -fx-font-family: 'Ink Free'; -fx-font-weight: BOLD;");
                moneyDisplay.setFill(Color.STEELBLUE);

                questions[0].setStyle("-fx-font-size: 1.5em; -fx-font-family: 'Ink Free'; -fx-font-weight: BOLD");
                questions[0].setFill(Color.STEELBLUE);

                questions[1].setStyle("-fx-font-size: 1.5em; -fx-font-family: 'Ink Free'; -fx-font-weight: BOLD;");
                questions[1].setFill(Color.STEELBLUE);

                title.setStyle("-fx-font-size: 2.5em; -fx-font-family: 'Ink Free'; -fx-font-weight: BOLD;");
                title.setFill(Color.STEELBLUE);
            }
        });

        // SUBMIT BUTTON EVENT HANDLER
        submitBtn.setOnAction((ActionEvent aEvent) -> {
            // MAKE SURE THE CORRECT NUMBER OF NUMS WERE CHOSEN
            if(chosenNums.size() == spots) {
                title.setStyle("-fx-font-size: 2.5em; -fx-font-weight: BOLD");
                title.setFill(Color.DARKRED);

                // SETUP FIRST DRAWING
                if (drawings > 1) {
                    Text[] displayOne = new Text[24];
                    BorderPane resultsBoardOne = resultsBoard(displayOne);
                    Button continueBtn = new Button("Next Drawing");
                    continueBtn.setMinSize(70, 35);
                    VBox bottom = new VBox(continueBtn);
                    bottom.setAlignment(Pos.CENTER);
                    bottom.setPadding(inset);
                    resultsBoardOne.setBottom(bottom);

                    Scene resultsScene = new Scene(resultsBoardOne, 700, 700);
                    primaryStage.setScene(resultsScene);
                    primaryStage.show();

                    // PAUSE TRANSITION FOR DISPLAYING DRAWING RESULTS
                    final int[] f = {0};
                    ps.setOnFinished((ActionEvent event) -> {
                        displayOne[f[0]].setVisible(true);
                        if(f[0] < 23) {
                            f[0]++;
                            ps.play();
                        }
                    });
                    ps.play();

                    // SETUP SECOND DRAWING
                    continueBtn.setOnAction((ActionEvent a) -> {
                        if (drawings > 2) {
                            title.setStyle("-fx-font-size: 2.5em; -fx-font-weight: BOLD");
                            title.setFill(Color.DARKRED);
                            Text[] displayTwo = new Text[24];
                            BorderPane resultsBoardTwo = resultsBoard(displayTwo);
                            Button continueBtnTwo = new Button("Next Drawing");
                            VBox bottomTwo = new VBox(continueBtnTwo);
                            bottomTwo.setAlignment(Pos.CENTER);
                            continueBtnTwo.setMinSize(70, 35);
                            bottomTwo.setPadding(inset);
                            resultsBoardTwo.setBottom(bottomTwo);

                            Scene resultsSceneTwo = new Scene(resultsBoardTwo, 700, 700);
                            primaryStage.setScene(resultsSceneTwo);
                            primaryStage.show();

                            final int[] g = {0};
                            ps.setOnFinished((ActionEvent event) -> {
                                displayTwo[g[0]].setVisible(true);
                                if(g[0] < 23) {
                                    g[0]++;
                                    ps.play();
                                }
                            });
                            ps.play();

                            // SETUP THIRD DRAWING
                            continueBtnTwo.setOnAction((ActionEvent aEvents) -> {
                                if (drawings > 3) {
                                    title.setStyle("-fx-font-size: 2.5em; -fx-font-weight: BOLD");
                                    title.setFill(Color.DARKRED);
                                    Text[] displayThree = new Text[24];
                                    BorderPane resultsBoardThree = resultsBoard(displayThree);
                                    Button continueBtnThree = new Button("Next Drawing");
                                    VBox bottomThree = new VBox(continueBtnThree);
                                    bottomThree.setAlignment(Pos.CENTER);
                                    continueBtnThree.setMinSize(70, 35);
                                    bottomThree.setPadding(inset);
                                    resultsBoardThree.setBottom(bottomThree);

                                    Scene resultsSceneThree = new Scene(resultsBoardThree, 700, 700);
                                    primaryStage.setScene(resultsSceneThree);
                                    primaryStage.show();

                                    final int[] h = {0};
                                    ps.setOnFinished((ActionEvent event) -> {
                                        displayThree[h[0]].setVisible(true);
                                        if(h[0] < 23) {
                                            h[0]++;
                                            ps.play();
                                        }
                                    });
                                    ps.play();

                                    continueBtnThree.setOnAction((ActionEvent ActionEvents) -> {
                                        lastScreen(primaryStage);
                                    });
                                } else {
                                    lastScreen(primaryStage);
                                }
                            });
                        } else {
                            lastScreen(primaryStage);
                        }
                    });
                } else {
                    lastScreen(primaryStage);
                }
            }
        });
    }

    // QUESTION VBOX FUNCTION
    // CREATES A VBOX CONTAINING THE QUESTIONS ASKED ON THE PLAY SCREEN
    public VBox questionVbox(VBox gridBox, Text[] questions) {
        // CREATE TEXT AND BUTTONS FOR FIRST QUESTION
        Text askNumSpots = new Text("How many spots would you like to choose?");
        questions[0] = askNumSpots;
        askNumSpots.setStyle("-fx-font-size: 1.5em; -fx-font-family: Gill Sans, sans-serif; -fx-font-weight: BOLD");
        askNumSpots.setFill(Color.DARKRED);

        Button oneSpot = new Button("1");
        Button fourSpot = new Button("4");
        Button eightSpot = new Button("8");
        Button tenSpot = new Button("10");
        Button continueBtn = new Button("Continue");
        HBox spotBtns = new HBox(oneSpot, fourSpot, eightSpot, tenSpot);
        spotBtns.setAlignment(Pos.CENTER);

        // TEXT AND BUTTONS FOR SECOND QUESTION
        Text askNumDrawings = new Text("How many drawings will you play?");
        questions[1] = askNumDrawings;
        askNumDrawings.setStyle("-fx-font-size: 1.5em; -fx-font-family: Gill Sans, sans-serif; -fx-font-weight: BOLD");
        askNumDrawings.setFill(Color.DARKRED);

        Button oneDrawing = new Button("1");
        Button twoDrawing = new Button("2");
        Button threeDrawing = new Button("3");
        Button fourDrawing = new Button("4");
        Button continueBtnTwo = new Button("Continue");
        HBox drawingBtns = new HBox(oneDrawing, twoDrawing, threeDrawing, fourDrawing);
        drawingBtns.setAlignment(Pos.CENTER);

        VBox firstQuestion = new VBox(askNumSpots, spotBtns, continueBtn);
        firstQuestion.setAlignment(Pos.CENTER);

        VBox secondQuestion = new VBox(askNumDrawings, drawingBtns, continueBtnTwo);
        secondQuestion.setPadding(padding);
        secondQuestion.setAlignment(Pos.CENTER);
        secondQuestion.setVisible(false);

        // EVENT HANDLER FOR EACH BUTTON
        oneSpot.setOnAction((ActionEvent a1) -> {
            spots = 1;
            oneSpot.setDisable(true);
            fourSpot.setDisable(false);
            eightSpot.setDisable(false);
            tenSpot.setDisable(false);
        });

        fourSpot.setOnAction((ActionEvent a2) -> {
            spots = 4;
            oneSpot.setDisable(false);
            fourSpot.setDisable(true);
            eightSpot.setDisable(false);
            tenSpot.setDisable(false);
        });

        eightSpot.setOnAction((ActionEvent a3) -> {
            spots = 8;
            oneSpot.setDisable(false);
            fourSpot.setDisable(false);
            eightSpot.setDisable(true);
            tenSpot.setDisable(false);
        });

        tenSpot.setOnAction((ActionEvent a4) -> {
            spots = 10;
            oneSpot.setDisable(false);
            fourSpot.setDisable(false);
            eightSpot.setDisable(false);
            tenSpot.setDisable(true);
        });

        continueBtn.setOnAction((ActionEvent a5) -> {
            if(spots != 0) {
                oneSpot.setDisable(true);
                fourSpot.setDisable(true);
                eightSpot.setDisable(true);
                tenSpot.setDisable(true);
                secondQuestion.setVisible(true);
                continueBtn.setText("Selected: " + spots);
            }
        });

        oneDrawing.setOnAction((ActionEvent a6) -> {
            drawings = 1;
            oneDrawing.setDisable(true);
            twoDrawing.setDisable(false);
            threeDrawing.setDisable(false);
            fourDrawing.setDisable(false);
        });

        twoDrawing.setOnAction((ActionEvent a6) -> {
            drawings = 2;
            oneDrawing.setDisable(false);
            twoDrawing.setDisable(true);
            threeDrawing.setDisable(false);
            fourDrawing.setDisable(false);
        });

        threeDrawing.setOnAction((ActionEvent a6) -> {
            drawings = 3;
            oneDrawing.setDisable(false);
            twoDrawing.setDisable(false);
            threeDrawing.setDisable(true);
            fourDrawing.setDisable(false);
        });

        fourDrawing.setOnAction((ActionEvent a6) -> {
            drawings = 4;
            oneDrawing.setDisable(false);
            twoDrawing.setDisable(false);
            threeDrawing.setDisable(false);
            fourDrawing.setDisable(true);
        });

        continueBtnTwo.setOnAction((ActionEvent a7) -> {
            if(drawings != 0) {
                oneDrawing.setDisable(true);
                twoDrawing.setDisable(true);
                threeDrawing.setDisable(true);
                fourDrawing.setDisable(true);
                gridBox.setVisible(true);
                continueBtnTwo.setText("Selected: " + drawings);
            }
        });

        // RETURN THE FINAL VBOX
        VBox center = new VBox(firstQuestion, secondQuestion);
        center.setSpacing(20);
        return center;
    }

    // LAST SCREEN FUNCTION
    // SETS UP THE SCENE FOR THE FINAL SCREEN THAT USER WILL SEE FOR EACH DRAWING
    public void lastScreen(Stage primaryStage) {
        // CALL RESULTSBOARD FUNCTION AND CREATE PLAY AGAIN BUTTON
        Text[] display = new Text[24];
        BorderPane resultsBoard = resultsBoard(display);
        Button playAgain = new Button("Play Again!");
        HBox endBtns = new HBox(playAgain);
        endBtns.setSpacing(20);
        endBtns.setAlignment(Pos.CENTER);
        playAgain.setMinSize(70, 35);
        endBtns.setPadding(inset);
        resultsBoard.setBottom(endBtns);

        // WHEN PLAY AGAIN IS PRESSED, CALL SETUP FUNCTION TO REPEAT THE ENTIRE PROCESS
        playAgain.setOnAction((ActionEvent a) -> {
            // RESET ALL VARIABLES
            spots = 0;
            drawings = 0;
            drawingMoney = 0;
            chosenNums.clear();
            winningNums.clear();

            title.setStyle("-fx-font-size: 2.5em; -fx-font-weight: BOLD");
            title.setFill(Color.DARKRED);

            setUp(primaryStage);
        });

        Scene resultsScene = new Scene(resultsBoard, 700, 700);
        primaryStage.setScene(resultsScene);
        primaryStage.show();

        // PAUSE TRANSITION
        final int[] f = {0};
        ps.setOnFinished((ActionEvent event) -> {
            display[f[0]].setVisible(true);
            if(f[0] < 23) {
                f[0]++;
                ps.play();
            }
        });
        ps.play();
    }

    // RESULTS BOARD FUNCTION
    // CREATES THE SCENE USED TO DISPLAY DRAWING RESULTS
    public BorderPane resultsBoard(Text[] gridNums) {
        BorderPane resultsBoard = new BorderPane();
        resultsBoard.setBackground(bg);
        // CREATE HASH SET TO KEEP TRACK OF CORRECTLY CHOSEN NUMS
        HashSet<Integer> correctlyChosen = new HashSet<>();
        VBox top = new VBox(menu, title);
        title.setStyle("-fx-font-size: 2.5em; -fx-font-weight: BOLD");
        title.setFill(Color.DARKRED);
        top.setAlignment(Pos.CENTER);
        top.setSpacing(10);
        resultsBoard.setTop(top);
        Text drawinginProg = new Text("Drawing The Winning Numbers...");
        drawinginProg.setStyle("-fx-font-size: 1.5em; -fx-font-family: Gill Sans, sans-serif; -fx-font-weight: BOLD");
        drawinginProg.setFill(Color.DARKRED);

        // CREATE A GRID PANE TO HOLD AND DISPLAY EACH OF THE WINNING NUMBERS
        GridPane results = new GridPane();

        makeGrid(gridNums, winningNums, chosenNums, correctlyChosen, results);

        // DISPLAY WHICH NUMBERS THE USER GUESSED CORRECTLY
        String display = "Correctly chosen numbers:";
        for(int i : correctlyChosen) {
            display += " " + i;
        }
        Text correctNums = new Text(display);
        correctNums.setStyle("-fx-font-size: 1.5em; -fx-font-family: Gill Sans, sans-serif; -fx-font-weight: BOLD");
        correctNums.setFill(Color.DARKRED);
        correctNums.setVisible(false);
        gridNums[20] = correctNums;

        // CALCULATE THE AMOUNT OF MONEY WON IN THIS DRAWING AND DISPLAY STATS
        int money = calcResult(correctlyChosen, spots);
        drawingMoney += money;
        totalMoney += money;
        Text amountWon = new Text("Amount won this drawing: " + money);
        amountWon.setVisible(false);
        amountWon.setStyle("-fx-font-size: 1.5em; -fx-font-family: Gill Sans, sans-serif; -fx-font-weight: BOLD");
        amountWon.setFill(Color.DARKRED);
        Text drawingWinnings = new Text("Amount won in this set of drawings: " + drawingMoney);
        drawingWinnings.setVisible(false);
        drawingWinnings.setStyle("-fx-font-size: 1.5em; -fx-font-family: Gill Sans, sans-serif; -fx-font-weight: BOLD");
        drawingWinnings.setFill(Color.DARKRED);
        Text total = new Text("Total money earned: " + totalMoney);
        total.setVisible(false);
        total.setStyle("-fx-font-size: 1.5em; -fx-font-family: Gill Sans, sans-serif; -fx-font-weight: BOLD");
        total.setFill(Color.DARKRED);

        gridNums[21] = amountWon;
        gridNums[22] = drawingWinnings;
        gridNums[23] = total;

        results.setHgap(15);
        results.setVgap(15);
        results.setAlignment(Pos.CENTER);
        results.setPadding(padding);

        // SET ALL OF THESE COMPONENTS TO THE CENTER OF THE SCENE
        VBox center = new VBox(drawinginProg, results, correctNums, amountWon, drawingWinnings, total);
        center.setSpacing(30);
        center.setAlignment(Pos.CENTER);
        resultsBoard.setCenter(center);

        // CHANGE LOOK EVENT HANDLER FOR THIS PAGE
        changeLook.setOnAction((ActionEvent a) -> {
            if(resultsBoard.getBackground() == bg2) {
                resultsBoard.setBackground(bg3);

                title.setStyle("-fx-font-size: 1.5em; -fx-font-family: 'Palatino Linotype'; -fx-font-weight: BOLD");
                title.setFill(Color.DARKMAGENTA);

                drawinginProg.setStyle("-fx-font-size: 1.5em; -fx-font-family: 'Palatino Linotype'; -fx-font-weight: BOLD");
                drawinginProg.setFill(Color.DARKMAGENTA);

                for(int i = 0; i < 24; i++) {
                    gridNums[i].setStyle("-fx-font-size: 1.5em; -fx-font-family: 'Palatino Linotype'; -fx-font-weight: BOLD");
                    gridNums[i].setFill(Color.DARKMAGENTA);
                }
            }
            else {
                resultsBoard.setBackground(bg2);

                title.setStyle("-fx-font-size: 1.5em; -fx-font-family: 'Ink Free'; -fx-font-weight: BOLD;");
                title.setFill(Color.STEELBLUE);

                drawinginProg.setStyle("-fx-font-size: 2.5em; -fx-font-family: 'Ink Free'; -fx-font-weight: BOLD;");
                drawinginProg.setFill(Color.STEELBLUE);

                for(int i = 0; i < 24; i++) {
                    gridNums[i].setStyle("-fx-font-size: 1.5em; -fx-font-family: 'Ink Free'; -fx-font-weight: BOLD;");
                    gridNums[i].setFill(Color.STEELBLUE);
                }
            }
        });

        return resultsBoard;
    }

    // MAKE GRID FUNCTION
    // RANDOMLY CHOOSES 20 NUMBERS BETWEEN 1 AND 80 AND PUTS THEM INTO A GRID PANE
    public static void makeGrid(Text[] gridNums, HashSet<Integer> winningNums, HashSet<Integer> chosenNums, HashSet<Integer> correctlyChosen, GridPane results) {
        winningNums.clear();
        correctlyChosen.clear();
        for(int i = 0; i < 20; i++) {
            int rand = (int)Math.floor(Math.random() * 80);
            while(winningNums.contains(rand) || rand == 0) {
                rand = (int)Math.floor(Math.random() * 80);
            }
            gridNums[i] = new Text("" + rand);
            gridNums[i].setStyle("-fx-font-size: 1.5em; -fx-font-family: Gill Sans, sans-serif;");
            gridNums[i].setFill(Color.DARKRED);
            gridNums[i].setVisible(false);
            winningNums.add(rand);
            if(chosenNums.contains(rand)) {
                correctlyChosen.add(rand);
                gridNums[i].setStyle("-fx-font-size: 1.5em; -fx-font-family: Gill Sans, sans-serif; -fx-font-weight: BOLD");
            }
            results.add(gridNums[i], i % 5, i % 4);
        }
    }

    // CALC RESULT FUNCTION
    // CALCULATES THE AMOUNT OF MONEY WON IN A DRAWING
    public static int calcResult(HashSet<Integer> correctlyChosen, int spots) {
        int money = 0;

        if(spots == 1) {
            if(correctlyChosen.size() == 1) {
                money += 2;
            }
        }
        else if(spots == 4) {
            if(correctlyChosen.size() == 2) {
                money += 1;
            }
            if(correctlyChosen.size() == 3) {
                money += 5;
            }
            if(correctlyChosen.size() == 4) {
                money += 75;
            }
        }
        else if(spots == 8) {
            if(correctlyChosen.size() == 4) {
                money += 2;
            }
            if(correctlyChosen.size() == 5) {
                money += 12;
            }
            if(correctlyChosen.size() == 6) {
                money += 50;
            }
            if(correctlyChosen.size() == 7) {
                money += 750;
            }
            if(correctlyChosen.size() == 8) {
                money += 10000;
            }
        }
        else if(spots == 10) {
            if(correctlyChosen.size() == 0) {
                money += 5;
            }
            if(correctlyChosen.size() == 5) {
                money += 2;
            }
            if(correctlyChosen.size() == 6) {
                money += 15;
            }
            if(correctlyChosen.size() == 7) {
                money += 40;
            }
            if(correctlyChosen.size() == 8) {
                money += 450;
            }
            if(correctlyChosen.size() == 9) {
                money += 4250;
            }
            if(correctlyChosen.size() == 10) {
                money += 100000;
            }
        }

        return money;
    }
}
