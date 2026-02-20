package com.questions.practice.dynamicprogramming;

public class DpTest {

    private static int memo(int m,int n,int grid[][],int dp[][]){
        if(m == 0 && n == 0){
            return 1;
        }
        if(m < 0 || n < 0){
            return 0;
        }
        if(dp[m][n] != -1){
            return dp[m][n];
        }
        int ans = 0;
        if(grid[m][n] != 1){
            int up = memo(m-1,n,grid,dp);
            int left = memo(m, n-1,grid,dp);
            ans = up + left;
        }
        return dp[m][n] = ans;
    }

    public static void main(String[] args) {
        int n = 4;
        int m = 4;
        int grid[][] = new int[m][n];
        int dp[][] = new int[m][n];
        System.out.println(memo(m-1,n-1,grid,dp));
    }
}
