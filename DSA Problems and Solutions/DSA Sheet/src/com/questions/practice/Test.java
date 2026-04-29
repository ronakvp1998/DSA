package com.questions.practice;

import java.util.*;

public class Test {
    public static void main(String[] args) {

    }
    public static int optimalApproach(String s) {
        if(s == null || s.length() < 3) {
            return 0;
        }
        int[] lastSeen = new int[3];
        Arrays.fill(lastSeen,-1);
        int count = 0;
        int n = s.length();
        for(int i=0;i<n;i++){
            lastSeen[s.charAt(i) - 'a'] = i;
            if(lastSeen[0] != -1 && lastSeen[1] != -1 && lastSeen[2] != -1){
                count += 1 + Math.min(lastSeen[0],Math.min(lastSeen[1],lastSeen[2]));
            }
        }
        return count;
    }
    // longest subarray where <condition> sum <= k
    private static int longestSubstringConditionStandard(int[] arr, int k) {
        int maxLen=0,l=0,r=0,sum=0,n=arr.length;
        while (r<n){
            sum = sum + arr[r];
            while (sum > k && l <= r){
                sum = sum - arr[l];
                l++;
            }
            if(sum <= k){
                maxLen = Math.max(maxLen,r-l+1);
            }
            r++;
        }
        return maxLen;
    }


    // max sum by picking 4 elements at a time
    private static int constantWindow(int arr[],int k){
        int n = arr.length;
        int l=0,r=0,sum=0;
        for(int i=0;i<=r;i++){
            sum = sum + arr[i];
        }
        int maxSum = sum;
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