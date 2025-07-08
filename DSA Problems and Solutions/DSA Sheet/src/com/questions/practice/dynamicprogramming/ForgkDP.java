package com.questions.practice.dynamicprogramming;

import java.util.Arrays;

public class ForgkDP {
    public static void main(String[] args) {
        int arr[] = {30, 10, 60, 10, 60, 50};
        int n = arr.length;
        int k = 4;

        // ****************** Approach 1: Pure Recursion ******************
        // Simple recursive function without any optimization
        // Time complexity: O(2^n), Space: O(n) (recursion stack)
        System.out.println(recursive(n - 1, arr,k));

        // ****************** Approach 2: Memoization ******************
        // Initialize the DP array to store computed subproblems
//        int dp[] = new int[n];
//        Arrays.fill(dp, -1); // fill with -1 indicating uncomputed
//        System.out.println("Memoization: " + memorization(n - 1, arr, dp));

        // ****************** Approach 3: Tabulation (Bottom-Up DP) ******************
        // Reuse the same dp[] by re-filling it
//        Arrays.fill(dp, -1); // again fill with -1 before use
//        System.out.println("Tabulation: " + tabulation(n, arr, dp));

        // ****************** Approach 4: Space Optimized DP ******************
//        System.out.println("Space Optimized: " + spaceOptimized(n, arr));
    }
`
    private static int recursive(int n, int[] arr,int k) {
        if(n == 0){
            return 0;
        }
        int minCost = Integer.MAX_VALUE;
        for(int i=1;i<k;i++){
            if(n-i >= 0){
                int jumpCost = recursive(n-i,arr,k) + Math.abs(arr[n] - arr[n-i]);
                minCost = Math.min(minCost,jumpCost);
            }
        }
        return minCost;
    }
}
