package server;
import gameModel.*;
import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;


/**
 * This class is the client side of the network.  When called it creates a client socket at the specified
 * ip (passed into the constructor) which will connect to a server.  It also creates a thread
 * which handles data received from the server.
 *
 */
public class TanksClient {
	
	private Socket client;
	private ObjectOutputStream os;
	private ObjectInputStream is;
	private Thread receivingThread;
	
	private CommandReceiver cr;
	private String ip = "";
	
	/**
	 * The constructor takes in an ip and a CommandReceiver, then proceeds to start creating the client socket
	 * and connect it to the server.
	 * 
	 * @param gameHandler - GameHandler class that the current player has
	 * @param ip - String that contains the IP address for the socket to connect to
	 */
	public TanksClient(CommandReceiver receiver, String ip){
		this.cr = receiver;
		this.ip = ip;
		start();
	}
	
	/**
	 * The start method sets up the clients socket, connects it to the server at the given ip
	 * and sets up an output/input stream for the created socket.  Then, it proceeds to begin a
	 * new thread dedicated to receiving and processing messages from the server.
	 */
	public void start(){
		try {
			client = new Socket(ip, 4002);
			
			os = new ObjectOutputStream(client.getOutputStream());
			is = new ObjectInputStream(client.getInputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		receivingThread = new Thread(new ReceiveThread());
		receivingThread.start();
	}
	
	
	/**
	 * This thread class is created when the client socket is made.  While it is running,
	 * the input socket waits for server data to be sent to it, then sends it to that player's
	 * GameHandler to be used.
	 *
	 */
	private class ReceiveThread extends Thread{
		
		public void run() {
			
			while (true) {
				try {
					
					Command c = (Command) is.readObject();
					
					cr.receiveCommand(c);
					
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		
	}// End of Thread class
	
	/**
	 * This method sends any commands from this player from its client to the server
	 * for the server to send to everyone else.
	 * 
	 * @param c - Command
	 */
	public void sendCommand(Command c){
		try {
			os.writeObject(c);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
