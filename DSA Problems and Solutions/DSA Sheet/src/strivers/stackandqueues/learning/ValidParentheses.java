package strivers.stackandqueues.learning;

import java.util.*;

/**
 * ============================================================================
 * 20. Valid Parentheses
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']',
 * determine if the input string is valid.
 * * An input string is valid if:
 * 1. Open brackets must be closed by the same type of brackets.
 * 2. Open brackets must be closed in the correct order.
 * 3. Every close bracket has a corresponding open bracket of the same type.
 *
 * Example 1:
 * Input: s = "()"
 * Output: true
 *
 * Example 2:
 * Input: s = "()[]{}"
 * Output: true
 *
 * Example 3:
 * Input: s = "(]"
 * Output: false
 * * Example 4:
 * Input: s = "([])"
 * Output: true
 *
 * Example 5:
 * Input: s = "([)]"
 * Output: false
 *
 * CONSTRAINTS:
 * 1 <= s.length <= 10^4
 * s consists of parentheses only '()[]{}'.
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Stack (using Deque)
 * Phase 2: Brute Force Approach - Iterative String Replacement
 * Phase 3: Alternative Approach - Array-based Stack (Fastest execution)
 * ============================================================================
 */
public class ValidParentheses {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Stack using Deque)
     * ============================================================================
     * Detailed Intuition:
     * Parentheses matching is the textbook use-case for a Stack data structure
     * because of its LIFO (Last-In-First-Out) property. The most recently opened
     * bracket must be the first one to be closed.
     * * A highly elegant optimization: Instead of pushing the opening bracket onto
     * the stack and comparing it later, we push the *corresponding closing bracket* * when we encounter an open one. When we encounter a closing bracket in the
     * string, we simply check if it matches the top of the stack. If it doesn't,
     * or if the stack is empty (meaning a closing bracket appeared without an open
     * one), the string is invalid.
     * * Note: We use `ArrayDeque` instead of the legacy `Stack` class as it is
     * faster and unsynchronized.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), where N is the length of the string. We traverse
     * the string exactly once. Push and pop operations on an ArrayDeque take O(1) time.
     * - Space Complexity: O(N) auxiliary heap space for the Deque. In the worst-case
     * scenario (e.g., "((((((("), all characters will be pushed onto the stack.
     * ============================================================================
     */
    public boolean isValidOptimal(String s) {
        // Fast fail for strings with odd lengths (they can never be perfectly paired)
        if (s.length() % 2 != 0) return false;

        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(')');
            } else if (c == '{') {
                stack.push('}');
            } else if (c == '[') {
                stack.push(']');
            } else if (stack.isEmpty() || stack.pop() != c) {
                // Stack is empty (unmatched closing bracket) OR
                // the popped bracket doesn't match the expected one
                return false;
            }
        }

        // If the stack is empty, all brackets were matched.
        return stack.isEmpty();
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Iterative String Replacement) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * If a string of parentheses is valid, it must contain at least one adjacent
     * pair of valid brackets (i.e., "()", "{}", or "[]"). If we continuously find
     * and remove these adjacent valid pairs from the string, a completely valid
     * string will eventually be reduced to an empty string. If the string stops
     * changing but is not empty, it contains unmatched or incorrectly ordered brackets.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2). In the worst case (e.g., "(((())))"), we make
     * N/2 passes, and `replace()` traverses the string taking O(N) each time.
     * - Space Complexity: O(N) heap space. Strings are immutable in Java, so every
     * `replace()` operation creates a brand new string object in memory.
     * ============================================================================
     */
    public boolean isValidBruteForce(String s) {
        if (s.length() % 2 != 0) return false;

        int prevLength = -1;

        // Continue replacing while the string length is shrinking
        while (s.length() != prevLength) {
            prevLength = s.length();
            s = s.replace("()", "")
                    .replace("[]", "")
                    .replace("{}", "");
        }

        return s.isEmpty();
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Array-based Stack)
     * ============================================================================
     * Detailed Intuition:
     * While `ArrayDeque` is the recommended standard library class for stacks, we
     * can shave off object creation overhead by simulating a stack using a raw
     * primitive array. We know the stack will never exceed the length of the string.
     * We use a `head` integer to keep track of our position.
     * This approach operates on the exact same LIFO logic as Phase 1 but routinely
     * hits 100% execution speed on competitive platforms like LeetCode due to cache
     * locality and lack of Collection boxing/unboxing overhead.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). Single pass through the string, O(1) array access.
     * - Space Complexity: O(N) heap space to allocate the `char[]` stack.
     * ============================================================================
     */
    public boolean isValidArrayStack(String s) {
        if (s.length() % 2 != 0) return false;

        char[] stack = new char[s.length()];
        int head = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack[head++] = ')';
            } else if (c == '{') {
                stack[head++] = '}';
            } else if (c == '[') {
                stack[head++] = ']';
            } else if (head == 0 || stack[--head] != c) {
                return false;
            }
        }

        return head == 0;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests all approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        ValidParentheses solver = new ValidParentheses();

        // Setup Test Cases
        String[] testCases = {
                "()",           // Standard valid (Example 1)
                "()[]{}",       // Adjacent valid (Example 2)
                "(]",           // Mismatched pair (Example 3)
                "([])",         // Nested valid (Example 4)
                "([)]",         // Interleaved invalid (Example 5)
                "(((({}))))",   // Deeply nested valid
                "((((((((",     // All open brackets (Edge case)
                "}][{",         // All close brackets (Edge case)
                ""              // Empty string (Edge case)
        };

        boolean[] expected = {true, true, false, true, false, true, false, false, true};

        System.out.println("=== Valid Parentheses Testing Suite ===\n");

        for (int i = 0; i < testCases.length; i++) {
            String s = testCases[i];
            boolean exp = expected[i];

            boolean optRes = solver.isValidOptimal(s);
            boolean bfRes = solver.isValidBruteForce(s);
            boolean arrRes = solver.isValidArrayStack(s);

            // Using Java 8 Streams just for a clean validation check across all results
            boolean allMatch = java.util.stream.Stream.of(optRes, bfRes, arrRes)
                    .allMatch(res -> res == exp);

            System.out.printf("Test '%s':\n", s);
            System.out.printf("  Expected: %b | Optimal: %b | BruteForce: %b | ArrayStack: %b\n",
                    exp, optRes, bfRes, arrRes);
            System.out.printf("  Status: %s\n\n", allMatch ? "PASS \u2714" : "FAIL \u2718");
        }
    }
}