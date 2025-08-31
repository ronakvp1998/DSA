package com.questions.strivers.slidingwind2pointer.length;

/*
L4. Max Consecutive Ones III
-------------------------------------------------
Problem Statement:
Given a binary array nums and an integer k, return the maximum number of
consecutive 1's in the array if you can flip at most k 0's.

Examples:
1. Input: nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
   Output: 6
   Explanation: Flip two zeros → longest subarray = [1,1,1,0,0,1,1,1,1,1,1]

2. Input: nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], k = 3
   Output: 10
   Explanation: Flip three zeros → longest subarray = [1,1,1,1,1,1,1,1,1,1]
*/

public class MaxConsOnes {
    public static void main(String[] args) {
        int arr[] = {1,1,1,0,0,0,1,1,1,1,0};
        int k = 2;
//        System.out.println(maxConsOnes1(arr,k)); // Brute force
//        System.out.println(maxConsOnes2(arr,k)); // Sliding window with while
        System.out.println(maxConsOnes3(arr,k));   // Sliding window optimized
    }

    /**
     * Approach 3: Sliding Window (Optimized)
     * --------------------------------------
     * - Use two pointers (l = left, r = right).
     * - Expand window by moving r.
     * - If a 0 is added → increment zeros count.
     * - If zeros > k → shrink window from left until valid.
     * - Track maximum window size.
     *
     * Time Complexity: O(n), each element processed at most twice.
     * Space Complexity: O(1), constant extra variables.
     */
    public static int maxConsOnes3(int arr[], int k) {
        int maxLen = 0, l = 0, r = 0, zeros = 0;
        while (r < arr.length) {
            if (arr[r] == 0) {
                zeros++;
            }
            // If window has more than k zeros, shrink from left
            if (zeros > k) {
                if (arr[l] == 0) {
                    zeros--;
                }
                l++;
            }
            // Valid window → update max length
            int len = r - l + 1;
            maxLen = Math.max(maxLen, len);
            r++;
        }
        return maxLen;
    }

    /**
     * Approach 2: Sliding Window (while loop to shrink until valid)
     * -------------------------------------------------------------
     * - Similar to Approach 3 but explicitly shrinks with a while loop
     *   until zeros <= k.
     *
     * Time Complexity: O(n), efficient.
     * Space Complexity: O(1).
     */
    public static int maxConsOnes2(int arr[], int k) {
        int maxLen = 0, l = 0, r = 0, zeros = 0;
        while (r < arr.length) {
            if (arr[r] == 0) {
                zeros++;
            }
            // Shrink window until it becomes valid (zeros <= k)
            while (zeros > k) {
                if (arr[l] == 0) {
                    zeros--;
                }
                l++;
            }
            int len = r - l + 1;
            maxLen = Math.max(maxLen, len);
            r++;
        }
        return maxLen;
    }

    /**
     * Approach 1: Brute Force
     * ------------------------
     * - Generate all subarrays starting at i.
     * - Count zeros in each subarray.
     * - Stop expanding when zeros exceed k.
     * - Track maximum valid subarray length.
     *
     * Time Complexity: O(n^2), because nested loops over subarrays.
     * Space Complexity: O(1).
     */
    public static int maxConsOnes1(int arr[], int k) {
        int maxLen = 0, n = arr.length;
        for (int i = 0; i < n; i++) {
            int zeros = 0;
            for (int j = i; j < n; j++) {
                if (arr[j] == 0) {
                    zeros++;
                }
                if (zeros <= k) {
                    int length = j - i + 1;
                    maxLen = Math.max(maxLen, length);
                } else {
                    break; // No need to extend further
                }
            }
        }
        return maxLen;
    }
}
