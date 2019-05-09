package c8p4;

import static org.junit.Assert.*;

import org.junit.Test;

import c8p4.Jug.*;

public abstract class TestJugSort {

    
    
    @Test
    public void testSmall() {
        runTest(new int[]{18, 39, 75, 16, 22, 20, 59, 78, 89, 70}, 
                new int[]{70, 18, 39, 75, 20, 78, 59, 89, 16, 22});
    }
    
    @Test
    public void testMed() {
        runTest(new int[]{28, 57, 98, 6, 2, 89, 86, 48, 29, 91, 72, 99, 45, 50, 99, 26, 51, 34, 40, 51, 28, 51, 63, 61, 82, 65, 10, 86, 35, 59}, 
                new int[]{59, 28, 86, 89, 29, 61, 63, 91, 40, 2, 72, 99, 10, 28, 99, 6, 26, 51, 82, 50, 86, 51, 45, 51, 34, 48, 98, 57, 65, 35});
    }
 
    
    /**
     * Test to see if two arrays, one or red jugs the other of blue jugs, are
     * aligned so that for all i in range, reds[i] and blues[i] hold the same
     * amount of water. The arrays do not need to be sorted.
     * @param reds
     * @param blues
     * @return true if the arrays are aligned, false otherwise
     */
    public static boolean checkArrays(Jug.Red[] reds, Jug.Blue[] blues) {
        boolean ok = true;
        for (int i = 0; i < reds.length && ok; i++)
            ok &= reds[i].compareTo(blues[i]) == 0;
        return ok;
    }

    /**
     * Run a sorting algorithm (determined by a child class) on a given
     * pair of red- and blue-jug arrays.
     * @param forReds The array of red jugs
     * @param forBlues The array of blue jugs
     */
    private void runTest(int[] forReds, int[] forBlues) {
        assert forReds.length == forBlues.length;
        Red[] reds = new Red[forReds.length];
        Blue[] blues = new Blue[forBlues.length];
        Jug.fillArrays(reds, forReds, blues, forBlues);
        sort(reds, blues);
        assertTrue(checkArrays(reds, blues));
    }

    
    
    protected abstract void sort(Jug.Red[] reds, Jug.Blue[] blues); 
}
