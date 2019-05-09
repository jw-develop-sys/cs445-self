package c8p4;

import java.util.Random;

/**
 * Jug.java
 * 
 * Module for classes Jug.Blue and Jug.Red
 * 
 * @author Thomas VanDrunen
 * CSCI 445, Wheaton College
 * Sept 18, 2018
 */
public class Jug {

    

    /**
     * Class to model blue jugs. These are not comparable,
     * but red jugs can be compared to them.
     */
    public static class Blue {
        private final int capacity;
        private Blue(int capacity) { this.capacity = capacity; }
        public String toString() { return "B" + capacity; }
    }

    /**
     * Class to model red jugs. These are comparable
     * to blue jugs.
     */
    public static class Red implements Comparable<Blue> {
        private final int capacity;
        private Red(int capacity) { this.capacity = capacity; }
        public int compareTo(Blue o) {
            return o.capacity - capacity;
        }
        public String toString() { return "R" + capacity; }
   }

    /**
     * Medthod to fill two arrays with red and blue jugs (since
     * the constructors are private).
     */
    public static void fillArrays(Red[] reds, int[] forReds, Blue[] blues, int[] forBlues) {
        assert reds.length == blues.length;
        for (int i = 0; i < reds.length; i++) {
            reds[i] = new Red(forReds[i]);
            blues[i] = new Blue(forBlues[i]);
        }
    }

    
}
