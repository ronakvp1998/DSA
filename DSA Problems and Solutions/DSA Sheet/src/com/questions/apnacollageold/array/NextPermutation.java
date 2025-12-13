package com.questions.apnacollageold.array;

import java.util.Arrays;
// pg 1
// 7 next permutation
// A permutation of an array of integers is an arrangement of its members into a sequence or linear order.
// For example, for arr = [1,2,3], the following are all the permutations of arr: [1,2,3], [1,3,2], [2, 1, 3], [2, 3, 1], [3,1,2], [3,2,1].
// The next permutation of an array of integers is the next lexicographically greater permutation of its integer. More formally, if all the permutations of the array are sorted in one container according to their lexicographical order, then the next permutation of that array is the permutation that follows it in the sorted container. If such arrangement is not possible, the array must be rearranged as the lowest possible order (i.e., sorted in ascending order).
// For example, the next permutation of arr = [1,2,3] is [1,3,2]. Similarly, the next permutation of arr = [2,3,1] is [3,1,2].
// While the next permutation of arr = [3,2,1] is [1,2,3] because [3,2,1] does not have a lexicographical larger rearrangement.
// Example 1:
//
//        Input: nums = [1,2,3]
//        Output: [1,3,2]
//        Example 2:
//
//        Input: nums = [3,2,1]
//        Output: [1,2,3]
//        Example 3:
//
//        Input: nums = [1,1,5]
//        Output: [1,5,1]
public class NextPermutation {

    public static void main(String[] args) {
//        allPermutation("123","");
        int arr[] = {1,2,24,14,3};
        nextPermutation(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void nextPermutation(int arr[]){
        // step 1 find the pivot
        int n = arr.length-1;
        int pivot = -1;
        for(int i = n-1;i>=0;i--){
            if(arr[i] < arr[i+1]){
                pivot = i;
                break;
            }
        }
        // step 2 if pivot is -1 that means next permutation does not exists
        // simply reverse the array
        if (pivot == -1){
            // reverse the array and return
            reverse(arr,0,n);
            return;
        }

        // step 3 find the next largest element w.r.t pivot
        for(int i=n;i>pivot;i--){
            if(arr[i] > arr[pivot]){
                swap(arr,i,pivot);
                break;
            }
        }

        // step 4 reverse element from pivot+1 to n-1
        int i = pivot+1;
        int j = n;
        reverse(arr,i,j);
    }

    private static void reverse(int arr[],int i, int j){
        while(i <= j){
            swap(arr,i,j);
            i++;
            j--;
        }
    }

    private static void swap(int arr[], int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void allPermutation(String s,String ans){
        if(s.length() == 0){
            System.out.println(ans);
            return;
        }

        for (int i=0;i<s.length();i++){
            char curr = s.charAt(i);
            String newStr = s.substring(0,i) + s.substring(i+1);
            allPermutation(newStr,ans+curr);
        }
    }

}
