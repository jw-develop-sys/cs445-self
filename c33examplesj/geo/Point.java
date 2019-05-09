package geo;

import java.util.Comparator;

/**
 * Point
 * 
 * Class to model points for computational geometry.
 * 
 * @author Thomas VanDrunen
 * CSCI 445, Wheaton College
 * Oct 20, 2016
 */
public class Point {

    /**
     * The coordinates. They are public final to avoid getters.
     */
    public final int x, y;

    /**
     * A point to stand for the origin (for convenience)
     */
    public static final Point ORIGIN = new Point(0, 0);

    /**
     * Plain constructor
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Make a string representation, typical math notation
     */
    public String toString() { return "(" + x + "," + y + ")"; }

    /**
     * Compare too points
     */
    public boolean equals(Object o) {
        if (! (o instanceof Point)) return false;
        else {
            Point other = (Point) o;
            return other.x == x && other.y == y;
        }
    }

    /**
     * Compute the magnitude of the vector from the origin
     * to this point
     * @return This point's magnitude as a polar coordinate
     */
    public double magnitude() {
        return Math.sqrt(x*x + y * y);
    }
    
    /**
     * Compute the polar angle of the vector from the origin
     * to this point.
     * @return This point's angle as a polar coordinate
     */
    public double polarAngle() {
        // Math.atan returns a value from -pi/2 to pi/2.
        // We want a value from 0 to 2pi.
        double original = Math.atan(((double) y)/ ((double) x));
        // If it's negative, convert
        if (original < 0)
            return original + (2 * Math.PI);
        else
            return original;
    }
    
    /**
     * Compute [the magnitude of] the cross product of this and another point,
     * the signed area of the parallelogram formed by vectors
     * from the origin two these two points. See CLRS pg 1016.
     * @param other The other point
     * @return The cross product (magnitude). This is integral because we're
     * assuming the coordinates are integral and there is no division.
     */
    public int cross(Point other) {
        return x * other.y - other.x * y;
    }

    /**
     * Test to see if this point is the origin.
     * (Alternative: equals(ORIGIN)
     * @return whether or not this point is the origin
     */
    public boolean isOrigin() { return x == 0 && y == 0; }


    
    
    /**
     * Translate a point to use another point as a relative
     * origin. See CLRS pg 1016. In their notation, p0 is this
     * and p1 is other.
     * @param other The other (p1) point/vector
     * @return other - this
     */
    public Point subtract(Point other) {
        return new Point(other.x - x, other.y-y);
    }



    /**
     * Compute the polar angle of this point with respect to
     * another point as a relative origin. See CLRS Ex 33.1-3.
     * Note that a negative result means this is "clockwise"
     * from p0, and positive means "counterclockwise."
     * @param p0 The relative origin
     * @return The angle of this point with respect to the given 
     * relative origin.
     */
    public double polarAngleWithRespectTo(Point p0) {
        return subtract(p0).polarAngle();
    }

    /**
     * Compute the direction of a point from the end of a segment.
     * Consider the segment p0p1 and the point p2. What direction do
     * we turn to get to p2? Note that a negative value means we turn
     * left (counterclockwise) and a positive means we turn right
     * (clockwise). See CLRS pg 1017 and the DIRECTION procedure
     * on pg 1018. Used by Graham's Scan, and pretty much the same
     * as Segment.direction().
     * @param p0 first endpoint of the segment (pi)
     * @param p1 second endpoint of the segment (pj)
     * @param p2 other point (pk)
     * @return The direction we turn to get to p2 from segment p0p1
     */
    public static int crossOfThree(Point p0, Point p1, Point p2) {
        return p1.subtract(p0).cross(p2.subtract(p0));
    }
    
    /**
     * Make a comparator that will sort points by polar angle from this
     * point.
     * @return The comparator
     */
    public Comparator<Point> compareAngleFromPoint() {
        return new Comparator<Point>() {
            public int compare(Point p1, Point p2) {
                return (int) (100000 * (p1.polarAngleWithRespectTo(Point.this) 
                                - p2.polarAngleWithRespectTo(Point.this)));
            }
        };
    }
    
}
