package com.questions.dailypractice;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int arr[] = {1,2,3};
        List<List<Integer>> list = new ArrayList<>();
        test(arr,list,new ArrayList<>(),new boolean[arr.length]);
        System.out.println(list);
    }

    public static void test(int arr[], List<List<Integer>> list,List<Integer>temp,boolean[] freq){
        if(temp.size() == arr.length){
            list.add(new ArrayList<>(temp));
            return;
        }
        for(int i=0;i<arr.length;i++){
            if(!freq[i]){
                freq[i] = true;
                temp.add(arr[i]);
                test(arr,list,temp,freq);
                temp.remove(temp.size()-1);
                freq[i] = false;
            }
        }
    }

}
