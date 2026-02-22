package com.questions.strivers.greedyalgorithm.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
 */

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

    // Global variables to track the best result for the Brute Force recursion
    static int bruteMaxProfit = 0;
    static int bruteMaxJobsDone = 0;

    public static void main(String[] args) {
        // Test Case 1
        Job[] jobs1 = {
                new Job(1, 4, 20),
                new Job(2, 1, 10),
                new Job(3, 1, 40),
                new Job(4, 1, 30)
        };

        // Test Case 2
        Job[] jobs2 = {
                new Job(1, 2, 100),
                new Job(2, 1, 19),
                new Job(3, 2, 27),
                new Job(4, 1, 25),
                new Job(5, 1, 15)
        };

        System.out.println("--- TESTING OPTIMAL GREEDY APPROACH ---");
        int[] result1 = jobScheduling(jobs1);
        System.out.println("Test Case 1 -> Jobs Done: " + result1[0] + ", Max Profit: " + result1[1]);
        int[] result2 = jobScheduling(jobs2);
        System.out.println("Test Case 2 -> Jobs Done: " + result2[0] + ", Max Profit: " + result2[1]);

        System.out.println("\n--- TESTING BRUTE FORCE APPROACH ---");
        int[] bruteResult1 = jobSchedulingBruteForce(jobs1);
        System.out.println("Test Case 1 -> Jobs Done: " + bruteResult1[0] + ", Max Profit: " + bruteResult1[1]);
        int[] bruteResult2 = jobSchedulingBruteForce(jobs2);
        System.out.println("Test Case 2 -> Jobs Done: " + bruteResult2[0] + ", Max Profit: " + bruteResult2[1]);
    }

    /**
     *  * * ==================================================================================================
     *  * APPROACH 1: GREEDY ALGORITHM (Maximize Profit, Delay Execution) - OPTIMAL
     *  * ==================================================================================================
     *  * 1. Sort all jobs in descending order of their profit. We want the most lucrative jobs first.
     *  * 2. Find the maximum deadline among all jobs to determine the maximum timeline we have.
     *  * 3. Create a 'slots' array to keep track of occupied time slots.
     *  * 4. For each job, try to schedule it as LATE as possible (exactly on its deadline).
     *  * If that slot is taken, search backwards (deadline - 1, deadline - 2, etc.) for a free slot.
     *  * Why as late as possible? To leave earlier time slots open for jobs that have shorter deadlines!
     *  * Time Complexity: O(N log N) for sorting + O(N * maxDeadline) for slot searching.
     *  * Space Complexity: O(maxDeadline) for the slots array.
     */
    public static int[] jobScheduling(Job[] jobs) {
        int n = jobs.length;

        // 1. Sort the jobs in descending order based on profit.
        Arrays.sort(jobs, (a, b) -> b.profit - a.profit);

        // 2. Find the maximum deadline to know how many time slots we need.
        int maxDeadline = 0;
        for (int i = 0; i < n; i++) {
            maxDeadline = Math.max(maxDeadline, jobs[i].deadline);
        }

        // 3. Create an array to keep track of free time slots.
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

                    break; // Break out of the inner loop once scheduled
                }
            }
        }
        return new int[]{countJobs, maxProfit};
    }

    /**
     * ==================================================================================================
     * APPROACH 2: BRUTE FORCE RECURSIVE (Generate all Subsets)
     * ==================================================================================================
     * 1. Use Recursion/Backtracking to generate every possible subset of the given jobs.
     * 2. For every subset generated, verify if it is a "Valid" schedule (sort by deadline
     * and ensure each job's deadline >= its required execution sequence).
     * 3. If valid, calculate the total profit. Track the global maximum profit.
     * Time Complexity: O(2^N * K log K) - Exponential, triggers Time Limit Exceeded (TLE).
     * Space Complexity: O(N) due to recursion stack depth.
     * ==================================================================================================
     */
    public static int[] jobSchedulingBruteForce(Job[] jobs) {
        // Reset global variables for a fresh run
        bruteMaxProfit = 0;
        bruteMaxJobsDone = 0;

        // Start the recursive tree
        findMaxProfitRecursive(0, jobs, new ArrayList<>());

        return new int[]{bruteMaxJobsDone, bruteMaxProfit};
    }

    /**
     * Helper Method: Recursively generates all combinations of jobs to find the max profit.
     */
    private static void findMaxProfitRecursive(int index, Job[] jobs, List<Job> currentSubset) {
        // Base Case: We have decided to include or exclude every job
        if (index == jobs.length) {

            // 1. Verify if the jobs in this subset can be legally scheduled
            if (isValidSchedule(currentSubset)) {

                int currentProfit = 0;
                for (Job j : currentSubset) {
                    currentProfit += j.profit;
                }

                // 2. Track the maximum profit and jobs done across all valid subsets
                if (currentProfit > bruteMaxProfit) {
                    bruteMaxProfit = currentProfit;
                    bruteMaxJobsDone = currentSubset.size();
                } else if (currentProfit == bruteMaxProfit && currentSubset.size() > bruteMaxJobsDone) {
                    bruteMaxJobsDone = currentSubset.size(); // Tie-breaker: prefer doing more jobs
                }
            }
            return;
        }

        // Branch 1: Exclude current job
        findMaxProfitRecursive(index + 1, jobs, currentSubset);

        // Branch 2: Include current job
        currentSubset.add(jobs[index]);
        findMaxProfitRecursive(index + 1, jobs, currentSubset);

        // Backtrack
        currentSubset.remove(currentSubset.size() - 1);
    }

    /**
     * Helper Method: Verifies if a given subset of jobs can be scheduled without missing deadlines.
     */
    private static boolean isValidSchedule(List<Job> subset) {
        if (subset.isEmpty()) return true;

        // Copy list to avoid altering the recursion's subset order
        List<Job> sortedSubset = new ArrayList<>(subset);

        // Sort ascending by deadline
        Collections.sort(sortedSubset, (a, b) -> a.deadline - b.deadline);

        // Check if each job has a late enough deadline to accommodate its spot in the queue
        for (int i = 0; i < sortedSubset.size(); i++) {
            if (sortedSubset.get(i).deadline < i + 1) {
                return false; // Impossible to schedule this job in time
            }
        }
        return true;
    }
}