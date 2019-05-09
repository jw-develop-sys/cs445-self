package c8p4;

import c8p4.Jug.*;

/**
 * JugSort.java
 * 
 * Class to hold public methods for sorting parallel
 * arrays of jugs.
 */
public class JugSort {

    /**
     * Pair up the red and blue jugs so that they are equal.
     * PRECONDITION: The bags of the capacities of reds is equal to
     * the bags of capacities for blues.
     * POSTCONDITION: For all i within bounds, reds[i] = blues[i]
     * NOTE: It is not required that the arrays be sorted by capacity.
     */
    public static void jugSelectionSort(Red[] reds, Blue[] blues) {
         
    }
    
    /**
     * Pair up the red and blue jugs so that they are equal.
     * PRECONDITION: The bags of the capacities of reds is equal to
     * the bags of capacities for blues.
     * POSTCONDITION: For all i within bounds, reds[i] = blues[i]
     * NOTE: It is not required that the arrays be sorted by capacity,
     * though in the natural solution they will be.
     */
    public static void jugQuickSort(Red[] reds, Blue[] blues) {
        quickSort(reds, blues, 0, reds.length);
    }

    /**
     * Pair up the red and blue jugs so that they are equal.
     * PRECONDITION (recursion invariant): The bags of the capacities of reds is equal to
     * the bags of capacities for blues in the range [start, stop).
     * POSTCONDITION: For all i in [start, stop), reds[i] = blues[i]
     * NOTE: It is not required that the arrays be sorted by capacity,
     * though in the natural solution they will be.
     */    
    private static void quickSort(Red[] reds, Blue[] blues, int start, int stop) {
        
    }
    
}
