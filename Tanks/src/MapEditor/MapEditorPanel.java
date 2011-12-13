package MapEditor;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;

public class MapEditorPanel extends JPanel {
	
	public MapEditorPanel() {
		super(null);
		setSize(1100, 600);
		EditorPane ep = new EditorPane();
		OptionPane op = new OptionPane(ep);
		add(ep, 0, 0);
		add(op, 800, 0);
		
		sets(ep, 0, 0, 800, 600);
		sets(op, 800, 0, 300, 600);
		setPreferredSize(new Dimension(1100, 600));
		setVisible(true);
	}
	
	public void sets(Component o, int x, int y, int h, int w){
		this.add(o);
		o.setVisible(true);
		o.setLocation(x, y);
		o.setSize(h, w);
	}

}
