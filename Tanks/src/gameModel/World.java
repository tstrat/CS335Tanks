package gameModel;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**
 * Creates a new world which holds the actors and collidables of the current game.
 * Does not do the inner gui parts to the game but is the world in which everything
 * interacts with.
 * 
 */
public class World extends Observable {

	private List<Actor> actors;
	private List<Collidable> collidables;
	private List<Tank> tanks;
	private int player;
	private boolean needsSorting;
	/**
	 * Constructor for a new World. A world holds a list of Actors and Collidables.
	 * This constructor initializes those to empty. The player is set to the default
	 * value of 1. You should set it later, once you know what player you are controlling.
	 */
	public World() {
		actors = new ArrayList<Actor>();
		collidables = new ArrayList<Collidable>();
		tanks = new ArrayList<Tank>();
		player = 1;
	}
	
	/**
	 * Handles all collisions in the world. If a collision is made, checks
	 * if the two objects are properly colliding and then calls the collide method
	 * for those objects
	 */
	public void handleCollisions() {
		Collidable iC, jC;
		for (int i = 0; i < collidables.size(); i++) {
			for (int j = i + 1; j < collidables.size(); j++) {
				iC = collidables.get(i);
				jC = collidables.get(j);
				if(Math.abs(iC.getX() - jC.getX()) < 120) {
					if(Math.abs(iC.getY() - jC.getY()) < 120) {
						Rectangle collis = collidables.get(i).getCollisionBox()
								.intersection(collidables.get(j).getCollisionBox());
						if (collis.width > 0 && collis.height > 0){
								collidables.get(i).collide(collidables.get(j));
								collidables.get(j).collide(collidables.get(i));
						}
					}
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
			for (int j = 0; j < commands.size(); ++j) {
				Command c = commands.get(j);
				// TODO: Why does this happen?
				// It happens when StandardTank leaves a mine I think.
				if (c == null)
					continue;
				
				if (a.getPlayerNumber() == c.getPlayer())
					a.receiveCommand(c);
			}
		}
	}

	/**
	 * Returns the Actors list, mainly for the GUI.
	 */
	public List<Actor> getActors() {
		if (needsSorting) {
			Collections.sort(actors);
			needsSorting = false;
		}
		return actors;
	}
	
	/**
	 * Adds and Actor to the world, if also a Collidable
	 * adds it to that list as well as the actor list.
	 * And the same with the tanks list.
	 * 
	 * @param a - an Actor
	 */
	public void addActor(Actor a) {
		actors.add(a);
		needsSorting = true;
		if (a instanceof Collidable) {
			collidables.add((Collidable) a);
			
			if (a instanceof Tank) {
				tanks.add((Tank) a);
			}
		}
	}
	
	/**
	 * Gets the list of tanks.
	 * 
	 * @return A List of all tanks currently in the World.
	 */
	public List<Tank> getTanks() {
		return tanks;
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
				tanks.remove(actors.get(i));
				actors.remove(i);
			} else
				i++;
		}
	}
	
	/** 
	 * @return Player number of winner, or 0 if no one has won yet
	 */
	public int getWinner() {
		if(tanks.size() == 1) {
			setChanged();
			notifyObservers(tanks.get(0).getPlayerNumber());
			return tanks.get(0).getPlayerNumber();
		}
		return 0;
	}
	
	/**
	 * Returns the player number. The default value is 1, unless you change it
	 * via setPlayer.
	 * 
	 * @return The player number that this World has control of.
	 */
	public int getPlayer() {
		return player;
	}
	
	/**
	 * Sets the player number. This should generally be done after receiving the player number
	 * from the TanksClient.
	 * 
	 * @param player The new player number.
	 */
	public void setPlayer(int player) {
		this.player = player;
	}

}
