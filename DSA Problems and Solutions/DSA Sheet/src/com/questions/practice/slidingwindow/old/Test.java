package com.questions.practice.slidingwindow.old;

public class Test {
    public static void main(String[] args) {
//        int arr[] = {-1,2,3,3,4,5,-1};
//        int k = 4;
//        System.out.println(constantWindow2(arr,k));
//        int arr[] = {2,5,1,7,10};
//        int k=14;
//        System.out.println(conditionSubarray2(arr,k));
        int arr[] = {6,2,3,4,7,2,1,7,1};
        int k = 4;
        System.out.println(maxPoint(arr,k));
    }

    // Maxsum for window == k back or front
    public static int maxPoint(int arr[], int k){
        int n=arr.length,maxSum=0,sum=0,l=0,r=n-k;
        for(int i=0;i<k;i++){
            sum = sum + arr[i];
        }
        maxSum = sum;
        int rightIndex = n-1;
        for(int i=k-1;i>=0;i--){
            
        }
        return maxSum;
    }


    // longest subarray where sum <= k
    public static int conditionSubarray2(int arr[], int k){
        int maxLen=0,n=arr.length,l=0,r=0,sum = 0;
        while (r < n){
            sum = sum + arr[r];
            if (sum > k){
                sum = sum - arr[l];
                l++;
            }
            if(sum <= k) {
                maxLen = Math.max(maxLen,r-l+1);
            }
            r++;
        }
        return maxLen;
    }

    // longest subarray where sum <= k
    public static int conditionSubarray(int arr[], int k){
        int maxLen=0,n=arr.length;
        for(int i=0;i<n;i++){
            int sum = 0;
            for(int j=i;j<n;j++){
                sum = sum + arr[j];
                if(sum <= k){
                    maxLen = Math.max(maxLen,j-i+1);
                }else {
                    break;
                }
            }
        }
        return maxLen;
    }

    public static int constantWindow2(int arr[], int k) {
        int maxSum=0,l=0,r=k-1,n=arr.length,sum=0;
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

    public static int constantWindow(int arr[], int w){
        int maxSum=0,n=arr.length;
        for(int i=0;i<n-w;i++){
            for(int j=i;j<n-w;j++){
                int sum = 0;
                for(int k=i;k<i+w;k++){
                    sum = sum + arr[k];
                }
                maxSum = Math.max(maxSum,sum);
            }
        }
        return maxSum;
    }
}
