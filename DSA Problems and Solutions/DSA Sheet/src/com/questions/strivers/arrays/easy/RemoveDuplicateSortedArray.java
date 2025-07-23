package com.questions.strivers.arrays.easy;

//https://takeuforward.org/data-structure/remove-duplicates-in-place-from-sorted-array/
//Problem Statement: Given an integer array sorted in non-decreasing order,
// remove the duplicates in place such that each unique element appears only once.
//The relative order of the elements should be kept the same.
//If there are k elements after removing the duplicates, then the first k elements of the array should hold the final result.
// It does not matter what you leave beyond the first k elements.
//Note: Return k after placing the final result in the first k slots of the array.

import java.util.Arrays;
import java.util.HashSet;

public class RemoveDuplicateSortedArray {

    public static void main(String[] args) {
        int[] arr = {1, 1, 2};

        // Using brute-force method with HashSet
        System.out.println(removeDuplicates1(arr)); // Output: number of unique elements
    }

    // ✅ Brute-force method using HashSet (does NOT preserve sorted order)
    static int removeDuplicates1(int[] arr) {
        // Create a HashSet to store only unique elements
        HashSet<Integer> set = new HashSet<>();

        // Add all elements from the array to the HashSet
        for (int i = 0; i < arr.length; i++) {
            set.add(arr[i]); // duplicates are automatically removed
        }

        // `k` stores the count of unique elements
        int k = set.size();

        // Copy unique elements back to the original array (unordered)
        int j = 0;
        for (int x : set) {
            arr[j++] = x;
        }

        return k; // return the count of unique elements
    }

    /*
     🕒 Time Complexity: O(n)
     → O(n) for insertion into HashSet, O(n) for copying back

     🧠 Space Complexity: O(n)
     → Extra space used for the HashSet
     ⚠️ Note: This approach does not preserve order, which can be an issue with sorted arrays
    */


    // ✅ Optimal method for sorted array (in-place and order-preserving)
    static int removeDuplicates2(int[] arr) {
        // i points to the position of the last unique element
        int i = 0;

        // Traverse the array starting from the second element
        for (int j = 1; j < arr.length; j++) {
            // If a new unique element is found
            if (arr[i] != arr[j]) {
                i++;           // move i to next position
                arr[i] = arr[j]; // copy the unique element
            }
        }

        return i + 1; // length of unique portion
    }

    /*
     🕒 Time Complexity: O(n)
     → Single pass through the array

     🧠 Space Complexity: O(1)
     → No extra space used, in-place operation
     ✅ Recommended approach for sorted arrays
    */
}