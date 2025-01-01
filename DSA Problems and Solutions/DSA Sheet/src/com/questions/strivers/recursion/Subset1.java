package com.questions.strivers.recursion;

import java.util.ArrayList;

public class Subset1 {

    public static void main(String[] args) {
        int arr[] = {2,3};
        int n = 2;
        subsetSum(0,0,new ArrayList<>(),n,new ArrayList<>());
    }

    public static void subsetSum(int index,int sum, ArrayList<Integer> arr,
                                 int n, ArrayList<Integer> subsets){
        if(index == n){
            subsets.add(sum);
            return;
        }

        subsetSum(index+1,sum+arr.get(index),arr,n,subsets);
        subsetSum(index+1,sum,arr,n,subsets);
    }
}
