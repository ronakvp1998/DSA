package com.questions.strivers.arrays.medium;

import java.util.*;
import java.util.stream.Collectors;

// pf 8 book4
public class LeaderInArray {
    public static void main(String[] args) {
        int arr[] = {10,22,12,3,0,6};
        System.out.println(leaderArray(arr));
    }

    // optimized approach
    public static List<Integer> leaderArray1(int arr[]){
        List<Integer> res = new ArrayList<>();
        int maxElement = arr[arr.length-1];
        for(int i=arr.length-1;i>=0;i--){
            if(arr[i] >= maxElement){
                res.add(maxElement);
            }
        }
        Collections.reverse(res);
        return res;
    }


    // brute force approach
    public static List<Integer> leaderArray(int arr[]){
        Set<Integer> res = new HashSet<>();
        for(int i=0;i< arr.length;i++){
            int leader = arr[i];
            boolean flage = true;
            for(int j=i+1;j< arr.length;j++){
                if(arr[i] < arr[j]){
                    flage = false;
                }
            }
            if(flage){
                res.add(leader);
            }
        }
        return res.stream().sorted().collect(Collectors.toList());
    }
}
