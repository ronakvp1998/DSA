package com.questions.strivers.slidingwind2pointer.count;

public class NiceSubarray {

    public static void main(String[] args) {
        int arr[] = {1,1,2,1,1};
        int k = 3;
        System.out.println(niceSubarray(arr,k) - niceSubarray(arr,k-1) );
    }

    public static int niceSubarray(int arr[], int k){
        if(k < 0) return 0;
        int l=0,r=0,sum=0,count=0;
        while (r < arr.length){
            sum += (arr[r]%2);
            while (sum > k){
                sum = sum - (arr[l]%2);
                l = l+1;
            }
            count = count + (r-l+1);
            r = r+1;
        }
        return count;
    }
}
