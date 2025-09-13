package com.questions.strivers.recursionbacktracking.medium.sorting;

import java.util.Arrays;

public class QuickSort {

    public static void main(String[] args) {
        int arr[] = {4, 6, 2, 5, 7, 9, 1, 3};
        System.out.println("Original Array: " + Arrays.toString(arr));
        quickSort(arr, 0, arr.length - 1);
        System.out.println("Sorted Array:   " + Arrays.toString(arr));
    }

    /*
     Problem Statement:
     -----------------
     Implement QuickSort to sort an array of integers.

     QuickSort is a Divide and Conquer algorithm:
     1. Select a pivot element.
     2. Partition the array such that:
        - All elements <= pivot go to the left side.
        - All elements > pivot go to the right side.
     3. Recursively apply the same procedure to the left and right subarrays.
     4. Base case is reached when subarray size is 0 or 1.

     Example:
     Input:  [4, 6, 2, 5, 7, 9, 1, 3]
     Output: [1, 2, 3, 4, 5, 6, 7, 9]
    */

    /**
     * Function: quickSort
     * -------------------
     * Recursively partitions the array and sorts the left and right parts.
     *
     * Time Complexity:
     * - Best/Average Case: O(n log n)
     *   (Balanced partitions, tree depth = log n, each level costs O(n)).
     * - Worst Case: O(n^2)
     *   (Occurs when pivot always picks smallest/largest element, e.g., already sorted array).
     *
     * Space Complexity:
     * - O(log n) in best case due to recursion stack depth.
     * - O(n) in worst case (skewed recursion tree).
     */
    public static void quickSort(int arr[], int low, int high){
        if(low < high){ // corrected condition (<= would cause infinite recursion for single elements)
            int partition = func(arr, low, high); // partition index
            quickSort(arr, low, partition - 1);   // sort left subarray
            quickSort(arr, partition + 1, high);  // sort right subarray
        }
    }

    /**
     * Function: func (Partition function using Lomuto/Hoare style hybrid)
     * -----------------------------------------------------------------
     * Partitions the array around the pivot:
     * - Pivot chosen as the first element (arr[low]).
     * - i scans from left to right, j scans from right to left.
     * - Swap elements when arr[i] > pivot and arr[j] <= pivot.
     * - Finally place pivot at its correct sorted position.
     *
     * Time Complexity: O(n)
     * - Each element in the range [low..high] is compared at most once.
     *
     * Space Complexity: O(1)
     * - In-place partitioning (only a few variables).
     */
    public static int func(int arr[], int low, int high){
        int i = low;
        int j = high;
        int pivot = arr[low]; // first element as pivot

        // Partitioning logic
        while (i < j){
            // Move i right until finding element greater than pivot
            while (arr[i] <= pivot && i <= high - 1){
                i++;
            }
            // Move j left until finding element smaller or equal to pivot
            while (arr[j] > pivot && j >= low + 1){
                j--;
            }
            // Swap if pointers haven't crossed
            if(i < j){
                swap(i, j, arr);
            }
        }
        // Place pivot at its correct sorted position
        swap(low, j, arr);
        return j; // return pivot index
    }

    /**
     * Function: swap
     * --------------
     * Utility function to swap two elements in the array.
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public static void swap(int i, int j, int arr[]){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

/*
Summary of Complexities:
------------------------
quickSort():
   - Best/Average Case: O(n log n)
   - Worst Case: O(n^2)
   - Space: O(log n) average, O(n) worst due to recursion

func():
   - Time: O(n)
   - Space: O(1)

swap():
   - Time: O(1)
   - Space: O(1)

Overall:
   Time Complexity = O(n log n) average, O(n^2) worst
   Space Complexity = O(log n) average, O(n) worst
*/
