package com.questions.practice.recursion;

import java.util.*;

public class SubsetSum2 {

    public static void main(String[] args) {
        int arr[] = {1,2,2};
        Arrays.sort(arr);
        Set<List<Integer>> ans1 = new HashSet<>();
        subset2(0,arr,new ArrayList<>(),ans1);
        System.out.println(ans1);
//        List<List<Integer>>ans2 = new ArrayList<>();
//        subsetSum(0,arr,new ArrayList<>(),ans2);
//        System.out.println(ans2);
    }

    // pick and not pick
    // [[1, 2, 2], [1], [2, 2], [2], [], [1, 2]]
    public static void subset2(int index,int arr[], List<Integer>ds, Set<List<Integer>>ans){
        if(index == arr.length){
            ans.add(new ArrayList<>(ds));
            return;
        }
        ds.add(arr[index]);
        subset2(index+1,arr,ds,ans);
        ds.remove(ds.size()-1);
        subset2(index+1,arr,ds,ans);
    }

    // optimized
    // [[], [1], [1, 2], [1, 2, 2], [2], [2, 2]]
    public static void subsetSum(int index, int arr[], List<Integer> ds, List<List<Integer>> ans){
        ans.add(new ArrayList<>(ds));
        for(int i=index;i<arr.length;i++){
            if(i > index && arr[i] == arr[i-1]){
                continue;
            }
            ds.add(arr[i]);
            subsetSum(i+1,arr,ds,ans);
            ds.remove(ds.size()-1);
        }
    }
}
