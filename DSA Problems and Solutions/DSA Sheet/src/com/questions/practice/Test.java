package com.questions.practice;

import java.util.Arrays;

public class Test {

    public static void merge(int[] arr1, int m, int[] arr2, int n) {
        int[] arr3 = new int[n + m];
        int left = 0, right = 0, index = 0;

        // Merge both arrays into arr3
        while (left < m && right < n) {
            if (arr1[left] <= arr2[right]) {
                arr3[index++] = arr1[left++];
            } else {
                arr3[index++] = arr2[right++];
            }
        }

        // Copy remaining elements
        while (left < m) {
            arr3[index++] = arr1[left++];
        }
        while (right < n) {
            arr3[index++] = arr2[right++];
        }
        arr1 = arr3;
        System.out.println(Arrays.toString(arr1));
    }

    public static void main(String[] args) {
        int[] arr1_tc1 = {1, 4, 8, 10};
        int[] arr2_tc1 = {2, 3, 9};
        merge(arr1_tc1,arr2_tc1.length,arr2_tc1,arr2_tc1.length);
    }
}
