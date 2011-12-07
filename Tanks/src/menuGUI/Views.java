package menuGUI;

/**
 * Different things that the GUI can show to the player
 * @author seancmw, adapted from Cody Mingus's "Javaboy"
 *
 */

public enum Views {

	
	/**
	 * The main menu 
	 */
	MAIN,
	
	/**
	 * A screen that provides the player the chance to pick the map, their tank, and their weapon
	 */
	LOBBY,
	
	/**
	 * The actual game screen, for singleplayer
	 */
	GAME_SINGLE,
	
	/**
	 * The actual game screen, for multiplayerplayer
	 */
	GAME_MULTI,
	
	/**
	 * Where players can change options - most likely, key bindings
	 */
	
	OPTIONS,
	
	/**
	 * Where players choose to host or join a multiplayer game
	 */
	MULTIPLAYER_CHOICE
	
	
}
