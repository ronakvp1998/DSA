package com.questions.strivers.dynamicprogramming.oneddp;

import java.util.Arrays;

//https://takeuforward.org/data-structure/dynamic-programming-frog-jump-with-k-distances-dp-4/
public class FrogJumpKDP {
    public static void main(String[] args) {

        // normal recursion
        int arr[] = {30,10,60,10,60,50};
        int n = 6,k=4;
        System.out.println(recursive(n-1,arr,k));
        // memorazation
        int dp[] = new int[n];
        Arrays.fill(dp,-1);
        System.out.println(memorization(n-1,arr,dp,k));

        // tabulation
        Arrays.fill(dp,-1);
        System.out.println(tabulation(n,arr,k));
    }

    public static int tabulation(int n,int arr[],int k){
        int dp[] = new int[n];
        dp[0] = 0;
        for (int i = 1; i < n; i++) {
            int minCost = Integer.MAX_VALUE;
            for (int j = 1; j <= k; j++) {
                if (i - j >= 0) {
                    int cost = dp[i - j] + Math.abs(arr[i] - arr[i - j]);
                    minCost = Math.min(minCost, cost);
                }
            }
            dp[i] = minCost;
        }
        return dp[n - 1];
    }

    public static int memorization(int n, int arr[],int []dp,int k){
        if(n == 0){
            return 0;
        }
        if(dp[n] != -1){
            return dp[n];
        }
        int minCost = Integer.MAX_VALUE;
        for(int j=1;j<=k;j++){
            if(n-j >= 0){
                int jumpCost = memorization(n-j,arr,dp,k) + Math.abs(arr[n] - arr[n-j]);
                minCost = Math.min(minCost, jumpCost);
            }
        }
        return dp[n] = minCost;
    }

    public static int recursive(int n, int [] arr,int k){
        if(n == 0){
            return 0;
        }
        int minCost = Integer.MAX_VALUE;
        for(int j=1;j<=k;j++){
            if(n-j >= 0){
                int jumpCost = recursive(n-j, arr, k) + Math.abs(arr[n] - arr[n-j]);
                minCost = Math.min(minCost, jumpCost);
            }
        }
        return minCost;
    }

}
