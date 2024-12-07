package com.questions.strivers.arrays.medium;

//https://leetcode.com/problems/longest-consecutive-sequence/editorial/

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LongestConsecutiveSeq {
    public static void main(String[] args) {
        int arr[] = {100,4,200,1,3,2};
        System.out.println(longestCongSeq2(arr));
    }

    // 2nd optimized approach
    public static int longestCongSeq2(int arr[]){
        int n = arr.length;
        if(n == 0) return 0;
        int longest = 1;
        Set<Integer> set = new HashSet<>();
        for(int i=0;i<n;i++){
            set.add(arr[i]);
        }
        for (int it : set) {
            // if 'it' is a starting number
            if (!set.contains(it - 1)) {
                // find consecutive numbers
                int cnt = 1;
                int x = it;
                while (set.contains(x + 1)) {
                    x = x + 1;
                    cnt = cnt + 1;
                }
                longest = Math.max(longest, cnt);
            }
        }
        return longest;

    }


    // 1st optimized approach using sorting
    public static int longestCongSeq1(int arr[]){
        if(arr.length == 0){
            return 0;
        }
        Arrays.sort(arr);
        int count=0, longest=1, lastSmallest = Integer.MIN_VALUE;

        for(int i=0;i< arr.length;i++){
            if(arr[i]-1 == lastSmallest){
                count = count + 1;
                lastSmallest = arr[i];
            }else if(arr[i] == lastSmallest){
                continue;
            }else if(lastSmallest != arr[i]){
                count = 1;
                lastSmallest = arr[i];
            }
            longest = Math.max(longest,count);
        }
        return longest;
    }

    // Brute force approach
    public static int longestCongSeq(int arr[]){
        int longest = 1;

        for(int i=0;i<arr.length;i++){
            int x = arr[i], count = 1;
            while (Arrays.binarySearch(arr,x) >=0){
                count = count + 1;
            }
            if(longest > count){
                longest = count;
            }
        }
        return longest;
    }
}
