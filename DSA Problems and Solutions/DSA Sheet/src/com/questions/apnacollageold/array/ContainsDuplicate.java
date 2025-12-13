package com.questions.apnacollageold.array;


import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.IntStream;

// 4  Given an integer array nums,
// return true if any value appears at least twice in the array,
// and return false if every element is distinc
public class ContainsDuplicate {

    public static void main(String[] args) {
        int arr[] = {1,2,3,11};
        System.out.println(containDuplicate(arr, arr.length));
;    }

    // using hashset frequency count TC O(n) SC O(N)
    private static void containDuplicateFrequencyCount(int [] arr){
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int a : arr){
            map.put(a,map.getOrDefault(a,0) + 1);
        }
        System.out.println(map);
    }

    // using stream api
    private static void containDuplicateUsingStream(int [] arr){
        HashSet<Integer> set = new HashSet<>();
        System.out.println(IntStream.of(arr).anyMatch(num -> !set.add(num)));

//        Arrays.stream(arr).boxed().collect(Collectors.counting(),)
    }

    // using sortingc TC O(nlogn)
    private static boolean containsDuplicateUsingSorting(int [] arr){
        Arrays.sort(arr);
        for(int i=1;i< arr.length;i++){
            if(arr[i] == arr[i-1]){
                return true;
            }
        }
        return false;
    }

    // using Hashset Tc O(N) SC O(N)
    private static boolean containDuplicateHashSet(int []arr){
        HashSet<Integer> set = new HashSet<>();
        for(int num : arr){
            if(!set.add(num)){
                return true;
            }
        }
        return false;
    }


    // brute force approach O(n2)
    private static boolean containDuplicate(int arr[] ,int n){
        for(int i=0;i<n-1;i++){
            for(int j=i+1;j<n;j++){
                if(arr[i] == arr[j]){
                    return true;
                }
            }
        }
        return false;
    }


}
