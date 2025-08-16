package com.questions.strivers.binarysearch.bson1darray;

/*
Problem Statement:
Given a sorted array of N integers and an integer x, find the smallest index (lower bound)
such that arr[index] >= x.

Lower Bound Definition:
- The "lower bound" of a value x in a sorted array is the index of the first element
  that is greater than or equal to x.
- If all elements are smaller than x, it returns n (size of the array).

Examples:
Example 1:
    Input: arr = {1, 2, 2, 3}, x = 2
    Output: 1
    Explanation: arr[1] = 2 is the first element >= 2.

Example 2:
    Input: arr = {3, 5, 8, 15, 19}, x = 9
    Output: 3
    Explanation: arr[3] = 15 is the first element >= 9.
*/

public class LowerBound {

    public static void main(String[] args) {
        int arr[] = {1, 2, 3, 3, 7, 8, 9, 9, 9, 11};
        int x = 1;
        System.out.println(lowerBound(arr, arr.length, x)); // Expected Output: 0
    }

    /**
     * Finds the lower bound of a given number x in a sorted array using Binary Search.
     *
     * Approach:
     * 1. Initialize `low = 0`, `high = n - 1`, and `ans = n` (default to n if x is greater than all elements).
     * 2. Perform binary search:
     *      - If arr[mid] >= x:
     *          → mid could be our potential answer, so store it in ans.
     *          → Move left (high = mid - 1) to check if there is a smaller index satisfying the condition.
     *      - Else:
     *          → arr[mid] < x, so we need to search in the right half (low = mid + 1).
     * 3. Return `ans` (smallest index where arr[index] >= x).
     *
     * Time Complexity:
     * - O(log n) because we halve the search space each iteration.
     *
     * Space Complexity:
     * - O(1) because we use only a constant amount of extra space.
     *
     * @param arr Sorted input array
     * @param n   Size of the array
     * @param x   Target value for lower bound
     * @return Index of the lower bound, or n if all elements are smaller than x
     */
    public static int lowerBound(int arr[], int n, int x) {
        int low = 0, high = n - 1;
        int ans = n; // Default value if x is greater than all elements

        while (low <= high) {
            int mid = low + (high - low) / 2; // Prevents overflow

            if (arr[mid] >= x) {
                ans = mid;     // mid is a valid lower bound
                high = mid - 1; // Check if a smaller index also satisfies the condition
            } else {
                low = mid + 1; // Move to the right half
            }
        }

        return ans;
    }
}
