package com.questions.strivers.arrays.medium;

import java.util.Arrays;

public class NextPermutation {

    public static void main(String[] args) {
        int arr[] = {2,3,1};
        nextPermutation(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void nextPermutation(int arr[]){
        int pivot = -1,n = arr.length-1;

        // step 1 find pivot
        for(int i= n-1;i>=0;i--){
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
            if(arr[i]>arr[pivot]){
                swap(arr,i,pivot);
                break;
            }
        }

         // step 4 reverse element from pivoit+1 to n-1
        int i = pivot+1;
         int j = n;
         reverse(arr,i,j);
    }

    public static void reverse(int arr[],int i, int j){
        while(i <= j){
            swap(arr,i,j);
            i++;
            j--;
        }
    }

    public static void swap(int arr[], int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
