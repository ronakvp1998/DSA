package com.questions.strivers.greedyalgorithm.medium;
/**
 * 🟡 PROBLEM: JOB SEQUENCING PROBLEM (MEDIUM)
 * * ==================================================================================================
 * PROBLEM STATEMENT:
 * ==================================================================================================
 * You are given a set of N jobs where each job comes with a deadline and profit.
 * The profit can only be earned upon completing the job within its deadline.
 * Find the number of jobs done and the maximum profit that can be obtained.
 * Each job takes a single unit of time and only one job can be performed at a time.
 *
 * Constraints:
 * - 1 <= N <= 10^5
 * - 1 <= Deadline <= 10^5
 * - 1 <= Profit <= 500
 * * Example 1:
 * Input: N = 4, Jobs = [(1, 4, 20), (2, 1, 10), (3, 1, 40), (4, 1, 30)]
 * Output: 2 60
 * Explanation: Job 3 (deadline 1) done at time 1. Job 1 (deadline 4) done at time 2. Profit = 40+20=60.
 *
 * Example 2:
 * Input: N = 5, Jobs = [(1, 2, 100), (2, 1, 19), (3, 2, 27), (4, 1, 25), (5, 1, 15)]
 * Output: 2 127
 * Explanation: Job 4 (deadline 1) done at time 1. Job 1 (deadline 2) done at time 2. Profit = 25+100=127.
 * * ==================================================================================================
 * CONCEPTUAL VISUALIZATION (DP Approach for Max Profit - Example 2)
 * ==================================================================================================
 * To solve this using DP (Knapsack variation), we MUST first sort jobs by ascending deadline.
 * Sorted Example 2 Jobs (Deadline, Profit): J4(1, 25), J2(1, 19), J5(1, 15), J1(2, 100), J3(2, 27)
 * * RECURSION TREE (State: index, currentTime):
 *                              (0, 0)
 *                           /        \
 *                  Take J4 /            \ Skip J4
 *                   (1, 1)          (1, 0)
 *                  /      \         /     \
 *          Skip J2 /  Take J2(X) Take J2     Skip J2
 *          (2, 1)    (Cannot,   (2, 1)   (2, 0)
 *          time>1)
 * * * NOTE: While DP finds the max profit perfectly, the GREEDY approach (Phase 5) is the
 * industry standard for this problem because it natively tracks both "Jobs Done" and
 * "Max Profit" much more efficiently.
 */

import java.util.*;

public class JobSequencing {
    static class Job {
        int id, deadline, profit;
        Job(int id, int deadline, int profit) {
            this.id = id;
            this.deadline = deadline;
            this.profit = profit;
        }
    }
    /**
     * PHASE 1: BRUTE FORCE RECURSION - The "Think it" stage.
     * Intuition: Sort jobs by deadline. At any given time unit, we have a choice:
     * either schedule the current job (if time permits) or skip it.
     * * Complexity Analysis:
     * Time Complexity: O(2^N) - Two choices for every job.
     * Space Complexity: O(N) Auxiliary stack space for recursion.
     */
    public static int maxProfitRecursive(Job[] jobs) {
        // Must sort by deadline ascending for timeline-based DP
        Arrays.sort(jobs, (a, b) -> a.deadline - b.deadline);
        return solveBrute(jobs, 0, 0);
    }

    private static int solveBrute(Job[] jobs, int idx, int time) {
        if (idx == jobs.length) return 0;

        int skip = solveBrute(jobs, idx + 1, time);
        int take = 0;

        // If current time + 1 unit is within the deadline, we can take it
        if (time + 1 <= jobs[idx].deadline) {
            take = jobs[idx].profit + solveBrute(jobs, idx + 1, time + 1);
        }

        return Math.max(skip, take);
    }

    /**
     * PHASE 2: TOP-DOWN MEMOIZATION - The "Refine it" stage.
     * Intuition: The recursive tree has overlapping subproblems: solve(idx, time).
     * We cache these states in a 2D array.
     * * Complexity Analysis:
     * Time Complexity: O(N * MaxDeadline) - Processing each unique state once.
     * Space Complexity: O(N * MaxDeadline) Heap + O(N) Stack.
     */
    public static int maxProfitMemo(Job[] jobs) {
        Arrays.sort(jobs, (a, b) -> a.deadline - b.deadline);
        int maxD = 0;
        for (Job j : jobs) maxD = Math.max(maxD, j.deadline);

        Integer[][] memo = new Integer[jobs.length][maxD + 1];
        return solveMemo(jobs, 0, 0, memo);
    }

