package strivers.string.hard;

/**
 * ============================================================================
 * 921. Minimum Add to Make Parentheses Valid
 * ============================================================================
 * A parentheses string is valid if and only if:
 * - It is the empty string,
 * - It can be written as AB (A concatenated with B), where A and B are valid strings, or
 * - It can be written as (A), where A is a valid string.
 * * You are given a parentheses string s. In one move, you can insert a parenthesis
 * at any position of the string.
 * * For example, if s = "()))", you can insert an opening parenthesis to be "(()))"
 * or a closing parenthesis to be "())))".
 * * Return the minimum number of moves required to make s valid.
 * * Example 1:
 * Input: s = "())"
 * Output: 1
 * * Example 2:
 * Input: s = "((("
 * Output: 3
 * * Constraints:
 * - 1 <= s.length <= 1000
 * - s[i] is either '(' or ')'.
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 */
import java.util.ArrayDeque;
import java.util.Deque;

public class MinimumAddToMakeValid {

    /**
     * ========================================================================
     * Phase 1: Stack Simulation - The "Think it" stage.
     * ========================================================================
     * Detailed Intuition:
     * When dealing with valid parentheses, the classic data structure is a Stack.
     * We iterate through the string character by character.
     * If we see an opening bracket '(', we push it onto the stack.
     * If we see a closing bracket ')', we check the stack:
     * - If the stack has an opening bracket, they match! We pop the '(' off
     * the stack (they cancel each other out).
     * - If the stack is empty, it means we have a closing bracket with no
     * matching opening bracket. We must add an opening bracket to fix it.
     * We keep a counter (`unmatchedRight`) for these.
     * At the very end of the string, any '(' left sitting in the stack are
     * unmatched left brackets. We need to add a ')' for each of them.
     * The total additions required = `unmatchedRight` + the size of the stack.
     * * Complexity Analysis:
     * - Time Complexity: O(N), where N is the length of the string. We process
     * every character exactly once. Stack push and pop are O(1) operations.
     * - Space Complexity: O(N) Auxiliary Stack Space. In the worst-case scenario
     * (e.g., "(((((("), every character is pushed onto the stack, consuming
     * memory proportional to the input length.
     * ========================================================================
     */
    public static int minAddToMakeValidStack(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        int unmatchedRight = 0; // Tracks ')' that have no matching '('

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                stack.push(c);
            } else { // c == ')'
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop(); // Found a valid pair, remove the '('
                } else {
                    unmatchedRight++; // ')' with no preceding '('
                }
            }
        }

        // The remaining elements in the stack are unmatched '('
        int unmatchedLeft = stack.size();

        return unmatchedRight + unmatchedLeft;
    }

    /**
     * ========================================================================
     * Phase 2: Greedy Counting (Space Optimized) - The "Perfect it" stage.
     * ========================================================================
     * Detailed Intuition:
     * As a Senior Developer, we realize we don't actually care about *storing* * the parentheses; we only care about *counting* them.
     * We can completely eliminate the Stack object by using two integer variables:
     * 1. `openNeeded`: Tracks how many '(' we need to add to fix unmatched ')'.
     * 2. `closeNeeded`: Tracks how many ')' we need to add to fix unmatched '('.
     * (This essentially acts as our virtual stack size).
     * * As we iterate:
     * - If we see '(', we increment `closeNeeded` (we now expect a closing bracket).
     * - If we see ')', we check `closeNeeded`:
     * - If `closeNeeded > 0`, we decrement it (we successfully paired a bracket).
     * - If `closeNeeded == 0`, we have an extra ')'. We must increment `openNeeded`.
     * The total moves required is simply `openNeeded` + `closeNeeded`.
     * * Complexity Analysis:
     * - Time Complexity: O(N). Single pass through the string, examining each
     * character exactly once.
     * - Space Complexity: O(1) Auxiliary Space. We only allocate two primitive
     * integer variables, completely eliminating the O(N) memory overhead of
     * the Deque.
     * ========================================================================
     */
    public static int minAddToMakeValidOptimal(String s) {
        int openNeeded = 0;  // Represents unmatched ')'
        int closeNeeded = 0; // Represents unmatched '(' (virtual stack)

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                closeNeeded++; // We expect a future ')'
            } else { // c == ')'
                if (closeNeeded > 0) {
                    closeNeeded--; // We found the expected ')', pair matched!
                } else {
                    openNeeded++; // We encountered ')' but had no prior '(', need to add one.
                }
            }
        }

        // Total additions is the sum of both types of unmatched brackets
        return openNeeded + closeNeeded;
    }

    /**
     * ========================================================================
     * Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        String[] testCases = {
                "())",          // Example 1: 1 unmatched right
                "(((",          // Example 2: 3 unmatched left
                "()",           // Standard valid string (0 needed)
                ")(",           // Completely inverted (2 needed)
                "()))((",       // Mixed unmatched left and right (4 needed)
                "",             // Edge case: Empty string (0 needed)
                "((((((((",     // Large unmatched left
                "))))))))"      // Large unmatched right
        };

        System.out.println("Running test cases for 921. Minimum Add to Make Parentheses Valid...\n");

        for (int i = 0; i < testCases.length; i++) {
            String input = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": \"" + input + "\"");

            int res1 = minAddToMakeValidStack(input);
            int res2 = minAddToMakeValidOptimal(input);

            System.out.println("Phase 1 (Stack Simulation) : " + res1);
            System.out.println("Phase 2 (Greedy Counting)  : " + res2);

            // Validation step
            if (res1 == res2) {
                System.out.println("-> ALL PASS ✓\n");
            } else {
                System.out.println("-> FAIL ✗ (Outputs differ)\n");
            }
        }
    }
}
