package gameModel;

public class ExplosiveMine extends Mine {

	/**
	 * The constructor for the ExplosiveMine class sends parameters on to 
	 * the Mine constructor.
	 * 
	 * @see gameModel.Mine
	 * 	and
	 * @see gameModel.Collidable
	 * 
	 * @param w The World that this Collidable belongs to.
	 * @param x The initial x-position.
	 * @param y The initial y-position.
	 * @param rotation The initial rotation.
	 * 
	 * @param t  The tank belonging to the player that laid the mine.
	 */
	
	public ExplosiveMine(World w, double x, double y, double rotation, Tank t) {
		super(w, x, y, rotation, t);
	}

	/** 
	 * When the activateMine method is triggered by the collide method
	 * it creates a new explosion at the location of the mine, then
	 * the mine is used and its existance is set to false and is removed from
	 * the world.
	 */
	
	@Override
	public void activateMine() {
		SoundPlayer.play("mineexplode.mp3");
		new MineExplosion(w, x, y, rotation);
		exists = false;
	}
	
	/**
	 * The MineExplosion class creates a collidable int he form of an 
	 * explosion in the location of the mine. This is triggered by the
	 * activation of the mine.  The explosion causes all Tanks who are
	 * in its collision range to receive damage.
	 */
	
	private class MineExplosion extends Collidable{

		private boolean notActivated;
		private int ticks;

		/**
		 * The constructor is a Collidable and so calls the super constructor
		 * with the explosion location in the world.  Then it sets the tick count to
		 * 0, and states that it is not yet activated.
		 * 
		 * @param w The World that this Collidable belongs to.
		 * @param x The initial x-position.
		 * @param y The initial y-position.
		 * @param rotation The initial rotation
		 */
		
		public MineExplosion(World w, double x, double y, double rotation) {
			super(w, x, y, rotation);
			notActivated = true;
			ticks = 0;
		}

		/** 
		 * If the collision occurs with an obstacle that is not the tank of the player whose
		 * Mine caused the explosion, then the obstacle (opponent's tank or otherwise) recieves
		 * damage.
		 */
		
		@Override
		public void collide(Collidable c) {
			if(c instanceof Obstacle && !c.equals(t) && notActivated) {
				((Obstacle) c).receiveDamage(850);
				notActivated = false;				
			}
		}

		/**
		 * The act method increments the tick count.
		 */
		
		@Override
		public void act() {
			ticks++;			
		}
		
		/**
		 * When the tick count reaches 150, the explosion object will no longer exist on the world.
		 * It then will be removed.
		 */
		
		public boolean exists() {
			return ticks < 150;
		}
		
		/**
		 * Sets the drawPriority to 25.
		 */
		
		@Override
		public int getDrawPriority() {
			return 25;
		}
		
		
		private DrawObject draw = new DrawFadingObject("mineExplosion.png", 80);
		
		/**
		 * returns the DrawObject with the image of the mine explosion and a fade count of 80.
		 */
		
		@Override
		public DrawObject getDraw() {
			return draw;
		}
		
	}

}
