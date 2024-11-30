package com.questions.strivers.arrays.medium;

import java.util.Arrays;
import java.util.HashMap;
//https://leetcode.com/problems/two-sum/description/
// pg 1  book4
public class Sum2Problem {

    public static void main(String[] args) {
        int arr[] = {2,6,4,8,11};
        int target = 14;
        System.out.println(Arrays.toString(find2Sum2(arr,target)));
    }

    // optimizes approach using 2 pointer
    public static int[] find2Sum2(int arr[], int target){
        // sort the array
        Arrays.sort(arr);
        int res[] = new int[2];
        int i=0, j= arr.length-1;
        while (i<j){
            int temp = target - (arr[i] + arr[j]);
            if(temp == 0){
                res[0] = i;
                res[1] = j;
            }if(temp>0){
                i++;
            }else{
                j--;
            }
        }
        return res;
    }

    // optimized approach 1 using hashmap
    public static int[] find2Sum1(int arr[], int target) {
        HashMap<Integer,Integer> map = new HashMap<>();
        int res[] = new int[2];
        for(int i=0;i<arr.length;i++){
            Integer rem = target - arr[i];
            if(map.get(rem) == null){
                map.put(arr[i],i);
            }else{
                res[0] = i;
                res[1] = map.get(rem);
            }
        }
        return res;
    }
    // brute force approach
    public static void find2Sum(int arr[], int target){
        for(int i=0;i<arr.length;i++){
            for(int j=i+1;j<arr.length;j++){
                if((arr[i] + arr[j]) == target){
                    System.out.println("yes");
                    System.out.println("i=" + i + ", j=" + j);
                    return;
                }
            }
        }
        System.out.println("no");
    }


}
