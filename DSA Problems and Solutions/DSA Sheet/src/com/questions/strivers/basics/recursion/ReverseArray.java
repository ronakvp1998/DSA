package com.questions.strivers.basics.recursion;

public class ReverseArray {
    public static void main(String[] args) {
        int arr[] = {1,2,3,4,5};

    }
    public void reverseArray(int arr[]) {
        // code here
        reverse(0,arr,arr.length);
    }
    public void reverse(int i, int []arr, int n){
        if(i >= n/2) return;
        swap(i,n-i-1,arr);
        reverse(i+1,arr,n);

    }
    public void swap(int i,int j,int arr[]){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


}
