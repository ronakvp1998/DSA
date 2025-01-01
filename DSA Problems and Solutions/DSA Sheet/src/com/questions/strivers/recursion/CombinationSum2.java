package com.questions.strivers.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSum2 {

    public static void main(String[] args) {
        int arr[] = {1,1,1,2,2,4};
        int target = 4;
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(arr);
//        combinationSum(0,arr,ans,new ArrayList<>(),target);
        System.out.println(ans);
    }

}
