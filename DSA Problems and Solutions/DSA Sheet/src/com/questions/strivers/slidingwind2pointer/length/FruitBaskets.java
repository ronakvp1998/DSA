package com.questions.strivers.slidingwind2pointer.length;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
===========================================================
L5. Fruit Into Baskets
-----------------------------------------------------------
Problem Statement:
We are given an array 'fruits' where fruits[i] represents
the type of fruit at the i-th tree. You have two baskets
and each basket can hold only one type of fruit, but can
store unlimited quantity of that type.

Rules:
1. You must start picking from any tree and move only to the right.
2. From each tree, you must pick exactly one fruit.
3. You must stop when you encounter a tree with fruit that
   cannot fit into either of the two baskets.

Task:
Return the maximum number of fruits you can pick.

Examples:
Input : [1,2,1]
Output: 3
Explanation: Basket1 = {1,1}, Basket2 = {2}.

Input : [1,2,3,2,2]
Output: 4
Explanation: Basket1 = {2,2,2}, Basket2 = {3}.
===========================================================
*/

public class FruitBaskets {

    public static void main(String[] args) {
        int arr[] = {3,3,3,1,2,1,1,2,3,3,4};
        // Using brute force approach
        System.out.println("Brute Force: " + fruitBaskets1(arr));
        // Using optimized sliding window approach
        System.out.println("Sliding Window: " + fruitBaskets2(arr));
    }

    /*
     * Approach 1: Brute Force
     * --------------------------------------------------------
     * - Generate all subarrays starting from each index.
     * - Use a HashSet to track distinct fruit types.
     * - If size of set <= 2, update max length.
     * - Stop inner loop when we encounter > 2 fruit types.
     *
     * Time Complexity: O(n^2)
     * - Outer loop runs n times, inner loop up to n times.
     * Space Complexity: O(3) ≈ O(1)
     * - HashSet stores at most 3 fruit types before breaking.
     */
    public static int fruitBaskets1(int arr[]) {
        int maxLen = 0;
        for (int i = 0; i < arr.length; i++) {
            Set<Integer> set = new HashSet<>();
            for (int j = i; j < arr.length; j++) {
                set.add(arr[j]); // add current fruit to the set
                if (set.size() <= 2) {
                    // valid subarray since ≤ 2 fruit types
                    maxLen = Math.max(maxLen, j - i + 1);
                } else {
                    // more than 2 fruit types → invalid
                    break;
                }
            }
        }
        return maxLen;
    }

    /*
     * Approach 2: Sliding Window + HashMap (Optimized)
     * --------------------------------------------------------
     * - Use two pointers (left, right) to represent a window.
     * - Expand 'right' and count fruit occurrences in a map.
     * - If map size > 2, shrink window from 'left' until size ≤ 2.
     * - Update maximum length of valid window.
     *
     * Time Complexity: O(n)
     * - Each element is added/removed from the map at most once.
     * Space Complexity: O(2) ≈ O(1)
     * - HashMap stores at most 2 fruit types at a time.
     */
    public static int fruitBaskets2(int arr[]) {
        int n = arr.length;
        int maxLen = 0, left = 0, right = 0;
        Map<Integer, Integer> map = new HashMap<>();

        while (right < n) {
            // add current fruit to map
            map.put(arr[right], map.getOrDefault(arr[right], 0) + 1);

            // shrink window until at most 2 fruit types remain
            while (map.size() > 2) {
                map.put(arr[left], map.get(arr[left]) - 1);
                if (map.get(arr[left]) == 0) {
                    map.remove(arr[left]); // remove fruit type if count is 0
                }
                left++; // shrink window
            }

            // update max length of valid window
            maxLen = Math.max(maxLen, right - left + 1);
            right++;
        }
        return maxLen;
    }
}

