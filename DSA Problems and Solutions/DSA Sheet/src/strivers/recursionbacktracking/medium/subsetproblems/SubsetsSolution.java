package strivers.recursionbacktracking.medium.subsetproblems;

/**
 * ============================================================================
 * 78. Subsets
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an integer array nums of unique elements, return all possible subsets
 * (the power set).
 *
 * The solution set must not contain duplicate subsets. Return the solution
 * in any order.
 *
 * EXAMPLES:
 * Example 1:
 * Input: nums = [1,2,3]
 * Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
 *
 * Example 2:
 * Input: nums = [0]
 * Output: [[],[0]]
 *
 * CONSTRAINTS:
 * - 1 <= nums.length <= 10
 * - -10 <= nums[i] <= 10
 * - All the numbers of nums are unique.
 *
 * ============================================================================
 * CONCEPTUAL VISUALIZATION (Recursion / Backtracking Tree)
 * ============================================================================
 * For nums = [1, 2]
 * The backtracking approach explores a state-space tree where at each node,
 * we either include the next element or exclude it.
 *
 *                          [] (Start)
 *                        /    \
 *               (exclude 1)  (include 1)
 *                   /            \
 *                 []             [1]
 *                /  \           /   \
 *          (ex 2) (in 2)    (ex 2) (in 2)
 *           /        \       /        \
 *         []        [2]    [1]       [1,2]
 *
 * Final subsets found at the nodes of the recursion process.
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SubsetsSolution {
    /**
     * ============================================================================
     * PHASE 1: Optimal Approach - Backtracking (Recursive)
     * ============================================================================
     * Detailed Intuition:
     * This is the quintessential backtracking approach. Because every node in our
     * decision tree represents a valid subset, we immediately take a snapshot of
     * the 'current' list and add it to our results at the start of the method.
     *
     * After capturing the current subset, we check our explicit base case: if our
     * starting index has reached the end of the array, there are no more elements
     * to explore on this path, so we return. Otherwise, we iterate through the
     * remaining elements, add one to our subset, recurse, and then backtrack
     * (remove it) to explore other branches.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * 2^N)
     *   There are 2^N subsets, and for each subset, creating a new ArrayList takes
     *   O(N) time in the worst case (when copying the elements).
     * - Space Complexity: O(N) auxiliary stack space
     *   The recursion depth goes up to N. The heap space for the result list itself
     *   takes O(N * 2^N) space, but auxiliary space (excluding output) is just O(N).
     * ============================================================================
     */
    public List<List<Integer>> subsetsOptimal(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), nums, 0);
        return result;
    }

    private void backtrack(List<List<Integer>> result, List<Integer> current, int[] nums, int start) {
        // 1. CAPTURE: Every step is a valid subset, so add it immediately
        result.add(new ArrayList<>(current));

        // 2. BASE CASE: If we've processed all elements, stop going deeper
        if (start == nums.length) {
            return;
        }

        // 3. EXPLORE: Iterate over the remaining elements to branch out
        for (int i = start; i < nums.length; i++) {
            current.add(nums[i]);                  // INCLUDE the current element
            backtrack(result, current, nums, i + 1); // RECURSE for the next elements
            current.remove(current.size() - 1);    // BACKTRACK to exclude the element
        }
    }

    // Pattern 1: Subsets WITHOUT a for loop
    private void backtrack2(List<List<Integer>> result, List<Integer> current, int[] nums, int index) {
        // Base Case: We have made a Yes/No decision for every single element
        if (index == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }

        // CHOICE 1: Exclude the current element
        backtrack(result, current, nums, index + 1);

        // CHOICE 2: Include the current element
        current.add(nums[index]);
        backtrack(result, current, nums, index + 1);

        // Backtrack to clean up before returning
        current.remove(current.size() - 1);
    }

