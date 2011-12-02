package gui;

import gameModel.Actor;
import gameModel.Command;
import gameModel.CommandReceiver;
import gameModel.GameHandler;
import gameModel.MoveCommand;
import gameModel.RotateCommand;
import gameModel.Missile;
import gameModel.Tank;
import gameModel.World;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class TanksDisplay extends JPanel implements Observer {

	private World world;
	private GameHandler handler;

	/**
	 * The default constructor creates a World and GameHandler
	 * and adds a Tank to the World.
	 */
	public TanksDisplay() {
		super(true); // It is double buffered.

		setPreferredSize(new Dimension(800, 600));

		world = new World();
		handler = new GameHandler(world);
		world.addActor(new Tank(200, 300, 2, 2));
		world.addActor(new Missile(200, 300, 2, 50, 2));

		setFocusable(true);
		requestFocus();
		addKeyListener(new TanksKeyboardListener(handler, 2));
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
	 * This class handles keyboard commands for the TanksDisplay.
	 * Basic commands are W/A/S/D.
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

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

}
