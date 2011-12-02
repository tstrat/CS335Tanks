package gui;

import gameModel.Actor;
import gameModel.Command;
import gameModel.CommandReceiver;
import gameModel.FireCommand;
import gameModel.GameHandler;
import gameModel.MoveCommand;
import gameModel.RotateCommand;
import gameModel.RotateGunCommand;
import gameModel.Tank;
import gameModel.World;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class TanksDisplay extends JPanel implements Observer {

	private World world;
	private GameHandler handler;

	/**
	 * The default constructor creates a World and GameHandler and adds a Tank
	 * to the World.
	 */
	public TanksDisplay() {
		super(true); // It is double buffered.

		setPreferredSize(new Dimension(800, 600));

		world = new World();
		Tank t = new Tank(world, 200, 300, 2, 2);
		t.fire();

		handler = new GameHandler(world);
		world.addObserver(this);

		setFocusable(true);
		requestFocus();
		addKeyListener(new TanksKeyboardListener(handler, 2));
		addMouseListener(new TanksMouseListener(handler, 2));
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

		public TanksKeyboardListener(CommandReceiver receiver, int player) {
			this.receiver = receiver;
			this.player = player;
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();

			Command c = null;

			// TODO: This shouldn't be hard wired to player 2.
			switch (keyCode) {
			case KeyEvent.VK_A:
				c = new RotateCommand(player, -.05);
				break;

			case KeyEvent.VK_D:
				c = new RotateCommand(player, .05);
				break;

			case KeyEvent.VK_W:
				c = new MoveCommand(player, 5, 0);
				break;

			case KeyEvent.VK_S:
				c = new MoveCommand(player, -5, 0);
				break;

			}

			if (c != null)
				receiver.receiveCommand(c);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// We don't need to do anything here.
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// This is also unnecessary.
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
		repaint();
	}

}
