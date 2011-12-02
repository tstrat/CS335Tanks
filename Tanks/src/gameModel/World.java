package gameModel;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class World extends Observable {

	private List<Actor> actors;
	private List<Collidable> collidables;

	public World() {
		actors = new ArrayList<Actor>();
		collidables = new ArrayList<Collidable>();
		// TODO: the world should be initialized by reading from
		// some map file, somehow

	}

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

	/*
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
		return actors;
	}

	public void addActor(Actor a) {
		actors.add(a);
		if (a instanceof Collidable)
			collidables.add((Collidable) a);
	}

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
