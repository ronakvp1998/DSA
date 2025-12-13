package com.questions.practice.slidingwindow.old;

public class LongestSubstringCond {

    public static void main(String[] args) {
        int arr[] = {2,5,1,7,10};
        int k = 14;
        System.out.println(longestSubstringCond2(arr,k));
    }

    // Approach3 longest subarray with sum<=k
    private static int longestSubstringCond3(int arr[], int k){
        int l=0,r=0,sum=0,maxLen=0,n=arr.length;
        while (r < n){
            sum = sum + arr[l];
            if(sum > k){
                sum = sum - arr[l];
                l = l + 1;
            }
            if(sum <= k){
                maxLen = Math.max(maxLen,r-l+1);
            }
            r = r+1;
        }
        return maxLen;
    }

    // Approach2 longest subarray with sum<=k
    private static int longestSubstringCond2(int arr[], int k){
        int l=0,r=0,sum=0,maxLen=0,n=arr.length;
        while (r<n){
            sum = sum + arr[r];
            while (sum > k){
                sum = sum - arr[l];
                l = l + 1;
            }
            if(sum <= k){
                maxLen = Math.max(maxLen,r-l+1);
            }
            r = r+1;
        }
        return maxLen;
    }

    // Approach1 longest subarray with sum<=k
    private static int longestSubstringCond1(int arr[], int k){
        int maxLen = 0,n=arr.length;
        for(int i=0;i<n;i++){
            int sum = 0;
            for(int j=i;j<n;j++){
                sum = sum + arr[j];
                if(sum <= k){
                    maxLen = Math.max(maxLen,j-i+1);
                } else if (sum > k) {
                    break;
                }
            }
        }
        return maxLen;
    }
}
