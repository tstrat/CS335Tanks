package gui;

import gameModel.Actor;
import gameModel.Command;
import gameModel.CommandReceiver;
import gameModel.DrawObject;
import gameModel.FireCommand;
import gameModel.GameHandler;
import gameModel.HealingBeacon;
import gameModel.HeavyTank;
import gameModel.HoverTank;
import gameModel.Indestructible;
import gameModel.LayMineCommand;
import gameModel.MoveCommand;
import gameModel.MudPatch;
import gameModel.MultiplayerBroadcaster;
import gameModel.RotateCommand;
import gameModel.RotateGunCommand;
import gameModel.RotateGunCommand2;
import gameModel.SoundPlayer;
import gameModel.SpeedPatch;
import gameModel.SpikePit;
import gameModel.SpinningAI;
import gameModel.StandardTank;
import gameModel.StupidAI;
import gameModel.SyncCommand;
import gameModel.TNTBarrel;
import gameModel.TreeStump;
import gameModel.Tank;
import gameModel.Wall;
import gameModel.World;
import gameModel.WorldCreator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import server.TanksClient;
import server.TanksServer;

public class TanksDisplay extends JPanel implements Observer {

	private World world;
	private GameHandler handler;
	int player;
	
	private TanksKeyboardListener keyListener;
	private TanksMouseListener mouseListener;
	
	private Image img;
	
	private Image gameOverImg;
	
	/**
	 * Background music.
	 */
	private SoundPlayer soundPlayer;
	
	/**
	 * Contains common initialization for both constructors.
	 */
	private TanksDisplay() {
		super(true); // It is double buffered.
		
		setPreferredSize(new Dimension(800, 600));
		setBackground(new Color(245, 228, 156));
		
		soundPlayer = SoundPlayer.playerFromResource("elevatormusic.mp3");
		soundPlayer.loop();

		setFocusable(true);
		requestFocus();
		
		ImageIcon ii = new ImageIcon(this.getClass().getResource("map3.png"));
		img = ii.getImage();
	}
	
	/**
	 * Creates a World specified by the given WorldCreator, and adds one player.
	 * 
	 * @param creator A WorldCreator specifying how to create the World.
	 */
	public TanksDisplay(WorldCreator creator) {
		this();
		
		world = creator.getWorld();
		
		player = 1;
		world.setPlayer(1);
		
		handler = new GameHandler(world);
		creator.setCommandReceiver(handler);
		creator.addAIActors(world);
		
		world.addObserver(this);
		
		addKeyListener(keyListener = new TanksKeyboardListener(handler, player));
		addMouseListener(mouseListener = new TanksMouseListener(handler, player));
		addMouseMotionListener(mouseListener);
	}

	/**
	 * Creates a display for a World synchronized over the network.
	 * If you are hosting, this constructor assumes that host will
	 * be the string "localhost". Please do not capitalize it, or use
	 * 127.0.0.1 or whatever.
	 */
	public TanksDisplay(String host, WorldCreator creator) {
		this();
				
		// Try to connect to host
		TanksClient client = new TanksClient(host);
		
		client.addFrom(creator);
		
		if (host.equals("localhost")) {
			client.addMap(creator);
			
			HostStartDialog dlg = new HostStartDialog();
			
			// Wait for the user to press start.
			while (dlg.isAlive()) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
			}
			
			client.becomeReady();
		}
				
