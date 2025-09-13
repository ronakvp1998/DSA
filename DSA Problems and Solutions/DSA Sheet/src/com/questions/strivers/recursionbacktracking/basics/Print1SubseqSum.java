package com.questions.strivers.recursionbacktracking.basics;

import java.util.ArrayList;
import java.util.List;

public class Print1SubseqSum {

    public static void main(String[] args) {
        int arr[] = {1,2,1};
        int sum = 2;
        printSub(0,new ArrayList<>(),0,sum,arr, arr.length);
    }


    // functional approach
    public static boolean printSub(int index, List<Integer>list,int res, int sum,int arr[],int n){
        if(index == n){
            if(res == sum){
                System.out.println(list);
                return true;
            }else{
                return false;
            }
        }

        res = res + arr[index];
        list.add(arr[index]);
        if(printSub(index+1,list,res,sum,arr,n) == true) return true;

        res = res - arr[index];
        list.remove(Integer.valueOf(arr[index]));
        if(printSub(index+1,list,res,sum,arr,n) == true) return true;

        return false;
    }

}
