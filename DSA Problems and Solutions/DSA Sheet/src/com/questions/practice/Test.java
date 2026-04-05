package com.questions.practice;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        int [][] arr = {{1,1,1},{1,0,1},{1,1,1}};
        setZeroesOptimal(arr);
        for(int[] a : arr){
            System.out.println(Arrays.toString(a));
        }
    }
    public static void setZeroesOptimal(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        boolean firstRowZero = false, firstColZero = firstRowZero;
        for(int i=0;i<m;i++){
            if(matrix[i][0] == 0){
                firstColZero = true;
            }
        }
        for(int j=0;j<n;j++){
            if(matrix[0][j] == 0){
                firstRowZero = true;
            }
        }
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                if(matrix[i][j] == 0){
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                if(matrix[i][0] == 0 || matrix[0][j] == 0){
                    matrix[i][j] = 0;
                }
            }
        }
        if(firstColZero){
            for(int i=0;i<m;i++){
                matrix[i][0] = 0;
            }
        }
        if(firstRowZero){
            for(int j=0;j<n;j++){
                matrix[0][j] = 0;
            }
        }
    }
    public void setZeroesBetter(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        boolean [] zeroRows = new boolean[m];
        boolean [] zeroCols = new boolean[n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(matrix[i][j] == 0){
                    zeroRows[i] = true;
                    zeroCols[j] = true;
                }
            }
        }
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(zeroRows[i] || zeroCols[j]){
                    matrix[i][j] = 0;
                }
            }
        }
    }
    public void setZeroesBruteForce(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int [][] copy = new int[m][n];
        for(int i=0;i<m;i++){
            copy[i] = matrix[i].clone();
        }
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(matrix[i][j] == 0){
                    for(int c = 0;c<n;c++){
                        copy[i][c] = 0;
                    }
                    for(int r = 0;r<m;r++){
                        copy[r][j] = 0;
                    }
                }
            }
        }
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                matrix[i][j] = copy[i][j];
            }
        }
    }

}
