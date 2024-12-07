package com.questions.strivers.arrays.easy;

import java.util.Arrays;

public class RotateArrayByK {
    public static void main(String[] args) {
        int arr[] = {1,2,3,4,5,6,7};
        int k = 3;
        rotateArray(arr,k);
        System.out.println(Arrays.toString(arr));
    }

    public static void rotateArray(int arr[], int k){
        k = arr.length-k;
        int temp[] = new int [k];
        for(int i=0;i<k;i++){
            temp[i] = arr[i];
        }

        for(int i=0;i< arr.length-k;i++){
            arr[i] = arr[i+k];
        }

        for(int i= arr.length-k; i< arr.length;i++){
            arr[i] = temp[i-arr.length+k];
        }

    }
}
