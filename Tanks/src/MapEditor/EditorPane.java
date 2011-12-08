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
import javax.swing.event.MouseInputListener;

public class EditorPane extends JPanel implements MouseInputListener {
	private Image img;
	private ArrayList<Image> drawList = new ArrayList<Image>();
	private ArrayList<Integer> drawXList = new ArrayList<Integer>();
	private ArrayList<Integer> drawYList = new ArrayList<Integer>();
	private int mouseX, mouseY, mouseSendX, mouseSendY;
	private boolean clicked;
	
	public EditorPane(){
		super(true);
		setPreferredSize(new Dimension(800, 600));
		ImageIcon ii = new ImageIcon(this.getClass().getResource("map.png"));
		img = ii.getImage();
		
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
	
	public boolean draw(int x, int y, String imga){
		
		try {
			ImageIcon iii = new ImageIcon(this.getClass().getResource(imga));
			drawList.add(iii.getImage());
			drawXList.add(x);
			drawYList.add(y);
			repaint();
			return true;
			
		} catch (Exception e) {
			System.out.println("Something went wrong");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean clickListen(){
		clicked = false;
		addMouseListener(this);
		
		while(!clicked){
			
		}
		
		return clicked;
	}
	
	public int mouseSX(){
		return mouseSendX;
	}
	
	public int mouseSY(){
		return mouseSendY;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("LOOK MAH");
		mouseSendX = mouseX;
		mouseSendY = mouseY;
		clicked = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
}
