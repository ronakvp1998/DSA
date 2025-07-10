package com.questions.practice.dynamicprogramming;

import java.util.Arrays;

public class FrogDP {
    public static void main(String[] args) {
        // Define the height of each stair
        int arr[] = {30, 10, 60, 10, 60, 50};
        int n = arr.length;

        // ****************** Approach 1: Pure Recursion ******************
        // Simple recursive function without any optimization
        // Time complexity: O(2^n), Space: O(n) (recursion stack)
         System.out.println(recursive(n - 1, arr));

        // ****************** Approach 2: Memoization ******************
        // Initialize the DP array to store computed subproblems
        int dp[] = new int[n];
        Arrays.fill(dp, -1); // fill with -1 indicating uncomputed
        System.out.println("Memoization: " + memorization(n - 1, arr, dp));

        // ****************** Approach 3: Tabulation (Bottom-Up DP) ******************
        // Reuse the same dp[] by re-filling it
        Arrays.fill(dp, -1); // again fill with -1 before use
        System.out.println("Tabulation: " + tabulation(n, arr, dp));

        // ****************** Approach 4: Space Optimized DP ******************
        System.out.println("Space Optimized: " + spaceOptimized(n, arr));
    }

    private static int recursive(int i, int[] arr) {
        if(i == 0 ){
            return 0;
        }
        int left = recursive(i-1,arr) + Math.abs(arr[i] - arr[i-1]);
        int right = Integer.MAX_VALUE;
        if(i > 1){
            right = recursive(i-2,arr) + Math.abs(arr[i] - arr[i-2]);
        }
        return Math.min(left,right);
    }

    private static int  spaceOptimized(int n, int[] arr) {
        int prev1 = 0, prev2 = 0;
        for(int i=1;i<arr.length;i++){
            int jumpOne = prev1 + Math.abs(arr[i] - arr[i-1]);
            int jumpTwo = Integer.MAX_VALUE;
            if(i > 1){
                jumpTwo = prev2 + Math.abs(arr[i] - arr[i-2]);
            }
            int curr = Math.min(jumpOne,jumpTwo);
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }
//
    private static int tabulation(int n, int[] arr, int[] dp) {
        dp[0] = 0;
        for(int i=1;i<n;i++){
            int jumpOne = dp[i-1] + Math.abs( arr[i] - arr[i-1]);
            int jumpTwo = Integer.MAX_VALUE;
            if(i > 1){
                jumpTwo = dp[i-2] + Math.abs(arr[i] - arr[i-2]);
            }
            dp[i] = Math.min(jumpOne,jumpTwo);
        }
        return dp[n-1];
    }
//
    private static int memorization(int i, int[] arr, int[] dp) {
        if(i == 0){
            return 0;
        }
        if(dp[i] != -1){
            return dp[i];
        }
        int left = memorization(i-1,arr,dp) + Math.abs(arr[i] - arr[i-1]);
        int right = Integer.MAX_VALUE;
        if( i > 1){
            right = memorization(i-2,arr,dp) + Math.abs(arr[i] - arr[i-2]);
        }
        return dp[i] = Math.min(left,right);
    }
}
