package com.questions.apnacollageold.array;

import java.util.Arrays;

// 2 Array Reverse
public class ReverseArray {
    public static void main(String[] args) {
        int arr[] = {4,3,2,7,6,1};
        reverseArray(arr,arr.length);
        System.out.println(Arrays.toString(arr));

        reverseArray1(arr,arr.length);
        System.out.println(Arrays.toString(arr));

    }

    // approach 2 two pointer TC O(n) SC O(1)
    public static void reverseArray1(int arr[] ,int n){
        int start = 0;
        int end = arr.length-1;

        while (start<=end){
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }


    // approach 1 tempoaray array TC O(n) SC O(N)
    public static void reverseArray(int arr[],int n){
        int temp[] = new int[arr.length];

        for(int i=0;i< arr.length;i++){
            temp[i] = arr[arr.length-1-i];
        }


        for(int i=0;i< arr.length;i++){
            arr[i] = temp[i];
        }
    }


}
