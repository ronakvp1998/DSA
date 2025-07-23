package com.questions.strivers.arrays.easy;
//https://takeuforward.org/data-structure/check-if-an-array-is-sorted/
//Problem Statement: Given an array of size n,
//write a program to check if the given array is sorted in (ascending / Increasing / Non-decreasing) order or not.
//If the array is sorted then return True, Else return False.

public class CheckArrayIsSorted {

    public static void main(String[] args) {
        // Input array
        int arr[] = {1, 2, 3, 4, 5};

        int n = arr.length;

        // Check if array is sorted using brute force
        System.out.println(isSorted(arr, n));

        // Check if array is sorted using optimized method
        System.out.println(isSortedOptimized(arr, n));
    }

    // ❌ Brute-force approach to check if array is sorted in non-decreasing order
    static boolean isSorted(int arr[], int n) {
        // Compare each pair of elements
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // If any later element is smaller than current, array is not sorted
                if (arr[j] < arr[i])
                    return false;
            }
        }
        // If no violation found, array is sorted
        return true;
    }

    /*
     🕒 Time Complexity: O(n^2)
     → Nested loops check every possible pair of elements.

     🧠 Space Complexity: O(1)
     → Only constant space used (no extra data structures).
    */

    // ✅ Optimized approach to check if array is sorted in non-decreasing order
    static boolean isSortedOptimized(int arr[], int n) {
        // Start checking from the second element
        for (int i = 1; i < n; i++) {
            // If current element is smaller than the previous one, it's not sorted
            if (arr[i] < arr[i - 1])
                return false;
        }
        // If loop completes without returning false, array is sorted
        return true;
    }

    /*
     🕒 Time Complexity: O(n)
     → Single pass through the array.

     🧠 Space Complexity: O(1)
     → No extra space used.
    */
}
