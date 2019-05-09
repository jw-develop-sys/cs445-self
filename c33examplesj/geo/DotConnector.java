package geo;
/**
 * DotConnector.java
 *
 * Painter to draw dots and lines.
 *
 * @author Thomas VanDrunen
 * CSCI 235, Wheaton College, Fall 2008
 * In-class example
 * Dec 2, 2008
 */
import java.awt.*;
import java.util.*;

import hmgraphics.Painter;

public class DotConnector implements Painter {

    /**
     * The x and y coordinates for the dots, generated
     * at instantiation time.
     */
    private ArrayList<Point> points;
    
    /**
     * The number of lines to draw, incremented as we go
     * along.
     */
    private int lines;

    /**
     * Constructor. Generate the given number of random
     * dots, with a 10 pixel margin all around.
     * @param numDots The number of dots to draw
     */
    public DotConnector(int numDots) {
        points = new ArrayList<Point>();
        for (int i = 0; i < numDots; i++) {
            points.add(new Point(10 + (int) (380 * Math.random()),
                    10 + (int) (380 * Math.random())));
        }
        System.out.println(points);
        lines = 0;
    }

    /**
     * Draw the dots and, possibly, lines.
     * First draw all the lines-- these will be bounded by
     * the number of requested lines at this point and
     * by the number of dots. There can be at most
     * one fewer lines than dots. The draw the dots,
     * which actually are rectangles 3 pixels by 3 pixels.
     * @param g The graphic controller object
     */
    public void paint(Graphics g) {
        g.setColor(Color.RED);
        Point a = points.get(0);
        for (int i = 1; i < lines + 1 && i < points.size(); i++) {
            Point b = points.get(i);
            g.drawLine(a.x, a.y, b.x, b.y);
            a = b;
        }

        g.setColor(Color.BLACK);
        for (Point p : points)
            g.fillRect(p.x, p.y, 3, 3);

    }

    /**
     * Next time, draw one more line.
     */
    public void increment() { lines++; }
}
