package com.questions.practice.dynamicprogramming;

//
// * MASTERCLASS: TARGET SUM (LeetCode 494)
// * ==================================================================================================
//         * * ### 1. Header & Problem Context
// * * **Problem Statement:**
//        * You are given an integer array 'nums' and an integer 'target'.
//        * You want to build an expression out of nums by adding one of the symbols '+' or '-'
//        * before each integer in nums and then concatenate all the integers.
// * Return the number of different expressions that you can build, which evaluates to target.
public class DpTest {

    private static int recu(int i,int k,int nums[]){
        if(i == 0){
            return (k%nums[i] == 0) ? 1 : 0;
        }
        int notTake = recu(i-1,k,nums);
        int take = 0;
        if(nums[i] <= k){
            take = recu(i,k - nums[i],nums);
        }
        return take + notTake;
    }

    private static int tabu(int k,int nums[]){
        int n = nums.length;
        int[][] dp = new int[n][k+1];
        for(int i=0;i<=k;i++){
            if(i%nums[i] == 0) dp[0][i] = 1;
        }
        for(int i=1;i<n;i++){
            for(int j=0;j<=k;j++){
                int notTake = dp[i-1][j];
                int take = 0;
                if(nums[i] <= j){
                    take = dp[i][j-nums[i]];
                }
                dp[i][j] = notTake + take;
            }
        }
        return dp[n-1][k];
    }

}
