package com.questions.strivers.slidingwind2pointer.length;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
/*
L5 Fruit Into Baskets
        There is only one row of fruit trees on the farm, oriented left to right. An integer array called fruits
        represents the trees, where fruits[i] denotes the kind of fruit produced by the ith tree.
        The goal is to gather as much fruit as possible, adhering to the owner's stringent rules:
        1) There are two baskets available, and each basket can only contain one kind of fruit.
        The quantity of fruit each basket can contain is unlimited.
        2) Start at any tree, but as you proceed to the right, select exactly one fruit from each tree,
        including the starting tree. One of the baskets must hold the harvested fruits.
        3) Once reaching a tree with fruit that cannot fit into any basket, stop.
        Return the maximum number of fruits that can be picked.
        Examples: Input : fruits = [1, 2, 1] Output : 3
        Explanation : We will start from first tree.
        The first tree produces the fruit of kind '1' and we will put that in the first basket.
        The second tree produces the fruit of kind '2' and we will put that in the second basket.
        The third tree produces the fruit of kind '1' and we have first basket that is already holding fruit of kind '1'.
         So we will put it in first basket.
        Hence we were able to collect total of 3 fruits.
        Input : fruits = [1, 2, 3, 2, 2] Output : 4
        Explanation : we will start from second tree.
        The first basket contains fruits from second , fourth and fifth.
        The second basket will contain fruit from third tree.
        Hence we collected total of 4 fruits. */

// find out max len consecutive subarray with at most 2 types of numbers

public class FruitBaskets {

    public static void main(String[] args) {
        int arr[] =  {3,3,3,1,2,1,1,2,3,3,4};
        int k = 3;
//        System.out.println(fruitBaskets1(arr,k));
        System.out.println(fruitBaskets2(arr,k));
    }

    // approach2 using sliding window and 2 pointers
    public static int fruitBaskets2(int arr[],int k){
        int n = arr.length;
        int maxLen = 0,left=0,right=0;
        Map<Integer,Integer> map = new HashMap<>();
        while (right < n){
            map.put(arr[right],map.getOrDefault(arr[right],0) + 1);
            while (map.size() > k){
                map.put(arr[left],map.get(arr[left]) - 1);
                if(map.get(arr[left]) == 0){
                    map.remove(arr[left]);
                }
                left++;
            }
            maxLen = Math.max(maxLen, right - left + 1);
            right++;
        }
        return maxLen;
    }

    // Approach1 generate all the subarrays,use set DS
    public static int fruitBaskets1(int arr[], int k){
        int maxLen = 0;
        for(int i=0;i<arr.length;i++){
            Set<Integer> set = new HashSet<>();
            for(int j=i;j<arr.length;j++){
                set.add(arr[j]);
                if(set.size() <= 2){
                    maxLen = Math.max(maxLen,j - i + 1);
                }else{
                    break;
                }
            }
        }
        return maxLen;
    }

}
