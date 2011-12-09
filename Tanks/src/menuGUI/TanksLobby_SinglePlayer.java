package menuGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class TanksLobby_SinglePlayer extends TanksLobby {
	
	public TanksLobby_SinglePlayer(TanksFrame t) {
		super(t);
		initComponents();
		listeners();
		singlePlayerListeners();		
	}
	
	private void singlePlayerListeners()
	{
		playButton.addActionListener(new SinglePlayListener());
	}
	
	private class SinglePlayListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			t.changeViews(Views.GAME_SINGLE);
			
		}
		
	}
	
	

}
