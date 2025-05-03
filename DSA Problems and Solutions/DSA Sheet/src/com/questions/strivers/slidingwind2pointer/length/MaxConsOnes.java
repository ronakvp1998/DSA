package com.questions.strivers.slidingwind2pointer.length;
/*
L4. Max Consecutive Ones III
        Given a binary array nums and an integer k, return the maximum number of consecutive 1's in the array if you can flip at most k 0's.
        Example 1:Input: nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2 Output: 6
        Explanation: [1,1,1,0,0,1,1,1,1,1,1] Bolded numbers were flipped from 0 to 1. The longest subarray is underlined.
        Example 2:
        Input: nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], k = 3 Output: 10
        Explanation: [0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1] Bolded numbers were flipped from 0 to 1. The longest subarray is underlined.
*/
public class MaxConsOnes {
    public static void main(String[] args) {
        int arr[] = {1,1,1,0,0,0,1,1,1,1,0};
        int k=2;
//        System.out.println(maxConsOnes1(arr,k));
//        System.out.println(maxConsOnes2(arr,k));
        System.out.println(maxConsOnes3(arr,k));

    }

    // approach 3 using slinging window
    public static int maxConsOnes3(int arr[], int k) {
        int maxLen=0,l=0,r=0,zeros=0;
        while (r < arr.length){
            if(arr[r] == 0){
                zeros++;
            }
            if(zeros > k){
                if(arr[l] == 0){
                    zeros--;
                }
                l++;
            }
            if(zeros <= k){
                int len = r - l + 1;
                maxLen = Math.max(maxLen,len);
            }
            r++;
        }
        return maxLen;
    }

    // approach 2 using sliding window
    public static int maxConsOnes2(int arr[],int k){
        int maxLen=0,l=0,r=0,zeros = 0;
        while (r < arr.length){
            if(arr[r] == 0){
                zeros++;
            }
            while (zeros > k){
                if(arr[l] == 0 ){
                    zeros--;
                }
                l++;
            }
            if(zeros <= k){
                int len = r - l + 1;
                maxLen = Math.max(maxLen,len);
            }
            r++;
        }
        return maxLen;
    }

    // approach 1 brute force, generate all subarrays & figure out the longest
    // subarray that has at most k zeros
    public static int maxConsOnes1(int arr[],int k){
        int maxLen = 0,n=arr.length;
        for(int i=0;i<n;i++){
            int zeros = 0;
            for(int j=i;j<n;j++){
                if(arr[j] == 0){
                    zeros++;
                }
                if(zeros <= k){
                    int length = j - i +1;
                    maxLen = Math.max(maxLen,length);
                }else{
                    break;
                }
            }
        }
        return maxLen;
    }
}
