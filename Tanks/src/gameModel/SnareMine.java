package gameModel;

public class SnareMine extends Mine {

	public SnareMine(World w, double x, double y, double rotation, Tank t) {
		super(w, x, y, rotation, t);
	}

	@Override
	public void activateMine() {
		new Snare(w, x, y, rotation);
		exists = false;

	}
	
	@Override
	public void collide(Collidable c) {
		if(c instanceof Tank && !c.equals(t)) {
			x = c.getX();
			y = c.getY();
			activateMine();
		}
	}
	
	private class Snare extends Obstacle {

		private int ticks;
		private int drawPriority = 50;

		public Snare(World w, double x, double y, double rotation) {
			super(w, x, y, rotation);
			ticks = 0;
			setDraw();
			maxHealth = 25;
			health = 25;
		}
		
		@Override
		public void receiveDamage(int d) {			
		}

		@Override
		public void collide(Collidable c) {
			if(c instanceof Tank && !c.equals(t)) {
				((Tank) c).receiveDamage(3);
				t.receiveDamage(-3);
			}
		}
		
		/**
		 * The DrawObject that defines how the GUI draws the Tank.
		 */
		private DrawObject draw;
		
		private DrawObject draw1 = new DrawSingleFrameObject("snare1.png");
		private DrawObject draw2 = new DrawSingleFrameObject("snare2.png");
		private DrawObject draw3 = new DrawSingleFrameObject("snare3.png");
		private DrawObject draw4 = new DrawSingleFrameObject("snare4.png");
		private DrawObject draw5 = new DrawSingleFrameObject("snare5.png");
		
		/**
		 * Returns the DrawObject of the tank, which controls how the Tank is drawn.
		 */
		@Override
		public DrawObject getDraw() {
			return draw;
		}
		
		@Override
		public int getDrawPriority() {
			return drawPriority;
		}

		@Override
		public void act() {
			ticks++;
			if(ticks > 125)
				health = 0;
			if(ticks % 5 == 0) {
				setDraw();
			}
		}
		
		private void setDraw() {
			rotation = TRand.random() * Math.PI * 2;
			double d = TRand.random();
			if(d < .2)
				draw = draw1;
			else if (d < .4)
				draw = draw2;
			else if (d < .6)
				draw = draw3;
			else if (d < .8)
				draw = draw4;
			else
				draw = draw5;
		}
		
	}

}
