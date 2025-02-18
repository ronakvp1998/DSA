package com.questions.strivers.slidingwind2pointer;

public class Test {

    public static void main(String[] args) {
//        int arr[] = {-1,2,3,4,4,5,-1};
//        int k = 4;
        // type1 constant windows
//        System.out.println(constantWindow(arr,k));

        // type2 longest subarray where <condition> condition is sum <= k
        int arr[] = {2,5,1,7,10};
        int k = 14;
//        System.out.println(conditionWindow1(arr,k));
        System.out.println(conditionWindow2(arr,k));
        System.out.println(conditionWindow3(arr,k));
    }

    // type2 longest subarray where <condition> condition is sum <= k
    // 1 bruteforce approach
    public static int conditionWindow1(int arr[], int k){
        int maxLen = 0,n = arr.length;
        for(int i=0;i<n;i++){
            int sum = 0;
            for(int j=i;j<n;j++){
                sum = sum + arr[j];
                if(sum <= k){
                    maxLen = Math.max(maxLen,j-i+1);
                } else if (sum > k) {
                    break;
                }
            }
        }
        return maxLen;
    }

    // 2 using sliding window & 2 pointer
    public static int conditionWindow2(int arr[], int k){
        int l=0,r=0,maxlen=0,sum=0,n=arr.length;
        while (r<n){
            sum = sum + arr[r];
            // check the condition to expand or srink
            // invalid window so srink
            while (sum > k){
                sum = sum - arr[l];
                l = l+1;
            }
            // valid window
            if(sum <= k){
                maxlen = Math.max(maxlen,sum);
            }
            r = r + 1;
        }
        System.out.println(maxlen);
        return maxlen;
    }

    // 3 using sliding window & 2 pointer
    public static int conditionWindow3(int arr[], int k) {
        int l=0,r=0,sum=0,maxLen=0,n=arr.length;
        while (r < n){
            sum = sum + arr[r];
            if(sum > k){
                sum = sum - arr[l];
                l = l + 1;
            }
            if(sum <= k){
                maxLen = Math.max(maxLen,sum);
            }
            r = r + 1;
        }
        System.out.println(maxLen);
        return maxLen;
    }


    // type1 constant windows
    public static int constantWindow(int arr[], int k){
        if (arr == null || arr.length < k || k <= 0) {
            throw new IllegalArgumentException("Invalid input array or window size.");
        }
        int maxSum = Integer.MIN_VALUE, sum=0;
        int l=0,r=k-1,n=arr.length-1;

        for(int i=0;i<k;i++){
            sum = sum + arr[i];
        }
        maxSum = sum;
        while (r<n){
            sum = sum - arr[l];
            l++;
            r++;
            sum = sum + arr[r];
            maxSum = Math.max(maxSum,sum);
        }
        return maxSum;
    }
}
