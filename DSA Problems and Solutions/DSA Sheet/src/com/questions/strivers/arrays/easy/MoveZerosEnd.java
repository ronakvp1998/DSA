package com.questions.strivers.arrays.easy;

import java.util.Arrays;

public class MoveZerosEnd {

    public static void main(String[] args) {
        int arr[] = {0,1,0,3,12};
        moveZeroesEnd(arr);
        System.out.println(Arrays.toString(arr));
    }

    // optimized inplace approach using 2 pointers
    public static int [] moveZeroesEnd(int arr[]){
        int j=-1;
        int n = arr.length;
        // place the j pointer to the first zero
        for(int i=0;i<n;i++){
            if(arr[i] == 0){
                j = i;
                break;
            }
        }

        // no zero element
        if(j == -1) return arr;

        // move the pointer i and j
        for(int i=j+1;i<n;i++){
            if(arr[i]!= 0){
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                j++;
            }
        }
        return arr;
    }
}
