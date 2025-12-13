package com.questions.practice.dynamicprogramming;

import java.util.Arrays;

public class ForgkDP {
    public static void main(String[] args) {
        int arr[] = {30, 10, 60, 10, 60, 50};
        int n = arr.length;
        int k = 4;

        // ****************** Approach 1: Pure Recursion ******************
        // Simple recursive function without any optimization
        // Time complexity: O(2^n), Space: O(n) (recursion stack)
        System.out.println(recursive(n - 1, arr,k));

        // ****************** Approach 2: Memoization ******************
        // Initialize the DP array to store computed subproblems
        int dp[] = new int[n];
        Arrays.fill(dp, -1); // fill with -1 indicating uncomputed
        System.out.println("Memoization: " + memorization(n - 1, arr, dp,k));

        // ****************** Approach 3: Tabulation (Bottom-Up DP) ******************
        // Reuse the same dp[] by re-filling it
        Arrays.fill(dp, -1); // again fill with -1 before use
        System.out.println("Tabulation: " + tabulation(n, arr, dp,k));

        // ****************** Approach 4: Space Optimized DP ******************
//        System.out.println("Space Optimized: " + spaceOptimization(n, arr));
    }

//    private static int spaceOptimization(int n,int arr[],int k){
//        int dp[] = new int [k];
//        dp[0] = 0;
//        for(int i=1;i<n;i++){
//            int minCost = Integer.MAX_VALUE;
//            for(int j=1;j<=k;j++){
//                if(i-j >= 0){
//                    int cost = dp[(i-j) %k ] +
//                }
//            }
//        }
//    }

    private static int tabulation(int n, int arr[], int dp[],int k){
        dp[0] = 0;
        for(int i=1;i<n;i++){
            int minCost = Integer.MAX_VALUE;
            for(int j=1;j<=k;j++){
                if(i - j >= 0){
                    int cost = dp[i-1] + Math.abs(arr[i] - arr[i-j]);
                    minCost = Math.min(minCost,cost);
                }
            }
            dp[i] = minCost;
        }
        return dp[n-1];
    }

    private static int recursive(int n, int[] arr,int k) {
        if(n == 0){
            return 0;
        }
        int minCost = Integer.MAX_VALUE;
        for(int i=1;i<=k;i++){
            if(n-i >= 0){
                int jumpCost = recursive(n - i, arr, k) + Math.abs(arr[n - i] - arr[n]);
                minCost = Math.min(jumpCost,minCost);
            }
        }
        return minCost;
    }

    private static int memorization(int n,int arr[],int []dp, int k){
        if(n == 0){
            return 0;
        }
        if(dp[n] != -1){
            return dp[n];
        }
        int minCost = Integer.MAX_VALUE;
        for(int j=1;j<=k;j++){
            if(n-j >= 0){
                int jumpCost = memorization(n-j,arr,dp,k) + Math.abs(arr[n-j] - arr[n]) ;
                minCost = Math.min(jumpCost,minCost);
            }
        }
        dp[n] = minCost;
        return minCost;
    }
}
