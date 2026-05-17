package com.questions.strivers.dynamicprogramming.dponsubsequences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class BalancedSubsetPartition {

    // ----------------------------------------------------------------------------------
    // 1️⃣ RECURSIVE
    // ----------------------------------------------------------------------------------
    // Time Complexity: O(2^2n) - Exponential due to branching at each index (pick or not)
    // Space Complexity: O(2n) - Maximum recursion stack depth (n is half the array size)

    private static int recursive(int[] nums) {
        int totalSum = Arrays.stream(nums).sum(); // Total sum of the entire array
        return recur(nums, 0, 0, 0, totalSum);     // Start recursive check
    }

    private static int recur(int[] nums, int index, int count, int sum1, int totalSum) {
        // Base Case: All elements processed
        if (index == nums.length) {
            // Only valid if exactly n elements used in subset1
            if (count == nums.length / 2) {
                int sum2 = totalSum - sum1;               // Subset2 sum
                return Math.abs(sum1 - sum2);             // Return absolute difference
            }
            return Integer.MAX_VALUE; // Invalid partition
        }

        if (count > nums.length / 2) return Integer.MAX_VALUE; // Prune extra subset1

        // Choice 1: include current element in subset1
        int pick = recur(nums, index + 1, count + 1, sum1 + nums[index], totalSum);

        // Choice 2: exclude current element (goes into subset2 implicitly)
        int notPick = recur(nums, index + 1, count, sum1, totalSum);

        // Return minimum difference
        return Math.min(pick, notPick);
    }

    // ----------------------------------------------------------------------------------
    // 2️⃣ MEMOIZATION (TOP-DOWN DP)
    // ----------------------------------------------------------------------------------
    // Time Complexity: O(N * (N/2) * totalSum)
    // Space Complexity: O(N * (N/2) * totalSum) + recursion stack

    private static int memoization(int[] nums) {
        int n = nums.length;
        int totalSum = Arrays.stream(nums).sum();

        // dp[index][count][sum1] stores the result of subproblem
        Integer[][][] dp = new Integer[n + 1][n / 2 + 1][totalSum + 1];

        return memo(nums, 0, 0, 0, totalSum, dp);
    }

    private static int memo(int[] nums, int index, int count, int sum1, int totalSum, Integer[][][] dp) {
        if (index == nums.length) {
            if (count == nums.length / 2) {
                return Math.abs(sum1 - (totalSum - sum1));
            }
            return Integer.MAX_VALUE;
        }

        if (count > nums.length / 2) return Integer.MAX_VALUE;

        // Return already computed result
        if (dp[index][count][sum1] != null)
            return dp[index][count][sum1];

        // Include current element in subset1
        int pick = memo(nums, index + 1, count + 1, sum1 + nums[index], totalSum, dp);

        // Exclude current element (goes in subset2)
        int notPick = memo(nums, index + 1, count, sum1, totalSum, dp);

        // Save and return result
        return dp[index][count][sum1] = Math.min(pick, notPick);
    }

    // ----------------------------------------------------------------------------------
    // 3️⃣ TABULATION (BOTTOM-UP DP)
    // ----------------------------------------------------------------------------------
    // Time Complexity: O(N * (N/2) * totalSum)
    // Space Complexity: O(N * (N/2) * totalSum)

    private static int tabulation(int[] nums) {
        int N = nums.length;
        int half = N / 2;
        int totalSum = Arrays.stream(nums).sum();

        // dp[i][c][s] = true means: from first i elements,
        // we can pick c elements having sum = s
        boolean[][][] dp = new boolean[N + 1][half + 1][totalSum + 1];
        dp[0][0][0] = true; // 0 elements picked, sum 0 is possible

        for (int i = 0; i < N; i++) {
            for (int c = 0; c <= half; c++) {
                for (int s = 0; s <= totalSum; s++) {
                    if (!dp[i][c][s]) continue;

                    // Do not pick nums[i]
                    dp[i + 1][c][s] = true;

                    // Pick nums[i] into subset1
                    if (c + 1 <= half && s + nums[i] <= totalSum) {
                        dp[i + 1][c + 1][s + nums[i]] = true;
                    }
                }
            }
        }

        // Find the minimum difference among valid n-element subsets
        int minDiff = Integer.MAX_VALUE;
        for (int s1 = 0; s1 <= totalSum; s1++) {
            if (dp[N][half][s1]) {
                int s2 = totalSum - s1;
                minDiff = Math.min(minDiff, Math.abs(s1 - s2));
            }
        }

        return minDiff;
    }

    // ----------------------------------------------------------------------------------
    // 4️⃣ SPACE OPTIMIZED TABULATION
    // ----------------------------------------------------------------------------------
    // Time Complexity: O(N * (N/2) * totalSum)
    // Space Complexity: O((N/2) * totalSum)

    static int minSubsetSumDifference(ArrayList<Integer> arr, int n) {
        int totSum = 0;

        // Calculate the total sum of the array elements
        for (int i = 0; i < n; i++) {
            totSum += arr.get(i);
        }

        // Create an array to store DP results for the previous row
        boolean[] prev = new boolean[totSum + 1];

        // Initialize the DP array for the first row
        prev[0] = true;

        // Initialize the DP array for the first column
        if (arr.get(0) <= totSum) {
            prev[arr.get(0)] = true;
        }

        // Fill in the DP array using bottom-up dynamic programming
        for (int ind = 1; ind < n; ind++) {
            // Create an array to store DP results for the current row
            boolean[] cur = new boolean[totSum + 1];
            cur[0] = true;
            for (int target = 1; target <= totSum; target++) {
                // Calculate if the current element is not taken
                boolean notTaken = prev[target];

                // Calculate if the current element is taken
                boolean taken = false;
                if (arr.get(ind) <= target) {
                    taken = prev[target - arr.get(ind)];
                }

                // Update the DP array for the current element and target sum
                cur[target] = notTaken || taken;
            }
            prev = cur;
        }

        int mini = Integer.MAX_VALUE;

        // Find the minimum absolute difference between two subsets
        for (int i = 0; i <= totSum; i++) {
            if (prev[i]) {
                int diff = Math.abs(i - (totSum - i));
                mini = Math.min(mini, diff);
            }
        }
        return mini;
    }

    // ----------------------------------------------------------------------------------
    // 🧪 MAIN METHOD FOR TESTING
    // ----------------------------------------------------------------------------------

    public static void main(String[] args) {
        int[] nums1 = {3, 9, 7, 3};
        int[] nums2 = {-36, 36};
        int[] nums3 = {2, -1, 0, 4, -2, -9};

        System.out.println("Input: [3, 9, 7, 3]");
        System.out.println("Recursive:        " + recursive(nums1));
        System.out.println("Memoization:      " + memoization(nums1));
        System.out.println("Tabulation:       " + tabulation(nums1));


        ArrayList<Integer> arr = new ArrayList<>(
                Arrays.asList(Arrays.stream(nums2)
                        .boxed()
                        .toArray(Integer[]::new))
        );
        int n = arr.size();
        // Calculate and print the minimum absolute difference
        System.out.println("The minimum absolute difference is: " + minSubsetSumDifference(arr, n));
    }
}
