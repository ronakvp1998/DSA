package com.questions.strivers.greedyalgorithm.medium;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: Shortest Job First (SJF) CPU Scheduling
 * ==================================================================================================
 * Given a list of job durations representing the time it takes to complete each job.
 * Implement the Shortest Job First algorithm to find the average waiting time for these jobs.
 * (Assume all jobs arrive at time = 0).
 *
 * Example 1:
 * Input: jobs = [3, 1, 4, 2, 5]
 * Output: 4
 * Explanation:
 * - Job (duration 1): waiting time = 0
 * - Job (duration 2): waiting time = 1
 * - Job (duration 3): waiting time = 1 + 2 = 3
 * - Job (duration 4): waiting time = 1 + 2 + 3 = 6
 * - Job (duration 5): waiting time = 1 + 2 + 3 + 4 = 10
 * Average waiting time = (0 + 1 + 3 + 6 + 10) / 5 = 20 / 5 = 4.
 *
 * Example 2:
 * Input: jobs = [4, 3, 7, 1, 2]
 * Output: 4
 * Explanation: Sorted jobs are [1, 2, 3, 4, 7].
 * Waiting times: 0, 1, 3, 6, 10. Total = 20. Average = 20 / 5 = 4.
 * * ==================================================================================================
 * APPROACH: GREEDY ALGORITHM (Sorting)
 * ==================================================================================================
 * To minimize the overall waiting time, we must always execute the shortest available job first.
 * If we execute a long job first, every subsequent job has to wait for that long duration,
 * heavily inflating the total waiting time.
 * * 1. Sort the job durations in ascending order.
 * 2. Maintain a `currentWaitingTime` (the time the current job had to wait).
 * 3. Maintain a `totalWaitingTime` (the sum of all individual waiting times).
 * 4. Iterate through the sorted jobs, accumulating the waiting times, and divide by N at the end.
 * ==================================================================================================
 */

import java.util.Arrays;

public class ShortestJobFirst {

    public static void main(String[] args) {
        // Test Case 1: Expected 4
        int[] jobs1 = {3, 1, 4, 2, 5};
        System.out.println("Test Case 1 (Expected: 4) -> Result: " + averageWaitingTime(jobs1));

        // Test Case 2: Expected 4
        int[] jobs2 = {4, 3, 7, 1, 2};
        System.out.println("Test Case 2 (Expected: 4) -> Result: " + averageWaitingTime(jobs2));
    }

    /**
     * Calculates the average waiting time using the SJF scheduling algorithm.
     * Note: Returning an integer based on the problem examples. For exact precision in real systems,
     * a double or float would be returned.
     * * @param jobs Array of job durations
     * @return The average waiting time (floored to nearest integer as per examples)
     */
    public static int averageWaitingTime(int[] jobs) {
        // Edge case: If there are no jobs, the waiting time is 0.
        if (jobs == null || jobs.length == 0) {
            return 0;
        }

        // 1. Sort the jobs in ascending order.
        // This is the core Greedy step: placing the shortest jobs first minimizes
        // the cascading delay applied to all subsequent jobs.
        Arrays.sort(jobs);

        int totalWaitingTime = 0;
        int currentWaitingTime = 0;

        // 2. Iterate through the sorted jobs to calculate waiting times
        for (int i = 0; i < jobs.length; i++) {
            // Add the time the current job had to wait to our global total
            totalWaitingTime += currentWaitingTime;

            // The NEXT job will have to wait for the current job to finish.
            // So we add the current job's duration to the running waiting time.
            currentWaitingTime += jobs[i];
        }

        // 3. Return the average waiting time
        return totalWaitingTime / jobs.length;
    }
}