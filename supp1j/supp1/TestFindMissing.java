package supp1;

import static org.junit.Assert.assertEquals;
import static supp1.FindMissing.findMissing;

import org.junit.Test;

public class TestFindMissing {

    @Test
    public void testFirstMissing() {
        assertEquals(0, findMissing(new int[] {1,2,3,4,5}));
    }

    @Test
    public void testLastMissing() {
        assertEquals(5, findMissing(new int[] {0,1,2,3,4}));
    }
    
    
}
