package com.questions.strivers.dynamicprogramming.intro;

import java.util.Arrays;

public class FibonacciDP {
    public static void main(String[] args) {
        int n = 5;
        int dp [] = new int[n+1];
        Arrays.fill(dp,-1);
//        System.out.println(f(n,dp));
//        System.out.println(tabulation(n,dp));
        System.out.println(spaceOpt(n));
    }
    // memorization
    public static int f(int n,int []dp){
        if(n <= 1){
            return n;
        }
        if(dp[n] != -1){
            return dp[n];
        }
        return dp[n] = f(n-1,dp) + f(n-2,dp);
    }
    // Tabulation
    public static int tabulation(int n, int[]dp){
        dp[0] = 0;
        dp[1] = 1;
        for(int i=2;i<=n;i++){
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }
    // space optimized
    public static int spaceOpt(int n){
        int prev1 = 0, prev2 = 1;
        for(int i=2;i<=n;i++){
            int curr = prev1 + prev2;
            prev1 = prev2;
            prev2 = curr;
        }
        return prev2;
    }
}
