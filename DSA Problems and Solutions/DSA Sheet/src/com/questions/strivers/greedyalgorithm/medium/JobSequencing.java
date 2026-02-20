package com.questions.strivers.greedyalgorithm.medium;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: Job Sequencing Problem (Medium)
 * ==================================================================================================
 * You are given a set of N jobs where each job comes with a deadline and profit.
 * The profit can only be earned upon completing the job within its deadline.
 * Find the number of jobs done and the maximum profit that can be obtained.
 * Each job takes a single unit of time and only one job can be performed at a time.
 *
 * Example 1:
 * Input: N = 4, Jobs = [(1, 4, 20), (2, 1, 10), (3, 1, 40), (4, 1, 30)]
 * Output: 2 60
 * Explanation:
 * - Job 3 (deadline 1) is done at time slot 1.
 * - Job 1 (deadline 4) is done at time slot 2 (or 3 or 4).
 * Total profit = 40 + 20 = 60. Jobs done = 2.
 *
 * Example 2:
 * Input: N = 5, Jobs = [(1, 2, 100), (2, 1, 19), (3, 2, 27), (4, 1, 25), (5, 1, 15)]
 * Output: 2 127
 * Explanation:
 * - Job 1 (deadline 2) is done at time slot 2.
 * - Job 3 (deadline 2) is done at time slot 1.
 * Total profit = 100 + 27 = 127. Jobs done = 2.
 * * ==================================================================================================
 * APPROACH: GREEDY ALGORITHM (Maximize Profit, Delay Execution)
 * ==================================================================================================
 * 1. Sort all jobs in descending order of their profit. We want the most lucrative jobs first.
 * 2. Find the maximum deadline among all jobs to determine the maximum timeline we have.
 * 3. Create a 'slots' array to keep track of occupied time slots.
 * 4. For each job, try to schedule it as LATE as possible (exactly on its deadline).
 * If that slot is taken, search backwards (deadline - 1, deadline - 2, etc.) for a free slot.
 * Why as late as possible? To leave earlier time slots open for jobs that have shorter deadlines!
 * ==================================================================================================
 */

import java.util.Arrays;

// Job class to group ID, deadline, and profit together
class Job {
    int id;
    int deadline;
    int profit;

    Job(int id, int deadline, int profit) {
        this.id = id;
        this.deadline = deadline;
        this.profit = profit;
    }
}

public class JobSequencing {

    public static void main(String[] args) {
        // Test Case 1
        Job[] jobs1 = {
                new Job(1, 4, 20),
                new Job(2, 1, 10),
                new Job(3, 1, 40),
                new Job(4, 1, 30)
        };
        int[] result1 = jobScheduling(jobs1);
        System.out.println("Test Case 1 -> Jobs Done: " + result1[0] + ", Max Profit: " + result1[1]);

        // Test Case 2
        Job[] jobs2 = {
                new Job(1, 2, 100),
                new Job(2, 1, 19),
                new Job(3, 2, 27),
                new Job(4, 1, 25),
                new Job(5, 1, 15)
        };
        int[] result2 = jobScheduling(jobs2);
        System.out.println("Test Case 2 -> Jobs Done: " + result2[0] + ", Max Profit: " + result2[1]);
    }

    /**
     * Finds the maximum profit and number of jobs done.
     * * @param jobs Array of Job objects
     * @return An integer array where index 0 is count of jobs, and index 1 is max profit.
     */
    public static int[] jobScheduling(Job[] jobs) {
        int n = jobs.length;

        // 1. Sort the jobs in descending order based on profit.
        // We use a custom comparator. b.profit - a.profit gives descending order.
        Arrays.sort(jobs, (a, b) -> b.profit - a.profit);

        // 2. Find the maximum deadline to know how many time slots we need.
        int maxDeadline = 0;
        for (int i = 0; i < n; i++) {
            maxDeadline = Math.max(maxDeadline, jobs[i].deadline);
        }

        // 3. Create an array to keep track of free time slots.
        // We use maxDeadline + 1 so we can use 1-based indexing for days (Day 1 to maxDeadline).
        // Initialize all slots to -1 (indicating they are empty).
        int[] slots = new int[maxDeadline + 1];
        Arrays.fill(slots, -1);

        int countJobs = 0;
        int maxProfit = 0;

        // 4. Iterate through the sorted jobs and try to schedule them
        for (int i = 0; i < n; i++) {

            // Try to place the job as late as possible, starting from its deadline
            for (int j = jobs[i].deadline; j > 0; j--) {

                // If the slot is empty, we can schedule the job here!
                if (slots[j] == -1) {
                    slots[j] = jobs[i].id; // Assign job ID to this slot
                    countJobs++;           // Increment completed jobs count
                    maxProfit += jobs[i].profit; // Add to total profit

                    // Break out of the inner loop since the job is successfully scheduled
                    break;
                }
            }
        }

        // Return the results as an array [jobs_done, total_profit]
        return new int[]{countJobs, maxProfit};
    }
}