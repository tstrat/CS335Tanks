package gameModel;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * The main purpose of this class is to delay construction of the World until all the information
 * is available.
 * 
 * @author Parker Snell
 */
public class WorldCreator implements Serializable {

	/**
	 * Hi.
	 */
	private static final long serialVersionUID = 5086310289280403969L;
	
	private String fileContents;
	
	private transient List<TankPair> tanks;
	private transient List<AIPair> ais;
	
	private transient CommandReceiver receiver;
	
	/**
	 * Represents a pair of information necessary to construct a tank:
	 * The player, and the type of tank.
	 * 
	 * @author Parker Snell
	 */
	public static class TankPair implements Serializable {
		/**
		 * 1234567890
		 */
		private static final long serialVersionUID = 3709050594539542725L;

		/**
		 * P1, P2, etc. as used by the world loading code.
		 */
		public String player;
		
		/**
		 * One of "Standard Tank", "Heavy Tank", or "Hover Tank".
		 */
		public String tankName;
		
		/**
		 * Creates a TankPair with the given values.
		 */
		public TankPair(String player, String tankName) {
			this.player = player;
			this.tankName = tankName;
		}
	}
	
	/**
	 * For right now, this only needs the player number even though it's called a "pair".
	 * In the future, with different types of (usable) AIs, we will need to store the type.
	 * 
	 * @author Parker Snell
	 */
	public static class AIPair implements Serializable {
		/**
		 * Good day sir. This is how many sheep I count before I go to sleep.
		 */
		private static final long serialVersionUID = 164270533437161498L;
		
		/**
		 * Which player number the tank is assigned to.
		 */
		public int player;
		
		/**
		 * Creates an AIPair with the given values.
		 */
		public AIPair(int player) {
			this.player = player;
		}
	}
	
	/**
	 * Creates a WorldCreator for a given filename.
	 * 
	 * @param filename A valid map filename.
	 */
	public WorldCreator(String filename) {
		try {
			BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File(filename + ".txt")));
			
			Scanner scanner = new Scanner(is);
			StringBuffer sb = new StringBuffer();
			while (scanner.hasNextLine()) {
				sb.append(scanner.nextLine()).append("\n");
			}
			
			fileContents = sb.toString();
		} catch (FileNotFoundException e) {
			fileContents = "";
		}
		
		tanks = new ArrayList<WorldCreator.TankPair>();
		ais = new ArrayList<WorldCreator.AIPair>();
	}
	
	/**
	 * Returns an InputStream for the text data of the world file.
	 * Right now, this just loads the whole file in at one time, although
	 * it could be modified to return a stream for the actual file.
	 * 
	 * @return An InputStream for the map file's text data.
	 */
	public InputStream getInputStream() {
		return new ByteArrayInputStream(fileContents.getBytes());
	}
	
	/**
	 * Adds a TankPair to the list of pairs. If this WorldCreator is being used in a
	 * multiplayer environment, the pair's player field may be null, because the server
	 * will fill one in for you.
	 * 
	 * @param tankPair A TankPair describing the tank to be added.
	 */
	public void addTank(TankPair tankPair) {
		tanks.add(tankPair);
	}
	
	/**
	 * Adds an AIPair to the list of pairs. Similar to addTank, the player number is ignored
	 * when this object is sent over the network.
	 * 
	 * @param aiPair An AIPair describing the AI to be added.
	 */
	public void addAI(AIPair aiPair) {
		ais.add(aiPair);
	}
	
	/**
	 * Sets the CommandReceiver used to create the AIs.
	 * 
	 * @param cr A CommandReceiver.
	 */
	public void setCommandReceiver(CommandReceiver cr) {
		this.receiver = cr;
	}
	
	private TankPair findPair(String player) {
		for (TankPair pair : tanks) {
			if (pair.player.equals(player))
				return pair;
		}
		
		return null;
	}
	
	/**
	 * Creates a World specified by the level data, tanks, and AIs stored in this object.
	 * 
	 * @return A World object ready for game play.
	 */
	public World getWorld() {
		return new Loader().load();
	}
	
	/**
	 * Used by the Loader class.
	 * 
	 * @author Treavor
	 */
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
	
	private class Loader {
		
		private World world;
		
		// Sorry for the hacks...
		// This maps a player number to an x,y packed into an integer.
		private HashMap<Integer, Integer> ai_xy;
		
		/**
		 * Used to load the worlds from a WorldCreator.
		 */
		public World load() {
			world = new World();
			ai_xy = new HashMap<Integer, Integer>();
			
			int space1 = 0, space2 = 0;
			int count = 0;
						
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(getInputStream()));
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
						ai_xy.put(addThis.charAt(1) - '1' + 1, (place1 << 16) | place2);
						addPlayer(findPair(addThis), place1, place2);
					} else{
						addThing(addThis, place1, place2);
					}
					count = 0;
				}
				br.close();
			} catch (Exception e) {
				//System.err.println("Error: " + e.getMessage());
			}
			
			addAIPlayers();
			return world;
		}
		
		private void addAIPlayers() {
			for (AIPair pair : ais) {
				Integer xy = ai_xy.get(pair.player);
				if (xy == null)
					continue;
				
				int x = xy >> 16;
				int y = xy & 0xFFFF;
				new StandardTank(world, x, y, 0, pair.player);
			}
		}
		
		private void addPlayer(TankPair pair, int place1, int place2) {
			// No pair was found: don't add the player.
			if (pair == null)
				return;
			
			int player = 1;
			if (pair.player.equals("P2"))
				player = 2;
			else if (pair.player.equals("P3"))
				player = 3;
			else if (pair.player.equals("P4"))
				player = 4;
			
			if (pair.tankName.equals("Standard Tank"))
				new StandardTank(world, place1, place2, 0, player);
			else if (pair.tankName.equals("Hover Tank"))
				new HoverTank(world, place1, place2, 0, player);
			else if (pair.tankName.equals("Heavy Tank"))
				new HeavyTank(world, place1, place2, 0, player);
		}
		
		private void addThing(String thing, int x, int y) {
			ObsAndTer toAddS = ObsAndTer.valueOf(thing.toUpperCase());
			
			switch(toAddS){
			case HEALINGBEACON:
				new HealingBeacon(world, x, y, 0);
				break;
			case INDESTRUCTIBLE:
				new Indestructible(world, x, y, 0);
				break;
			case TREESTUMP:
				new TreeStump(world, x, y, 0);
				break;
			case SPIKEPIT:
				new SpikePit(world, x, y, 0, 4);
				break;
			case WALL2:
				new Wall(world, x, y, 0);
				break;
			case TNT:
				new TNTBarrel(world, x, y, 0);
				break;
			case MUD:
				new MudPatch(world, x, y, 0);
				break;
			case SPEEDPATCH:
				new SpeedPatch(world, x, y, 0);
				break;
			}
		}
	}

	public void addAIActors(World world) {
		for (AIPair pair : ais) {
			
			// Find the tank that belongs to me.
			Tank tank = null;
			for (Tank t : world.getTanks()) {
				if (t.getPlayerNumber() == pair.player)
					tank = t;
			}
			
			if (tank == null)
				continue;
			
			new StupidAI(world, tank, receiver);
		}
	}

	/**
	 * @return The list of tank pairs.
	 */
	public List<TankPair> getTankPairs() {
		return tanks;
	}
	
	/**
	 * @return The list of AI pairs.
	 */
	public List<AIPair> getAIPairs() {
		return ais;
	}
	
}
