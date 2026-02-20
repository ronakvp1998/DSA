package com.questions.strivers.bitmanipulation.advancemaths;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 204. Count Primes (Medium)
 * ==================================================================================================
 * Given an integer n, return the number of prime numbers that are strictly less than n.
 *
 * Example 1:
 * Input: n = 10 -> Output: 4 (Primes: 2, 3, 5, 7)
 *
 * Example 2:
 * Input: n = 0 -> Output: 0
 *
 * Constraints:
 * 0 <= n <= 5 * 10^6
 * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (Trial Division)
 * ==================================================================================================
 * Iterate through every number from 2 to n-1. For each number, check if it is prime by
 * dividing it by all integers from 2 up to sqrt(i).
 * Time Complexity: O(n * sqrt(n)). This will result in a TLE (Time Limit Exceeded).
 * ==================================================================================================
 * APPROACH 2: OPTIMAL (Sieve of Eratosthenes)
 * ==================================================================================================
 * Instead of checking each number individually, we "sieve" out multiples of primes.
 * 1. Create a boolean array 'isPrime' of size n, initialized to true.
 * 2. Start from p = 2 (the first prime). If isPrime[p] is true, mark all its
 * multiples (p*p, p*p + p, ...) as false.
 * 3. We only need to iterate p up to sqrt(n).
 * 4. Count the remaining 'true' values in the array.
 * ==================================================================================================
 */

import java.util.Arrays;

public class CountPrimes {

    public static void main(String[] args) {
        int n = 10;
        System.out.println("Input: " + n);
        System.out.println("Brute Force Count: " + countPrimesBruteForce(n));
        System.out.println("Optimal Sieve Count: " + countPrimesOptimal(n));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: BRUTE FORCE (Trial Division)
     * ----------------------------------------------------------------------
     */
    public static int countPrimesBruteForce(int n) {
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime(i)) count++;
        }
        return count;
    }

    private static boolean isPrime(int num) {
        if (num <= 1) return false;
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: OPTIMAL (Sieve of Eratosthenes)
     * ----------------------------------------------------------------------
     * Logic:
     * - We mark multiples of each prime starting from its square.
     * - Optimization: We stop the outer loop at sqrt(n) because any composite
     * number smaller than n must have a prime factor less than or equal to sqrt(n).
     */
    public static int countPrimesOptimal(int n) {
        // According to the problem, count primes STRICTLY less than n.
        if (n <= 2) return 0;

        boolean[] isPrime = new boolean[n];
        // Initialize all to true
        Arrays.fill(isPrime, true);

        // 0 and 1 are not prime
        isPrime[0] = false;
        isPrime[1] = false;

        // Start sieving from the first prime, 2.
        for (int p = 2; p * p < n; p++) {
            if (isPrime[p]) {
                // Mark all multiples of p starting from p*p as not prime.
                // We start at p*p because smaller multiples like 2p, 3p...
                // would have already been marked by smaller primes 2, 3...
                for (int multiple = p * p; multiple < n; multiple += p) {
                    isPrime[multiple] = false;
                }
            }
        }

        // Count the number of primes
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime[i]) count++;
        }

        return count;
    }
}