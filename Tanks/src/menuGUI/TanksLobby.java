package menuGUI;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;


/**
 * This will be a display to show the players that are connected
 * and potentially allow them to set their colors. It's a way
 * to make sure both players are ready before the match begins.
 * 
 * @author seancmw
 *
 */

public class TanksLobby extends MasterPanel {

	
		public TanksLobby(TanksFrame t)
		{
			super(t);
			buttonsAndLayout();
		}
		
		
		private void buttonsAndLayout()
		{
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			JButton previous = new JButton("<-");
			previous.setAlignmentY(CENTER_ALIGNMENT);
			previous.setAlignmentX(LEFT_ALIGNMENT);
			this.add(previous);
			this.add(Box.createRigidArea(new Dimension(50,0)));
			JButton next = new JButton("->");
			next.setAlignmentX(RIGHT_ALIGNMENT);
			next.setAlignmentY(CENTER_ALIGNMENT);
			this.add(next);
			JButton mainMenu = new JButton("Main Menu");
			mainMenu.setAlignmentX(CENTER_ALIGNMENT);
			mainMenu.setAlignmentY(BOTTOM_ALIGNMENT);
			mainMenu.addActionListener(new mainMenuListener());
			this.add(mainMenu);
		}
		
		
		
		private class mainMenuListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0)
			{
				t.changeViews(Views.MAIN);
			}
		}
	
}
