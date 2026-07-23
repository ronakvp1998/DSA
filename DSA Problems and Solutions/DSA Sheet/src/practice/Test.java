package practice;

import strivers.arrays.medium.MaxSubArraySumPrint;

import java.util.*;
import java.util.stream.Collectors;

public class Test {

    public int subarraySum(int[] nums, int k) {

    }

    public void nextPermutation(int[] nums) {
        int n = nums.length;
        int a=-1;
        for(int i=n-2;i>=0;i--){
            // nums[i] < nums[i+1]
            if(nums[i] < nums[i+1]){
                a = i;
                break;
            }
        }
        if(a == -1){
            reverse(nums,0,n-1);
            return;
        }
        // nums[j] > nums[i];
        for(int j=n-1;j>=0;j--){
            if(nums[j] > nums[a]){
                swap(nums,a,j);
                break;
            }
        }
        reverse(nums,a+1,n-1);
    }

    private void reverse(int nums[],int start,int end){
        int left=start,right=end;
        while (left <= right){
            swap(nums,left,right);
            left++;
            right--;
        }
    }

    private void swap(int nums[],int i,int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public int maxSubArray(int[] nums) {
        int currentSum = 0,globalMax= Integer.MIN_VALUE;
        int n = nums.length;
        int start=0,end=0,tempStart=0;
        for (int i=0;i<n;i++){
            currentSum += nums[i];
            if(currentSum > globalMax){
                globalMax = currentSum;
                start = tempStart;
                end = i;
            }
            if(currentSum < 0){
                currentSum = 0;
                tempStart = i+1;
            }
        }
        for(int i=start;i<=end;i++){
            System.out.print(nums[i] + " ");
        }
        System.out.println();
        System.out.println(globalMax);
        return globalMax;
    }

}