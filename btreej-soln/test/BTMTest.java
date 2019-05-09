package test;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import adt.Map;
import impl.BTreeMap;

public class BTMTest extends MapTest {

    protected void reset() {
        testMap = new BTreeMap<String, String>(10);
    }

    
    @Test
    public void putLotsRandom() {
        Map<Integer, String> testMap = new BTreeMap<Integer, String>(10);
        boolean[] keys = new boolean[100000000];
        Random rand = new Random();
        for (int i = 0; i < 100000; i++){
            int x = rand.nextInt(100000000);
            Integer xx = new Integer(x);
            testMap.put(xx, "q");
            keys[x] = true;
        }
        for (int i = 0; i < keys.length; i++)
            if (keys[i]) {
                assertTrue(testMap.containsKey(i)); 
            }
    }

}
