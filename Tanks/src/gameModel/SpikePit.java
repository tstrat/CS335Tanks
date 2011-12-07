package gameModel;

public class SpikePit extends Terrain {

 private static int drawPriority = 25;
 private int damage;
 
 public SpikePit(World w, double x, double y, double rotation, int damage) {
  super(w, x, y, rotation);
  this.damage = damage;
  exists = true;
 }
 
 /**
  * The collide method dictates how terrain interacts with other
  * objects colliding into it.
  */
 @Override
 public void collide(Collidable c) {
  if(c instanceof Obstacle) {
   ((Obstacle) c).receiveDamage(damage);
  }
 }
 
 // TODO: This stuff should be moved to the relevant classes.
 private static DrawObject draw = new DrawSingleFrameObject("defaultImg.png"); 
 /**
  * Gets the DrawObject used to draw this Terrain. This can return null,
  * in which case nothing should be drawn.
  * 
  * @return A DrawObject representing this Terain, or null.
  */
 @Override
 public DrawObject getDraw() {
  return draw;
 }

 /**
  * Returns the priority of this Terrain's draw. A higher priority object is drawn over
  * a lower priority object in the main GUI.
  */
 @Override
 public int getDrawPriority() {
  // TODO Auto-generated method stub
  return drawPriority;
 }

}