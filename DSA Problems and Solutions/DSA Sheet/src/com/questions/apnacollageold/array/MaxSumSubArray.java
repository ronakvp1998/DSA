package com.questions.apnacollageold.array;

// 3 find max sub array
public class MaxSumSubArray {

    public static void main(String[] args) {

//        int arr[] = {2,4,6,8,10};
//        maxSubArray(arr, arr.length);
//        System.out.println(Arrays.toString(arr));

        int arr[] = {1,-2,6,-1,3};
//        maxSubArray2(arr, arr.length);
//        System.out.println(Arrays.toString(arr));

        maxSubarray3(arr,arr.length);
    }

    // appraoch 3 Kadane's algorithm
    public static void maxSubarray3(int arr[], int n){

        int currSum = 0;
        int maxSum = Integer.MIN_VALUE;

        for(int i=0;i<arr.length;i++) {
            currSum = currSum + arr[i];
            if(currSum  < 0){
                currSum = 0;
            }
            maxSum = Math.max(currSum,maxSum);
        }
        System.out.println("Max sum " + maxSum);
    }

    // approach 2 Prefix sum TC-> O(n2) SC-> O(n)
    public static void maxSubArray2(int arr[] ,int n){
        // creating prefix array
        int prefix[] = new int[arr.length];
        prefix[0] = arr[0];
        for(int i=1;i< arr.length;i++){
            prefix[i] = prefix[i-1] + arr[i];
        }

        // finding the max sub array sum
        int currSum = 0;
        int maxSum = Integer.MIN_VALUE;
        for(int i=0;i<arr.length;i++){
            for(int j=i;j< arr.length;j++){
                int start = i;
                int end = j;
                currSum = start == 0 ? prefix[end]
                        : prefix[end] - prefix[start-1];
                if(maxSum < currSum){
                    maxSum = currSum;
                }
            }
        }
        System.out.println("Max Sum " + maxSum);
    }

    // approach 1 bruteforce TC O(n3)
    public static void maxSubArray(int arr[],int n){
        int start = 0;
        int end = 0;
        int maxSum = Integer.MIN_VALUE;
        for(int i=0;i<arr.length;i++){
            for(int j=i;j< arr.length;j++){
                start = i;
                end = j;
                int sum = 0;
                for(int k=start;k<=end;k++){
                    sum = arr[k] + sum;
                    System.out.print(arr[k] + " ");
                }
                if(maxSum < sum){
                    maxSum = sum;
                }
                System.out.println();
            }
            System.out.println();
        }

        System.out.println("max Sum " + maxSum);
    }
}
