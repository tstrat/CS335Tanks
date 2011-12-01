package gameModel;
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
	
	/**
	 * command() commands the actors with the list of commands
	 * passed through the parameter. This method first goes
	 * through all the actors, checks the Actor's player number.
	 * If the player number matches the one on the Command, it
	 * receives the command.
	 * 
	 * @param commands The list of Commands to send to the tanks.
	 */
	public void command(List<Command> commands) {
		// TODO This method can be optimized to cache the players,
		// avoiding the two loops.
		Iterator<Actor> it = actors.iterator();
		
		while (it.hasNext()) {
			Actor a = it.next();
			for(Command c: commands) {
				if (a.getPlayerNumber() == c.getPlayer())
					a.receiveCommand(c);
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
