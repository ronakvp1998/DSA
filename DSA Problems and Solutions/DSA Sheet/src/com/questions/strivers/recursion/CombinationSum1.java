package com.questions.strivers.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*Combination Sum - 1
//        Given an array of distinct integers and a target, you have to return the list of all unique combinations
//        where the chosen numbers sum to target. You may return the combinations in any order.
//        The same number may be chosen from the given array an unlimited number of times.
//        Two combinations are unique if the frequency of at least one of the chosen numbers is different.
//        It is guaranteed that the number of unique combinations that sum up to target is less than 150 combinations for
//        the given input.
Example 1:
Input: array = [2,3,6,7], target = 7
Output: [[2,2,3],[7]]
Explanation: 2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times.
             7 is a candidate, and 7 = 7.
             These are the only two combinations.
Example 2:
Input: array = [2], target = 1
Output: []
Explaination: No combination is possible.

*/
public class CombinationSum1 {

    public static void main(String[] args) {
        int arr[] = {2,3,6,1,2,2,3,7};
        int target = 6;
        List<List<Integer>> ans = new ArrayList<>();
        combinationSum(0,arr,ans,new ArrayList<>(),target,0);
        System.out.println(ans);
    }

    public static void combinationSum(int index, int[] arr,List<List<Integer>>ans, List<Integer> ds,int target,int sum){
        if(index >= arr.length){
            if(sum == target){
                ans.add(new ArrayList<>(ds));
            }
            return;
        }
        if(sum <= target){
            ds.add(arr[index]);
            combinationSum(index,arr,ans,ds,target,sum + arr[index]);
            ds.remove(ds.size()-1);
        }
        combinationSum(index+1,arr,ans,ds,target,sum);
    }

}
