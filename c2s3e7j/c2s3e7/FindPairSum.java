package c2s3e7;

/**
 * CLRS 2.3-7
 * Thomas VanDrunen
 * CSCI 445
 * July 21, 2016
 */
public class FindPairSum {

    

    /**
     * Class to represent the result of findPairSum.
     * Naturally it would return a tuble, but this is Java, so let's
     * make our own class.
     */
    public static class FPSResult { 
        public FPSResult(int i, int j) { this.i = i; this.j = j; }
        public final int i;
        public final int j;
    }

    /**
     * Find a pair in a set that sums to a given number, if any.
     * The problem said "set", which doesn't fully specify the problem.
     * We need to assume that set can be iterated over in linear time.
     * So I'm going to assume it's an array (but not that it is sorted).
     * I'm also assuming it's ok to rearrange the array.
     * @param s The set (array) of numbers
     * @param x The sum we want to find two addends in the set of
     * @return A tuple with the values in the set that sum to x, or null if none
     * exists.
     * Note that we want to return the values, not indices. Since we're
     * rearranging the set, the indices will change.
     */
    public static FPSResult findPairSum(int[] s, int x) {
         throw new UnsupportedOperationException(); 
    }
}