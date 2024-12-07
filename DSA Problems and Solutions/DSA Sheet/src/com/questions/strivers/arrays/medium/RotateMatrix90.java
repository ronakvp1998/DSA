package com.questions.strivers.arrays.medium;

public class RotateMatrix90 {

    public static void main(String[] args) {
        int arr[][] = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
        rotateMatrix1(arr);
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    // optimal approach avoid new matrix
    public static void rotateMatrix1(int matrix[][]){
        // step 1 transpose the matrix i->j and j->i
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i; j < matrix[0].length; j++) {
                int temp = 0;
                temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        // step 2 reverse the rows of every matrix
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length / 2; j++) {
                int temp = 0;
                temp = matrix[i][j];
                matrix[i][j] = matrix[i][matrix.length - 1 - j];
                matrix[i][matrix.length - 1 - j] = temp;
            }
        }

    }


    // brute force approach
    public static void rotateMatrix(int arr[][]){
        int ans[][] = new int[arr.length][arr[0].length];
        int n=ans.length;
        for(int i=0;i<ans.length;i++){

            for(int j=0;j<ans[0].length;j++){
                ans[j][n-1-i] = arr[i][j];
            }
        }
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                arr[i][j] = ans[i][j];
            }
        }
    }
}
