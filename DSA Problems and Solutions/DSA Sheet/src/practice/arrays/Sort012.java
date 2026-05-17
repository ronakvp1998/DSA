package com.questions.practice.arrays;

import java.util.Arrays;

public class Sort012 {

    public static void main(String[] args) {
        int arr[] = {0,1,2,0,1,2,1,2,0,0,0,1};
        sort012(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void sort012(int arr[]) {
        int n = arr.length;
        int low=0,mid=0,high=n-1;
        while (mid<=high){
            if(arr[mid] == 0){
                swap(arr,low,mid);
                low++;
                mid++;
            }else if(arr[mid] == 1){
                mid++;
            }else{
                swap(arr,mid,high);
                high--;
            }
        }
    }

    private static void swap(int arr[], int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
