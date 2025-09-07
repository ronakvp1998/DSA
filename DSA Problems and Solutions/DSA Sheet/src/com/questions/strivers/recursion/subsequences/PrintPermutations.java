package com.questions.strivers.recursion.subsequences;

import java.util.ArrayList;
import java.util.List;

/*
Problem Statement:
    Given an array of distinct integers, print all possible permutations of the array.
    Each permutation should contain all elements exactly once.

Example:
    Input: arr = [1, 2, 3]
    Output:
        [ [1, 2, 3],
          [1, 3, 2],
          [2, 1, 3],
          [2, 3, 1],
          [3, 1, 2],
          [3, 2, 1] ]

We will solve this using two standard approaches:

-------------------------------------------------------------------------------
Approach 1: Using Frequency Array (Backtracking with extra boolean array)
-------------------------------------------------------------------------------
- Maintain a boolean array `freq[]` to track which elements are already used.
- At each recursion step, try placing an unused element into the current list.
- Once the current list reaches size n, we add it to the result.
- Backtracking ensures we explore all possibilities.

Time Complexity:
    - There are n! permutations for n elements.
    - Each permutation requires O(n) work to copy the current list.
    - Overall: O(n * n!).

Space Complexity:
    - Recursion stack: O(n).
    - Frequency array: O(n).
    - Temporary list: O(n).
    - Result storage: O(n * n!) (to store all permutations).
    - Auxiliary space (excluding result): O(n).

-------------------------------------------------------------------------------

*/

public class PrintPermutations {
    public static void main(String[] args) {
        int arr[] = {1, 2, 3};

        // Approach 1: Using frequency array
        boolean[] freq = new boolean[arr.length]; // keeps track of used elements
        List<List<Integer>> list1 = new ArrayList<>();
        generatePermutationWithFreq(arr, new ArrayList<>(), list1, freq);
        System.out.println("Approach 1 (Using freq[]): " + list1);

        // Approach 2: Using swapping
        List<List<Integer>> list2 = new ArrayList<>();
        generatePermutationBySwapping(0, arr, list2);
        System.out.println("Approach 2 (Using swapping): " + list2);
    }

    /*
     * Approach 1: Using frequency array
     * Recursively build permutations by picking unused elements.
     */
    public static void generatePermutationWithFreq(int arr[], List<Integer> temp,
                                                   List<List<Integer>> list, boolean[] freq) {
        // Base case: one complete permutation is formed
        if (temp.size() == arr.length) {
            list.add(new ArrayList<>(temp)); // add a copy of current permutation
            return;
        }

        // Try each element
        for (int i = 0; i < arr.length; i++) {
            if (!freq[i]) { // if not used yet
                freq[i] = true;            // mark as used
                temp.add(arr[i]);          // add to current permutation
                generatePermutationWithFreq(arr, temp, list, freq); // recurse
                temp.remove(temp.size() - 1); // backtrack
                freq[i] = false;           // unmark for future use
            }
        }
    }

    /*
     * Approach 2: Using swapping
     * Generate permutations by swapping elements in-place.
- At index `i`, swap each element from index `i` to n-1 with `i`.
- This "fixes" one element in place, then recurse for the next index.
- After recursion, swap back (backtracking) to restore the original array.
- This way we generate permutations in-place without using extra freq[] array.

Time Complexity:
    - Same as above: O(n * n!).
    - Because we generate n! permutations and copying each permutation costs O(n).

Space Complexity:
    - Recursion stack: O(n).
    - No extra frequency array needed (O(1) extra).
    - Result storage: O(n * n!).
    - Auxiliary space (excluding result): O(n).

-------------------------------------------------------------------------------
     */
    public static void generatePermutationBySwapping(int index, int arr[], List<List<Integer>> list) {
        // Base case: one complete permutation is formed
        if (index == arr.length) {
            List<Integer> temp = new ArrayList<>();
            for (int num : arr) temp.add(num); // convert array → list
            list.add(temp);
            return;
        }

        // Swap current index with each index from 'index' to end
        for (int i = index; i < arr.length; i++) {
            swap(arr, index, i); // place arr[i] at position 'index'
            generatePermutationBySwapping(index + 1, arr, list); // recurse
            swap(arr, index, i); // backtrack (restore original array)
        }
    }

    // Utility function to swap two elements in an array
    private static void swap(int arr[], int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
