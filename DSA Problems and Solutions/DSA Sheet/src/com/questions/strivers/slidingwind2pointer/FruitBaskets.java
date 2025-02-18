package com.questions.strivers.slidingwind2pointer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FruitBaskets {

    public static void main(String[] args) {
        int arr[] =  {3,3,3,1,2,1,1,2,3,3,4};
        int k = 3;
        System.out.println(fruitBaskets2(arr,k));
    }

    // approach2 using sliding window and 2 pointers
    public static int fruitBaskets2(int arr[],int k){
        int n = arr.length;
        int maxLen = 0, left = 0, right = 0;
        Map<Integer, Integer> map = new HashMap<>();
        while (right < n) {
            map.put(arr[right], map.getOrDefault(arr[right], 0) + 1);
            // Shrink the window if the map contains more than `k` distinct elements
            while (map.size() > k) {
                map.put(arr[left], map.get(arr[left]) - 1);
                if (map.get(arr[left]) == 0) {
                    map.remove(arr[left]);
                }
                left++;
            }
            // Update the maximum length of the subarray
            maxLen = Math.max(maxLen, right - left + 1);
            right++;
        }
        return maxLen;
    }

    // Approach1

    public static int fruitBasket1(int arr[], int k){
        int maxLen = 0;
        for(int i=0;i<arr.length;i++){
            Set<Integer> set = new HashSet<>();
            for(int j=i;j<arr.length;j++){
                set.add(arr[j]);
                if(set.size() <= 2) {
                    maxLen = Math.max(maxLen, j-i+1);
                }else{
                    break;
                }
            }
        }
        return maxLen;
    }

}
