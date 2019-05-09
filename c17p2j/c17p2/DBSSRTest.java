package c17p2;

public class DBSSRTest extends SetRemoveTest {

    protected void reset() {
        testSet = new DynamicBinarySearchSet<String>();
    }

    protected void resetInt() {
        testSetInt = new DynamicBinarySearchSet<Integer>();
    }
}
