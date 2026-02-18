package com.questions.practice.dynamicprogramming;

import java.util.Arrays;

public class DpTest {
    private static int minsDistance(String s1,String s2){
        int n = s1.length();
        int m = s2.length();
        int [][]dp = new int[n][m];
        for(int [] i : dp){
            Arrays.fill(i,-1);
        }
        int lcsLength = lcsHelper(s1,s2,n-1,m-1,dp);
        return n+m-2*lcsLength;
    }
    private static int lcsHelper(String s1,String s2,int i,int j,int [][]dp){
        if(i<0 || j<0){
            return 0;
        }
        if(dp[i][j] != -1){
            return dp[i][j];
        }
        if(s1.charAt(i) == s2.charAt(j)){
            return dp[i][j] = 1 + lcsHelper(s1,s2,i-1,j-1,dp);
        }
        return dp[i][j] = Math.max(
                lcsHelper(s1,s2,i-1,j,dp),
                lcsHelper(s1,s2,i,j-1,dp)
        );
    }
}
