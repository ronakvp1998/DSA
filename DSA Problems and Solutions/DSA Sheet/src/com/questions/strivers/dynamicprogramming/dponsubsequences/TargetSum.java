package com.questions.strivers.dynamicprogramming.dponsubsequences;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: TARGET SUM (LeetCode 494)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * You are given an integer array 'nums' and an integer 'target'.
 * You want to build an expression out of nums by adding one of the symbols '+' or '-'
 * before each integer in nums and then concatenate all the integers.
 *
 * For example, if nums = [2, 1], you can add a '+' before 2 and a '-' before 1 to get expression "+2-1".
 * Return the number of different expressions that you can build, which evaluates to target.
 *
 * EXAMPLE:
 * Input: nums = [1,1,1,1,1], target = 3
 * Output: 5
 * Explanation: There are 5 ways to assign symbols to make the sum of nums be 3.
 -1 + 1 + 1 + 1 + 1 = 3
 +1 - 1 + 1 + 1 + 1 = 3
 +1 + 1 - 1 + 1 + 1 = 3
 +1 + 1 + 1 - 1 + 1 = 3
 +1 + 1 + 1 + 1 - 1 = 3
 *
 * Constraints:
 *
 * 1 <= nums.length <= 20
 * 0 <= nums[i] <= 1000
 * 0 <= sum(nums[i]) <= 1000
 * -1000 <= target <= 1000
 *
 * APPROACH SUMMARY:
 * This code uses the "Direct Assignment" approach.
 * At every index, we have two choices:
 * 1. Add the current number (+nums[i])
 * 2. Subtract the current number (-nums[i])
 *
 * KEY CHALLENGE - NEGATIVE INDICES:
 * Since subtracting numbers can lead to a negative intermediate sum (e.g., 0 - 5 = -5),
 * and array indices cannot be negative, we use an **OFFSET**.
 * We shift the range of possible sums.
 * - Real Range: [-TotalSum ... +TotalSum]
 * - DP Array Range: [0 ... 2*TotalSum]
 * - Mapping: dp_index = actual_sum + TotalSum (Offset)
 * ==================================================================================================
 */
public class TargetSum {

