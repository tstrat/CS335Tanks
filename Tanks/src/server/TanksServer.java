package server;
import gameModel.*;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.List;

public class TanksServer {
	
	private ServerSocket server;
	private List<ClientManager> clientList;
	
	public TanksServer(){
		clientList = new LinkedList<ClientManager>();
		start();
	}
	
	public void start(){
		try {
			server = new ServerSocket(4002);
			
			while (true){
				Socket sock = server.accept();
				ClientManager newClient = new ClientManager(sock);
				clientList.add(newClient);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class ClientManager{
		
		private Socket socket;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		private Thread commandThread;
		
		public ClientManager(Socket sock){
			socket = sock;
			
			try {
				oos = new ObjectOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			commandThread = new Thread(new CommandThread());
		}
		
		private class CommandThread extends Thread{
			@Override
			public void run(){
				while (true){
					try {
						Command c = (Command) ois.readObject();
						
						for (ClientManager manager : clientList)
							manager.send(c);
								
					} catch (IOException e) {
						System.out.println("A client has disconnected.");
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
		
		public void send(Command c){
			try {
				oos.writeObject(c);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
