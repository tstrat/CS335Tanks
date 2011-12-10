package gameModel;

public class LayMineCommand extends Command {

	public LayMineCommand(int Player) {
		super(Player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void applyTo(Actor a) {
		if (!(a instanceof Tank))
			throw new IllegalArgumentException("LayMineCommand only works on Tanks!");
		
		((Tank)a).layMine();

	}

}