    public static void main(String[] args) {
        int[] arr = {1, 1, 1, 1, 1};
        int target = 3;
        int n = arr.length;

        System.out.println("Input Array: " + Arrays.toString(arr));
        System.out.println("Target: " + target);
        System.out.println("--------------------------------------------------");

        // 1. Recursion
        System.out.println("1. Recursion       : " + countWaysRecursive(n - 1, target, arr));

        // 2. Memoization
        // Calculate total sum to define the range of possible sums
        int totalSum = Arrays.stream(arr).sum();

        // DP Table Size: Rows = n, Cols = 2*totalSum + 1 (to handle negative range)
        // Offset = totalSum. e.g., index 0 maps to sum -totalSum.
        int[][] dp = new int[n][2 * totalSum + 1];
        for (int[] row : dp) Arrays.fill(row, -1);

        // Edge Case: If target is impossible to reach (outside max possible range)
        if (target > totalSum || target < -totalSum) {
            System.out.println("2. Memoization     : 0");
        } else {
            System.out.println("2. Memoization     : " + countWaysMemo(n - 1, target, arr, dp, totalSum));
        }

        // 3. Tabulation
        System.out.println("3. Tabulation      : " + countWaysTabulation(arr, target));

        // 4. Space Optimized
        System.out.println("4. Space Optimized : " + countWaysSpaceOptimized(arr, target));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Try every combination. At index 'i', we try adding arr[i] and subtracting arr[i].
     * The new target for the next step becomes (target - arr[i]) or (target + arr[i]).
     *
     * COMPLEXITY:
     * - Time: O(2^N) -> Binary tree depth N.
     * - Space: O(N) -> Recursion stack.
     */
    private static int countWaysRecursive(int index, int target, int[] arr) {
        // Base case: We processed all elements (index reached -1 is conceptual, here we stop at 0)
        if (index == 0) {
            // Case 1: If current target is 0 and arr[0] is 0
            // We can do +0 or -0. Both result in 0. So 2 ways.
            if (target == 0 && arr[0] == 0) return 2;

            // Case 2: If we can satisfy the target by either adding OR subtracting arr[0]
            if (target == arr[0] || target == -arr[0]) return 1;

            // Case 3: Impossible
            return 0;
        }

        // Choice 1: Assign '+' to arr[index]
        // Effectively, we need to find (target - arr[index]) from the rest of the array.
        int plus = countWaysRecursive(index - 1, target - arr[index], arr);

        // Choice 2: Assign '-' to arr[index]
        // Effectively, we need to find (target + arr[index]) from the rest of the array.
        int minus = countWaysRecursive(index - 1, target + arr[index], arr);

        return plus + minus;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Same as recursion, but we store the result of states.
     * State = (index, current_target).
     * * OFFSET LOGIC:
     * The 'current_target' can range from -TotalSum to +TotalSum.
     * Array indices cannot be negative.
     * We shift the index by 'totalSum'.
     * Real Sum -5 maps to index (-5 + offset).
     *
     * COMPLEXITY:
     * - Time: O(N * TotalSum)
     * - Space: O(N * TotalSum)
     */
    private static int countWaysMemo(int index, int target, int[] arr, int[][] dp, int offset) {
        if (index == 0) {
            if (target == 0 && arr[0] == 0) return 2;
            if (target == arr[0] || target == -arr[0]) return 1;
            return 0;
        }

        // Check cache with offset logic
        // Verify bounds to prevent IndexOutOfBounds if intermediate target explodes
        if (target + offset < 0 || target + offset >= dp[0].length) return 0;

        if (dp[index][target + offset] != -1) {
            return dp[index][target + offset];
        }

        int plus = countWaysMemo(index - 1, target - arr[index], arr, dp, offset);
        int minus = countWaysMemo(index - 1, target + arr[index], arr, dp, offset);

        return dp[index][target + offset] = plus + minus;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * We iterate through every index and every possible sum (from -TotalSum to +TotalSum).
     * dp[i][sum] = ways to reach 'sum' using first 'i' elements.
     * * COMPLEXITY:
     * - Time: O(N * TotalSum)
     * - Space: O(N * TotalSum)
     */
    private static int countWaysTabulation(int[] arr, int target) {
        int n = arr.length;
        int sum = Arrays.stream(arr).sum();
        int offset = sum; // Offset to handle negative indices

        // Edge case: Target is theoretically impossible
        if (target < -sum || target > sum) return 0;

        int[][] dp = new int[n][2 * sum + 1];

        // Base Case Initialization (Index 0)
        if (arr[0] == 0) {
            dp[0][offset] = 2; // +0 and -0 both land at sum 0 (index 'offset')
        } else {
            dp[0][offset + arr[0]] = 1; // +arr[0]
            dp[0][offset - arr[0]] = 1; // -arr[0]
        }

        // Iterate through rest of the array
        for (int i = 1; i < n; i++) {
            // Iterate through all possible sums 't' from -sum to +sum
            for (int t = -sum; t <= sum; t++) {

                // Ways to reach sum 't' if we ADD arr[i]
                // We must have come from state (t - arr[i])
                int plus = 0;
                if (t - arr[i] >= -sum && t - arr[i] <= sum) {
                    plus = dp[i - 1][offset + t - arr[i]];
                }

                // Ways to reach sum 't' if we SUBTRACT arr[i]
                // We must have come from state (t + arr[i])
                int minus = 0;
                if (t + arr[i] >= -sum && t + arr[i] <= sum) {
                    minus = dp[i - 1][offset + t + arr[i]];
                }

                dp[i][offset + t] = plus + minus;
            }
        }

        return dp[n - 1][offset + target];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZATION
     * ----------------------------------------------------------------------
     * LOGIC:
     * We only need the previous row (prev) to calculate the current row (curr).
     * We can reduce space from O(N * Sum) to O(Sum).
     * * COMPLEXITY:
     * - Time: O(N * TotalSum)
     * - Space: O(TotalSum)
     */
    private static int countWaysSpaceOptimized(int[] arr, int target) {
        int n = arr.length;
        int sum = Arrays.stream(arr).sum();
        int offset = sum;

        if (target < -sum || target > sum) return 0;

        int[] prev = new int[2 * sum + 1];

        // Base Case
        if (arr[0] == 0) {
            prev[offset] = 2;
        } else {
            prev[offset + arr[0]] = 1;
            prev[offset - arr[0]] = 1;
        }

        // Iteration
        for (int i = 1; i < n; i++) {
            int[] curr = new int[2 * sum + 1];
            for (int t = -sum; t <= sum; t++) {

                int plus = 0;
                if (t - arr[i] >= -sum && t - arr[i] <= sum) {
                    plus = prev[offset + t - arr[i]];
                }

                int minus = 0;
                if (t + arr[i] >= -sum && t + arr[i] <= sum) {
                    minus = prev[offset + t + arr[i]];
                }
                curr[offset + t] = plus + minus;
            }
            prev = curr.clone(); // Update state
        }

        return prev[offset + target];
    }
}