package c17p2;

import java.util.Iterator;

/**
 * DynamicBinarySearchSet
 * 
 * An implementation of a dynamic set using a sequence of
 * sorted arrays, each a length of a power of two.
 * 
 * @author Thomas VanDrunen
 * CSCI 445, Wheaton College
 * Oct 9, 2018
 * @param <E> The base type for keys of this set.
 */
public class DynamicBinarySearchSet<E extends Comparable<E>> implements Set<E> {
    
    /**
     * The number of keys currently in the set
     */
    private byte n;
    
    
    /**
     * The arrays containing the keys
     */
    private E[][] internal;

    // Class invariants:
    //  - n is the number of keys in the set
    //  - internal.length == 8
    //  - for all i in [0, 8), internal,
    //        (internal[i].length == 0 and the ith bit of n is 0), or
    //        (internal[i].length = 2^i and internal[i] is sorted and 
    //            the ith bit of n is 1)
    
    
    /**
     * Class to encapsulate a result from the helper method search(),
     * representing the coordinates (which array in internal and
     * where in that array) where a searched key is.
     * If only Java had tuples...
     */
    private static class Location {
        /**
         * The array in which the key is located, as an index into internal
         */
        final int i;
        /**
         * The position in that array where the key is located, as an index
         * into internal[i]
         */
        final int j;
        Location(int i, int j) { this.i = i; this.j = j; }
        public String toString() { return "(" + i + ", " + j + ")"; }
    }
    

    /**
     * Plain constructor for an initially empty set
     */
    @SuppressWarnings("unchecked")
    public DynamicBinarySearchSet() {
        internal = (E[][]) new Comparable[8][];
        n = 0;
    }
    
    /**
     * Helper method to find the location of an key, if it exists.
     * @param key The key to find
     * @return The location of that key, if it exists, or
     * null otherwise
     */
    private Location search(E key) {
        Location result = null;
        // invariant: (result == null and for all k in [0, i), internal[k] doesn't contain key)
        //            or internal[result.i][result.j].equals(key)
        for (int i = 0; result == null && i < 8; i++) {
             // Finish this in part a
        }
        return result;
    }
    
    /**
     * Helper method to allocate an array of the key type.
     * Basically this wraps the array allocation in a cast
     * and a "SuppressWarnings" annotation, to deal with the
     * generic-array problem.
     * @param size The size of the array to allocate
     * @return The newly allocated array
     */
    @SuppressWarnings("unchecked")
    private E[] aalloc(int size) {
        return (E[]) new Comparable[size];
    }

    /**
     * (Suggested) helper method to merge two equal-sized, sorted arrays into
     * a new sorted array.
     * @param a An array of the key type, sorted and the same size as b
     * @param b An array of the key type, sorted and the same size as a
     * @return A new array that has all the elements of a and b, sorted
     */
    private E[] merge(E[] a, E[] b) {
        assert a.length == b.length;
         throw new UnsupportedOperationException();
    }
    
    /**
     * Add an item to the set. (Do nothing if the item is 
     * already there.)
     * @param item The item to add
     */
    public void add(E item) {
        if (search(item) == null) {
             throw new UnsupportedOperationException();
        }
    }

    /**
     * Does this set contain the item?
     * @param item The item to check
     * @return True if the item is in the set, false otherwise
     */
    public boolean contains(E item) {
        return search(item) != null;
    }

    /**
     * Remove an item from the set, if it's there;
     * ignore otherwise.
     * @param item The item to remove
     */
    public void remove(E item) {
        throw new UnsupportedOperationException();
    }


    /**
     * The number of items in the set
     * @return The number of items.
     */
    public int size() {
        return n;
        // If you can believe it, I actually wrote the code below 
        // (which works) before I realized that it just re-computes n.
        // I guess I was up too late grading your tests...  -TVD
        /*
        int result = 0;
        for (int i = 0; i < 8; i++) {
            if ((n & (1 << i)) != 0) result += (1 << i);
        }
        return result;
        */
    }

    
    /**
     * Is the set empty?
     * @return True if the set is empty, false otherwise.
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Provide a way to access the keys in this set
     * @return An iterator over the keys in this set.
     */
    public Iterator<E> iterator() {
         throw new UnsupportedOperationException();
    }

    /**
     * Produce a string representation of this set, which
     * vertical bars indicating the boundaries between the individual arrays
     */
    public String toString() {
        String toReturn ="[";
        for (int i = 0; i < 8; i++) {
            if ((n & (1 << i)) != 0)
                for (E x : internal[i])
                    toReturn += x + ",";
            if (toReturn.charAt(toReturn.length()-1) == ',')
                toReturn = toReturn.substring(0, toReturn.length()-1);
            if (i < 7)
                toReturn += "|";
        }
        return toReturn.substring(0, toReturn.length()-1) + "]";
    }

}
