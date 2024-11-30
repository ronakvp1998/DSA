package com.questions.strivers.arrays.medium;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
//https://leetcode.com/problems/majority-element/submissions/1466270053/
// pg 5 book4
public class MajorityElement {

    public static void main(String[] args) {
        int[] arr = {8,9,8,9,8};
        System.out.println(majorityElement3(arr));

    }

    // moore's voting algorithm
    public static int majorityElement3(int arr[]) {
        int count = 0;
        int element = arr[0];
        int n = arr.length;

        for(int i=0;i<n;i++){
            if(count == 0){
                count = 1;
                element = arr[i];
            } else if (arr[i] == element) {
                count++;
            }else{
                count--;
            }
        }

            count = 0;
            for (int i = 0; i < n; i++) {
                if (arr[i] == element) {
                    count++;
                }
            }
            if (count > n / 2) {
                return element;
            }
        return -1;
    }


    // using java streams
    public static int majorityElement2(int arr[]){
        int n = arr.length;
        Map<Integer,Long> frequencyMap = Arrays.stream(arr)
                .boxed()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        return Math.toIntExact(frequencyMap.entrySet().stream()
                .filter(entry -> entry.getValue() > n / 2)
                .map(e -> e.getKey()).findFirst().get());
    }


    // hashing approach
    public static int majorityElement1(int arr[]){
        int n = arr.length;
        Map<Integer,Integer> map = new HashMap<>();

        for(int a : arr){
            map.put(a,map.getOrDefault(a,0)+1);
        }

        for(Map.Entry e : map.entrySet()){
            int count = (int) e.getValue();
            if(count > n/2){
                return (int)e.getKey();
            }
        }
        return -1;
    }

    // bruteforce approach
    public static int majorityElement(int arr[], int n) {
        for(int i=0;i<arr.length;i++){
            int count = 0;
            for(int j=i;j<arr.length;j++){
                if(arr[i] == arr[j]){
                    count++;
                }
            }
            if(n/2 < count){
                return arr[i];
            }
        }
        return -1;
    }

}
