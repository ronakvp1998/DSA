package com.questions.strivers.greedyalgorithm.easy;
/**
 * ============================================================================
 * 678. Valid Parenthesis String
 * ============================================================================
 * * PROBLEM STATEMENT:
 * Given a string s containing only three types of characters: '(', ')' and '*',
 * return true if s is valid.
 * * The following rules define a valid string:
 * 1. Any left parenthesis '(' must have a corresponding right parenthesis ')'.
 * 2. Any right parenthesis ')' must have a corresponding left parenthesis '('.
 * 3. Left parenthesis '(' must go before the corresponding right parenthesis ')'.
 * 4. '*' could be treated as a single right parenthesis ')' or a single left
 * parenthesis '(' or an empty string "".
 * * * CONSTRAINTS:
 * - 1 <= s.length <= 100
 * - s[i] is '(', ')' or '*'.
 * * * EXAMPLES:
 * Example 1: Input: s = "()"   -> Output: true
 * Example 2: Input: s = "(*)"  -> Output: true
 * Example 3: Input: s = "(*))" -> Output: true
 * * * ============================================================================
 * CONCEPTUAL VISUALIZATION: RECURSION TREE (s = "(*)")
 * ============================================================================
 * Let f(index, count) represent checking the string from 'index' with 'count'
 * number of currently unmatched open '(' brackets.
 * *                                f(0, 0) [char: '(']
 *                                          |
 *                                          | (count becomes 1)
 *                                          v
 *          f(1, 1) [char: '*'] -> Wildcard branches into 3 possibilities!
 *                                          |
 *          +-----------------------+-----------------------+
 *          | (Treat as '(')        | (Treat as empty "")   | (Treat as ')')
 *          v                       v                       v
 *          f(2, 2) [char: ')']     f(2, 1) [char: ')']     f(2, 0) [char: ')']
 *          |                       |                       |
 *          | (count: 2->1)         | (count: 1->0)         | (count: 0->-1) INVALID!
 *          v                       v                       v
 *          f(3, 1)                 f(3, 0)                 f(3, -1)
 *          (Base: index 3,         (Base: index 3,         (Returns false early)
 *          count != 0 -> false)    count == 0 -> TRUE!)
 *
 * * * Overall Result: TRUE (since at least one branch reached f(3, 0))
 * * * COMPLETE FINAL DP ARRAY STATE (s = "(*)", n = 3):
 * (Rows = index, Cols = count of open brackets)
 * (T = true, F = false)
 * * 0  1  2  3 (count)
 * 0 [T, F, F, F] <- Final Answer at top left (index 0, count 0): TRUE
 * 1 [F, T, F, F]
 * 2 [T, F, T, F]
 * 3 [T, F, F, F] <- Base Cases
 */

import java.util.Arrays;

