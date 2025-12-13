package com.questions.strivers.arrays.medium;
//https://takeuforward.org/data-structure/find-the-majority-element-that-occurs-more-than-n-2-times/
/*Problem Statement: Given an array of N integers,
        write a program to return an element that occurs more than N/2 times in the given array.
        You may consider that such an element always exists in the array.

        Example 1:
        Input Format: N = 3, nums[] = {3,2,3}
        Result: 3
        Explanation: When we just count the occurrences of each number and compare with half of the size of the array, you will get 3 for the above solution.

        Example 2:
        Input Format:  N = 7, nums[] = {2,2,1,1,1,2,2}
        Result: 2
        Explanation: After counting the number of times each element appears and comparing it with half of array size, we get 2 as result.

        Example 3:
        Input Format:  N = 10, nums[] = {4,4,2,4,3,4,4,3,2,4}
        Result: 4*/

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MajorityElementsN2 {

    public static void main(String[] args) {
        int[] arr = {8, 9, 8, 9, 8};

        // Calling the best version that handles both cases
        System.out.println(majorityElement3(arr));
    }

    // ✅ 1. Moore's Voting Algorithm (When majority element is guaranteed to exist)
    // Time: O(N), Space: O(1)
    private static int majority2(int n, int arr[]) {
        int res = arr[0]; // Assume first element is the majority
        int count = 1;

        for (int i = 1; i < arr.length; i++) {
            if (count == 0) {
                res = arr[i];  // Reset to current element
            }

            if (arr[i] == res) {
                count++;      // Same element increases confidence
            } else {
                count--;      // Different element reduces confidence
            }
        }

        return res;  // Since majority always exists, return candidate directly
    }

    // ✅ 2. Moore's Voting Algorithm (Validation step added: when majority MAY NOT exist)
    // Time: O(N) + O(N) = O(N), Space: O(1)
    private static int majorityElement3(int arr[]) {
        int count = 0;
        int element = arr[0];
        int n = arr.length;

        // Phase 1: Find candidate
        for (int i = 0; i < n; i++) {
            if (count == 0) {
                count = 1;
                element = arr[i];  // Reset candidate
            } else if (arr[i] == element) {
                count++;
            } else {
                count--;
            }
        }

        // Phase 2: Verify if candidate is really majority
        count = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] == element) {
                count++;
            }
        }

        if (count > n / 2) return element;
        return -1; // If no majority
    }

    // ✅ 3. Java Streams Approach
    // Time: O(N), Space: O(N)
    private static int majorityElement2(int arr[]) {
        int n = arr.length;

        // Group elements by value and count occurrences using Streams
        Map<Integer, Long> frequencyMap = Arrays.stream(arr)
                .boxed()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        // Find first element with count > n/2
        return Math.toIntExact(frequencyMap.entrySet().stream()
                .filter(entry -> entry.getValue() > n / 2)
                .map(Map.Entry::getKey)
                .findFirst().get());
    }

    // ✅ 4. HashMap Frequency Count
    // Time: O(N), Space: O(N)
    private static int majorityElement1(int arr[]) {
        int n = arr.length;
        Map<Integer, Integer> map = new HashMap<>();

        // Count frequency of each element
        for (int a : arr) {
            map.put(a, map.getOrDefault(a, 0) + 1);
        }

        // Find the majority element
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > n / 2) {
                return entry.getKey();
            }
        }

        return -1;
    }

    // ❌ 5. Brute-force approach (Nested loop)
    // Time: O(N^2), Space: O(1)
    private static int majorityElement(int arr[], int n) {
        for (int i = 0; i < arr.length; i++) {
            int count = 0;

            // Count occurrences of arr[i]
            for (int j = 0; j < arr.length; j++) {
                if (arr[i] == arr[j]) {
                    count++;
                }
            }

            if (count > n / 2) {
                return arr[i];
            }
        }

        return -1;
    }
}
