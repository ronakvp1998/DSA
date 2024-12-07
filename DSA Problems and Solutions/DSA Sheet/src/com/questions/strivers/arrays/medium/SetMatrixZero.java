package com.questions.strivers.arrays.medium;

//https://leetcode.com/problems/set-matrix-zeroes/description/

public class SetMatrixZero {

    public static void main(String[] args) {
        int arr[][] = {{1,1,1,1},{1,0,0,1},{1,1,0,1},{1,1,1,1}};
        setZeros1(arr);
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    // better approach using 2 new 1D arr
    public static void setZeros1(int arr[][]){
        int rowArr[] = new int[arr.length];
        int colArr[] = new int[arr[0].length];

        for(int i=0;i< arr.length;i++){
            for(int j=0;j< arr[0].length;j++){
                if(arr[i][j] == 0){
                    rowArr[i] = 1;
                    colArr[j] = 1;
                }
            }
        }

        for(int i=0;i< arr.length;i++){
            for(int j=0;j< arr[0].length;j++){
                if(rowArr[i] == 1){
                    arr[i][j] = 0;
                } else if (colArr[j] == 1) {
                    arr[i][j] = 0;
                }
            }
        }
    }

    // bruteforce approach
    public static void setZeros(int arr[][]){
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                if(arr[i][j] == 0){
                    // put -1 in this row and column
                    for(int k=0;k< arr.length;k++){
                        arr[i][k] = -1;
                        arr[k][i] = -1;
                    }
                }
            }
        }

        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                if(arr[i][j] == -1){
                    arr[i][j] =0;
                }
            }
        }

    }



}
