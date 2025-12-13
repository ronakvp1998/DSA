package com.questions.strivers.arrays.medium;

import java.util.Arrays;
import java.util.HashMap;

// 📌 Leetcode: https://leetcode.com/problems/two-sum/description/
// 📖 Page 1 - Striver SDE Sheet Book 4
/*Problem Statement: Given an array of integers arr[] and an integer target.
        1st variant: Return YES if there exist two numbers such that their sum is equal to the target.
        Otherwise, return NO.

        2nd variant: Return indices of the two numbers such that their sum is equal to the target.
        Otherwise, we will return {-1, -1}.

        Note: You are not allowed to use the same element twice.
        Example: If the target is equal to 6 and num[1] = 3, then nums[1] + nums[1] = target is not a solution.

        Example 1:
        Input Format: N = 5, arr[] = {2,6,5,8,11}, target = 14
        Result: YES (for 1st variant)
        [1, 3] (for 2nd variant)
        Explanation: arr[1] + arr[3] = 14. So, the answer is “YES” for the first variant and [1, 3] for 2nd variant.

        Example 2:
        Input Format: N = 5, arr[] = {2,6,5,8,11}, target = 15
        Result: NO (for 1st variant)
        [-1, -1] (for 2nd variant)
        Explanation: There exist no such two numbers whose sum is equal to the target. */

public class Sum2Problem {

    public static void main(String[] args) {
        int arr[] = {2, 6, 4, 8, 11};
        int target = 14;

        // Call the optimized 2-pointer version and print result
        System.out.println(Arrays.toString(find2Sum2(arr, target)));
    }

    /**
     * ✅ Optimized Approach: Two-pointer technique after sorting
     *
     * 🔍 Idea:
     * Instead of using a brute-force nested loop, sort the array and apply the two-pointer
     * approach to find the two numbers that add up to the target. To retain original indices,
     * we pair each number with its original index before sorting.
     *
     * 🕒 Time Complexity: O(n log n)
     * - O(n) to create the paired array
     * - O(n log n) for sorting
     * - O(n) for two-pointer traversal
     * - Overall: O(n log n)
     *
     * 🧠 Space Complexity: O(n)
     * - Extra space used for storing value-index pairs (not O(1) as previously stated)
     *
     * ⚠️ Note: Returned indices correspond to the original array, not the sorted one.
     */
    private static int[] find2Sum2(int arr[], int target) {
        // Step 1: Create a new array of [value, original_index] pairs
        int[][] numsWithIndex = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            numsWithIndex[i][0] = arr[i]; // Store value
            numsWithIndex[i][1] = i;      // Store original index
        }

        // Step 2: Sort the array based on the values
        Arrays.sort(numsWithIndex, (a, b) -> a[0] - b[0]);

        // Step 3: Initialize two pointers
        int left = 0, right = arr.length - 1;

        // Step 4: Traverse using two pointers to find the target sum
        while (left < right) {
            int sum = numsWithIndex[left][0] + numsWithIndex[right][0];

            if (sum == target) {
                // Pair found, return their original indices
                return new int[]{numsWithIndex[left][1], numsWithIndex[right][1]};
            } else if (sum < target) {
                left++; // Move to a larger value
            } else {
                right--; // Move to a smaller value
            }
        }

        // Step 5: No such pair found
        return new int[]{-1, -1};
    }

    /**
     * ✅ Optimized Approach 1: HashMap for complement lookup
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    private static int[] find2Sum1(int arr[], int target) {
        HashMap<Integer, Integer> map = new HashMap<>(); // value -> index
        int[] res = new int[2];

        for (int i = 0; i < arr.length; i++) {
            int complement = target - arr[i];

            // If the complement exists in the map, we've found the pair
            if (map.containsKey(complement)) {
                res[0] = map.get(complement); // index of complement
                res[1] = i;                   // current index
                return res;
            }

            // Otherwise, store current element with its index
            map.put(arr[i], i);
        }

        // No pair found
        return new int[]{-1, -1};
    }

    /**
     * 🐢 Brute-force Approach: Try all pairs
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     */
    private static void find2Sum(int arr[], int target) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] + arr[j] == target) {
                    System.out.println("Yes, pair found at indices:");
                    System.out.println("i = " + i + ", j = " + j);
                    return;
                }
            }
        }

        // If loop finishes, no valid pair found
        System.out.println("No valid pair found.");
    }
}
