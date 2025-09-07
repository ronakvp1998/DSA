package com.questions.strivers.recursion.sorting;

import java.util.ArrayList;
import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {
        int arr[] = {3,1,2,4,1,5,2,6,4};
        System.out.println("Original Array: " + Arrays.toString(arr));
        mergeSort(arr,0,arr.length-1);
        System.out.println("Sorted Array:   " + Arrays.toString(arr));
    }

    /*
     Problem Statement:
     -----------------
     Implement Merge Sort to sort an array of integers.

     Merge Sort is a Divide and Conquer algorithm:
     1. Divide the array into two halves recursively.
     2. Sort each half recursively using Merge Sort.
     3. Merge the two sorted halves into one sorted array.

     Example:
     Input:  [3, 1, 2, 4]
     Output: [1, 2, 3, 4]
    */

    /**
     * Function: mergeSort
     * -------------------
     * Recursively divides the array into two halves until single elements remain,
     * then merges them in sorted order.
     *
     * Time Complexity: O(n log n)
     * - Dividing the array takes O(log n) levels of recursion.
     * - Each level requires merging which costs O(n).
     * - Total = O(n log n).
     *
     * Space Complexity: O(n)
     * - Extra temporary array (ArrayList) used in merging at each recursion level.
     * - Recursion stack depth = O(log n).
     */
    public static void mergeSort(int arr[], int low, int high) {
        if (low >= high) {
            return; // Base case: single element or invalid range
        }
        int mid = low + (high - low) / 2;

        // Recursively divide left half
        mergeSort(arr, low, mid);

        // Recursively divide right half
        mergeSort(arr, mid + 1, high);

        // Merge the two sorted halves
        merge(arr, low, mid, high);
    }

    /**
     * Function: merge
     * ----------------
     * Merges two sorted subarrays:
     * - Left part: arr[low..mid]
     * - Right part: arr[mid+1..high]
     *
     * Steps:
     * 1. Compare elements from left and right subarrays.
     * 2. Pick the smaller one and put into a temporary list.
     * 3. Append remaining elements if one subarray is exhausted.
     * 4. Copy back the merged result into the original array.
     *
     * Time Complexity: O(n)
     * - Each element in the range [low..high] is visited once.
     *
     * Space Complexity: O(n)
     * - Temporary ArrayList stores up to (high - low + 1) elements.
     */
    public static void merge(int arr[], int low, int mid, int high) {
        ArrayList<Integer> temp = new ArrayList<>();
        int left = low;
        int right = mid + 1;

        // Step 1: Compare elements and merge in sorted order
        while (left <= mid && right <= high) {
            if (arr[left] < arr[right]) {
                temp.add(arr[left]);
                left++;
            } else {
                temp.add(arr[right]);
                right++;
            }
        }

        // Step 2: Copy remaining elements from left subarray
        while (left <= mid) {
            temp.add(arr[left]);
            left++;
        }

        // Step 3: Copy remaining elements from right subarray
        while (right <= high) {
            temp.add(arr[right]);
            right++;
        }

        // Step 4: Copy sorted elements back into original array
        for (int i = low; i <= high; i++) {
            arr[i] = temp.get(i - low);
        }
    }
}

/*
Summary of Complexities:
------------------------
mergeSort():
   - Time: O(n log n)
   - Space: O(n) (temp array + recursion stack)

merge():
   - Time: O(n) for merging subarrays of size n
   - Space: O(n) temporary ArrayList

Overall:
   Time Complexity = O(n log n)
   Space Complexity = O(n)
*/
