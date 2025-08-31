package com.questions.strivers.slidingwind2pointer;

import java.util.HashSet;
import java.util.Set;

public class Test {

    public static void main(String[] args) {
        int arr[] = {3,3,3,1,2,1,1,2,3,3,4};
        System.out.println(test(arr));
    }
    // longest substring of 1 & 0 with max zeros as k
    public static int test(int arr[]){
        int maxLen = 0,n=arr.length;
        for(int i=0;i<n;i++){
            Set<Integer> set = new HashSet<>();
            for(int j=i;j<n;j++){
                set.add(arr[j]);
                if(set.size() <= 2){
                    maxLen = Math.max(maxLen,j-i+1);
                }else{
                    break;
                }
            }
        }
        return maxLen;
    }


}
