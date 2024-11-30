package com.questions.strivers.arrays.medium;

import java.util.Arrays;
//https://leetcode.com/problems/majority-element/description/
// pg 3 book4
public class SortArray012 {

    public static void main(String[] args) {
        int arr[] = {0,1,1,0,1,2,1,2,0,0,0};
        sortArray(arr,arr.length);
        System.out.println(Arrays.toString(arr));

    }

    public static void sortArray(int arr[], int n){
        int low = 0,mid=0,high=n-1;

        while (mid<=high){
            if(arr[mid] == 0){
                swap(arr,mid,low);
                mid++;low++;
            }else if(arr[mid] == 1){
                mid++;
            } else{
                swap(arr,mid,high);
                high--;
            }
        }
    }

    public static void swap(int arr[], int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
