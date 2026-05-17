package com.questions.strivers.recursionbacktracking.hard;

import java.util.ArrayList;
import java.util.List;

public class ExpressionAddOperators {

    /*
     * Problem: Insert +, -, * between digits of num such that expression = target.
     *
     * Approach: Backtracking
     * --------------------------------------
     * - At each recursion step, choose a substring of digits as the current operand.
     * - If it's the first operand, just start with it (no operator).
     * - Otherwise, try adding '+', '-', or '*' before it.
     * - Maintain:
     *    1. 'calc' → current evaluated value of expression
     *    2. 'tail' → last operand (for handling multiplication correctly)
     * - Special case: prevent leading zeros ("05" is invalid, only "0" allowed).
     */

    public List<String> addOperators(String num, int target) {
        List<String> result = new ArrayList<>();
        if (num == null || num.length() == 0) return result;

        backtrack(result, new StringBuilder(), num, target, 0, 0, 0);
        return result;
    }

    /*
     * @param result  → final list of valid expressions
     * @param path    → current expression being built
     * @param num     → input digit string
     * @param target  → target value to reach
     * @param index   → current position in num string
     * @param calc    → evaluated value so far
     * @param tail    → last operand value (needed for multiplication adjustment)
     */
    private void backtrack(List<String> result, StringBuilder path,
                           String num, int target,
                           int index, long calc, long tail) {

        // Base case: all digits are used
        if (index == num.length()) {
            if (calc == target) {
                result.add(path.toString());
            }
            return;
        }

        // Try all possible splits of remaining substring
        for (int i = index; i < num.length(); i++) {
            // Avoid numbers with leading zeros
            if (i != index && num.charAt(index) == '0') break;

            // Current operand
            String str = num.substring(index, i + 1);
            long curr = Long.parseLong(str);
            int len = path.length();

            if (index == 0) {
                // First operand: no operator needed
                path.append(str);
                backtrack(result, path, num, target, i + 1, curr, curr);
                path.setLength(len); // backtrack
            } else {
                // Option 1: Add '+'
                path.append("+").append(str);
                backtrack(result, path, num, target, i + 1, calc + curr, curr);
                path.setLength(len);

                // Option 2: Add '-'
                path.append("-").append(str);
                backtrack(result, path, num, target, i + 1, calc - curr, -curr);
                path.setLength(len);

                // Option 3: Add '*'
                path.append("*").append(str);
                // Multiplication needs adjustment:
                // Remove last operand (tail) and replace with (tail * curr)
                backtrack(result, path, num, target, i + 1,
                        calc - tail + tail * curr, tail * curr);
                path.setLength(len);
            }
        }
    }

    // Driver code
    public static void main(String[] args) {
        ExpressionAddOperators solution = new ExpressionAddOperators();

        System.out.println(solution.addOperators("123", 6));  // [1+2+3, 1*2*3]
        System.out.println(solution.addOperators("232", 8));  // [2+3*2, 2*3+2]
        System.out.println(solution.addOperators("3456237490", 9191)); // []
    }
}

/*
Time Complexity:
Each digit can either:
Start a new operand, or
Continue the current operand.
For n = num.length, we can make O(2^(n-1)) splits.
At each split, we try 3 operators (+, -, *).
Worst-case complexity ≈ O(3^(n-1)).
Since n ≤ 10, this is feasible.

Space Complexity:
Recursion depth = O(n).
Expression string length ≤ 2n (digits + operators).
O(n) auxiliary space (ignoring result list).
Result storage can take up to O(3^(n-1)) expressions.
 */
