package hmgraphics;
/**
 * Painter.java
 *
 * Interface for an object that will handle the painting
 * of a paintable GUI component.
 *
 * @author Thomas VanDrunen
 * CSCI 235, Wheaton College, Fall 2008
 * Nov 20, 2008
 */
import java.awt.Graphics;

public interface Painter {

    /**
     * Update the display using the given graphics
     * object.
     * @param g The graphics object to manipulate
     */
    public void paint(Graphics g);

}
