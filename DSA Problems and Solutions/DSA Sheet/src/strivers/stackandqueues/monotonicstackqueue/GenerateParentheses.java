package strivers.stackandqueues.monotonicstackqueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * ============================================================================
 * MASTERCLASS SOLUTION: GENERATE PARENTHESES
 * ============================================================================
 * * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * * * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem: 22. Generate Parentheses (Medium)
 * * Given n pairs of parentheses, write a function to generate all combinations
 * of well-formed parentheses.
 * * Constraints:
 * - 1 <= n <= 8
 * * Example 1:
 * Input: n = 3
 * Output: ["((()))","(()())","(())()","()(())","()()()"]
 * * Example 2:
 * Input: n = 1
 * Output: ["()"]
 * * * ----------------------------------------------------------------------------
 * 2.2 PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP)
 * ----------------------------------------------------------------------------
 * This problem relies heavily on combinatorics and state-space exploration.
 * - Phase 1: Optimal Approach - Backtracking with state pruning.
 * - Phase 2: Brute Force Approach - Generate all sequences and validate.
 * - Phase 3: Alternative Approach - Divide and Conquer (Closure Number).
 * ============================================================================
 */
public class GenerateParentheses {

    /**
     * ----------------------------------------------------------------------------
     * Phase 1: Optimal Approach (Backtracking)
     * ----------------------------------------------------------------------------
     * Detailed Intuition:
     * Instead of generating all possible sequences of '(' and ')' and then
     * checking if they are valid, we can generate only valid sequences on the fly.
     * We achieve this by keeping track of the count of open and closed brackets
     * placed so far.
     * * Rules for valid generation:
     * 1. We can always place an open bracket '(' if we haven't used all 'n' of them.
     * 2. We can only place a close bracket ')' if there are more open brackets
     * placed than close brackets currently (i.e., open > close).
     * 3. The base case is reached when our current string reaches a length of 2 * n.
     * * Complexity Analysis:
     * - Time Complexity: O(4^n / sqrt(n)). This is bounded by the n-th Catalan number,
     * which represents the number of valid parenthesis combinations. The asymptotic
     * behavior of Catalan numbers is (4^n) / (n * sqrt(n)).
     * - Space Complexity: O(n) Auxiliary Stack Space for the recursion depth.
     * O(4^n / sqrt(n)) Heap Space to store the final list of valid combinations.
     * ----------------------------------------------------------------------------
     */
    public List<String> generateParenthesesPhase1(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, new StringBuilder(), 0, 0, n);
        return result;
    }

    private void backtrack(List<String> result, StringBuilder currentString, int openCount, int closeCount, int maxPairs) {
        // Base case: If the current string is complete
        if (currentString.length() == maxPairs * 2) {
            result.add(currentString.toString());
            return;
        }

        // Decision 1: Add an open parenthesis if we still have available ones
        if (openCount < maxPairs) {
            currentString.append("(");
            backtrack(result, currentString, openCount + 1, closeCount, maxPairs);
            currentString.deleteCharAt(currentString.length() - 1); // Backtrack
        }

        // Decision 2: Add a close parenthesis if it pairs with an unmatched open one
        if (closeCount < openCount) {
            currentString.append(")");
            backtrack(result, currentString, openCount, closeCount + 1, maxPairs);
            currentString.deleteCharAt(currentString.length() - 1); // Backtrack
        }
    }

    /**
     * ----------------------------------------------------------------------------
     * Phase 2: Brute Force Approach ("Think it" stage)
     * ----------------------------------------------------------------------------
     * Detailed Intuition:
     * The simplest way to approach this is to ignore the rules of validity during
     * generation. We generate absolutely every possible combination of exactly 'n'
     * open and 'n' close brackets. Since there are 2n positions and 2 choices for
     * each, we generate 2^(2n) sequences.
     * Once a sequence of length 2n is formed, we pass it to a validation function.
     * A sequence is valid if the balance variable (open - close) never drops below
     * zero and ends exactly at zero.
     * * Complexity Analysis:
     * - Time Complexity: O(2^(2n) * n). There are 2^(2n) combinations. For each, we
     * take O(n) time to validate it.
     * - Space Complexity: O(n) Auxiliary Stack Space for recursion depth.
     * O(2^(2n) * n) Temporary Heap Space for building strings before filtering.
     * ----------------------------------------------------------------------------
     */
    public List<String> generateParenthesesPhase2(int n) {
        List<String> result = new ArrayList<>();
        generateAll(new char[2 * n], 0, result);
        return result;
    }

    private void generateAll(char[] current, int pos, List<String> result) {
        // Base case: sequence is fully constructed
        if (pos == current.length) {
            if (isValid(current)) {
                result.add(new String(current));
            }
            return;
        }

        // Branch 1: Try '('
        current[pos] = '(';
        generateAll(current, pos + 1, result);

        // Branch 2: Try ')'
        current[pos] = ')';
        generateAll(current, pos + 1, result);
    }

    private boolean isValid(char[] current) {
        int balance = 0;
        for (char c : current) {
            if (c == '(') {
                balance++;
            } else {
                balance--;
            }
            // A closing bracket appeared before a matching open bracket
            if (balance < 0) return false;
        }
        return balance == 0;
    }

    /**
     * ----------------------------------------------------------------------------
     * Phase 3: Alternative Approach (Divide and Conquer / Closure Number)
     * ----------------------------------------------------------------------------
     * Detailed Intuition:
     * We can define the generation recursively based on the "Closure Number".
     * Any valid parenthesis sequence must start with an open bracket '('.
     * Let's say the matching closing bracket ')' for this first '(' is at some
     * position. Everything inside this pair `( ... )` must be a valid sequence
     * of `c` pairs, and everything to the right of it must be a valid sequence of
     * the remaining `n - 1 - c` pairs.
     * By iterating `c` from 0 to `n-1`, we can deterministically combine sub-answers:
     * Result = "(" + generate(c) + ")" + generate(n - 1 - c).
     * * Complexity Analysis:
     * - Time Complexity: O(4^n / sqrt(n)). Still bounded by the Catalan number
     * since it strictly generates valid combinations.
     * - Space Complexity: O(n) Auxiliary Stack Space + O(4^n / sqrt(n)) Heap Space.
     * (Note: Memoization could be added to optimize overlapping subproblems if n was larger).
     * ----------------------------------------------------------------------------
     */
    public List<String> generateParenthesesPhase3(int n) {
        List<String> ans = new ArrayList<>();
        // Base case: 0 pairs means exactly one valid combination: the empty string
        if (n == 0) {
            ans.add("");
            return ans;
        }

        // c represents the number of pairs inside the primary outer brackets
        for (int c = 0; c < n; ++c) {
            for (String left : generateParenthesesPhase3(c)) {
                for (String right : generateParenthesesPhase3(n - 1 - c)) {
                    ans.add("(" + left + ")" + right);
                }
            }
        }
        return ans;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        GenerateParentheses generator = new GenerateParentheses();

        // Define Test Cases
        List<TestCase> testCases = Arrays.asList(
                new TestCase(3, Arrays.asList("((()))","(()())","(())()","()(())","()()()"), "Example 1 (n = 3)"),
                new TestCase(1, Arrays.asList("()"), "Example 2 (n = 1)"),
                new TestCase(2, Arrays.asList("(())", "()()"), "Standard Case (n = 2)")
        );

        System.out.println("--- Starting Evaluation Test Suite ---");

        // Method references to test
        List<MethodReference> methods = Arrays.asList(
                new MethodReference("Phase 1: Optimal Backtracking", generator::generateParenthesesPhase1),
                new MethodReference("Phase 2: Brute Force", generator::generateParenthesesPhase2),
                new MethodReference("Phase 3: Closure Number", generator::generateParenthesesPhase3)
        );

        // Java 8 Streams execution
        methods.forEach(method -> {
            System.out.println("\nExecuting " + method.name + "...");
            testCases.stream().forEach(test -> {
                List<String> result = method.function.apply(test.n);

                // Sort both lists to ensure order doesn't fail the equality check
                List<String> sortedResult = new ArrayList<>(result);
                List<String> sortedExpected = new ArrayList<>(test.expectedOutput);
                Collections.sort(sortedResult);
                Collections.sort(sortedExpected);

                boolean passed = sortedResult.equals(sortedExpected);
                System.out.printf("[%s] %s | Expected Size: %d | Got Size: %d%n",
                        passed ? "PASS" : "FAIL", test.description, test.expectedOutput.size(), result.size());
                if (!passed) {
                    System.out.println("   Expected: " + sortedExpected);
                    System.out.println("   Got:      " + sortedResult);
                }
            });
        });
    }

    // Helper classes for clean testing
    static class TestCase {
        int n;
        List<String> expectedOutput;
        String description;

        TestCase(int n, List<String> expectedOutput, String description) {
            this.n = n;
            this.expectedOutput = expectedOutput;
            this.description = description;
        }
    }

    static class MethodReference {
        String name;
        Function<Integer, List<String>> function;

        MethodReference(String name, Function<Integer, List<String>> function) {
            this.name = name;
            this.function = function;
        }
    }
}