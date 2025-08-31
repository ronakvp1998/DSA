package com.questions.dailypractice;

/*
Problem Statement: Maximum Points You Can Obtain from Cards

There are several cards arranged in a row, and each card has an associated number of points.
You can take exactly k cards from either the beginning or the end of the array.
Return the maximum score you can obtain.

Examples:
1. Input: cardPoints = [1,2,3,4,5,6,1], k = 3 → Output: 12
   Explanation: Best choice is taking [6,5,1] → sum = 12.

2. Input: cardPoints = [2,2,2], k = 2 → Output: 4
   Explanation: Any two cards = 2 + 2 = 4.

3. Input: cardPoints = [9,7,7,9,7,7,9], k = 7 → Output: 55
   Explanation: Must take all cards = total sum.
*/
public class Main {

    public static void main(String[] args) {
        int arr[] = {6,2,3,4,7,2,1,7,1};
        int k = 4;
        System.out.println(test(arr,k));
    }

    public static int test(int arr[] ,int k){
        int sum=0,maxSum=0,n=arr.length;
        for(int i=0;i<k;i++){
            sum = sum + arr[i];
        }
        maxSum = sum;
        int leftIndex = k-1;
        int rightIndex = n-1;
        for(int i=0;i<k;i++){
            sum -= arr[leftIndex];
            sum += arr[rightIndex];
            maxSum = Math.max(maxSum,sum);
            leftIndex--;
            rightIndex--;
        }
        return maxSum;
    }
}
