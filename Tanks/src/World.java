import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class World {
	
	private List<Actor> actors;
	
	public World() {
		actors = new LinkedList<Actor>();
		// TODO: the world should be initialized by reading from 
		// some map file, somehow
		
	}

	public void handleCollisions() {
		// TODO Someone has to come up with how collisions work
		
	}

	/*
	 * World goes through all its actors, and makes them use their act() method.
	 */
	public void makeActorsAct() {
		Iterator<Actor> it = actors.iterator();
		
		while (it.hasNext())
			it.next().act();
	}
	
	/*
	 * command() commands the actors with the list of commands
	 * passed through the parameter. This method first goes
	 * throught all the actors, and find which ones are Tanks.
	 * It then gives each tank all the commands; each tank will
	 * only follow commands relevent to them.
	 * 
	 * param c:
	 * 				The Command to send to the tanks.
	 */
	public void command(List<Command> commands) {
		Iterator<Actor> it = actors.iterator();
		
		while (it.hasNext()) {
			Actor a = it.next();
			if(a instanceof Tank) {
				for(Command c: commands) {
					((Tank) a).receiveCommand(c);
				}
			}
		}		
	}
	
	/**
	 * Returns the Actors list, mainly for the GUI.
	 */
	public List<Actor> getActors() {
		return actors;
	}

	public void addActor(Actor a) {
		actors.add(a);		
	}

}
