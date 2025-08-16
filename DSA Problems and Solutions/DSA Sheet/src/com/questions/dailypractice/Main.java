package com.questions.dailypractice;

public class Main {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5}; // Sample input array
        int limit = 8; // Threshold for sum

        int ans = smallestDivisor(arr, limit);
        System.out.println("The minimum divisor is: " + ans); // Expected Output: 3
    }

    public static int smallestDivisor2(int [] arr, int limit){
        int n = arr.length;
        int maxi = Integer.MIN_VALUE;
        for(int i=0;i<n;i++){
            maxi = Math.max(maxi,arr[i]);
        }
        int low = 1, high = maxi;
        while (low <= high){
            int mid = low + (high - low)/2;
            int sum = 0;
            for(int i=0;i<arr.length;i++){
                sum += Math.ceil((double) arr[i] / (double) mid);
            }
            if(sum <= limit){
                high = mid-1;
            }
            if(sum > limit){
                low = mid + 1;
            }
        }
        return low;
    }

    public static int smallestDivisor(int [] arr, int limit){
        int n = arr.length;
        int maxi = Integer.MIN_VALUE;
        for(int i=0;i<n;i++){
            maxi = Math.max(maxi,arr[i]);
        }
        for(int d=1;d<=maxi;d++){
            int sum=0;
            for(int j=0;j<n;j++){
                sum += Math.ceil((double) (arr[j])/(double) (d));
            }
            if(sum <= limit){
                return d;
            }
        }
        return -1;
    }

}
