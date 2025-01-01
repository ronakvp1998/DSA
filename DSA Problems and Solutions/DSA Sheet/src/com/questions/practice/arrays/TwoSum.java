package com.questions.practice.arrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {

    public static void main(String[] args) {
        int arr[] = {2,6,5,8,11};
        int target = 17;
        System.out.println(Arrays.toString(twoSum2(arr,target)));
    }


    // 3rd app
    public static int [] twoSum2(int arr[], int target){
        Arrays.sort(arr);
        int n = arr.length;
        int left=0, right = n-1;

        while (left < right){
            int sum = arr[left] + arr[right];
            if(sum == target){
                return new int[]{left,right};
            } else if (sum < target) {
                left++;
            } else if (sum > target) {
                 right--;
            }

        }
        return new int[]{-1,-1};
    }

    // 2nd app
    public static int[] twoSum1(int[] arr, int target){
        int n = arr.length;
        Map<Integer,Integer> map = new HashMap<>();
//        int ans[] = new int[2];
        for(int i=0;i<arr.length;i++){
            int rem = target - arr[i];
           if( map.containsKey(rem)){
//               ans[0] = i;
//               ans[1] = map.get(rem);
//               return ans;
               return new int[]{i,map.get(rem)};
            }
            map.put(arr[i],i);
        }
        return new int [] {-1,-1};
    }

    public static int[] twoSum(int arr[],int target){
        int n = arr.length;
        for(int i=0;i<arr.length;i++){
            for(int j=i+1;j<arr.length;j++){
                if((arr[i] + arr[j]) == target){
                    return new int[]{i,j};
                }
            }
        }
        return new int []{-1,-1};
    }
}
