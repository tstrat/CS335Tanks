package MapEditor;

import gameModel.Obstacle;
import gameModel.Terrain;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OptionPane extends JFrame implements ActionListener {
	EditorPane ep;
	
	int mouseX, mouseY;
	boolean clicked = false;
	JPanel options = new JPanel();
	JLabel header = new JLabel("Editor Options");
	JLabel terrain = new JLabel("Terrain: ");
	JLabel obstacles = new JLabel("Obstacles: ");
	JLabel tR = new JLabel("Terrian Left: ");
	JLabel oR = new JLabel("Obstacles Left: ");
	JTextField terRemaining = new JTextField(4);
	JTextField obsRemaining = new JTextField(4);
	ArrayList<String> terList = new ArrayList<String>();
	ArrayList<String> obsList = new ArrayList<String>();
	ArrayList<JButton> terButList = new ArrayList<JButton>();
	ArrayList<JButton> obsButList = new ArrayList<JButton>();
	
	public OptionPane(EditorPane ep){
		this.ep = ep;
		this.setSize(300, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(800, 0);
		setUp();
		placeOptions();
		placeButtons();
	}
	
	public void setUp(){
		this.add(options);
		options.setSize(300,600);
		options.setVisible(true);
		options.setLayout(null);		

		createLists();
	}
	
	public void placeButtons(){
		for(int i = 1; i <= terList.size(); i++){
			sets(terButList.get(i-1), 20, 30 + (i * 30), 120, 25);
			terButList.get(i-1).addActionListener(this);
		}
		
		for(int i = 1; i <= obsList.size(); i++){
			sets(obsButList.get(i-1), 20, 220 + (i * 30), 120, 25);
			obsButList.get(i-1).addActionListener(this);
		}
	}
	
	public void createLists(){
		terList.add("SpikePit");
		obsList.add("Wall");
		obsList.add("TreeStump");
		obsList.add("HealingBeacon");
		obsList.add("Indestructible");
		obsList.add("TNTBarrel");
		
		for(int i = 0; i < terList.size(); i++)
			terButList.add(new JButton(terList.get(i)));
		
		for(int j = 0; j < obsList.size(); j++)
			obsButList.add(new JButton(obsList.get(j)));
	}
	
	public void placeOptions(){
		sets(header, 90, 0, 80, 30);
		sets(terrain, 20, 20, 70, 30);
		sets(tR, 150, 20, 80, 30);
		sets(terRemaining, 225, 25, 30, 20);
		terRemaining.setEditable(false);
		terRemaining.setText("5");
		sets(obstacles, 20, 220, 70, 30);
		sets(oR, 135, 220, 90, 30);
		sets(obsRemaining, 225, 225, 30, 20);
		obsRemaining.setEditable(false);
		obsRemaining.setText("10");
	}
	
	public void sets(Component o, int x, int y, int h, int w){
		options.add(o);
		o.setVisible(true);
		o.setLocation(x, y);
		o.setSize(h, w);
	}
	
	public void disableTerButs(){
		for(int i = 0; i < terList.size(); i++)
			terButList.get(i).setEnabled(false);
	}

	public void actionPerformed(ActionEvent e) {
		if(((AbstractButton) e.getSource()).getText().equals("SpikePit")){
			ep.clickListen();			
			
			int loc1 = ep.mouseSX();
			int loc2 = ep.mouseSY();
			//int loc2 = Integer.parseInt(JOptionPane.showInputDialog("Enter Y-Coord: "));
			
			boolean checker = ep.draw(loc1, loc2, "spikePit.png");
			
			int remains;
			if(checker){
				remains = Integer.parseInt(terRemaining.getText()) - 1;
				terRemaining.setText(""+ (remains));
				if(remains == 0)
					disableTerButs();
			}
				
		}
		
	}

}
