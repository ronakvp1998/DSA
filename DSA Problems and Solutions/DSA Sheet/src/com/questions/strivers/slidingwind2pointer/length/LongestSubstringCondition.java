package com.questions.strivers.slidingwind2pointer.length;

import java.util.Map;

// L2 find the longest substring length with sum <= k (return maxlength)
public class LongestSubstringCondition {
    public static void main(String[] args) {
        int arr[] = {2,5,1,7,10};
        int k = 14;
        // condition sum <= k
//        System.out.println(longestSubstringCondition1(arr,k));
//        System.out.println(longestSubstringCondition2(arr,k));
        System.out.println(longestSubstringCondition3(arr,k));

    }

    // approach3 using optimized silding window just change the inner while loop to if
    public static int longestSubstringCondition3(int arr[], int k){
        int maxLen=Integer.MIN_VALUE,l=0,r=0,n=arr.length,sum=0;
        while (r < n){
            sum = sum + arr[r];
            if(sum > k){
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

    // approach2 using sliding window & 2 pointers
    public static int longestSubstringCondition2(int arr[], int k){
        int maxLen = Integer.MIN_VALUE,l=0,r=0,n=arr.length,sum=0;
        while (r < n){
            sum = sum + arr[r];
            while (sum > k){
                sum = sum - arr[l];
                l++;
            }
            if(sum <= k){
                maxLen = Math.max(maxLen,r-l+1);
            }
            r = r + 1;
        }
        return maxLen;
    }

    // approach1 generate all substring
    public static int longestSubstringCondition1(int arr[], int k){
        int maxLen = Integer.MIN_VALUE, n = arr.length;
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
