package com.questions.strivers.recursion.medium.subsequences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
Problem Statement:
Given an array, print all the sums of the subsets generated from it, in increasing order.

Example 1:
Input: N = 3, arr[] = {5,2,1}
Output: 0,1,2,3,5,6,7,8

Example 2:
Input: N = 3, arr[] = {3,1,2}
Output: 0,1,2,3,3,4,5,6
 */

public class Subset1 {

    public static void main(String[] args) {
        int arr[] = {3, 1, 2};
        int n = arr.length;

        System.out.println("Approach 1: Pick / Not Pick Recursion");
        List<Integer> res1 = new ArrayList<>();
        subsetSumPickNotPick(0, 0, arr, n, res1);
        Collections.sort(res1);
        System.out.println(res1);

        System.out.println("\nApproach 2: DFS Loop Recursion");
        List<Integer> res2 = new ArrayList<>();
        subsetSumDFS(0, 0, arr, res2);
        Collections.sort(res2);
        System.out.println(res2);

        System.out.println("\nApproach 3: Backtracking with subset list");
        List<Integer> res3 = new ArrayList<>();
        subsetSumBacktrack(0, arr, new ArrayList<>(), res3);
        Collections.sort(res3);
        System.out.println(res3);
    }

    // -----------------------------------------------------------------
    // Approach 1: Classic Pick / Not Pick Recursion
    // Time Complexity: O(2^n)
    // Space Complexity: O(n) recursion stack
    // -----------------------------------------------------------------
    public static void subsetSumPickNotPick(int index, int sum, int[] arr, int n, List<Integer> result) {
        if (index == n) {
            result.add(sum);
            return;
        }
        // Pick the element
        subsetSumPickNotPick(index + 1, sum + arr[index], arr, n, result);

        // Not pick the element
        subsetSumPickNotPick(index + 1, sum, arr, n, result);
    }

    // -----------------------------------------------------------------
    // Approach 2: DFS Loop Recursion
    // Uses a for-loop to simulate subset choices
    // Time Complexity: O(2^n)
    // Space Complexity: O(n) recursion stack
    // -----------------------------------------------------------------
    public static void subsetSumDFS(int index, int sum, int[] arr, List<Integer> result) {
        result.add(sum); // record sum at each step
        for (int i = index; i < arr.length; i++) {
            subsetSumDFS(i + 1, sum + arr[i], arr, result);
        }
    }

    // -----------------------------------------------------------------
    // Approach 3: Backtracking with subset list
    // Generates subsets explicitly and computes sum at leaf
    // Time Complexity: O(2^n * n) (extra O(n) to compute sum at each leaf)
    // Space Complexity: O(n) recursion + O(n) subset storage
    // -----------------------------------------------------------------
    public static void subsetSumBacktrack(int index, int[] arr, List<Integer> temp, List<Integer> result) {
        if (index == arr.length) {
            int sum = 0;
            for (int num : temp) sum += num;
            result.add(sum);
            return;
        }

        // Pick element
        temp.add(arr[index]);
        subsetSumBacktrack(index + 1, arr, temp, result);

        // Backtrack (remove last picked)
        temp.remove(temp.size() - 1);

        // Not Pick element
        subsetSumBacktrack(index + 1, arr, temp, result);
    }
}
