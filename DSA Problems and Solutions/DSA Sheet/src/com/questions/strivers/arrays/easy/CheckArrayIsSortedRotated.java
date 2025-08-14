package com.questions.strivers.arrays.easy;

// Problem: Check if Array is Sorted and Rotated
// Link: https://leetcode.com/problems/check-if-array-is-sorted-and-rotated/

/*
Definition:
An array is considered "sorted and rotated" if it can be obtained by taking a sorted (non-decreasing) array
and rotating it some number of positions (possibly zero).

Example:
Input: nums = [3,4,5,1,2]
Explanation: The sorted array [1,2,3,4,5] rotated 3 positions -> [3,4,5,1,2]
*/

import java.util.Arrays;

public class CheckArrayIsSortedRotated {

    public static void main(String[] args) {
        int arr1[] = {3, 4, 5, 1, 2}; // sorted & rotated
        int arr2[] = {1, 2, 3};       // sorted but not rotated
        int arr3[] = {2, 1, 3};       // not sorted & rotated

        System.out.println("Approach 1: Count Break Points");
        System.out.println(checkCountBreaks(arr1)); // true
        System.out.println(checkCountBreaks(arr2)); // true
        System.out.println(checkCountBreaks(arr3)); // false

        System.out.println("\nApproach 2: Simulate All Rotations");
        System.out.println(checkRotationSimulation(arr1)); // true
        System.out.println(checkRotationSimulation(arr2)); // true
        System.out.println(checkRotationSimulation(arr3)); // false

        System.out.println("\nApproach 3: Sorted Copy + Rotation Check");
        System.out.println(checkUsingSortedCopy(arr1)); // true
        System.out.println(checkUsingSortedCopy(arr2)); // true
        System.out.println(checkUsingSortedCopy(arr3)); // false
    }

    // --------------------------------------------------------------------
    // Approach 1: Count Break Points
    // --------------------------------------------------------------------
    /*
    Logic:
    - Traverse array and count the number of "breaks" where nums[i] < nums[i-1].
    - For a sorted & rotated array, there should be at most 1 break point.
    - Also check wrap-around condition: last element > first element (extra break).
    */
    public static boolean checkCountBreaks(int[] nums) {
        int countBreaks = 0;
        int n = nums.length;

        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[i - 1]) {
                countBreaks++;
            }
        }
        // Wrap-around check
        if (nums[n - 1] > nums[0]) {
            countBreaks++;
        }
        return countBreaks <= 1;
    }
    // Time Complexity: O(n)
    // Space Complexity: O(1)

    // --------------------------------------------------------------------
    // Approach 2: Simulate All Rotations (Brute Force)
    // --------------------------------------------------------------------
    /*
    Logic:
    - Create all possible rotations of the array.
    - For each rotation, check if it is sorted in non-decreasing order.
    - If any rotation is sorted, return true.
    */
    public static boolean checkRotationSimulation(int[] nums) {
        int n = nums.length;
        for (int shift = 0; shift < n; shift++) {
            boolean sorted = true;
            for (int i = 1; i < n; i++) {
                if (nums[(i + shift) % n] < nums[(i + shift - 1 + n) % n]) {
                    sorted = false;
                    break;
                }
            }
            if (sorted) return true;
        }
        return false;
    }
    // Time Complexity: O(n^2)  (because for each rotation O(n) check)
    // Space Complexity: O(1)

    // --------------------------------------------------------------------
    // Approach 3: Sorted Copy + Rotation Check
    // --------------------------------------------------------------------
    /*
    Logic:
    - Make a sorted copy of nums.
    - Concatenate sorted copy with itself (to simulate all rotations).
    - Check if original array appears as a subarray in the doubled sorted array.
    */
    public static boolean checkUsingSortedCopy(int[] nums) {
        int n = nums.length;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        // Create doubled array
        int[] doubled = new int[2 * n];
        for (int i = 0; i < 2 * n; i++) {
            doubled[i] = sorted[i % n];
        }

        // Search for nums in doubled array
        for (int start = 0; start < n; start++) {
            boolean match = true;
            for (int j = 0; j < n; j++) {
                if (nums[j] != doubled[start + j]) {
                    match = false;
                    break;
                }
            }
            if (match) return true;
        }
        return false;
    }
    // Time Complexity: O(n log n) for sorting + O(n^2) for matching → O(n^2) worst-case
    // Space Complexity: O(n) for sorted and doubled arrays
}
