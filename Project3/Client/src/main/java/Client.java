import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class Client extends Thread {
    // CLIENT DATA MEMBERS

    //socketClient: The client end of the connection
    Socket socketClient;

    // Input/Output streams to communicate with server
    ObjectOutputStream out;
    ObjectInputStream in;
    // Host and Port input by the User
    String host;
    int port;
    // GameInfo: Stores the choices and player number for this client
    MorraInfo gameInfo;
    boolean isPlayerOne = false;

    // Consumers send client info to the GUI

    // ServerMsg: adds strings to the server list
    private Consumer<Serializable> serverMsg;
    // Update GUI: changes the scene depending on the point in the program
    private Consumer<Serializable> updateGui;
    // Update Game: displays the results of each game
    private Consumer<Serializable> updateGame;
    // gameMsg: adds strings to the game results list
    private Consumer<Serializable> gameMsg;

    Client(String host, int port, Consumer<Serializable> call, Consumer<Serializable> call2, Consumer<Serializable> call3, Consumer<Serializable> call4){
        this.host = host;
        this.port = port;
        serverMsg = call;
        updateGui = call2;
        updateGame = call3;
        gameMsg = call4;
    }

    public void run() {

        try {
            socketClient= new Socket(host, port);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
            out.writeObject(gameInfo);
            out.reset();
        }
        catch(Exception e) {
            System.out.println("Failed to Connect");
            System.exit(2);
        }

        try {
            // Get info from server to determine player number
            gameInfo = (MorraInfo) in.readObject();
            isPlayerOne = gameInfo.isPlayerOne;

            // If points are 1000, there are too many clients -> Display error message and exit
            if(gameInfo.p1Points == 1000 && gameInfo.p2Points == 1000) {
                updateGui.accept("Three");
            }

            serverMsg.accept("Accepted into Server");

            // If there aren't two players, client must wait
            if(!gameInfo.hasTwoPlayers) {
                waitingRoom();
            }
            // Update GUI to the game room
            updateGui.accept("Two");
        }
        catch(Exception e) {
            System.out.println("Was not accepted into the server: " + e.getMessage());
            System.exit(3);
        }

        try {
            while(true) {
                int currPointsP1 = gameInfo.p1Points;
                int currPointsP2 = gameInfo.p2Points;

                // Wait for game results from the server
                try {
                    gameInfo = (MorraInfo) in.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                isPlayerOne = gameInfo.isPlayerOne;

                // Update server list
                serverMsg.accept("Results of this game:");
                serverMsg.accept("Player 1's Number: " + gameInfo.p1Num);
                serverMsg.accept("Player 2's Number: " + gameInfo.p2Num);
                serverMsg.accept("--------------------------------------");
                serverMsg.accept("Player 1's Guess: " + gameInfo.p1Guess);
                serverMsg.accept("Player 2's Guess: " + gameInfo.p2Guess);
                serverMsg.accept("--------------------------------------");
                serverMsg.accept("Player 1's Points: " + gameInfo.p1Points);
                serverMsg.accept("Player 2's Points: " + gameInfo.p2Points);

                // Code to Display Results:
                if(isPlayerOne) {
                    if(currPointsP1 < gameInfo.p1Points) {
                        gameMsg.accept("You Won This Round!");
                    } else if (currPointsP2 < gameInfo.p2Points){
                        gameMsg.accept("You Lost This Round!");
                    } else {
                        gameMsg.accept("No Winners This Round!");
                    }

                    gameMsg.accept("Opponent's Guess: " + gameInfo.p2Guess);
                    gameMsg.accept("Opponent's Points: " + gameInfo.p2Points);
                    gameMsg.accept("Your Points: " + gameInfo.p1Points);
                    if(gameInfo.p1Points == 2) {
                        gameMsg.accept("You've won the game!!!");
                    }
                    else if(gameInfo.p2Points == 2) {
                        gameMsg.accept("You've lost the game...");
                    }
                } else {
                    if(currPointsP1 < gameInfo.p1Points) {
                        gameMsg.accept("You Lost This Round!");
                    } else if (currPointsP2 < gameInfo.p2Points){
                        gameMsg.accept("You Won This Round!");
                    } else {
                        gameMsg.accept("No Winners This Round!");
                    }

                    gameMsg.accept("Opponent's Guess: " + gameInfo.p1Guess);
                    gameMsg.accept("Opponent's Points: " + gameInfo.p1Points);
                    gameMsg.accept("Your Points: " + gameInfo.p2Points);

                    if(gameInfo.p2Points == 2) {
                        gameMsg.accept("You've won the game!!!");
                    }
                    else if(gameInfo.p1Points == 2) {
                        gameMsg.accept("You've lost the game...");
                    }
                }

                // Use update game to display results on the GUI
                updateGame.accept(gameInfo);

                // If someone has won, call playAgain
                if(gameInfo.p1Points == 2 || gameInfo.p2Points == 2) {
                    playAgain();
                }
            }
        }
        catch(Exception e) {
            System.out.println("Failed to receive results: " + e.getMessage());
            System.exit(4);
        }

    }

    // Play Again: Called after someone has won
    public void playAgain() {
        try {
            // Receive player number from server
            gameInfo = (MorraInfo) in.readObject();
            isPlayerOne = gameInfo.isPlayerOne;
            serverMsg.accept("Playing Again!");

            // Call waiting room if there aren't two players
            if (!gameInfo.hasTwoPlayers) {
                waitingRoom();
            }
            // Update GUI to the game room
            updateGui.accept("Two");
        }
        catch (Exception e) {
            System.out.println("Failed to play again...");
            System.exit(5);
        }
    }

    // Play Game: Gets the num and total choices from the GUI and sends them to server
    public void playGame(int selectedNum, int totalGuess) {
        try {
            if (isPlayerOne) {
                gameInfo.p1Num = selectedNum;
                gameInfo.p1Guess = totalGuess;
            } else {
                gameInfo.p2Num = selectedNum;
                gameInfo.p2Guess = totalGuess;
            }
            gameInfo.isPlayerOne = isPlayerOne;
            serverMsg.accept("Sending choices to the server...");
            send(gameInfo);
        }
        catch (Exception e) {
            System.out.println("Failed to send choices to server: " + e.getMessage());
            System.exit(6);
        }
    }

    // Waiting Room: Called when waiting for another player
    public void waitingRoom() {
        serverMsg.accept("Waiting for another player...");
        // Wait until server says that there are two players
        while(!gameInfo.hasTwoPlayers) {
            try {
                gameInfo = (MorraInfo) in.readObject();
            }
            catch(Exception e) {
                System.out.println("Failure while waiting: " + e.getMessage());
                System.exit(7);
            }
        }
    }

    // Send: Used to send current game info to server
    public void send(MorraInfo data) {

        try {
            serverMsg.accept("Sending info to server...");
            out.writeObject(data);
            out.reset();
        } catch (IOException e) {
            System.out.println("Failed to send data: " + e.getMessage());
            System.exit(8);
        }
    }
}