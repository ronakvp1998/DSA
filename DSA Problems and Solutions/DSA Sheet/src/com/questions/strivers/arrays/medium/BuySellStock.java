package com.questions.strivers.arrays.medium;
// Problem Link: https://takeuforward.org/data-structure/stock-buy-and-sell/
// Problem Statement:
// You are given an array of prices where prices[i] is the price of a given stock on the ith day.
// You want to maximize your profit by choosing a single day to buy one stock and choosing a
// different day in the future to sell that stock. Return the maximum profit you can achieve
// from this transaction. If you cannot achieve any profit, return 0.

public class BuySellStock {

    public static void main(String[] args) {
        int arr[] = {7, 1, 5, 3, 6, 4};

        // Calling the optimized method to find max profit
        System.out.println(buySellStock(arr));
    }

    // Brute force approach - Check every possible pair (i < j)
    static int maxProfit1(int[] arr) {
        int maxPro = 0;

        // Loop through every element
        for (int i = 0; i < arr.length; i++) {
            // For each i, check every j > i
            for (int j = i + 1; j < arr.length; j++) {
                // If selling price is more than buying price
                if (arr[j] > arr[i]) {
                    // Calculate profit and update maxProfit if needed
                    maxPro = Math.max(arr[j] - arr[i], maxPro);
                }
            }
        }
        return maxPro;
    }

    // ✅ Time Complexity: O(n^2)
    // ✅ Space Complexity: O(1) — constant space
    // ⚠️ Not efficient for large input sizes

    // Slightly optimized approach - single pass keeping track of minPrice so far
    static int maxProfit2(int[] arr) {
        int maxPro = 0; // Store max profit seen so far
        int minPrice = Integer.MAX_VALUE; // Store the minimum price seen so far

        // Traverse through the array
        for (int i = 0; i < arr.length; i++) {
            // Update the minPrice if the current price is lower
            minPrice = Math.min(minPrice, arr[i]);

            // Calculate the profit if sold today, and update maxProfit if it's higher
            maxPro = Math.max(maxPro, arr[i] - minPrice);
        }

        return maxPro;
    }

    // ✅ Time Complexity: O(n)
    // ✅ Space Complexity: O(1) — constant space
    // ⚡ Efficient and readable solution

    // Another optimized version with slightly different condition logic
    private static int buySellStock(int arr[]) {
        int buyPrice = Integer.MAX_VALUE; // Initialize to max value so any price will be lower
        int maxProfit = 0; // Variable to store max profit

        // Loop through each day's price
        for (int i = 0; i < arr.length; i++) {
            // If current price is higher than the minimum buying price seen so far
            if (buyPrice < arr[i]) {
                // Calculate today's profit and update maxProfit if it's better
                int profit = arr[i] - buyPrice;
                maxProfit = Math.max(maxProfit, profit);
            } else {
                // If current price is lower, update the buyPrice (better day to buy)
                buyPrice = arr[i];
            }
        }

        return maxProfit; // Return the maximum profit found
    }

    // ✅ Time Complexity: O(n)
    // ✅ Space Complexity: O(1)
    // ✅ Recommended: Most efficient approach using single pass

    // print the arrays elements of buy and sell dates
    private static int buySellStock2(int arr[]) {
        int maxprofit = 0;
        int minPrice = Integer.MAX_VALUE;

        int startAns = -1, endAns = -1;  // final buy/sell indices
        int minIndex = -1;              // track index of minPrice (possible buy)

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < minPrice) {
                minPrice = arr[i];
                minIndex = i;  // update potential buy day
            } else {
                int profit = arr[i] - minPrice;
                if (profit > maxprofit) {
                    maxprofit = profit;
                    startAns = minIndex; // only update startAns if this profit is best so far
                    endAns = i;
                }
            }
        }

        System.out.println("Buy on day: " + startAns + ", Sell on day: " + endAns);
        return maxprofit;
    }


}

