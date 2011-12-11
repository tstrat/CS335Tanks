package gameModel;

public class SpiderMine extends Mine{
	private Tank tar;
	
	public SpiderMine(World w, double x, double y, double rotation, Tank t) {
		super(w, x, y, rotation, t);
		// TODO Auto-generated constructor stub
	}
	private static DrawObject draw = new DrawSingleFrameObject("InvisibleBig.png");
	private static DrawObject selectiveDraw = new DrawSingleFrameObject("mineTargetBig.png");
	@Override
	public DrawObject getDraw() {
		// TODO Auto-generated method stub
		if(w.getPlayer() != player)
			return draw;
		return selectiveDraw;
	}
	
	@Override
	public void activateMine() {
		if(exists)
			new Spider(w, x, y, tar);
		exists = false;
		
	}
	
	@Override
	public void collide(Collidable c) {
		if(c instanceof Tank && !c.equals(t)) {
			tar = (Tank)c;
			activateMine();
		}
	}
	
	private class Spider extends Collidable {
		private int drawPriority = 18;
		private Tank target;
		
		private int ticks = 150;

		public Spider(World w, double x, double y, Tank tar) {
			super(w, x, y, TRand.random() * 2 * Math.PI);
			target = tar;
		}
		
		public void act() {
			ticks--;
			if(target.getX() > Spider.this.x)
				Spider.this.x += 1.5;
			else if(target.getX() < Spider.this.x)
				Spider.this.x -= 1.5;
			if(target.getY() > Spider.this.y)
				Spider.this.y += 1.5;
			else if(target.getY() < Spider.this.y)
				Spider.this.y -= 1.5;
			if(ticks < 0)
				explode();
		}

		@Override
		public void collide(Collidable c) {
			if(c.equals(tar) && exists)
				explode();			
		}

		private void explode() {
			Explosion.createExplosion(w, Spider.this.x, Spider.this.y, 1, 0, 12);
			exists = false;
		}

		@Override
		public int getDrawPriority() {
			// TODO Auto-generated method stub
			return drawPriority;
		}

		private DrawObject draw1 = new DrawSingleFrameObject("spider1.png");
		private DrawObject draw2 = new DrawSingleFrameObject("spider2.png");
		private DrawObject draw3 = new DrawSingleFrameObject("spider3.png");
		@Override
		public DrawObject getDraw() {
			if(ticks % 9 < 3)
				return draw1;
			else if(ticks % 9 < 6)
				return draw2;
			else
				return draw3;
		}
		
	}
}
