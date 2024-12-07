package com.questions.strivers.arrays.easy;

public class LargestElementInArray {

    public static void main(String[] args) {
        int arr[] = {2,5,1,3,0};
//        System.out.println(largestElement(arr,1,arr[0]));
        System.out.println(largestElement1(arr));
    }

    public static int largestElement1(int arr[]) {
        int max= arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max= arr[i];
            }
        }
        return max;
    }

    public static  int largestElement(int arr[],int i,int longest){
        if(i == arr.length){
            return longest;
        }
        return Math.max(arr[i],longest);
    }
}
