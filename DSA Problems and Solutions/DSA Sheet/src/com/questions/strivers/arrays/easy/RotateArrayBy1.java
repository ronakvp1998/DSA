package com.questions.strivers.arrays.easy;

import java.util.Arrays;

public class RotateArrayBy1 {

    public static void main(String[] args) {
        int arr[] = {1,2,3,4,5,6,7};
        int k =3;
        rotateArray(arr,k);
        System.out.println(Arrays.toString(arr));
    }

    public static void rotateArray(int arr[], int k ){
        int temp = arr[0];
        for(int i=1;i< arr.length;i++){
            arr[i-1] = arr[i];
        }
        arr[arr.length-1] = temp;
    }
}
