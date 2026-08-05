package strivers.recursionbacktracking.medium.subsequences;

/**
 * ============================================================================
 * 🤖 Java-Centric DSA Prompt Template: Senior Interviewer & Evaluator
 * ============================================================================
 *
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement: Print All Subsequences of an Array
 *
 * Given an integer array, generate and print all of its subsequences. A
 * subsequence is a sequence that can be derived from the array by deleting
 * some or no elements without changing the order of the remaining elements.
 *
 * 🚨 CODE VALIDATION & EVALUATION:
 * Your provided code used the classic "Pick / Don't Pick" backtracking logic.
 * It is algorithmically optimal! However, there is a CRITICAL BUG in your
 * backtracking step:
 *
 * Your code: list.remove(Integer.valueOf(arr[index]));
 * Bug: This removes the *first occurrence* of the value in the list, not
 * necessarily the element you just added. If your array has duplicates
 * (e.g., arr = {2, 1, 2}), this will remove the wrong '2' and corrupt the
 * subsequence generation.
 * Fix: Always remove the last element added using index:
 * list.remove(list.size() - 1);
 *
 * Constraints:
 * - 1 <= arr.length <= 20 (Beyond 20, 2^N subsets cause Time Limit Exceeded)
 * - -100 <= arr[i] <= 100
 *
 * Input/Output Formats:
 * - Input: An array of integers.
 * - Output: Print all subsets/subsequences. (In the optimal solution below,
 *   we collect them into a List<List<Integer>> for clean testing).
 *
 * Examples:
 *
 * Example 1:
 * Input: arr = [3, 1, 2]
 * Output: [[3, 1, 2], [3, 1], [3, 2], [3], [1, 2], [1], [2], []]
 *
 * Example 2:
 * Input: arr = [0]
 * Output: [[0], []]
 *
 * ============================================================================
 * 2.2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrintAllSubsequencesMasterclass {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Recursive Backtracking: Pick / Don't Pick)
     * ============================================================================
     *
     * Detailed Intuition:
     * For every element in the array, we have exactly two choices:
     * 1. Pick it: Add it to our current list and move to the next index.
     * 2. Don't Pick it: Do not add it, and move to the next index.
     *
     * We use a single `ArrayList` to keep track of our current path. After the
     * "Pick" recursive call finishes, we backtrack by removing the element we just
     * added. This ensures the list is perfectly clean for the "Don't Pick" branch.
     * This is an in-place DFS traversal of the state-space tree.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * 2^N)
     *   There are 2^N possible subsequences. For each base case, we potentially
     *   copy/print a list of up to size N.
     * - Space Complexity: O(N) Auxiliary Stack Space
     *   The maximum depth of the recursion tree is N. The dynamic list also takes
     *   up to O(N) heap space. Output space (if returning the list) is O(N * 2^N).
     */
    public static List<List<Integer>> generateSubsequencesOptimal(int[] arr) {
        List<List<Integer>> result = new ArrayList<>();
        if (arr == null || arr.length == 0) return result;

        backtrack(arr, 0, new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(int[] arr, int index, List<Integer> current, List<List<Integer>> result) {
        // Base Condition: If index reaches the array length, we've made a decision for all elements
        if (index == arr.length) {
            result.add(new ArrayList<>(current)); // Deep copy the current list
            return;
        }

        // Choice 1: Pick the element at the current index
        current.add(arr[index]);
        backtrack(arr, index + 1, current, result);

        // BACKTRACKING STEP: Remove the element we just added.
        // FIXED BUG: Use list.size() - 1 instead of Integer.valueOf(arr[index])
        current.remove(current.size() - 1);

        // Choice 2: Don't pick the element
        backtrack(arr, index + 1, current, result);
    }


    /**
     * ============================================================================
     * PHASE 2: ALTERNATIVE APPROACH (Iterative Bit Manipulation / Power Set)
     * ============================================================================
     *
     * Detailed Intuition:
     * A subsequence essentially maps to a binary number. For an array of size N,
     * there are 2^N subsequences. The numbers from 0 to (2^N - 1) represent all
     * possible combinations.
     * For example, if N = 3 (array [3, 1, 2]):
     * 0 = 000 -> [] (empty)
     * 1 = 001 -> [3] (only 0th bit set)
     * 6 = 110 -> [1, 2] (1st and 2nd bits set)
     *
     * We iterate through all numbers from 0 to 2^N-1, check their set bits, and
     * pick the corresponding array elements.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * 2^N)
     *   The outer loop runs 2^N times. The inner loop checks N bits.
     * - Space Complexity: O(1) Auxiliary Space
     *   We don't use the recursion call stack, making this strictly O(1) extra
     *   space (excluding the space needed to store the output).
     */
    public static List<List<Integer>> generateSubsequencesBitwise(int[] arr) {
        List<List<Integer>> result = new ArrayList<>();
        if (arr == null || arr.length == 0) return result;

        int n = arr.length;
        int totalSubsequences = 1 << n; // 2^n

        for (int i = 0; i < totalSubsequences; i++) {
            List<Integer> currentSubsequence = new ArrayList<>();
            // Check each bit of the number 'i'
            for (int bitIndex = 0; bitIndex < n; bitIndex++) {
                // If the bit at bitIndex is 1, pick the element
                if ((i & (1 << bitIndex)) != 0) {
                    currentSubsequence.add(arr[bitIndex]);
                }
            }
            result.add(currentSubsequence);
        }

        return result;
    }


    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("🤖 Executing Test Suite: Print All Subsequences\n");

        runTest("Test Case 1: Standard Array", new int[]{3, 1, 2});
        runTest("Test Case 2: Array with Duplicates (Tests your bug fix)", new int[]{2, 1, 2});
        runTest("Test Case 3: Single Element", new int[]{5});
        runTest("Test Case 4: Empty Array", new int[]{});
    }

    /**
     * Helper method to initialize, execute, and nicely print results.
     */
    private static void runTest(String testName, int[] arr) {
        System.out.println(">>> " + testName);

        // Print input array
        String inputStr = IntStream.of(arr)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("Input Array: " + inputStr);

        // Phase 1: Optimal Backtracking
        long startOptimal = System.nanoTime();
        List<List<Integer>> resultOptimal = generateSubsequencesOptimal(arr);
        long timeOptimal = System.nanoTime() - startOptimal;

        // Phase 2: Bitwise
        long startBitwise = System.nanoTime();
        List<List<Integer>> resultBitwise = generateSubsequencesBitwise(arr);
        long timeBitwise = System.nanoTime() - startBitwise;

        // Print Results
        System.out.println("Total Subsequences Generated: " + resultOptimal.size());

        if (resultOptimal.size() <= 16) {
            System.out.println("Optimal Output: " + resultOptimal);
            System.out.println("Bitwise Output: " + resultBitwise);
        } else {
            System.out.println("Output hidden (too large to print gracefully).");
        }

        System.out.printf("Optimal Execution Time : %.3f ms\n", timeOptimal / 1_000_000.0);
        System.out.printf("Bitwise Execution Time : %.3f ms\n", timeBitwise / 1_000_000.0);
        System.out.println("--------------------------------------------------\n");
    }
}