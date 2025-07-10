package com.questions.practice.dynamicprogramming;

import java.util.Arrays;

public class FibonacciDP {

    public static void main(String[] args) {
        int n = 6; // Example input
        System.out.println("Fibonacci of " + n + " (Recursive): " + fibonacciRecursive(n));
        int dp[] = new int[n+1];
        Arrays.fill(dp,-1);
        System.out.println("Fibonacci of " + n + " (Memoization): " + fibonacciMemoization(n,dp));
        System.out.println("Fibonacci of " + n + " (Tabulation): " + fibonacciTabulation(n,dp));
        System.out.println("Fibonacci of " + n + " (Space Optimized): " + fibonacciSpaceOptimized(n));
    }

    public static int fibonacciSpaceOptimized(int n){
        if(n<=1){
            return n;
        }
        int prev1= 0 , prev2 = 1;
        for(int i=2;i<=n;i++){
            int curr = prev1 + prev2;
            prev1 = prev2;
            prev2 = curr;
        }
        return prev2;
    }

    public static int fibonacciTabulation(int n,int []dp){
        dp[0] = 0;
        dp[1] = 1;
        for(int i=2;i<=n;i++){
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }

    public static int fibonacciMemoization(int n,int []dp){
        if(n == 0 || n == 1){
            return n;
        }
        if(dp[n] != -1){
            return dp[n];
        }
        int left = fibonacciMemoization(n-1,dp);
        int right = fibonacciMemoization(n-2,dp);
        dp[n] = left + right;
        return dp[n];
    }

    public static int fibonacciRecursive(int n){
        if (n == 0 || n == 1){
            return n;
        }
        int left = fibonacciRecursive(n-1);
        int right = fibonacciRecursive(n-2);
        return left + right;
    }




}
