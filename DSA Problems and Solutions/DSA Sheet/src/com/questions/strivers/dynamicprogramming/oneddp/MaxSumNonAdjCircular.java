package com.questions.strivers.dynamicprogramming.oneddp;

import java.util.Arrays;

// Problem Link: https://takeuforward.org/data-structure/dynamic-programming-house-robber-dp-6/

// Problem:
// You are given a circular array where each element represents the amount of money in a house.
// You cannot rob two adjacent houses, and the first and last houses are also considered adjacent.
// The task is to find the maximum amount of money you can rob without robbing two adjacent houses.

public class MaxSumNonAdjCircular {
    public static void main(String[] args) {
        int arr[] = {2, 3, 2};
        int n = arr.length;

        // Approach 1: Recursive solution (brute force)
        System.out.println("Recursive: " + houseRobberRecursive(arr));

        // Approach 2: Memoization (Top-down dynamic programming)
        System.out.println("Memoization: " + houseRobberMemoization(arr));

        // Approach 3: Tabulation (Bottom-up dynamic programming)
        System.out.println("Tabulation: " + houseRobberTabulation(arr));

        // Approach 4: Space Optimized dynamic programming
        System.out.println("Space Optimized: " + houseRobberSpaceOptimized(arr));
    }

    // Recursive solution for circular array
    private static int houseRobberRecursive(int[] arr) {
        int n = arr.length;

        // If there's only one house, return its value
        if (n == 1) {
            return arr[0];
        }

        // Case 1: Exclude the first house and solve for the rest
        int excludeFirst = recursive(arr, 1, n - 1);

        // Case 2: Exclude the last house and solve from index 0 to n-2
        int excludeLast = recursive(arr, 0, n - 2);

        // Return the maximum of both cases
        return Math.max(excludeFirst, excludeLast);
    }

    // Recursive helper for linear version of house robber
    private static int recursive(int[] arr, int start, int end) {
        // If the end index is before start, no houses left to rob
        if (end < start) {
            return 0;
        }

        // If there's only one house in the range, return its value
        if (end == start) {
            return arr[start];
        }

        // Option 1: Include the current house and move two steps back
        int include = arr[end] + recursive(arr, start, end - 2);

        // Option 2: Skip the current house and move one step back
        int exclude = recursive(arr, start, end - 1);

        // Return the maximum value of the two options
        return Math.max(include, exclude);
    }

    // Memoization approach for circular house robber
    private static int houseRobberMemoization(int[] arr) {
        int n = arr.length;

        // If there's only one house, return its value
        if (n == 1) {
            return arr[0];
        }

        // Create two dp arrays for two subproblems (excluding first and last)
        int[] dp1 = new int[n]; // for range 1 to n-1
        int[] dp2 = new int[n]; // for range 0 to n-2
        Arrays.fill(dp1, -1);
        Arrays.fill(dp2, -1);

        // Solve excluding first house
        int excludeFirst = memoization(arr, 1, n - 1, dp1);

        // Solve excluding last house
        int excludeLast = memoization(arr, 0, n - 2, dp2);

        // Return the maximum value of both cases
        return Math.max(excludeFirst, excludeLast);
    }

    // Memoized helper for linear house robber problem
    private static int memoization(int[] arr, int start, int end, int[] dp) {
        // If end index is before start, return 0
        if (end < start) {
            return 0;
        }

        // If only one house left
        if (end == start) {
            return arr[start];
        }

        // If already computed, return saved value
        if (dp[end] != -1) {
            return dp[end];
        }

        // Include current house and skip previous
        int include = arr[end] + memoization(arr, start, end - 2, dp);

        // Exclude current house and take previous
        int exclude = memoization(arr, start, end - 1, dp);

        // Save result and return
        return dp[end] = Math.max(include, exclude);
    }

    // Tabulation approach for circular house robber
    private static int houseRobberTabulation(int[] arr) {
        int n = arr.length;

        // If there's only one house, return its value
        if (n == 1) {
            return arr[0];
        }

        // Solve two cases and return max
        int excludeFirst = tabulation(arr, 1, n - 1);
        int excludeLast = tabulation(arr, 0, n - 2);

        return Math.max(excludeFirst, excludeLast);
    }

    // Tabulated helper for linear house robber
    private static int tabulation(int[] arr, int start, int end) {
        int n = end - start + 1; // size of subarray

        int[] dp = new int[n]; // dp[i] represents max sum from start to (start + i)
        dp[0] = arr[start]; // base case for first element

        for (int i = 1; i < n; i++) {
            int include = arr[start + i]; // current house value

            // If we can go two steps back, include dp[i - 2]
            if (i > 1) {
                include += dp[i - 2];
            }

            // Exclude current house
            int exclude = dp[i - 1];

            // Take the maximum of include and exclude
            dp[i] = Math.max(include, exclude);
        }

        // Return result stored at the last index
        return dp[n - 1];
    }

    // Space optimized version of house robber for circular array
    private static int houseRobberSpaceOptimized(int[] arr) {
        int n = arr.length;

        // If there's only one house, return its value
        if (n == 1) {
            return arr[0];
        }

        // Solve two cases with space optimization
        int excludeFirst = spaceOptimized(arr, 1, n - 1);
        int excludeLast = spaceOptimized(arr, 0, n - 2);

        return Math.max(excludeFirst, excludeLast);
    }

    // Space optimized linear house robber
    private static int spaceOptimized(int[] arr, int start, int end) {
        int prev1 = 0; // dp[i - 1]
        int prev2 = 0; // dp[i - 2]

        for (int i = start; i <= end; i++) {
            // Include current house + dp[i - 2]
            int include = arr[i] + prev2;

            // Exclude current house = dp[i - 1]
            int exclude = prev1;

            // Current max value
            int curr = Math.max(include, exclude);

            // Update prev2 and prev1
            prev2 = prev1;
            prev1 = curr;
        }

        // Final result is in prev1
        return prev1;
    }
}