//    Use normal recursion (no loop) when your decision at each step is strictly binary (e.g., "Do I pick this item or not?").
//    Use a for loop when you have a pool of candidates to choose from at each step, and the number of choices is variable.

    /**
     * ============================================================================
     * PHASE 2: Alternative Approach - Cascading (Iterative)
     * ============================================================================
     * Detailed Intuition:
     * Instead of recursion, we can iteratively build subsets. We start with a
     * list containing only the empty subset. For each number in the input array,
     * we take all existing subsets in our result list, clone them, and append
     * the current number to each clone. Finally, we add these new subsets back
     * into the result list.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * 2^N)
     *   At each step i, the number of subsets doubles. Copying subsets takes O(N)
     *   max time per subset.
     * - Space Complexity: O(1) auxiliary space
     *   No recursion stack is used. Overall space is O(N * 2^N) for the output array.
     * ============================================================================
     */
    public List<List<Integer>> subsetsAlternativeIterative(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>()); // Start with the empty subset

        for (int num : nums) {
            int currentSize = result.size();
            for (int i = 0; i < currentSize; i++) {
                // Copy the existing subset and add the current number
                List<Integer> newSubset = new ArrayList<>(result.get(i));
                newSubset.add(num);
                result.add(newSubset);
            }
        }
        return result;
    }

    /**
     * ============================================================================
     * PHASE 3: Alternative Approach - Bit Manipulation
     * ============================================================================
     * Detailed Intuition:
     * Since an array of length N has exactly 2^N subsets, we can map every subset
     * to a binary number from 0 to (2^N - 1).
     * For example, for nums = [1,2,3], N = 3 (8 subsets, 000 to 111):
     * 000 -> []
     * 001 -> [1]
     * 010 -> [2]
     * 011 -> [1, 2]
     * ...
     * 111 -> [1, 2, 3]
     * The j-th bit determines if nums[j] is included in the current subset.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * 2^N)
     *   Outer loop runs 2^N times, inner loop runs N times.
     * - Space Complexity: O(1) auxiliary space
     *   Ignoring the space required for output.
     * ============================================================================
     */
    public List<List<Integer>> subsetsAlternativeBitmask(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;
        int totalSubsets = 1 << n; // 2^n

        for (int i = 0; i < totalSubsets; i++) {
            List<Integer> subset = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                // Check if the j-th bit is set in the binary representation of i
                if ((i & (1 << j)) != 0) {
                    subset.add(nums[j]);
                }
            }
            result.add(subset);
        }
        return result;
    }

    /**
     * ============================================================================
     * TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        SubsetsSolution solution = new SubsetsSolution();

        int[][] testCases = {
                {1, 2, 3}, // Standard Example 1
                {0},       // Standard Example 2
                {},        // Edge case: Empty array
                {-10, 10}  // Negative values and boundaries
        };

        System.out.println("====== SUBSETS DSA EVALUATION ======\n");

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("Test Case " + (i + 1) + ": " +
                    java.util.Arrays.toString(testCases[i]));

            // Test Optimal (Recursive)
            List<List<Integer>> resOptimal = solution.subsetsOptimal(testCases[i]);
            System.out.println("  [Optimal Recursive]   " + formatResult(resOptimal));

            // Test Iterative
            List<List<Integer>> resIterative = solution.subsetsAlternativeIterative(testCases[i]);
            System.out.println("  [Iterative Cascading] " + formatResult(resIterative));

            // Test Bitmask
            List<List<Integer>> resBitmask = solution.subsetsAlternativeBitmask(testCases[i]);
            System.out.println("  [Bitmask Approach]    " + formatResult(resBitmask));
            System.out.println("--------------------------------------------------");
        }
    }

    // Utility to format list using Java 8 Streams for clean console output
    private static String formatResult(List<List<Integer>> res) {
        return "[" + res.stream()
                .map(list -> "[" + list.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) + "]")
                .collect(Collectors.joining(",")) + "]";
    }
}