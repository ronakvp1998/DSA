package com.questions.strivers.arrays.easy;

/*
Problem Statement: Given an integer N and an array of size N-1 containing N-1 numbers between 1 to N.
Find the number(between 1 to N), that is not present in the given array.

Examples:

Example 1:
Input Format: N = 5, array[] = {1,2,4,5}
Result: 3
Explanation: In the given array, number 3 is missing. So, 3 is the answer.

Example 2:
Input Format: N = 3, array[] = {1,3}
Result: 2
Explanation: In the given array, number 2 is missing. So, 2 is the answer.
*/

public class MissingNumber {

    public static void main(String[] args) {
        int arr[] = {3,0,1};
        System.out.println(findMissing(arr));
    }

    // Approach 1: Brute Force - Nested Loop Search
    // Time Complexity: O(N^2) -> For each number from 1..N, we scan the array
    // Space Complexity: O(1)  -> No extra space used
    private static int missingNumber(int []a, int N) {
        for (int i = 1; i <= N; i++) {
            int flag = 0;
            for (int j = 0; j < N - 1; j++) {
                if (a[j] == i) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) return i;
        }
        return -1;
    }

    // Approach 2: Hashing (Frequency Array)
    // Time Complexity: O(N) -> Single pass to fill hash, single pass to find missing number
    // Space Complexity: O(N) -> Extra array to store frequencies
    private static int missingNumber1(int []a, int N) {
        int hash[] = new int[N + 1];
        for (int i = 0; i < N - 1; i++)
            hash[a[i]]++;
        for (int i = 1; i <= N; i++) {
            if (hash[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    // Approach 3: Sum Formula
    // Time Complexity: O(N) -> One pass to calculate array sum
    // Space Complexity: O(1) -> No extra space used
    private static int missingNumber2(int []a, int N) {
        int sum = (N * (N + 1)) / 2;
        int s2 = 0;
        for (int i = 0; i < N - 1; i++) {
            s2 += a[i];
        }
        return sum - s2;
    }

    // Approach 4: XOR Method
    // Time Complexity: O(N) -> Single pass over array
    // Space Complexity: O(1) -> No extra space used
    private static int missingNumber3(int []a, int N) {
        int xor1 = 0, xor2 = 0;
        for (int i = 0; i < N - 1; i++) {
            xor2 ^= a[i];      // XOR of array elements
            xor1 ^= (i + 1);   // XOR from 1..N-1
        }
        xor1 ^= N; // XOR up to N
        return xor1 ^ xor2;
    }

    // Variation: Using Sum Formula (Zero-indexed array input)
    // Time Complexity: O(N)
    // Space Complexity: O(1)
    private static int findMissing(int arr[]) {
        int n = arr.length;
        int reqSum = (n) * (n + 1) / 2;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return reqSum - sum;
    }
}
