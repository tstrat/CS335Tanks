package gui;

import javax.swing.JFrame;

public class BasicFrame extends JFrame {

	public BasicFrame() {
		super("Tanks basic display");
		
		add(new TanksDisplay());
		
		pack();
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		BasicFrame frame = new BasicFrame();
	}
	
}
