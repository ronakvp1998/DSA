package com.questions.strivers.recursion.medium.subsequences;

import java.util.ArrayList;
import java.util.List;

public class PrintAllSubsequence {
    public static void main(String[] args) {
        int arr[] = {3, 1, 2};
        printSubseq(arr, new ArrayList<>(), 0);
    }


    // parametrises approach
    public static void printSubseq(int arr[], List<Integer> list, int index){
        if(index >= arr.length){
            System.out.println(list);
            return;
        }
        list.add(arr[index]);
        printSubseq(arr,list,index+1);
        list.remove(Integer.valueOf(arr[index]));
        printSubseq(arr,list,index+1);
    }

}