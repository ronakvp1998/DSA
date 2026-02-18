package com.questions.strivers.dynamicprogramming.lis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ==================================================================================================
 * PROBLEM: LONGEST INCREASING SUBSEQUENCE (LeetCode 300 / Striver DP-43)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given an integer array nums, return the length of the longest strictly increasing subsequence.
 *
 * EXAMPLE 1:
 * Input: nums = [10, 9, 2, 5, 3, 7, 101, 18]
 * Output: 4
 * Explanation: The LIS is [2, 3, 7, 18].
 *
 * KEY INSIGHT (BINARY SEARCH):
 * We maintain a 'temp' list that is ALWAYS SORTED.
 * - If arr[i] > last element of temp: Append it.
 * - If arr[i] <= last element: Find the first element in temp that is >= arr[i] and REPLACE it.
 *
 * Note: The 'temp' list does NOT essentially contain the LIS itself, but its LENGTH is correct.
 * ==================================================================================================
 */
public class LongestIncreasingSubsequenceBS {

    public static void main(String[] args) {
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("Input: " + Arrays.toString(nums));
        System.out.println("--------------------------------------------------");

        // 1. Standard DP (O(N^2)) - Good for context
        System.out.println("1. Standard DP (O(N^2)) : " + lengthOfLIS_DP(nums));

        // 2. Binary Search (O(N log N)) - Optimal
        System.out.println("2. Binary Search (Opt)  : " + lengthOfLIS_BinarySearch(nums));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: BINARY SEARCH (OPTIMAL)
     * ----------------------------------------------------------------------
     * LOGIC:
     * 1. Create a list 'temp'.
     * 2. Iterate through each number 'x' in nums.
     * 3. If 'x' is greater than the last element of 'temp', add it (Sequence extended).
     * 4. If 'x' is smaller, use Binary Search to find the index of the first element >= x.
     * Replace that element with 'x'.
     * (This keeps the sequence length same but lowers the "barrier" for future elements).
     *
     * COMPLEXITY:
     * - Time: O(N * log N) -> We iterate N times, and binary search takes log N.
     * - Space: O(N) -> To store the temp list.
     */
    private static int lengthOfLIS_BinarySearch(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        List<Integer> temp = new ArrayList<>();
        temp.add(nums[0]);

        for (int i = 1; i < n; i++) {
            // Case 1: If current element is greater than the last element of our LIS candidate
            // We can simply extend the subsequence.
            if (nums[i] > temp.get(temp.size() - 1)) {
                temp.add(nums[i]);
            }
            // Case 2: If current element is smaller or equal, we perform a replacement step.
            // We want to replace the first element in 'temp' that is >= nums[i] with nums[i].
            else {
                int ind = lowerBound(temp, nums[i]);
                temp.set(ind, nums[i]);
            }
        }
        return temp.size();
    }

    // Helper: Custom Lower Bound implementation
    // Returns the index of the first element in list >= target
    private static int lowerBound(List<Integer> list, int target) {
        int left = 0;
        int right = list.size() - 1;
        int ans = right;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) >= target) {
                ans = mid;
                right = mid - 1; // Look for smaller index on the left
            } else {
                left = mid + 1; // Look on the right
            }
        }
        return ans;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: STANDARD DP (TABULATION)
     * ----------------------------------------------------------------------
     * Included for comparison.
     * dp[i] = Length of LIS ending at index i.
     * Time: O(N^2)
     */
    private static int lengthOfLIS_DP(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int maxLen = 1;

        for (int i = 0; i < n; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (nums[prev] < nums[i]) {
                    dp[i] = Math.max(dp[i], 1 + dp[prev]);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }
}