public class ValidParenthesisStringDP {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * INTUITION:
     * We process the string character by character. We must maintain a `count`
     * of open '(' brackets.
     * - If we see '(', increment count.
     * - If we see ')', decrement count. (If count drops below 0, it's invalid).
     * - If we see '*', we branch into 3 paths (increment, decrement, or skip).
     * If ANY path leads to the end of the string with count == 0, it's valid.
     * * * COMPLEXITY:
     * - Time: O(3^N) -> In the worst case (string of all '*'), we branch 3 ways
     * at every character. Exponential.
     * - Space: O(N) -> Auxiliary stack space for recursion depth up to N. No heap.
     */
    public boolean checkValidStringRecursive(String s) {
        return solveRecursive(0, 0, s);
    }

    private boolean solveRecursive(int index, int count, String s) {
        // Base cases
        if (count < 0) return false; // More ')' than '('
        if (index == s.length()) return count == 0; // All open brackets closed

        char c = s.charAt(index);
        if (c == '(') {
            return solveRecursive(index + 1, count + 1, s);
        } else if (c == ')') {
            return solveRecursive(index + 1, count - 1, s);
        } else {
            // Wildcard '*': try '(', ')', and empty
            return solveRecursive(index + 1, count + 1, s) ||
                    solveRecursive(index + 1, count - 1, s) ||
                    solveRecursive(index + 1, count, s);
        }
    }

    /**
     * ========================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" stage)
     * ========================================================================
     * INTUITION:
     * The recursion tree reveals massive overlapping subproblems. For example,
     * solving `f(index=3, count=2)` could happen from many different wildcard
     * assignments. We cache results in a 2D `memo` array to avoid re-evaluating.
     * (We use an int array where -1 is unvisited, 0 is false, 1 is true).
     * * * COMPLEXITY:
     * - Time: O(N^2) -> Max N indices, and max possible count is N.
     * - Space: O(N^2) Heap Space for `memo` + O(N) Auxiliary Stack Space.
     */
    public boolean checkValidStringMemo(String s) {
        int n = s.length();
        int[][] memo = new int[n][n + 1];
        for (int[] row : memo) Arrays.fill(row, -1);

        return solveMemo(0, 0, s, memo) == 1;
    }

    private int solveMemo(int index, int count, String s, int[][] memo) {
        if (count < 0) return 0;
        if (index == s.length()) return count == 0 ? 1 : 0;

        if (memo[index][count] != -1) return memo[index][count];

        char c = s.charAt(index);
        boolean isValid = false;

        if (c == '(') {
            isValid = solveMemo(index + 1, count + 1, s, memo) == 1;
        } else if (c == ')') {
            isValid = solveMemo(index + 1, count - 1, s, memo) == 1;
        } else {
            isValid = solveMemo(index + 1, count + 1, s, memo) == 1 ||
                    solveMemo(index + 1, count - 1, s, memo) == 1 ||
                    solveMemo(index + 1, count, s, memo) == 1;
        }

        return memo[index][count] = isValid ? 1 : 0;
    }

    /**
     * ========================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" stage)
     * ========================================================================
     * INTUITION:
     * Convert the memoized recursion into an iterative approach. We build the
     * table from the end of the string (index N) backwards to index 0.
     * `dp[index][count]` means "Can we form a valid string from 'index' to end
     * given we currently have 'count' unmatched open brackets?"
     * * * EXACT DEFAULT STATE OF DP ARRAY (s = "(*)", n=3):
     * After base case initialization (dp[n][0] = true), before loops:
     * 0  1  2  3 (count)
     * 0 [F, F, F, F]
     * 1 [F, F, F, F]
     * 2 [F, F, F, F]
     * 3 [T, F, F, F]  <- dp[3][0] is true, representing string end with 0 open.
     * * * COMPLEXITY:
     * - Time: O(N^2) -> Two nested loops, each running N times.
     * - Space: O(N^2) Heap Space for `dp`. No stack space.
     */
    public boolean checkValidStringTabulation(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n + 1][n + 1];

        // Base Case: end of string, 0 open brackets is true.
        dp[n][0] = true;

        for (int i = n - 1; i >= 0; i--) {
            for (int count = 0; count <= n; count++) {
                char c = s.charAt(i);
                boolean isValid = false;

                if (c == '(') {
                    if (count + 1 <= n) isValid = dp[i + 1][count + 1];
                } else if (c == ')') {
                    if (count > 0) isValid = dp[i + 1][count - 1];
                } else {
                    if (count + 1 <= n) isValid |= dp[i + 1][count + 1];
                    if (count > 0) isValid |= dp[i + 1][count - 1];
                    isValid |= dp[i + 1][count];
                }

                dp[i][count] = isValid;
            }
        }
        return dp[0][0];
    }

