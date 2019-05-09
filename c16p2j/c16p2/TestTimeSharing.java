package c16p2;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestTimeSharing {

    @Test
    public void test() {
        TSProcessor.Task[] tasks = TSProcessor.taskSetFactory(
                new int[][] {{3,0}, {4,5}, {9,6}, {1,7}});
        assertEquals(10.0, TSProcessor.run(tasks, Scheduler.scheduleTimeSharing(tasks)),
                .001);
    }

}
