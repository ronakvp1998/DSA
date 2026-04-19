package com.questions.practice;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        int  nums[] = {4,5,6,7,0,1,2};
        int target = 0;

    }

    public boolean search(int[] nums, int target) {
        if (nums == null || nums.length == 0) return false;
        int n = nums.length;
        int low=0, high=n-1;
        while (low <= high){
            int mid = low + (high-low)/2;
            if(nums[mid] == target){
                return true;
            }
            // Handle duplicates: Cannot determine which half is sorted
            if (nums[low] == nums[mid] && nums[mid] == nums[high]) {
                low++;
                high--;
                continue;
            }
            if(nums[low] <= nums[mid]){
                if(nums[low] <= target && nums[mid] > target){
                    high = mid - 1;
                }else{
                    low = mid + 1;
                }
            }else{
                if(target > nums[mid] && nums[high] >= target){
                    low = mid + 1;
                }else{
                    high = mid - 1;
                }
            }
        }
        return false;
    }

    public int[] searchRange(int[] nums, int target) {
        int a = firstPos(nums,target);
        if (a == -1) {
            return new int[]{-1, -1};
        }
        int b = lastPos(nums,target);
        return new int[]{a,b};
    }

    public static int firstPos(int nums[],int target){
        int n = nums.length;
        int low=0,high=n-1;
        int ans = -1;
        while (low <= high){
            int mid = low + (high-low)/2;
            if(nums[mid] == target){
                ans = mid;
                high = mid-1;
            } else if (nums[mid] > target) {
                high--;
            }else{
                low++;
            }
        }
        return ans;
    }


    public static int lastPos(int nums[],int target){
        int n = nums.length;
        int low=0,high=n-1;
        int ans = -1;
        while (low <= high){
            int mid = low + (high-low)/2;
            if(nums[mid] == target){
                ans = mid;
                low = mid+1;
            } else if (nums[mid] > target) {
                high--;
            }else{
                low++;
            }
        }
        return ans;
    }
}