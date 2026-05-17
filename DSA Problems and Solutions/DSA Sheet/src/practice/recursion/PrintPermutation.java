package com.questions.practice.recursion;

import java.util.ArrayList;
import java.util.List;

public class PrintPermutation {
    public static void main(String[] args) {
        int arr[] = {1,2,3};
        List<List<Integer>>ans = new ArrayList<>();
        boolean []freq = new boolean[arr.length];
        permutation1(arr,new ArrayList<>(),ans,freq);
        System.out.println(ans);
    }

    private static void permutation1(int []arr, List<Integer>ds,
                                    List<List<Integer>>ans, boolean[] freq){

        if(ds.size()==arr.length){
            ans.add(new ArrayList<>(ds));
            return;
        }

        for(int i=0;i<arr.length;i++){
            if(!freq[i]){
                freq[i] = true;
                ds.add(arr[i]);
                permutation1(arr,ds,ans,freq);
                ds.remove(ds.size()-1);
                freq[i] = false;
            }
        }

    }

}
