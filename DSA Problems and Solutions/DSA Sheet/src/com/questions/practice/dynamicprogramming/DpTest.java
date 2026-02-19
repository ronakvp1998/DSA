package com.questions.practice.dynamicprogramming;

import java.util.Arrays;

public class DpTest {

    private static int houseRobber(int arr[]){
        int n = arr.length;
        if(n == 1){
            return arr[0];
        }
        int dp1[] = new int[n];
        int dp2[] = new int[n];
        Arrays.fill(dp1,-1);
        Arrays.fill(dp2,-1);

        int case1 = memo(0,n-2,arr,dp1);
        int case2 = memo(1,n-1,arr,dp2);
        return Math.max(case1,case2);
    }

    private static int memo(int start,int end,int arr[],int dp[]){
        if(end < start){
            return 0;
        }
        if(end == start){
            return arr[start];
        }
        if(dp[end] != -1){
            return dp[end];
        }
        int notPick = 0 + memo(start,end-1,arr,dp);
        int pick = arr[end] + memo(start,end-2,arr,dp);
        return dp[end] = Math.max(notPick,pick);
    }
}
