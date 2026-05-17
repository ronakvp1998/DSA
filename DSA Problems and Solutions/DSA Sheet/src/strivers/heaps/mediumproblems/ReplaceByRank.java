package com.questions.strivers.heaps.mediumproblems;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: Replace Elements by its Rank in the Array
 * ==================================================================================================
 * Given an array of N integers, replace each element with its rank.
 * The rank represents the position of an element when the array is sorted in ascending order.
 * If multiple elements have the same value, they share the same rank.
 *
 * Example 1:
 * Input:  [20, 15, 26, 2, 98, 6]
 * Output: [4, 3, 5, 1, 6, 2]
 * Explanation: Sorted unique values: 2(1), 6(2), 15(3), 20(4), 26(5), 98(6).
 *
 * Example 2:
 * Input:  [1, 5, 8, 15, 8, 25, 9]
 * Output: [1, 2, 3, 5, 3, 6, 4]
 * Explanation: Sorted unique values: 1(1), 5(2), 8(3), 9(4), 15(5), 25(6).
 * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (Nested Loops)
 * ==================================================================================================
 * For each element, iterate through the entire array to count how many unique elements
 * are smaller than it.
 * Drawback: Time Complexity is O(N^2), which is too slow for large inputs.
 * ==================================================================================================
 * APPROACH 2: OPTIMAL (Sorting + HashMap)
 * ==================================================================================================
 * 1. Clone the original array.
 * 2. Sort the cloned array -> O(N log N).
 * 3. Use a HashMap to store the rank of each unique element.
 * - Iterate through the sorted array.
 * - Assign a rank starting from 1.
 * - If an element is already in the map, skip it (to handle duplicates).
 * 4. Replace original array elements with values from the HashMap.
 * ==================================================================================================
 */

import java.util.*;

public class ReplaceByRank {

    public static void main(String[] args) {
        int[] arr1 = {20, 15, 26, 2, 98, 6};
        System.out.println("Original: " + Arrays.toString(arr1));
        System.out.println("Ranked:   " + Arrays.toString(replaceWithRank(arr1)));

        int[] arr2 = {1, 5, 8, 15, 8, 25, 9};
        System.out.println("\nOriginal: " + Arrays.toString(arr2));
        System.out.println("Ranked:   " + Arrays.toString(replaceWithRank(arr2)));
    }

    /**
     * Replaces each element in the array with its rank.
     * Time Complexity: O(N log N)
     * Space Complexity: O(N)
     */
    public static int[] replaceWithRank(int[] arr) {
        int n = arr.length;
        if (n == 0) return arr;

        // 1. Clone the original array so we don't lose the original positions
        int[] sortedCopy = arr.clone();

        // 2. Sort the cloned array
        Arrays.sort(sortedCopy);

        // 3. Map each unique element to its rank
        Map<Integer, Integer> rankMap = new HashMap<>();
        int currentRank = 1;

        for (int i = 0; i < n; i++) {
            // Only assign a rank if the element hasn't been seen before (handles duplicates)
            if (!rankMap.containsKey(sortedCopy[i])) {
                rankMap.put(sortedCopy[i], currentRank);
                currentRank++;
            }
        }

        // 4. Replace elements in the original array with their rank from the map
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = rankMap.get(arr[i]);
        }

        return result;
    }
}