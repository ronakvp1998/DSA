package com.questions.strivers.recursion;

import java.util.ArrayList;
import java.util.List;

/*
 Problem Statement: Combination Sum - I
 --------------------------------------
 Given an array of distinct integers (candidates) and a target integer, return a list of all
 unique combinations where the chosen numbers sum to target. You may return the combinations
 in any order.

 - The same number can be chosen an unlimited number of times.
 - Two combinations are unique if the frequency of at least one number differs.
 - It is guaranteed that the number of unique combinations is less than 150 for the given input.

 Example 1:
 Input: array = [2,3,6,7], target = 7
 Output: [[2,2,3],[7]]

 Example 2:
 Input: array = [2], target = 1
 Output: []
 Explanation: No combination can sum to target.
*/

public class CombinationSum1 {

    public static void main(String[] args) {
        int arr[] = {2,3,6,1,2,2,3,7}; // Example input
        int target = 6;

        List<List<Integer>> ans = new ArrayList<>();
        combinationSum(0, arr, ans, new ArrayList<>(), target, 0);
        System.out.println("All Combinations: " + ans);
    }

    /**
     * Function: combinationSum
     * ------------------------
     * Recursively finds all unique combinations that sum to target.
     *
     * Parameters:
     * - index: current index in the array.
     * - arr: input array of integers.
     * - ans: final result list storing all valid combinations.
     * - ds: current combination (temporary list).
     * - target: required target sum.
     * - sum: running sum of the current combination.
     *
     * Approach:
     * - At each index, we have two choices:
     *   1. Pick the element (if sum + arr[index] <= target) → recursive call with the same index.
     *   2. Do not pick the element → recursive call with index + 1.
     * - Base case: when index reaches end of array.
     *   If sum == target → add current combination to result.
     *
     * Time Complexity:
     * - Worst case: O(2^n * k)
     *   - Each element has 2 choices (pick / not pick) → O(2^n).
     *   - Each valid combination can take up to O(k) to copy (k = average length of a combination).
     * - Since input guarantees <150 valid combinations, the recursion tree is pruned effectively.
     *
     * Space Complexity:
     * - O(k) recursion stack depth (where k = target / smallest element).
     * - O(k) extra space for the temporary list `ds`.
     * - Final result storage = O(number_of_combinations * k).
     */
    public static void combinationSum(int index, int[] arr, List<List<Integer>> ans,
                                      List<Integer> ds, int target, int sum) {
        // Base case: reached end of array
        if (index >= arr.length) {
            if (sum == target) {
                ans.add(new ArrayList<>(ds)); // store valid combination
            }
            return;
        }

        // Choice 1: Pick the element (can reuse same index for unlimited frequency)
        if (sum <= target) {
            ds.add(arr[index]);
            combinationSum(index, arr, ans, ds, target, sum + arr[index]);
            ds.remove(ds.size() - 1); // backtrack
        }

        // Choice 2: Do not pick the element → move to next index
        combinationSum(index + 1, arr, ans, ds, target, sum);
    }
}

/*
Summary of Complexities:
------------------------
combinationSum():
   - Time Complexity: O(2^n * k) in worst case
   - Space Complexity: O(k) (recursion depth + temp list),
                       O(number_of_combinations * k) for result storage
*/
