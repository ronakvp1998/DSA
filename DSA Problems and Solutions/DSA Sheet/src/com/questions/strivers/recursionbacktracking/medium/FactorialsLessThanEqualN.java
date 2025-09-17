package com.questions.strivers.recursionbacktracking.medium;

import java.util.ArrayList;
import java.util.List;

/**
 * Problem Statement:
 * ------------------
 * Given a number n, generate all factorial numbers that are less than or equal to n.
 * A factorial number is of the form k! (1! = 1, 2! = 2, 3! = 6, ...).
 *
 * Example:
 * Input:  n = 6
 * Output: [1, 2, 6]
 * Explanation: Factorials are 1! = 1, 2! = 2, 3! = 6. Since 4! = 24 > 6, stop.
 *
 * ------------------------------------------------------------
 * Code Logic:
 * 1. Start with res = 1 (which represents 1!).
 * 2. Recursively compute factorials:
 *      - If current factorial res <= n, add it to the list.
 *      - Multiply res by i (to get next factorial i!) and call recursion for i+1.
 * 3. Stop recursion when res > n (base case).
 *
 * ------------------------------------------------------------
 * Time Complexity:
 * - Factorials grow very fast.
 * - The recursion runs until k! > n.
 * - Number of recursive calls ≈ O(log n) (because factorial grows faster than exponential).
 *
 * Space Complexity:
 * - O(k) recursion depth where k! ≤ n.
 * - Result list stores all valid factorials (at most O(log n) elements).
 * - Overall space = O(log n).
 */

public class FactorialsLessThanEqualN {

    public static void main(String[] args) {
        long n = 6;
        System.out.println(factorialNumbers(n));
    }

    /**
     * Function to generate factorial numbers <= n
     *
     * @param n upper bound
     * @return list of factorial numbers
     */
    public static ArrayList<Long> factorialNumbers(long n) {
        ArrayList<Long> list = new ArrayList<>();
        fact(n, list, 1, 2); // start with 1! = 1 and multiplier i = 2
        return list;
    }

    /**
     * Recursive helper function
     *
     * @param n    upper bound
     * @param list result list
     * @param res  current factorial value
     * @param i    next multiplier (for computing i!)
     */
    public static void fact(long n, List<Long> list, long res, long i) {
        // Base case: stop if factorial exceeds n
        if (res > n) {
            return;
        }

        // Add current factorial to result
        list.add(res);

        // Recurse with next factorial (res * i) and increment multiplier
        fact(n, list, res * i, i + 1);
    }
}
