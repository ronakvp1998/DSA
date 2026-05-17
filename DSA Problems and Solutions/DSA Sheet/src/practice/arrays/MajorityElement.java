package com.questions.practice.arrays;

import java.util.HashMap;
import java.util.Map;

public class MajorityElement {

    public static void main(String[] args) {
        int arr[] = {7,7,5,7,5,1,5,7,5,5,7,7,5,5,5,5};
        int n = arr.length;
        System.out.println(majorityElement1(arr,n));
    }

    private static int majorityElement1(int arr[], int n){
        int count = 0,ans = 0;
        for(int i=0;i<arr.length;i++) {
            if (count == 0) {
                ans = arr[i];
                count++;
            } else if (arr[i] == ans) {
                count++;
            } else {
                count--;
            }
        }
        if (count > 0) {
            count = 0;
            for(int i=0;i<n;i++){
                if(arr[i] == ans){
                    count++;
                }
            }
            if(count > n/2){
                return ans;
            }else{
                return -1;
            }
        }else {
            return -1;
        }
    }

    private static int majorityElement(int arr[] ,int n){
        Map<Integer, Integer> map = new HashMap<>();
        for(int i=0;i<n;i++){
            int count = map.getOrDefault(arr[i],0)+1;
            map.put(arr[i],count);
            if(count > (n/2)){
                return arr[i];
            }
        }
        return -1;
    }
}
