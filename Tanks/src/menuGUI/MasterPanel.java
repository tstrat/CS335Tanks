package menuGUI;


import javax.swing.JPanel;

/**
 * A KeyEventedPanel is a JPanel that can be placed inside of any JFrame or
 * other JPanel. It also implements the KeyListener interface so that keyboard
 * input can be used to the control the model and all keyboard input goes
 * through the MasterView. It also implements the Observer interface so that the
 * model can update to this view.
 * 
 * @author Cody Mingus
 * 
 */
@SuppressWarnings("serial")
public abstract class MasterPanel extends JPanel {

	protected TanksFrame t;

	/**
	 * Creates a new KeyEventedPanel with a reference to the TanksFrame.
	 * 
	 * @param t
	 *            the TanksFrame
	 */
	public MasterPanel(TanksFrame t) {
		super(true);  // It is double buffered.
		this.t = t;
	}

}
