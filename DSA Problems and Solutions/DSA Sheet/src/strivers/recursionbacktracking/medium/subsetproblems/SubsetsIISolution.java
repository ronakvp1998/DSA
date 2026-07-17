package strivers.recursionbacktracking.medium.subsetproblems;

/**
 * ============================================================================
 * 90. Subsets II
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an integer array nums that may contain duplicates, return all possible
 * subsets (the power set).
 *
 * The solution set must not contain duplicate subsets. Return the solution
 * in any order.
 *
 * EXAMPLES:
 * Example 1:
 * Input: nums = [1,2,2]
 * Output: [[],[1],[1,2],[1,2,2],[2],[2,2]]
 *
 * Example 2:
 * Input: nums = [0]
 * Output: [[],[0]]
 *
 * CONSTRAINTS:
 * - 1 <= nums.length <= 10
 * - -10 <= nums[i] <= 10
 *
 * ============================================================================
 * CONCEPTUAL VISUALIZATION (Recursion Tree for For-Loop Approach)
 * ============================================================================
 * CRITICAL PRE-STEP: We MUST sort the array first so duplicates are adjacent.
 * For nums = [1, 2a, 2b] (where 2a and 2b have the same value, 2)
 *
 *                                 []
 *                        /        |         \
 *                      /          |           \
 *                 i=0(1)       i=1(2a)       i=2(2b) -> SKIPPED!
 *                 /    \         |  \           (i > start & nums[i]==nums[i-1])
 *               /        \       |    \
 *          i=1(2a)  i=2(2b)  i=2(2b)  (stop)
 *           /   \      |        |
 *      i=2(2b) (stop)(stop)   (stop)
 *        /
 *     (stop)
 *
 * Subsets collected at every node:
 * [], [1], [1, 2a], [1, 2a, 2b], [1, 2b](skipped), [2a], [2a, 2b]
 * Final Output: [[], [1], [1,2], [1,2,2], [2], [2,2]]
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SubsetsIISolution {

    /**
     * ============================================================================
     * PHASE 1A: Optimal Approach - For-Loop Based Backtracking (Explore Candidates)
     * ============================================================================
     * Detailed Intuition:
     * This uses an N-ary decision tree pattern. Because the array contains duplicates,
     * we first SORT the array. Sorting guarantees that duplicate elements sit next
     * to each other.
     *
     * In our for-loop, at any given tree level (represented by the `start` index),
     * if we see an element that is exactly the same as the previous element we just
     * processed AT THE SAME LEVEL (`i > start && nums[i] == nums[i - 1]`), we skip it.
     * This prevents creating identical sub-trees and completely eliminates duplicate subsets.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * 2^N)
     *   Sorting takes O(N log N). In the worst case (all unique), there are 2^N subsets,
     *   and we spend O(N) time copying each into the result list.
     * - Space Complexity: O(N) auxiliary stack space + O(N * 2^N) heap space
     *   The recursion tree goes at most N levels deep. The heap space stores up
     *   to 2^N subsets, each of size up to N.
     * ============================================================================
     */
    public List<List<Integer>> subsetsWithDupForLoop(int[] nums) {
        // Sort to bring duplicates together
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        solveForLoop(0, nums, new ArrayList<>(), result);
        return result;
    }

    private void solveForLoop(int start, int[] nums, List<Integer> current, List<List<Integer>> result) {
        // Every node is a valid subset, add a copy immediately
        result.add(new ArrayList<>(current));

        for (int i = start; i < nums.length; i++) {
            // Pruning condition: Skip duplicates at the SAME tree level
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }

            current.add(nums[i]);                  // INCLUDE candidate
            solveForLoop(i + 1, nums, current, result); // RECURSE for next elements
            current.remove(current.size() - 1);    // BACKTRACK
        }
    }

    /**
     * ============================================================================
     * PHASE 1B: Optimal Approach - Normal Recursion (Pick / Don't Pick)
     * ============================================================================
     * Detailed Intuition:
     * This uses the strict binary tree pattern. At each index, we decide to either
     * pick the element or not pick it.
     *
     * To handle duplicates: If we decide NOT to pick `nums[index]`, we must skip
     * ALL subsequent occurrences of `nums[index]`. Why? Because if we skip the
     * first '2' but pick the second '2', we generate the same subset as if we had
     * picked the first '2' and skipped the second '2'.
     * So, on the "Don't Pick" branch, we advance the index past all duplicates.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * 2^N)
     *   Even with skipping branches, the worst-case scenario (all unique elements)
     *   still requires generating 2^N subsets.
     * - Space Complexity: O(N) auxiliary stack space + O(N * 2^N) heap space
     *   The recursion depth is bounded by N.
     * ============================================================================
     */
    public List<List<Integer>> subsetsWithDupPickDontPick(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        solvePickDontPick(0, nums, new ArrayList<>(), result);
        return result;
    }

    private void solvePickDontPick(int index, int[] nums, List<Integer> current, List<List<Integer>> result) {
        // Base case: processed all elements
        if (index == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }

        // Choice 1: PICK the current element
        current.add(nums[index]);
        solvePickDontPick(index + 1, nums, current, result);
        current.remove(current.size() - 1); // Backtrack

        // Choice 2: DON'T PICK the current element
        // CRITICAL STEP: Skip all adjacent duplicates to avoid duplicate subsets
        while (index + 1 < nums.length && nums[index] == nums[index + 1]) {
            index++;
        }

        // Recurse on the next different element
        solvePickDontPick(index + 1, nums, current, result);
    }

    /**
     * ============================================================================
     * PHASE 3: Alternative Approach - Iterative (Cascading)
     * ============================================================================
     * Detailed Intuition:
     * We can build subsets iteratively. Start with an empty subset `[[]]`.
     * For each number in the sorted array, we add it to the existing subsets.
     *
     * To handle duplicates: If the current number is the same as the previous one,
     * we do NOT append it to all existing subsets. Instead, we ONLY append it to
     * the new subsets that were generated in the immediately previous step.
     * We track the start and end sizes of the result list to manage this.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * 2^N)
     * - Space Complexity: O(1) auxiliary space + O(N * 2^N) heap space
     *   No recursion stack is used. Memory is purely for the output.
     * ============================================================================
     */
    public List<List<Integer>> subsetsWithDupIterative(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());

        int startIndex = 0;
        int endIndex = 0;

        for (int i = 0; i < nums.length; i++) {
            startIndex = 0;
            // If it's a duplicate, only append to subsets created in the previous step
            if (i > 0 && nums[i] == nums[i - 1]) {
                startIndex = endIndex;
            }

            endIndex = result.size();
            for (int j = startIndex; j < endIndex; j++) {
                List<Integer> newSubset = new ArrayList<>(result.get(j));
                newSubset.add(nums[i]);
                result.add(newSubset);
            }
        }
        return result;
    }

    /**
     * ============================================================================
     * TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        SubsetsIISolution solution = new SubsetsIISolution();

        int[][] testCases = {
                {1, 2, 2},          // Standard Example 1
                {0},                // Standard Example 2
                {4, 4, 4, 1, 4},    // Out of order with multiple duplicates
                {}                  // Edge case: empty array
        };

        System.out.println("====== SUBSETS II DSA EVALUATION ======\n");

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("Test Case " + (i + 1) + ": " +
                    Arrays.toString(testCases[i]));

            // Test Phase 1A: For Loop Backtracking
            List<List<Integer>> resForLoop = solution.subsetsWithDupForLoop(testCases[i]);
            System.out.println("  [For-Loop Visited]  : " + formatResult(resForLoop));

            // Test Phase 1B: Pick / Don't Pick Recursion
            List<List<Integer>> resPickDontPick = solution.subsetsWithDupPickDontPick(testCases[i]);
            System.out.println("  [Pick/Don't Pick]   : " + formatResult(resPickDontPick));

            // Test Phase 3: Iterative Cascading
            List<List<Integer>> resIterative = solution.subsetsWithDupIterative(testCases[i]);
            System.out.println("  [Iterative Cascade] : " + formatResult(resIterative));

            System.out.println("--------------------------------------------------");
        }
    }

    // Utility to format List<List<Integer>> cleanly for the console using Java 8 Streams
    private static String formatResult(List<List<Integer>> res) {
        if (res.isEmpty()) return "[]";
        return "[" + res.stream()
                .map(list -> "[" + list.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) + "]")
                .collect(Collectors.joining(",")) + "]";
    }
}