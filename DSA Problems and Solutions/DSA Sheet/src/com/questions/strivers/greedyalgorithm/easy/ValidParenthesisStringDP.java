package com.questions.strivers.greedyalgorithm.easy;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 678. Valid Parenthesis String (Medium)
 * ==================================================================================================
 * Given a string s containing only three types of characters: '(', ')' and '*',
 * return true if s is valid.
 * * The following rules define a valid string:
 * 1. Any left parenthesis '(' must have a corresponding right parenthesis ')'.
 * 2. Any right parenthesis ')' must have a corresponding left parenthesis '('.
 * 3. Left parenthesis '(' must go before the corresponding right parenthesis ')'.
 * 4. '*' could be treated as a single right parenthesis ')' or a single left parenthesis '('
 * or an empty string "".
 *
 * Example 1: Input: s = "()"   -> Output: true
 * Example 2: Input: s = "(*)"  -> Output: true
 * Example 3: Input: s = "(*))" -> Output: true
 * ==================================================================================================
 * APPROACH: TOP-DOWN DYNAMIC PROGRAMMING (Memoization)
 * ==================================================================================================
 * We can use recursion to explore all possible interpretations of the '*' character.
 * To avoid the exponential time complexity of pure recursion (where '*' causes 3 branches),
 * we use a 2D 'memo' array to cache our results.
 * * State Variables:
 * 1. `index`: Our current position in the string.
 * 2. `openCount`: The number of unmatched left parentheses '(' we currently have.
 * * Base Cases:
 * - If `openCount < 0`, we have too many ')' and it's invalid. Return false.
 * - If we reach the end of the string (`index == s.length()`), it's valid ONLY if `openCount == 0`.
 * * Recursive Transitions:
 * - If '(', we MUST increment openCount.
 * - If ')', we MUST decrement openCount.
 * - If '*', we branch into 3 possibilities: treat as '(', treat as ')', or treat as "".
 * If ANY of these branches return true, the current path is valid.
 * * Time Complexity: O(N^2) - We have N indices and at most N possible values for openCount.
 * Space Complexity: O(N^2) - For the 2D memoization array, plus O(N) for the recursion stack.
 * ==================================================================================================
 */
public class ValidParenthesisStringDP {

    public static void main(String[] args) {
        System.out.println("Test Case 1 '()'   (Expected: true)  -> Result: " + checkValidString("()"));
        System.out.println("Test Case 2 '(*)'  (Expected: true)  -> Result: " + checkValidString("(*)"));
        System.out.println("Test Case 3 '(*))' (Expected: true)  -> Result: " + checkValidString("(*))"));
        System.out.println("Test Case 4 ')*('  (Expected: false) -> Result: " + checkValidString(")*("));
    }

    /**
     * Wrapper method to initialize the memoization table and start recursion.
     */
    public static boolean checkValidString(String s) {
        int n = s.length();
        // memo[index][openCount]
        // We use the Boolean object wrapper so unvisited states default to 'null'
        Boolean[][] memo = new Boolean[n][n + 1];

        return isValid(s, 0, 0, memo);
    }

    /**
     * Recursive helper method with Memoization.
     * * @param s The input string.
     * @param index The current character index we are evaluating.
     * @param openCount The current balance of unmatched '(' parentheses.
     * @param memo The cache to store previously computed states.
     * @return true if the remaining string can be valid, false otherwise.
     */
    private static boolean isValid(String s, int index, int openCount, Boolean[][] memo) {
        // BASE CASE 1:
        // If at any point we have more closing brackets than open ones, the string is permanently invalid.
        // We MUST check this before accessing the memo array to prevent ArrayIndexOutOfBoundsException!
        if (openCount < 0) {
            return false;
        }

        // BASE CASE 2:
        // We have successfully processed the entire string.
        if (index == s.length()) {
            // It is only valid if all opened parentheses have been perfectly matched/closed.
            return openCount == 0;
        }

        // MEMOIZATION CHECK:
        // If we have already solved the subproblem for this specific index and openCount,
        // instantly return the cached result instead of recalculating the tree.
        if (memo[index][openCount] != null) {
            return memo[index][openCount];
        }

        char currentChar = s.charAt(index);
        boolean isValidPath = false;

        // RECURSIVE TRANSITIONS:
        if (currentChar == '(') {
            // Strictly treat as an open parenthesis
            isValidPath = isValid(s, index + 1, openCount + 1, memo);
        }
        else if (currentChar == ')') {
            // Strictly treat as a close parenthesis
            isValidPath = isValid(s, index + 1, openCount - 1, memo);
        }
        else if (currentChar == '*') {
            // The magic of the '*' character: We try ALL THREE possibilities.
            // If even a single one of these paths returns true, then 'isValidPath' becomes true.
            isValidPath =
                    isValid(s, index + 1, openCount + 1, memo) || // 1. Treat '*' as '('
                            isValid(s, index + 1, openCount - 1, memo) || // 2. Treat '*' as ')'
                            isValid(s, index + 1, openCount, memo);       // 3. Treat '*' as empty string ""
        }



        // CACHE THE RESULT:
        // Save the outcome of this state so we never have to compute it again.
        memo[index][openCount] = isValidPath;

        return isValidPath;
    }
}