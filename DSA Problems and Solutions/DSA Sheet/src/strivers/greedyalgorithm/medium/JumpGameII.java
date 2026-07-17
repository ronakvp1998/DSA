package strivers.greedyalgorithm.medium;
/**
 * ============================================================================
 * 🚀 MASTERCLASS: 45. Jump Game II
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer & Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * You are given a 0-indexed array of integers `nums` of length `n`. You are
 * initially positioned at index 0.
 *
 * Each element `nums[i]` represents the maximum length of a forward jump from
 * index `i`. In other words, if you are at index `i`, you can jump to any
 * index `(i + j)` where:
 *   - 0 <= j <= nums[i]
 *   - i + j < n
 *
 * Return the minimum number of jumps to reach index `n - 1`. The test cases
 * are generated such that you can reach index `n - 1`.
 *
 * EXAMPLES:
 * Example 1:
 * Input: nums = [2,3,1,1,4]
 * Output: 2
 * Explanation: The minimum number of jumps to reach the last index is 2.
 * Jump 1 step from index 0 to 1, then 3 steps to the last index.
 *
 * Example 2:
 * Input: nums = [2,3,0,1,4]
 * Output: 2
 *
 * CONSTRAINTS:
 * - 1 <= nums.length <= 10^4
 * - 0 <= nums[i] <= 1000
 * - It's guaranteed that you can reach nums[n - 1].
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Primary Focus)
 *
 * While Jump Game II can be modeled as a Dynamic Programming problem, the
 * explicitly requested Optimal approach is Greedy. We will structure the
 * roadmap as follows:
 *   Phase 1: Optimal Approach (Greedy)
 *   Phase 2: Brute Force Approach (Pure Recursion)
 *   Phase 3: Alternative Approach (DP Tabulation / Memoization)
 * ============================================================================
 */

import java.util.Arrays;
import java.util.List;

public class JumpGameII {

    /**
     * ========================================================================
     * PHASE 1: OPTIMAL APPROACH (Greedy) - The "Master it" Stage
     * ========================================================================
     *
     * INTUITION:
     * Instead of calculating the minimum jumps for every single index (like DP),
     * we can keep track of the maximum reach we can achieve within the current
     * jump's boundary.
     *
     * We maintain three variables:
     * 1. `jumps`: The number of jumps taken so far.
     * 2. `currentEnd`: The furthest index we can reach with the current number of jumps.
     * 3. `farthest`: The farthest index we can reach if we take one more jump from
     *    any of the indices we've traversed up to `currentEnd`.
     *
     * As we iterate through the array, we constantly update `farthest`. When our
     * loop index `i` reaches `currentEnd`, it means we have evaluated all the
     * jumping options for the current jump phase. We must now "take" the jump
     * (increment `jumps`), and our new boundary becomes the `farthest` we found.
     *
     * Note: We stop the loop at `n - 2` (or `nums.length - 1`) because we do not
     * need to jump *from* the last index.
     *
     * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N) where N is the length of `nums`. We do a single pass.
     * - Space Complexity: O(1) auxiliary space. We only use primitive variables.
     * ========================================================================
     */
    public int jumpGreedy(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0; // No jumps needed if we're already at the end.
        }

        int jumps = 0;
        int currentEnd = 0;
        int farthest = 0;

