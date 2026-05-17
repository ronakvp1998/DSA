package com.questions.strivers.bitmanipulation.advancemaths;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: Print all Divisors of a given Number
 * ==================================================================================================
 * Given an integer N, return all divisors of N.
 * A divisor is a positive integer that divides N without leaving a remainder (N % i == 0).
 *
 * Example 1:
 * Input: N = 36
 * Output: [1, 2, 3, 4, 6, 9, 12, 18, 36]
 *
 * Example 2:
 * Input: N = 12
 * Output: [1, 2, 3, 4, 6, 12]
 * ==================================================================================================
 * APPROACH 1: BRUTE FORCE
 * ==================================================================================================
 * Iterate through every number from 1 to N. If N is divisible by the current number,
 * it is a divisor.
 * ==================================================================================================
 * APPROACH 2: OPTIMAL (Square Root Method)
 * ==================================================================================================
 * Divisors always occur in pairs. For a number N, if 'i' is a divisor, then 'N/i'
 * is also a divisor.
 * Example for 36: (1, 36), (2, 18), (3, 12), (4, 9), (6, 6).
 * By iterating only up to the square root of N, we can find all divisors efficiently.
 * ==================================================================================================
 */

import java.util.*;

public class AllDivisors {

    public static void main(String[] args) {
        int n = 36;

        System.out.println("Divisors of " + n + " (Brute Force):");
        System.out.println(findDivisorsBruteForce(n));

        System.out.println("\nDivisors of " + n + " (Optimal):");
        System.out.println(findDivisorsOptimal(n));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: BRUTE FORCE
     * ----------------------------------------------------------------------
     * Logic: Check every number from 1 to N.
     */
    public static List<Integer> findDivisorsBruteForce(int n) {
        List<Integer> divisors = new ArrayList<>();

        // Loop from 1 to N
        for (int i = 1; i <= n; i++) {
            // Check if i divides n perfectly
            if (n % i == 0) {
                divisors.add(i);
            }
        }
        return divisors;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: OPTIMAL (O(sqrt(N)))
     * ----------------------------------------------------------------------
     * Logic:
     * 1. Iterate from 1 to sqrt(N).
     * 2. If i is a divisor, add i to the list.
     * 3. Also add (n/i) to the list if (n/i) is not the same as i.
     * 4. Sort the list at the end for clean output.
     */
    public static List<Integer> findDivisorsOptimal(int n) {
        List<Integer> divisors = new ArrayList<>();

        // Loop from 1 to sqrt(n)
        for (int i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                // Add the first divisor of the pair
                divisors.add(i);

                // Add the second divisor of the pair (n/i)
                // Avoid adding the same number twice for perfect squares (like 6*6 = 36)
                if (i != n / i) {
                    divisors.add(n / i);
                }
            }
        }

        // Sort the list as finding pairs results in unsorted order
        Collections.sort(divisors);
        return divisors;
    }
}