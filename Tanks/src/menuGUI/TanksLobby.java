//TODO: add an area to see what players are connected, create a way to load all saved maps and switch between them.  Perhaps add a drop down box to switch to a specific map?

package menuGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Lobby.java
 *
 * Created on Dec 4, 2011, 3:29:03 PM
 */
/**
 *
 * @author Sean
 */
public class TanksLobby extends MasterPanel {

    /** Creates new form Lobby */
    public TanksLobby(TanksFrame t) {
    	super(t);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        previous = new javax.swing.JButton();
        next = new javax.swing.JButton();
        mainMenu = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(800, 600));

        previous.setText("Previous");
        previous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousActionPerformed(evt);
            }
        });

        next.setText("Next");

        mainMenu.setText("Main Menu");
        mainMenu.addActionListener(new mainMenuListener());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(previous)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 648, Short.MAX_VALUE)
                        .addComponent(next)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(mainMenu)
                        .addGap(354, 354, 354))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(297, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(previous)
                    .addComponent(next))
                .addGap(246, 246, 246)
                .addComponent(mainMenu)
                .addContainerGap())
        );
    }// </editor-fold>

private void previousActionPerformed(java.awt.event.ActionEvent evt) {
// TODO add your handling code here:
}

    // Variables declaration - do not modify
    private javax.swing.JButton mainMenu;
    private javax.swing.JButton next;
    private javax.swing.JButton previous;
    // End of variables declaration
   
    
    private class mainMenuListener implements ActionListener
    {
    	public void actionPerformed(ActionEvent arg0)
    	{
    		t.changeViews(Views.MAIN);
    	}
    }
}


