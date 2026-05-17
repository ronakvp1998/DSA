package com.questions.strivers.arrays.easy;

import java.util.ArrayList;

/*
https://takeuforward.org/data-structure/move-all-zeros-to-the-end-of-the-array/
Problem:
--------
Given an integer array, move all zeros to the end while maintaining the relative order of non-zero elements.

Example:
--------
Input:  [1, 0, 2, 3, 2, 0, 0, 4, 5, 1]
Output: [1, 2, 3, 2, 4, 5, 1, 0, 0, 0]
*/

public class MoveZerosEnd {

    // ----------------------------------------------------------------
    // Approach 1: Using Temporary Array (Extra Space)
    // ----------------------------------------------------------------
    /*
    Logic:
    - Store all non-zero elements in a temporary list.
    - Copy them back to the original array.
    - Fill the rest with zeros.
    */
    private static int[] moveZerosExtraSpace(int n, int[] a) {
        ArrayList<Integer> temp = new ArrayList<>();

        // Step 1: Copy all non-zero elements to temp
        for (int i = 0; i < n; i++) {
            if (a[i] != 0) {
                temp.add(a[i]);
            }
        }

        // Step 2: Copy non-zero elements back to array
        int nz = temp.size(); // number of non-zero elements
        for (int i = 0; i < nz; i++) {
            a[i] = temp.get(i);
        }

        // Step 3: Fill remaining positions with zero
        for (int i = nz; i < n; i++) {
            a[i] = 0;
        }
        return a;
    }
    /*
     🕒 Time Complexity: O(n)
     🧠 Space Complexity: O(n) (due to temp ArrayList)
     ✅ Simple to understand
     ❌ Uses extra space
    */

    // ----------------------------------------------------------------
    // Approach 2: Two-Pointer Method (In-place, O(1) Space)
    // ----------------------------------------------------------------
    /*
    Logic:
    - First, find the index j of the first zero.
    - Then, iterate from j+1 onwards:
        - If a non-zero is found, swap with position j, then increment j.
    - This shifts non-zero elements forward while maintaining order.
    */
    private static int[] moveZerosInPlace(int n, int[] a) {
        int j = -1; // index of the first zero

        // Step 1: Find the first zero index
        for (int i = 0; i < n; i++) {
            if (a[i] == 0) {
                j = i;
                break;
            }
        }

        // If there are no zeros, return as is
        if (j == -1) return a;

        // Step 2: Swap non-zero elements with the zero at position j
        for (int i = j + 1; i < n; i++) {
            if (a[i] != 0) {
                // Swap a[i] and a[j]
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
                j++; // move j to next zero
            }
        }
        return a;
    }
    /*
     🕒 Time Complexity: O(n)
     🧠 Space Complexity: O(1)
     ✅ In-place and efficient
     ✅ Preserves order
     ✅ Preferred approach
    */

    // ----------------------------------------------------------------
    // Driver code
    // ----------------------------------------------------------------
    public static void main(String[] args) {
        int[] arr1 = {1, 0, 2, 3, 2, 0, 0, 4, 5, 1};
        int[] arr2 = arr1.clone();

        int n = arr1.length;

        // Using extra space
        moveZerosExtraSpace(n, arr1);
        System.out.print("Using Extra Space: ");
        for (int num : arr1) System.out.print(num + " ");
        System.out.println();

        // Using in-place method
        moveZerosInPlace(n, arr2);
        System.out.print("Using In-place Method: ");
        for (int num : arr2) System.out.print(num + " ");
    }
}
