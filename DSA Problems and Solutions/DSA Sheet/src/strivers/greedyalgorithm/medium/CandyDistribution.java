package com.questions.strivers.greedyalgorithm.medium;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 135. Candy (Hard)
 * ==================================================================================================
 * There are n children standing in a line. Each child is assigned a rating value given in the
 * integer array ratings.
 * * You are giving candies to these children subjected to the following requirements:
 * 1. Each child must have at least one candy.
 * 2. Children with a higher rating get more candies than their neighbors.
 * * Return the minimum number of candies you need to have to distribute the candies to the children.
 *
 * Example 1:
 * Input: ratings = [1,0,2]
 * Output: 5
 * Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
 *
 * Example 2:
 * Input: ratings = [1,2,2]
 * Output: 4
 * Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
 * The third child gets 1 candy because it satisfies the above two conditions.
 * * ==================================================================================================
 * APPROACH: GREEDY ALGORITHM (Two-Pass Array)
 * ==================================================================================================
 * The rules state a child must have more candies than their neighbor ONLY IF they have a higher rating.
 * Trying to check both left and right neighbors simultaneously gets very complicated and messy.
 * * Instead, we break the problem into two simpler, independent rules:
 * 1. Left-to-Right Pass: Make sure every child has more candies than their LEFT neighbor
 * (if their rating is higher).
 * 2. Right-to-Left Pass: Make sure every child has more candies than their RIGHT neighbor
 * (if their rating is higher).
 * * By taking the MAXIMUM of the candies required from both passes for each child, we guarantee
 * both rules are satisfied simultaneously with the absolute minimum number of candies.
 * ==================================================================================================
 */

import java.util.Arrays;

public class CandyDistribution {

    public static void main(String[] args) {
        // Test Case 1: Expected 5 (2 + 1 + 2)
        int[] ratings1 = {1, 0, 2};
        System.out.println("Test Case 1 (Expected: 5) -> Result: " + candy(ratings1));

        // Test Case 2: Expected 4 (1 + 2 + 1)
        int[] ratings2 = {1, 2, 2};
        System.out.println("Test Case 2 (Expected: 4) -> Result: " + candy(ratings2));

        // Test Case 3: Expected 15 (1 + 2 + 3 + 4 + 5)
        int[] ratings3 = {1, 2, 3, 4, 5};
        System.out.println("Test Case 3 (Expected: 15) -> Result: " + candy(ratings3));
    }

    /**
     * Calculates the minimum number of candies needed to satisfy all rating rules.
     * * @param ratings Array of children's ratings
     * @return The minimum total number of candies
     */
    public static int candy(int[] ratings) {
        int n = ratings.length;

        // Edge Case: If there are no children, 0 candies are needed.
        if (n == 0) {
            return 0;
        }

        // Array to keep track of how many candies each child gets.
        int[] candies = new int[n];

        // Rule 1: Every child MUST have at least 1 candy.
        Arrays.fill(candies, 1);

        // PASS 1: Left to Right
        // We only compare each child with their LEFT neighbor.
        for (int i = 1; i < n; i++) {
            // If the current child has a higher rating than the child to their left
            if (ratings[i] > ratings[i - 1]) {
                // They must get exactly 1 more candy than the left child
                candies[i] = candies[i - 1] + 1;
            }
        }

        // PASS 2: Right to Left
        // We only compare each child with their RIGHT neighbor.
        for (int i = n - 2; i >= 0; i--) {
            // If the current child has a higher rating than the child to their right
            if (ratings[i] > ratings[i + 1]) {
                // They must have more candies than the right child.
                // However, they might ALREADY have enough candies from Pass 1!
                // So, we take the MAXIMUM of what they already have vs what they need now.
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }

        // Calculate the total number of candies
        int totalCandies = 0;
        for (int i = 0; i < n; i++) {
            totalCandies += candies[i];
        }

        return totalCandies;
    }
}