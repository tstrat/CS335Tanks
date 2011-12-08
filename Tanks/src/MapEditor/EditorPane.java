package MapEditor;

import gameModel.Actor;
import gameModel.DrawObject;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;

public class EditorPane extends JPanel implements MouseInputListener {
	private Image img;
	private ArrayList<Image> drawList = new ArrayList<Image>();
	private ArrayList<Integer> drawXList = new ArrayList<Integer>();
	private ArrayList<Integer> drawYList = new ArrayList<Integer>();
	private boolean clicked = false, drew = false;
	private ImageIcon iii;
	private JTextField textF;
	
	public EditorPane(){
		super(true);
		setPreferredSize(new Dimension(800, 600));
		ImageIcon ii = new ImageIcon(this.getClass().getResource("map.png"));
		img = ii.getImage();
		
		addMouseListener(this);
		setFocusable(true);
		requestFocus();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		((Graphics2D)g).drawImage(img, 0, 0, null);
		for(int i = 0; i < drawList.size(); i++)
			((Graphics2D)g).drawImage(drawList.get(i), drawXList.get(i), drawYList.get(i), null);
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void clickListen(String imgName, JTextField remains){
		requestFocus();
		clicked = true;
		iii = new ImageIcon(this.getClass().getResource(imgName));
		textF = remains;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(clicked){
			drawList.add(iii.getImage());
			drawXList.add(e.getX());
			drawYList.add(e.getY());
			repaint();
			textF.setText("" + (Integer.parseInt(textF.getText()) - 1));
			clicked = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}
}
