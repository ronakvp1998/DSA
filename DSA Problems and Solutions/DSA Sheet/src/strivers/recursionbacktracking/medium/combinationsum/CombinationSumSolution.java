package strivers.recursionbacktracking.medium.combinationsum;

/**
 * ============================================================================
 * 39. Combination Sum
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an array of distinct integers candidates and a target integer target,
 * return a list of all unique combinations of candidates where the chosen
 * numbers sum to target. You may return the combinations in any order.
 *
 * The same number may be chosen from candidates an unlimited number of times.
 * Two combinations are unique if the frequency of at least one of the chosen
 * numbers is different.
 *
 * The test cases are generated such that the number of unique combinations
 * that sum up to target is less than 150 combinations for the given input.
 *
 * EXAMPLES:
 * Example 1:
 * Input: candidates = [2,3,6,7], target = 7
 * Output: [[2,2,3],[7]]
 * Explanation:
 * 2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times.
 * 7 is a candidate, and 7 = 7.
 * These are the only two combinations.
 *
 * Example 2:
 * Input: candidates = [2,3,5], target = 8
 * Output: [[2,2,2,2],[2,3,3],[3,5]]
 *
 * Example 3:
 * Input: candidates = [2], target = 1
 * Output: []
 *
 * CONSTRAINTS:
 * - 1 <= candidates.length <= 30
 * - 2 <= candidates[i] <= 40
 * - All elements of candidates are distinct.
 * - 1 <= target <= 40
 *
 * ============================================================================
 * CONCEPTUAL VISUALIZATION (Recursion Tree for For-Loop Approach)
 * ============================================================================
 * For candidates = [2, 3, 6, 7], target = 7
 * State format: (Current Path, Remaining Target)
 *
 *                                 ([], 7)
 *                               /    |    \    \
 *                 i=0 (+2)    /   i=1 (+3)  \   i=3 (+7)
 *                           /        |       \        \
 *                      ([2], 5)   ([3], 4)  ([6], 1)  ([7], 0) ✅
 *                     /   |        /    \       x (stop)
 *              i=0 (+2)  i=1 (+3) /      \
 *                 /       |      /        \
 *            ([2,2], 3) ([2,3],2) ([3,3],1) ([3,6], -2)
 *             /    |       |         x         x
 *      i=0 (+2) i=1 (+3)  x (stop)
 *           /      |
 *   ([2,2,2],1)([2,2,3],0) ✅
 *        x
 *
 * Note: When iterating in the loop, we only pick items from index `i` onwards
 * to avoid duplicate combinations like [2,3,2] and [2,2,3].
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CombinationSumSolution {

    /**
     * ============================================================================
     * PHASE 1A: Optimal Approach - Normal Recursion (Pick / Don't Pick)
     * ============================================================================
     * Detailed Intuition:
     * This uses a strict binary decision tree. At any index `i`, we decide whether
     * to "pick" the candidate or "don't pick" it.
     *
     * The twist for this problem: Since we can pick the same element an UNLIMITED
     * number of times, when we make the choice to "pick" `candidates[index]`,
     * we DO NOT increment the index in our recursive call. We only increment the
     * index when we choose to "don't pick" and move on.
     *
     * Complexity Analysis:
     * - Time Complexity: O(2^T) loosely, but strictly O(N ^ (T/M)) where T is
     *   the target and M is the minimum value in the array. This represents the
     *   maximum number of nodes in the recursion tree.
     * - Space Complexity: O(T/M) auxiliary stack space
     *   The longest possible path (deepest recursion) is picking the smallest
     *   element repeatedly (target / min_element) times. Heap space requires
     *   O(K * (T/M)) where K is the number of valid combinations.
     * ============================================================================
     */
    public List<List<Integer>> combinationSumPickDontPick(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        solvePickDontPick(0, candidates, target, new ArrayList<>(), result);
        return result;
    }

    private void solvePickDontPick(int index, int[] candidates, int target, List<Integer> current, List<List<Integer>> result) {
        // Base case 1: Target reached
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        // Base case 2: Exhausted all candidates or exceeded target
        if (target < 0 || index == candidates.length) {
            return;
        }

        // Choice 1: Pick the current element (if it doesn't exceed the target)
        if (candidates[index] <= target) {
            current.add(candidates[index]);
            // RECURSE: Keep the SAME index because we can reuse this element
            solvePickDontPick(index, candidates, target - candidates[index], current, result);
            // BACKTRACK: Undo the choice
            current.remove(current.size() - 1);
        }

        // Choice 2: Don't pick the element, strictly move to the next index
        solvePickDontPick(index + 1, candidates, target, current, result);
    }

    /**
     * ============================================================================
     * PHASE 1B: Optimal Approach - For-Loop Based Backtracking (Explore Candidates)
     * ============================================================================
     * Detailed Intuition:
     * This uses an N-ary decision tree. From any current path sum, what element
     * can we add next? We loop through all available candidates from `start` to
     * the end of the array.
     *
     * To allow reusing the same element, when we recurse, we pass the current
     * loop index `i` as the new `start` (rather than `i + 1`). This ensures we
     * can pick `candidates[i]` again, but we can never pick `candidates[i-1]`,
     * preventing duplicate sets like [2,3] and [3,2].
     *
     * Complexity Analysis:
     * - Time Complexity: O(N ^ (T/M))
     *   Where T is target and M is the minimum element. The bounding logic prunes
     *   branches early, making it highly efficient for standard constraints.
     * - Space Complexity: O(T/M) auxiliary stack space
     *   The depth of recursion is bounded by target / min_element.
     * ============================================================================
     */
    public List<List<Integer>> combinationSumForLoop(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        solveForLoop(0, candidates, target, new ArrayList<>(), result);
        return result;
    }

    private void solveForLoop(int start, int[] candidates, int remain, List<Integer> current, List<List<Integer>> result) {
        // Base case 1: If remaining target drops below zero, this path is dead
        if (remain < 0) {
            return;
        }

        // Base case 2: Perfect sum found
        if (remain == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        // Branch out: Iterate through candidates starting from 'start'
        for (int i = start; i < candidates.length; i++) {
            current.add(candidates[i]); // INCLUDE candidate

            // RECURSE: Note we pass 'i' as start, NOT 'i+1'. This allows reusing the same number.
            solveForLoop(i, candidates, remain - candidates[i], current, result);

            current.remove(current.size() - 1); // BACKTRACK
        }
    }

    /**
     * ============================================================================
     * TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        CombinationSumSolution solution = new CombinationSumSolution();

        // Structure: Object[] {candidates array, target value}
        Object[][] testCases = {
                {new int[]{2, 3, 6, 7}, 7},  // Standard Example 1
                {new int[]{2, 3, 5}, 8},     // Standard Example 2
                {new int[]{2}, 1},           // Standard Example 3 (Impossible)
                {new int[]{3, 4, 5}, 12},    // Larger target with multiple reuses
                {new int[]{8, 7, 4, 3}, 11}  // Unsorted array
        };

        System.out.println("====== COMBINATION SUM DSA EVALUATION ======\n");

        for (int i = 0; i < testCases.length; i++) {
            int[] candidates = (int[]) testCases[i][0];
            int target = (int) testCases[i][1];

            System.out.println("Test Case " + (i + 1) + ": Candidates = " +
                    java.util.Arrays.toString(candidates) + ", Target = " + target);

            // Phase 1A: Normal Recursion (Pick / Don't Pick)
            List<List<Integer>> resPickDontPick = solution.combinationSumPickDontPick(candidates, target);
            System.out.println("  [Pick / Don't Pick] : " + formatResult(resPickDontPick));

            // Phase 1B: For Loop Backtracking
            List<List<Integer>> resForLoop = solution.combinationSumForLoop(candidates, target);
            System.out.println("  [For-Loop Backtrack]: " + formatResult(resForLoop));

            System.out.println("--------------------------------------------------");
        }
    }

    // Utility to format List<List<Integer>> using Java 8 Streams
    private static String formatResult(List<List<Integer>> res) {
        if (res.isEmpty()) return "[]";
        return "[" + res.stream()
                .map(list -> "[" + list.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) + "]")
                .collect(Collectors.joining(",")) + "]";
    }
}