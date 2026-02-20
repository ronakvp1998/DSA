package com.questions.strivers.greedyalgorithm.easy;


import java.util.Arrays;
import java.util.Comparator;

/**
 * ==================================================================================================
 * PROBLEM: FRACTIONAL KNAPSACK (Greedy Approach)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given weights and values of N items, we need to put these items in a knapsack of capacity W
 * to get the maximum total value in the knapsack.
 * Note: Unlike 0/1 Knapsack, you can break an item into smaller fractions.
 *
 * EXAMPLE:
 * Input: val = [60, 100, 120], wt = [10, 20, 30], capacity = 50
 * Output: 240.0
 * * STRATEGY (GREEDY):
 * 1. Calculate the value/weight ratio for each item.
 * 2. Sort the items in DESCENDING order of this ratio.
 * 3. Iterate through the sorted items:
 * - If the whole item fits, take it completely and reduce the capacity.
 * - If the whole item doesn't fit, take whatever fraction fits to exactly fill the knapsack,
 * then break out of the loop.
 * ==================================================================================================
 */

// A simple Item class to hold the value and weight properties together
class Item {
    int value;
    int weight;

    Item(int value, int weight) {
        this.value = value;
        this.weight = weight;
    }
}

public class FractionalKnapsack {

    public static void main(String[] args) {
        int capacity = 50;
        Item[] arr = {
                new Item(60, 10),
                new Item(100, 20),
                new Item(120, 30)
        };

        System.out.println("Knapsack Capacity: " + capacity);
        System.out.println("--------------------------------------------------");

        double maxValue = getMaxValue(arr, capacity);
        System.out.printf("Maximum value we can obtain = %.2f\n", maxValue);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH: GREEDY (Sort by Value/Weight Ratio)
     * ----------------------------------------------------------------------
     * COMPLEXITY:
     * - Time: O(N log N) for sorting the array + O(N) to iterate through it.
     * Total Time Complexity = O(N log N).
     * - Space: O(1) auxiliary space (ignoring the space used by the sorting algorithm).
     * * @param arr Array of Items (each containing value and weight).
     * @param capacity Maximum weight the knapsack can hold.
     * @return The maximum total value as a double.
     */
    public static double getMaxValue(Item[] arr, int capacity) {

        // 1. Sort the items based on their value/weight ratio in descending order.
        // We use Double.compare to safely handle floating-point comparisons.
        Arrays.sort(arr, new Comparator<Item>() {
            @Override
            public int compare(Item a, Item b) {
                double ratioA = (double) a.value / a.weight;
                double ratioB = (double) b.value / b.weight;

                // For descending order, we compare ratioB against ratioA
                return Double.compare(ratioB, ratioA);
            }
        });

        double totalValue = 0.0;
        int currentWeight = 0;

        // 2. Iterate through the sorted items
        for (int i = 0; i < arr.length; i++) {

            // Case 1: The entire item fits into the remaining capacity
            if (currentWeight + arr[i].weight <= capacity) {
                currentWeight += arr[i].weight;
                totalValue += arr[i].value;
            }
            // Case 2: The item is too heavy. Take only the fraction that fits!
            else {
                // Calculate how much weight is left in the knapsack
                int remainingCapacity = capacity - currentWeight;

                // Add the proportionate value of the remaining weight
                totalValue += ((double) arr[i].value / arr[i].weight) * remainingCapacity;

                // The knapsack is now completely full, so we can stop.
                break;
            }
        }

        return totalValue;
    }
}