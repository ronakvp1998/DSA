package com.questions.strivers.recursion.basics;

import java.util.Arrays;

public class QuickSort {

    public static void main(String[] args) {
        int arr[] = {4, 6, 2, 5, 7, 9, 1, 3};
        System.out.println(Arrays.toString(arr));
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int arr[], int low, int high){
        if(low <= high){
            int partition = func(arr,low,high);
            quickSort(arr,low,partition-1);
            quickSort(arr,partition+1,high);
        }
    }

    public static int func(int arr[], int low, int high){
        int i = low;
        int j = high;
        int pivot = arr[low];

        while (i < j){
            while (arr[i] <= pivot && i <= high-1){
                i++;
            }
            while (arr[j] > pivot && j >= low+1){
                j--;
            }
            if(i < j){
                swap(i,j,arr);
            }
        }
        swap(low,j,arr);
        return j;
    }

    public static void swap(int i, int j, int arr[]){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
