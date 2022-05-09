import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;

public class Server{

    // SERVER DATA MEMBERS
    // Count: keeps track of the number for each client
    int count = 1;
    // Port: given by user to determine which port to connect to
    int port;
    // Clients: ArrayList of Clients
    ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
    // Server: inner-class object
    TheServer server;
    // callback: used to send messages to GUI
    private Consumer<Serializable> callback;
    // syncObject: used to sync the two threads
    final Object syncObject = new Object();
    // game: variable to store the results of a game
    MorraInfo game;

    // Constructor: takes in the port and the call from the GUI
    Server(int port, Consumer<Serializable> call){
        this.port = port;
        callback = call;
        server = new TheServer();
        server.start();
    }

    public class TheServer extends Thread{

        public void run() {

            // Use the given port number to create a socket
            try(ServerSocket mysocket = new ServerSocket(port);){
                System.out.println("Server is waiting for a client!");

                // Infinite loop to wait for new clients
                while(true) {
                    ClientThread c = new ClientThread(mysocket.accept(), count);
                    callback.accept("client has connected to server: " + "client #" + count);
                    clients.add(c);
                    c.start();

                    count++;

                }
            }//end of try
            catch(Exception e) {
                callback.accept("Server socket did not launch");
            }
        }//end of while
    }


    class ClientThread extends Thread{

        // CLIENT THREAD DATA MEMBERS
        // connection: stores the socket for the client
        Socket connection;
        // count: unique number for client
        int count;
        // in/out: input and output streams for this client
        ObjectInputStream in;
        ObjectOutputStream out;
        // gameInfo: keeps track of the choices for this particular client
        MorraInfo gameInfo = new MorraInfo();
        // isPlayerOne: keeps track of the player's number
        boolean isPlayerOne = false;

        ClientThread(Socket s, int count){
            this.connection = s;
            this.count = count;
        }

        // Update Clients: sends data to all clients
        public void updateClients(MorraInfo data) {
            for(int i = 0; i < clients.size(); i++) {
                ClientThread t = clients.get(i);
                try {
                    data.isPlayerOne = t.isPlayerOne;
                    t.out.writeObject(data);
                    out.reset();
                }
                catch(Exception e) {
                    callback.accept("Error during client update: client #" + count);
                    clients.clear();
                }
            }
        }

        // Start Game: Determines if client is player one or two and updates them
        public void startGame() {
            // 1 client -> Player One, has to wait for someone else
            if(clients.size() == 1) {
                isPlayerOne = true;
                gameInfo.isPlayerOne = true;
                gameInfo.hasTwoPlayers = false;
                updateClients(gameInfo);
            }
            // 2 client -> Player two, game can begin!
            else if(clients.size() == 2) {
                gameInfo.hasTwoPlayers = true;
                updateClients(gameInfo);
            }
            // 3 or more clients -> cannot play game, display message
            else {
                clients.remove(this);
                gameInfo.p1Points = 1000;
                gameInfo.p2Points = 1000;
                try {
                    out.writeObject(gameInfo);
                }
                catch(Exception e) {
                    callback.accept("Error during client update: client #" + count);
                }
            }

            playGame();
        }

        // Play Game: Receives the choices from each client
        public void playGame() {
            try {
                // First, get the data from the client
                gameInfo = null;
                do {
                    gameInfo = (MorraInfo) in.readObject();
                } while (gameInfo == null);

                // Now wait until both clients have submitted
                while(!checkNull()) {
                    checkNull();
                }

                // Now compute the results of the game
                // Results need to be computed only once, so playerOne thread computes and the other waits
                if(isPlayerOne) {
                    computeWinner();
                }
                else {
                    synchronized (syncObject) {
                        syncObject.wait();
                    }
                }

                // If game hasn't finished yet, call this function again for the next round
                if(game.p1Points < 2 && game.p2Points < 2) {
                    setNull();
                    playGame();
                } // Otherwise, call playAgain
                else {
                    playAgain();
                }
            }
            catch(Exception e) {
                callback.accept("Error during client info retrieval, client #" + count);
                clients.clear();
            }
        }

        // Set Null: Sets the gameInfo for each client to null
        public void setNull() {
            for(int i = 0; i < 2; i++) {
                ClientThread t = clients.get(i);
                if(t.gameInfo != null) {
                    t.gameInfo = null;
                }
            }
        }

        // Check Null: checks if the clients' gameInfo is null or not
        public boolean checkNull() {
            for(int i = 0; i < clients.size(); i++) {
                ClientThread t = clients.get(i);
                if(t.gameInfo == null) {
                    return false;
                }
            }
            return true;
        }

