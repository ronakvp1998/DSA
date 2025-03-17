package com.questions.practice.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class CombinationSum2 {

    public static void main(String[] args) {
        int arr[] = {10,1,2,7,6,1,5};
        int target = 8;
        Arrays.sort(arr);
//        HashSet<ArrayList<Integer>> ans = new HashSet<>();
        List<List<Integer>>ans1 = new ArrayList<>();
//        combinationSum1(0,arr,ans,new ArrayList<>(),0,target);
        combinationSum2(0,arr,ans1,new ArrayList<>(),target);
        System.out.println(ans1);
//        ans1.addAll(ans);
//        System.out.println(ans1);
    }

    public static void combinationSum2(int index, int arr[], List<List<Integer>>ans,
                                       ArrayList<Integer>ds, int target){
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


    public static void combinationSum1(int index, int arr[], HashSet<ArrayList<Integer>>ans,
                                       ArrayList<Integer>ds,int sum, int target){

        if(index == arr.length){
            if(sum == target){
                ans.add(new ArrayList<>(ds));
            }
            return;
        }

        if(sum <= target){
            ds.add(arr[index]);
            combinationSum1(index+1,arr,ans,ds,sum+arr[index],target);
            ds.remove(ds.size()-1);

        }
        combinationSum1(index+1,arr,ans,ds,sum,target);
    }

}
