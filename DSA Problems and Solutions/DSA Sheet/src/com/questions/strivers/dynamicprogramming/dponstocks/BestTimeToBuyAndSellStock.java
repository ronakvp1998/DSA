package com.questions.strivers.dynamicprogramming.stocks;

/**
 * ==================================================================================================
 * PROBLEM: BEST TIME TO BUY AND SELL STOCK (LeetCode 121)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * You want to maximize your profit by choosing a single day to buy one stock and choosing a
 * different day in the future to sell that stock.
 * Return the maximum profit. If no profit is possible, return 0.
 *
 * EXAMPLE 1:
 * Input: prices = [7,1,5,3,6,4]
 * Output: 5
 * Explanation: Buy at 1 (Day 2), Sell at 6 (Day 5). Profit = 5.
 *
 * EXAMPLE 2:
 * Input: prices = [7,6,4,3,1]
 * Output: 0
 * Explanation: Prices are strictly decreasing. No profit possible.
 *
 * KEY INSIGHT (GREEDY / ONE PASS):
 * To maximize profit (Sell - Buy), we need to:
 * 1. Buy at the LOWEST possible price seen *so far*.
 * 2. Sell at the current price if the difference is greater than our current max profit.
 *
 * We can visualize this as keeping track of the "valley" (lowest price) as we walk through the array,
 * and checking the height of every "peak" (current price) relative to that valley.
 * ==================================================================================================
 */
public class BestTimeToBuyAndSellStock {

    public static void main(String[] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println("Prices: " + java.util.Arrays.toString(prices));

        // 1. Brute Force (For comparison)
        System.out.println("1. Brute Force     : " + maxProfitBruteForce(prices));

        // 2. Optimal One Pass
        System.out.println("2. Optimal One Pass: " + maxProfitOptimal(prices));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: BRUTE FORCE
     * ----------------------------------------------------------------------
     * LOGIC:
     * Check every pair (i, j) where j > i.
     * Calculate profit and track the maximum.
     *
     * COMPLEXITY:
     * - Time: O(N^2) -> Two nested loops.
     * - Space: O(1)
     */
    private static int maxProfitBruteForce(int[] prices) {
        int maxProfit = 0;
        int n = prices.length;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int profit = prices[j] - prices[i];
                if (profit > maxProfit) {
                    maxProfit = profit;
                }
            }
        }
        return maxProfit;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: ONE PASS (OPTIMAL)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Iterate through the array while maintaining two variables:
     * 1. 'minPrice': The lowest price encountered *so far*.
     * 2. 'maxProfit': The maximum difference calculated *so far*.
     *
     * Algorithm:
     * - If current price < minPrice: Update minPrice (We found a new valley).
     * - Else if (current price - minPrice) > maxProfit: Update maxProfit (We found a better selling point).
     *
     *
     *
     * COMPLEXITY:
     * - Time: O(N) -> Single pass.
     * - Space: O(1) -> Only two variables used.
     */
    private static int maxProfitOptimal(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            // Case 1: Found a cheaper buying day? Update minPrice.
            if (price < minPrice) {
                minPrice = price;
            }
            // Case 2: Found a good selling day? Check if profit beats record.
            else if (price - minPrice > maxProfit) {
                maxProfit = price - minPrice;
            }
        }

        return maxProfit;
    }
}