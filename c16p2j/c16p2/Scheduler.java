package c16p2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import c16p2.BatchProcessor.Task;

public class Scheduler {

    // 16-2.a
    /**
     * Schedule a bunch of tasks to be executed on a
     * batch processor by re-arranging the arrangement of
     * tasks. 
     * @param tasks The tasks to execute.
     * POSTCONDITION: tasks has been rearranged into the
     * order to be executed
     */
    public static void scheduleBatch(BatchProcessor.Task[] tasks) {

         
    }

    // 16-2.b
    /**
     * Schedule a bunch of tasks to be executed on a 
     * time-sharing system by producing a schedule consisting
     * of units, each unit indicating which task and for how long.
     * Note you can schedule periods for the processor to be idle,
     * and rearranging the tasks array is harmless.
     * @param tasks
     * @return
     */
    public static Iterable<TSProcessor.ScheduleUnit> 
        scheduleTimeSharing(TSProcessor.Task[] tasks) {

        ArrayList<TSProcessor.ScheduleUnit> schedule = 
                new ArrayList<TSProcessor.ScheduleUnit>();

        // ...
        
        
        return schedule;
    }

    
}
