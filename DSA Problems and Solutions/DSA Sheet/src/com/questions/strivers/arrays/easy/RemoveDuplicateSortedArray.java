package com.questions.strivers.arrays.easy;

import java.util.Arrays;



public class RemoveDuplicateSortedArray {

    public static void main(String[] args) {
        int [] arr = {1,1,2};
        System.out.println(removeDuplicate(arr));
    }

    public static int removeDuplicate(int nums[]){
        int[] res = new int[nums.length];
        int j = 0;
        res[j] = nums[0];
        for(int i=1;i<nums.length;i++){
            if(nums[i] == nums[i-1]){
                continue;
            }else{
                j = j+1;
                res[j] = nums[i];
            }

        }
        System.arraycopy(res,0,nums,0, nums.length);
        return j+1;
    }
}
