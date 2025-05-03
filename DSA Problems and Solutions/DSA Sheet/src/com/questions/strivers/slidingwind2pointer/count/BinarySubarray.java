package com.questions.strivers.slidingwind2pointer.count;

public class BinarySubarray {

    public static void main(String[] args) {
        int arr[] = {1,0,0,1,1,0};
        int goal = 2;
        System.out.println(binarySub(arr,goal) - binarySub(arr,goal-1));
    }

    public static int binarySub(int arr[], int k){
        if(k < 0 ){
            return 0;
        }
        int l=0,r=0,sum=0,count=0;
        while (r < arr.length){
            sum += arr[r];
            while (sum > k){
                sum = sum - arr[l];
                l = l + 1;
            }
            count = count + (r-l+1);
            r = r+1;
        }
        return count;
    }
}
