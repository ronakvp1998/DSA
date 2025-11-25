package com.questions.dailypractice;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int arr[] = {1,2,3};
        int target = 6;
        List<List<Integer>> res = new ArrayList<>();
//        combinationSum1(arr,0,res,new ArrayList<>(),target);
//        combinationSum2(arr,0,res,new ArrayList<>(),target);
        List<Integer> temp = new ArrayList<>();

        subsetSum1(arr,0,temp,0);
        System.out.println(temp);
    }

    public static void subsetSum1(int arr[], int index,List<Integer>res,  int sum){
        if(index == arr.length){
            res.add(sum);
            return;
        }
        subsetSum1(arr,index+1,res,sum + arr[index]);
        subsetSum1(arr,index+1,res,sum);

    }

    public static void combinationSum2(int arr[], int index, List<List<Integer>> res,List<Integer> temp,int target){

        if(target == 0){
                res.add(new ArrayList<>(temp));
                return;
        }
        for(int i=index;i<arr.length;i++){
            if(i > index && arr[i] == arr[i-1]){
                continue;
            }
            if(arr[i] > target){
                break;
            }
            temp.add(arr[i]);
            combinationSum2(arr,i+1,res,temp,target-arr[i]);
            temp.remove(temp.size()-1);
        }

    }

    public static void combinationSum1(int arr[], int index, List<List<Integer>> res,List<Integer> temp,int target){

        if(index == arr.length){
            if(target == 0){
                res.add(new ArrayList<>(temp));
            }
            return;
        }
        if(arr[index] <= target){
            temp.add(arr[index]);
            combinationSum1(arr,index,res,temp,target-arr[index]);
            temp.remove(temp.size()-1);
        }
        combinationSum1(arr,index+1,res,temp,target);

    }

}
