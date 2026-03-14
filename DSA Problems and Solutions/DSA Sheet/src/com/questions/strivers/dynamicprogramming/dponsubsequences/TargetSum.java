package com.questions.strivers.dynamicprogramming.dponsubsequences;
/**
 * ==================================================================================================
 * MASTERCLASS: TARGET SUM (LeetCode 494)
 * ==================================================================================================
 * * ### 1. Header & Problem Context
 * * **Problem Statement:**
 * You are given an integer array 'nums' and an integer 'target'.
 * You want to build an expression out of nums by adding one of the symbols '+' or '-'
 * before each integer in nums and then concatenate all the integers.
 * Return the number of different expressions that you can build, which evaluates to target.
 * Example 1:
 * Input: nums = [1,1,1,1,1], target = 3
 * Output: 5
 * Explanation: There are 5 ways to assign symbols to make the sum of nums be target 3.
 * -1 + 1 + 1 + 1 + 1 = 3
 * +1 - 1 + 1 + 1 + 1 = 3
 * +1 + 1 - 1 + 1 + 1 = 3
 * +1 + 1 + 1 - 1 + 1 = 3
 * +1 + 1 + 1 + 1 - 1 = 3
 * Example 2:
 *
 * Input: nums = [1], target = 1
 * Output: 1
 *
 * * **Constraints:**
 * - 1 <= nums.length <= 20
 * - 0 <= nums[i] <= 1000
 * - 0 <= sum(nums[i]) <= 1000
 * - -1000 <= target <= 1000
 * * --------------------------------------------------------------------------------------------------
 * **Conceptual Visualization & Mathematical Reduction**
 * * *Crucial Senior Insight:*
 * Trying to memoize the +/- decisions directly is messy because the 'currentSum' can be negative.
 * We can reduce this problem to "Count Subsets with Sum K" using basic math:
 * 1. Let S1 be the sum of elements assigned '+'
 * 2. Let S2 be the sum of elements assigned '-'
 * 3. We know: S1 - S2 = target
 * 4. We know: S1 + S2 = totalSum
 * 5. Add the equations: 2 * S1 = target + totalSum
 * 6. Therefore: S1 = (target + totalSum) / 2
 * * **Conclusion:** The problem simply asks: "Find the number of subsets in 'nums' that sum up to S1."
 * * **Recursion Tree (For Subset Sum S1):**
 * Example: nums = [1, 1, 1], S1 = 2 (derived from target=1, total=3)
 * f(index, sum) -> returns ways
 * *                             f(2, 2)
 *                              [nums[2]=1]
 *                              /         \
 *                  NOT PICK (sum=2)       PICK (sum=2-1=1)
 *                      /                      \
 *                  f(1, 2)                   f(1, 1)
 *                  [nums[1]=1]               [nums[1]=1]
 *                   /       \                 /        \
 *              NP(2)       P(1)            NP(1)      P(0)
 *               /           \               /          \
 *              f(0, 2)       f(0, 1)       f(0, 1)      f(0, 0)
 *              (Ret 0)       (Ret 1)       (Ret 1)      (Ret 1)
 * * ==================================================================================================
 * * **Final DP Array Filled:**
 *      * Index (i) \ Sum (s) ->  0   1   2
 *      * ---------------------------------
 *      * 0 (nums[0]: 1)       |  1   1   0
 *      * 1 (nums[1]: 1)       |  1   2   1  <- dp[1][1] = 2 because we can pick either 1
 *      * 2 (nums[2]: 1)       |  1   3   3  <- Answer is 3 (Three ways to make sum 2)
 */

import java.util.Arrays;

public class TargetSum {

    public static void main(String[] args) {
        System.out.println("=== LeetCode 494: Target Sum Testing Suite ===\n");

        int[] nums1 = {1, 1, 1, 1, 1}; int target1 = 3;  // Expected: 5
        int[] nums2 = {1};             int target2 = 1;  // Expected: 1
        int[] nums3 = {0, 0, 0, 0, 0, 0, 0, 0, 1}; int target3 = 1; // Expected: 256 (Zero Trap!)

        System.out.println("Test 1 (Expected 5)   : \n" + runAllPhases(nums1, target1));
        System.out.println("Test 2 (Expected 1)   : \n" + runAllPhases(nums2, target2));
        System.out.println("Test 3 (Expected 256) : \n" + runAllPhases(nums3, target3));
    }

