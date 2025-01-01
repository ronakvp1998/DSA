package com.questions.practice.arrays;

import java.util.Arrays;

public class SetMatrixZero {

    public static void main(String[] args) {
        int arr[][] = {{0,1,2,0},{3,4,5,2},{1,3,1,5}};
        for(int [] a: arr){
            System.out.println(Arrays.toString(a));
        }
        System.out.println();
        setMatrixZero(arr);
    }

    public static void setMatrixZero(int arr[][]){
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                if(arr[i][j] == 0){
                    for(int a=0;a<arr.length;a++){
                        arr[i][a] = -1;
                        arr[a][j] = -1;
                    }
                }
            }
        }


        for(int i=0;i< arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                if(arr[i][j] == -1){
                    arr[i][j] = 0;
                }
            }
        }

        for(int [] a: arr){
            System.out.println(Arrays.toString(a));
        }
    }
}