        // Iterate up to the second-to-last element.
        for (int i = 0; i < nums.length - 1; i++) {
            // Update the farthest index we can reach from the current position
            farthest = Math.max(farthest, i + nums[i]);

            // If we have reached the boundary of the current jump, we must jump
            if (i == currentEnd) {
                jumps++;
                currentEnd = farthest;

                // Early exit optimization: if our current jump boundary reaches or
                // exceeds the last index, we can stop immediately.
                if (currentEnd >= nums.length - 1) {
                    break;
                }
            }
        }
        return jumps;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Recursion) - The "Think it" Stage
     * ========================================================================
     *
     * INTUITION:
     * The most rudimentary way to solve this is to stand at index 0 and try
     * jumping 1 step, 2 steps, ..., up to `nums[0]` steps. From each landing
     * spot, we recursively ask, "What is the minimum jumps to the end from here?"
     *
     * The minimum of all those recursive calls + 1 (for the jump we just took)
     * will be our answer.
     *
     * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(K^N) in the worst case, where K is the max jump length
     *   and N is the size of the array. The branching factor is massive.
     * - Space Complexity: O(N) auxiliary stack space for the recursion tree.
     * ========================================================================
     */
    public int jumpBruteForce(int[] nums) {
        return solveRecursive(nums, 0);
    }

    private int solveRecursive(int[] nums, int currentIndex) {
        // Base case: If we've reached or exceeded the last index, 0 more jumps needed.
        if (currentIndex >= nums.length - 1) {
            return 0;
        }

        int minJumps = 100000; // Acts as "Infinity" to avoid overflow with +1

        // Try every possible jump length from the current index
        int maxJumpLimit = Math.min(nums.length - 1, currentIndex + nums[currentIndex]);
        for (int nextIndex = currentIndex + 1; nextIndex <= maxJumpLimit; nextIndex++) {
            int jumpsFromNext = solveRecursive(nums, nextIndex);
            if (jumpsFromNext != 100000) {
                minJumps = Math.min(minJumps, jumpsFromNext + 1);
            }
        }

        return minJumps;
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (DP Tabulation) - The "Build it" Stage
     * ========================================================================
     *
     * INTUITION:
     * We can optimize the brute force by remembering the answers to overlapping
     * subproblems. Instead of recursion (Top-Down), we can use an array (Bottom-Up).
     *
     * Let `dp[i]` be the minimum number of jumps needed to reach the end from index `i`.
     * We can build this from right to left.
     *
     * INITIAL STATE OF DP ARRAY (for nums = [2, 3, 1, 1, 4]):
     * Index:  [   0,   1,   2,   3,   4 ]
     * Values: [ INF, INF, INF, INF,   0 ]
     * (We need 0 jumps from the last index to the last index).
     *
     * FINAL STATE OF DP ARRAY (for nums = [2, 3, 1, 1, 4]):
     * Index:  [  0,  1,  2,  3,  4 ]
     * Values: [  2,  1,  2,  1,  0 ]
     * Output is dp[0] = 2.
     *
     * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N^2) because for every index `i`, we might iterate
     *   through up to N subsequent indices.
     * - Space Complexity: O(N) heap space for the `dp` array. No call stack used.
     * ========================================================================
     */
    public int jumpDP(int[] nums) {
        int n = nums.length;
        if (n <= 1) return 0;

        int[] dp = new int[n];
        // Initialize the array with an arbitrarily large number
        Arrays.fill(dp, 100000);

        // Base case: 0 jumps required to reach the last index from the last index
        dp[n - 1] = 0;

        // Build from right to left
        for (int i = n - 2; i >= 0; i--) {
            int maxJumpLimit = Math.min(n - 1, i + nums[i]);
            // Check all reachable nodes and find the one that requires min jumps
            for (int j = i + 1; j <= maxJumpLimit; j++) {
                dp[i] = Math.min(dp[i], 1 + dp[j]);
            }
        }

        return dp[0];
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     * Comprehensive tests against standard scenarios and zero-value edge cases.
     * Utilizing Java 8 Stream API for clean test execution.
     */
    public static void main(String[] args) {
        JumpGameII solver = new JumpGameII();

        // Structure: List of Test Cases (Input Array)
        List<int[]> testCases = Arrays.asList(
                new int[]{2, 3, 1, 1, 4}, // Standard case 1
                new int[]{2, 3, 0, 1, 4}, // Standard case 2 (with a 0)
                new int[]{0},             // Edge case: length 1, no jump needed
                new int[]{1},             // Edge case: length 1, no jump needed
                new int[]{1, 2, 3},       // Linear increasing
                new int[]{7, 0, 9, 6, 9, 6, 1, 7, 9, 0, 1, 2, 9, 0, 3} // Larger case
        );

        System.out.println("🚀 Executing Jump Game II Testing Suite 🚀\n");

        testCases.forEach(testCase -> {
            System.out.println("Input: " + Arrays.toString(testCase));

            long startTimeGreedy = System.nanoTime();
            int resGreedy = solver.jumpGreedy(testCase);
            long timeGreedy = System.nanoTime() - startTimeGreedy;

            long startTimeDP = System.nanoTime();
            int resDP = solver.jumpDP(testCase);
            long timeDP = System.nanoTime() - startTimeDP;

            // Only run Brute Force on small arrays to avoid TLE during testing
            String resBrute = "Skipped (Length > 10)";
            if (testCase.length <= 10) {
                resBrute = String.valueOf(solver.jumpBruteForce(testCase));
            }

            System.out.printf("  -> Optimal Greedy : %d jumps [Time: %d ns]%n", resGreedy, timeGreedy);
            System.out.printf("  -> DP Tabulation  : %d jumps [Time: %d ns]%n", resDP, timeDP);
            System.out.printf("  -> Brute Force    : %s jumps%n", resBrute);
            System.out.println("-".repeat(50));
        });
    }
}