    private static int solveMemo(Job[] jobs, int idx, int time, Integer[][] memo) {
        if (idx == jobs.length) return 0;
        if (memo[idx][time] != null) return memo[idx][time];

        int skip = solveMemo(jobs, idx + 1, time, memo);
        int take = 0;
        if (time + 1 <= jobs[idx].deadline) {
            take = jobs[idx].profit + solveMemo(jobs, idx + 1, time + 1, memo);
        }

        return memo[idx][time] = Math.max(skip, take);
    }

    /**
     * PHASE 3: BOTTOM-UP TABULATION - The "Build it" stage.
     * Intuition: Convert the recursive stack into an iterative table.
     * dp[i][t] represents the max profit using a subset of the first 'i' jobs by time 't'.
     * * EXACT DEFAULT STATE OF DP ARRAY (Example 2: 5 Jobs, Max Deadline 2):
     * t=0 t=1 t=2
     * i=0 [0,  0,  0]
     * i=1 [0,  0,  0]
     * i=2 [0,  0,  0]
     * i=3 [0,  0,  0]
     * i=4 [0,  0,  0]
     * i=5 [0,  0,  0]
     * * EXACT FINAL STATE OF DP ARRAY (Example 2):
     * t=0 t=1 t=2
     * i=0 [0,  0,   0]
     * i=1 [0, 25,  25] -> J4(D1,P25)
     * i=2 [0, 25,  25] -> J2(D1,P19)
     * i=3 [0, 25,  25] -> J5(D1,P15)
     * i=4 [0, 25, 125] -> J1(D2,P100)
     * i=5 [0, 27, 127] -> J3(D2,P27)
     * * Complexity Analysis:
     * Time Complexity: O(N * MaxDeadline)
     * Space Complexity: O(N * MaxDeadline) Heap space.
     */
    public static int maxProfitTabulation(Job[] jobs) {
        Arrays.sort(jobs, (a, b) -> a.deadline - b.deadline);
        int n = jobs.length;
        int maxD = jobs[n-1].deadline; // Since sorted, last element might not have max deadline if ties exist.
        for (Job j : jobs) maxD = Math.max(maxD, j.deadline); // Safe max finding

        int[][] dp = new int[n + 1][maxD + 1];

        for (int i = 1; i <= n; i++) {
            for (int t = 0; t <= maxD; t++) {
                int skip = dp[i - 1][t];
                int take = 0;
                // Since we are looking at job i-1, we check if taking it at time (t) is valid
                // We take it if the deadline is at least t, and add profit to the state of (t-1)
                if (jobs[i - 1].deadline >= t && t > 0) {
                    take = jobs[i - 1].profit + dp[i - 1][t - 1];
                }
                dp[i][t] = Math.max(skip, take);
            }
        }
        return dp[n][maxD];
    }

    /**
     * PHASE 4: SPACE OPTIMIZATION - The "Perfect it" stage.
     * Intuition: Looking at Phase 3, dp[i] only relies on dp[i-1]. We can reduce
     * the 2D array to two 1D arrays (prev and curr).
     * * Complexity Analysis:
     * Time Complexity: O(N * MaxDeadline)
     * Space Complexity: O(MaxDeadline) Heap space.
     */
    public static int maxProfitSpaceOpt(Job[] jobs) {
        Arrays.sort(jobs, (a, b) -> a.deadline - b.deadline);
        int maxD = 0;
        for (Job j : jobs) maxD = Math.max(maxD, j.deadline);

        int[] prev = new int[maxD + 1];
        int[] curr = new int[maxD + 1];

        for (int i = 1; i <= jobs.length; i++) {
            for (int t = 0; t <= maxD; t++) {
                int skip = prev[t];
                int take = 0;
                if (jobs[i - 1].deadline >= t && t > 0) {
                    take = jobs[i - 1].profit + prev[t - 1];
                }
                curr[t] = Math.max(skip, take);
            }
            prev = curr.clone();
        }
        return prev[maxD];
    }

