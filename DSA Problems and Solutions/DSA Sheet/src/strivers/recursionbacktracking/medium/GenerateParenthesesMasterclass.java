package strivers.recursionbacktracking.medium;

/**
 * ============================================================================
 * 🤖 Java-Centric DSA Prompt Template: Senior Interviewer & Evaluator
 * ============================================================================
 *
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement: 22. Generate Parentheses (LeetCode Medium)
 *
 * Given n pairs of parentheses, write a function to generate all combinations
 * of well-formed parentheses.
 *
 * 🚨 EVALUATION OF PROVIDED CODE:
 * The code you provided is absolutely stellar. It uses recursive backtracking
 * with a `StringBuilder` and tightly controls the placement of '(' and ')'
 * using `open` and `close` counts. This prunes the search space efficiently and
 * avoids unnecessary object creation in the heap.
 * This IS the Optimal Approach! I have retained it exactly as you wrote it in
 * Phase 1, fully documenting its internals.
 *
 * Constraints:
 * - 1 <= n <= 8
 *
 * Input/Output Formats:
 * - Input: An integer `n` representing the number of pairs.
 * - Output: A List of strings containing all valid combinations.
 *
 * Examples:
 *
 * Example 1:
 * Input: n = 3
 * Output: ["((()))","(()())","(())()","()(())","()()()"]
 *
 * Example 2:
 * Input: n = 1
 * Output: ["()"]
 *
 * ============================================================================
 * 2.2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 *
 * Combinatorics Note: The number of valid combinations for n pairs is defined
 * by the n-th Catalan number.
 * C(n) = (1 / (n + 1)) * (2n choose n).
 * Asymptotically, this is bounded by (4^n / (n * sqrt(n))).
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GenerateParenthesesMasterclass {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Your Backtracking with StringBuilder)
     * ============================================================================
     *
     * Detailed Intuition:
     * To form a valid sequence, we must place exactly `n` open '(' and `n` close ')'
     * brackets. We can build this sequence character by character.
     * Rule 1: We can always add an open bracket '(' as long as we haven't used all `n`.
     * Rule 2: We can only add a close bracket ')' if we have placed MORE open
     * brackets than close brackets so far (`close < open`). This strictly ensures
     * the sequence remains well-formed at every prefix.
     *
     * By using `StringBuilder` and backtracking (deleting the last character),
     * we traverse the decision tree efficiently without flooding the heap space.
     *
     * Complexity Analysis:
     * - Time Complexity: O(4^n / sqrt(n))
     *   This bounds the nth Catalan number. Our tight constraints ensure we only
     *   visit valid branches.
     * - Space Complexity: O(n) Auxiliary Stack Space
     *   The depth of the recursion tree is 2*n. The `StringBuilder` also takes O(n).
     *   (Excludes the O(4^n / sqrt(n)) space to hold the output list).
     */
    public static List<String> generateOptimal(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, new StringBuilder(), 0, 0, n);
        return result;
    }

    private static void backtrack(List<String> result, StringBuilder current, int open, int close, int n) {
        // Base case: if the string is of length 2*n, it's a valid sequence
        if (current.length() == 2 * n) {
            result.add(current.toString());
            return;
        }

        // Add an opening parenthesis if possible
        if (open < n) {
            current.append('(');
            backtrack(result, current, open + 1, close, n);
            current.deleteCharAt(current.length() - 1); // backtrack
        }

        // Add a closing parenthesis if possible (only if close < open)
        if (close < open) {
            current.append(')');
            backtrack(result, current, open, close + 1, n);
            current.deleteCharAt(current.length() - 1); // backtrack
        }
    }


    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Generate All & Validate)
     * ============================================================================
     *
     * Detailed Intuition:
     * What if we didn't know the clever `close < open` rule? We would blindly
     * generate EVERY possible sequence of length 2n consisting of '(' and ')'.
     * Once a sequence is generated, we scan it left-to-right to verify if it
     * is well-formed.
     *
     * Complexity Analysis:
     * - Time Complexity: O(2^(2n) * n)
     *   There are 2^(2n) possible sequences of length 2n. Validating each takes O(n).
     *   This is vastly slower than the Catalan number bound of the optimal approach.
     * - Space Complexity: O(n) Aux Stack Space.
     */
    public static List<String> generateBruteForce(int n) {
        List<String> result = new ArrayList<>();
        generateAll(new char[2 * n], 0, result);
        return result;
    }

    private static void generateAll(char[] current, int pos, List<String> result) {
        if (pos == current.length) {
            if (isValid(current)) {
                result.add(new String(current));
            }
            return;
        }
        current[pos] = '(';
        generateAll(current, pos + 1, result);

        current[pos] = ')';
        generateAll(current, pos + 1, result);
    }

    private static boolean isValid(char[] current) {
        int balance = 0;
        for (char c : current) {
            if (c == '(') balance++;
            else balance--;

            if (balance < 0) return false; // More closing than opening at some prefix
        }
        return balance == 0;
    }


    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Divide & Conquer / Closure Number)
     * ============================================================================
     *
     * Detailed Intuition:
     * Every valid sequence of length 2n can be constructed by picking a "closure
     * number" `c` (where `0 <= c < n`).
     * The sequence can be formulated as: "(" + {valid sequence of length c} + ")"
     * + {valid sequence of length n - 1 - c}.
     * This recursive relationship naturally leads to a DP/Memoization approach
     * where we build larger solutions from smaller, previously computed ones.
     *
     * Complexity Analysis:
     * - Time Complexity: O(4^n / sqrt(n))
     *   Same Catalan number bound, building combinations explicitly.
     * - Space Complexity: O(4^n / sqrt(n)) Heap Space
     *   We store all intermediate lists in the `dp` array.
     */
    public static List<String> generateAlternativeDP(int n) {
        if (n == 0) return new ArrayList<>(Collections.singletonList(""));

        List<List<String>> dp = new ArrayList<>();
        dp.add(Collections.singletonList("")); // n = 0

        for (int i = 1; i <= n; i++) {
            List<String> cur = new ArrayList<>();
            for (int c = 0; c < i; c++) {
                for (String inside : dp.get(c)) {
                    for (String outside : dp.get(i - 1 - c)) {
                        cur.add("(" + inside + ")" + outside);
                    }
                }
            }
            dp.add(cur);
        }
        return dp.get(n);
    }


    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("🤖 Executing Masterclass Test Suite: Generate Parentheses\n");

        runTest("Test Case 1: Standard N = 3", 3);
        runTest("Test Case 2: Minimal N = 1", 1);
        runTest("Test Case 3: Edge N = 4", 4);
    }

    /**
     * Helper method to initialize, run all phases, and validate results using
     * Java 8 Stream API formatting.
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
        List<String> resAlt = generateAlternativeDP(n);
        long timeAlt = System.nanoTime() - startAlt;

        // Since output order might slightly differ for DP, we sort before comparing
        Collections.sort(resOptimal);
        Collections.sort(resBrute);
        Collections.sort(resAlt);

        boolean passed = resOptimal.equals(resBrute) && resBrute.equals(resAlt);

        System.out.println("Phase 1 (Optimal) Size: " + resOptimal.size());

        if (resOptimal.size() <= 15) { // Print if manageable size
            System.out.println("Valid Parentheses: \n" +
                    resOptimal.stream().collect(Collectors.joining("\n  ", "  ", "")));
        }

        System.out.println("Validation Status     : " + (passed ? "✅ PASS" : "❌ FAIL"));
        System.out.printf("Optimal Execution Time: %.4f ms\n", timeOptimal / 1_000_000.0);
        System.out.printf("Brute Execution Time  : %.4f ms\n", timeBrute / 1_000_000.0);
        System.out.printf("DP Alt Execution Time : %.4f ms\n", timeAlt / 1_000_000.0);
        System.out.println("--------------------------------------------------\n");
    }
}