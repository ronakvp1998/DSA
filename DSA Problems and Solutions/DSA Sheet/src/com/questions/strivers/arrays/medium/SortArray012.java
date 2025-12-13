package com.questions.strivers.arrays.medium;

import java.util.Arrays;

// LeetCode Link: https://leetcode.com/problems/sort-colors/
// Problem from: Striver SDE Sheet, Page 3 Book 4
//https://takeuforward.org/data-structure/sort-an-array-of-0s-1s-and-2s/
//Problem Statement: Given an array consisting of only 0s, 1s, and 2s.
//Write a program to in-place sort the array without using inbuilt sort functions.
//        ( Expected: Single pass-O(N) and constant space)

public class SortArray012 {

    public static void main(String[] args) {
        // Sample input array containing only 0s, 1s, and 2s
        int arr[] = {0,1,1,0,1,2,1,2,0,0,0};

        // Call sorting function
        sortArray(arr, arr.length);

        // Print the sorted array
        System.out.println(Arrays.toString(arr));
    }

    // Function to sort the array using Dutch National Flag Algorithm
    private static void sortArray(int arr[], int n) {
        int low = 0;      // Points to the beginning of the array (boundary for 0s)
        int mid = 0;      // Traverses the array
        int high = n - 1; // Points to the end of the array (boundary for 2s)

        // Continue until mid crosses high
        while (mid <= high) {
            if (arr[mid] == 0) {
                // If current element is 0, swap it to the 'low' region
                swap(arr, mid, low);
                mid++;    // move to next element
                low++;    // expand the 0s region
            } else if (arr[mid] == 1) {
                // If current element is 1, just move forward
                mid++;
            } else {
                // If current element is 2, swap it to the 'high' region
                swap(arr, mid, high);
                high--;   // shrink the 2s region
                // don't increment mid, because swapped value could be 0 or 1
            }
        }
    }

    // Utility function to swap two elements in the array
    private static void swap(int arr[], int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}