		// Wait for a maximum of 20 seconds to receive info from the host.
		long targetTime = System.currentTimeMillis() + 20000L;
		while (!client.isReady() && System.currentTimeMillis() < targetTime) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				break;
			}
		}
		
		if (!client.isReady()) {
			return;
			// TODO: Throw an exception.
		}
		
		player = client.getPlayerNumber();
		
		// At this point, this World contains all the Tanks and AIs for everyone.
		creator = client.getWorldCreator();
		world = creator.getWorld();
		world.setPlayer(player);
		world.addObserver(this);
		
		handler = new GameHandler(world);
		MultiplayerBroadcaster receiver = new MultiplayerBroadcaster(handler, client);
		
		client.setReceiver(receiver);
		creator.setCommandReceiver(receiver);
		creator.addAIActors(world);
		
		// Start a sync timer that will synchronize my player every second or so.
		new SyncTimer(client).start();
		
		addKeyListener(keyListener = new TanksKeyboardListener(receiver, player));
		addMouseListener(mouseListener = new TanksMouseListener(receiver, player));
		addMouseMotionListener(mouseListener);
	}
	
	/**
	 * Opens a dialog box for the host that will let the server know when you are
	 * done waiting for people to connect. This is done in a separate thread,
	 * while TanksDisplay waits.
	 */
	private class HostStartDialog extends Thread {
		
		@Override
		public void run() {
			int result = JOptionPane.showOptionDialog(TanksDisplay.this,
					"Waiting for players to join...", "Waiting",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, new String[]{"Start", "Cancel"}, "Start");
			
			// Uh, we don't care if they picked start or cancel for now...
		}
		
	}
	
	/**
	 * It syncs things I guess.
	 * 
	 * @author Parker Snell
	 */
	private class SyncTimer extends Thread {
		
		private TanksClient client;
		private Tank tank;
		
		/**
		 * Creates a SyncTimer to occasionally sync everyone else with my player.
		 * 
		 * @param client A TanksClient to send the synchronization info to.
		 */
		public SyncTimer(TanksClient client) {
			this.client = client;
			
			for (Tank t : world.getTanks()) {
				if (t.getPlayerNumber() == player)
					tank = t;
			}
		}
		
		@Override
		public void run() {
			
			while (true) {
				try {
					Thread.sleep(100);
					if (tank == null)
						return;
					
					client.sendCommand(new SyncCommand(tank));
				}
				catch (InterruptedException e) {
					// Uh...
				}
			}
			
		}
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		((Graphics2D)g).drawImage(img, 0, 0, null);
		
		if (gameOverImg != null) {
			g.drawImage(gameOverImg, 0, 0, null);
		}
		else {
			for (Actor a : world.getActors()) {
				DrawObject draw = a.getDraw();
				if (draw == null)
					continue;
				draw.draw(g, a.getX(), a.getY(), a.getRotation());
			}
		}

		Toolkit.getDefaultToolkit().sync();
	}
	
	
	/**
	 * This class handles keyboard commands for the TanksDisplay. Basic commands
	 * are W/A/S/D.
	 * 
	 * @author Parker Snell
	 */
	private class TanksKeyboardListener implements KeyListener {

		private CommandReceiver receiver;
		private int player;
		
		private boolean[] keyStates;
		
		private static final int KEY_W = 0;
		private static final int KEY_A = 1;
		private static final int KEY_S = 2;
		private static final int KEY_D = 3;
		private static final int KEY_J = 4;
		private static final int KEY_K = 5;
		private static final int KEY_L = 6;
		private static final int KEY_Q = 7;
		
		// This should always be equal to the last KEY_* above + 1.
		// (this is the total number of KEY_* declarations).
		private static final int KEY_AMOUNT = 8;

		public TanksKeyboardListener(CommandReceiver receiver, int player) {
			this.receiver = receiver;
			this.player = player;
			keyStates = new boolean[KEY_AMOUNT];
		}
		
		/**
		 * This method is called at each game step. By maintaining a set of key states
		 * and handling them all here, we can allow multiple keys down at the same time
		 * and repeating actions.
		 */
		public void step() {
			// WASD move/rotate.
			if (keyStates[KEY_W])
				receiver.receiveCommand(new MoveCommand(player, /* isBackward */ false));
			
			if (keyStates[KEY_A])
				receiver.receiveCommand(new RotateCommand(player, -0.03));
			
			if (keyStates[KEY_S])
				receiver.receiveCommand(new MoveCommand(player, /* isBackward */ true));
			
			if (keyStates[KEY_D])
				receiver.receiveCommand(new RotateCommand(player, 0.03));
			
			// JKL gun.
			if (keyStates[KEY_J])
				receiver.receiveCommand(new RotateGunCommand2(player, -0.06));
			
			if (keyStates[KEY_K])
				receiver.receiveCommand(new FireCommand(player));
			
			if (keyStates[KEY_L])
				receiver.receiveCommand(new RotateGunCommand2(player, 0.06));
			
			if (keyStates[KEY_Q])
				receiver.receiveCommand(new LayMineCommand(player));
		}
		
		/**
		 * REMOVE THIS FOR PRODUCTION.
		 */
		private void changePlayer(int newPlayer) {
			player = newPlayer;
			TanksDisplay.this.mouseListener.changePlayer(newPlayer);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			
			// REMOVE FOR PRODUCTION
			// Changes which player we control.
			if (e.isControlDown()) {
				if (keyCode == KeyEvent.VK_1)
					changePlayer(1);
				else if (keyCode == KeyEvent.VK_2)
					changePlayer(2);
				else if (keyCode == KeyEvent.VK_3)
					changePlayer(3);
				else if (keyCode == KeyEvent.VK_4)
					changePlayer(4);
			}

			switch (keyCode) {
			case KeyEvent.VK_A:
				keyStates[KEY_A] = true;
				break;

			case KeyEvent.VK_D:
				keyStates[KEY_D] = true;
				break;

			case KeyEvent.VK_W:
				keyStates[KEY_W] = true;
				break;

			case KeyEvent.VK_S:
				keyStates[KEY_S] = true;
				break;
				
			case KeyEvent.VK_J:
				keyStates[KEY_J] = true;
				break;

			case KeyEvent.VK_K:
				keyStates[KEY_K] = true;
				break;

			case KeyEvent.VK_L:
				keyStates[KEY_L] = true;
				break;
				
			case KeyEvent.VK_SPACE:
				keyStates[KEY_K] = true;
				break;
				
			case KeyEvent.VK_Q:
				keyStates[KEY_Q] = true;
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int keyCode = e.getKeyCode();
						
			switch (keyCode) {
			case KeyEvent.VK_A:
				keyStates[KEY_A] = false;
				break;

			case KeyEvent.VK_D:
				keyStates[KEY_D] = false;
				break;

			case KeyEvent.VK_W:
				keyStates[KEY_W] = false;
				break;

			case KeyEvent.VK_S:
				keyStates[KEY_S] = false;
				break;
				
			case KeyEvent.VK_J:
				keyStates[KEY_J] = false;
				break;

			case KeyEvent.VK_K:
				keyStates[KEY_K] = false;
				break;

			case KeyEvent.VK_L:
				keyStates[KEY_L] = false;
				break;
				
			case KeyEvent.VK_SPACE:
				keyStates[KEY_K] = false;
				break;

			case KeyEvent.VK_Q:
				keyStates[KEY_Q] = false;
				break;

			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// This is not necessary.
		}

	}
	
	private class TanksMouseListener implements MouseInputListener {

		private CommandReceiver receiver;
		private int player;
		
		private int mouseX, mouseY;

		public TanksMouseListener(CommandReceiver receiver, int player) {
			this.receiver = receiver;
			this.player = player;
		}
		
		/**
		 * Remove for production!
		 */
		public void changePlayer(int newPlayer) {
			player = newPlayer;
		}

		public void step() {
			receiver.receiveCommand(new RotateGunCommand(player, mouseX, mouseY));
		}
		
		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			int button = e.getButton();

			Command c = null;

			switch (button) {
			case MouseEvent.BUTTON1:
				c = new FireCommand(player);
				break;
			case MouseEvent.BUTTON3:
				c = new LayMineCommand(player);
				break;
			}

			if (c != null)
				receiver.receiveCommand(c);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg != null) {
			// It's an Integer, indicating the number of the player that won.
			
			if ((Integer)arg == player)
				gameOverImg = new ImageIcon(World.class.getResource("youwin.png")).getImage();
			else
				gameOverImg = new ImageIcon(World.class.getResource("youlose.png")).getImage();
			
			repaint();
			JFrame meh = new JFrame();
			Object[] options = {"Replay", "Main Menu", "Quit"};
			int n = JOptionPane.showOptionDialog(meh, "Would you like to Replay, Main Menu or Quit?", "Replay", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			
			if(n == 0){
				//replay option
			}
			
			if(n == 1){
				JOptionPane.getFrameForComponent(this).dispose();
				//soundPlayer.stop(); //This gets a null pointer for some reason
				new BasicMenu();
			}
				
			if(n == 2)
				System.exit(0);
			
			return;			
		}
		
		if (keyListener != null)
			keyListener.step();
		
		if (mouseListener != null)
			mouseListener.step();
		
		repaint();
	}

}
