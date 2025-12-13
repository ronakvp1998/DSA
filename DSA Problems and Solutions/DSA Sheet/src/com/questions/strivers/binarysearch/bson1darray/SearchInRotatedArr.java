package com.questions.strivers.binarysearch.bson1darray;

/*
Problem Link: https://leetcode.com/problems/search-in-rotated-sorted-array/

-----------------------------------------------
Problem Statement:
Given an integer array arr[] of size N, sorted in ascending order (with distinct values)
and then rotated at some pivot point unknown to you, and a target value k.
Find the index at which k is present; if not present, return -1.

-----------------------------------------------
Examples:

Example 1:
Input: arr = [4,5,6,7,0,1,2,3], target = 0
Output: 4
Explanation: 0 is present at index 4 in the rotated array.

Example 2:
Input: arr = [4,5,6,7,0,1,2], target = 3
Output: -1
Explanation: 3 is not present in the array.
-----------------------------------------------
*/

public class SearchInRotatedArr {
    public static void main(String[] args) {
        int arr[] = {3, 1};
        int target = 1;
        System.out.println(searchRotatedArray(arr, arr.length, target)); // Expected output: 1
    }

    /*
     * Approach: Modified Binary Search
     * --------------------------------
     * 1. Since the array is rotated, one of the two halves (left or right)
     *    will always be sorted.
     * 2. We check which half is sorted by comparing arr[low] and arr[mid].
     * 3. If the left half is sorted, we check if the target lies within
     *    this half:
     *      - If yes → move `high` to mid - 1.
     *      - If no → search in the right half (low = mid + 1).
     * 4. If the right half is sorted, we check if the target lies within it:
     *      - If yes → move `low` to mid + 1.
     *      - If no → search in the left half (high = mid - 1).
     * 5. Continue until low > high → target not found.
     *
     * Time Complexity: O(log N)
     *  - Binary search halves the search space each iteration.
     *
     * Space Complexity: O(1)
     *  - No extra data structures used; only variables for pointers and mid.
     */
    private static int searchRotatedArray(int arr[], int n, int target) {
        int low = 0, high = n - 1;

        // Edge case: empty array
        if (low > high) {
            return -1;
        }

        // Edge case: single element array
        if (arr.length == 1) {
            return (target == arr[0]) ? 0 : -1;
        }

        while (low <= high) {
            // Mid-point calculation (avoids overflow)
            int mid = low + (high - low) / 2;

            // Case 1: target found
            if (arr[mid] == target) {
                return mid;
            }

            // Case 2: Left half is sorted
            if (arr[low] <= arr[mid]) {
                // Target lies in sorted left half
                if (arr[low] <= target && target < arr[mid]) {
                    high = mid - 1;
                } else { // Target lies in right half
                    low = mid + 1;
                }
            }
            // Case 3: Right half is sorted
            else {
                // Target lies in sorted right half
                if (arr[mid] < target && target <= arr[high]) {
                    low = mid + 1;
                } else { // Target lies in left half
                    high = mid - 1;
                }
            }
        }

        // Target not found
        return -1;
    }
}
