package com.questions.strivers.binarysearch.bsonanswers;
/*
Problem Statement: You are the owner of a Shipment company.
You use conveyor belts to ship packages from one port to another. The packages must be shipped within 'd' days.
The weights of the packages are given in an array 'of weights'.
The packages are loaded on the conveyor belts every day in the same order as they appear in the array.
The loaded weights must not exceed the maximum weight capacity of the ship.
Find out the least-weight capacity so that you can ship all the packages within 'd' days.

Examples

Example 1:
Input Format: N = 5, weights[] = {5,4,5,2,3,4,5,6}, d = 5
Result: 9
Explanation: If the ship capacity is 9, the shipment will be done in the following manner:
Day         Weights            Total
1        -       5, 4          -        9
2        -       5, 2          -        7
3        -       3, 4          -        7
4        -       5              -        5
5        -       6              -        6
So, the least capacity should be 9.

Example 2:

Input Format: N = 10, weights[] = {1,2,3,4,5,6,7,8,9,10}, d = 1
Result: 55
Explanation: We have to ship all the goods in a single day.
So, the weight capacity should be the summation of all the weights i.e. 55.

Observation:


Minimum ship capacity: The minimum ship capacity should be the maximum value in the given array.
Let’s understand using an example.
Assume the given weights array is {1, 2, 3, 4, 5, 6, 7, 8, 9, 10} and the ship capacity is 8.
Now in the question,
it is clearly stated that the loaded weights in the ship must not exceed the maximum weight capacity of the ship.
For this constraint, we can never ship the weights 9 and 10, if the ship capacity is 8.
That is why, in order to ship all the weights,
the minimum ship capacity should be equal to the maximum of the weights array i.e. nax(weights[]).
Maximum capacity: If the ship capacity is equal to the sum of all the weights,
we can ship all goods within a single day.
Any capacity greater than this will yield the same result.
So, the maximum capacity will be the summation of all the weights i.e. sum(weights[]).

From the observations, it is clear that our answer lies in the range
[max(weights[]), sum(weights[])].


How to calculate the number of days required to ship all the weights for a certain ship capacity:


In order to calculate this, we will write a function findDays().
This function accepts the weights array and a capacity as parameters and returns the number of days required for
 that particular capacity. The steps will be the following:


findDays(weights[], cap):


We will declare to variables
i.e. ‘days’(representing the required days) and ‘load’ (representing the loaded weights in the ship).
As we are on the first day, ‘days’ should be initialized with 1 and ‘load’ should be initialized with 0.
Next, we will use a loop(say i) to iterate over the weights. For each weight, weights[i], we will check the following:
If load+weights[i] > cap: If upon adding current weight with load exceeds the ship capacity,
we will move on to the next day(i.e. day = day+1) and then load the current weight
(i.e. Set load to weights[i], load = weights[i]).
Otherwise, We will just add the current weight to the load(i.e. load = load+weights[i]).
Finally, we will return ‘days’ which represents the number of days required.

Approaches:

        1. Brute Force (leastWeightCapacity):
        - Iterate from max(weights) to sum(weights).
        - For each capacity, calculate number of days required using findDays().
        - Return the first capacity that works within 'd' days.
        - Time: O((sum - max) * n)
        - Space: O(1)
        - Not efficient for large inputs.

        2. Optimized (Binary Search - leastWeightCapacity2):
        - Observation: Answer lies between [max(weights), sum(weights)].
        - Apply binary search on capacity range.
        - For each mid (capacity), calculate number of days using findDays2().
        - If required days <= d → search left (high = mid-1).
        - Else search right (low = mid+1).
        - Return low as minimum capacity.
        - Time: O(n * log(sum - max))
        - Space: O(1)
        - Much faster than brute force.

        Helper Function: findDays / findDays2
        - Simulates loading packages into ships for a given capacity.
        - Counts how many days are required.

        ---
        */

public class ShipPackagesDDays {

    /**
     * Helper function for Brute Force approach.
     * Calculates how many days are needed for a given ship capacity.
     */
    public static int findDays(int[] weights, int cap) {
        int days = 1; // Start from Day 1
        int load = 0; // Current load on the ship

        for (int i = 0; i < weights.length; i++) {
            // If adding this package exceeds capacity → move to next day
            if (load + weights[i] > cap) {
                days += 1; // Move to next day
                load = weights[i]; // Start new day with current weight
            } else {
                load += weights[i]; // Load on same day
            }
        }
        return days;
    }

    /**
     * Brute Force Approach
     * Tries all possible capacities from max(weights) to sum(weights).
     *
     * Time Complexity: O((sum - max) * n)
     * Space Complexity: O(1)
     */
    public static int leastWeightCapacity(int[] weights, int d) {
        // Find max element and sum of weights
        int maxi = Integer.MIN_VALUE, sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += weights[i];
            maxi = Math.max(maxi, weights[i]);
        }

        // Try every capacity from maxi to sum
        for (int i = maxi; i <= sum; i++) {
            if (findDays(weights, i) <= d) {
                return i; // First valid capacity found
            }
        }
        return -1; // Should never hit
    }

    /**
     * Helper function for Binary Search approach.
     * Same as findDays().
     */
    public static int findDays2(int[] weights, int cap) {
        int days = 1;
        int load = 0;

        for (int i = 0; i < weights.length; i++) {
            if (load + weights[i] > cap) {
                days += 1;
                load = weights[i];
            } else {
                load += weights[i];
            }
        }
        return days;
    }

    /**
     * Optimized Approach - Binary Search
     * Uses binary search on capacity range [max(weights), sum(weights)].
     *
     * Time Complexity: O(n * log(sum - max))
     *   - log(sum - max) iterations of binary search
     *   - Each iteration calls findDays2() which takes O(n)
     *
     * Space Complexity: O(1)
     */
    public static int leastWeightCapacity2(int[] weights, int d) {
        int low = Integer.MIN_VALUE, high = 0;

        // Determine search space: [max(weights), sum(weights)]
        for (int i = 0; i < weights.length; i++) {
            high += weights[i];
            low = Math.max(low, weights[i]);
        }

        // Binary search over capacities
        while (low <= high) {
            int mid = (low + high) / 2; // Mid capacity
            int numberOfDays = findDays2(weights, mid);

            if (numberOfDays <= d) {
                // Can ship in 'd' days or less → try smaller capacity
                high = mid - 1;
            } else {
                // Requires more days → increase capacity
                low = mid + 1;
            }
        }
        return low; // Minimum valid capacity
    }

    public static void main(String[] args) {
        int[] weights = {5, 4, 5, 2, 3, 4, 5, 6};
        int d = 5;

        // Optimized Binary Search Solution
        int ans = leastWeightCapacity2(weights, d);
        System.out.println("The minimum capacity should be: " + ans);
    }
}