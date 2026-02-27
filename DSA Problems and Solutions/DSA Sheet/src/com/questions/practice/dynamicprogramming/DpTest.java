package com.questions.practice.dynamicprogramming;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: TRIANGLE (LeetCode 120)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given a triangle array, return the minimum path sum from top to bottom.
 * For each step, you may move to an adjacent number of the row below.
 * More formally, if you are on index 'i' on the current row, you may move to either:
 * - index 'i' (directly below-left)
 * - index 'i + 1' (directly below-right)
 * on the next row.
 *
 *
 * EXAMPLE:
 * Input: triangle = [[2], [3,4], [6,5,7], [4,1,8,3]]
 * 2
 * 3 4
 * 6 5 7
 * 4 1 8 3
 *
 * Output: 11
 * Explanation: The minimum path sum from top to bottom is 2 + 3 + 5 + 1 = 11.
 *
 * KEY INSIGHT:
 * In a Triangle, every cell (row, col) has two children in the next row:
 * 1. (row + 1, col)
 * 2. (row + 1, col + 1)
 *
 * It is best to solve this BOTTOM-UP (start from the last row and move up).
 * Why?
 * - If we start from the top, we need to check boundary conditions for every move.
 * - If we start from the bottom, every cell is guaranteed to have two children below it.
 * ==================================================================================================
 */
public class DpTest {
    public static void main(String[] args) {
        int n = 5;
        int m = 5;
        int dp[][] = new int[n][m];
        int arr[][] = new int[n][m];
        for(int i[] : dp){
            Arrays.fill(i,-1);
        }
        System.out.println(minPathSum(n-1,m-1,arr,dp));
    }

    // button up approach
    private static int minPathSum(int row,int col,int arr[][],int dp[][]) {
        if(row < 0 || col < 0 ){
            return (int)1e9;
        }
        if(row == 0 && col == 0){
            return arr[row][col];
        }
        int up = (int)1e9;
        int left = (int)1e9;
        for (int i = 0; i < arr[arr.length - 1].length; i++) {
            up = minPathSum(row-1,i,arr,dp);
        }
        left = minPathSum(row,col-1,arr,dp);
        return arr[row][col] + Math.min(up,left);
    }
}
