package strivers.recursionbacktracking.medium.subsequences;

/**
 * ============================================================================
 * 🤖 Java-Centric DSA Prompt Template: Senior Interviewer & Evaluator
 * ============================================================================
 *
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement: Print All Subsequences with Target Sum K
 *
 * Given an array of integers and a target sum K, generate and print all
 * possible subsequences whose elements sum up exactly to K.
 *
 * 🚨 EVALUATION OF PROVIDED CODE:
 * Your provided code uses a standard recursive "Pick / Don't Pick" backtracking
 * logic. Conceptually, this is the correct algorithmic approach for generating
 * all combinations.
 *
 * However, there is a CRITICAL BUG in your backtracking step:
 * Your code: `list.remove(Integer.valueOf(arr[index]));`
 * Bug: The `remove(Object)` method removes the *first occurrence* of that value
 * in the list. If your array has duplicates (e.g., `arr = {1, 2, 1}`), and your
 * current list is `[1, 2, 1]`, backtracking the last `1` will accidentally remove
 * the *first* `1`. The list becomes `[2, 1]` instead of `[1, 2]`, corrupting
 * the entire subsequence generation.
 * Fix: Always remove by index for backtracking: `list.remove(list.size() - 1);`
 *
 * Constraints:
 * - 1 <= arr.length <= 20 (Beyond 20, 2^n subsets cause Time Limit Exceeded)
 * - -100 <= arr[i] <= 100
 * - -1000 <= K <= 1000
 *
 * Input/Output Formats:
 * - Input: An integer array `arr` and an integer `K`.
 * - Output: A list of all valid subsequences (List<List<Integer>>).
 *
 * Examples:
 *
 * Example 1:
 * Input: arr = [1, 2, 1], K = 2
 * Output: [[1, 1], [2]]
 *
 * Example 2:
 * Input: arr = [3, 2, 1, 1], K = 3
 * Output: [[3], [2, 1] (first 1), [2, 1] (second 1)]
 *
 * ============================================================================
 * 2.2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 *
 * Note: Generating ALL valid paths inherently requires traversing them.
 * Thus, Dynamic Programming is NOT optimal for *printing* all paths (it is
 * used for *counting* them). Backtracking is the mathematically optimal
 * approach for generation.
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

public class PrintSubseqSumKMasterclass {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Corrected Backtracking)
     * ============================================================================
     *
     * Detailed Intuition:
     * We use a Depth First Search (DFS) state-space tree. At each element, we
     * branch into two paths:
     * 1. Pick the element: Add to our current list, subtract its value from target.
     * 2. Skip the element: Don't add to list, target remains the same.
     *
     * We fix the backtracking bug by using `list.remove(list.size() - 1)`.
     * Additionally, instead of keeping a running sum and comparing it to the target,
     * we subtract from the target as we go down. When target == 0 at the base case,
     * we found a valid subsequence.
     *
     * Complexity Analysis:
     * - Time Complexity: O(2^n * n)
     *   There are 2^n possible subsequences. If a subsequence is valid, deep copying
     *   it to the result list takes O(n) time.
     * - Space Complexity: O(n) Auxiliary Stack Space
     *   The call stack reaches a maximum depth of `n`. The temporary list also takes
     *   O(n) heap space. (Excluding the O(2^n * n) space used to store the output).
     */
    public static List<List<Integer>> generateOptimal(int[] arr, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (arr == null || arr.length == 0) return result;

        backtrackOptimal(arr, 0, target, new ArrayList<>(), result);
        return result;
    }

    private static void backtrackOptimal(int[] arr, int index, int target,
                                         List<Integer> current, List<List<Integer>> result) {
        // Base Condition
        if (index == arr.length) {
            if (target == 0) {
                result.add(new ArrayList<>(current)); // O(n) deep copy
            }
            return;
        }

        // Choice 1: Pick
        current.add(arr[index]);
        backtrackOptimal(arr, index + 1, target - arr[index], current, result);

        // BACKTRACK: Properly remove the last added element (Fixes the bug)
        current.remove(current.size() - 1);

        // Choice 2: Don't Pick
        backtrackOptimal(arr, index + 1, target, current, result);
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Your Original Provided Code - Adapted)
     * ============================================================================
     *
     * Detailed Intuition:
     * This faithfully reproduces your original approach of carrying `res` forward
     * and adding/subtracting from it. It still contains the semantic logic you
     * provided, but I am collecting the results into a list rather than printing
     * them directly so our testing suite can validate it.
     *
     * 🚨 Notice the bug when testing this with duplicates (Test Case 1)!
     *
     * Complexity Analysis:
     * - Time Complexity: O(2^n * n)
     * - Space Complexity: O(n) Aux stack space.
     */
    public static List<List<Integer>> generateBruteForceBuggy(int[] arr, int sum) {
        List<List<Integer>> result = new ArrayList<>();
        printSubseqSumK(arr, sum, 0, new ArrayList<>(), 0, result);
        return result;
    }

    // Your parametrized approach (adapted to store in 'result' instead of sysout)
    private static void printSubseqSumK(int arr[], int sum, int res,
                                        List<Integer> list, int index,
                                        List<List<Integer>> result) {
        if(index == arr.length){
            if(res == sum){
                result.add(new ArrayList<>(list));
            }
            return;
        }

        list.add(arr[index]);
        res = res + arr[index];
        printSubseqSumK(arr, sum, res, list, index + 1, result);

        // BUG LIVES HERE: Removes the first occurrence of arr[index], not the last!
        list.remove(Integer.valueOf(arr[index]));
        res = res - arr[index];

        printSubseqSumK(arr, sum, res, list, index + 1, result);
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Iterative Bitmasking / Power Set)
     * ============================================================================
     *
     * Detailed Intuition:
     * For an array of length N, there are 2^N subsequences. We can represent each
     * subsequence as a binary number from 0 to (2^N - 1). If the i-th bit is 1,
     * we include arr[i] in the subsequence. We compute the sum for each combination
     * and store it if it matches the target.
     *
     * Complexity Analysis:
     * - Time Complexity: O(n * 2^n)
     *   Outer loop runs 2^n times, inner loop runs n times to check bits.
     * - Space Complexity: O(1) Auxiliary Space
     *   No recursion stack is used. Memory is purely for the output list.
     */
    public static List<List<Integer>> generateBitwise(int[] arr, int sum) {
        List<List<Integer>> result = new ArrayList<>();
        if (arr == null || arr.length == 0) return result;

        int n = arr.length;
        int totalCombinations = 1 << n; // 2^n

        for (int i = 0; i < totalCombinations; i++) {
            List<Integer> currentSubsequence = new ArrayList<>();
            int currentSum = 0;

            for (int bit = 0; bit < n; bit++) {
                if ((i & (1 << bit)) != 0) { // If bit is set
                    currentSubsequence.add(arr[bit]);
                    currentSum += arr[bit];
                }
            }

            if (currentSum == sum) {
                result.add(currentSubsequence);
            }
        }

        return result;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("🤖 Executing Test Suite: Print Subsequences Summing to K\n");

        // Notice how Phase 2 (Buggy) fails on the duplicates!
        runTest("Test Case 1 (Standard with Duplicates)", new int[]{1, 2, 1}, 2);

        runTest("Test Case 2 (No duplicates, Buggy code works fine here)", new int[]{3, 2, 1}, 3);

        runTest("Test Case 3 (Zero elements and negative targets)", new int[]{0, -1, 3, 2}, 2);
    }

    /**
     * Helper method to initialize, run, and elegantly format test results.
     */
    private static void runTest(String testName, int[] arr, int target) {
        System.out.println(">>> " + testName);
        System.out.println("Input Array: " + Arrays.toString(arr) + " | Target K: " + target);

        List<List<Integer>> optimalResult = generateOptimal(arr, target);
        List<List<Integer>> buggyResult = generateBruteForceBuggy(arr, target);
        List<List<Integer>> bitwiseResult = generateBitwise(arr, target);

        // Format output using Streams
        System.out.println("Phase 1 (Optimal Backtrack): " + formatResult(optimalResult));
        System.out.println("Phase 2 (Your Provided Code) : " + formatResult(buggyResult) + " <-- Notice missing or incorrect lists if duplicates exist!");
        System.out.println("Phase 3 (Bitmask Alternative): " + formatResult(bitwiseResult));
        System.out.println("--------------------------------------------------\n");
    }

    private static String formatResult(List<List<Integer>> result) {
        if (result.isEmpty()) return "[]";
        return result.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ", "[", "]"));
    }
}