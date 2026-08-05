package strivers.recursionbacktracking.medium.binaryproblems;

/**
 * ============================================================================
 * 🤖 Java-Centric DSA Prompt Template: Senior Interviewer & Evaluator
 * ============================================================================
 *
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement: N-bit binary numbers with more 1s than 0s in all prefixes
 *
 * Given an integer N, generate all N-bit binary numbers such that for every
 * prefix of the binary number, the count of '1's is greater than or equal to
 * the count of '0's.
 *
 * 🚨 EVALUATION OF PROVIDED CODE:
 * The provided code uses recursive backtracking with String concatenation
 * (`output + "1"`). While logically 100% correct and mathematically sound,
 * using String concatenation in Java creates a new String object in the Heap
 * at every single recursive step. This leads to heavy Garbage Collection overhead
 * and an O(N * 2^N) memory footprint.
 * We will optimize this using a `StringBuilder` (Phase 1) and a `char[]` (Phase 3)
 * to achieve zero intermediate object creation.
 *
 * Constraints:
 * - 1 <= N <= 20 (Beyond 20, the output size causes memory limits to exceed)
 *
 * Input/Output Formats:
 * - Input: An integer `n`.
 * - Output: A List of Strings containing all valid binary numbers.
 *
 * Examples:
 *
 * Example 1:
 * Input: N = 4
 * Output: ["1111", "1110", "1101", "1100", "1011", "1010"]
 *
 * Example 2:
 * Input: N = 2
 * Output: ["11", "10"]
 *
 * ============================================================================
 * 2.2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.List;

public class NBitBinaryNum1MorePrefix {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Backtracking with StringBuilder)
     * ============================================================================
     *
     * Detailed Intuition:
     * Instead of creating a new String at every step, we pass a single mutable
     * `StringBuilder` down the recursion tree. When we append a character and recurse,
     * we must "undo" that choice (backtrack) by deleting the last character when
     * the recursion returns. This keeps Heap allocations strictly limited to the
     * final valid strings added to the result list.
     *
     * Complexity Analysis:
     * - Time Complexity: O(2^N) upper bound. (More accurately, the number of valid
     *   strings is bounded by Catalan numbers C(N), making it significantly faster
     *   than a full 2^N traversal). Constructing the final string takes O(N). Total
     *   time is O(N * C(N)).
     * - Space Complexity: O(N) Auxiliary Stack Space + O(N) for the StringBuilder.
     *   (Excluding the output list space).
     */
    public static List<String> generateOptimal(int n) {
        List<String> result = new ArrayList<>();
        if (n <= 0) return result;
        backtrackOptimal(n, 0, 0, new StringBuilder(), result);
        return result;
    }

    private static void backtrackOptimal(int n, int ones, int zeros, StringBuilder sb, List<String> result) {
        if (sb.length() == n) {
            result.add(sb.toString());
            return;
        }

        // Choice 1: Add '1'
        sb.append('1');
        backtrackOptimal(n, ones + 1, zeros, sb, result);
        sb.deleteCharAt(sb.length() - 1); // Backtrack

        // Choice 2: Add '0' (Only if doing so keeps ones >= zeros)
        if (ones > zeros) {
            sb.append('0');
            backtrackOptimal(n, ones, zeros + 1, sb, result);
            sb.deleteCharAt(sb.length() - 1); // Backtrack
        }
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Original Provided Code)
     * ============================================================================
     *
     * Detailed Intuition:
     * We pass a `String` as the current state. Since Strings are immutable in Java,
     * `output + "1"` inherently creates a brand new String. This means we don't
     * explicitly have to "backtrack" (remove characters), making the code slightly
     * shorter and easier to read, at the massive cost of heap memory churn.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * 2^N) worst-case.
     * - Space Complexity: O(N) Auxiliary Stack Space + O(N * 2^N) Heap Space due
     *   to intermediate string creation.
     */
    public static List<String> generateBruteForce(int n) {
        List<String> result = new ArrayList<>();
        if (n <= 0) return result;
        generateBinaryString(n, 0, 0, "", result);
        return result;
    }

    private static void generateBinaryString(int n, int ones, int zeros, String output, List<String> result) {
        if (output.length() == n) {
            result.add(output);
            return;
        }

        generateBinaryString(n, ones + 1, zeros, output + "1", result);

        if (ones > zeros) {
            generateBinaryString(n, ones, zeros + 1, output + "0", result);
        }
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Backtracking with char array)
     * ============================================================================
     *
     * Detailed Intuition:
     * To squeeze out the absolute maximum performance, we can replace the
     * `StringBuilder` with a primitive `char[]`. Because our strings are strictly
     * of length `N`, we don't even need to "delete" characters during backtracking!
     * We simply overwrite the index. This removes the bounds-checking overhead
     * present inside `StringBuilder.append()`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * C(N)) where C(N) is the number of valid paths.
     * - Space Complexity: O(N) Aux Space + O(N) for the `char[]`.
     */
    public static List<String> generateAlternative(int n) {
        List<String> result = new ArrayList<>();
        if (n <= 0) return result;
        char[] buffer = new char[n];
        backtrackChar(n, 0, 0, buffer, 0, result);
        return result;
    }

    private static void backtrackChar(int n, int ones, int zeros, char[] buffer, int index, List<String> result) {
        if (index == n) {
            result.add(new String(buffer));
            return;
        }

        // Choice 1: Add '1'
        buffer[index] = '1';
        backtrackChar(n, ones + 1, zeros, buffer, index + 1, result);

        // Choice 2: Add '0' (Only if ones > zeros)
        if (ones > zeros) {
            buffer[index] = '0';
            backtrackChar(n, ones, zeros + 1, buffer, index + 1, result);
        }
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("🤖 Executing Test Suite: N-Bit Binary Strings (1s >= 0s)\n");

        runTest("Test Case 1: Standard N = 4", 4);
        runTest("Test Case 2: Minimal Valid N = 1", 1);
        runTest("Test Case 3: N = 2", 2);
        runTest("Test Case 4: Edge Case N = 0", 0);
    }

    /**
     * Helper method to initialize, run all phases, and validate results.
     */
    private static void runTest(String testName, int n) {
        System.out.println(">>> " + testName + " | Input: " + n);

        long startOptimal = System.nanoTime();
        List<String> resOptimal = generateOptimal(n);
        long timeOptimal = System.nanoTime() - startOptimal;

        long startBrute = System.nanoTime();
        List<String> resBrute = generateBruteForce(n);
        long timeBrute = System.nanoTime() - startBrute;

        long startAlt = System.nanoTime();
        List<String> resAlt = generateAlternative(n);
        long timeAlt = System.nanoTime() - startAlt;

        System.out.println("Phase 1 (Optimal SB)  : " + resOptimal);
        System.out.println("Phase 2 (Brute String): " + resBrute);
        System.out.println("Phase 3 (Alternative) : " + resAlt);

        boolean passed = resOptimal.equals(resBrute) && resBrute.equals(resAlt);
        System.out.println("Validation Status     : " + (passed ? "✅ PASS" : "❌ FAIL"));

        System.out.printf("Optimal Execution Time: %.4f ms\n", timeOptimal / 1_000_000.0);
        System.out.printf("Brute Execution Time  : %.4f ms\n", timeBrute / 1_000_000.0);
        System.out.printf("Alt. Execution Time   : %.4f ms\n", timeAlt / 1_000_000.0);
        System.out.println("--------------------------------------------------\n");
    }
}