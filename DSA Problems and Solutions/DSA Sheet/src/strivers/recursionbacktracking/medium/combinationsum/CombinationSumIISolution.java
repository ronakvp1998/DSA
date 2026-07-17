package strivers.recursionbacktracking.medium.combinationsum;

/**
 * ============================================================================
 * 40. Combination Sum II
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given a collection of candidate numbers (candidates) and a target number
 * (target), find all unique combinations in candidates where the candidate
 * numbers sum to target.
 *
 * Each number in candidates may only be used once in the combination.
 * Note: The solution set must not contain duplicate combinations.
 *
 * EXAMPLES:
 * Example 1:
 * Input: candidates = [10,1,2,7,6,1,5], target = 8
 * Output:
 * [
 * [1,1,6],
 * [1,2,5],
 * [1,7],
 * [2,6]
 * ]
 *
 * Example 2:
 * Input: candidates = [2,5,2,1,2], target = 5
 * Output:
 * [
 * [1,2,2],
 * [5]
 * ]
 *
 * CONSTRAINTS:
 * - 1 <= candidates.length <= 100
 * - 1 <= candidates[i] <= 50
 * - 1 <= target <= 30
 *
 * ============================================================================
 * CONCEPTUAL VISUALIZATION (Recursion Tree for For-Loop Approach)
 * ============================================================================
 * CRITICAL PRE-STEP: Sort the array so duplicates are adjacent and we can prune.
 * For candidates = [1, 1, 2, 5, 6, 7, 10], target = 8 (Example 1 sorted)
 *
 * State format: (Current Path, Remaining Target)
 *
 *                                      ([], 8)
 *                             /           |           \
 *                 i=0 (1)   /      i=1 (1) SKIP       i=2 (2) ...
 *                         /             (duplicate)      \
 *                   ([1], 7)                            ([2], 6)
 *                  /        \                           /      \
 *           i=1 (1)       i=2 (2)                 i=3 (5)    i=4 (6)
 *             /             \                       /            \
 *        ([1,1], 6)       ([1,2], 5)           ([2,5], 1)      ([2,6], 0) ✅
 *         /      \            \                   /
 *    i=2 (2)    i=4 (6)      i=3 (5)            stop
 *      /            \            \
 * ([1,1,2], 4)   ([1,1,6], 0)✅ ([1,2,5], 0)✅
 *
 * Pruning: If candidates[i] > remaining target, we break the loop immediately
 * because all subsequent elements will also be too large (since array is sorted).
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CombinationSumIISolution {

    /**
     * ============================================================================
     * PHASE 1A: Optimal Approach - For-Loop Based Backtracking
     * ============================================================================
     * Detailed Intuition:
     * This follows the N-ary tree exploration pattern. Because we cannot have
     * duplicate combinations and the array might contain duplicate numbers, we
     * must sort the array first.
     *
     * When looping through candidates to pick the next element, if the current
     * element is the same as the previous element at the SAME level of the tree
     * (i.e., `i > start && candidates[i] == candidates[i - 1]`), we skip it.
     * Additionally, since the array is sorted, if `candidates[i] > target`, we
     * can immediately `break` the loop, pruning massive branches of the tree.
     *
     * Complexity Analysis:
     * - Time Complexity: O(2^N) in the absolute worst case, but practically much
     *   smaller (O(K) where K is valid combinations) due to aggressive pruning.
     * - Space Complexity: O(N) auxiliary stack space + O(K * length) heap space.
     *   The depth of the recursion tree is at most N.
     * ============================================================================
     */
    public List<List<Integer>> combinationSum2ForLoop(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<>();
        solveForLoop(0, candidates, target, new ArrayList<>(), result);
        return result;
    }

    private void solveForLoop(int start, int[] candidates, int target, List<Integer> current, List<List<Integer>> result) {
        // Base case: Target reached
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            // Pruning 1: Skip duplicates at the same recursive level
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }

            // Pruning 2: Since array is sorted, if current exceeds target,
            // all subsequent elements will also exceed it. Break early.
            if (candidates[i] > target) {
                break;
            }

            current.add(candidates[i]); // INCLUDE candidate
            // RECURSE: pass i + 1 because each number is used only once
            solveForLoop(i + 1, candidates, target - candidates[i], current, result);
            current.remove(current.size() - 1); // BACKTRACK
        }
    }

    /**
     * ============================================================================
     * PHASE 1B: Optimal Approach - Normal Recursion (Pick / Don't Pick)
     * ============================================================================
     * Detailed Intuition:
     * This uses the strict binary tree pattern. We sort the array first. At each
     * index, we have a choice: Pick the element, or Don't Pick the element.
     *
     * To avoid duplicate combinations, if we choose the "Don't Pick" branch, we
     * must skip ALL subsequent duplicate occurrences of the current element. If we
     * didn't skip them, we would just pick the duplicate later, resulting in the
     * exact same combination being generated via a different path.
     *
     * Complexity Analysis:
     * - Time Complexity: O(2^N) worst case.
     * - Space Complexity: O(N) auxiliary stack space + O(K * length) heap space.
     * ============================================================================
     */
    public List<List<Integer>> combinationSum2PickDontPick(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<>();
        solvePickDontPick(0, candidates, target, new ArrayList<>(), result);
        return result;
    }

    private void solvePickDontPick(int index, int[] candidates, int target, List<Integer> current, List<List<Integer>> result) {
        // Base case: Target reached
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        // Base case: Out of bounds or target exceeded
        // (Since sorted, if target < 0, we can stop immediately)
        if (target < 0 || index == candidates.length) {
            return;
        }

        // CHOICE 1: Pick the current element (if it doesn't exceed target)
        if (candidates[index] <= target) {
            current.add(candidates[index]);
            solvePickDontPick(index + 1, candidates, target - candidates[index], current, result);
            current.remove(current.size() - 1); // Backtrack
        }

        // CHOICE 2: Don't pick the current element
        // CRITICAL STEP: Skip all adjacent duplicates to avoid duplicate combinations
        while (index + 1 < candidates.length && candidates[index] == candidates[index + 1]) {
            index++;
        }

        solvePickDontPick(index + 1, candidates, target, current, result);
    }

    /**
     * ============================================================================
     * TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        CombinationSumIISolution solution = new CombinationSumIISolution();

        // Object array to hold different types (int[] and int)
        Object[][] testCases = {
                {new int[]{10, 1, 2, 7, 6, 1, 5}, 8}, // Standard Example 1
                {new int[]{2, 5, 2, 1, 2}, 5},        // Standard Example 2
                {new int[]{1, 1, 1, 1}, 2},           // Edge Case: All duplicates
                {new int[]{2}, 1}                     // Edge Case: Impossible target
        };

        System.out.println("====== COMBINATION SUM II DSA EVALUATION ======\n");

        for (int i = 0; i < testCases.length; i++) {
            int[] candidates = (int[]) testCases[i][0];
            int target = (int) testCases[i][1];

            System.out.println("Test Case " + (i + 1) + ": Candidates = " +
                    Arrays.toString(candidates) + ", Target = " + target);

            // Test Phase 1A: For Loop Backtracking
            List<List<Integer>> resForLoop = solution.combinationSum2ForLoop(candidates, target);
            System.out.println("  [For-Loop Backtrack] : " + formatResult(resForLoop));

            // Test Phase 1B: Pick / Don't Pick Recursion
            List<List<Integer>> resPickDontPick = solution.combinationSum2PickDontPick(candidates, target);
            System.out.println("  [Pick/Don't Pick]    : " + formatResult(resPickDontPick));

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