package com.questions.dailypractice;

import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        int[] arr = {1, 1, 2};
        // Using brute-force method with HashSet
        System.out.println(removeDuplicates1(arr)); // Output: number of unique elements
        System.out.println(removeDuplicates2(arr)); // Output: number of unique elements

    }

    static int removeDuplicates2(int arr[]){
        int i=0;
        for(int j=1;j<arr.length;j++){
            if(arr[i] != arr[j]){
                i++;
                arr[i] = arr[j];
            }
        }
        return i+1;
    }


    static int removeDuplicates1(int arr[]){
        HashSet<Integer> set = new HashSet<>();
        for(int i=0;i<arr.length;i++){
            set.add(arr[i]);
        }
        int k = set.size();
        int j = 0;
        for(int x : set){
            arr[j++] = x;
        }
        return k;
    }

}
