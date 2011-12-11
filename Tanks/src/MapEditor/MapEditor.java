package MapEditor;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class MapEditor extends JFrame{
	static EditorPane ep = new EditorPane();
	
	/**
	 * A constructor for a Map Editor
	 * 
	 * Creates your basic Map Editor
	 */
	public MapEditor(){
		super("Map Editor");
		add(ep);
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new OptionPane(ep);
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		
		new MapEditor();		
	}
}
