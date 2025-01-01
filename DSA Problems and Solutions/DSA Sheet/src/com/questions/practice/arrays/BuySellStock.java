package com.questions.practice.arrays;

public class BuySellStock {
    public static void main(String[] args) {
        int arr[] = {7,1,5,3,6,4};
        System.out.println(buySell(arr,arr.length));
    }

    public static int buySell(int arr[], int n){
        int ans=Integer.MIN_VALUE, byprice=Integer.MAX_VALUE;

        for(int i=0;i< arr.length;i++){
            if(arr[i] < byprice){
                byprice = arr[i];
                continue;
            }
            int profit = arr[i] - byprice;

            if(profit > ans){
                ans = profit;
            }

        }
        return ans;
    }
}
