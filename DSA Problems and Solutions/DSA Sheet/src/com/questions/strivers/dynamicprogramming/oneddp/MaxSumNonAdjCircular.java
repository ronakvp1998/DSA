package com.questions.strivers.dynamicprogramming.oneddp;

import java.util.Arrays;

//https://takeuforward.org/data-structure/dynamic-programming-house-robber-dp-6/
public class MaxSumNonAdjCircular {
    public static void main(String[] args) {
        int arr[] = {2, 3, 2};
        int n = arr.length;

        // Recursive approach
        System.out.println("Recursive: " + houseRobberRecursive(arr));

        // Memoization approach
        System.out.println("Memoization: " + houseRobberMemoization(arr));

        // Tabulation approach
        System.out.println("Tabulation: " + houseRobberTabulation(arr));

        // Space-optimized approach
        System.out.println("Space Optimized: " + houseRobberSpaceOptimized(arr));
    }

    public static int houseRobberRecursive(int[] arr) {
        int n = arr.length;
        if (n == 1) {
            return arr[0];
        }
        int excludeFirst = recursive(arr, 1, n - 1); // Exclude first house
        int excludeLast = recursive(arr, 0, n - 2); // Exclude last house
        return Math.max(excludeFirst, excludeLast);
    }

    public static int recursive(int[] arr, int start, int end) {
        if (end < start) {
            return 0;
        }
        if (end == start) {
            return arr[start];
        }
        int include = arr[end] + recursive(arr, start, end - 2); // Include current house
        int exclude = recursive(arr, start, end - 1); // Exclude current house
        return Math.max(include, exclude);
    }

    public static int houseRobberMemoization(int[] arr) {
        int n = arr.length;
        if (n == 1) {
            return arr[0];
        }
        int[] dp1 = new int[n];
        int[] dp2 = new int[n];
        Arrays.fill(dp1, -1);
        Arrays.fill(dp2, -1);
        int excludeFirst = memoization(arr, 1, n - 1, dp1); // Exclude first house
        int excludeLast = memoization(arr, 0, n - 2, dp2); // Exclude last house
        return Math.max(excludeFirst, excludeLast);
    }

    public static int memoization(int[] arr, int start, int end, int[] dp) {
        if (end < start) {
            return 0;
        }
        if (end == start) {
            return arr[start];
        }
        if (dp[end] != -1) {
            return dp[end];
        }
        int include = arr[end] + memoization(arr, start, end - 2, dp); // Include current house
        int exclude = memoization(arr, start, end - 1, dp); // Exclude current house
        return dp[end] = Math.max(include, exclude);
    }

    public static int houseRobberTabulation(int[] arr) {
        int n = arr.length;
        if (n == 1) {
            return arr[0];
        }
        int excludeFirst = tabulation(arr, 1, n - 1); // Exclude first house
        int excludeLast = tabulation(arr, 0, n - 2); // Exclude last house
        return Math.max(excludeFirst, excludeLast);
    }

    public static int tabulation(int[] arr, int start, int end) {
        int n = end - start + 1;
        int[] dp = new int[n];
        dp[0] = arr[start];
        for (int i = 1; i < n; i++) {
            int include = arr[start + i];
            if (i > 1) {
                include += dp[i - 2];
            }
            int exclude = dp[i - 1];
            dp[i] = Math.max(include, exclude);
        }
        return dp[n - 1];
    }

    public static int houseRobberSpaceOptimized(int[] arr) {
        int n = arr.length;
        if (n == 1) {
            return arr[0];
        }
        int excludeFirst = spaceOptimized(arr, 1, n - 1); // Exclude first house
        int excludeLast = spaceOptimized(arr, 0, n - 2); // Exclude last house
        return Math.max(excludeFirst, excludeLast);
    }

    public static int spaceOptimized(int[] arr, int start, int end) {
        int prev1 = 0, prev2 = 0;
        for (int i = start; i <= end; i++) {
            int include = arr[i] + prev2; // Include current house
            int exclude = prev1; // Exclude current house
            int curr = Math.max(include, exclude);
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }
}

