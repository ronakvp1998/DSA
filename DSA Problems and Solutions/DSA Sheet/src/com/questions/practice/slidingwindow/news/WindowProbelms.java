package com.questions.practice.slidingwindow.news;

public class WindowProbelms {
    public static void main(String[] args) {
//        int arr[] = {-1,2,3,3,4,5,-1};
//        int k = 4;
//        System.out.println(constantWindow(arr,k));

        int arr[] = {2,5,1,7,10};
        int k = 14;

    }

    


    private static int constantWindow(int arr[], int k){
        int maxSum = Integer.MIN_VALUE;
        int l=0,r=k-1,sum=0,n=arr.length;
        for(int i=0;i<=r;i++){
            sum = sum + arr[i];
        }
        maxSum = sum;
        while (r < n-1){
            sum = sum - arr[l];
            l++;
            r++;
            sum = sum + arr[r];
            maxSum = Math.max(maxSum,sum);
        }
        return maxSum;
    }
}