        // Compute Winner: Gets the results of the game and updates the clients
        public void computeWinner() {
            try {
                // Create a new MorraInfo for the data from both games
                MorraInfo wholeGame;
                wholeGame = clients.get(0).gameInfo;
                MorraInfo secondGame = clients.get(1).gameInfo;
                if (secondGame.isPlayerOne) {
                    wholeGame.p1Num = secondGame.p1Num;
                    wholeGame.p1Guess = secondGame.p1Guess;
                } else {
                    wholeGame.p2Num = secondGame.p2Num;
                    wholeGame.p2Guess = secondGame.p2Guess;
                }

                // The correct total is Player 1's num + Player 2's num
                int correctTotal = wholeGame.p1Num + wholeGame.p2Num;

                // Update server gui with choices
                callback.accept("RESULTS FOR THIS GAME:");
                callback.accept("Player 1's Number: " + wholeGame.p1Num);
                callback.accept("Player 2's Number: " + wholeGame.p2Num);
                callback.accept("--------------------------------------");
                callback.accept("Player 1's Guess: " + wholeGame.p1Guess);
                callback.accept("Player 2's Guess: " + wholeGame.p2Guess);
                callback.accept("--------------------------------------");
                callback.accept("Player 1's Points: " + wholeGame.p1Points);
                callback.accept("Player 2's Points: " + wholeGame.p2Points);
                callback.accept("--------------------------------------");


                // Now find a winner, if any, and update server gui
                if (wholeGame.p1Guess == correctTotal && wholeGame.p2Guess != correctTotal) {
                    wholeGame.p1Points++;
                    callback.accept("Player 1 wins!");
                    callback.accept("Player 1 now has " + wholeGame.p1Points + " point(s)");
                    callback.accept("--------------------------------------");
                } else if (wholeGame.p1Guess != correctTotal && wholeGame.p2Guess == correctTotal) {
                    wholeGame.p2Points++;
                    callback.accept("Player 2 wins!");
                    callback.accept("Player 2 now has " + wholeGame.p2Points + " point(s)");
                    callback.accept("--------------------------------------");
                } else {
                    callback.accept("No winners in this game...");
                    callback.accept("--------------------------------------");
                }

                // Send results to clients
                updateClients(wholeGame);
                game = wholeGame;

                // Let the other thread continue
                synchronized (syncObject) {
                    syncObject.notify();
                }

//                System.out.println("Whole game Info:");
//                System.out.println(wholeGame.p1Num);
//                System.out.println(wholeGame.p2Num);
//                System.out.println(wholeGame.p1Guess);
//                System.out.println(wholeGame.p2Guess);
//                System.out.println(wholeGame.p1Points);
//                System.out.println(wholeGame.p2Points);
//                System.out.println(wholeGame.isPlayerOne);
            }
            catch (Exception e) {
                callback.accept("Error during winner computation: client #" + count);
                clients.clear();
            }
        }

        // Play Again: Called after a game is finished
        public void playAgain() {
            // Remove the current clients from the list and see if they'll play again
            clients.remove(this);
            isPlayerOne = false;

            try {
                // If info is received from client, they'll play again!
                gameInfo = (MorraInfo) in.readObject();
                callback.accept("Client #" + count + " is playing again!");
                gameInfo = new MorraInfo();
                // Add them back into the list and call startGame
                clients.add(this);
                startGame();
            } catch(Exception e) {
                // Otherwise, they are leaving the server
                callback.accept("Leaving server: #" + count);
            }
        }

        // Run: The starting point for each client
        public void run(){

            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
            }
            catch(Exception e) {
                System.out.println("Streams not open");
            }

            try {
                // Call startGame
                startGame();
            }
            catch(Exception e) {
                callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
                clients.remove(this);
            }
        }//end of run


    }//end of client thread

    // Version of computeWinner used for testing purposes
    public static MorraInfo calcWinner(MorraInfo p1, MorraInfo p2) {
        MorraInfo wholeGame = p1;
        if (p2.isPlayerOne) {
            wholeGame.p1Num = p2.p1Num;
            wholeGame.p1Guess = p2.p1Guess;
        } else {
            wholeGame.p2Num = p2.p2Num;
            wholeGame.p2Guess = p2.p2Guess;
        }

        int correctTotal = wholeGame.p1Num + wholeGame.p2Num;

        // Now find a winner
        if (wholeGame.p1Guess == correctTotal && wholeGame.p2Guess != correctTotal) {
            wholeGame.p1Points++;
        } else if (wholeGame.p1Guess != correctTotal && wholeGame.p2Guess == correctTotal) {
            wholeGame.p2Points++;
        }

        return wholeGame;
    }
}