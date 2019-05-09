package geo;

/**
 * Point
 * 
 * Class to model segments for computational geometry.
 * 
 * @author Thomas VanDrunen
 * CSCI 445, Wheaton College
 * Oct 20, 2016
 */
public class Segment {

    /**
     * The endpoints of this segment.
     */
    private final Point start, stop;

    /**
     * Plain constructor
     */
    public Segment(Point start, Point stop) {
        this.start = start;
        this.stop = stop;
    }

    
    /**
     * A child class specifically for vectors just because it's
     * too easy not to do.
     */
    public static class Vector extends Segment {
        public Vector(Point stop) {
            super(Point.ORIGIN, stop);
        }
    }

    /**
     * Translate this segment to an equivalent segment starting
     * from the origin.
     * @return A vector with the same magnitude and direction
     * as this segment.
     */
    public Vector vectorize() {
        return new Vector(stop.subtract(start));
    }
    
    
    /**
     * Compute the direction of a point from the end of this segment.
     * Note that a negative value means we turn
     * left (counterclockwise) and a positive means we turn right
     * (clockwise). See CLRS pg 1017 and the DIRECTION procedure
     * on pg 1018.
     * @param p0 first endpoint of the segment (pi)
     * @param p1 second endpoint of the segment (pj)
     * @param p2 other point (pk)
     * @return The direction we turn to get to p2 from segment p0p1
     */
   public int direction(Point p) {
        return p.subtract(start).cross(stop.subtract(start));
    }

   /**
    * Compute whether this and another segment intersect.
    * If they do, it is because either they straddle 
    * each other or one of their endpoints is on the other line.
    * @param other The other segment
    * @return Whether these segments intersect or not
    */
    public boolean intersects(Segment other) {
        int[] directions = new int[] {
                other.direction(start), // how other turns toward our start
                other.direction(stop),  // how other turns toward our stop
                direction(other.start),  // how we turn toward other's start
                direction(other.stop)  // how we turn toward other's stop
        };
        return  // -- "normal" case : straddling -- 
                // start and stop are on opposite sides of other
                (((directions[0] > 0 && directions[1] < 0) ||
                        (directions[0] < 0 && directions[1] > 0)) &&
                // other's start and stop are on opposite sides of this
                ((directions[2] > 0 && directions[3] < 0) ||
                        (directions[2] < 0 && directions[3] > 0))) ||
                
                // -- "boundary" cases: end point of one segment is on the other
                (directions[0] == 0 && other.containsPoint(start)) ||
                (directions[1] == 0 && other.containsPoint(stop)) ||
                (directions[2] == 0 && containsPoint(other.start)) ||
                (directions[3] == 0 && containsPoint(other.stop));
    }
    
    /**
     * Test to see whether this segment contains a given point.
     * This is called ON-SEGMENT in CLRS, pg 1018.
     * @param p The other point
     * @return Whether or not the given point is on this segment
     */
    public boolean containsPoint(Point p) {
        return Math.min(start.x, stop.x) <= p.x 
                && p.x <= Math.max(start.x, stop.x)
                && Math.min(start.y, stop.y) <= p.y 
                && p.y <= Math.max(start.y, stop.y);
    }
    

}
