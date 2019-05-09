package c16p2;

/**
 * Class to model a uniprocessor with time-sharing.
 * 
 * CLRS 16-2.b
 * @author Thomas VanDrunen
 * CSCI 445
 * Oct 4, 2016
 */
public class TSProcessor {

    /**
     * Degenerate class representing tasks.
     */
    public static class Task {
        /**
         * The number of cycles this task requires
         */
        public final int processingTime;
        /**
         * The number of cycles (after the beginning of the
         * start of the first task) after which this 
         */
        public final int releaseTime;
        private int timeLeft;
        public Task(int processingTime, int releaseTime) {
            this.processingTime = processingTime;
            this.releaseTime = releaseTime;
            this.timeLeft = processingTime;
        }
    }
    
    /**
     * Factory method to make a bunch of tasks.
     * @param times A two-dim array such that times[i][0] 
     * gives the processingTime for task i and times[i][1]
     * gives the releaseTime for task i
     * @return An array of tasks
     */
    public static Task[] taskSetFactory(int[][] times) {
        Task[] toReturn = new Task[times.length];
        for (int i = 0; i < times.length; i++) 
            toReturn[i] = new Task(times[i][0], times[i][1]);
        return toReturn;

    }
    
    /**
     * Exception for a task executed too early.
     */
    public static class UnreleasedTaskException extends RuntimeException {
        private static final long serialVersionUID = -8692907811369171133L;

        public UnreleasedTaskException(int attemptedExecutionTime, int releaseTime) {
            super("Attempted to execute task to be released at time " +
                    releaseTime + " at time " + attemptedExecutionTime);
        }
    }
    
    /**
     * Exception for a task incomplete when the schedule is finished.
     */
    public static class IncompleteTaskException extends RuntimeException {
        private static final long serialVersionUID = -4228656682239873731L;

        public IncompleteTaskException(int id) {
            super("Task " + id + " incomplete at end of schedule");
        }
    }

    /**
     * Degenerate class to stand for a scheduling of a
     * task for part of its run: a task ID and the
     * length of time given to that task.
     */
    public static class ScheduleUnit {
        private final Task scheduledTask;
        private final int scheduledTime;
        /**
         * @param scheduledTask A reference to the task to be scheduled
         * @param scheduledTime The length of time the task is given
         * on the processor; it's ok if that is longer than the
         * task needs to complete, but the processor will be idle for any
         * excess time assigned to a task.
         */
        public ScheduleUnit(Task scheduledTask, int scheduledTime) {
            this.scheduledTask = scheduledTask;
            this.scheduledTime = scheduledTime;
        }
    }
    
    /**
     * Schedule downtime for the processor (such as to wait
     * for a task to be released).
     */
    public static class IdleScheduleUnit extends ScheduleUnit {
        /**
         * @param idleTime The time the processor needs to wait
         * until a task is released
         */
        public IdleScheduleUnit(int idleTime) {
            super(new Task(0,0), idleTime);
        }
    }
    
    /**
     * Calculate the average completion time for
     * a batch of tasks executed in the order given.
     * @param tasks The tasks to execute in the given order
     * @return The average completion time
     */
    public static double run(Task[] tasks, Iterable<ScheduleUnit> schedule) {
        int totalCompletionTime = 0;
        int tasksCompleted = 0;
        int cycles = 0;
        for (ScheduleUnit su : schedule) {
            if (su.scheduledTask.releaseTime > cycles)
                throw new UnreleasedTaskException(cycles,
                        su.scheduledTask.releaseTime);
            if (su.scheduledTask.timeLeft > 0 && 
                    su.scheduledTask.timeLeft <= su.scheduledTime) {
                totalCompletionTime += cycles + su.scheduledTask.timeLeft;
                tasksCompleted++;
            }
            cycles += su.scheduledTime;
            su.scheduledTask.timeLeft -= su.scheduledTime;
        }
        
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i].timeLeft > 0) {
                assert tasksCompleted < tasks.length;
                throw new IncompleteTaskException(i);
            }
        }
        assert tasksCompleted == tasks.length;
        
        return ((double) totalCompletionTime) / tasksCompleted;
    }
    
    
}
