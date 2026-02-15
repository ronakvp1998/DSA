package com.questions.practice.dynamicprogramming;

import java.util.List;

public class DpTest {
    public int minimumTotal(List<List<Integer>> matrix) {
        int n = matrix.size();
        int m = matrix.get(0).size();
        Integer dp[][] = new Integer[n][m];
        int minPath = Integer.MAX_VALUE;
        for(int j=0;j<m;j++){
            minPath = Math.min(minPath,recursion(matrix,n-1,j,dp));
        }
        return minPath;
    }

    private static int recursion(List<List<Integer>> matrix,int i,int j,Integer dp[][]){
        if(j < 0 || j >= matrix.get(0).size()){
            return (int)1e9;
        }
        if(i == 0){
            return matrix.get(0).get(j);
        }
        int down = recursion(matrix,i-1,j,dp);
        int downRight = recursion(matrix,i-1,j+1,dp);
        int downLeft = recursion(matrix,i-1,j-1,dp);
        return dp[i][j] =
                matrix.get(i).get(j) + Math.min(downLeft,Math.min(down,downRight));
    }

}
