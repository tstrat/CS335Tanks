package gameModel;

/**
 * 
 * This Command triggers a mine to be laid on the world.  The command is then sent to all
 * other players (if networked) or to the GameHandler of the current player for processing. 
 *
 */

public class LayMineCommand extends Command {
	/**
	 * Constructs the command with the players number.
	 * 
	 * @param player player number
	 */
	
	public LayMineCommand(int player) {
		super(player);
	}
	
	/**
	 * This method declares that the command is only applied to tanks.  If any other object tries to
	 * use the layMineCommand, an illegalArgumentException is thrown.  Then it proceeds to have the
	 * tank actor lay the mine.
	 * 
	 * @param a - The actor that is trying to use the command
	 * @throws IllegalArgumentException - "LayMineCommand only works on Tanks!"
	 */
	
	@Override
	public void applyTo(Actor a) {
		if (!(a instanceof Tank))
			throw new IllegalArgumentException("LayMineCommand only works on Tanks!");
		
		((Tank)a).layMine();

	}

}
