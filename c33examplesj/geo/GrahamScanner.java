package geo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import hmgraphics.PaintPanel;
import hmgraphics.Painter;

/**
 * GrahamScanner
 * 
 * Class to encapsulate the Graham's Scan algorithm for computing
 * the convex hull of a set of points, in conjunction with a graphical
 * visualization.
 * 
 * Specifically, an instance of this class takes a set of points as a
 * parameter to its constructor. Then it identifies and removes successive
 * convex hull "layers" of the set of points. That is, when the convex hull
 * of the (current) set of points has been identified, those points are 
 * removed from the set, and it then finds the convex hull of the remaining
 * points.
 * 
 * The user indicates when a step is to be taken by pressing a button,
 * communicated to an instance of this class by an action performed method.
 * 
 * @author Thomas VanDrunen
 * CSCI 445, Wheaton College
 * Oct 19, 2016
 */
public class GrahamScanner implements Painter, ActionListener {

    /**
     * Local implementation of a stack for use in Graham's Scan.
     * Specifically, this stack has the following unconventional
     * features: retrieving the item one down from the top of the
     * stack (the "penult"); retrieving the bottom element; and
     * iterating through the elements currently in the stack 
     * (bottom to top) without removing. Also, this *lacks* an
     * empty operation, since the Graham Scan algorithm never needs
     * to check that the stack is empty (or full, for that matter).
     */
    private class GrahamStack implements Iterable<Point> {
        /**
         * Internal representation of the stack, with 0 being
         * the bottom of the stack; this also represents the
         * current guess at the hull, and, when the algorithm
         * is finished, this is the actual hull. 
         */
        Point[] hull;
        
        /**
         * Index where the top element is, or -1 if the stack is 
         * empty.
         */
        int top;

        /**
         * Plain constructor. Capacity is the total number of 
         * points (on this round).
         */
        GrahamStack() {
            hull = new Point[allPoints.size()];
            top = -1;
        }

        /**
         * Push a point.
         */
        void push(Point p) {
            // If the algorithm is correct, this stack should never be full
            assert top < hull.length - 1;
            hull[++top] = p;
        }
        
        /**
         * Pop a point.
         */
        Point pop() {
            // If the algorithm is correct, this method will never be called
            // when the stack is empty
            assert top >= 0;
            return hull[top--];
        }

        /**
         * Retrieve the top point.
         */
        Point top() { 
            // If the algorithm is correct, this method will never be called
            // when the stack is empty
            assert top >= 0;
            return hull[top];
        }
        
        /**
         * Retrieve the second-to-top point.
         * (Calling it "penult" suggests that the "top" of the stack is the
         * "end" of the stack. Since the top is the last one in, the penult
         * is the second-to-last one in.)
         */
        Point penult() {
            // If the algorithm is correct, this method will never be called
            // when the stack has fewer than two elements
            assert top > 0;
            return hull[top-1];
        }      
        
        /**
         * Retrieve the bottom point, ie the one that was first
         * put in the stack. This is also the starting point of the 
         * sequences that constitutes the hull.
         * @return
         */
        Point bottom() { 
            // If the algorithm is correct, this method will never be called
            // when the stack is empty
            assert top >= 0;
            return hull[0];
        }
        
        /**
         * Iterate through the points in this stack/hull from bottom
         * to top.
         */
        public Iterator<Point> iterator() {
            return new Iterator<Point>() {
                int i = 0;
                public boolean hasNext() {
                    return i <= top;
                }
                public Point next() {
                    if (! hasNext())
                        throw new NoSuchElementException();
                    return hull[i++];
                }
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }

    /**
     * All the points in this round of Graham's Scan.
     * The points on the hull will be removed at the end of
     * the round.
     */
    private Set<Point> allPoints;

    /**
     * Suggested instance variable: The remaining
     * points to be iterated over in counter clockwise order around
     * the starting point.
     */
    private Iterator<Point> sortedPoints;

    /**
     * The current guess at the hull.
     */
    private GrahamStack stack;

    /**
     * The GUI component on which to draw the points and hull
     */
    private PaintPanel panel;

    /**
     * Are we done with the current round? This will be set to
     * true after all the points have been considered. The real
     * reason for having this instance variable is so that a completed
     * hull will be displayed for the user before the hull points are
     * removed.
     */
    private boolean done;

    /**
     * Initialize a series of rounds of Graham's Scan
     * @param allPoints The set of points. Note that this set object
     * will be modified.
     * @param panel The GUI component to which to draw the points
     */
    public GrahamScanner(Set<Point> allPoints, PaintPanel panel) {
        this.allPoints = allPoints;
        this.panel = panel;
        reset();
    }

    /**
     * Draw the points and the current state of the round of Graham's 
     * Scan. Points are black. A finished hull is green, unfinished
     * red.
     */
    public void paint(Graphics g) {

        // Is this round finished?
        g.setColor(done? Color.GREEN : Color.RED);

        // Draw the (current state) of the scan.
        Point previous = null;
        for (Point next : stack) {
            if (previous != null)
                g.drawLine(previous.x, panel.height() - previous.y, next.x, panel.height() - next.y);
            previous = next;
        }
        
        // If we have processed all the points, then connect the
        // hull to the starting point
        if (! sortedPoints.hasNext() && previous != null) {
            g.drawLine(previous.x, panel.height() - previous.y, stack.bottom().x, panel.height() - stack.bottom().y);
        }
           
        // Draw the points
        g.setColor(Color.BLACK);
        for (Point p : allPoints)
            g.fillRect(p.x, panel.height() - p.y, 3, 3);

        // If we have processed all the points, then indicate
        // that we are done. Show as green once; reset nexttime.
        if (! sortedPoints.hasNext() && previous != null) {
            if (done) reset();
            else done = true;
        }

    }

    /**
     * Begin a round of Graham's scan. Remove any points from last time;
     * clear out the stack; find a starting point; sort the points; do any
     * other initialization is needed.
     */
    private void reset() {
        // remove any points from last time
        if (stack != null) {
            for (Point p : stack)
                allPoints.remove(p);
        }

        // clear out the stack
        ArrayList<Point> toSort = new ArrayList<Point>();
        toSort.addAll(allPoints);
        stack = new GrahamStack();

         // add code here
        
        done = false;
        
    }
    
   
    /**
     * Do one step in Graham's Scan. 
     */
    public void actionPerformed(ActionEvent e) {
        if (sortedPoints.hasNext()) {
             // add code here
        }
        panel.repaint();
    }
}