    /**
     * ========================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" stage)
     * ========================================================================
     * INTUITION:
     * Looking at the Tabulation code, to calculate `dp[i][...]`, we strictly
     * only ever need data from `dp[i+1][...]`. We do not need the rest of the
     * matrix. We can collapse the 2D matrix into two 1D arrays: `next`
     * (representing i+1) and `curr` (representing i).
     * * * COMPLEXITY:
     * - Time: O(N^2) -> Iteration count remains the same.
     * - Space: O(N) Heap Space -> Two 1D arrays of size N+1.
     */
    public boolean checkValidStringSpaceOptimized(String s) {
        int n = s.length();
        boolean[] next = new boolean[n + 1];
        boolean[] curr = new boolean[n + 1];

        next[0] = true;

        for (int i = n - 1; i >= 0; i--) {
            for (int count = 0; count <= n; count++) {
                char c = s.charAt(i);
                boolean isValid = false;

                if (c == '(') {
                    if (count + 1 <= n) isValid = next[count + 1];
                } else if (c == ')') {
                    if (count > 0) isValid = next[count - 1];
                } else {
                    if (count + 1 <= n) isValid |= next[count + 1];
                    if (count > 0) isValid |= next[count - 1];
                    isValid |= next[count];
                }

                curr[count] = isValid;
            }
            // Swap arrays for next iteration
            next = curr.clone(); // Clone is safer here as we overwrite curr next loop
        }
        return next[0];
    }

    /**
     * ========================================================================
     * PHASE 5: ALTERNATIVE APPROACH (Greedy - The Absolute Optimal Solution)
     * ========================================================================
     * INTUITION:
     * The DP approaches show that at any index, 'count' isn't just one value,
     * but a *range* of possible open brackets due to '*'.
     * Let `cmin` be the minimum possible open brackets (can't go below 0).
     * Let `cmax` be the maximum possible open brackets.
     * - '(' increases both limits.
     * - ')' decreases both limits. (If cmax < 0, it means we have too many ')' -> false).
     * - '*' means we can decrease (treat as ')'), increase (treat as '('), or keep same.
     * At the end, if 0 is within the range [cmin, cmax], then `cmin == 0` is true.
     * * * COMPLEXITY:
     * - Time: O(N) -> Single pass through the string.
     * - Space: O(1) -> Only integer variables used.
     */
    public boolean checkValidStringGreedy(String s) {
        int cmin = 0; // Min open '(' possible
        int cmax = 0; // Max open '(' possible

        for (char c : s.toCharArray()) {
            if (c == '(') {
                cmax++;
                cmin++;
            } else if (c == ')') {
                cmax--;
                cmin--;
            } else if (c == '*') {
                cmax++; // if we treat '*' as '('
                cmin--; // if we treat '*' as ')'
            }

            // If cmax is negative, it means even if we used all '*' as '(',
            // we still have unmatched ')'. Invalid string.
            if (cmax < 0) return false;

            // cmin can't be negative. If it goes negative, it just means we
            // shouldn't have treated previous '*' as ')'. We floor it to 0.
            cmin = Math.max(cmin, 0);
        }

        return cmin == 0;
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        ValidParenthesisStringDP solver = new ValidParenthesisStringDP();

        System.out.println("=== Test Case 1: Standard Success ===");
        String s1 = "()";
        System.out.println("Recursion  : " + solver.checkValidStringRecursive(s1));
        System.out.println("Memoization: " + solver.checkValidStringMemo(s1));
        System.out.println("Tabulation : " + solver.checkValidStringTabulation(s1));
        System.out.println("Space Opt  : " + solver.checkValidStringSpaceOptimized(s1));
        System.out.println("Greedy     : " + solver.checkValidStringGreedy(s1));
        System.out.println("Expected   : true\n");

        System.out.println("=== Test Case 2: Wildcard Success ===");
        String s2 = "(*)";
        System.out.println("Greedy     : " + solver.checkValidStringGreedy(s2));
        System.out.println("Expected   : true\n");

        System.out.println("=== Test Case 3: Wildcard Multiple Closing ===");
        String s3 = "(*))";
        System.out.println("Greedy     : " + solver.checkValidStringGreedy(s3));
        System.out.println("Expected   : true\n");

        System.out.println("=== Edge Case: Invalid Early Closing ===");
        String s4 = ")(";
        System.out.println("Greedy     : " + solver.checkValidStringGreedy(s4));
        System.out.println("Expected   : false\n");

        System.out.println("=== Edge Case: All Wildcards ===");
        String s5 = "***";
        System.out.println("Greedy     : " + solver.checkValidStringGreedy(s5));
        System.out.println("Expected   : true\n");
    }
}