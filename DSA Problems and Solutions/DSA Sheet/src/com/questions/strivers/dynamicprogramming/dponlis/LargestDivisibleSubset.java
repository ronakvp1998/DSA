package com.questions.strivers.dynamicprogramming.lis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ==================================================================================================
 * PROBLEM: LARGEST DIVISIBLE SUBSET (LeetCode 368)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given a set of distinct positive integers nums, return the largest subset answer such that
 * every pair (answer[i], answer[j]) of elements in this subset satisfies:
 * answer[i] % answer[j] == 0, or answer[j] % answer[i] == 0.
 *
 * EXAMPLE 1:
 * Input: nums = [1, 2, 3]
 * Output: [1, 2] (or [1, 3])
 * Explanation: 1 divides 2. 1 also divides 3. But 2 does not divide 3.
 *
 * EXAMPLE 2:
 * Input: nums = [1, 2, 4, 8]
 * Output: [1, 2, 4, 8]
 * Explanation: 1|2, 2|4, 4|8. It forms a perfect chain.
 *
 * ALGORITHM (LIS VARIATION):
 * 1. Sort the array. This ensures that for any pair (i, j) with i > j, we only check nums[i] % nums[j].
 * 2. Use DP to find the length of the longest chain ending at index i.
 * - dp[i] = 1 + max(dp[j]) where nums[i] % nums[j] == 0.
 * 3. Use a 'hash' (parent) array to keep track of the predecessor index to reconstruct the solution.
 * ==================================================================================================
 */
public class LargestDivisibleSubset {

    public static void main(String[] args) {
        int[] nums = {1, 2, 4, 8};
        System.out.println("Input: " + Arrays.toString(nums));
        System.out.println("Largest Subset: " + largestDivisibleSubset(nums));

        int[] nums2 = {1, 2, 3};
        System.out.println("\nInput: " + Arrays.toString(nums2));
        System.out.println("Largest Subset: " + largestDivisibleSubset(nums2));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH: SORTING + DP (O(N^2))
     * ----------------------------------------------------------------------
     * COMPLEXITY:
     * - Time: O(N^2) -> Two nested loops, similar to standard LIS.
     * - Space: O(N) -> For dp and hash arrays.
     */
    public static List<Integer> largestDivisibleSubset(int[] nums) {
        int n = nums.length;
        if (n == 0) return new ArrayList<>();

        // 1. Sort the array to ensure we only check divisibility in one direction
        Arrays.sort(nums);

        // dp[i] stores the length of the largest divisible subset ending at index i
        int[] dp = new int[n];
        // hash[i] stores the index of the previous element in the subset
        int[] hash = new int[n];

        // Initialize arrays
        Arrays.fill(dp, 1);
        for (int i = 0; i < n; i++) {
            hash[i] = i; // Initialize parent as itself
        }

        int maxLen = 1;
        int lastIndex = 0;

        // 2. Build the DP Table
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // Key Logic: If nums[i] is divisible by nums[j] AND extending j gives a longer chain
                if (nums[i] % nums[j] == 0 && 1 + dp[j] > dp[i]) {
                    dp[i] = 1 + dp[j];
                    hash[i] = j; // Store the predecessor
                }
            }

            // Track the global maximum length and where it ends
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                lastIndex = i;
            }
        }

        // 3. Reconstruct the Subset (Backtracking)
        List<Integer> result = new ArrayList<>();
        result.add(nums[lastIndex]);

        // Trace back using the hash array until the element points to itself
        while (hash[lastIndex] != lastIndex) {
            lastIndex = hash[lastIndex];
            result.add(nums[lastIndex]);
        }

        // The subset is constructed backwards (Largest -> Smallest), so reverse it (optional)
        // [8, 4, 2, 1] -> [1, 2, 4, 8]
        Collections.reverse(result);

        return result;
    }
}