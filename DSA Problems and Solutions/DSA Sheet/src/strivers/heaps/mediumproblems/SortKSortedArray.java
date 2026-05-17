package com.questions.strivers.heaps.mediumproblems;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: Sort a K-Sorted Array
 * ==================================================================================================
 * Given an array where every element is at most 'k' distance away from its target sorted position,
 * completely sort the array.
 *
 * Example:
 * Input: arr = [6, 5, 3, 2, 8, 10, 9], k = 3
 * Output: [2, 3, 5, 6, 8, 9, 10]
 *
 * Logic: The element that should be at index 0 must be somewhere in the range [0, k].
 * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (Standard Sorting)
 * ==================================================================================================
 * Ignore the fact that it is K-sorted and use a standard sorting algorithm like QuickSort
 * or MergeSort (Arrays.sort in Java).
 * Drawback: Standard sorting takes O(N log N). This doesn't take advantage of the
 * "nearly sorted" property.
 * ==================================================================================================
 * APPROACH 2: OPTIMAL (Min-Heap / Priority Queue)
 * ==================================================================================================
 * We know the smallest element of the entire array must be within the first k+1 elements.
 * 1. Create a Min-Heap and insert the first k+1 elements into it.
 * 2. The root of the heap is guaranteed to be the smallest element for the current position.
 * 3. Extract the min from the heap and place it at the beginning of the array.
 * 4. Add the next available element from the array into the heap.
 * 5. Repeat until the array is processed, then empty the remaining heap.
 * ==================================================================================================
 */

import java.util.PriorityQueue;
import java.util.Arrays;

public class SortKSortedArray {

    public static void main(String[] args) {
        int[] arr1 = {6, 5, 3, 2, 8, 10, 9};
        int k1 = 3;
        System.out.println("Original: " + Arrays.toString(arr1));
        sortK(arr1, k1);
        System.out.println("Sorted:   " + Arrays.toString(arr1));

        int[] arr2 = {1, 4, 5, 2, 3, 6, 7, 8, 9, 10};
        int k2 = 2;
        System.out.println("\nOriginal: " + Arrays.toString(arr2));
        sortK(arr2, k2);
        System.out.println("Sorted:   " + Arrays.toString(arr2));
    }

    /**
     * Sorts a K-sorted array using a Min-Heap.
     * Time Complexity: O(N log K)
     * Space Complexity: O(K)
     */
    public static void sortK(int[] arr, int k) {
        if (arr == null || arr.length == 0) return;

        // Min-Heap to keep track of the smallest element in the current window
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // 1. Add the first k + 1 elements to the heap.
        // The smallest element must be within this range [0...k].
        int n = arr.length;
        // Handle cases where k is larger than the array size
        int initialWindow = Math.min(n, k + 1);

        for (int i = 0; i < initialWindow; i++) {
            minHeap.add(arr[i]);
        }

        // 2. Process the remaining elements
        int targetIndex = 0;
        for (int i = k + 1; i < n; i++) {
            // The smallest element currently in the heap is the correct element
            // for the current targetIndex.
            arr[targetIndex++] = minHeap.poll();

            // Add the next element from the array to maintain the window size
            minHeap.add(arr[i]);
        }

        // 3. The array is exhausted, but the heap still contains the remaining largest elements.
        while (!minHeap.isEmpty()) {
            arr[targetIndex++] = minHeap.poll();
        }
    }
}