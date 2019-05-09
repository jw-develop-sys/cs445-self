package c2s3e7;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestFindPairSum {

    /**
     * Test if a given FPSResult contains the given indices.
     */
    public void found(FindPairSum.FPSResult result, int i, int j) {
        assertTrue(result != null);
        assertTrue(result.i == i && result.j == j);
    }

    // Test cases start here

    @Test
    public void endPoints() {
        found(FindPairSum.findPairSum(new int[] { 4, 5, 2, 3, 8, 9} , 11), 2, 9);
    }

    @Test
    public void notAtAll() {
        assertEquals(null, FindPairSum.findPairSum(new int[] { 8, 3, 17, 51, 22, 7} , 33));
    }

    
}
