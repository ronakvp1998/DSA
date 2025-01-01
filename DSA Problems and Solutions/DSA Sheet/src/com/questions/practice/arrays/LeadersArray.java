package com.questions.practice.arrays;

import java.util.ArrayList;
import java.util.List;

public class LeadersArray {
    public static void main(String[] args) {
        int arr[] = {10,22,12,3,0,6};
        System.out.println(leaderArray(arr));
    }

    public static List<Integer> leaderArray(int arr[]){
        int element=arr[arr.length-1], maxElement=arr[arr.length-1];
        List<Integer> leaders = new ArrayList<>();
        leaders.add(element);
        for(int i= arr.length-2;i>=0;i--){
            if(arr[i] > maxElement){
                leaders.add(arr[i]);
                maxElement = arr[i];
            }
        }
        return leaders;
    }
}
