package MapEditor;

import gameModel.Actor;
import gameModel.DrawObject;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
	private ArrayList<String> savingList = new ArrayList<String>();
	private ImageIcon iii;
	private JTextField textF;
	private String saveName;

	public EditorPane() {
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
		((Graphics2D) g).drawImage(img, 0, 0, null);
		for (int i = 0; i < drawList.size(); i++)
			((Graphics2D) g).drawImage(drawList.get(i), drawXList.get(i),
					drawYList.get(i), null);
		Toolkit.getDefaultToolkit().sync();
	}

	public void clickListen(String imgName, JTextField remains) {
		requestFocus();
		iii = new ImageIcon(this.getClass().getResource(imgName));
		textF = remains;
		saveName = imgName;
	}

	public void writeToFile(String mapName, int tRemains, int oRemains) {
		try {
			FileWriter fstream = new FileWriter(mapName);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("" + tRemains + " " + oRemains);
			out.newLine();
			for (int i = 0; i < drawList.size(); i++) {
				Image im = drawList.get(i);
				int w = im.getWidth(null) / 2;
				int h = im.getHeight(null) / 2;
				out.write(savingList.get(i) + " " + (drawXList.get(i) + w)
						+ " " + (drawYList.get(i) + h));
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}

	}

	public void readFromFile(String mapName, JTextField tR, JTextField oR) {
		int space1 = 0, space2 = 0;
		int count = 0;

		try {
			FileInputStream fstream = new FileInputStream(mapName);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			savingList.clear();
			drawList.clear();
			drawXList.clear();
			drawYList.clear();

			strLine = br.readLine();
			for (int j = 0; j < strLine.length(); j++) {
				if (strLine.charAt(j) == ' ') {
					tR.setText(strLine.substring(0, j));
					oR.setText(strLine.substring(j + 1, strLine.length()));
				}
			}

			while ((strLine = br.readLine()) != null) {
				for (int i = 0; i < strLine.length(); i++) {
					if (strLine.charAt(i) == ' ' && count == 0) {
						space1 = i;
						count++;
					}
					if (strLine.charAt(i) == ' ' && count == 1)
						space2 = i;
				}
				iii = new ImageIcon(this.getClass().getResource(
						strLine.substring(0, space1)));
				savingList.add(strLine.substring(0, space1));
				Image im = iii.getImage();
				int x = Integer.parseInt(strLine.substring(space1 + 1, space2))
						- im.getWidth(null) / 2;
				int y = Integer.parseInt(strLine.substring(space2 + 1,
						strLine.length()))
						- im.getHeight(null) / 2;
				drawXList.add(x);
				drawYList.add(y);
				drawList.add(iii.getImage());
				count = 0;
			}
			in.close();
			repaint();

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (iii != null) {
			if (Integer.parseInt(textF.getText()) > 0) {
				textF.setText("" + (Integer.parseInt(textF.getText()) - 1));
				savingList.add(saveName);
				Image im = iii.getImage();
				int w = im.getWidth(null);
				int h = im.getHeight(null);
				int x = w * (e.getX() / w);
				// x -= w/2;
				int y = h * (e.getY() / h);
				// y -= h/2;
				drawList.add(im);
				drawXList.add(x);
				drawYList.add(y);
				repaint();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
