package com.questions.strivers.dynamicprogramming.intro;

import java.util.Arrays;

/**
 * ==================================================================================================
 * FIBONACCI SEQUENCE - 4 APPROACHES
 * ==================================================================================================
 * * This class demonstrates the progression from a naive recursive solution to a
 * fully space-optimized Dynamic Programming solution.
 *
 * INPUT: An integer n (0 <= n <= 30)
 * OUTPUT: The nth Fibonacci number
 * ==================================================================================================
 */
public class FibonacciDP {

    public static void main(String[] args) {
        int n = 10;  // Example Input: Calculate the 10th Fibonacci number

        // ------------------ PREPARATION FOR MEMOIZATION ------------------
        // We need an array to store computed results.
        // Size is n + 1 because we need index 'n' to store F(n).
        int[] dp = new int[n + 1];

        // Initialize with -1 to indicate that no state has been computed yet.
        Arrays.fill(dp, -1);

        // ------------------ EXECUTION & OUTPUT ------------------
        System.out.println("Input n: " + n);
        System.out.println("--------------------------------------------------");

        // 1. Recursive
        long startTime = System.nanoTime();
        int recResult = basicRecursion(n);
        System.out.printf("1. Basic Recursion : %d (Time: %d ns)\n", recResult, (System.nanoTime() - startTime));

        // 2. Memoization
        startTime = System.nanoTime();
        // Note: We pass the pre-filled dp array
        int memoResult = fibMemoization(n, dp);
        System.out.printf("2. Memoization     : %d (Time: %d ns)\n", memoResult, (System.nanoTime() - startTime));

        // 3. Tabulation
        // Resetting DP array isn't strictly necessary for correctness here
        // since we overwrite, but good for clarity.
        int[] table = new int[n + 1];
        startTime = System.nanoTime();
        int tabResult = fibTabulation(n, table);
        System.out.printf("3. Tabulation      : %d (Time: %d ns)\n", tabResult, (System.nanoTime() - startTime));

        // 4. Space Optimization
        startTime = System.nanoTime();
        int optResult = fibSpaceOptimized(n);
        System.out.printf("4. Space Optimized : %d (Time: %d ns)\n", optResult, (System.nanoTime() - startTime));
    }

    /**
     * ==============================================================================================
     * APPROACH 1: BASIC RECURSION (BRUTE FORCE)
     * ==============================================================================================
     * * LOGIC: Directly implements the mathematical formula F(n) = F(n-1) + F(n-2).
     * * WHY IT'S BAD: It re-calculates the same sub-problems many times.
     * E.g., to calc F(5), it calcs F(3) twice. To calc F(6), it calcs F(3) three times.
     * * COMPLEXITY:
     * - Time: O(2^n) -> Exponential. Very slow for n > 40.
     * - Space: O(n) -> Stack space for recursion depth.
     */
    private static int basicRecursion(int n) {
        // Base Case: F(0)=0, F(1)=1
        if (n <= 1) return n;

        // Recursive Step
        return basicRecursion(n - 1) + basicRecursion(n - 2);
    }

    /**
     * ==============================================================================================
     * APPROACH 2: MEMOIZATION (TOP-DOWN DYNAMIC PROGRAMMING)
     * ==============================================================================================
     * * LOGIC: Before making a recursive call, check if the value is already in our 'dp' array.
     * If it is, return it. If not, compute it, SAVE it in 'dp', and then return.
     * * "Top-Down" because we start at 'n' and break it down to 0.
     * * COMPLEXITY:
     * - Time: O(n) -> We solve each sub-problem (0 to n) exactly once.
     * - Space: O(n) + O(n) -> Array space + Recursion Stack space.
     */
    private static int fibMemoization(int n, int[] dp) {
        // Base Case
        if (n <= 1) return n;

        // Step 1: Check Cache (Memoization)
        if (dp[n] != -1) {
            return dp[n];
        }

        // Step 2: Compute and Store
        // We compute the value and save it in dp[n] before returning
        dp[n] = fibMemoization(n - 1, dp) + fibMemoization(n - 2, dp);

        return dp[n];
    }

    /**
     * ==============================================================================================
     * APPROACH 3: TABULATION (BOTTOM-UP DYNAMIC PROGRAMMING)
     * ==============================================================================================
     * * LOGIC: We iterate starting from the base cases (0 and 1) up to 'n'.
     * We fill the table sequentially. No recursion overhead.
     * * "Bottom-Up" because we start at 0 and build up to 'n'.
     * * COMPLEXITY:
     * - Time: O(n) -> Simple for-loop.
     * - Space: O(n) -> Array to store values.
     */
    private static int fibTabulation(int n, int[] dp) {
        // Edge Case: Handling n=0 specifically avoids ArrayIndexOutOfBounds for dp[1]
        if (n <= 1) return n;

        // Step 1: Initialize Base Cases
        dp[0] = 0;
        dp[1] = 1;

        // Step 2: Fill the table iteratively
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        // Step 3: Return the final state
        return dp[n];
    }

    /**
     * ==============================================================================================
     * APPROACH 4: SPACE OPTIMIZATION (CONSTANT SPACE)
     * ==============================================================================================
     * * LOGIC: To calculate F(i), we ONLY need F(i-1) and F(i-2).
     * We don't need to store the entire history (F(0)...F(i-3)).
     * We can maintain just two variables: 'prev' and 'prev2'.
     * * [prev2] [prev] [current]
     * i-2    i-1      i
     * * COMPLEXITY:
     * - Time: O(n) -> Simple for-loop.
     * - Space: O(1) -> Only uses constant extra memory. (Best Solution)
     */
    private static int fibSpaceOptimized(int n) {
        // Base Case
        if (n <= 1) return n;

        int prev2 = 0; // Represents F(i-2), initially F(0)
        int prev = 1;  // Represents F(i-1), initially F(1)

        for (int i = 2; i <= n; i++) {
            int current = prev + prev2; // Calculate F(i)

            // Shift the window for the next iteration
            prev2 = prev; // F(i-2) becomes old F(i-1)
            prev = current; // F(i-1) becomes the current F(i)
        }

        return prev; // prev now holds F(n)
    }
}