package com.questions.strivers.arrays.medium;

import java.util.*;
import java.util.stream.Collectors;

/*
 * https://takeuforward.org/data-structure/leaders-in-an-array/
 *
 * Problem Statement:
 * Given an array, print all the elements which are leaders.
 * A Leader is an element that is greater than or equal to all the elements on its right side in the array.
 *
 * Examples:
 * Example 1:
 * Input: arr = [4, 7, 1, 0]
 * Output: 7 1 0
 *
 * Example 2:
 * Input: arr = [10, 22, 12, 3, 0, 6]
 * Output: 22 12 6
 */

public class LeaderInArray {
    public static void main(String[] args) {
        int arr[] = {10, 22, 12, 3, 0, 6};

        // Brute Force method
        System.out.println(leaderArrayBrute(arr));

        // Optimized method (add to list then reverse at the end)
        System.out.println(leaderArrayOptimized(arr));

        // Optimized method (addFirst into LinkedList, avoids reverse)
        System.out.println(leaderArrayAddFirst(arr));
    }

    /**
     * Brute Force Approach
     * Logic:
     * - For each element arr[i], check all elements to its right.
     * - If no element is greater, arr[i] is a leader.
     *
     * Time Complexity: O(n^2) → For each element, scanning the right side.
     * Space Complexity: O(k) → Storing leaders in result set/list (k ≤ n).
     */
    public static List<Integer> leaderArrayBrute(int arr[]) {
        Set<Integer> res = new HashSet<>(); // Stores leaders without duplicates

        // Outer loop: pick each element
        for (int i = 0; i < arr.length; i++) {
            boolean isLeader = true; // Assume current element is a leader

            // Inner loop: compare with all elements to its right
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] < arr[j]) { // If any right element is greater
                    isLeader = false;  // Not a leader
                    break;             // No need to check further
                }
            }

            // If leader remains true, add to result
            if (isLeader) {
                res.add(arr[i]);
            }
        }

        // Convert Set → List and sort for consistent output
        return res.stream().sorted().collect(Collectors.toList());
    }

    /**
     * Optimized Approach (Reverse at end)
     * Logic:
     * - Start from rightmost element (always a leader).
     * - Keep track of maxElement so far.
     * - If current element >= maxElement, it's a leader.
     * - Append leaders to list, then reverse at the end.
     *
     * Time Complexity: O(n) → Single pass from right to left + O(k) reverse.
     * Space Complexity: O(k) → To store leaders (k ≤ n).
     */
    public static List<Integer> leaderArrayOptimized(int arr[]) {
        List<Integer> res = new ArrayList<>(); // Stores leaders in reverse order

        int maxElement = arr[arr.length - 1]; // Last element is always a leader
        res.add(maxElement);

        // Traverse array from second-last to first element
        for (int i = arr.length - 2; i >= 0; i--) {
            if (arr[i] >= maxElement) { // If current element is >= all seen so far
                maxElement = arr[i];   // Update maxElement
                res.add(arr[i]);       // Add current element to list
            }
        }

        // Reverse to restore correct order
        Collections.reverse(res);
        return res;
    }

    /**
     * Optimized Approach (AddFirst to avoid reverse)
     * Logic:
     * - Traverse from right to left.
     * - Maintain the maximum seen so far.
     * - If current > max, insert at front of list.
     * - Using LinkedList allows O(1) insertion at the front.
     *
     * Time Complexity: O(n) → Single pass from right to left.
     * Space Complexity: O(k) → To store leaders.
     */
    public static List<Integer> leaderArrayAddFirst(int arr[]) {
        LinkedList<Integer> res = new LinkedList<>(); // Supports addFirst in O(1)

        int max = Integer.MIN_VALUE; // Initialize max to smallest integer

        // Traverse array from rightmost to leftmost element
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] > max) {   // Found a new leader
                max = arr[i];     // Update max
                res.addFirst(arr[i]); // Insert at the front (no reverse needed)
            }
        }

        return res;
    }
}
