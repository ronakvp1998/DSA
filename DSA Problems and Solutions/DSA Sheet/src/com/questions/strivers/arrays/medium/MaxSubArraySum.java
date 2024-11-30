package com.questions.strivers.arrays.medium;

//https://leetcode.com/problems/maximum-subarray/description/
//
public class MaxSubArraySum {

    public static void main(String[] args) {
        int arr[] = {-2,-3,4,-1,-2,1,5,-2};
        System.out.println(maxSubArray2(arr));
    }

    // also print the sub array
    public static int maxSubArray2(int arr[]){
        int maxSum = Integer.MIN_VALUE;
        int sum = 0;
        int start = 0, ansStart=-1,ansEnd = -1;
        for(int i=0;i<arr.length;i++){
            if(sum == 0){
                start = i;
            }
            sum = sum + arr[i];
            if(sum < 0){
                sum = 0;
            }
            if(maxSum < sum){
                maxSum = sum;
                ansStart = start;
                ansEnd = i;
            }
        }
        int max = Integer.MIN_VALUE;
        if(maxSum == 0){
            // find max negative number
            for(int i=0;i<arr.length;i++){
                if(arr[i] > max){
                    max = arr[i];
                }
            }
            maxSum = max;
        }

        // print the subarray
        for(int i=start;i<=ansEnd;i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
        return maxSum;
    }


    // kadans algorithm
    public static int maxSubArray(int arr[]){
        int maxSum = Integer.MIN_VALUE;
        int sum = 0;
        for(int i=0;i<arr.length;i++){
            sum = sum + arr[i];
            if(sum < 0){
                sum = 0;
            }
            if(maxSum < sum){
                maxSum = sum;
            }
        }
        int max = Integer.MIN_VALUE;
        if(maxSum == 0){
            // find max negative number
            for(int i=0;i<arr.length;i++){
                if(arr[i] > max){
                    max = arr[i];
                }
            }
            maxSum = max;
        }
        return maxSum;
    }

}
