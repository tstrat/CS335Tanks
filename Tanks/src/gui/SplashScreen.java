package gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SplashScreen extends JFrame {
	public SplashScreen() {
		this.setUndecorated(true);
		JPanel blah = new JPanel();
		blah.add(new JLabel(new ImageIcon(this.getClass().getResource("splash.png"))));
		add(blah);
		pack();
		setResizable(false);
		setVisible(true);
		setLocation(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.dispose();
		new BasicMenu();
	}
}
