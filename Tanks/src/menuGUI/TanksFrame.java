package menuGUI;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;

import menuGUI.MainMenu;

/**
 * Creates the window that the Tank game will be run in.
 * 
 * @author seancmw, adapted from Cody Mingus's "Javaboy"
 *
 */


@SuppressWarnings("serial")
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
	 * Constructor for the TanksFrame. Sets it to go to the main menu when first created.
	 */
	
	
	public TanksFrame(){
		this.setLayout(new BorderLayout());
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
		case MULTIPLAYER_CHOICE:
			//currentPanel = new GUI_multiplayerChoice(this);
			panels.push(currentPanel);
			body.add(currentPanel, v.name());
			CardLayout cl3 = (CardLayout) body.getLayout();
			cl3.show(body, v.name());
			break;
		case GAME_SINGLE:
			currentPanel = new TanksDisplay("127.0.0.1", this); //Pass localhost or 127.0.0.1 as the host variable
			panels.push(currentPanel);
			body.add(currentPanel, v.name());
			CardLayout cl4 = (CardLayout) body.getLayout();
			cl4.show(body, v.name());
			break;
		}
	}
	
	
	
}