    private static String runAllPhases(int[] nums, int target) {
        int totalSum = Arrays.stream(nums).sum();

        // Edge Cases for Math Reduction
        if (totalSum - Math.abs(target) < 0 || (totalSum + target) % 2 != 0) {
            return "  All Phases: 0 (Impossible Target)";
        }

        int subsetTarget = (totalSum + target) / 2;
        int n = nums.length;

        // Phase 1: Brute Force (Direct +/- approach)
        int p1 = phase1BruteForce(n - 1, 0, nums, target);

        // Phase 2: Memoization (Using Subset Sum reduction)
        int[][] dp = new int[n][subsetTarget + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        int p2 = phase2Memoization(n - 1, subsetTarget, nums, dp);

        // Phase 3 & 4
        int p3 = phase3Tabulation(nums, subsetTarget);
        int p4 = phase4SpaceOptimized(nums, subsetTarget);

        return String.format("  P1 (Brute): %d \n  P2 (Memo):  %d \n  P3 (Tab):   %d \n  P4 (Opt):   %d\n", p1, p2, p3, p4);
    }

    /**
     * ==========================================================================
     * Phase 1: Brute Force Recursion (The "Think it" stage)
     * ==========================================================================
     * **Detailed Intuition:**
     * Before doing the fancy math reduction, this is the raw implementation of the
     * problem statement. We start at the last index and branch into two possibilities:
     * 1. Add the current number.
     * 2. Subtract the current number.
     * When we exhaust all numbers (index < 0), we check if our accumulated sum == target.
     * * **Complexity Analysis:**
     * - Time Complexity: O(2^N) - We literally branch into + and - for every number.
     * - Space Complexity: O(N) - Maximum depth of the recursion tree.
     */
    public static int phase1BruteForce(int index, int currentSum, int[] nums, int target) {
        if (index < 0) {
            return currentSum == target ? 1 : 0;
        }

        // Branch 1: Assign '+'
        int add = phase1BruteForce(index - 1, currentSum + nums[index], nums, target);

        // Branch 2: Assign '-'
        int subtract = phase1BruteForce(index - 1, currentSum - nums[index], nums, target);

        return add + subtract;
    }

    /**
     * ==========================================================================
     * Phase 2: Top-Down Memoization (The "Refine it" stage)
     * ==========================================================================
     * **Detailed Intuition:**
     * We transition from the O(2^N) +/- approach to the O(N*K) "Subset Sum" approach
     * using the mathematical reduction S1 = (totalSum + target) / 2.
     * This eliminates negative targets, allowing us to use a standard 2D array.
     * *CRITICAL:* We must handle the "Zero Trap". If a subset needs to sum to 0,
     * and we encounter a '0' in the array, it counts as 2 ways (pick it or don't pick it).
     * * **Complexity Analysis:**
     * - Time Complexity: O(N * S1) where S1 is the reduced target sum.
     * - Space Complexity: O(N * S1) for Heap DP Matrix + O(N) for Stack.
     */
    public static int phase2Memoization(int i, int s1, int[] nums, int[][] dp) {
        // Base Case: Must handle zeros dynamically!
        if (i == 0) {
            if (s1 == 0 && nums[0] == 0) return 2; // Can pick or not pick 0
            if (s1 == 0 || nums[0] == s1) return 1; // Target is 0, OR element matches
            return 0;
        }

        if (dp[i][s1] != -1) return dp[i][s1];

        int notPick = phase2Memoization(i - 1, s1, nums, dp);

        int pick = 0;
        if (nums[i] <= s1) {
            pick = phase2Memoization(i - 1, s1 - nums[i], nums, dp);
        }

        return dp[i][s1] = pick + notPick;
    }

    /**
     * ==========================================================================
     * Phase 3: Bottom-Up Tabulation (The "Build it" stage)
     * ==========================================================================
     * **Detailed Intuition:**
     * We convert Phase 2 into iterative loops. We carefully initialize row 0 based
     * on the presence of a '0' in the array to avoid missing valid subsets.
     * * **DP Array State Before Loops (Base Cases Initialization):**
     * Example: nums = [1, 1, 1], Target S1 = 2
     * Index (i) \ Sum (s) ->  0   1   2
     * ---------------------------------
     * 0 (nums[0]: 1)       |  1   1   0  <- Pre-filled! (1 way to make 0, 1 way to make 1)
     * 1 (nums[1]: 1)       |  0   0   0
     * 2 (nums[2]: 1)       |  0   0   0
     * * **Final DP Array Filled:**
     * Index (i) \ Sum (s) ->  0   1   2
     * ---------------------------------
     * 0 (nums[0]: 1)       |  1   1   0
     * 1 (nums[1]: 1)       |  1   2   1  <- dp[1][1] = 2 because we can pick either 1
     * 2 (nums[2]: 1)       |  1   3   3  <- Answer is 3 (Three ways to make sum 2)
     * * **Complexity Analysis:**
     * - Time Complexity: O(N * S1)
     * - Space Complexity: O(N * S1) completely in Heap space.
     */
    public static int phase3Tabulation(int[] nums, int target) {
        int n = nums.length;
        int[][] dp = new int[n][target + 1];

        // 1. Initialize Base Case for Index 0
        if (nums[0] == 0) {
            dp[0][0] = 2; // Two ways to make sum 0: {} and {0}
        } else {
            dp[0][0] = 1; // One way to make sum 0: {}
        }

        if (nums[0] != 0 && nums[0] <= target) {
            dp[0][nums[0]] = 1; // One way to make sum nums[0]
        }

        // 2. Iterate DP Table
        for (int i = 1; i < n; i++) {
            for (int s = 0; s <= target; s++) {

                int notPick = dp[i - 1][s];

                int pick = 0;
                if (nums[i] <= s) {
                    pick = dp[i - 1][s - nums[i]];
                }

                dp[i][s] = pick + notPick;
            }
        }

        return dp[n - 1][target];
    }

    /**
     * ==========================================================================
     * Phase 4: Space Optimization (The "Perfect it" stage)
     * ==========================================================================
     * **Detailed Intuition:**
     * Just like standard 0/1 Knapsack, the `notPick` and `pick` decisions in Tabulation
     * ONLY rely on the row immediately above it (`i - 1`).
     * We can compress the 2D matrix into a single 1D array of size `S1 + 1`.
     * *CRITICAL:* We MUST traverse the target sum BACKWARDS (target down to 0).
     * If we traverse forwards, we will overwrite data needed for the current iteration,
     * effectively reusing the same number multiple times (Unbounded Knapsack bug).
     * * **Complexity Analysis:**
     * - Time Complexity: O(N * S1)
     * - Space Complexity: O(S1) - Flattened 2D matrix to 1D array.
     */
    public static int phase4SpaceOptimized(int[] nums, int target) {
        int[] dp = new int[target + 1];

        // 1. Base Case Initialization
        if (nums[0] == 0) dp[0] = 2;
        else dp[0] = 1;

        if (nums[0] != 0 && nums[0] <= target) {
            dp[nums[0]] = 1;
        }

        // 2. Iterate Array
        for (int i = 1; i < nums.length; i++) {
            // Traverse BACKWARDS for 0/1 Knapsack pattern
            for (int s = target; s >= 0; s--) {

                int notPick = dp[s];

                int pick = 0;
                if (nums[i] <= s) {
                    pick = dp[s - nums[i]];
                }

                dp[s] = pick + notPick;
            }
        }

        return dp[target];
    }

    /**
     * ==========================================================================
     * Phase 5: Alternative Approaches
     * ==========================================================================
     * 1. Direct Offset DP (Without Math Reduction):
     * - If we didn't want to use the math reduction, we could directly memoize the
     * raw currentSum. Since sums can be negative, we would shift all sums by `totalSum`
     * so they fit in a positive array index: `dp[index][currentSum + totalSum]`.
     * - This uses an array of size `[N][2 * totalSum + 1]`. The math reduction
     * is vastly superior as it cuts the space requirement by more than half.
     * * 2. Meet in the Middle:
     * - If N was slightly larger (e.g., N=40) and target was massive, O(N * target) DP
     * would MLE/TLE. Meet in the middle splits the array into two halves of 20,
     * generates all 2^20 sums, and uses Binary Search / Hash Maps to find the target.
     */
}