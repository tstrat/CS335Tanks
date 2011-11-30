package gui;

import gameModel.Actor;
import gameModel.Tank;
import gameModel.World;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class TanksDisplay extends JPanel {

	private World world;
	
	public TanksDisplay() {
		super(true); // It is double buffered.
		
		setPreferredSize(new Dimension(800, 600));
		
		world = new World();
		world.addActor(new Tank(200, 300, 2, 2));
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		for (Actor a : world.getActors()) {
			a.getDraw().draw(g, a.getX(), a.getY(), a.getRotation(), this);
		}
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
	
}
