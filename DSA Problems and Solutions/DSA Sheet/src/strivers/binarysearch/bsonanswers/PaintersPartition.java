package strivers.binarysearch.bsonanswers;

/**
 * ============================================================================
 * MASTERCLASS DSA EVALUATION
 * ============================================================================
 * * Painter's Partition Problem
 * Solved | Hard (Conceptually identical to Book Allocation)
 * * * PROBLEM STATEMENT:
 * Given an array/list of length ‘N’, where the array/list represents the boards
 * and each element of the given array/list represents the length of each board.
 * Some ‘K’ numbers of painters are available to paint these boards. Consider
 * that each unit of a board takes 1 unit of time to paint.
 * * You are supposed to return the area of the minimum time to get this job done
 * of painting all the ‘N’ boards under the constraint that any painter will
 * only paint the continuous sections of boards.
 * * * EXAMPLES:
 * Example 1:
 * Input Format: N = 4, boards[] = {5, 5, 5, 5}, k = 2
 * Result: 10
 * Explanation: We can divide the boards into 2 equal-sized partitions, so each
 * painter gets 10 units of the board and the total time taken is 10.
 * * Example 2:
 * Input Format: N = 4, boards[] = {10, 20, 30, 40}, k = 2
 * Result: 60
 * Explanation: We can divide the first 3 boards for one painter and the last
 * board for the second painter. The maximum time taken is by the second painter (40)
 * but wait, 10+20+30 = 60 for the first painter. Max(60, 40) = 60.
 * * * CONSTRAINTS:
 * - 1 <= N <= 10^5
 * - 1 <= boards[i] <= 10^9
 * - 1 <= k <= 10^5
 * ============================================================================
 */
public class PaintersPartition {

    /**
     * ========================================================================
     * PHASE 1: Best and Recommended Approach (Binary Search on Answer Space)
     * ========================================================================
     * * APPROACH & STEPS:
     * This problem is mathematically isomorphic to "Allocate Minimum Number of Pages"
     * and "Split Array Largest Sum". It falls under the classic "Minimax" category
     * commonly highlighted in advanced DSA revision sheets.
     * * 1. Define the Search Space:
     * - The absolute minimum time required (`low`) is the maximum single board length
     * (`max(boards)`). A painter cannot split a single board, so the largest board
     * will act as the baseline bottleneck.
     * - The absolute maximum time required (`high`) is if a single painter paints
     * every single board (`sum(boards)`).
     * 2. Apply Binary Search: Calculate `mid` as the proposed maximum time allowed
     * per painter.
     * 3. Greedily Check Feasibility: Iterate through the boards. Keep adding board
     * lengths to the current painter's workload. If adding the next board exceeds
     * `mid`, assign it to a new painter.
     * 4. Decision Logic:
     * - If `paintersRequired <= k`, the `mid` time limit is feasible! Record it,
     * but try to push the painters harder by lowering the maximum time allowed
     * (`high = mid - 1`).
     * - If `paintersRequired > k`, the time limit `mid` is too strict, forcing us
     * to hire more than `k` painters. We must relax the time constraint
     * (`low = mid + 1`).
     * * * DETAILED INTUITION:
     * Because the required number of painters decreases monotonically as the allowed
     * time increases, the search space is perfectly sorted. We leverage Binary Search
     * to logarithmically home in on the exact minimum maximum-time, completely
     * bypassing a linear scan.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N * log(Sum - Max)), where N is the number of boards.
     * Finding the min/max bounds takes O(N). The search space halves log(Sum - Max)
     * times, and each iteration requires an O(N) greedy simulation.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1) (Iterative execution).
     * - Heap Space: O(1) (No dynamic collections required).
     */
    public static int findLargestMinDistanceOptimal(int[] boards, int k) {
        if (boards == null || boards.length == 0) return 0;

        int low = 0;
        long high = 0; // Guarding against integer overflow for massive sums
        // low = max, high = sum
        for (int board : boards) {
            low = Math.max(low, board);
            high += board;
        }

        long optimalResult = high;

        while (low <= high) {
            long mid = low + (high - low) / 2;

            if (countPainters(boards, mid) <= k) {
                optimalResult = mid; // Feasible, but can we do it faster?
                high = mid - 1;
            } else {
                low = (int) mid + 1; // Not enough time, painters are overflowing
            }
        }

        return (int) optimalResult;
    }

