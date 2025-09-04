package com.questions.strivers.slidingwind2pointer;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        int arr[] = {2,3,6,7};
        int target = 7;
        List<List<Integer>>list = new ArrayList<>();
        combinationSum(arr,0,new ArrayList<>(),list,target,0);
        System.out.println(list);
    }

    public static void combinationSum(int arr[], int index,List<Integer> temp ,
                                      List<List<Integer>> list,int target,int sum){
        if(index == arr.length){
            if(sum == target){
                list.add(new ArrayList<>(temp));
            }
            return;
        }
        if(sum + arr[index] <= target) {
            sum = sum + arr[index];
            temp.add(arr[index]);
            combinationSum(arr,index,temp,list,target,sum);
            temp.remove(temp.size()-1);
            sum = sum - arr[index];
        }
            combinationSum(arr,index+1,temp,list,target,sum);
        return ;


    }



}
