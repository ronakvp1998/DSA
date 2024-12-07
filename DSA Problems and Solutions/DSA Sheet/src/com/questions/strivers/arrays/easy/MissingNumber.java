package com.questions.strivers.arrays.easy;

public class MissingNumber {

    public static void main(String[] args) {
        int arr[] = {3,0,1};
        System.out.println(findMissing(arr));

    }

    public static int findMissing(int arr[]){
        int n = arr.length;
        int reqSum = (n)*(n+1)/2;
        int sum=0;
        for(int i=0;i< arr.length;i++){
            sum = sum + arr[i];
        }
        return reqSum - sum;
    }

}
