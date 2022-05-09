import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;

public class Server{

	// SERVER DATA MEMBERS:
	// Count: unique identifier for each client
	int count = 1;
	// Clients: arraylist that holds all client threads
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	// Counts: arrayList that holds all ids of current clients
	ArrayList<Integer> counts = new ArrayList<>();
	TheServer server;
	// Callback: Updates the listview for the server
	private Consumer<Serializable> callback;
	
	// Server Constructor
	Server(Consumer<Serializable> call){
		callback = call;
		server = new TheServer();
		server.start();
	}
	
	
	public class TheServer extends Thread{
		
		public void run() {
			// Set up an executor service
			ExecutorService ex = Executors.newFixedThreadPool(5);
		
			try(ServerSocket mysocket = new ServerSocket(5555);){
		    System.out.println("Server is waiting for a client!");
			
		    while(true) {
				// Make new Client Thread whenever someone connects to port
				ClientThread c = new ClientThread(mysocket.accept(), count);
				callback.accept("client has connected to server: " + "client #" + count);
				// Update arraylists
				clients.add(c);
				counts.add(count);
				count++;

				// Start the thread
				ex.execute(c);
			    }
			}//end of try
				catch(Exception e) {
					callback.accept("Server socket did not launch");
					ex.shutdown();
				}
			}//end of while
		}
	

		class ClientThread implements Runnable{

			Socket connection;
			// Count: Holds the id for this thread
			int count;
			// input/output streams
			ObjectInputStream in;
			ObjectOutputStream out;
			// ChatInfo object that is passed between client and server
			ChatInfo info;
			
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count = count;	
			}

			// UpdateClients: Sends ChatInfo to the clients specified in the selected list
			public synchronized void updateClients(ChatInfo info) {
				for(int i = 0; i < clients.size(); i++) {
					ClientThread t = clients.get(i);
					if(info.selected.contains(t.count) || t.count == count) {
						try {
							t.out.writeObject(info);
							t.out.reset();
						} catch (Exception e) {
							System.out.println("Error during update");
						}
					}
				}
			}
			
			public void run(){
					
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);	
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}

				// New Client has joined: Update all clients
				info = new ChatInfo();
				info.message = "new client on server: client #"+count;
				info.allClients = counts;
				info.selected = counts;
				info.isClientUpdate = true;
				updateClients(info);

				// Wait for messages from client
				 while(true) {
					try {
						info = (ChatInfo) in.readObject();
						// Update server
						callback.accept("client# " + count + " sent: " + info.message);
						// Update clients
						info.message = "client #"+count+" said: "+ info.message;
						info.isClientUpdate = false;
						updateClients(info);
					}
					catch(Exception e) {
						// If a client disconnects, remove their id from the lists
						callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
						clients.remove(this);
						counts.remove((Integer) this.count);
						// Now update other clients
						info.message = "Client #"+count+" has left the server!";
						info.allClients = counts;
						info.selected = counts;
						info.isClientUpdate = true;

						updateClients(info);
						break;
					}
				 }
			}//end of run
			
		}//end of client thread
}


	
	

	
