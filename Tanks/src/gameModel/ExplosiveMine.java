package gameModel;

public class ExplosiveMine extends Mine {

	public ExplosiveMine(World w, double x, double y, double rotation, Tank t) {
		super(w, x, y, rotation, t, t.getPlayerNumber());
	}

	@Override
	public void activateMine() {
		new MineExplosion(w, x, y, rotation);
		exists = false;
	}
	
	private class MineExplosion extends Collidable{

		private boolean notActivated;
		private int ticks;

		public MineExplosion(World w, double x, double y, double rotation) {
			super(w, x, y, rotation);
			notActivated = true;
			ticks = 0;
		}

		@Override
		public void collide(Collidable c) {
			if(c instanceof Obstacle && !c.equals(t) && notActivated) {
				((Obstacle) c).receiveDamage(850);
				notActivated = false;				
			}
		}

		@Override
		public void act() {
			ticks++;			
		}
		
		public boolean exists() {
			return ticks < 150;
		}

		@Override
		public int getDrawPriority() {
			return 25;
		}

		private DrawObject draw = new DrawFadingObject("mineExplosion.png", 80);
		@Override
		public DrawObject getDraw() {
			return draw;
		}
		
	}

}
