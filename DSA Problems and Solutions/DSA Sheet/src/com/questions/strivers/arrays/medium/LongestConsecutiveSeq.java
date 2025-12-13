package com.questions.strivers.arrays.medium;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
 * https://leetcode.com/problems/longest-consecutive-sequence/editorial/
 *
 * Problem Statement:
 * You are given an array of ‘N’ integers.
 * You need to find the length of the longest sequence that contains consecutive elements.
 *
 * Example 1:
 * Input: [100, 200, 1, 3, 2, 4]
 * Output: 4
 * Explanation: Longest consecutive subsequence is [1, 2, 3, 4]
 *
 * Example 2:
 * Input: [3, 8, 5, 7, 6]
 * Output: 4
 * Explanation: Longest consecutive subsequence is [5, 6, 7, 8]
 */

public class LongestConsecutiveSeq {
    public static void main(String[] args) {
        int arr[] = {100, 4, 200, 1, 3, 2};

        System.out.println(longestCongSeqBrute(arr));  // Brute force
        System.out.println(longestCongSeqSort(arr));   // Optimized using sorting
        System.out.println(longestCongSeqHashSet(arr));// Optimized using HashSet
    }

    /**
     * Brute Force Approach (only for unique elements its will count duplicates as well)
     * Logic:
     * - For each element, check if the next consecutive number exists in the array.
     * - Keep counting until no next number exists.
     * - Track the maximum count found.
     *
     * Time Complexity: O(n^2) → For each element (n), we may scan the array (n) times.
     * Space Complexity: O(1) → No extra space used.
     */
    private static int longestCongSeqBrute(int arr[]) {
        int n = arr.length;
        if (n == 0) return 0;

        int longest = 1;

        for (int i = 0; i < n; i++) {
            int x = arr[i];  // Current number
            int count = 1;   // Length of current sequence

            // Check for consecutive numbers
            while (linearSearch(arr, x + 1)) { // If next consecutive exists
                x = x + 1;  // Move to next number
                count++;
            }

            longest = Math.max(longest, count);
        }
        return longest;
    }

    // Helper method for brute force to search in array
    private static boolean linearSearch(int[] arr, int num) {
        for (int j : arr) {
            if (j == num) return true;
        }
        return false;
    }

    /**
     * Optimized Approach #1 (Using Sorting)
     * Logic:
     * - Sort the array.
     * - Iterate through, counting consecutive numbers.
     * - Skip duplicates.
     *
     * Time Complexity: O(n log n) → Sorting takes O(n log n) + O(n) for scanning.
     * Space Complexity: O(1) if in-place sort is allowed.
     */
    private static int longestCongSeqSort(int arr[]) {
        if (arr.length == 0) return 0;

        Arrays.sort(arr); // Sort array

        int count = 1; // Current sequence length
        int longest = 1; // Maximum sequence length
        int lastSmallest = arr[0]; // Last number in sequence

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] - 1 == lastSmallest) {
                // Consecutive number found
                count++;
                lastSmallest = arr[i];
            } else if (arr[i] != lastSmallest) {
                // Reset if not consecutive (skip duplicates)
                count = 1;
                lastSmallest = arr[i];
            }
            longest = Math.max(longest, count);
        }
        return longest;
    }

    /**
     * Optimized Approach #2 (Using HashSet) — O(n) solution
     * Logic:
     * - Add all numbers into a HashSet for O(1) lookups.
     * - Only start counting when a number is the "start" of a sequence
     *   (i.e., no number before it in the sequence exists).
     * - Count how many consecutive numbers exist starting from that number.
     *
     * Time Complexity: O(n) → Each number is processed once.
     * Space Complexity: O(n) → Storing all elements in HashSet.
     */
    private static int longestCongSeqHashSet(int arr[]) {
        int n = arr.length;
        if (n == 0) return 0;

        int longest = 1;
        Set<Integer> set = new HashSet<>();

        // Step 1: Store all numbers in a HashSet
        for (int num : arr) {
            set.add(num);
        }

        // Step 2: Iterate through each unique number
        for (int num : set) {
            // Start counting only if this is the start of a sequence
            if (!set.contains(num - 1)) {
                int currentNum = num;
                int count = 1;

                // Count consecutive numbers
                while (set.contains(currentNum + 1)) {
                    currentNum++;
                    count++;
                }

                longest = Math.max(longest, count);
            }
        }
        return longest;
    }
}
