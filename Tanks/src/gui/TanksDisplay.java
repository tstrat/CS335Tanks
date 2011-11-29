package gui;

import gameModel.Actor;
import gameModel.Tank;
import gameModel.World;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class TanksDisplay extends JPanel {

	private World world;
	
	public TanksDisplay() {
		super(true); // It is double buffered.
		
		setPreferredSize(new Dimension(600, 800));
		
		world = new World();
		world.addActor(new Tank(200, 300, 0, 0));
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		for (Actor a : world.getActors()) {
			a.getDraw().draw(g, a.getX(), a.getY(), a.getRotation());
		}
	}
	
}
