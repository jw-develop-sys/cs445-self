package c16p2;

/**
 * Class to model a batch uniprocessor (no time-sharing).
 * 
 * CLRS 16-2.a
 * @author Thomas VanDrunen
 * CSCI 445
 * Oct 4, 2016
 */
public class BatchProcessor {
    
    /**
     * Degenerate class representing tasks.
     */
    public static class Task {
        /**
         * The number of cycles this task requires
         */
        public final int processingTime;
        public Task(int processingTime) {
            this.processingTime = processingTime;
        }
    }
    
    /**
     * Factory method to make a bunch of tasks.
     * @param processingTimes The processing times for the tasks
     * @return An array of tasks
     */
    public static Task[] taskSetFactory(int[] processingTimes) {
        Task[] toReturn = new Task[processingTimes.length];
        for (int i = 0; i < processingTimes.length; i++) 
            toReturn[i] = new Task(processingTimes[i]);
        return toReturn;
    }
    
    /**
     * Calculate the average completion time for
     * a batch of tasks executed in the order given.
     * @param tasks The tasks to execute in the given order
     * @return The average completion time
     */
    public static double run(Task[] tasks) {
        int totalCompletionTime = 0;
        int totalProcessingTime = 0;
        int tasksCompleted = 0;
        for (Task t : tasks) {
            totalProcessingTime += t.processingTime;
            totalCompletionTime += totalProcessingTime;
            tasksCompleted++;
        }
        return ((double) totalCompletionTime) / tasksCompleted;
        // I used the counter tasksCompleted instead of tasks.length
        // for easier refactoring to an Iterable.
        // Yeah, shoulda used Python.
    }

    
    
}

