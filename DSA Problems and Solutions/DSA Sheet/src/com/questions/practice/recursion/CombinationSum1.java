package com.questions.practice.recursion;

import java.util.ArrayList;

public class CombinationSum1 {
    public static void main(String[] args) {
        int arr[] = {2,3,6,7};
        int target = 7;
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        combinationSum(0,arr,ans,new ArrayList<>(),target,0);
        System.out.println(ans);
    }

    private static void combinationSum(int index, int arr[],
                                      ArrayList<ArrayList<Integer>> ans,
                                      ArrayList<Integer>ds,int target,int sum){
        if(index == arr.length){
            if(sum == target){
                ans.add(new ArrayList<>(ds));
            }
            return;
        }
        if(sum <= target){
            ds.add(arr[index]);
            combinationSum(index,arr,ans,ds,target,sum+arr[index]);
            ds.remove(ds.size()-1);
        }
        combinationSum(index+1,arr,ans,ds,target,sum);

    }
}