    /**
     * PHASE 5: ALTERNATIVE APPROACH - GREEDY (The Interview Standard)
     * Intuition: DP is overkill here because it explores all times.
     * Instead, sort jobs by PROFIT (descending). Try to do the most profitable job
     * as LATE as possible (closest to its deadline). This leaves earlier slots open
     * for less urgent jobs.
     * * Returns: int[] {Total Jobs Done, Total Profit}
     * * Complexity Analysis:
     * Time Complexity: O(N log N) for sorting + O(N * MaxDeadline) for slot finding = O(N^2) worst case.
     * Space Complexity: O(MaxDeadline) to keep track of filled slots.
     */
    public static int[] jobSchedulingGreedy(Job[] jobs) {
        // 1. Sort by profit descending
        Arrays.sort(jobs, (a, b) -> b.profit - a.profit);

        int maxD = 0;
        for (Job j : jobs) maxD = Math.max(maxD, j.deadline);

        // 2. Create an array to keep track of free time slots
        int[] result = new int[maxD + 1];
        Arrays.fill(result, -1);

        int countJobs = 0, jobProfit = 0;

        // 3. Iterate through jobs and place them in the latest possible slot
        for (int i = 0; i < jobs.length; i++) {
            for (int j = jobs[i].deadline; j > 0; j--) {
                if (result[j] == -1) {
                    result[j] = jobs[i].id; // Slot is filled
                    countJobs++;
                    jobProfit += jobs[i].profit;
                    break;
                }
            }
        }
        return new int[]{countJobs, jobProfit};
    }

    /**
     * PHASE 6: ADVANCED ALTERNATIVE - GREEDY + DISJOINT SET UNION (DSU)
     * Intuition: In the standard Greedy approach, finding an empty slot takes O(MaxDeadline).
     * We can optimize this to nearly O(1) using Union-Find. Each slot points to the
     * greatest available empty slot to its left.
     * * Complexity Analysis:
     * Time Complexity: O(N log N) for sorting + O(N * α(N)) for DSU operations = O(N log N) overall.
     * Space Complexity: O(MaxDeadline) for DSU parent array.
     */
    static class DSU {
        int[] parent;
        DSU(int n) {
            parent = new int[n + 1];
            for (int i = 0; i <= n; i++) parent[i] = i;
        }
        int find(int i) {
            if (parent[i] == i) return i;
            return parent[i] = find(parent[i]); // Path compression
        }
        void union(int u, int v) {
            parent[v] = u; // Make u the parent of v
        }
    }

    public static int[] jobSchedulingDSU(Job[] jobs) {
        Arrays.sort(jobs, (a, b) -> b.profit - a.profit);
        int maxD = 0;
        for (Job j : jobs) maxD = Math.max(maxD, j.deadline);

        DSU dsu = new DSU(maxD);
        int countJobs = 0, jobProfit = 0;

        for (Job job : jobs) {
            // Find the maximum available free slot <= deadline
            int availableSlot = dsu.find(job.deadline);

            if (availableSlot > 0) {
                // If a slot is available, use it
                dsu.union(dsu.find(availableSlot - 1), availableSlot);
                countJobs++;
                jobProfit += job.profit;
            }
        }
        return new int[]{countJobs, jobProfit};
    }

    // --- TESTING SUITE ---
    public static void main(String[] args) {
        System.out.println("--- JOB SEQUENCING PROBLEM ---");

        Job[] ex1 = {
                new Job(1, 4, 20), new Job(2, 1, 10),
                new Job(3, 1, 40), new Job(4, 1, 30)
        };

        Job[] ex2 = {
                new Job(1, 2, 100), new Job(2, 1, 19),
                new Job(3, 2, 27), new Job(4, 1, 25), new Job(5, 1, 15)
        };

        // DP outputs only max profit. Greedy outputs [Jobs Done, Max Profit].
        System.out.println("Example 1 (DP Tabulation): " + maxProfitTabulation(ex1.clone()));
        System.out.println("Example 2 (DP Tabulation): " + maxProfitTabulation(ex2.clone()));

        System.out.println("\nExample 1 (Greedy): " + Arrays.toString(jobSchedulingGreedy(ex1.clone())));
        System.out.println("Example 2 (Greedy): " + Arrays.toString(jobSchedulingGreedy(ex2.clone())));

        System.out.println("\nExample 1 (Greedy + DSU): " + Arrays.toString(jobSchedulingDSU(ex1.clone())));
        System.out.println("Example 2 (Greedy + DSU): " + Arrays.toString(jobSchedulingDSU(ex2.clone())));

        // Edge Case: All jobs have the same deadline
        Job[] edge = {
                new Job(1, 1, 50), new Job(2, 1, 10), new Job(3, 1, 20)
        };
        System.out.println("\nEdge Case (Same Deadlines): " + Arrays.toString(jobSchedulingDSU(edge)));
        // Expected: [1, 50] (Only highest profit job can be done at time 1)
    }
}