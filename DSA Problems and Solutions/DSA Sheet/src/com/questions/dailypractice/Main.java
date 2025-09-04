package com.questions.dailypractice;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int arr[] = {2,3};
        int target = 2;
        List<Integer> list = new ArrayList<>();
        test(0,arr,list,0);
        System.out.println(list);
    }

    public static void test(int index,int arr[], List<Integer>temp, int sum){
        if(index == arr.length){
            temp.add(sum);
            return;
        }
        test(index+1,arr,temp,sum+arr[index]);
        test(index+1,arr,temp,sum);

    }

}
