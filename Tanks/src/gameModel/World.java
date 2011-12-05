package gameModel;
/**
 * Creates a new world which holds the actors and collidables of the current game.
 * Does not do the inner gui parts to the game but is the world in which everything
 * interacts with.
 * 
 */
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class World extends Observable {

	private List<Actor> actors;
	private List<Collidable> collidables;

	/**
	 * Constructor for a new World. A world holds a list of Actors and Collidables.
	 * This constructor initializes those to empty.
	 */
	public World() {
		actors = new ArrayList<Actor>();
		collidables = new ArrayList<Collidable>();
		// TODO: the world should be initialized by reading from
		// some map file, somehow

	}

	/**
	 * Handles all collisions in the world. If a collision is made, checks
	 * if the two objects are properly colliding and then calls the collide method
	 * for those objects
	 */
	public void handleCollisions() {
		for (int i = 0; i < collidables.size(); i++) {
			for (int j = 0; j < collidables.size(); j++) {
				Rectangle collis = collidables.get(i).getCollisionBox()
						.intersection(collidables.get(j).getCollisionBox());
				if (collis.width > 0 && collis.height > 0 && i != j){
						collidables.get(i).collide(collidables.get(j));
				}
			}
		}
	}

	/**
	 * World goes through all its actors, and makes them use their act() method.
	 */
	public void makeActorsAct() {
		for (int i = 0; i < actors.size(); i++) {
			actors.get(i).act();
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * command() commands the actors with the list of commands passed through
	 * the parameter. This method first goes through all the actors, checks the
	 * Actor's player number. If the player number matches the one on the
	 * Command, it receives the command.
	 * 
	 * @param commands
	 *            The list of Commands to send to the tanks.
	 */
	public void command(List<Command> commands) {
		for (int i = 0; i < actors.size(); i++) {
			Actor a = actors.get(i);
			for (Command c : commands) {
				if (a.getPlayerNumber() == c.getPlayer())
					a.receiveCommand(c);
			}
		}
	}

	/**
	 * Returns the Actors list, mainly for the GUI.
	 */
	public List<Actor> getActors() {
		Collections.sort(actors);
		return actors;
	}
	
	/**
	 * Adds and Actor to the world, if also a Collidable
	 * adds it to that list as well as the actor list.
	 * 
	 * @param a - an Actor
	 */
	public void addActor(Actor a) {
		actors.add(a);
		if (a instanceof Collidable)
			collidables.add((Collidable) a);
	}
	
	/**
	 * Cleans up unnecessary things in the world. If an actor
	 * no longer exists it removes that actor
	 */
	public void cleanup() {
		int i = 0;
		while (i < actors.size()) {
			if (!actors.get(i).exists()) {
				collidables.remove(actors.get(i));
				actors.remove(i);
			} else
				i++;
		}
	}

}
