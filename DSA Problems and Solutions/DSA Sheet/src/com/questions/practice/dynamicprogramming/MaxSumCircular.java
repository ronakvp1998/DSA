package com.questions.practice.dynamicprogramming;

import java.util.Arrays;

public class MaxSumCircular {
    public static void main(String[] args) {
        int arr[] = {2, 3, 2};
        int n = arr.length;

        // Approach 1: Recursive solution (brute force)
        System.out.println("Recursive: " + houseRobberRecursive(arr));

        // Approach 2: Memoization (Top-down dynamic programming)
//        System.out.println("Memoization: " + houseRobberMemoization(arr));

        // Approach 3: Tabulation (Bottom-up dynamic programming)
//        System.out.println("Tabulation: " + houseRobberTabulation(arr));

        // Approach 4: Space Optimized dynamic programming
//        System.out.println("Space Optimized: " + houseRobberSpaceOptimized(arr));
    }

    private static int houseRobberRecursive(int arr[]){
        int n = arr.length;
        if(n == 1){
            return arr[0];
        }
        int excludeFirst = recursive(arr,1,n-1);
        int excludeLast = recursive(arr,0,n-2);
        return Math.max(excludeFirst,excludeLast);
    }

    private static int recursive(int arr[],int start,int end){
        if(end < start){
            return 0;
        }
        if(end == start){
            return arr[start];
        }
        int include = arr[end] + recursive(arr,start,end-2);
        int exclude = recursive(arr,start,end-1);
        return Math.max(include,exclude);
    }

    private static int hourseRobberMemorization(int arr[]){
        int n = arr.length;
        if(n == 1){
            return arr[0];
        }
        int []dp1 = new int[n];
        int []dp2 = new int[n];
        Arrays.fill(dp1,-1);;
        Arrays.fill(dp2,-1);
        int excludeFirst = memorizaion(arr,1,n-1,dp1);
        int excludeLast = memorizaion(arr,0,n-2,dp2);
        return Math.max(excludeFirst,excludeLast);
    }

    private static int memorizaion(int arr[],int start,int last,int dp[]){
        if(last < start){
            return 0;
        }
        if(last == start){
            return arr[start];
        }
        if(dp[last] != -1){
            return dp[last];
        }
        int include = memorizaion(arr,start,last - 2,dp) + arr[last];
        int exclude = memorizaion(arr,start,last-1,dp);
        return dp[last] = Math.max(exclude,include);
    }

}
