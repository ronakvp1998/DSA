package com.questions.practice.slidingwindow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FruitsInBaskets {

    public static void main(String[] args) {
        int arr[] = {3,3,3,1,2,1,1,2,3,3,4};
        int k = 2;
        System.out.println(fruitBaskets2(arr,k));
    }

    // approach2
    public static int fruitBaskets2(int arr[],int k){
        int maxLen=0,l=0,r=0,n=arr.length;
        Map<Integer,Integer> map = new HashMap<>();
        while (r < n){
            map.put(arr[r],map.getOrDefault(arr[r],0)+1);
            while (map.size() > k){
                map.put(arr[l],map.get(arr[l])-1);
                if(map.get(arr[l]) == 0){
                    map.remove(arr[l]);
                }
                l++;
            }
            maxLen = Math.max(maxLen,r-l+1);
            r++;
        }
        return maxLen;
    }

    // approach1 brute force approach
    public static int fruitBaskets1(int arr[],int k){
        int maxLen=0;
        for(int i=0;i<arr.length;i++){
            Set<Integer> set = new HashSet<>();
            for(int j=i;j<arr.length;j++){
                set.add(arr[j]);
                if(set.size() > k){
                    break;
                }
                else if (set.size() <= k){
                    maxLen = Math.max(maxLen,j-i+1);
                }
            }
        }
        return maxLen;
    }
}
