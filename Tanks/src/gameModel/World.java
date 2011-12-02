package gameModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;


public class World extends Observable {
	
	private List<Actor> actors;
	
	public World() {
		actors = new ArrayList<Actor>();
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
		for(int i = 0; i < actors.size(); i++) {
			actors.get(i).act();
		}
		setChanged();
		notifyObservers();
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
		for(int i = 0; i < actors.size(); i++) {
			Actor a = actors.get(i);
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
