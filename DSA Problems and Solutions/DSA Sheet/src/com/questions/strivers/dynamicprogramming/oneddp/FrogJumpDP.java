package com.questions.strivers.dynamicprogramming.oneddp;

import java.util.Arrays;

public class FrogJumpDP {
    public static void main(String[] args) {

        // normal recursion
        int arr[] = {30,10,60,10,60,50};
        int n = 6;
//        System.out.println(recursive(n-1,arr));
        // memorazation
        int dp[] = new int[n];
        Arrays.fill(dp,-1);
        System.out.println(memorization(n-1,arr,dp));

        // tabulation
        Arrays.fill(dp,-1);
        System.out.println(tabulation(n,arr,dp));

        // space optimization
        System.out.println(spaceOptimized(n,arr));
    }

    public static int spaceOptimized(int n, int arr[] ) {
        int prev1=0,prev2=0;
        for(int i=1;i<arr.length;i++){
            int jumpOne = prev1 + Math.abs(arr[i] - arr[i-1]);
            int jumpTwo = Integer.MAX_VALUE;
            if(i > 1){
                jumpTwo = prev2 + Math.abs(arr[i] - arr[i-2]);
            }
            int curr = Math.min(jumpOne, jumpTwo);
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }

    public static int tabulation(int n,int arr[], int dp[]){
        dp[0] = 0;
        for(int i=1;i<n;i++){
            int jumpOne = dp[i-1] + Math.abs(arr[i] - arr[i-1]);
            int jumpTwo = Integer.MAX_VALUE;
            if(i > 1){
                jumpTwo = dp[i-2] + Math.abs(arr[i] - arr[i-2]);
            }
            dp[i] = Math.min(jumpOne, jumpTwo);
        }
        return dp[n-1];
    }

    public static int memorization(int n, int arr[],int []dp){
        if(n == 0){
            return 0;
        }
        if(dp[n] != -1){
            return dp[n];
        }
        int left = memorization(n-1,arr,dp) + Math.abs(arr[n] - arr[n-1]);
        int right = Integer.MAX_VALUE;
        if (n > 1){
            right = memorization(n-2,arr,dp) + Math.abs(arr[n] - arr[n-2]);
        }
        return dp[n] = Math.min(left,right);

    }

    public static int recursive(int n, int [] arr){
        if(n == 0){
            return 0;
        }
        int left = recursive(n-1,arr) + Math.abs(arr[n] - arr[n-1]);
        int right = Integer.MAX_VALUE;
        if(n > 1){{
            right = recursive(n-2,arr) + Math.abs(arr[n] - arr[n-2]);
        }}
        return Math.min(left,right);
    }
}
