package com.questions.strivers.dynamicprogramming.oneddp;

import java.util.Arrays;

public class MaxSumNonAdj {
    public static void main(String[] args) {
        // recursive approach
        int arr[] = {2, 4, 6, 2, 5};
        int n = arr.length;
        System.out.println(recursive(n - 1, arr));
        // memorization approach
        int dp[] = new int[n];
        Arrays.fill(dp, -1);
        System.out.println(memorization(n - 1, arr,dp));
        // Tabulation
        System.out.println(tabulation(n, arr, dp));
    }

    public static int spaceOptimized(int n, int arr[]) {
        int prev1 = arr[0]; // This is the max sum including the first element
        int prev2 = 0; // This is the max sum excluding the first element
        for (int i = 0; i < n; i++) {
            int include = arr[i];
            if (i > 1) {
                include += prev2; // Include current and add previous non-adjacent
            }
            int exclude = prev1; // Exclude current, take previous max
            int curr = Math.max(include, exclude); // Current max sum
            prev2 = prev1; // Update previous non-adjacent
            prev1 = curr; // Update previous max
        }
        // answer is at n-1 or prev1
        return prev1;
    }

    public static int tabulation(int n, int arr[], int [] dp){
        dp[0] = arr[0];
        for (int i = 1; i < n; i++) {
            // include the current element skip the next element
            int include = arr[i];
            if (i > 1) {
                include += dp[i - 2];
            }
            // exclude the current element
            int exclude = 0 +  dp[i - 1];
            dp[i] = Math.max(include, exclude);
        }
        return dp[n - 1];

    }

    public static int memorization(int n, int arr[],int []dp) {
        if (n < 0) {
            return 0;
        }
        if (n == 0) {
            return arr[0];
        }
        if(dp[n] != -1) {
            return dp[n];
        }
        // include the current element skip the next element
        int include = arr[n] + memorization(n - 2, arr,dp);
        // exclude the current element
        int exclude = 0 + memorization(n - 1, arr,dp);
        return dp[n] = Math.max(include, exclude);
    }

    public static int recursive(int n, int arr[]){
        if (n < 0) {
            return 0;
        }
        if (n == 0) {
            return arr[0];
        }
        // include the current element skip the next element
        int include = arr[n] + recursive(n - 2, arr);
        // exclude the current element
        int exclude = 0 + recursive(n - 1, arr);
        return Math.max(include, exclude);
    }
}
