package com.questions.strivers.slidingwind2pointer;

public class MaxConsOnes {
    public static void main(String[] args) {
        int arr[] = {1,1,1,0,0,0,1,1,1,1,0};
        int k=2;
        System.out.println(maxConsOnes2(arr,2));
    }

    // approach 3 using slinging window
    public static int maxConOne3(int arr[], int k) {
        int l = 0, r = 0, zeros = 0, maxLen = 0;
        while (r < arr.length) {
            if (arr[r] == 0) {
                zeros++;
            }
            if (zeros > k) {
                if (arr[l] == 0) {
                    zeros--;
                    l++;
                }
            }
            if (zeros <= k) {
                int len = r - l + 1;
                maxLen = Math.max(maxLen, len);
            }
            r++;
        }
        return maxLen;
    }

    // approach 2 usign sliding window
    public static int maxConsOnes2(int arr[],int k){
        int maxLen = 0,l=0,r=0,n=arr.length,zeros=0;
        while (r < n){
            if(arr[r] == 0){
                zeros++;
            }
            while (zeros > k){
                if(arr[l] == 0){
                    zeros--;
                }
                l++;
            }
            if(zeros <= k){
                maxLen = Math.max(maxLen,r-l+1);
            }
            r++;
        }
        return maxLen;
    }

    // approach 1 brute force
    public static int maxConsOnes1(int arr[],int k){
        int maxLen=0;
        for(int i=0;i<arr.length;i++){
            int zeros = 0;
            for(int j=i;j<arr.length;j++){
                if(arr[j] == 0){
                    zeros++;
                }
                if(zeros <= k){
                    int len = j-i+1;
                    maxLen = Math.max(len,maxLen);
                }else{
                    break;
                }
            }
        }
        return maxLen;
    }
}
