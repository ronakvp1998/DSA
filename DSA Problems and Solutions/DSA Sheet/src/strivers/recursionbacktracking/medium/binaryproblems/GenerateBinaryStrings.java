package strivers.recursionbacktracking.medium.binaryproblems;

/**
 * ============================================================================
 * 🤖 Java-Centric DSA Prompt Template: Senior Interviewer & Evaluator
 * ============================================================================
 *
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement: Generate All Binary Strings of Length n
 *
 * Given an integer n, generate all possible binary strings of length n. The
 * output should ideally be generated in lexicographical order (i.e., starting
 * from "00..0" to "11..1").
 *
 * Constraints:
 * - 0 <= n <= 20 (Beyond 20, 2^n strings will exceed typical memory/time limits
 *   for standard outputs).
 *
 * Input/Output Formats:
 * - Input: An integer `n` representing the length of the binary string.
 * - Output: A List of Strings representing all valid binary strings.
 *
 * Examples:
 *
 * Example 1:
 * Input: n = 3
 * Output: ["000", "001", "010", "011", "100", "101", "110", "111"]
 *
 * Example 2:
 * Input: n = 1
 * Output: ["0", "1"]
 *
 * ============================================================================
 * 2.2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenerateBinaryStrings {

    /**
     * ============================================================================
     * OPTIMAL APPROACH (Using StringBuilder)
     * ============================================================================
     *
     * Detailed Intuition:
     * We pass a single StringBuilder object down the recursion tree. At each step,
     * we choose '0', recurse, and then "undo" our choice by deleting the last
     * character. We then do the exact same thing for '1'. This ensures we only
     * ever use one dynamic string builder in memory, avoiding O(2^n) String creations.
     *
     * Complexity Analysis:
     * - Time Complexity: O(n * 2^n) - 2^n combinations, and converting StringBuilder
     *   to a String at the base case takes O(n) time.
     * - Space Complexity: O(n) Auxiliary Stack Space + O(n) Heap Space for the
     *   single StringBuilder.
     */
    public static List<String> generateOptimalSB(int n) {
        List<String> result = new ArrayList<>();
        if (n <= 0) {
            return result;
        }

        // Initialize the single StringBuilder we will reuse
        StringBuilder sb = new StringBuilder();
        backtrack(n, sb, result);

        return result;
    }

    private static void backtrack(int n, StringBuilder sb, List<String> result) {
        // Base case: If the StringBuilder reaches length n, string is fully formed
        if (sb.length() == n) {
            result.add(sb.toString());
            return;
        }

        // Branch 1: Append '0', recurse, then undo
        sb.append('0');
        backtrack(n, sb, result);
        sb.deleteCharAt(sb.length() - 1); // <-- The crucial "undo" step

        // Branch 2: Append '1', recurse, then undo
        sb.append('1');
        backtrack(n, sb, result);
        sb.deleteCharAt(sb.length() - 1); // <-- The crucial "undo" step
    }

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Backtracking with char array)
     * ============================================================================
     *
     * Detailed Intuition:
     * While creating strings recursively by appending "0" or "1" works, strings in Java
     * are immutable. Concatenating strings at every recursive step creates O(2^n)
     * intermediate string objects in the heap space, leading to heavy Garbage Collection
     * overhead.
     *
     * The optimal pattern is "Backtracking" using a pre-allocated `char[]`. We mutate
     * the exact same array in-place. Once the index reaches `n`, we convert the `char[]`
     * into a new String and add it to our result list. This completely eliminates
     * intermediate object creation.
     *
     * Complexity Analysis:
     * - Time Complexity: O(n * 2^n) - There are 2^n combinations. For each valid combination,
     *   we take O(n) time to construct a new String from the char array.
     * - Space Complexity: O(n) Auxiliary Stack Space (recursion depth) + O(n) for the
     *   `char[]` buffer. Output space is O(n * 2^n) which isn't counted as auxiliary space.
     */
    public static List<String> generateOptimal(int n) {
        List<String> result = new ArrayList<>();
        if (n <= 0) return result;

        char[] buffer = new char[n];
        backtrack(n, 0, buffer, result);
        return result;
    }

    private static void backtrack(int n, int index, char[] buffer, List<String> result) {
        // Base case: If index reaches n, string is fully formed
        if (index == n) {
            result.add(new String(buffer));
            return;
        }

        // Branch 1: Set current character to '0' and recurse
        buffer[index] = '0';
        backtrack(n, index + 1, buffer, result);

        // Branch 2: Set current character to '1' and recurse
        buffer[index] = '1';
        backtrack(n, index + 1, buffer, result);
    }


    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Recursion with String Concatenation)
     * ============================================================================
     *
     * Detailed Intuition:
     * This is the literal translation of the provided approach: "We use recursion
     * to build strings digit by digit... At each index, we can either put '0' or '1'."
     * We pass an accumulated prefix string down the call stack. While very readable,
     * it is computationally brute-force regarding heap memory usage due to String
     * immutability.
     *
     * Complexity Analysis:
     * - Time Complexity: O(n * 2^n) - String concatenation `prefix + "0"` takes time
     *   proportional to the length of the string at every single node in the recursion tree.
     * - Space Complexity: O(n) Auxiliary Stack Space, but O(2^n * n) Heap Space is consumed
     *   by intermediate GC-eligible String objects across the recursive calls.
     */
    public static List<String> generateBruteForce(int n) {
        List<String> result = new ArrayList<>();
        if (n <= 0) return result;

        buildStringRecursive(n, "", result);
        return result;
    }

    private static void buildStringRecursive(int n, String prefix, List<String> result) {
        if (prefix.length() == n) {
            result.add(prefix);
            return;
        }

        // At each step, branch into '0' and '1'
        buildStringRecursive(n, prefix + "0", result);
        buildStringRecursive(n, prefix + "1", result);
    }


    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Iterative Bit Manipulation)
     * ============================================================================
     *
     * Detailed Intuition:
     * A binary string of length `n` perfectly represents the integers from `0` to `(2^n) - 1`.
     * Instead of using recursion, we can just run a loop through these integers. For each
     * integer, we convert it to its binary string representation. We must pad the left
     * side with '0's to ensure every string is exactly length `n`.
     *
     * We can leverage Java 8 IntStream for a highly declarative and concise implementation.
     *
     * Complexity Analysis:
     * - Time Complexity: O(n * 2^n) - Iterating 2^n times, and formatting/padding each
     *   number to a string of length `n` takes O(n) time.
     * - Space Complexity: O(1) Auxiliary Space since we use no recursion stack or extra
     *   data structures other than the required output list.
     */
    public static List<String> generateIterativeBitwise(int n) {
        if (n <= 0) return new ArrayList<>();

        int totalCombinations = 1 << n; // Equivalent to Math.pow(2, n)

        // Using Java 8 Streams to generate numbers 0 to (2^n - 1),
        // convert to binary strings, and pad with leading zeros.
        return IntStream.range(0, totalCombinations)
                .mapToObj(i -> {
                    String binaryStr = Integer.toBinaryString(i);
                    // Pad with leading zeros to meet length n
                    return String.format("%" + n + "s", binaryStr).replace(' ', '0');
                })
                .collect(Collectors.toList());
    }


    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("🤖 Executing Test Suite: Generate Binary Strings of Length n\n");

        runTest("Phase 1: Optimal (char[] Backtracking) - n = 3", 3, 1);
        runTest("Phase 2: Brute Force (String Concatenation) - n = 3", 3, 2);
        runTest("Phase 3: Alternative (Bit Manipulation) - n = 3", 3, 3);

        System.out.println("--------------------------------------------------\n");

        runTest("Edge Case: n = 1 (Optimal)", 1, 1);
        runTest("Edge Case: n = 0 (Alternative)", 0, 3);
    }

    /**
     * Helper method to initialize, run the specified algorithm phase, and print
     * results cleanly.
     */
    private static void runTest(String testName, int n, int phase) {
        System.out.println(">>> Test: " + testName);

        List<String> result = new ArrayList<>();
        long startTime = System.nanoTime();

        switch (phase) {
            case 1:
                result = generateOptimal(n);
                break;
            case 2:
                result = generateBruteForce(n);
                break;
            case 3:
                result = generateIterativeBitwise(n);
                break;
        }

        long endTime = System.nanoTime();

        System.out.println("    Generated Count : " + result.size());

        // Limit printing to prevent console flooding for larger values of n if modified later
        if (result.size() <= 16) {
            System.out.println("    Output Strings  : " + result);
        } else {
            System.out.println("    Output Strings  : [" + result.get(0) + ", " + result.get(1) + ", ... " + result.get(result.size()-1) + "]");
        }

        System.out.printf("    Execution Time  : %.3f ms\n\n", (endTime - startTime) / 1_000_000.0);
    }
}