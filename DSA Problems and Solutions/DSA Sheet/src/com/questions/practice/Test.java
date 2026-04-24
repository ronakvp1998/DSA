package com.questions.practice;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        int []nums = {4,5,6,7,0,1,2};
        int target = 0;
    }
    public static int minEatingSpeedOptimal(int[] piles, int h) {
        int left = 1,right=0;
        for(int pile: piles){
            right = Math.max(right,pile);
        }
        int optimalSpeed = right;

        while (left <= right){
            int k = left + (right - left)/2;
            if(checkOptimal(k,piles,h)){
                right = k - 1;
            }else{
                left = k + 1;
            }
        }
        return optimalSpeed;
    }
    private static boolean checkOptimal(int k,int []piles,int h){
        int hoursReq = 0;
        for(int pile : piles){
            
            hoursReq += (pile + k - 1)/k;
        }
        return hoursReq <= h;
    }
    private static int nroot(int n,int m){
        if(n == 1){
            return m;
        }
        int low=1,high=m/2;
        while (low <= high){
            int mid = low + (high-low)/2;
            int checkResult = powerCheck(mid,n,m);
            if(checkResult == 1){
                return mid;
            } else if (checkResult == 0) {
                low = mid + 1;
            }else{
                high = mid-1;
            }
        }
        return -1;
    }
    private static int powerCheck(int mid,int n,int m){
        long ans = 1;
        for(int i=1;i<=n;i++){
            ans = ans*mid;
            if(ans>m){
                return 2;
            }
        }
        if(ans==m){
            return 1;
        }
        return 0;

    }

    private static int sqrt(int n) {
        int low=0, high=n/2,ans=1;
        while (low <= high){
            int mid = low + (high-low)/2;
            long square = (long) mid * mid;
            if(square == n){
                return mid;
            }
            if(square < n){
                ans = mid;
                low = mid + 1;
            }else{
                high = mid-1;
            }
        }
        return ans;
    }
}