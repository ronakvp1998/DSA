package com.questions.strivers.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*Combination Sum II - Find all unique combinations In this article we will solve the most asked interview question
Problem Statement: Given a collection of candidate numbers (candidates) and a target number (target),
find all unique combinations in candidates where the candidate numbers sum to target.
Each number in candidates may only be used once in the combination.
Note: The solution set must not contain duplicate combinations.
Example 1: Input: candidates = [10,1,2,7,6,1,5], target = 8
Output: [[1,1,6],[1,2,5],[1,7],[2,6]]
Explanation: These are the unique combinations whose sum is equal to target.
Example 2:
Input: candidates = [2,5,2,1,2], target = 5
Output: [[1,2,2],[5]]
Explanation: These are the unique combinations whose sum is equal to target.*/

public class CombinationSum2 {

    public static void main(String[] args) {
        int arr[] = {1,1,1,2,2,4};
        int target = 4;
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(arr);
        combinationSum2(0,arr,ans,new ArrayList<>(),target);
        System.out.println(ans);
    }

    public static void combinationSum2(int index,int arr[], List<List<Integer>>ans, List<Integer>ds, int target){
        if(target == 0){
            ans.add(new ArrayList<>(ds));
            return;
        }
        for(int i=index;i<arr.length;i++){
            if(i > index && arr[i] == arr[i-1]){
                continue;
            }
            if(arr[i] > target){
                break;
            }
            ds.add(arr[i]);
            combinationSum2(i+1,arr,ans,ds,target-arr[i]);
            ds.remove(ds.size()-1);
        }

    }
}
