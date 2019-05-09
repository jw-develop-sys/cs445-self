package c17p2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;


public abstract class SetRemoveTest extends SetTest{

    @Test
    public void removeInitial() {
        reset();
        testSet.remove(getData()[0]);
        assertFalse(testSet.contains(getData()[0]));
        assertEquals(0, testSet.size());
        assertTrue(testSet.isEmpty());
    }

    @Test
    public void removeSpurious() {
        reset();
        testSet.add(getData()[0]);
        testSet.add(getData()[1]);
        testSet.add(getData()[0]);
        testSet.remove(getData()[2]);
        assertTrue(testSet.contains(getData()[0]));
        assertTrue(testSet.contains(getData()[1]));
        assertFalse(testSet.contains(getData()[2]));
        assertEquals(2, testSet.size());
        assertFalse(testSet.isEmpty());
    }

    @Test
    public void removeOnlySingleton() {
        reset();
        testSet.add(getData()[0]);
        testSet.remove(getData()[0]);
        assertFalse(testSet.contains(getData()[0]));
        assertEquals(0, testSet.size());
        assertTrue(testSet.isEmpty());
    }

    @Test
    public void removeOnlyMultiple() {
        reset();
        testSet.add(getData()[0]);
        testSet.add(getData()[0]);
        testSet.add(getData()[0]);
        testSet.remove(getData()[0]);
        assertFalse(testSet.contains(getData()[0]));
        assertEquals(0, testSet.size());
        assertTrue(testSet.isEmpty());
    }
        
    @Test
    public void removeSingletonAmongMany() {
        reset();
        testSet.add(getData()[0]);
        testSet.add(getData()[1]);
        testSet.add(getData()[2]);
        testSet.add(getData()[1]);
        testSet.add(getData()[3]);
        testSet.add(getData()[4]);
        testSet.add(getData()[1]);
        testSet.add(getData()[4]);
        testSet.remove(getData()[0]);
        assertFalse(testSet.contains(getData()[0]));
        assertTrue(testSet.contains(getData()[1]));
        assertTrue(testSet.contains(getData()[2]));
        assertTrue(testSet.contains(getData()[3]));
        assertTrue(testSet.contains(getData()[4]));
        assertEquals(4, testSet.size());
        assertFalse(testSet.isEmpty());
    }

    @Test
    public void removeMultipleAmongMany() {
        reset();
        testSet.add(getData()[0]);
        testSet.add(getData()[1]);
        testSet.add(getData()[2]);
        testSet.add(getData()[1]);
        testSet.add(getData()[0]);
        testSet.add(getData()[3]);
        testSet.add(getData()[4]);
        testSet.add(getData()[1]);
        testSet.add(getData()[4]);
        testSet.add(getData()[0]);
        testSet.remove(getData()[0]);
        assertFalse(testSet.contains(getData()[0]));
        assertTrue(testSet.contains(getData()[1]));
        assertTrue(testSet.contains(getData()[2]));
        assertTrue(testSet.contains(getData()[3]));
        assertTrue(testSet.contains(getData()[4]));
        assertEquals(4, testSet.size());
        assertFalse(testSet.isEmpty());
    }

    @Test
    public void addSameAfterRemove() {
        reset();
        testSet.add(getData()[0]);
        testSet.add(getData()[1]);
        testSet.add(getData()[2]);
        testSet.add(getData()[1]);
        testSet.add(getData()[0]);
        testSet.add(getData()[3]);
        testSet.add(getData()[4]);
        testSet.add(getData()[1]);
        testSet.add(getData()[4]);
        testSet.add(getData()[0]);
        testSet.remove(getData()[0]);
        testSet.add(getData()[0]);
        assertTrue(testSet.contains(getData()[0]));
        assertTrue(testSet.contains(getData()[1]));
        assertTrue(testSet.contains(getData()[2]));
        assertTrue(testSet.contains(getData()[3]));
        assertTrue(testSet.contains(getData()[4]));
        assertEquals(5, testSet.size());
        assertFalse(testSet.isEmpty());
    }

    @Test
    public void addDifferentAfterRemove() {
        reset();
        testSet.add(getData()[0]);
        testSet.add(getData()[1]);
        testSet.add(getData()[2]);
        testSet.add(getData()[1]);
        testSet.add(getData()[0]);
        testSet.add(getData()[3]);
        testSet.add(getData()[4]);
        testSet.add(getData()[1]);
        testSet.add(getData()[4]);
        testSet.add(getData()[0]);
        testSet.remove(getData()[0]);
        testSet.add(getData()[4]);
        testSet.add(getData()[5]);
        assertFalse(testSet.contains(getData()[0]));
        assertTrue(testSet.contains(getData()[1]));
        assertTrue(testSet.contains(getData()[2]));
        assertTrue(testSet.contains(getData()[3]));
        assertTrue(testSet.contains(getData()[4]));
        assertTrue(testSet.contains(getData()[5]));
        assertEquals(5, testSet.size());
        assertFalse(testSet.isEmpty());
    }

    @Test 
    public void removeSomeOfMany() {
        reset();
        populate();
        testSet.remove(getData()[3]);
        testSet.remove(getData()[4]);
        testSet.remove(getData()[9]);
        for (int i = 0; i < getData().length; i++)
            if (i == 3 || i == 4 || i == 9)
                assertFalse(testSet.contains(getData()[i]));
            else
                assertTrue(testSet.contains(getData()[i]));
        assertEquals(getData().length - 3, testSet.size());
    }

    @Test
    public void removeAllOfMany() {
        reset();
        populate();
        for (int i = 0; i < getData().length; i++)
            testSet.remove(getData()[i]);
        for (int i = 0; i < getData().length; i++)
            assertFalse(testSet.contains(getData()[i]));
        assertEquals(0, testSet.size());
    }

    @Test
    public void removeSomeIterator() {
        reset();
        populate();
        testSet.remove(getData()[3]);
        testSet.remove(getData()[4]);
        testSet.remove(getData()[9]);
        clearMarks();
        for (Iterator<String> it = testSet.iterator(); it.hasNext(); )
            marks[indexForDatum(it.next())]++;
        for (int i = 0; i < marks.length; i++)
            if (i == 3 || i == 4 || i == 9)
                assertEquals(0, marks[i]);
            else
                assertEquals(1, marks[i]);
    }

    @Test
    public void removeAllIterator() {
        reset();
        populate();
        for (int i = 0; i < getData().length; i++)
            testSet.remove(getData()[i]);
        int i = 0;
        for (Iterator<String> it = testSet.iterator(); it.hasNext(); i++)
            it.next();
        assertEquals(0, i);
    }

    @Test
    public void addAfterRemoveIterator() {
        reset();
        populate();
        for (int i = 0; i < getData().length; i++)
            testSet.remove(getData()[i]);
        testSet.add(getData()[0]);
        testSet.add(getData()[1]);
        testSet.add(getData()[0]);
        clearMarks();
        for (Iterator<String> it = testSet.iterator(); it.hasNext(); )
            marks[indexForDatum(it.next())]++;
        assertEquals(1, marks[0]);
        assertEquals(1, marks[1]);
        for (int i = 2; i < marks.length; i++)
            assertEquals(0, marks[i]);
        
    }
 
}
