package com.questions.strivers.recursionbacktracking.medium.subsequences;

import java.util.ArrayList;
import java.util.List;

public class CombinationSumIII {

    /*
     * Problem: Find all unique combinations of k numbers (from 1-9) that sum to n.
     *
     * Approach: Backtracking
     * -----------------------------------
     * - Use recursion to try each number from 'start' to 9.
     * - At each step:
     *      1. Add current number to the combination.
     *      2. Recurse with updated sum and next number.
     *      3. Backtrack (remove last number).
     * - Base cases:
     *      - If combination size == k and sum == n → valid, add to result.
     *      - If sum > n or size > k → stop exploring this path (pruning).
     */

    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), k, n, 1);
        return result;
    }

    // Recursive helper function
    private void backtrack(List<List<Integer>> result, List<Integer> current,
                           int k, int target, int start) {
        // If target reached and size == k → valid combination
        if (target == 0 && current.size() == k) {
            result.add(new ArrayList<>(current)); // Add copy of current list
            return;
        }

        // If sum exceeded or size exceeded → invalid path
        if (target < 0 || current.size() > k) {
            return;
        }

        // Try all numbers from 'start' to 9
        for (int num = start; num <= 9; num++) {
            // Choose
            current.add(num);

            // Explore with updated target and next number
            backtrack(result, current, k, target - num, num + 1);

            // Undo choice (backtrack)
            current.remove(current.size() - 1);
        }
    }

    // Driver code
    public static void main(String[] args) {
        CombinationSumIII solution = new CombinationSumIII();

        System.out.println(solution.combinationSum3(3, 7));  // [[1,2,4]]
        System.out.println(solution.combinationSum3(3, 9));  // [[1,2,6],[1,3,5],[2,3,4]]
        System.out.println(solution.combinationSum3(4, 1));  // []
    }
}
/*
Time Complexity:
Worst case: try all subsets of numbers 1–9.
That’s 2^9 = 512 subsets.
But since we restrict size to k, the actual complexity is closer to O(C(9, k)).
Each recursion explores at most 9 levels → O(2^9) ≈ O(1) (constant upper bound).

Space Complexity:
Recursion depth = at most 9 (since numbers are from 1–9).
Each recursive call holds a current list up to size k.
O(k) auxiliary space + result storage.
 */