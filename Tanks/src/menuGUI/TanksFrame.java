package menuGUI;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import menuGUI.MainMenu;

/**
 * Creates the window that the Tank game will be run in.
 * 
 * @author seancmw, adapted from Cody Mingus's "Javaboy"
 *
 */


public class TanksFrame extends JFrame{

	
	private MasterPanel currentPanel;
	private Stack<MasterPanel> panels;
	private JPanel body;
	
	/**
	 * Makes a new TanksFrame
	 * @param args
	 * 	N/A - none are used
	 */
	public static void main(String[] args)
	{
		new TanksFrame();
	}
	
	/**
	 * Constructor for the TanksFrame. Creates a menu bar for exiting,
	 * and sets it to go to the main menu when first created.
	 */
	
	
	//TODO: is a menu bar really needed?
	public TanksFrame(){
		this.setLayout(new BorderLayout());
		this.setUpMenuBar();
		this.setMainMenu();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(200, 100);
		this.setSize(800, 600);
		this.setVisible(true);
		this.requestFocus();
	}

	/**
	 * When the client is first launched, set the frame to
	 * show the main menu
	 */
	
	private void setMainMenu() {
		panels = new Stack<MasterPanel>();
		currentPanel = new MainMenu(this);
		
		body = new JPanel();
		body.setLayout(new CardLayout());
		body.add(currentPanel, "MAIN");
		
		panels.push(currentPanel);
		this.add(body, BorderLayout.CENTER);		
	}



	/**
	 * Creates the menu bar for the Tank game window
	 */
	
	private void setUpMenuBar() {
		JMenuBar jmb = new JMenuBar();
		JMenuItem exit = new JMenuItem("Exit");
		jmb.add(exit);
		exit.addActionListener(new ExitListener());
		this.add(jmb, BorderLayout.NORTH);
	}
	
	private class ExitListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}

	}
	
	/**
	 * Switches views based on based enum
	 * @param v
	 * 			The view to switch to
	 */
	
	public void changeViews(Views v)
	{
		switch(v)
		{
		case MAIN:
			currentPanel = new MainMenu(this);
			panels.push(currentPanel);
			body.add(currentPanel, v.name());
			CardLayout cl = (CardLayout) body.getLayout();
			cl.show(body, v.name());
			break;
		
		case LOBBY:
			currentPanel = new TanksLobby(this);
			panels.push(currentPanel);
			body.add(currentPanel, v.name());
			CardLayout cl2 = (CardLayout) body.getLayout();
			cl2.show(body, v.name());
			break;
			
		}
	}
	
	
	
}