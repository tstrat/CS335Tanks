package gameModel;

/**
 * 
 * @author Messiah Kane
 *
 *		The SpiderMine class defines a special mine that, upon activation, creates a
 *		small explosive spider that chases down whatever set the mine off.
 *
 */

public class SpiderMine extends Mine{
	
	/**
	 * This is the target of the explosive spiders the mine creates, set when a tank 
	 * moves over the mine
	 */
	private Tank tar;
	
	/**
	 * Creates a new SpiderMine
	 * 
	 * @see Mine.Mine(World, double, double, double, Tank)
	 * 
	 */
	public SpiderMine(World w, double x, double y, double rotation, Tank t) {
		super(w, x, y, rotation, t);
	}
	
	/**
	 * The DrawObjects of SpiderMine. They are larger than that of normal mines 
	 * because of the larger activation radius of the SpiderMine
	 */
	private static DrawObject draw = new DrawSingleFrameObject("InvisibleBig.png");
	private static DrawObject selectiveDraw = new DrawSingleFrameObject("mineTargetBig.png");
	
	/**
	 * returns the draw of the SpiderMine. It is a target if the world is drawn
	 * for the player who dropped the mine, and transparent otherwise
	 * 
	 * @see Mine.DrawObject()
	 */
	@Override
	public DrawObject getDraw() {
		if(w.getPlayer() != player)
			return draw;
		return selectiveDraw;
	}
	
	/**
	 * This is called when the mine is activated by an enemy Tank coming int ocontact with
	 * the mine's activation radius. It creates an explosive Spider with the target set to
	 * the tan kthat set the mine off. The Mine can only do this one, and becomes nonexistant.
	 */
	@Override
	public void activateMine() {
		if(exists)
			new Spider(w, x, y, tar);
		exists = false;
		
	}
	
	/**
	 * When a SpiderMine collides with a tank that is not the dropper of the mine, it will
	 * become activated. Otherise it does nothing.
	 */
	@Override
	public void collide(Collidable c) {
		if(c instanceof Tank && !c.equals(t)) {
			tar = (Tank)c;
			activateMine();
		}
	}
	
	/**
	 * 
	 * @author Messiah Kane
	 *
	 *		The private Spider class defines the explosive spiders that the SpiderMine creates
	 *		upon activation. It chases down a target Tank, and explodes after it reaches its
	 *		target or if it runs out of time.
	 *
	 */
	private class Spider extends Collidable {
		
		private int drawPriority = 18;
		private Tank target;
		
		/**
		 * the lifetime of a Spider
		 */
		private int ticks = 300;

		/**
		 * The Spider is created at the given location and has its target set to a certain tank
		 * (The one that set off the mine that created the Spider)
		 * @param w		The World the spider is created it
		 * @param x		its x position
		 * @param y		its y position
		 * @param tar	The target of the Spider: an enemy tank
		 */
		public Spider(World w, double x, double y, Tank tar) {
			super(w, x, y, TRand.random() * 2 * Math.PI);
			target = tar;
		}
		
		/**
		 * During its act method the Spider chases down its target and explodes if
		 * it has run out of time
		 */
		@Override
		public void act() {
			ticks--;
			if(target.getX() > Spider.this.x)
				Spider.this.x += .8;
			else if(target.getX() < Spider.this.x)
				Spider.this.x -= .8;
			if(target.getY() > Spider.this.y)
				Spider.this.y += .8;
			else if(target.getY() < Spider.this.y)
				Spider.this.y -= .8;
			if(ticks < 0)
				explode();
		}
		
		/**
		 * Collides with an actor, blowing up if it is its target Tank
		 */
		@Override
		public void collide(Collidable c) {
			if(c.equals(tar) && exists)
				explode();			
		}

		/**
		 * Creates s small but dengerous explosion at the Spider's location. The Spider no
		 * longer exists after exploding.
		 */
		private void explode() {
			Explosion.createExplosion(w, Spider.this.x, Spider.this.y, 1, 0, 12);
			exists = false;
		}

		/**
		 * @see Actor.retirnDrawPriority()
		 */
		@Override
		public int getDrawPriority() {
			return drawPriority;
		}
		
		/**
		 * The multiple draws of Spider are drawn at different times.
		 */
		private DrawObject draw1 = new DrawSingleFrameObject("spider1.png");
		private DrawObject draw2 = new DrawSingleFrameObject("spider2.png");
		private DrawObject draw3 = new DrawSingleFrameObject("spider3.png");
		
		/**
		 * Depending on the ticks left of the spider's existence, it will fluctuate
		 * between 3 positions. This makes the Spider appear as if it is running.
		 */
		@Override
		public DrawObject getDraw() {
			if(ticks % 16 < 4)
				return draw1;
			else if(ticks % 16 < 8)
				return draw2;
			else if (ticks % 16 < 12)
				return draw1;
			else
				return draw3;
		}
		
	}
}
