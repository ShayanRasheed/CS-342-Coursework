import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;



public class Client extends Thread{

	// CLIENT DATA MEMBERS:
	Socket socketClient;
	// Input/Output streams
	ObjectOutputStream out;
	ObjectInputStream in;
	// Id for this client
	int count;

	// Consumers to update GUI
	private Consumer<Serializable> callback;
	private Consumer<Serializable> clientCall;
	// ChatInfo to send to server
	ChatInfo info = new ChatInfo();
	
	Client(Consumer<Serializable> call, Consumer<Serializable> call2){
		callback = call;
		clientCall = call2;
	}
	
	public void run() {
		
		try {
			socketClient= new Socket("127.0.0.1",5555);
			out = new ObjectOutputStream(socketClient.getOutputStream());
			in = new ObjectInputStream(socketClient.getInputStream());
			socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {}

		// Get info from server to determine id
		try {
			info = (ChatInfo) in.readObject();
			callback.accept(info.message);
			count = info.allClients.get(info.allClients.size()-1);
			info.allClients.remove((Integer) count);
			clientCall.accept(info);
		}
		catch(Exception e) {
			System.out.println("Error entering server");
		}
		
		while(true) {
			 // Wait until server sends messages
			try {
				info = (ChatInfo) in.readObject();
				// Update GUI with the message
				callback.accept(info.message);
				// If a new client joined or if a client left, GUI must be updated
				if(info.isClientUpdate) {
					info.allClients.remove((Integer) count);
					clientCall.accept(info);
				}
			}
			catch(Exception e) {
				System.out.println("Error occurred");
			}
		}
	
    }

	// Send: Receives the selections of the client and sends them to the server
	public void send(String data, ArrayList<Integer> selected) {
		
		try {
			info.message = data;
			info.selected = selected;
			out.writeObject(info);
			out.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
