package server;
import gameModel.*;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents the server.  The server starts up, then waits for connections
 * from several clients.  After it connects a client, it begins a thread to handle input
 * from that client.  The thread takes in the input from the client in the form of a Command
 * class and sends it out to the other clients via their ObjectOutput streams.  
 *
 */
public class TanksServer {
	
	private ServerSocket server;
	private List<ClientManager> clientList;
	private int playerNum;
	
	private boolean doneConnecting;
	
	/**
	 * The constructor creates an empty linkedList of clients.  Then, it starts the server.
	 */
	public TanksServer(){
		playerNum = 1;
		doneConnecting = false;
		clientList = new LinkedList<ClientManager>();
		start();


	}
	
	/**
	 * First, the server starts, running on port 4002.  Next, it iterates through a loop,
	 * adding clients as they connect to the server.  As the client connects, its socket
	 * gets added to the clientList.  The loop is controlled by a boolean which is set by
	 * the client hosting when they feel like there are enough players in the game.
	 * 
	 * Note - boolean doneConnecting is set by another method and called from outside the server class.
	 */
	public void start(){
		try {
			server = new ServerSocket(4002);
			
			while (!doneConnecting){
				Socket sock = server.accept();
				ClientManager newClient = new ClientManager(sock, playerNum);
				clientList.add(newClient);
				playerNum++; // increments the player number
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Calling this method sets the doneConnecting boolean to true which terminates the while loop in
	 * start() which controls adding more players to the clientList.
	 */
	public void doneConnecting(){
		doneConnecting = true;
	}
	
	/**
	 * Closes down all the streams and sockets of each client and the server.
	 */
	public void close(){
		
		for (ClientManager client: clientList){
			client.closeClientManager();
		}
		
		
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 *	The client manager class copies the socket from the client, then creates ObjectOutput/Input
	 *  streams and then begins a thread.  Every time the client sends data to its CommandThread
	 *  the Command gets passed on to the other clients in the list.
	 *
	 */
	private class ClientManager{
		
		private Socket socket;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		private Thread commandThread;
		private int team;
		/**
		 * Connects the server to the client via output and input streams.
		 * The first thing it sends to the client is the clients number
		 * (i.e Player 1, 2, 3... etc).  The number is kept by a private int that
		 * the server maintains.  Then the manager starts a thread for the client.
		 * 
		 * @param sock This is the socket that the client and is used to create the servers
		 * 				output and input streams
		 * @param teamNum  This is the number that each client is given.  It is their player number
		 * 					and corresponds to the order in which they joined the server.  It is stored
		 * 					in ClientManager, but is also send back to the client.
		 */
		public ClientManager(Socket sock, int teamNum){
			socket = sock;
			this.team = teamNum;
			
			
			try {
				oos = new ObjectOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());
				
				oos.writeObject(team);  // sends team # back to client.
				
			
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			commandThread = new Thread(new CommandThread());
			commandThread.start();
		}
		
		
		
		/**
		 * The CommandTread is a thread that waits for input from a client, then, upon getting the command
		 * data, sends the command to the other clients in the clientList.
		 *
		 */
		private class CommandThread extends Thread{
			
			@Override
			public void run(){
				
				while (true){
					try {
						
						Command c = (Command) ois.readObject();
						
						for (ClientManager manager : clientList)
							manager.send(c);
								
					} catch (IOException e) {
						
						try {
							ClientManager.this.socket.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						break;
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				
				}
			}
		}
		
		/**
		 * Sends a command from the server to the client.  This method is called in the CommandThread
		 * after the thread receives input from one client.
		 * 
		 * @param c - Command that is sent to a client.
		 */
		public void send(Command c){
			try {
				oos.writeObject(c);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		/**
		 * Closes the ObjectOutput and ObjectInput streams  and then closes the client's socket.
		 * This gets called for all instances of the ClientManager, and will effectively close down all clients before the server
		 * is then closed down.
		 */
		public void closeClientManager(){
			try{
				oos.close();
				ois.close();
				socket.close();
			}catch (IOException ioe){
				ioe.printStackTrace();
			}
		}
	}
}
