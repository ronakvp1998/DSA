package com.questions.strivers.recursion.subsequences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 Combination Sum II - Find all unique combinations
 -------------------------------------------------
 Problem Statement:
 Given a collection of candidate numbers (candidates) and a target number (target),
 find all unique combinations in candidates where the candidate numbers sum to target.
 Each number in candidates may only be used once in the combination.

 Note: The solution set must not contain duplicate combinations.

 Example 1:
 Input: candidates = [10,1,2,7,6,1,5], target = 8
 Output: [[1,1,6],[1,2,5],[1,7],[2,6]]

 Example 2:
 Input: candidates = [2,5,2,1,2], target = 5
 Output: [[1,2,2],[5]]
*/

public class CombinationSum2 {

    public static void main(String[] args) {
        int arr[] = {1, 1, 1, 2, 2, 4};
        int target = 4;

        // ✅ Optimized Backtracking Approach
        List<List<Integer>> ans1 = new ArrayList<>();
        Arrays.sort(arr); // sort required for skipping duplicates
        combinationSum2(0, arr, ans1, new ArrayList<>(), target);
        System.out.println("Backtracking Result: " + ans1);

        // ✅ Brute Force Approach
        List<List<Integer>> ans2 = combinationSum2BruteForce(arr, target);
        System.out.println("Brute Force Result: " + ans2);
    }

    /**
     * Optimized Backtracking Approach
     * --------------------------------
     * - Sort the array to handle duplicates.
     * - At each index:
     *   1. Either pick the element (and move to next index since we can use it only once).
     *   2. Or skip it.
     * - Skip duplicates using: if(i > index && arr[i] == arr[i-1]) continue.
     * - Stop exploring when arr[i] > target (pruning).
     *
     * Time Complexity: O(2^n) in worst case (each element has pick/not-pick choice)
     *                  but pruning and duplicate skipping reduce calls significantly.
     * Space Complexity: O(n) recursion depth + O(k) for temporary list
     *                  where k = average size of combination.
     */
    public static void combinationSum2(int index, int arr[], List<List<Integer>> ans,
                                       List<Integer> ds, int target) {
        if (target == 0) {
            ans.add(new ArrayList<>(ds));
            return;
        }
        for (int i = index; i < arr.length; i++) {
            if (i > index && arr[i] == arr[i - 1]) {
                continue; // skip duplicates
            }
            if (arr[i] > target) {
                break; // prune search space
            }
            ds.add(arr[i]);
            combinationSum2(i + 1, arr, ans, ds, target - arr[i]);
            ds.remove(ds.size() - 1); // backtrack
        }
    }

    /**
     * Brute Force Approach
     * ---------------------
     * - Generate ALL subsequences (2^n possibilities).
     * - For each subsequence, check if sum == target.
     * - Use a Set to remove duplicate combinations.
     *
     * Time Complexity: O(n * 2^n)
     *   - 2^n subsequences
     *   - Each subsequence can take O(n) to compute sum
     *   - Inserting into Set is O(logM) or O(1) average
     * Space Complexity: O(2^n * n) for storing subsets in worst case
     *                   O(n) recursion stack
     */
    public static List<List<Integer>> combinationSum2BruteForce(int[] arr, int target) {
        Arrays.sort(arr); // sorting ensures duplicates can be compared in Set properly
        Set<List<Integer>> set = new HashSet<>();
        generateSubsequences(0, arr, new ArrayList<>(), set, target);
        return new ArrayList<>(set);
    }

    private static void generateSubsequences(int idx, int[] arr, List<Integer> current,
                                             Set<List<Integer>> set, int target) {
        if (idx == arr.length) {
            int sum = 0;
            for (int num : current) sum += num;
            if (sum == target) {
                set.add(new ArrayList<>(current));
            }
            return;
        }

        // Include current element
        current.add(arr[idx]);
        generateSubsequences(idx + 1, arr, current, set, target);

        // Exclude current element
        current.remove(current.size() - 1);
        generateSubsequences(idx + 1, arr, current, set, target);
    }
}
