package com.questions.strivers.dynamicprogramming.oneddp;

import java.lang.reflect.Array;
import java.util.Arrays;

//https://takeuforward.org/data-structure/dynamic-programming-climbing-stairs/
public class ClimbingStairs {
    // 0=>1, 1=>1, 2=>2, 3=>3, 4=>5
    public static void main(String[] args) {
        // memorization
        int n = 3;
        int dp[] = new int[n + 1];
        Arrays.fill(dp, -1);
        System.out.println(memoraization(n, dp));
        // tabulation
        System.out.println(tabulation(n,dp));
        // space optimization
        System.out.println(spaceOpt(n));
    }

    public static int memoraization(int n, int[] dp) {
        if (n == 0 || n == 1) {
            return 1;
        }
        if (dp[n] != -1) {
            return dp[n];
        }
        dp[n] = memoraization(n - 1, dp) + memoraization(n - 2, dp);
        return dp[n];
    }

    public static int tabulation(int n, int dp[]){
        dp[0] = 1; dp[1] = 1;
        for(int i=2;i<=n;i++){
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }

    public static int spaceOpt(int n) {
        if (n == 1) return 1;
        int prev1 = 1, prev2 = 2; // base: ways(1) = 1, ways(2) = 2
        for (int i = 3; i <= n; i++) {
            int curr = prev1 + prev2;
            prev1 = prev2;
            prev2 = curr;
        }
        return prev2;
    }
}
