package com.questions.practice.slidingwindow.old;

public class MaxConsOnes {
    public static void main(String[] args) {
        int arr[] = {1,1,1,0,0,0,1,1,1,1,0};
        int k = 2;
        System.out.println(maxConsOnes2(arr,k));
    }

    // Approach 2 max consecutive ones
    public static int maxConsOnes2(int arr[], int k){
        int maxLen=0,l=0,r=0,len=0,zeros=0,n=arr.length;
        while (r < n){
            if(arr[r] == 0){
                zeros++;
            }
            while (zeros > k){
                if(arr[l] == 0){
                    zeros--;
                }
                l++;
            }
            if(zeros <= k){
                len = r-l+1;
                maxLen = Math.max(maxLen,len);
            }
            r++;
        }
        return maxLen;
    }

    // Approach 1 max consecutive ones
    public static int maxConsOnes1(int arr[], int k){
        int maxLen=0;
        for(int i=0;i<arr.length;i++){
            int zeros = 0;
            for(int j=i;j<arr.length;j++){
                if(arr[j] == 0){
                    zeros++;
                }
                if(zeros <= k){
                    maxLen = Math.max(maxLen,j-i+1);
                }else{
                    break;
                }
            }
        }
        return maxLen;
    }
}
