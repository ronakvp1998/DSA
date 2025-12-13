package com.questions.strivers.arrays.hard;

/*
🔗 Problem Link: https://takeuforward.org/data-structure/count-inversions-in-an-array/

🧠 Problem Statement:
Given an array of size N, count the number of inversions.
An inversion is a pair (i, j) such that:
    - i < j
    - a[i] > a[j]

📌 Example:
Input: [5, 4, 3, 2, 1]
Output: 10 (All pairs are inversions)
*/

// Import ArrayList for temporary merging
import java.util.ArrayList;

public class CountInversion {

    /**
     * ✅ Brute Force Approach
     * Count all pairs (i, j) where i < j and a[i] > a[j]
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     */
    private static int numberOfInversions(int[] a, int n) {
        int cnt = 0;
        // Check every pair (i, j)
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // If a[i] > a[j], it's an inversion
                if (a[i] > a[j]) cnt++;
            }
        }
        return cnt;
    }

    /**
     * 🔧 Helper Function: Merge with Inversion Count
     * This function merges two sorted halves of the array and counts inversions during merge step.
     */
    private static int merge(int[] arr, int low, int mid, int high) {
        ArrayList<Integer> temp = new ArrayList<>(); // temporary array for merged elements
        int left = low;       // Pointer for left half
        int right = mid + 1;  // Pointer for right half
        int cnt = 0;          // Inversion count

        // Merge both halves in sorted order
        while (left <= mid && right <= high) {
            if (arr[left] <= arr[right]) {
                temp.add(arr[left++]);
            } else {
                temp.add(arr[right++]);
                // All elements from arr[left] to arr[mid] are greater than arr[right]
                cnt += (mid - left + 1);
            }
        }

        // Append remaining elements from left half
        while (left <= mid) {
            temp.add(arr[left++]);
        }

        // Append remaining elements from right half
        while (right <= high) {
            temp.add(arr[right++]);
        }

        // Copy merged elements back to original array
        for (int i = low; i <= high; i++) {
            arr[i] = temp.get(i - low);
        }

        return cnt; // Return inversion count
    }

    /**
     * 🧠 Optimal Approach: Merge Sort Based Inversion Count
     * Uses divide and conquer similar to merge sort and counts during merge.
     * Time Complexity: O(n log n)
     * Space Complexity: O(n) for temporary merge array
     */
    private static int mergeSort(int[] arr, int low, int high) {
        int cnt = 0;
        // Base case: single element has no inversions
        if (low >= high) return cnt;

        int mid = (low + high) / 2;

        // Count inversions in left half
        cnt += mergeSort(arr, low, mid);

        // Count inversions in right half
        cnt += mergeSort(arr, mid + 1, high);

        // Count cross inversions during merge
        cnt += merge(arr, low, mid, high);

        return cnt;
    }

    /**
     * Wrapper function for optimal approach
     */
    private static int numberOfInversions2(int[] a, int n) {
        return mergeSort(a, 0, n - 1);
    }

    public static void main(String[] args) {
        int[] a = {5, 4, 3, 2, 1}; // Reverse sorted: maximum inversions
        int n = a.length;

        // ✅ Using brute force
        int cnt = numberOfInversions(a.clone(), n); // clone to preserve original for next method
        System.out.println("The number of inversions (Brute Force) are: " + cnt);

        // ✅ Using merge sort (Optimal)
        cnt = numberOfInversions2(a.clone(), n);
        System.out.println("The number of inversions (Merge Sort) are: " + cnt);
    }
}
