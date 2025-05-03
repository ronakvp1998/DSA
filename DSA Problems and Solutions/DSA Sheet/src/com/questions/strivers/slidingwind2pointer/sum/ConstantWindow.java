package com.questions.strivers.slidingwind2pointer.sum;

// L1 Return Max sum by picking k elements consectively

public class ConstantWindow {
    public static void main(String[] args) {
        int arr[] = {-1,2,3,3,4,5,-1};
        int k = 4;
        System.out.println(constantWindow(arr,k));
    }

    public static int constantWindow(int arr[], int k){
        int maxSum=Integer.MIN_VALUE,l=0,r=k-1,sum=0,n=arr.length;
        for(int i=0;i<=r;i++){
            sum = sum + arr[i];
        }
        maxSum = sum;
        while (r < n-1){
            sum = sum - arr[l];
            l++;
            r++;
            sum = sum + arr[r];
            maxSum = Math.max(maxSum,sum);
        }
        return maxSum;
    }
}
