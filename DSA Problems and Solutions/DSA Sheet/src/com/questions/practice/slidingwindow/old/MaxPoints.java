package com.questions.practice.slidingwindow.old;

public class MaxPoints {
    public static void main(String[] args) {
        int arr[] = {6,2,3,4,7,2,1,7,1};
        int k=4;
        System.out.println(maxPoints(arr,k));
    }

    // approch1 brute force
    private static int maxPoints(int arr[], int k){
        int rsum=0,lsum=0,sum=0,maxSum=0,n=arr.length;
        for(int i=0;i<k;i++){
            lsum = lsum + arr[i];
        }
        maxSum = lsum;
        int r = n-1;
        for(int i=k-1;i>=0;i--){
            lsum = lsum - arr[i];
            rsum = rsum + arr[r];
            r = r - 1;
            maxSum = Math.max(maxSum,lsum+rsum);
        }
        return maxSum;
    }
}
