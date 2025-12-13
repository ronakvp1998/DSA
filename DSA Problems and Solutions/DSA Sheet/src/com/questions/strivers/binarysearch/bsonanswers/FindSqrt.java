package com.questions.strivers.binarysearch.bsonanswers;

public class FindSqrt {

    public static void main(String[] args) {
        System.out.println(floorSqrt(25));
    }

    private static int floorSqrt(int n){
        int low=1, high=n;
        while (low<=high){
            int mid = low + (high-low)/2;
            int val = (mid * mid);
            if(val <= n){
                low = mid+1;
            }else{
                high = mid-1;
            }
        }
        // high will always be having the final answer
        return high;
    }
}
