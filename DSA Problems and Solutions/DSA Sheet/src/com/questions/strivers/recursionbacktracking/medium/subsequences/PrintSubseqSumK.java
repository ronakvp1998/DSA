package com.questions.strivers.recursionbacktracking.medium.subsequences;

import java.util.ArrayList;
import java.util.List;

public class PrintSubseqSumK {
    public static void main(String[] args) {
        int arr[] = {1,2,1};
        int sum = 2;
        printSubseqSumK(arr,sum,0,new ArrayList<>(),0);
    }

    // parametrized approach
    public static void printSubseqSumK(int arr[], int sum, int res, List<Integer> list, int index){
        if(index == arr.length){
            if(res == sum){
                System.out.println(list);
            }
            return;
        }
        list.add(arr[index]);
        res = res + arr[index];
        printSubseqSumK(arr,sum,res, list,index+1);
        list.remove(Integer.valueOf(arr[index]));
        res = res - arr[index];
        printSubseqSumK(arr,sum,res ,list,index+1);
    }
}
