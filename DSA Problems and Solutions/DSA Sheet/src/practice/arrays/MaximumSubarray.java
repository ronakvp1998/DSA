package com.questions.practice.arrays;

public class MaximumSubarray {

    public static void main(String[] args) {
        int[] arr = { 2, 1, -3, 4, -1, 2, 1, -5, 4};
        int n = arr.length;
        int maxSum = maxSubarraySum(arr, n);
        System.out.println("The maximum subarray sum is: " + maxSum);

    }

    private static int maxSubarraySum(int arr[], int n){
        int sum=0;
        int ans = Integer.MIN_VALUE;
        int start=0, end = 0;
        for(int i=0;i<arr.length;i++){
            sum += arr[i];
            if(sum<0){
                sum = 0;
                start = i;
            }
            if(sum > ans){
                ans = sum;
                end = i;
            }
        }
        System.out.println(start  + " " +end);
        return ans;
    }
}
