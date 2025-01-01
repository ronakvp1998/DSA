package com.questions.strivers.binarysearch.bsonanswers;

public class FindNRoot {

    public static void main(String[] args) {
        int n = 3, m = 1;
        System.out.println(findNRoot(n,m));
    }

    public static int findNRoot(int n, int m){
        int low = 1, high=m;

        while (low<=high){
            int mid = low + (high-low)/2;
            double ans = Math.pow(mid,n);
            if(ans == m){
                return mid;
            }

            if(ans>m){
                high = mid-1;
            }else{
                low = mid+1;
            }
        }
        return -1;
    }

}
