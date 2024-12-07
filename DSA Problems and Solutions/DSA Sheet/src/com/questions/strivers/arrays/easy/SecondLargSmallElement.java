package com.questions.strivers.arrays.easy;

public class SecondLargSmallElement {

    public static void main(String[] args) {
        int[] arr = {1, 2, 4, 7, 7, 5};
        System.out.println(secondLargest(arr));
        System.out.println(secondSmallest(arr));
    }

    public static int secondSmallest(int arr[]){
        int small = Integer.MAX_VALUE;
        int secSmall = Integer.MAX_VALUE;
        for(int i=0;i<arr.length;i++){
            if(arr[i] < small){
                secSmall = small;
                small = arr[i];
            }else if (arr[i] < secSmall && arr[i] != small)
            {
                secSmall = arr[i];
            }
        }
        return secSmall;
    }
    public static int secondLargest(int arr[]){
        int large = Integer.MIN_VALUE;
        int secLarge = Integer.MIN_VALUE;
        for(int i=0;i<arr.length;i++){
            if(arr[i] > large){
                secLarge = large;
                large = arr[i];
            }else if (arr[i] > secLarge && arr[i] != large)
            {
                secLarge = arr[i];
            }
        }
        return secLarge;
    }
}
