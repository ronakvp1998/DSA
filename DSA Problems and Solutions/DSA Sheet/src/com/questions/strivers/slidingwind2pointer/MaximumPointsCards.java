package com.questions.strivers.slidingwind2pointer;

public class MaximumPointsCards {

    public static void main(String[] args) {
        int arr[] = {61,16,51,40,37,21,96,70,13,98,28,75,74,87,68,55,95,24,46,87};
        int k = 19;
        System.out.println(maxSum1(arr,k));
    }

    public static int maxSum1(int arr[], int k){
        int n= arr.length;
        int maxSum=Integer.MIN_VALUE, lsum=0,rsum=0;
        for(int i=0;i<k;i++){
            lsum = lsum + arr[i];
        }
        maxSum = lsum;

        int l=k-1, r=n-1;
        while (l >= 0){
            lsum = lsum - arr[l];
            rsum = rsum + arr[r];
            l--;
            r--;
            maxSum = Math.max(maxSum,lsum+rsum);
        }
        return maxSum;
    }
}
