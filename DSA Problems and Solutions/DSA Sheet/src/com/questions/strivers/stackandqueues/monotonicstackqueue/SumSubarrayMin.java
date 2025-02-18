package com.questions.strivers.stackandqueues.monotonicstackqueue;

public class SumSubarrayMin {

    public static void main(String[] args) {

    }

    // appraoch2
//    public static int sum(int arr[]){
////        int nse[] = findNse(arr);
////        int pse[] = findPse(arr);
//        int n = arr.length;
//        int total = 0, mod = (int) (10^9 + 7);
//        for(int i=0;i<=n-1;i++){
//            int left = i - pse[i];
//            int right = nse[i] - i;
//            total = (total + )
//        }
//    }

    // approach1
    public static int findMin(int arr[]){
        int sum = 0 , n = arr.length; 
        int mod = 1_000_000_007;
        for(int i=0;i<n;i++){
            int min = arr[i];
            for(int j=i;j<n-1;j++){
                min = Math.min(min,arr[j]);
                sum = (sum+min)%mod;
            }
        }
        return sum;
    }
}
