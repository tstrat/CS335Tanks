package MapEditor;

import gameModel.Obstacle;
import gameModel.Terrain;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OptionPane extends JPanel implements ActionListener {
	EditorPane ep;
	
	int mouseX, mouseY;
	boolean clicked = false;
	JPanel options = new JPanel();
	JLabel header = new JLabel("Editor Options");
	JLabel terrain = new JLabel("Terrain: ");
	JLabel obstacles = new JLabel("Obstacles: ");
	JLabel tanks = new JLabel("Tanks: ");
	JLabel tR = new JLabel("Terrian Left: ");
	JLabel oR = new JLabel("Obstacles Left: ");
	JTextField terRemaining = new JTextField(4);
	JTextField obsRemaining = new JTextField(4);
	ArrayList<String> terList = new ArrayList<String>();
	ArrayList<String> obsList = new ArrayList<String>();
	ArrayList<String> tanksList = new ArrayList<String>();
	ArrayList<JButton> terButList = new ArrayList<JButton>();
	ArrayList<JButton> obsButList = new ArrayList<JButton>();
	ArrayList<JButton> tanksButList = new ArrayList<JButton>();
	JButton save = new JButton("Save");
	JButton load = new JButton("Load");
	JButton undo = new JButton("Undo");
	
	/**
	 * Default OptionPane constructor. Makes a standard OptionPane
	 * 
	 * @param ep - The Editor Pane, the OptionPane MUST know about this
	 */
	public OptionPane(EditorPane ep){
		super();
		this.ep = ep;
		this.setPreferredSize(new Dimension(300, 600));
		this.setVisible(true);
		setUp();
		placeOptions();
		placeButtons();
		save.addActionListener(this);
		load.addActionListener(this);
		undo.addActionListener(this);
	}
	
	/**
	 * Sets up the OptionPane (panels, buttons, etc)
	 */
	public void setUp(){
		this.setSize(300,600);
		this.setVisible(true);
		this.setLayout(null);		

		createLists();
	}
	
	/**
	 * Places the buttons and adds action listeners
	 */
	public void placeButtons(){
		for(int i = 1; i <= terList.size(); i++){
			sets(terButList.get(i-1), 20, 30 + (i * 30), 120, 25);
			terButList.get(i-1).addActionListener(this);
		}
		
		for(int i = 1; i <= obsList.size(); i++){
			sets(obsButList.get(i-1), 20, 160 + (i * 30), 120, 25);
			obsButList.get(i-1).addActionListener(this);
		}
		
		for(int i = 1; i <= tanksList.size(); i++){
			sets(tanksButList.get(i-1), 20, 325 + (i * 30), 120, 25);
			tanksButList.get(i-1).addActionListener(this);
		}
	}
	
	/**
	 * Creates the lists for button making
	 */
	public void createLists(){
		terList.add("SpikePit");
		terList.add("MudPatch");
		terList.add("SpeedPatch");
		obsList.add("Wall");
		obsList.add("TreeStump");
		obsList.add("HealingBeacon");
		obsList.add("Indestructible");
		obsList.add("TNTBarrel");
		tanksList.add("Player 1 Tank");
		tanksList.add("Player 2 Tank");
		tanksList.add("Player 3 Tank");
		tanksList.add("Player 4 Tank");
		
		for(int i = 0; i < terList.size(); i++)
			terButList.add(new JButton(terList.get(i)));
		
		for(int j = 0; j < obsList.size(); j++)
			obsButList.add(new JButton(obsList.get(j)));
		
		for(int j = 0; j < tanksList.size(); j++)
			tanksButList.add(new JButton(tanksList.get(j)));
	}
	
	/**
	 * Sets up remainder of the panel
	 */
	public void placeOptions(){
		sets(header, 90, 0, 80, 30);
		sets(terrain, 20, 20, 70, 30);
		sets(tR, 150, 20, 80, 30);
		sets(terRemaining, 225, 25, 30, 20);
		terRemaining.setEditable(false);
		terRemaining.setText("50");
		sets(obstacles, 20, 160, 70, 30);
		sets(oR, 135, 160, 90, 30);
		sets(obsRemaining, 225, 165, 30, 20);
		obsRemaining.setEditable(false);
		obsRemaining.setText("100");
		sets(tanks, 20, 330, 70, 30);
		sets(save, 50, 520, 80, 25);
		sets(load, 150, 520, 80, 25);
		sets(undo, 100, 485, 80, 25);
	}
	
	/**
	 * Sets method to make setting easier and quicker when adding new things
	 * 
	 * @param o - The thing to add to the panel
	 * @param x - It's x location
	 * @param y - It's y location
	 * @param h - It's height
	 * @param w - It's width
	 */
	public void sets(Component o, int x, int y, int h, int w){
		this.add(o);
		o.setVisible(true);
		o.setLocation(x, y);
		o.setSize(h, w);
	}

	/**
	 * When a button is press call correct method in
	 * Editor pane depending on which button.
	 * If save or load, pop up a dialog box asking for
	 * name of map.
	 */
	public void actionPerformed(ActionEvent e) {
		if(((AbstractButton) e.getSource()).getText().equals("SpikePit")){
			ep.clickListen("spikePit.png", terRemaining);				
		}
		
		if(((AbstractButton) e.getSource()).getText().equals("MudPatch")){
			ep.clickListen("mud.png", terRemaining);				
		}
		
		if(((AbstractButton) e.getSource()).getText().equals("Player 1 Tank")){
			ep.clickListenTank("P1.png", tanksButList.get(0));				
		}
		
		if(((AbstractButton) e.getSource()).getText().equals("Player 2 Tank")){
			ep.clickListenTank("P2.png", tanksButList.get(1));			
		}
		
		if(((AbstractButton) e.getSource()).getText().equals("Player 3 Tank")){
			ep.clickListenTank("P3.png", tanksButList.get(2));			
		}
		
		if(((AbstractButton) e.getSource()).getText().equals("Player 4 Tank")){
			ep.clickListenTank("P4.png", tanksButList.get(3));				
		}
		
		if(((AbstractButton) e.getSource()).getText().equals("SpeedPatch")){
			ep.clickListen("speedPatch.png", terRemaining);				
		}
		
		if(((AbstractButton) e.getSource()).getText().equals("TreeStump")){
			ep.clickListen("TreeStump.png", obsRemaining);			
		}
		
		if(((AbstractButton) e.getSource()).getText().equals("HealingBeacon")){
			ep.clickListen("HealingBeacon.png", obsRemaining);				
		}
		
		if(((AbstractButton) e.getSource()).getText().equals("Wall")){
			ep.clickListen("wall2.png", obsRemaining);	
		}
		
		if(((AbstractButton) e.getSource()).getText().equals("Indestructible")){
			ep.clickListen("Indestructible.png", obsRemaining);	
		}
		
		if(((AbstractButton) e.getSource()).getText().equals("TNTBarrel")){
			ep.clickListen("TNT.png", obsRemaining);	
		}
		
		if(((AbstractButton) e.getSource()).getText().equals("Save")){
			String fName = JOptionPane.showInputDialog("Enter in Map name to save:");
			ep.writeToFile(fName + ".txt", Integer.parseInt(terRemaining.getText()), Integer.parseInt(obsRemaining.getText()));
		}
		
		if(((AbstractButton) e.getSource()).getText().equals("Load")){
			for(JButton j: tanksButList)
				j.setEnabled(true);
			String fName = JOptionPane.showInputDialog("Enter in Map name to load:");
			ep.readFromFile(fName + ".txt", terRemaining, obsRemaining);
		}
		
		if(((AbstractButton) e.getSource()).getText().equals("Undo")){
			ep.undo();
		}
		
	}

}
