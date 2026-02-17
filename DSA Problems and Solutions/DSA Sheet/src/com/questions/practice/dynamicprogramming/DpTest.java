package com.questions.practice.dynamicprogramming;

public class DpTest {
    private static int recursion(int i,int []wt,int []val,int w,int [][]dp){
        if(i == 0){
            return (w/wt[0]) * val[0];
        }
        if(dp[i][w] != -1){
            return dp[i][w];
        }
        int notTake = recursion(i-1,wt,val,w,dp);
        int take = Integer.MIN_VALUE;
        if(wt[i] <= w){
            take = val[i] + recursion(i,wt,val,w - wt[i],dp);
        }
        return dp[i][w] = Math.max(take,notTake);
    }
}
