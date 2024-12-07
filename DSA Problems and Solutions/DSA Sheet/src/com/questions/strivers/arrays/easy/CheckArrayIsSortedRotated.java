package com.questions.strivers.arrays.easy;

// https://leetcode.com/problems/check-if-array-is-sorted-and-rotated/

public class CheckArrayIsSortedRotated {

    public static void main(String[] args) {
        int arr[] = {};
        System.out.println(check(arr));

    }
    public static boolean check(int[] nums) {
        int countBreaks = 0;
        int n = nums.length;
        // Count the number of "breaks" in ascending order
        for(int i=1;i<nums.length;i++){
            if(nums[i] < nums[i-1]){
                countBreaks++;
            }
        }
        // Check the wrap-around condition (last element compared to the first)
        if(nums[n-1] > nums[0]){
            countBreaks++;
        }
        // If more than one break point, return false
        return countBreaks <= 1;
    }

}
