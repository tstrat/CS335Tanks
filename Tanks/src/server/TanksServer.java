package server;
import gameModel.TRand;
import gameModel.WorldCreator.AIPair;
import gameModel.WorldCreator.TankPair;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
	
	private double seed;
	
	private List<TankPair> tanksList;
	private List<AIPair> aiList;
	
	private byte[] mapData;
	
	/**
	 * Control constants for messages sent between client and server.
	 */
	public static final int RECV_PLAYERNO = 1;
	public static final int RECV_COMMAND = 2;
	public static final int RECV_SEED = 3;
	public static final int RECV_MAP = 4;
	public static final int RECV_TANK = 5;
	public static final int RECV_AI = 6;
	public static final int RECV_READY = 7;
	
	/**
	 * The constructor creates an empty linkedList of clients.  Then, it starts the server.
	 */
	public TanksServer(){
		playerNum = 1;
		doneConnecting = false;
		clientList = new LinkedList<ClientManager>();
		
		tanksList = new ArrayList<TankPair>();
		aiList = new ArrayList<AIPair>();
		
		seed = TRand.random();
		
		// Start the listener in a new thread.
		new Thread() {
			public void run() {
				TanksServer.this.start();
			}
		}.start();
	}
	
	/**
	 * First, the server starts, running on port 4002.  Next, it iterates through a loop,
	 * adding clients as they connect to the server.  As the client connects, its socket
	 * gets added to the clientList.  The loop is controlled by a boolean which is set by
	 * the client hosting when they feel like there are enough players in the game.
	 * 
	 * Note - boolean doneConnecting is set by another method and called from outside the server class.
	 */
	private void start(){
		try {
			server = new ServerSocket(4002);
			
			while (!doneConnecting){
				Socket sock = server.accept();
				ClientManager newClient = new ClientManager(sock, playerNum);
				clientList.add(newClient);
				playerNum++; // increments the player number
			}
		} catch (IOException e) {
		}
	}
	
	/**
	 * Calling this method sets the doneConnecting boolean to true which terminates the while loop in
	 * start() which controls adding more players to the clientList.
	 */
	private void doneConnecting(){
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
		}
	}
	
	/**
	 * @return the number of clients that are currently connected.
	 */
	public int getClients(){
		return clientList.size();
	}
	
	/**
	 * Called when a client sends the ready command. Stops accepting connections,
	 * and sends all info out to everyone currently connected.
	 */
	private void ready() {
		doneConnecting();
		
		int numClients = getClients();
		
		// The number of AIs to add, assuming it is >= 0.
		int ai = 4 - numClients;
		if (aiList.size() < ai)
			ai = aiList.size();
		
		for (ClientManager manager : clientList) {
			manager.send(RECV_MAP, mapData);
			
			for (TankPair tp : tanksList) {
				manager.send(RECV_TANK, tp);
			}
			
			for (int i = 0; i < ai; ++i) {
				AIPair aipair = aiList.get(i);
				
				// Assign the AI a player number higher than any client.
				aipair.player = numClients + i + 1;
				
				manager.send(RECV_AI, aipair);
			}
		}
		
		for (ClientManager manager : clientList) {
			manager.becomeReady();
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
		private InputStream is;
		private DataInputStream dis;
		private OutputStream os;
		private DataOutputStream dos;
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
				os = socket.getOutputStream();
				is = socket.getInputStream();
				dis = new DataInputStream(is);
				dos = new DataOutputStream(os);
				
				send(RECV_PLAYERNO, teamNum);
				send(RECV_SEED, seed);
			} catch (IOException e) {
				return;
			}
			
			commandThread = new Thread(new CommandThread());
			commandThread.start();
		}
		
		/**
		 * Lets the client know that we are ready to start the game.
		 */
		public void becomeReady() {
			try {
				dos.writeInt(RECV_READY << 24);
			} catch (IOException e) {
			}
		}

		/**
		 * The CommandTread is a thread that waits for input from a client, then, upon getting the command
		 * data, sends the command to the other clients in the clientList.
		 *
		 */
		private class CommandThread extends Thread{
			
			@Override
			public void run(){
				
				while (socket.isConnected()){
					try {
						
						int header = dis.readInt();
						int type = header >> 24;
						int size = header & 0xFFFFFF;
						byte[] data = new byte[size];
						int read = dis.read(data, 0, size);
						
						while (read < size) {
							read += dis.read(data, read, size - read);
						}
												
						receiveBytes(type, data);
						
					} catch (IOException e) {
						
						try {
							ClientManager.this.socket.close();
							return;
						} catch (IOException e1) {
							return;
						}
					}
				}
				
			}
			
			/**
			 * This is called when we have received a full array of bytes matching
			 * the size specified in the header. The bytes are then sent out to
			 * each connected client.
			 * 
			 * @param type The type of data received.
			 * @param bytes The array of bytes that make up the data.
			 */
			private void receiveBytes(int type, byte[] bytes) {
				switch (type) {
				case RECV_TANK:
					try {
						TankPair tankPair =
								(TankPair)new ObjectInputStream(
										new ByteArrayInputStream(bytes)).readObject();
						// Set this tank's player number to the client's number.
						tankPair.player = "P" + team;
						tanksList.add(tankPair);
					} catch (ClassNotFoundException e1) {
					} catch (IOException e1) {
					}
					break;
					
				case RECV_AI:
					try {
						aiList.add(
								(AIPair)new ObjectInputStream(
										new ByteArrayInputStream(bytes)).readObject());
					} catch (ClassNotFoundException e1) {
					} catch (IOException e1) {
					}
					break;
					
				case RECV_MAP:
					mapData = bytes;
					break;
					
				case RECV_READY:
					ready();
					break;
					
				default:
					for (ClientManager manager : clientList)
						manager.send(type, bytes);
					break;
				}
			}
			
		}
		
		/**
		 * Sends a serializable object to the client.
		 * 
		 * @param type The type of command to use (RECV_*).
		 * @param o Object that is sent to a client.
		 */
		public void send(int type, Serializable o) {
			try {
				ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
				ObjectOutputStream objectstream = new ObjectOutputStream(bytestream);
				objectstream.writeObject(o);
				
				byte[] bytes = bytestream.toByteArray();
				
				send(type, bytes);
			} catch (IOException e) {
			}
		}
		
		/**
		 * Sends a message to a client, by attaching a header onto the content
		 * that you want to send.
		 * 
		 * @param type The type of command you are sending.
		 * @param bytes The raw bytes that make up the message to be sent.
		 */
		public void send(int type, byte[] bytes) {
			if (socket.isClosed())
				return;
			
			try {
				dos.writeInt((type << 24) | bytes.length);
				dos.write(bytes);
				dos.flush();
			} catch (IOException e) {
			}
		}
		
		/**
		 * Sends a byte to the client. Intended to be used mainly for
		 * sending the player number. Note that even though the parameter's type
		 * is int, this method writes a byte, so values over 255 are not valid.
		 * 
		 * @param type The type of command you are sending.
		 * @param b A byte to be sent to the client.
		 */
		public void send(int type, int b) {
			try {
				dos.writeInt((type << 24) | 1);
				dos.write(b);
			} catch (IOException e) {
			}
		}
		
		public void send(int type, double d) {
			try {
				dos.writeInt((type << 24) | 8);
				dos.writeDouble(d);
			}
			catch (IOException e) {
			}
		}
		
		/**
		 * Closes the ObjectOutput and ObjectInput streams  and then closes the client's socket.
		 * This gets called for all instances of the ClientManager, and will effectively close down all clients before the server
		 * is then closed down.
		 */
		public void closeClientManager(){
			try{
				is.close();
				os.close();
				socket.close();
			}catch (IOException ioe){
			}
		}
	}
}
