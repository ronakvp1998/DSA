package com.questions.practice2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        int arr[] = {3,1,2,4,1,5,2,6,4};
        System.out.println(Arrays.toString(arr));
        mergeSort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }
    public static void mergeSort(int arr[],int start, int end){
        if(start >= end){
            return;
        }
        int mid = start + (end-start)/2;
        mergeSort(arr,start,mid);
        mergeSort(arr,mid+1,end);
        merge(arr,start,mid,end);
    }
    public static void merge(int arr[], int start, int mid, int end){
        int left = start;
        int right = mid+1;
        List<Integer> temp = new ArrayList<>();
        while (left <= mid && right <= end){
            if(arr[left] < arr[right]){
                temp.add(arr[left]);
                left++;
            }else{
                temp.add(arr[right]);
                right++;
            }
        }
        while (left <= mid){
            temp.add(arr[left]);
            left++;
        }
        while (right <= end){
            temp.add(arr[right]);
            right++;
        }
        for(int k=start;k<=end;k++){
            arr[k] = temp.get(k-start);
        }
    }

}
