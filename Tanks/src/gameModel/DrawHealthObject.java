package gameModel;

import java.awt.Color;
import java.awt.Graphics;

public class DrawHealthObject implements DrawObject{
	
	int health;
	int max;
	
	public DrawHealthObject(int health, int max) {
		this.health = health;
		this.max = max;

	}

	@Override
	public void draw(Graphics g, double x1, double y1, double rotation) {
		int x = (int) x1;
		int y = (int) y1;
		g.drawRect(x - 11, y, 32, 6);
		Color old = g.getColor();
		g.setColor(Color.GREEN);
		int hWid = (int)((health * 30) / max);
		g.fillRect(x - 10, y + 1, hWid, 4);
		g.setColor(Color.RED);
		g.fillRect((int)x - 9 + hWid, (int)y, 30 - hWid, 4);
		g.setColor(old);
	}
	
	public void updateHealth(int health, int max) {
		this.health = health;
		this.max = max;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

}
