package com.questions.apnacollageold.array;

// 5 Given an array arr[] of n integers where arr[i] represents the number of chocolates in ith packet. Each packet can have a variable number of chocolates. There are m students, the task is to distribute chocolate packets such that:
//
//Each student gets exactly one packet.
//The difference between the maximum and minimum number of chocolates in the packets
// given to the students is minimized.

import java.util.Arrays;

public class ChocolateDistributionProblem {

    public static void main(String[] args) {
        int arr1 [] ={7,3,2,4,9,12,56};
        int m = 3;

        m = 5;
    }

    private static int findMinDiff(int arr[], int m){
        int n = arr.length;
        Arrays.sort(arr);

        int minDiff = Integer.MAX_VALUE;
        // i+m-1 is an window
        for(int i=0;i+m-1 < n;i++){
            int diff = arr[i+m-1] - arr[i];
            if(diff< minDiff){
                minDiff = diff;
            }
        }
        return minDiff;
    }
}
