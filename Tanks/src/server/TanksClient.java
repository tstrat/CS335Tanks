package server;
import gameModel.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;
public class TanksClient {
	
	private Socket client;
	private ObjectOutputStream os;
	private ObjectInputStream is;
	private Thread receivingThread;
	
	private String name;
	
	public TanksClient(String name){
		this.name = name;
		start();
	}
	
	public void start(){
		try {
			client = new Socket("localhost", 4002);
			
			os = new ObjectOutputStream(client.getOutputStream());
			is = new ObjectInputStream(client.getInputStream());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		receivingThread = new Thread(new ReceiveThread());
		receivingThread.start();
	}
	
	
	private class ReceiveThread extends Thread{
		
		public void run() {
			while (true) {
				try {
					Command c = (Command) is.readObject();
					
				} catch (IOException e) {
					JOptionPane
							.showMessageDialog(null,
									"The connection to the server has been lost. The program will now close.");
					System.exit(0);
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void sendCommand(Command c){
		try {
			os.writeObject(c);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
