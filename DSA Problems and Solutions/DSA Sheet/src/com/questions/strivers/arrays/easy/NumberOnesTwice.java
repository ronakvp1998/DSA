package com.questions.strivers.arrays.easy;

import java.util.HashMap;
import java.util.Map;

/*
 Problem Statement:
 Given a non-empty array of integers, every element appears twice except for one.
 Find that single one.

 Examples:
 Input: arr[] = {2, 2, 1}
 Output: 1

 Input: arr[] = {4, 1, 2, 1, 2}
 Output: 4
*/

public class NumberOnesTwice {

    /**
     * Approach 1: Brute Force (Check frequency of each element)
     * - For each element, count how many times it appears in the array.
     * - Return the one that appears only once.
     *
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     */
    public static int getSingleElement1(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int num = arr[i];
            int cnt = 0;
            for (int j = 0; j < n; j++) {
                if (arr[j] == num) cnt++;
            }
            if (cnt == 1) return num;
        }
        return -1; // Should never reach here if input is valid
    }

    /**
     * Approach 2: Frequency Array
     * - Use an array to count occurrences (works if values are positive and small).
     *
     * Time Complexity: O(n + max(arr[i]))
     * Space Complexity: O(max(arr[i]))
     */
    public static int getSingleElement2(int[] arr) {
        int n = arr.length;
        int maxi = arr[0];
        for (int num : arr) maxi = Math.max(maxi, num);

        int[] hash = new int[maxi + 1];
        for (int num : arr) hash[num]++;

        for (int num : arr) {
            if (hash[num] == 1) return num;
        }
        return -1;
    }

    /**
     * Approach 3: HashMap
     * - Store the frequency of each number in a map.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public static int getSingleElement3(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) return entry.getKey();
        }
        return -1;
    }

    /**
     * Approach 4: XOR Method (Optimal)
     * - XOR of two equal numbers is 0, XOR with 0 gives the number itself.
     * - XORing all numbers will cancel out duplicates and leave the single number.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static int getSingleElement4(int[] arr) {
        int xorr = 0;
        for (int num : arr) {
            xorr ^= num;
        }
        return xorr;
    }

    public static void main(String[] args) {
        int[] arr = {4, 1, 2, 1, 2};

        System.out.println("Approach 1 (Brute Force): " + getSingleElement1(arr));
        System.out.println("Approach 2 (Frequency Array): " + getSingleElement2(arr));
        System.out.println("Approach 3 (HashMap): " + getSingleElement3(arr));
        System.out.println("Approach 4 (XOR): " + getSingleElement4(arr));
    }
}
