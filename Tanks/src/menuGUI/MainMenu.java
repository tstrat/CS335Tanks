package menuGUI;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


/**
 * 
 * This will be the screen when Tanks first starts.  It will have options for
 * single player, multiplayer, a menu to change options, and an exit button
 * @author seancmw, adapted from Cody Minugs
 *
 */

@SuppressWarnings("serial")
public class MainMenu extends MasterPanel{

	private JButton singleplayer;
	private JButton multiplayer;
	private JButton options;
	private JButton exit;
	
	public MainMenu(TanksFrame t)
	{
		super(t);
		setupLayout();
		setUpButtons();
	}
	/**
	 * Creates a grid layout for the whole panel,
	 * as well as making a second grid layout to hold
	 * the logo at the top.  This second part will probably
	 * be isolated to a different method.
	 */
	private void setupLayout()
	{
		this.setLayout(new GridLayout(2,1));
		JPanel graphicHolder = new JPanel();
		System.out.println(graphicHolder.getHeight());
		System.out.println(graphicHolder.getWidth());
		this.add(graphicHolder);
		/*
		*TODO: figure out how large the logo needs to be and/or set
		*the size of graphicHolder to be the size of the logo
		*/
	}
	/**
	 * Creates all the buttons for the main menu, and adds them into
	 * the bottom part of the main menu screen.
	 */
	private void setUpButtons()
	{
		JPanel buttonsHolder = new JPanel();
		buttonsHolder.setLayout(new GridLayout(2,2));
		singleplayer = new JButton("Single Player");
		Font sp = new Font("Single Player", Font.PLAIN, 32);
		singleplayer.setFont(sp);
		singleplayer.addActionListener(new singleplayerListener());
		buttonsHolder.add(singleplayer);
		multiplayer = new JButton("Mulitplayer");
		Font mp = new Font("Multiplayer", Font.PLAIN, 32);
		multiplayer.setFont(mp);
		multiplayer.addActionListener(new multiplayerListener());
		buttonsHolder.add(multiplayer);
		options = new JButton("Options");
		Font op = new Font("Options", Font.PLAIN, 33);
		options.setFont(op);
		buttonsHolder.add(options);
		exit = new JButton("Exit");
		Font ex = new Font("Exit", Font.PLAIN, 36);
		exit.setFont(ex);
		exit.addActionListener(new ExitListener());
		buttonsHolder.add(exit);
		this.add(buttonsHolder);
	}
	
	private class singleplayerListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			t.changeViews(Views.MAP);
		}
		
	}
	
	private class multiplayerListener implements ActionListener
	{
		public void actionPerformed (ActionEvent arg0)
		{
			t.changeViews(Views.LOBBY);
		}
	}
	
	private class ExitListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}

	} 
	
}