    // Helper method to greedily simulate the painting process
    private static int countPainters(int[] boards, long maxTimeAllowed) {
        int paintersRequired = 1;
        long currentTimeSpent = 0;

        for (int board : boards) {
            if (currentTimeSpent + board > maxTimeAllowed) {
                // Current painter is out of time, bring in the next painter
                paintersRequired++;
                currentTimeSpent = board;
            } else {
                // Painter continues working
                currentTimeSpent += board;
            }
        }

        return paintersRequired;
    }

    /**
     * ========================================================================
     * PHASE 2: Brute Force Approach - The "Think it" stage
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Calculate the minimum possible bound (`low` = max element) and the
     * maximum possible bound (`high` = sum of elements).
     * 2. Run a linear loop starting from `time = low` up to `high`.
     * 3. For each `time`, simulate the board allocation using the greedy helper.
     * 4. The very first `time` that allows the job to be finished with `<= k`
     * painters is inherently the minimum possible time.
     * * * DETAILED INTUITION:
     * We simulate a project manager testing deadlines day by day. "Can we finish
     * with max 40 hours per guy? No. 41? No. 60? Yes!" Since we start from the
     * tightest theoretical deadline, the first "Yes" is mathematically the optimum.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N * (Sum - Max)). The loop runs `Sum - Max` times,
     * with an O(N) array traversal inside. Under competitive constraints (e.g.,
     * elements up to 10^9), this guarantees a Time Limit Exceeded (TLE).
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1).
     * - Heap Space: O(1).
     */
    public static int findLargestMinDistanceBruteForce(int[] boards, int k) {
        if (boards == null || boards.length == 0) return 0;

        int low = 0;
        long high = 0;

        for (int board : boards) {
            low = Math.max(low, board);
            high += board;
        }

        for (long time = low; time <= high; time++) {
            if (countPainters(boards, time) <= k) {
                return (int) time;
            }
        }

        return (int) low;
    }

    /**
     * ========================================================================
     * PHASE 3: Alternative Approaches (Dynamic Programming)
     * ========================================================================
     * * Approach: MCM-style Dynamic Programming
     * - The problem can be modeled as partitioning an array into `k` contiguous
     * subarrays such that the maximum sum among these subarrays is minimized.
     * - `dp[i][j]` = minimum time to paint the first `i` boards using `j` painters.
     * - Transition requires iterating over a split point `p` to find the minimum of
     * `max(dp[p][j-1], sum(boards[p...i-1]))`.
     * * Why it fails in practice:
     * - DP Time Complexity is O(K * N^2). With N = 10^5, N^2 = 10^10 operations,
     * which fundamentally shatters typical 1-second execution limits.
     * - DP Space Complexity is O(K * N), causing Memory Limit Exceeded (MLE).
     * - Binary Search is the absolute, undisputed champion for monotonic Minimax
     * threshold problems.
     */

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("Running Painter's Partition Test Suite...\n");

        // Test Case 1: Standard case (Example 1)
        int[] boards1 = {5, 5, 5, 5};
        int k1 = 2;
        runTestCase(1, boards1, k1, 10);

        // Test Case 2: Standard case (Example 2)
        int[] boards2 = {10, 20, 30, 40};
        int k2 = 2;
        runTestCase(2, boards2, k2, 60);

        // Test Case 3: Edge Case - k equals 1 (One painter does everything)
        int[] boards3 = {10, 20, 30, 40};
        int k3 = 1;
        runTestCase(3, boards3, k3, 100);

        // Test Case 4: Edge Case - k >= N (More painters than boards)
        // Bottleneck is simply the largest single board.
        int[] boards4 = {10, 20, 30, 40};
        int k4 = 5;
        runTestCase(4, boards4, k4, 40);

        // Test Case 5: Large Values (Integer overflow simulation)
        // Summing these exceeds standard 32-bit signed integer limits.
        int[] boards5 = {1000000000, 1000000000, 1000000000};
        int k5 = 2;
        runTestCase(5, boards5, k5, 2000000000);
    }

    private static void runTestCase(int testNumber, int[] boards, int k, int expected) {
        long startTime = System.nanoTime();
        int resultOptimal = findLargestMinDistanceOptimal(boards, k);
        long endTime = System.nanoTime();

        System.out.println("Test Case " + testNumber + ":");
        System.out.println("Input: boards = " + java.util.Arrays.toString(boards) + ", k = " + k);
        System.out.println("Expected: " + expected);
        System.out.println("Output (Optimal): " + resultOptimal);
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Status: " + (resultOptimal == expected ? "✅ PASS" : "❌ FAIL"));
        System.out.println("--------------------------------------------------");
    }
}