package strivers.recursionbacktracking.medium.subsetproblems;

/**
 * ============================================================================
 * Subset Sums
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an array, print all the sums of the subsets generated from it, in
 * increasing order.
 *
 * EXAMPLES:
 * Example 1:
 * Input: N = 3, arr[] = {5,2,1}
 * Output: 0,1,2,3,5,6,7,8
 *
 * Example 2:
 * Input: N = 3, arr[] = {3,1,2}
 * Output: 0,1,2,3,3,4,5,6
 *
 * CONSTRAINTS:
 * - 1 <= N <= 15 (Standard constraint for 2^N subset generation)
 * - 0 <= arr[i] <= 10^4
 *
 * ============================================================================
 * CONCEPTUAL VISUALIZATION (Recursion Tree for Pick/Don't Pick)
 * ============================================================================
 * For arr = {3, 1, 2}, generating subset sums:
 * (State format: [index, currentSum])
 *
 *                                 [0, 0] (Start)
 *                                /      \
 *                      (exclude 3)      (include 3)
 *                             /            \
 *                        [1, 0]           [1, 3]
 *                        /    \           /    \
 *                 (ex 1)     (in 1) (ex 1)     (in 1)
 *                  /            \      /          \
 *              [2, 0]        [2, 1] [2, 3]      [2, 4]
 *              /    \        /    \   /   \      /   \
 *         [3,0] [3,2]    [3,1] [3,3][3,3] [3,5] [3,4] [3,6]
 *
 * Final generated sums at leaf nodes: {0, 2, 1, 3, 3, 5, 4, 6}
 * Sorted Output: {0, 1, 2, 3, 3, 4, 5, 6}
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SubsetSumsSolution {

    /**
     * ============================================================================
     * PHASE 1A: Optimal Approach - Normal Recursion (Pick / Don't Pick)
     * ============================================================================
     * Detailed Intuition:
     * This follows the strict binary decision tree pattern. At each index of the
     * array, we have exactly two choices: either include the current element's
     * value in our running sum, or skip it. We traverse the entire array making
     * this Yes/No choice. When we reach the end of the array (base case), we
     * have a completed subset sum which we add to our list. Finally, we sort.
     *
     * Complexity Analysis:
     * - Time Complexity: O(2^N + 2^N * log(2^N))
     *   Generating all sums takes 2^N operations. Sorting the resulting list of
     *   size 2^N takes O(2^N * log(2^N)).
     * - Space Complexity: O(N) auxiliary stack space + O(2^N) heap space
     *   The maximum recursion depth is N (auxiliary stack). We must store exactly
     *   2^N subset sums in the result list (heap).
     * ============================================================================
     */
    public List<Integer> subsetSumsPickDontPick(int[] arr) {
        List<Integer> result = new ArrayList<>();
        solvePickDontPick(0, 0, arr, result);
        // Sort using Java 8 Streams/Collections
        Collections.sort(result);
        return result;
    }

    private void solvePickDontPick(int index, int currentSum, int[] arr, List<Integer> result) {
        // Base condition: We have made a decision for every element
        if (index == arr.length) {
            result.add(currentSum);
            return;
        }

        // Choice 1: Include the current element
        solvePickDontPick(index + 1, currentSum + arr[index], arr, result);

        // Choice 2: Exclude the current element
        solvePickDontPick(index + 1, currentSum, arr, result);
    }

    /**
     * ============================================================================
     * PHASE 1B: Alternative Recursion - Loop-based Backtracking
     * ============================================================================
     * Detailed Intuition:
     * This follows the N-ary tree exploration pattern. Instead of strict Yes/No
     * decisions at each index, we consider every node in the recursion tree as a
     * valid state (subset sum). We add the current sum to our result immediately.
     * Then, we use a loop to "choose" the next element to add from the remaining
     * candidates. This perfectly matches the pattern for generating power sets.
     *
     * Complexity Analysis:
     * - Time Complexity: O(2^N + 2^N * log(2^N))
     *   The loop traverses every possible subset configuration exactly once.
     * - Space Complexity: O(N) auxiliary stack space + O(2^N) heap space
     * ============================================================================
     */
    public List<Integer> subsetSumsForLoop(int[] arr) {
        List<Integer> result = new ArrayList<>();
        solveForLoop(0, 0, arr, result);
        Collections.sort(result);
        return result;
    }

    private void solveForLoop(int start, int currentSum, int[] arr, List<Integer> result) {
        // Capture the sum at the current node. Every state is a valid subset sum.
        result.add(currentSum);

        // Loop through the remaining pool of candidates to branch out
        for (int i = start; i < arr.length; i++) {
            // We pass currentSum + arr[i] recursively.
            // We don't need explicit backtrack (e.g., currentSum -= arr[i]) here
            // because integers are passed by value in Java.
            solveForLoop(i + 1, currentSum + arr[i], arr, result);
        }
    }

    /**
     * ============================================================================
     * PHASE 3: Alternative Approach - Iterative Bitmask
     * ============================================================================
     * Detailed Intuition:
     * Since an array of length N has exactly 2^N subsets, we can map every subset
     * to a binary integer from 0 to (2^N - 1). The j-th bit of the number indicates
     * whether the j-th element of the array is included in the current subset sum.
     * We calculate the sum for each configuration and sort the results.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * 2^N + 2^N * log(2^N))
     *   We iterate 2^N times. For each iteration, we check N bits.
     * - Space Complexity: O(1) auxiliary space + O(2^N) heap space
     *   No recursion stack is used, strictly O(1) extra space outside the output array.
     * ============================================================================
     */
    public List<Integer> subsetSumsBitmask(int[] arr) {
        List<Integer> result = new ArrayList<>();
        int n = arr.length;
        int totalSubsets = 1 << n; // 2^N

        for (int i = 0; i < totalSubsets; i++) {
            int currentSum = 0;
            for (int j = 0; j < n; j++) {
                // If the j-th bit is set, include arr[j] in the sum
                if ((i & (1 << j)) != 0) {
                    currentSum += arr[j];
                }
            }
            result.add(currentSum);
        }

        Collections.sort(result);
        return result;
    }

    /**
     * ============================================================================
     * TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        SubsetSumsSolution solution = new SubsetSumsSolution();

        int[][] testCases = {
                {5, 2, 1}, // Standard Example 1
                {3, 1, 2}, // Standard Example 2
                {10},      // Edge case: Single element
                {},        // Edge case: Empty array
                {0, 0, 0}  // Edge case: Zero-values (Duplicates)
        };

        System.out.println("====== SUBSET SUMS DSA EVALUATION ======\n");

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("Test Case " + (i + 1) + ": " +
                    java.util.Arrays.toString(testCases[i]));

            // Phase 1A: Normal Recursion (Pick / Don't Pick)
            List<Integer> resPickDontPick = solution.subsetSumsPickDontPick(testCases[i]);
            System.out.println("  [Pick / Don't Pick] : " + formatResult(resPickDontPick));

            // Phase 1B: For Loop Recursion
            List<Integer> resForLoop = solution.subsetSumsForLoop(testCases[i]);
            System.out.println("  [For Loop Recursion]: " + formatResult(resForLoop));

            // Phase 3: Bitmask
            List<Integer> resBitmask = solution.subsetSumsBitmask(testCases[i]);
            System.out.println("  [Bitmask Iterative] : " + formatResult(resBitmask));
            System.out.println("--------------------------------------------------");
        }
    }

    // Utility to format list using Java 8 Streams for clean console output
    private static String formatResult(List<Integer> res) {
        return res.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }
}