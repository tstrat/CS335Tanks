import java.util.LinkedList;
import java.util.List;


public class World {
	
	private LinkedList<Actor> actors;
	
	public World() {
		actors = new LinkedList<Actor>();
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
		for(int i = 0; i < actors.size(); i++)
			actors.get(i).act();		
	}
	
	/*
	 * command() commands the actors with the list of commands
	 * passed through the parameter. This method first goes
	 * throught all the actors, and find which ones are Tanks.
	 * It then gives each tank all the commands; each tank will
	 * only follow commands relevent to them.
	 * 
	 * param c:
	 * 				The Command to send to this GameHandler.
	 */
	public void command(List<Command> commands) {
		for(int i = 0; i < actors.size(); i++) {
			Actor a = actors.get(i);
			if(a instanceof Tank) {
				for(Command c: commands) {
					((Tank) actors.get(i)).receiveCommand(c);
				}
			}
		}		
	}

}
