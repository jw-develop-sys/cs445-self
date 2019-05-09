package c17p2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

public abstract class SetTest extends CollectionTest {

    protected Set<String> testSet;
    protected Set<Integer> testSetInt;

    public SetTest() {
        super();
    }

    protected void populate() {
        for (int i = 0; i < getData().length; i++)
            testSet.add(getData()[i]);
    }

    protected abstract void reset();

    protected abstract void resetInt();

    protected int indexForDatum(String datum) {
        int index = -1;
        for (int i = 0; i < getData().length && index == -1; i++)
            if (getData()[i].equals(datum))
                index = i;
        return index;
    }

    @Test
    public void initialEmpty() {
        reset();
        assertTrue(testSet.isEmpty());
    }

    @Test
    public void initialSize() {
        reset();
        assertEquals(0, testSet.size());
    }

    @Test
    public void addNotEmpty() {
        reset();
        testSet.add(getData()[0]);
        assertFalse(testSet.isEmpty());
    }

    @Test
    public void addSize() {
        reset();
        testSet.add(getData()[0]);
        assertEquals(1, testSet.size());
    }

    @Test
    public void twoAddsUniqueSize() {
        reset();
        testSet.add(getData()[0]);
        testSet.add(getData()[1]);
        assertFalse(testSet.isEmpty());
        assertEquals(2, testSet.size());
    }

    @Test
    public void twoAddsIdenticalSize() {
        reset();
        testSet.add(getData()[0]);
        testSet.add(getData()[0]);
        assertFalse(testSet.isEmpty());
        assertEquals(1, testSet.size());
    }

    @Test
    public void addsUnique() {
        reset();
        for (int i = 0; i < 8; i++) 
            testSet.add(getData()[i]);
        assertFalse(testSet.isEmpty());
        assertEquals(8, testSet.size());
    }

    @Test
    public void addsIdentical() {
        reset();
        for (int i = 0; i < 8; i++) 
            testSet.add(getData()[0]);
        assertFalse(testSet.isEmpty());
        assertEquals(1, testSet.size());
    }

    @Test
    public void addsMix() {
        reset();
        for (int i = 0; i < 50; i++)
            testSet.add(getData()[rand.nextInt(getData().length)]);
        assertFalse(testSet.isEmpty());
        //assertEquals(getData().length, testSet.size());
    }

    @Test
    public void addsLots() {
        reset();
        populate();
        assertFalse(testSet.isEmpty());
        assertEquals(getData().length, testSet.size());
    }

    @Test
    public void containsInitial() {
        reset();
        assertFalse(testSet.contains(getData()[0]));
    }

    @Test
    public void containsOne() {
        reset();
        testSet.add(getData()[0]);
        assertTrue(testSet.contains(getData()[0]));
    }

    @Test
    public void containsTwoUnique() {
        reset();
        testSet.add(getData()[0]);
        testSet.add(getData()[1]);
        assertTrue(testSet.contains(getData()[0]));
        assertTrue(testSet.contains(getData()[1]));
    }

    @Test
    public void containsTwoIdentical() {
        reset();
        testSet.add(getData()[0]);
        testSet.add(getData()[0]);
        assertTrue(testSet.contains(getData()[0]));
    }

    @Test
    public void containsLots() {
        reset();
        populate();
        for (int i = 0; i < getData().length; i++)
            assertTrue(testSet.contains(getData()[i]));
    }

    @Test
    public void initialIterator() {
        reset();
        int i = 0;
        for (Iterator<String> it = testSet.iterator(); it.hasNext(); )
            i++;
        assertEquals(0, i);
    }

    @Test
    public void onlySingletonIterator() {
        reset();
        testSet.add(getData()[0]);
        clearMarks();
        for (Iterator<String> it = testSet.iterator(); it.hasNext(); )
            marks[indexForDatum(it.next())]++;
        assertEquals(1, marks[0]);
        for (int i = 1; i < marks.length; i++)
            assertEquals(0, marks[i]);
    }

    @Test
    public void onlyMultipleIterator() {
        reset();
        testSet.add(getData()[0]);
        testSet.add(getData()[0]);
        testSet.add(getData()[0]);
        clearMarks();
        for (Iterator<String> it = testSet.iterator(); it.hasNext(); )
            marks[indexForDatum(it.next())]++;
        assertEquals(1, marks[0]);
        for (int i = 1; i < marks.length; i++)
            assertEquals(0, marks[i]);
    }

    @Test
    public void manyPlannedIterator() {
        reset();
        populate();
        clearMarks();
        for (Iterator<String> it = testSet.iterator(); it.hasNext(); )
            marks[indexForDatum(it.next())]++;
        for (int i = 0; i < marks.length; i++)
            assertEquals(1, marks[i]);
    }

    @Test
    public void manyRandomIterator() {
        reset();
        for (int i = 0; i < 50; i++)
            testSet.add(getData()[rand.nextInt(getData().length)]);
        clearMarks();
        for (Iterator<String> it = testSet.iterator(); it.hasNext(); )
            marks[indexForDatum(it.next())]++;
        for (int i = 0; i < marks.length; i++)
            assertEquals(testSet.contains(getData()[i])? 1 : 0, marks[i]);
    }

}