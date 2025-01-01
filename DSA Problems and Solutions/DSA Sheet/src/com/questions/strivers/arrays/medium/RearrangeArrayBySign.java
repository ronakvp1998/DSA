package com.questions.strivers.arrays.medium;

//https://leetcode.com/problems/rearrange-array-elements-by-sign/description/
// pg 6 book4

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RearrangeArrayBySign {
    public static void main(String[] args) {
//        int arr[] = {3,1,-2,-5,2,-4};
//        System.out.println(Arrays.toString(rearrangeArray3(arr)));

        //  the arr number of pos and neg are not same
        Integer arr[] = {1,2,-4,-5,3,-6,7};
        System.out.println(RearrangebySign(Arrays.asList(arr),arr.length));
    }

    // 2nd variant the arr number of pos and neg are not same
    public static List<Integer> RearrangebySign(List<Integer> A, int n) {
        // Define 2 ArrayLists, one for storing positive
        // and other for negative elements of the array.
        ArrayList<Integer> pos = new ArrayList<>();
        ArrayList<Integer> neg = new ArrayList<>();

        // Segregate the array into positives and negatives.
        for (int i = 0; i < n; i++) {
            if (A.get(i) > 0)
                pos.add(A.get(i));
            else
                neg.add(A.get(i));
        }

        // If positives are lesser than the negatives.
        if (pos.size() < neg.size()) {

            // First, fill array alternatively till the point
            // where positives and negatives are equal in number.
            for (int i = 0; i < pos.size(); i++) {
                A.set(2 * i, pos.get(i));
                A.set(2 * i + 1, neg.get(i));
            }

            // Fill the remaining negatives at the end of the array.
            int index = pos.size() * 2;
            for (int i = pos.size(); i < neg.size(); i++) {
                A.set(index, neg.get(i));
                index++;
            }
        }

        // If negatives are lesser than the positives.
        else {
            // First, fill array alternatively till the point
            // where positives and negatives are equal in number.
            for (int i = 0; i < neg.size(); i++) {
                A.set(2 * i, pos.get(i));
                A.set(2 * i + 1, neg.get(i));
            }

            // Fill the remaining positives at the end of the array.
            int index = neg.size() * 2;
            for (int i = neg.size(); i < pos.size(); i++) {
                A.set(index, pos.get(i));
                index++;
            }
        }
        return A;
    }


    // optimized approach 1
    public static int[] rearrangeArray3(int arr[]){
        int temp[] = new int[arr.length];
        int pos = 0, neg = 1;
        for(int i=0;i<arr.length;i++){
            if(arr[i] > 0){
                temp[pos] = arr[i];
                pos = pos+2;
            }else{
                temp[neg] = arr[i];
                neg = neg+2;
            }

        }
        return temp;
    }



    // bruteforce approach using 2 arrays TC->O(2n) SC->(2n)
    public static int [] rearrangeArray2(int arr[]){
        int pos[] = new int[arr.length/2];
        int neg[] = new int [arr.length/2];

        int a=0,b=0,c=0;
        while (c < arr.length){
            if(arr[c] < 0){
                neg[b++] = arr[c++];
            }else{
                pos[a++] = arr[c++];
            }
        }

        for(int i=0;i<arr.length/2;i++){
                arr[2*i] = pos[i];
                arr[2*i+1] = neg[i];
        }
        return arr;
    }

    // bruteforce approach using 2 arrays
    public static void rearrangeArray(int arr[]){
        List<Integer> neg = new ArrayList<>();
        List<Integer> pos = new ArrayList<>();

        for(int i=0;i<arr.length;i++){
            if(arr[i] < 0){
                neg.add(arr[i]);
            }else if(arr[i] > 0){
                pos.add(arr[i]);
            }
        }

        for(int i=0;i<arr.length;i++){
            if(i%2 ==0 ){
                arr[i] = pos.removeFirst();
            }else {
                arr[i] = neg.removeFirst();
            }
        }
    }
}
