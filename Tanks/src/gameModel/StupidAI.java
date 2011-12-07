package gameModel;

/**
 * This is a very basic AI that just moves the tank forward
 * and occasionally rotates it.
 * 
 * @author Parker Snell
 */
public class StupidAI extends AIController {

	/**
	 * A count of how many ticks have elapsed.
	 */
	private int step;
	
	/**
	 * How many ticks to move next turn.
	 */
	private int moveTicks;
	
	/**
	 * When to reset the step counter after rotating.
	 * This is always >= moveTicks.
	 */
	private int rotateTicks;
	
	public StupidAI(World w, Tank tank, CommandReceiver receiver) {
		super(w, tank, receiver);
		
		resetTicks();
	}
	
	/**
	 * Resets step, moveTicks and rotateTicks.
	 */
	private void resetTicks() {
		step = 0;
		moveTicks = (int)(TRand.random() * 100) + 50;
		rotateTicks = moveTicks + (int)(TRand.random() * 50);
	}

	@Override
	public void act() {
		++step;
		
		//if (step % 10 == 0)
		//	receiver.receiveCommand(new FireCommand(player));
		
		if (step < moveTicks)
			receiver.receiveCommand(new MoveCommand(player, false));
		else if (step < rotateTicks)
			receiver.receiveCommand(new RotateCommand(player, 0.05));
		else
			resetTicks();
	}

}
