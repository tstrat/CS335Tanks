package gameModel;


/**
 * 
 * This mine damages enemy tanks and then stops their movement for a few seconds.
 *
 */

public class SnareMine extends Mine {
	
	/**
	 * Calls the Mine Constructor
	 * @see gameModel.Mine
	 * 
	 * @param w  -  World
	 * @param x  -  X coordinate to place mine
	 * @param y  -  Y coordinate to place mine
	 * @param rotation - The orientation of the mine
	 * @param t - the Tank that laid the Mine
	 */
	
	public SnareMine(World w, double x, double y, double rotation, Tank t) {
		super(w, x, y, rotation, t);
	}
	
	/**
	 * Activates a new Snare Object.  This occurs after a tank has collided with the mine.
	 */
	
	@Override
	public void activateMine() {
		new Snare(w, x, y, rotation);
		exists = false;
	}
	
	/**
	 * Determines if the object that ran over mine is a tank.  If true, it activates the mine.
	 */
	
	@Override
	public void collide(Collidable c) {
		if(c instanceof Tank && !c.equals(t)) {
			x = c.getX();
			y = c.getY();
			activateMine();
		}
	}
	
	/**
	 * This Class is the snare part of the mine.  It traps the enemy tank, blocking its movement.
	 * Then it proceeds to damage it.
	 *
	 */
	private class Snare extends Obstacle {

		private int ticks;
		private int drawPriority = 50;
		/**
		 * Sets the tics to determine the Snare's lifespan. Health and Max Health are set.
		 *
		 * @param w  -  World
		 * @param x  -  X coordinate to place Obstacle
		 * @param y  -  Y coordinate to place Obstacle
		 * @param rotation - The orientation of the Object
		 */
		
		public Snare(World w, double x, double y, double rotation) {
			super(w, x, y, rotation);
			SoundPlayer.play("snare.mp3");
			ticks = 0;
			setDraw();
			maxHealth = 25;
			health = 25;
		}
		
		/**
		 * Ignore this method.  It has no Use for the Snare Obstacle
		 */
		
		@Override
		public void receiveDamage(int d) {}

		/**
		 * When there is a Collision check, the tank on top of the snare will recieve damage
		 */
		
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
		
		/**
		 * Returns the int that allows the gui to draw based on priority
		 */
		
		@Override
		public int getDrawPriority() {
			return drawPriority;
		}

		/**
		 * If the tic life span exceeds the given amount, the health of the obstacle is set to zero and
		 * then is destroyed.  If the tic count is a multiple of 5, it calls the setDraw method. 
		 */
		
		@Override
		public void act() {
			ticks++;
			if(ticks > 125)
				health = 0;
			if(ticks % 5 == 0) {
				setDraw();
			}
		}
		
		/**
		 * This method randomly sets the rotation of the object, then based on the random number
		 * generated it draws a different image.
		 */
		
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
