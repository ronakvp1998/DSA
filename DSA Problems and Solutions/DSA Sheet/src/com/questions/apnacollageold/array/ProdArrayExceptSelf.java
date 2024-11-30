package com.questions.apnacollageold.array;
// pg 6
// code 12 :Product of Array Except Self
//Given an integer array nums, return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i].
//        The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
//        You must write an algorithm that runs in O(n) time and without using the division operation.

import java.util.Arrays;

public class ProdArrayExceptSelf {
    public static void main(String[] args) {
        int nums[] = {1,2,3,4};
//        productArrayExceptSelf(nums);
//        System.out.println(Arrays.toString(nums));

        System.out.println(Arrays.toString(productArrayExceptSelf4(nums)));
    }

    // 4 optimized using prefix and suffix array TC O(3n) SC O(1)
    public static int[] productArrayExceptSelf4(int arr[]){
        int temp[] = new int[arr.length];
        Arrays.fill(temp,1);
        // get prefix
        for (int i=1;i< arr.length;i++){
            temp[i] = temp[i-1] * arr[i-1];
        }
        int suffix = 1;
        // get suffix
        for (int i=arr.length-2;i>=0;i--){
            suffix *= arr[i+1];
            temp[i] *= suffix;
        }

        return temp;
    }



    // 3 optimized using prefix and suffix array TC O(3n) SC O(3n)
    public static int[] productArrayExceptSelf3(int arr[]){
        int suffix[] = new int[arr.length];
        int prefix[] = new int[arr.length];
        suffix[0] = 1;
        prefix[arr.length-1] = 1;
        // get prefix
        for (int i=1;i< arr.length;i++){
            suffix[i] = suffix[i-1] * arr[i-1];
        }
        // get suffix
        for (int i=arr.length-2;i>=0;i--){
            prefix[i] = prefix[i+1] * arr[i+1];
        }
        int temp[] = new int[arr.length];
        for(int i =0;i<arr.length;i++){
            temp[i] = suffix[i] * prefix[i];
        }

        return temp;
    }

    // 2 bruteforce approach without division method
    public static int[] productArrayExceptSelf2(int arr[]){
        int temp[] = new int[arr.length];
        Arrays.fill(temp,1);
        for(int i=0;i< arr.length;i++){
            for(int j=0;j< arr.length;j++){
                if(i != j){
                    temp[i] *= arr[j];
                }
            }
        }
        return temp;

//        int temp[] = new int[arr.length];
//        for(int i=0;i< arr.length;i++){
//            int product = 1;
//            for(int j=0;j< arr.length;j++){
//                if(i == j){
//                    continue;
//                }
//                product *= arr[j];
//            }
//            temp[i] = product;
//        }
//        return temp;

    }


    // 1 bruteforce approach using divide method
    public static void productArrayExceptSelf(int arr[]){
        int product = 1;
        for(int a : arr){
            product *= a;
        }
        for(int i =0;i< arr.length;i++){
            arr[i] = product/arr[i];
        }
    }

}
