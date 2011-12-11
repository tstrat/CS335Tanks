package gameModel;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
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
	private String singleplayertank;
	private int aiNumber;
	private CommandReceiver receive;

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
		for (int i = 0; i < collidables.size(); i++) {
			for (int j = i + 1; j < collidables.size(); j++) {
				Rectangle collis = collidables.get(i).getCollisionBox()
						.intersection(collidables.get(j).getCollisionBox());
				if (collis.width > 0 && collis.height > 0){
						collidables.get(i).collide(collidables.get(j));
						collidables.get(j).collide(collidables.get(i));
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
	
	public void loadFileSP(String mapName, String tankName, int AINum, CommandReceiver receive) {		
		int space1 = 0, space2 = 0;
		int count = 0;
		singleplayertank = tankName;
		aiNumber = AINum;
		this.receive = receive;
		
		try {
			FileInputStream fstream = new FileInputStream(mapName + ".txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			strLine = br.readLine();		
			while((strLine = br.readLine()) != null){
				for(int i = 0; i < strLine.length(); i++){
					if(strLine.charAt(i) == ' ' && count == 0){
						space1 = i;
						count++;
					}
					if(strLine.charAt(i) == ' ' && count == 1)
						space2 = i;
				}
				int place1 = Integer.parseInt(strLine.substring(space1+1, space2));
				int place2 = Integer.parseInt(strLine.substring(space2+1, strLine.length()));
				String addThis = new String((strLine.substring(0, space1-4)));
				if(addThis.equals("P1") || addThis.equals("P2") || addThis.equals("P3") || addThis.equals("P4")){
					addTanksToWorldSP(addThis, place1, place2);
				} else{
					addThingsToWorldSP(addThis, place1, place2);
				}
				count = 0;
			}
			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		} 
	}
	
	public enum ObsAndTer {
		
		HEALINGBEACON,
		INDESTRUCTIBLE,
		TREESTUMP,
		WALL2,
		TNT,
		SPIKEPIT,
		P1,
		P2,
		P3,
		P4,
		SPEEDPATCH,
		MUD
	}
	
	private void addTanksToWorldSP(String tankType, int posx, int posy){
		if(tankType.equals("P1")){
			if(singleplayertank.equals("Standard Tank")){
				new StandardTank(this, posx, posy, 0, 1);
			}
			if(singleplayertank.equals("Heavy Tank")){
				new HeavyTank(this, posx, posy, 0, 1);
			}
			if(singleplayertank.equals("Hover Tank")){
				new HoverTank(this, posx, posy, 0, 1);
			}	
		}
		if(tankType.equals("P2") && aiNumber > 0){
			Tank tank = new StandardTank(this, posx, posy, 0, 2);
			new StupidAI(this, tank, receive);
			aiNumber--;
		}
		if(tankType.equals("P3") && aiNumber > 0){
			Tank tank = new StandardTank(this, posx, posy, 0, 3);
			new StupidAI(this, tank, receive);
			aiNumber--;
		}
		if(tankType.equals("P4") && aiNumber > 0){
			Tank tank = new StandardTank(this, posx, posy, 0, 4);
			new StupidAI(this, tank, receive);
			aiNumber--;
		}
		
	}
	
	private void addThingsToWorldSP(String toAdd, int x, int y){
		ObsAndTer toAddS = ObsAndTer.valueOf(toAdd.toUpperCase());
		
		switch(toAddS){
			case HEALINGBEACON:
				new HealingBeacon(this, x, y, 0);
				break;
			case INDESTRUCTIBLE:
				new Indestructible(this, x, y, 0);
				break;
			case TREESTUMP:
				new TreeStump(this, x, y, 0);
				break;
			case SPIKEPIT:
				new SpikePit(this, x, y, 0, 4);
				break;
			case WALL2:
				new Wall(this, x, y, 0);
				break;
			case TNT:
				new TNTBarrel(this, x, y, 0);
				break;
			case MUD:
				new MudPatch(this, x, y, 0);
				break;
			case SPEEDPATCH:
				new SpeedPatch(this, x, y, 0);
				break;
			
		}
	}

}
