package geo;
/**
 * ConnectListener.java
 *
 * ActionListener for the connect-the-dots program.
 *
 * @author Thomas VanDrunen
 * CSCI 235, Wheaton College, Fall 2008
 * In-class example
 * Dec 2, 2008
 */

import java.awt.event.*;

import hmgraphics.PaintPanel;

public class ConnectListener implements ActionListener {

    /**
     * The painter
     */
    private DotConnector dc;

    /**
     * The panel being painted
     */
    private PaintPanel panel;

    /**
     * Plain old constructor
     */
    public ConnectListener(DotConnector dc, PaintPanel panel) {
        this.dc = dc;
        this.panel = panel;
    }

    /**
     * Tell the painter to make one more line, and refresh the
     * panel.
     */
    public void actionPerformed(ActionEvent ae) {
        dc.increment();
        panel.repaint();
    }

}
