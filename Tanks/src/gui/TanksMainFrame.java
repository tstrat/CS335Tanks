package gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class TanksMainFrame extends JFrame {
	
	private JPanel mainPanel;
	private int wins, losses;
	
	public TanksMainFrame() {
		super("T*A*N*K*S");
		mainPanel = new JPanel();
		wins = 0;
		losses = 0;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPanel(new BasicMenu(this));
		//this.add(mainPanel);
	}
	
	public int getWins() {
		return wins;
	}
	
	public int getLosses() {
		return losses;
	}
	
	public void win(boolean win) {
		if(win)
			wins++;
		else
			losses++;
	}
	
	public void setPanel(JPanel panel) {
		setVisible(false);
		this.remove(mainPanel);
		mainPanel = panel;
		this.add(mainPanel);
		setResizable(true);		
		pack();
		setResizable(false);
		
		setVisible(true);
		mainPanel.setFocusable(true);
		mainPanel.requestFocus();
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		
		new SplashScreen();
	}

}
