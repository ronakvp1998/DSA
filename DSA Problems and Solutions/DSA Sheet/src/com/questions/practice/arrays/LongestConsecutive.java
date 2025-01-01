package com.questions.practice.arrays;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestConsecutive {

    public static void main(String[] args) {
        int arr[] = {100, 200, 1, 2, 3, 4};
        System.out.println(longestConsecutive1(arr));
    }

    public static int longestConsecutive1(int arr[]){
        Set<Integer> set = new HashSet<>();
        for(int i=0;i< arr.length;i++){
            set.add(arr[i]);
        }
        int longest = 1;
        for(int i=0;i< arr.length;i++){
            if(!set.contains(arr[i] - 1 )){
            int count=1;
            int x = arr[i];
            while (set.contains(x+1)){
                x+=1;
                count++;
            }
            longest = Math.max( longest , count);
            }
        }
        return longest;
    }


    public static int longestConsecutive(int arr[]){
        Arrays.sort(arr);
        int longest=1,currCount=0,lastSmallest=Integer.MIN_VALUE;
        int n = arr.length;
        for(int i=0;i<n;i++){
            if(arr[i] - 1 == lastSmallest){
                currCount++;
                if(currCount > longest){
                    longest = currCount;
                }
                lastSmallest = arr[i];
            }else{
                currCount=1;
                lastSmallest = arr[i];
            }
        }
        return longest;
    }
}
