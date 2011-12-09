package gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * A menu where the player can choose to host or join a multiplayer game
 * @author Sean
 *
 */

@SuppressWarnings("serial")
public class GUI_multiplayerChoice extends MasterPanel {

	private JButton host;
	private JButton join;
	private JButton mainMenu;
	
	public GUI_multiplayerChoice(TanksFrame t) {
		super(t);
		this.setLayout(new GridLayout(3,1));
		setButtons();
	}
	
	/**
	 * Creates three buttons: Host, Join, and Main Menu. All of them use the changeView method in
	 * TanksFrame
	 */
	
	private void setButtons()
	{
		Font tempFont = new Font("Host", Font.PLAIN, 32);
		
		host = new JButton("Host");
		
		host.setFont(tempFont);
		
		host.addActionListener(new hostListener());
		
		this.add(host);
		
		tempFont = new Font("Join", Font.PLAIN, 32);
		
		join = new JButton("Join");
		
		join.setFont(tempFont);
		
		join.addActionListener(new joinListener());
		
		this.add(join);
		
		mainMenu = new JButton("Main Menu");
		
		tempFont = new Font("Main Menu", Font.PLAIN, 32);
		
		mainMenu.setFont(tempFont);
		
		mainMenu.addActionListener(new menuListener());
		
		this.add(mainMenu);
	}
	
	private class hostListener implements ActionListener
	{
		public void actionPerformed (ActionEvent arg0)
		{
			t.changeViews(Views.LOBBY_MULTI, true);
		}
	}
	
	private class joinListener implements ActionListener
	{
		public void actionPerformed (ActionEvent arg0)
		{
			t.changeViews(Views.LOBBY_MULTI, false);
		}
	}
	
	private class menuListener implements ActionListener
	{
		public void actionPerformed (ActionEvent arg0)
		{
			t.changeViews(Views.MAIN);
		}
	}
	
	
	

}
