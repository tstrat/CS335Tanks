package gui;

import gameModel.Actor;
import gameModel.GameHandler;
import gameModel.Missile;
import gameModel.Tank;
import gameModel.World;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class TanksDisplay extends JPanel implements Observer {

	private World world;

	public TanksDisplay() {
		super(true); // It is double buffered.
		setPreferredSize(new Dimension(800, 600));

		world = new World();
		Tank t = new Tank(200, 300, 2, 2);
		world.addActor(t);
		t.fire(world);
		
		new GameHandler(world);
		world.addObserver(this);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (Actor a : world.getActors()) {
			a.getDraw().draw(g, a.getX(), a.getY(), a.getRotation());
		}
		Toolkit.getDefaultToolkit().sync();
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

}
