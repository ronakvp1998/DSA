package com.questions.strivers.arrays.easy;

// https://takeuforward.org/data-structure/remove-duplicates-in-place-from-sorted-array/

/*
Problem Statement:
------------------
Given a sorted integer array (in non-decreasing order), remove duplicates in-place so that
each unique element appears only once.

- The relative order of elements must remain the same.
- Return the count `k` of unique elements.
- The first `k` elements of the array should be the final result.
- It does not matter what values remain beyond the first `k` slots.

Example:
--------
Input:  arr = [1, 1, 2]
Output: k = 2, arr = [1, 2, _]

Input:  arr = [0,0,1,1,1,2,2,3,3,4]
Output: k = 5, arr = [0, 1, 2, 3, 4, _ , _ , _ , _ , _ ]
*/

import java.util.Arrays;
import java.util.HashSet;

public class RemoveDuplicateSortedArray {

    public static void main(String[] args) {
        int[] arr1 = {1, 1, 2};
        int[] arr2 = {0,0,1,1,1,2,2,3,3,4};

        // Brute Force using HashSet (order not guaranteed)
        int k1 = removeDuplicates1(arr1);
        System.out.println("Brute Force (HashSet) → k = " + k1 + ", array = " + Arrays.toString(arr1));

        // Optimal Two-Pointer method (order preserved)
        int k2 = removeDuplicates2(arr2);
        System.out.println("Optimal Two-Pointer → k = " + k2 + ", array = " + Arrays.toString(arr2));
    }

    // -----------------------------------------------------------------
    // ✅ Approach 1: Brute Force using HashSet
    // -----------------------------------------------------------------
    /*
    Logic:
    - Store all elements in a HashSet → automatically removes duplicates.
    - Copy unique elements back into the original array.
    - Return the size of the set (count of unique elements).

    ⚠ Note: Since HashSet does not guarantee order, this approach
      will NOT necessarily preserve the sorted order.
    */
    static int removeDuplicates1(int[] arr) {
        HashSet<Integer> set = new HashSet<>();

        // Add all elements to the set
        for (int num : arr) {
            set.add(num);
        }

        int k = set.size(); // number of unique elements

        // Copy back to array (order may change)
        int idx = 0;
        for (int val : set) {
            arr[idx++] = val;
        }

        return k;
    }
    /*
     🕒 Time Complexity: O(n)
        - O(n) to insert into HashSet
        - O(n) to copy back
        Overall: O(n)

     🧠 Space Complexity: O(n)
        - HashSet stores up to n elements

     ❌ Drawback: Does NOT preserve original sorted order
    */

    // -----------------------------------------------------------------
    // ✅ Approach 2: Optimal Two-Pointer Method (for sorted arrays)
    // -----------------------------------------------------------------
    /*
    Logic:
    - Use two pointers: `i` for the last unique position, `j` for scanning.
    - Start from index 1, compare arr[j] with arr[i].
    - If different → increment i, assign arr[i] = arr[j].
    - At the end, `i + 1` is the count of unique elements.
    - This works because the array is already sorted.

    This preserves order and does not require extra space.
    */
    static int removeDuplicates2(int[] arr) {
        if (arr.length == 0) return 0; // handle empty array

        int i = 0; // last unique index

        for (int j = 1; j < arr.length; j++) {
            if (arr[i] != arr[j]) {
                i++;
                arr[i] = arr[j];
            }
        }
        return i + 1; // length of unique part
    }
    /*
     🕒 Time Complexity: O(n)
        - Single pass through the array

     🧠 Space Complexity: O(1)
        - No extra space used (in-place)

     ✅ This is the recommended approach for sorted arrays
    */
}
