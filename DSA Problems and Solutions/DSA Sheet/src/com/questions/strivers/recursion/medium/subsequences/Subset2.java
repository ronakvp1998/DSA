package com.questions.strivers.recursion.medium.subsequences;

import java.util.*;

/*
Problem Statement:
-------------------
Given an array of integers that may contain duplicates, the task is to return all possible subsets.
Return only unique subsets (no duplicates). The subsets can be in any order.

Examples:
---------
Input: array[] = [1,2,2]
Output: [ [ ],[1],[1,2],[1,2,2],[2],[2,2] ]

Input: array[] = [1]
Output: [ [ ], [1] ]
 */

public class Subset2 {

    // ----------- Brute Force Approach (using HashSet) -----------
    // Generate ALL subsets (using pick/not pick recursion) and store them as Strings in a HashSet.
    // Sorting each subset before insertion ensures duplicates are avoided.
    public static void fun(int[] nums, int index, List<Integer> ds, HashSet<String> res) {
        // Base case: if index reaches end of array, store subset
        if (index == nums.length) {
            Collections.sort(ds); // sort to handle duplicates (e.g., [2,1] vs [1,2])
            res.add(ds.toString()); // HashSet ensures uniqueness
            return;
        }

        // Pick element
        ds.add(nums[index]);
        fun(nums, index + 1, ds, res);

        // Backtrack (remove last element)
        ds.remove(ds.size() - 1);

        // Not pick element
        fun(nums, index + 1, ds, res);
    }

    // Wrapper for brute force approach
    public static List<String> subsetsWithDup(int[] nums) {
        List<String> ans = new ArrayList<>();
        HashSet<String> res = new HashSet<>();
        fun(nums, 0, new ArrayList<>(), res);

        // Convert HashSet to List
        ans.addAll(res);
        return ans;
    }

    /*
     * Time Complexity (Brute Force):
     * - O(2^n * n log n)
     *   - There are 2^n subsets.
     *   - Sorting each subset (size up to n) costs O(n log n).
     *   - HashSet insert is O(1) average.
     *
     * Space Complexity:
     * - O(2^n * n) for storing all subsets in HashSet.
     * - O(n) recursion depth.
     */


    // ----------- Optimized Approach (Backtracking + Pruning) -----------
    // Sort array first, then generate subsets recursively.
    // Skip duplicates by ensuring we only pick the *first occurrence* of a number at each recursion depth.
    public static void findSubsets(int ind, int[] nums, List<Integer> ds, List<List<Integer>> ansList) {
        // Add current subset to answer
        ansList.add(new ArrayList<>(ds));

        // Iterate over remaining elements
        for (int i = ind; i < nums.length; i++) {
            // Skip duplicates: if same number appears at same recursion depth
            if (i != ind && nums[i] == nums[i - 1]) continue;

            // Pick element
            ds.add(nums[i]);
            findSubsets(i + 1, nums, ds, ansList);

            // Backtrack
            ds.remove(ds.size() - 1);
        }
    }

    public static List<List<Integer>> subsetsWithDup2(int[] nums) {
        Arrays.sort(nums); // sorting is important to group duplicates
        List<List<Integer>> ansList = new ArrayList<>();
        findSubsets(0, nums, new ArrayList<>(), ansList);
        return ansList;
    }

    /*
     * Time Complexity (Optimized):
     * - O(2^n)
     *   - Each element has two choices (pick / not pick).
     *   - Sorting once costs O(n log n).
     *   - Skipping duplicates avoids redundant recursion calls.
     *
     * Space Complexity:
     * - O(2^n * n) for storing subsets.
     * - O(n) recursion depth (stack).
     */


    // ---------------- Main ----------------
    public static void main(String args[]) {
        int nums[] = {1, 2, 2};

        // Brute force with HashSet
        List<String> ans = subsetsWithDup(nums);
        System.out.println(ans);

        // Optimized Backtracking
        List<List<Integer>> ans1 = subsetsWithDup2(nums);
        System.out.println(ans1);
    }
}
