package com.questions.practice.recursion;

import java.util.ArrayList;
import java.util.List;

public class SubsetSum1 {

    public static void main(String[] args) {
        int arr[] = {2,3};
        subSetSum1(0,arr,new ArrayList<>(),0);
    }

    public static void subSetSum1(int index, int arr[],
                                  List<Integer>ds,int sum){

        if(index == arr.length){
            ds.add(sum);
            System.out.println(ds);
            return;
        }
        subSetSum1(index+1,arr,ds,sum+arr[index]);
        subSetSum1(index+1,arr,ds,sum);
    }
}
