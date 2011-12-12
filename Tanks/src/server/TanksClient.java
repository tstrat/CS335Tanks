package server;
import gameModel.*;
import gameModel.WorldCreator.AIPair;
import gameModel.WorldCreator.TankPair;

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
	private OutputStream os;
	private InputStream is;
	private DataInputStream dis;
	private DataOutputStream dos;
	private Thread receivingThread;
	
	private CommandReceiver cr;
	private String ip = "";
	
	private int player = 0;
	
	private WorldCreator worldCreator;
	
	private boolean ready;
	
	/**
	 * The constructor takes in an ip, then proceeds to start creating the client socket
	 * and connect it to the server.
	 * 
	 * @param ip - String that contains the IP address for the socket to connect to
	 */
	public TanksClient(String ip){
		this.ip = ip;
		start();
	}
	
	/**
	 * Sets a CommandReceiver to distribute messages.
	 * 
	 * @param receiver A CommandReceiver.
	 */
	public void setReceiver(CommandReceiver receiver) {
		this.cr = receiver;
	}
	
	/**
	 * The start method sets up the clients socket, connects it to the server at the given ip
	 * and sets up an output/input stream for the created socket.  Then, it proceeds to begin a
	 * new thread dedicated to receiving and processing messages from the server.
	 */
	public void start(){
		try {
			client = new Socket(ip, 4002);
			
			os = client.getOutputStream();
			is = client.getInputStream();
			dis = new DataInputStream(is);
			dos = new DataOutputStream(os);
			
		} catch (IOException e) {
			return;
		}
		
		receivingThread = new ReceiveThread();
		receivingThread.start();
	}
	
	/**
	 * @return True if a World can be obtained by calling getWorld, false if that method
	 * will return null.
	 */
	public boolean isReady() {
		return ready;
	}
		
	/**
	 * Gets the WorldCreator.
	 */
	public WorldCreator getWorldCreator() {
		return worldCreator;
	}
	
	/**
	 * Gets the current player number for this client. A value of zero is invalid,
	 * and means that we haven't been assigned a player number yet by the server.
	 * 
	 * @return A positive integer, or zero if a number has not yet been assigned.
	 */
	public int getPlayerNumber() {
		return player;
	}
	
	/**
	 * This thread class is created when the client socket is made.  While it is running,
	 * the input socket waits for server data to be sent to it, then sends it to that player's
	 * GameHandler to be used.
	 *
	 */
	private class ReceiveThread extends Thread{
		
		public void run() {
			
			while (client.isConnected()) {
				try {
					
					int header = dis.readInt();
					int type = header >> 24;
					int size = header & 0xFFFFFF;
					byte[] data = new byte[size];
					int read = dis.read(data, 0, size);
										
					receiveBytes(type, data);
					
				} catch (IOException e) {
					try {
						client.close();
						return;
					}
					catch (IOException e1) {
						return;
					}
				}
			}
		}
		
		/**
		 * Called when a full array of bytes is received. It is then dealt with according
		 * to the type specified in the header.
		 * 
		 * @param type The type of data received.
		 * @param data The content of the data received.
		 */
		private void receiveBytes(int type, byte[] data) {
			switch (type) {
			case TanksServer.RECV_PLAYERNO:
				player = data[0];
				break;
				
			case TanksServer.RECV_SEED:
				try {
					TRand.seed(new DataInputStream(new ByteArrayInputStream(data)).readDouble());
				} catch (IOException e) {
					// fuck this shit
				}
				break;
				
			case TanksServer.RECV_MAP:
				try {
					worldCreator =
							(WorldCreator)new ObjectInputStream(new ByteArrayInputStream(data))
							.readObject();
				} catch (ClassNotFoundException e1) {
				} catch (IOException e1) {
				}
				
				break;
				
			case TanksServer.RECV_TANK:
				if (worldCreator == null)
					break;
				
				// Add a tank to the WorldCreator.
				// It will then be added to the World after we get a RECV_READY message.
				try {
					worldCreator.addTank(
							(TankPair)new ObjectInputStream(
									new ByteArrayInputStream(data)).readObject());
				} catch (ClassNotFoundException e1) {
				} catch (IOException e1) {
				}
				break;
				
			case TanksServer.RECV_AI:
				if (worldCreator == null)
					break;
				
				// Add AI's to the map. They each get their own unique player number
				// just like a normal player. It is important though that their player numbers
				// are higher than 1-4, which are reserved for regular players.
				try {
					worldCreator.addAI(
							(AIPair)new ObjectInputStream(
									new ByteArrayInputStream(data)).readObject());
				} catch (ClassNotFoundException e1) {
				} catch (IOException e1) {
				}
				break;
				
			case TanksServer.RECV_READY:
				ready = true;
				break;
				
			case TanksServer.RECV_COMMAND:
				try {
					Command c = (Command)new ObjectInputStream(new ByteArrayInputStream(data))
								.readObject();
					
					// Commands for my player are ignored, since they
					// are processed by GameHandler.
					if (c.getPlayer() != player && cr != null)
						cr.receiveCommand(c);
				}
				catch (IOException e) {
				}
				catch (ClassNotFoundException e) {
				}
				break;
			}
		}
		
	}// End of Thread class
	
	/**
	 * Sends a packet of data with a header indicating size and type of content.
	 * 
	 * @param header The header: (type << 24) | size.
	 * @param data The data to be sent.
	 */
	private synchronized void send(int header, byte[] data) {
		if (client == null || client.isClosed())
			return;
		
		try {
			dos.writeInt(header);
			dos.write(data);
			dos.flush();
		}
		catch (IOException e) {
		}
	}
	
	/**
	 * This method sends any commands from this player from its client to the server
	 * for the server to send to everyone else.
	 * 
	 * @param c - Command
	 */
	public synchronized void sendCommand(Command c){
		try {
			ByteArrayOutputStream bytesout = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bytesout);
			out.writeObject(c);
			byte[] data = bytesout.toByteArray();
			
			send((TanksServer.RECV_COMMAND << 24) | data.length, data);
		} catch (IOException e) {
		}
	}

	public void addFrom(WorldCreator creator) {
		//...
	}
}
