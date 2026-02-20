package com.questions.strivers.dynamicprogramming.twoddp;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: NINJA'S TRAINING (2D Dynamic Programming)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * A Ninja has an 'N' day training schedule. Each day, he can perform one of three activities:
 * 0: Running
 * 1: Fighting Practice
 * 2: Learning New Moves
 *
 * Each activity has specific merit points for that day.
 * CONSTRAINT: The Ninja cannot perform the same activity on two consecutive days.
 *
 * GOAL:
 * Find the maximum total merit points the Ninja can attain in N days.
 *
 * EXAMPLE:
 * Input:
 * Day 0: [10, 40, 70]
 * Day 1: [20, 50, 80]
 * Day 2: [30, 60, 90]
 *
 * Output: 210
 * Explanation:
 * Day 0: 70 (Task 2)
 * Day 1: 50 (Task 1) - Cannot do Task 2 again
 * Day 2: 90 (Task 2) - Cannot do Task 1 again
 * Total = 70 + 50 + 90 = 210.
 * ==================================================================================================
 */
public class NinjaTraining {

    public static void main(String[] args) {
        // Input: Merit points for [Day][Activity]
        int[][] meritPoints = {
                {10, 40, 70},
                {20, 50, 80},
                {30, 60, 90}
        };

        int n = meritPoints.length;
        System.out.println("Days: " + n);
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach
        // Note: We pass '3' as the initial lastTask because 0,1,2 are valid tasks.
        // 3 represents "No task performed previously".
        System.out.println("1. Recursion       : " + recursive(n - 1, 3, meritPoints));

        // 2. Memoization Approach
        // DP Array Size: [N][4]
        // N rows for days.
        // 4 columns for lastTask (0, 1, 2, and 3 for 'none').
        int[][] dp = new int[n][4];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("2. Memoization     : " + memoization(n - 1, 3, meritPoints, dp));

        // 3. Tabulation Approach
        System.out.println("3. Tabulation      : " + tabulation(meritPoints));

        // 4. Space Optimized Approach
        System.out.println("4. Space Optimized : " + spaceOptimized(meritPoints));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * We define a function f(day, lastTask) which returns the max points possible
     * from index 0 to 'day', given that the activity performed on 'day + 1' was 'lastTask'.
     *
     * - On 'day', we can pick any task 'i' (0, 1, 2) as long as i != lastTask.
     * - We recursively calculate the best path for 'day - 1'.
     * recursion is happening for days and for loop for the task
     *
     * COMPLEXITY:
     * - Time: O(3^N) -> At every step, we try 2 branches (since 1 task is blocked).
     * - Space: O(N) -> Recursion stack depth.
     */
    private static int recursive(int day, int lastTask, int[][] points) {
        // Base Case: Day 0
        // We must pick the maximum points from the tasks that are NOT 'lastTask'.
        if (day == 0) {
            int max = 0;
            for (int task = 0; task < 3; task++) {
                if (task != lastTask) {
                    max = Math.max(max, points[0][task]);
                }
            }
            return max;
        }

        int maxPoints = 0;
        // Try all 3 activities for the current day
        for (int task = 0; task < 3; task++) {
            if (task != lastTask) {
                // Calculate points for current task + max points from previous days
                // Pass current 'task' as 'lastTask' for the next recursive call
                int currentPoints = points[day][task] + recursive(day - 1, task, points);
                maxPoints = Math.max(maxPoints, currentPoints);
            }
        }
        return maxPoints;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Same logic as recursion, but we store the result of f(day, lastTask) in a DP table.
     * State: dp[day][lastTask]
     *
     * COMPLEXITY:
     * - Time: O(N * 4 * 3) -> O(N). There are N*4 states, and each state loops 3 times.
     * - Space: O(N * 4) + O(N) Stack -> O(N).
     */
    private static int memoization(int day, int lastTask, int[][] points, int[][] dp) {
        // Base Case
        if (day == 0) {
            int max = 0;
            for (int task = 0; task < 3; task++) {
                if (task != lastTask) {
                    max = Math.max(max, points[0][task]);
                }
            }
            return max;
        }

        // Step 1: Check Cache
        if (dp[day][lastTask] != -1) {
            return dp[day][lastTask];
        }

        int maxPoints = 0;
        // Step 2: Iterate options
        for (int task = 0; task < 3; task++) {
            if (task != lastTask) {
                int currentPoints = points[day][task] + memoization(day - 1, task, points, dp);
                maxPoints = Math.max(maxPoints, currentPoints);
            }
        }

        // Step 3: Store and Return
        return dp[day][lastTask] = maxPoints;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * dp[day][lastTask] represents the max score from day 0 to 'day', considering
     * that on the NEXT day (day+1), 'lastTask' is performed.
     *
     * 1. Initialize Day 0: For each possible next task (0,1,2,3), calculate best choice for Day 0.
     * 2. Loop Days 1 to N-1: Compute best scores based on previous day's results.
     *
     * COMPLEXITY:
     * - Time: O(N * 4 * 3) -> O(N).
     * - Space: O(N * 4) -> O(N).
     */
    private static int tabulation(int[][] points) {
        int n = points.length;
        int[][] dp = new int[n][4];

        // 1. Initialize Base Case (Day 0)
        // If the restriction from Day 1 is 'lastTask', we pick max of others.
        dp[0][0] = Math.max(points[0][1], points[0][2]); // If next is 0, pick max(1,2)
        dp[0][1] = Math.max(points[0][0], points[0][2]); // If next is 1, pick max(0,2)
        dp[0][2] = Math.max(points[0][0], points[0][1]); // If next is 2, pick max(0,1)
        dp[0][3] = Math.max(points[0][0], Math.max(points[0][1], points[0][2])); // No restriction

        // 2. Fill table for Day 1 to N-1
        for (int day = 1; day < n; day++) {
            // Calculate for every possible restriction (0, 1, 2, 3) passed from next day
            for (int lastTask = 0; lastTask < 4; lastTask++) {
                dp[day][lastTask] = 0; // Reset max

                // Try performing every task 'task' on current 'day'
                for (int task = 0; task < 3; task++) {
                    if (task != lastTask) {
                        // Current points + Max points from previous day (where today's task becomes restriction)
                        int point = points[day][task] + dp[day - 1][task];
                        dp[day][lastTask] = Math.max(dp[day][lastTask], point);
                    }
                }
            }
        }

        // Return the answer for Day N-1 with NO restriction (lastTask = 3)
        return dp[n - 1][3];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED (BEST SOLUTION)
     * ----------------------------------------------------------------------
     * LOGIC:
     * In Tabulation, to calculate `dp[day]`, we only need `dp[day-1]`.
     * We can replace the 2D array with two 1D arrays: `prev` and `curr` of size 4.
     *
     * COMPLEXITY:
     * - Time: O(N * 4 * 3) -> O(N).
     * - Space: O(4) -> O(1). Constant space.
     */
    private static int spaceOptimized(int[][] points) {
        int n = points.length;
        int[] prev = new int[4];

        // 1. Initialize Base Case (Day 0)
        prev[0] = Math.max(points[0][1], points[0][2]);
        prev[1] = Math.max(points[0][0], points[0][2]);
        prev[2] = Math.max(points[0][0], points[0][1]);
        prev[3] = Math.max(points[0][0], Math.max(points[0][1], points[0][2]));

        // 2. Loop Days 1 to N-1
        for (int day = 1; day < n; day++) {
            int[] curr = new int[4]; // Temporary array for current day

            for (int lastTask = 0; lastTask < 4; lastTask++) {
                curr[lastTask] = 0;
                for (int task = 0; task < 3; task++) {
                    if (task != lastTask) {
                        // Current points + Previous result where restriction was 'task'
                        int point = points[day][task] + prev[task];
                        curr[lastTask] = Math.max(curr[lastTask], point);
                    }
                }
            }
            // Update prev to be the current day's result for the next iteration
            prev = curr;
        }

        return prev[3];
    }
}

/*
1. Where will be your final answer?Your final answer is located at dp[n-1][3] (which in this specific example is dp[2][3]).
Why?Look at your initial method call in the main method:recursive(n - 1, 3, meritPoints, dp)
Because the last line of your recursive method is return dp[day][lastTask] = maxPoint;,
the very last thing the recursion does before finishing completely is save the ultimate maximum value into dp[n-1][3].

2. How and What is getting cached in the dp array?
To understand the caching, we need to define what the dimensions of dp[day][lastTask] actually mean.
Row (day): Represents the current day you are calculating points for (from Day 0 to Day 2).
Column (lastTask): Represents the task that was performed on the day after this one.
It ranges from 0 to 3:0, 1, 2: The actual tasks.

3: A "dummy" task representing that no task has been performed yet (used only for the very last day to allow picking any task).
The Value (dp[day][lastTask]): This stores the maximum points you can achieve from Day 0 up to day,
GIVEN that lastTask was performed on day + 1.Step-by-Step Caching (The Recursion Unwinding)Because this is Top-Down (Memoization),
the code dives all the way down to Day 0 before it starts caching anything.
It works its way backward up the days.Day 0

(The Base Case):The recursion hits day == 0.
It calculates the max points for Day 0 based on whatever lastTask was passed to it.
Note a quirk in your code: In your if (day == 0) block, you simply return max;.
You do not save it to the dp array! Because of this,
Row 0 of your dp array will remain completely full of -1s. (This is perfectly fine logically, but it means Day 0 isn't cached).

Day 1 (Caching begins):Now the recursion steps back up to day = 1.Suppose the recursion is evaluating dp[1][0]
(meaning: "What are the max points from Day 0 to Day 1, if Task 0 is performed on Day 2?").
It tries Task 1: points[1][1] + recursive(0, 1) $\rightarrow$ $50 + 70 = 120$.
It tries Task 2: points[1][2] + recursive(0, 2) $\rightarrow$ $80 + 40 = 120$.
It takes the max (120) and executes dp[1][0] = 120.Boom. Data is cached.
If any other branch of the recursion ever asks for dp[1][0] again,
it returns 120 instantly instead of recalculating.

Day 2 (Final Answer):The recursion steps back up to day = 2 with lastTask = 3.
It checks Task 0, Task 1, and Task 2. I
t fetches the heavily cached results from Day 1 to calculate the totals.
It finds the absolute maximum (which is 210) and caches it in dp[2][3].
What your printed dp array will actually look like:If you run your main method with that exact matrix,
here is what prints out at the end, and why:Plaintext

2.Memoization     : 210
-1 -1 -1 -1
120 120 120 -1
-1 -1 -1 210

Decoding the output
Row 0 (-1 -1 -1 -1): As mentioned, your base case returns early without writing to dp[0][lastTask].
Row 1 (120 120 120 -1): The code calculated the max points up to Day 1 if the next task was 0, 1, or 2.
(It never calculated dp[1][3] because a dummy task is only used on the final day).
Row 2 (-1 -1 -1 210): The code only ever evaluated Day 2 with lastTask = 3.
The final answer is sitting right there at the end!
 */