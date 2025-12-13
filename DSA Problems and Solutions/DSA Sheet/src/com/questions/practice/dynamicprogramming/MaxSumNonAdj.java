package com.questions.practice.dynamicprogramming;

public class MaxSumNonAdj {
    public static void main(String[] args) {
        int arr[] = {2,4,6,2,5};
        int n = arr.length;
        System.out.println(resursive(n-1,arr));
    }

    private static int resursive(int n, int arr[]){
        if(n < 0){
            return 0;
        }
        if(n == 0){
            return arr[0];
        }
        int include = arr[n] + resursive(n-2,arr);
        int exclude = resursive(n-1,arr);
        return Math.max(include,exclude);
    }

    private static int memorization(int n,int arr[],int []dp){
        if(n<0){
            return 0;
        }
        if(n == 0){
            return arr[0];
        }
        if(dp[n] != -1){
            return dp[n];
        }
        int include = arr[n] + memorization(n-2,arr,dp);
        int exclude = memorization(n-1,arr,dp);
        return dp[n] = Math.min(include,exclude);
    }
}
