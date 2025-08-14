package com.questions.strivers.arrays.easy;

import java.util.HashMap;
import java.util.Map;

/*
Problem Statement:
Given an array and a sum k, print the length of the longest subarray that sums to k.

Examples:
Example 1:
Input: N = 3, k = 5, array[] = {2, 3, 5}
Output: 2
Explanation: The longest subarray with sum 5 is {2, 3} → length = 2.

Example 2:
Input: N = 5, k = 10, array[] = {2, 3, 5, 1, 9}
Output: 3
Explanation: The longest subarray with sum 10 is {2, 3, 5} → length = 3.
*/

public class LongestSubarraySumk {

    /**
     * Approach 1: Brute Force (O(N^3))
     * - Triple nested loop:
     *     1st loop: start index
     *     2nd loop: end index
     *     3rd loop: calculate sum from scratch
     * - Very inefficient for large arrays.
     *
     * Time Complexity: O(N^3)
     *     → Outer loop runs N times
     *     → Inner loop runs ~N times for each outer loop
     *     → Innermost loop runs up to N times for sum calculation
     *
     * Space Complexity: O(1)
     *     → No extra data structures used.
     */
    public static int getLongestSubarray1(int[] a, long k) {
        int n = a.length;
        int len = 0;

        for (int i = 0; i < n; i++) { // start index
            for (int j = i; j < n; j++) { // end index
                long s = 0;
                for (int K = i; K <= j; K++) { // compute sum of current subarray
                    s += a[K];
                }
                // If sum matches, update length
                if (s == k) {
                    len = Math.max(len, j - i + 1);
                }
            }
        }
        return len;
    }

    /**
     * Approach 2: Improved Brute Force (O(N^2))
     * - Removes innermost loop by keeping a running sum for each subarray.
     *
     * Time Complexity: O(N^2)
     *     → Outer loop N times
     *     → Inner loop N times in worst case
     *     → Sum computed in O(1) each time (incrementally)
     *
     * Space Complexity: O(1)
     *     → Only variables for sum and length are used.
     */
    public static int getLongestSubarray2(int[] a, long k) {
        int n = a.length;
        int len = 0;

        for (int i = 0; i < n; i++) { // start index
            long s = 0;
            for (int j = i; j < n; j++) { // extend subarray
                s += a[j]; // incrementally update sum
                if (s == k) {
                    len = Math.max(len, j - i + 1);
                }
            }
        }
        return len;
    }

    /**
     * Approach 3: Prefix Sum + HashMap (O(N))
     * - Stores first occurrence of each prefix sum in a HashMap.
     * - Can handle negative numbers (unlike sliding window).
     *
     * Time Complexity: O(N)
     *     → Single pass through array
     *     → Each map operation is O(1) average time
     *
     * Space Complexity: O(N)
     *     → HashMap stores up to N prefix sums.
     */
    public static int getLongestSubarray3(int[] a, long k) {
        int n = a.length;
        Map<Long, Integer> preSumMap = new HashMap<>();
        long sum = 0;
        int maxLen = 0;

        for (int i = 0; i < n; i++) {
            sum += a[i]; // update prefix sum

            // Case 1: whole array from start to current index
            if (sum == k) {
                maxLen = Math.max(maxLen, i + 1);
            }

            // Case 2: remove previous sum to find subarray
            long rem = sum - k;
            if (preSumMap.containsKey(rem)) {
                int len = i - preSumMap.get(rem);
                maxLen = Math.max(maxLen, len);
            }

            // Store first occurrence of prefix sum
            preSumMap.putIfAbsent(sum, i);
        }
        return maxLen;
    }

    /**
     * Approach 4: Sliding Window / Two Pointers (O(N))
     * - Only works when array contains positive numbers (and zeros).
     * - Expands and shrinks the window based on sum comparison.
     *
     * Time Complexity: O(N)
     *     → Both pointers (left and right) move at most N steps.
     *
     * Space Complexity: O(1)
     *     → Only a few integer and long variables used.
     */
    public static int getLongestSubarray4(int[] a, long k) {
        int n = a.length;
        int left = 0, right = 0;
        long sum = a[0];
        int maxLen = 0;

        while (right < n) {
            // Shrink window from left if sum > k
            while (left <= right && sum > k) {
                sum -= a[left];
                left++;
            }

            // If sum matches, update max length
            if (sum == k) {
                maxLen = Math.max(maxLen, right - left + 1);
            }

            // Expand window
            right++;
            if (right < n) {
                sum += a[right];
            }
        }
        return maxLen;
    }

    public static void main(String[] args) {
        int[] a = {2, 3, 5, 1, 9};
        long k = 10;
        int len = getLongestSubarray1(a, k);
        System.out.println("The length of the longest subarray is: " + len);
    }

}
