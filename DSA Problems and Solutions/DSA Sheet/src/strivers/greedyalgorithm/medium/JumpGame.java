package strivers.greedyalgorithm.medium;
/**
 * ============================================================================
 * 55. Jump Game
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * You are given an integer array nums. You are initially positioned at the
 * array's first index, and each element in the array represents your maximum
 * jump length at that position.
 *
 * Return true if you can reach the last index, or false otherwise.
 *
 * Example 1:
 * Input: nums = [2,3,1,1,4]
 * Output: true
 * Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
 *
 * Example 2:
 * Input: nums = [3,2,1,0,4]
 * Output: false
 * Explanation: You will always arrive at index 3 no matter what. Its maximum
 * jump length is 0, which makes it impossible to reach the last index.
 *
 * Constraints:
 * 1 <= nums.length <= 10^4
 * 0 <= nums[i] <= 10^5
 *
 * ============================================================================
 * CONCEPTUAL VISUALIZATION (Dynamic Programming to Greedy Transition)
 * ============================================================================
 * Let f(i) be a boolean function representing "Can we reach the end starting from index i?"
 *
 * Recurrence Relation:
 * f(i) = true IF there exists a jump 'j' (where 1 <= j <= nums[i]) such that f(i + j) == true.
 *
 * Recursion Tree for f(0) with nums = [2, 3, 1, 1, 4]:
 * (Target is index 4)
 *
 *                           f(0) [nums[0]=2]
 *                          /                \
 *                     jump 1               jump 2
 *                     /                      \
 *               f(1) [nums[1]=3]         f(2) [nums[2]=1]
 *              /      |       \               |
 *         jump 1   jump 2    jump 3         jump 1
 *          /          |         \             |
 *        f(2)       f(3)       f(4)          f(3)
 *         |          |        (TARGET)        |
 *      jump 1     jump 1                    jump 1
 *         |          |                        |
 *        f(3)       f(4)                     f(4)
 *         |        (TARGET)                 (TARGET)
 *      jump 1
 *         |
 *        f(4)
 *       (TARGET)
 *
 * Overlapping subproblems are clearly visible (e.g., f(2) and f(3) are calculated multiple times).
 *
 * ============================================================================
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class JumpGame {

    /**
     * ============================================================================
     * PHASE 1: Brute Force Recursion - The "Think it" stage.
     * ============================================================================
     * Detailed Intuition:
     * Starting from index 0, we try every possible jump length available at our
     * current position. If any of those jumps eventually lead to the last index,
     * we return true. We explore all paths using Depth First Search (DFS).
     *
     * Complexity Analysis:
     * Time: O(2^N) - In the worst case (e.g., array of all 1s), we branch into
     *       multiple paths at every step, leading to an exponential number of paths.
     * Space: O(N) - Auxiliary stack space for recursion depth. O(1) heap space.
     */
    public boolean phase1BruteForce(int[] nums) {
        return canJumpRecursive(nums, 0);
    }

    private boolean canJumpRecursive(int[] nums, int position) {
        if (position >= nums.length - 1) return true;

        int furthestJump = Math.min(position + nums[position], nums.length - 1);

        // Try all possible jumps from current position
        for (int nextPosition = position + 1; nextPosition <= furthestJump; nextPosition++) {
            if (canJumpRecursive(nums, nextPosition)) {
                return true;
            }
        }
        return false;
    }

    /**
     * ============================================================================
     * PHASE 2: Top-Down Memoization - The "Refine it" stage.
     * ============================================================================
     * Detailed Intuition:
     * Notice in the recursion tree that we repeatedly ask "Can we reach the end
     * from index 'i'?". We can cache this result. We use a Boolean array where:
     * null = Unvisited, true = Good index, false = Bad index.
     *
     * Complexity Analysis:
     * Time: O(N^2) - For every element in the array (N), we might look at all
     *       elements to its right in the worst case (N). Thus, N * N.
     * Space: O(N) - O(N) heap space for the memoization array + O(N) auxiliary
     *       stack space for recursion depth.
     */
    public boolean phase2Memoization(int[] nums) {
        Boolean[] memo = new Boolean[nums.length];
        memo[nums.length - 1] = true; // Base case: end is always reachable from itself
        return canJumpMemo(nums, 0, memo);
    }

    private boolean canJumpMemo(int[] nums, int position, Boolean[] memo) {
        if (memo[position] != null) {
            return memo[position];
        }

        int furthestJump = Math.min(position + nums[position], nums.length - 1);
        for (int nextPosition = position + 1; nextPosition <= furthestJump; nextPosition++) {
            if (canJumpMemo(nums, nextPosition, memo)) {
                memo[position] = true;
                return true;
            }
        }

        memo[position] = false;
        return false;
    }

    /**
     * ============================================================================
     * PHASE 3: Bottom-Up Tabulation - The "Build it" stage.
     * ============================================================================
     * Detailed Intuition:
     * We can remove recursion overhead by building our answer iteratively. We start
     * from the right (end of the array) and work backwards. An index is "Good" if
     * it can jump to another "Good" index.
     *
     * EXACT DEFAULT STATE OF DP ARRAY (Post base-case init, Example 1):
     * [ false, false, false, false, true ]
     *
     * EXACT FINAL STATE OF DP ARRAY (Example 1):
     * [ true, true, true, true, true ]
     *
     * Complexity Analysis:
     * Time: O(N^2) - We iterate N times, and at each step, we scan up to N elements ahead.
     * Space: O(N) - O(N) heap space for the DP array. O(1) auxiliary stack space.
     */
    public boolean phase3Tabulation(int[] nums) {
        int n = nums.length;
        boolean[] dp = new boolean[n];
        dp[n - 1] = true; // The last index is naturally reachable

        for (int i = n - 2; i >= 0; i--) {
            int furthestJump = Math.min(i + nums[i], n - 1);
            for (int j = i + 1; j <= furthestJump; j++) {
                if (dp[j]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[0];
    }

    /**
     * ============================================================================
     * PHASE 4: Optimal Greedy Approach (Forward) - The "Perfect it" stage.
     * ============================================================================
     * Detailed Intuition:
     * Do we really need an array to track every reachable state? No.
     * We just need to track the MAXIMUM index we can reach so far.
     * As we iterate through the array, if our current index `i` is greater than
     * `maxReach`, it means we are stuck and cannot even reach our current position.
     * Otherwise, we update `maxReach` to be the max of its current value and
     * `i + nums[i]`. If `maxReach` exceeds or equals the last index, we win!
     *
     * Complexity Analysis:
     * Time: O(N) - A single pass through the array.
     * Space: O(1) - O(1) heap space (single integer). O(1) auxiliary stack space.
     */
    public boolean phase4OptimalGreedyForward(int[] nums) {
        int maxReach = 0;

        for (int i = 0; i < nums.length; i++) {
            // If the current index is beyond the maximum reach, we are stuck!
            if (i > maxReach) {
                return false;
            }

            // Update the furthest we can reach
            maxReach = Math.max(maxReach, i + nums[i]);

            // Early exit optimization: if we can already reach the end, stop checking
            if (maxReach >= nums.length - 1) {
                return true;
            }
        }

        return true;
    }

    /**
     * ============================================================================
     * PHASE 5: Alternative Greedy Approach (Backward)
     * ============================================================================
     * Detailed Intuition:
     * This directly maps to our DP Tabulation logic, but optimized to O(1) space.
     * Instead of checking if `dp[j] == true` for all reachable `j`, we just keep
     * track of the *closest* good index to us. Let's call it `lastGoodIndex`.
     * Initially, `lastGoodIndex` is the destination (`n - 1`). Moving right-to-left,
     * if from index `i` we can reach `lastGoodIndex` (i.e., `i + nums[i] >= lastGoodIndex`),
     * then `i` itself becomes the new `lastGoodIndex`. We shift the goalposts!
     *
     * Complexity Analysis:
     * Time: O(N) - A single pass from right to left.
     * Space: O(1) - O(1) heap space. O(1) auxiliary stack space.
     */
    public boolean phase5OptimalGreedyBackward(int[] nums) {
        int lastGoodIndex = nums.length - 1;

        for (int i = nums.length - 2; i >= 0; i--) {
            // Can we jump from our current position to the last known good position?
            if (i + nums[i] >= lastGoodIndex) {
                // If yes, shift the "goalpost" to our current position
                lastGoodIndex = i;
            }
        }

        // If the goalpost has been successfully shifted all the way to index 0, we win.
        return lastGoodIndex == 0;
    }

    /**
     * ============================================================================
     * TESTING SUITE
     * ============================================================================
     * Executing comprehensive tests across approaches using Java 8 Streams.
     */
    public static void main(String[] args) {
        JumpGame solver = new JumpGame();

        // Define Test Cases
        int[][] testCases = {
                {2, 3, 1, 1, 4},             // Example 1: Standard win
                {3, 2, 1, 0, 4},             // Example 2: Standard fail (stuck at 0)
                {0},                         // Edge Case: Single element (already at end)
                {1, 2, 3},                   // Simple progression
                {1, 0, 1, 0},                // Multiple zeroes fail
                {2, 0, 0},                   // Exact jump to end
                {5, 4, 3, 2, 1, 0, 0}        // Fail cascade
        };

        String[] approachNames = {
                "Phase 1: Brute Force (O(2^N)) ",
                "Phase 2: Memoization (O(N^2)) ",
                "Phase 3: Tabulation (O(N^2))  ",
                "Phase 4: Greedy Fwd (O(N))    ",
                "Phase 5: Greedy Bwd (O(N))    "
        };

        System.out.println("=================================================");
        System.out.println("           TESTING JUMP GAME SOLUTIONS           ");
        System.out.println("=================================================\n");

        IntStream.range(0, testCases.length).forEach(i -> {
            int[] nums = testCases[i];

            System.out.println("Test Case " + (i + 1) + ": " + Arrays.toString(nums));

            boolean[] results = {
                    solver.phase1BruteForce(nums),
                    solver.phase2Memoization(nums),
                    solver.phase3Tabulation(nums),
                    solver.phase4OptimalGreedyForward(nums),
                    solver.phase5OptimalGreedyBackward(nums)
            };

            for (int j = 0; j < results.length; j++) {
                System.out.println(approachNames[j] + " => " + results[j]);
            }
            System.out.println("-------------------------------------------------");
        });

        System.out.println("All implementations executed successfully.");
    }
}