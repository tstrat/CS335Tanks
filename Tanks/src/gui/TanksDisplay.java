package gui;

import gameModel.Actor;
import gameModel.Command;
import gameModel.CommandReceiver;
import gameModel.FireCommand;
import gameModel.GameHandler;
import gameModel.HeavyTank;
import gameModel.MoveCommand;
import gameModel.RotateCommand;
import gameModel.RotateGunCommand;
import gameModel.RotateGunCommand2;
import gameModel.StandardTank;
import gameModel.Tank;
import gameModel.World;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class TanksDisplay extends JPanel implements Observer {

	private World world;
	private GameHandler handler;
	
	private TanksKeyboardListener keyListener;

	/**
	 * The default constructor creates a World and GameHandler and adds a Tank
	 * to the World.
	 */
	public TanksDisplay() {
		super(true); // It is double buffered.

		setPreferredSize(new Dimension(800, 600));
		setBackground(new Color(245, 228, 156));

		world = new World();
		new HeavyTank(world, 200, 300, 2, 2);
		new StandardTank(world, 500, 400, 2, 3);
		handler = new GameHandler(world);
		world.addObserver(this);

		setFocusable(true);
		requestFocus();
		addKeyListener(keyListener = new TanksKeyboardListener(handler, 2));
		//addMouseListener(new TanksMouseListener(handler, 2));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (Actor a : world.getActors()) {
			a.getDraw().draw(g, a.getX(), a.getY(), a.getRotation());
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
		
		// This should always be equal to the last KEY_* above + 1.
		// (this is the total number of KEY_* declarations).
		private static final int KEY_AMOUNT = 7;

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
		}
		
		/**
		 * REMOVE THIS FOR PRODUCTION.
		 */
		private void changePlayer(int newPlayer) {
			player = newPlayer;
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

		public TanksMouseListener(CommandReceiver receiver, int player) {
			this.receiver = receiver;
			this.player = player;
		}
		
		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			System.out.println("mouse moved");
			receiver.receiveCommand(new RotateGunCommand(player, e.getX(), e.getY()));
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			int button = e.getButton();

			Command c = null;

			// TODO: This shouldn't be hard wired to player 2.
			switch (button) {
			case MouseEvent.BUTTON1:
				c = new FireCommand(player);
				break;
			case MouseEvent.BUTTON3:
				c = new RotateGunCommand(player, e.getX(), e.getY());
			}

			if (c != null)
				receiver.receiveCommand(c);
			
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
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	


	@Override
	public void update(Observable o, Object arg) {
		keyListener.step();
		repaint();
	}

}
