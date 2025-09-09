package com.questions.strivers.recursion.basics.stacksqueuesproblem;

import java.util.ArrayList;
import java.util.List;

/**
 * Problem Statement:
 * Sort a queue (or list) using recursion.
 * We are not allowed to use loops or any extra data structures,
 * only recursion for both sorting and inserting elements in order.
 *
 * Approach:
 * 1. Recursively remove the last element until the list becomes empty.
 * 2. Sort the remaining list recursively.
 * 3. Insert the removed element back into the list in the correct order
 *    (either ascending or descending) using another recursive function.
 *
 * This works like recursive insertion sort.
 */
public class SortQueueRecursion {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(0);
        list.add(8);
        list.add(5);
        list.add(2);

        System.out.println("Original List: " + list);

        // Uncomment one of the methods based on desired order
        // sortrecAsc(list);   // Sort in Ascending Order
        sortRecDec(list);      // Sort in Descending Order

        System.out.println("Sorted List: " + list);
    }

    /**
     * Sorts the list in Ascending Order using recursion.
     * Steps:
     * - Base condition: if list is empty, return.
     * - Remove the last element.
     * - Recursively sort the remaining list.
     * - Insert the removed element back into its correct position.
     */
    public static void sortrecAsc(List<Integer> list) {
        if (list.size() == 0) {
            return;
        }

        int val = list.get(list.size() - 1); // Remove last element
        list.remove(list.size() - 1);

        sortrecAsc(list); // Sort remaining elements recursively

        recInsertAsc(list, val); // Insert current element into sorted list
    }

    /**
     * Inserts an element into the list in Ascending Order using recursion.
     * - If list is empty OR last element <= current element → place element.
     * - Else, remove last element, recurse, then add it back.
     */
    public static void recInsertAsc(List<Integer> list, int temp) {
        if (list.size() == 0 || list.get(list.size() - 1) <= temp) {
            list.add(temp);
            return;
        }

        int val = list.get(list.size() - 1); // Remove last element
        list.remove(list.size() - 1);

        recInsertAsc(list, temp); // Recurse until correct position found

        list.add(val); // Backtrack and add elements back
    }

    /**
     * Sorts the list in Descending Order using recursion.
     * Steps are the same as ascending, but insertion logic is reversed.
     */
    public static void sortRecDec(List<Integer> list) {
        if (list.isEmpty()) {
            return;
        }

        int val = list.get(list.size() - 1); // Remove last element
        list.remove(list.size() - 1);

        sortRecDec(list); // Sort remaining elements recursively

        recInsertdesc(list, val); // Insert element in descending order
    }

    /**
     * Inserts an element into the list in Descending Order using recursion.
     * - If list is empty OR last element >= current element → place element.
     * - Else, remove last element, recurse, then add it back.
     */
    public static void recInsertdesc(List<Integer> list, int temp) {
        if (list.isEmpty() || list.get(list.size() - 1) >= temp) {
            list.add(temp);
            return;
        }

        int val = list.get(list.size() - 1); // Remove last element
        list.remove(list.size() - 1);

        recInsertdesc(list, temp); // Recurse until correct position found

        list.add(val); // Backtrack and add elements back
    }
}

/**
 * ⏱ Time Complexity:
 * - For each element, we recursively remove and insert it in the right position.
 * - In worst case, insertion takes O(N) for each element.
 * - Hence, total = O(N^2).
 *
 * 📦 Space Complexity:
 * - O(N) recursive call stack (since recursion depth can go up to N).
 * - No extra data structures are used.
 */
