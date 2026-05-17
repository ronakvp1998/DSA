package com.questions.strivers.arrays.easy;

/*
 https://takeuforward.org/data-structure/count-maximum-consecutive-ones-in-the-array/

 Problem Statement:
 Given an array that contains only 1 and 0, return the count of the maximum consecutive ones in the array.

 Example 1:
 Input: arr = {1, 1, 0, 1, 1, 1}
 Output: 3
 Explanation:
   - The first streak has 2 consecutive 1's.
   - The second streak has 3 consecutive 1's.
   - The maximum streak length is 3.

 Example 2:
 Input: arr = {1, 0, 1, 1, 0, 1}
 Output: 2
 Explanation:
   - The longest streak of consecutive 1's is of length 2.
*/

public class MaximumConsecutiveOnes {

    public static void main(String[] args) {
        int arr[] = {1, 1, 0, 1, 1, 1, 0, 1, 1};

        System.out.println("Approach 1 (Single Pass): " + maxConsecutiveOnes(arr));
        System.out.println("Approach 2 (Sliding Window): " + maxConsecutiveOnesSlidingWindow(arr));
        System.out.println("Approach 3 (Two Pointers): " + maxConsecutiveOnesTwoPointers(arr));
    }

    /**
     * Approach 1: Single Pass Counting
     * - Traverse the array once.
     * - Maintain `count` for current consecutive ones.
     * - Maintain `maxCount` for the maximum streak seen so far.
     * - Reset `count` to 0 whenever a 0 is encountered.
     *
     * Time Complexity: O(n) — single pass over the array
     * Space Complexity: O(1) — constant extra space
     */
    private static int maxConsecutiveOnes(int arr[]) {
        int count = 0, maxCount = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1) {
                count++; // Extend current streak
            } else {
                count = 0; // Reset when encountering a 0
            }
            maxCount = Math.max(maxCount, count); // Track max streak
        }
        return maxCount;
    }

    /**
     * Approach 2: Sliding Window
     * - Treat each segment of consecutive 1's as a window.
     * - Expand window while encountering 1's, reset window on 0's.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    private static int maxConsecutiveOnesSlidingWindow(int[] arr) {
        int left = 0, right = 0, maxCount = 0;

        while (right < arr.length) {
            if (arr[right] == 1) {
                maxCount = Math.max(maxCount, right - left + 1);
                right++;
            } else {
                right++;
                left = right; // Start a new window after a 0
            }
        }
        return maxCount;
    }

    /**
     * Approach 3: Two Pointers (Optimized)
     * - Use two pointers `start` and `end` to track a sequence of 1's.
     * - When a 0 is found, move `start` to `end + 1`.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    private static int maxConsecutiveOnesTwoPointers(int[] arr) {
        int start = 0, end = 0, maxCount = 0;

        while (end < arr.length) {
            if (arr[end] == 1) {
                maxCount = Math.max(maxCount, end - start + 1);
                end++;
            } else {
                end++;
                start = end; // Reset start pointer after a 0
            }
        }
        return maxCount;
    }
}
