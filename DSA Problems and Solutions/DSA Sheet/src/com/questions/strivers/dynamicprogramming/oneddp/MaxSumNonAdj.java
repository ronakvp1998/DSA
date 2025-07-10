package com.questions.strivers.dynamicprogramming.oneddp;

import java.util.Arrays;

// 🔗 Problem Link: https://takeuforward.org/data-structure/maximum-sum-of-non-adjacent-elements-dp-5/

/*
🎯 Problem Statement:
Given an array of integers, return the **maximum sum** of non-adjacent elements.
You are not allowed to pick two adjacent elements.
This is a variation of the "House Robber Problem".
*/

public class MaxSumNonAdj {
    public static void main(String[] args) {
        int arr[] = {2, 4, 6, 2, 5};
        int n = arr.length;

        // 1️⃣ Recursive Approach - No memoization (pure brute force)
        // Time: O(2^n), Space: O(n) (recursion stack)
        System.out.println("Recursive: " + recursive(n - 1, arr));

        // 2️⃣ Memoization (Top-down DP)
        // Time: O(n), Space: O(n) (dp[] + recursion stack)
        int dp[] = new int[n];
        Arrays.fill(dp, -1); // Initialize dp[] with -1 (indicates not computed)
        System.out.println("Memoization: " + memorization(n - 1, arr, dp));

        // 3️⃣ Tabulation (Bottom-up DP)
        // Time: O(n), Space: O(n)
        System.out.println("Tabulation: " + tabulation(n, arr, dp));

        // 4️⃣ Space Optimized DP
        // Time: O(n), Space: O(1)
        System.out.println("Space Optimized: " + spaceOptimized(n, arr));
    }

    // ✅ 1. Recursive solution (without memoization)
    private static int recursive(int n, int[] arr) {
        // 🔁 Base case 1: index goes below 0 — no elements to pick
        if (n < 0) return 0;

        // 🛑 Base case 2: only one element — max sum is the first element itself
        if (n == 0) return arr[0];

        // ✔ Option 1: Pick current element → move 2 steps back (non-adjacent)
        int include = arr[n] + recursive(n - 2, arr);

        // ❌ Option 2: Do not pick → move 1 step back (try next)
        int exclude = recursive(n - 1, arr);

        // 🔎 Return the max of both choices
        return Math.max(include, exclude);
    }

    // ✅ 2. Memoization (Top-down DP)
    private static int memorization(int n, int[] arr, int[] dp) {
        // 🔁 Base case 1
        if (n < 0) return 0;

        // 🔁 Base case 2
        if (n == 0) return arr[0];

        // 📦 If already computed, return stored result
        if (dp[n] != -1) return dp[n];

        // ✔ Option 1: Pick current and add value from n-2
        int include = arr[n] + memorization(n - 2, arr, dp);

        // ❌ Option 2: Skip current, check value at n-1
        int exclude = memorization(n - 1, arr, dp);

        // 💾 Store result in dp[] and return
        return dp[n] = Math.max(include, exclude);
    }

    // ✅ 3. Tabulation (Bottom-up DP)
    private static int tabulation(int n, int[] arr, int[] dp) {
        dp[0] = arr[0]; // 🟢 Base case: max sum at index 0 is arr[0]

        for (int i = 1; i < n; i++) {
            // ✔ Option 1: include current element
            int include = arr[i];
            if (i > 1) {
                // Only valid if i-2 >= 0
                include += dp[i - 2];
            }

            // ❌ Option 2: exclude current element (take previous value)
            int exclude = dp[i - 1];

            // 📦 Store the best of include/exclude at dp[i]
            dp[i] = Math.max(include, exclude);
        }

        return dp[n - 1]; // ✅ Final result stored in dp[n-1]
    }

    // ✅ 4. Space Optimized Version (Using two variables)
    private static int spaceOptimized(int n, int[] arr) {
        if (n == 0) return arr[0];

        // 🧠 prev1: max sum till i-1 (initially arr[0])
        // 🧠 prev2: max sum till i-2 (initially 0, since no element before 0)
        int prev1 = arr[0];
        int prev2 = 0;

        for (int i = 1; i < n; i++) {
            // ✔ Option 1: Pick current element
            int include = arr[i];
            if (i > 1) {
                include += prev2; // add sum from non-adjacent (i-2)
            }

            // ❌ Option 2: Exclude current element
            int exclude = prev1; // take the previous max

            // 🧮 Current max sum is the better of include/exclude
            int curr = Math.max(include, exclude);

            // 🔄 Update prev2 and prev1 for next iteration
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1; // 🔚 Final answer after loop completes
    }
}
