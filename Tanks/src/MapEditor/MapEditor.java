package MapEditor;

import javax.swing.JFrame;

public class MapEditor extends JFrame{
	static EditorPane ep = new EditorPane();
	
	public MapEditor(){
		super("Map Editor");
		add(ep);
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		new MapEditor();		
		new OptionPane(ep);
	}
}
