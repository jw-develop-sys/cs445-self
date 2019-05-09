package geo;
/**
 * ConnectTheDots.java
 *
 * Program to draw dots and then connect them with lines.
 *
 * @author Thomas VanDrunen
 * CSCI 235, Wheaton College, Fall 2008
 * In-class example
 * Dec 2, 2008
 */

import java.awt.*;
import javax.swing.*;

import hmgraphics.PaintPanel;

public class ConnectTheDots {

        public static void main(String[] args) {
            PaintPanel p = new PaintPanel(400, 400);   // area for graphics
            JFrame window = new JFrame("Connect the dots");  // window itself
            window.setSize(425, 475);
            window.setLocation(100, 100);
            window.setLayout(new FlowLayout());

            window.add(p);

            // The object which will do the drawing
            DotConnector s = new DotConnector(10);
            p.setPainter(s);

            // Allow the user to indicate another line should be drawn
            JButton next = new JButton("Next");
            next.addActionListener(new ConnectListener(s, p));
            window.add(next);

            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setVisible(true);

    }

}
