package c16p2;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestBatch {

    @Test
    public void test1() {
        BatchProcessor.Task[] tasks = BatchProcessor.taskSetFactory(new int[]{3, 6, 1});
        Scheduler.scheduleBatch(tasks);
        assertEquals(5.0, BatchProcessor.run(tasks), .001);
    }

}
