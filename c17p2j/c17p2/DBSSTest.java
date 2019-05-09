package c17p2;

public class DBSSTest extends SetTest {

    protected void reset() {
        testSet = new DynamicBinarySearchSet<String>();
    }

    protected void resetInt() {
        testSetInt = new DynamicBinarySearchSet<Integer>();
    }

}
