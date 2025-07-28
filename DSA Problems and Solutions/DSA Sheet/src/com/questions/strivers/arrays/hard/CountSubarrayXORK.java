package com.questions.strivers.arrays.hard;

import java.util.HashMap;
import java.util.Map;
/*Problem Statement: Given an array of integers A and an integer B.
Find the total number of subarrays having bitwise XOR of all elements equal to k.
        Example 1:
        Input Format: A = [4, 2, 2, 6, 4] , k = 6
        Result: 4
        Explanation: The subarrays having XOR of their elements as 6 are  [4, 2], [4, 2, 2, 6, 4], [2, 2, 6], [6]

        Example 2:
        Input Format: A = [5, 6, 7, 8, 9], k = 5
        Result: 2
        Explanation: The subarrays having XOR of their elements as 5 are [5] and [5, 6, 7, 8, 9] */

public class CountSubarrayXORK {

    public static int subarraysWithXorK(int []a, int k) {
        int n = a.length; //size of the given array.
        int cnt = 0;

        // Step 1: Generating subarrays:
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {

                //step 2:calculate XOR of all
                // elements:
                int xorr = 0;
                for (int K = i; K <= j; K++) {
                    xorr = xorr ^ a[K];
                }

                // step 3:check XOR and count:
                if (xorr == k) cnt++;
            }
        }
        return cnt;
    }

    public static int subarraysWithXorK2(int []a, int k) {
        int n = a.length; //size of the given array.
        int cnt = 0;

        // Step 1: Generating subarrays:
        for (int i = 0; i < n; i++) {
            int xorr = 0;
            for (int j = i; j < n; j++) {

                //step 2:calculate XOR of all
                // elements:
                xorr = xorr ^ a[j];

                // step 3:check XOR and count:
                if (xorr == k) cnt++;
            }
        }
        return cnt;
    }

    public static int subarraysWithXorK3(int []a, int k) {
        int n = a.length; //size of the given array.
        int xr = 0;
        Map<Integer, Integer> mpp = new HashMap<>(); //declaring the map.
        mpp.put(xr, 1); //setting the value of 0.
        int cnt = 0;

        for (int i = 0; i < n; i++) {
            // prefix XOR till index i:
            xr = xr ^ a[i];

            //By formula: x = xr^k:
            int x = xr ^ k;

            // add the occurrence of xr^k
            // to the count:
            if (mpp.containsKey(x)) {
                cnt += mpp.get(x);
            }

            // Insert the prefix xor till index i
            // into the map:
            if (mpp.containsKey(xr)) {
                mpp.put(xr, mpp.get(xr) + 1);
            } else {
                mpp.put(xr, 1);
            }
        }
        return cnt;
    }


    public static void main(String[] args) {
        int[] a = {4, 2, 2, 6, 4};
        int k = 6;
        int ans = subarraysWithXorK(a, k);
        System.out.println("The number of subarrays with XOR k is: " + ans);
    }

}
