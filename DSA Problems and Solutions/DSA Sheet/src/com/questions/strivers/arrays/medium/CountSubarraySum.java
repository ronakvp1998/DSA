package com.questions.strivers.arrays.medium;

import java.util.HashMap;
import java.util.Map;

public class CountSubarraySum {

    public static void main(String[] args) {
        int arr[] = {1,2,3,-3,1,1,1,4,2,-3};
        int k = 3;
        System.out.println(countSubarray2(arr,k));
    }

    public static int countSubarray2(int arr[], int k){
        int n = arr.length; // size of the given array.
        Map mpp = new HashMap();
        int preSum = 0, cnt = 0;

        mpp.put(0, 1); // Setting 0 in the map.
        for (int i = 0; i < n; i++) {
            // add current element to prefix Sum:
            preSum += arr[i];

            // Calculate x-k:
            int remove = preSum - k;

            // Add the number of subarrays to be removed:
            cnt = cnt+ (int)mpp.getOrDefault(remove, 0);

            // Update the count of prefix sum
            // in the map.
            mpp.put(preSum, (int)mpp.getOrDefault(preSum, 0) + 1);
        }
        return cnt;
    }

    public static int countSubarray1(int arr[], int l){
        int count=0;
        for(int i=0;i<arr.length;i++){
            int sum = 0;
            for(int j=i;j<arr.length;j++){
                sum += arr[j];
                if(sum == l){
                    count++;
                }
            }
        }
            return count;
    }


    public static int countSubarray(int arr[] ,int l){
        int count = 0;
        for(int i=0;i<arr.length;i++){
            for(int j=i;j<arr.length;j++){
                int sum = 0;
                for(int k=i;k<=j;k++){
                    sum += arr[k];
                }
                if(sum == l){
                    count++;
                }
            }

        }
        return count;

    }
}
