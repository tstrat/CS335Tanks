package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import MapEditor.MapEditorPanel;

import server.TanksServer;

@SuppressWarnings("serial")
public class BasicMenu extends JPanel {
	private ImageIcon bgIcon = new ImageIcon(this.getClass().getResource("okay.png"));
	private JLabel bg  = new JLabel(bgIcon);
	private JButton singlePlay = new JButton("SINGLEPLAYER");
	private JButton multiPlay = new JButton("MULTIPLAYER");
	private JButton mapEditing = new JButton("MAP EDITOR");
	private JButton exitG = new JButton ("EXIT GAME");
	private TanksMainFrame frame;
	
	public BasicMenu(TanksMainFrame f) {
		//add(mainP);
		super();
		

		setBackground(Color.YELLOW);
		frame = f;
		this.setPreferredSize(new Dimension(800, 720));
		this.setLayout(null);
		setup();
		setVisible(true);
	}

	public void setup(){

		
		sets(singlePlay, 200, 330, 400, 50);
		sets(multiPlay, 200, 420, 400, 50);
		sets(mapEditing, 200, 510, 400, 50);
		sets(exitG, 200, 600, 400, 50);
		sets(bg, 0, 0, 820, 820);
		
		singlePlay.addActionListener(new SinglePlayActionListener());
		multiPlay.addActionListener(new MultiPlayActionListener());
		mapEditing.addActionListener(new MapEditActionListener());
		exitG.addActionListener(new ExitActionListener());
	}	
		
	
	public void sets(Component o, int x, int y, int w, int h){
		this.add(o);
		o.setFont(new Font("SansSerif", Font.PLAIN, 18));
		o.setVisible(true);
		o.setLocation(x, y);
		o.setSize(w, h);
	}
	
	
	private class SinglePlayActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new MapSelectionFrame(false, frame);			
		}
		
	}
	
	
	private class MultiPlayActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame hostjoin = new JFrame("Host or Join");

			Object[] options = {"Host", "Join"};
			int n = JOptionPane.showOptionDialog(hostjoin, "Would you like to Host or Join a game?", "Host/Join", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if(n == 0) {
				new TanksServer();	
				new MapSelectionFrame(true, frame);
			} else {			
				
				String joinIp = JOptionPane.showInputDialog("Enter IP address to connect to.");
				new JoinGameFrame(joinIp, frame);			
			}
			
		}
		
	}
	
	private class MapEditActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//dispose();
			//new MapEditor();
			frame.setPanel(new MapEditorPanel());
		}
		
	}
	
	private class ExitActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
		
	}
	
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		
		new SplashScreen();
	}
	
	
}
