package com.questions.strivers.dynamicprogramming.twoddp;

import java.util.Arrays;
//https://takeuforward.org/data-structure/dynamic-programming-ninjas-training-dp-7/
/*Problem Statement: A Ninja has an ‘N’ Day training schedule. He has to perform one of these three activities
(Running, Fighting Practice, or Learning New Moves) each day.
There are merit points associated with performing an activity each day.
The same activity can’t be performed on two consecutive days.
We need to find the maximum merit points the ninja can attain in N Days.
We are given a 2D Array POINTS of size ‘N*3’ which tells us the merit point of specific activity on that particular day.
Our task is to calculate the maximum number of merit points that the ninja can earn.
Days = 3
Points = 10, 40, 70 // Day0
         20, 50, 80 // Day1
         30, 60, 90 // Day2
         Output: 210 // 70(Day0) + 50(Day2) + 90(Day3) */
public class NinjaTraining {

    public static void main(String[] args) {
        // Input: Merit points for each task on each day
        int[][] meritPoints = {
                {10, 40, 70},
                {20, 50, 80},
                {30, 60, 90}
        };

        // Solve using different approaches
        System.out.println("Recursive: " + ninjaTrainingRecursive(meritPoints));
        System.out.println("Memoization: " + ninjaTrainingMemoization(meritPoints));
        System.out.println("Tabulation: " + ninjaTrainingTabulation(meritPoints));
        System.out.println("Space Optimized: " + ninjaTrainingSpaceOptimized(meritPoints));
    }

    // Recursive approach to solve the problem
    // Time Complexity: O(3^N) - Each day has 3 tasks, and recursion explores all combinations
    // Space Complexity: O(N) - Recursion stack depth is equal to the number of days
    public static int recursive(int day, int lastTask, int[][] meritPoints) {
        // Base case: If it's the first day, return the maximum points for tasks not performed on the last day
        if (day == 0) {
            int maxPoints = 0;
            for (int task = 0; task < 3; task++) {
                if (task != lastTask) { // Ensure the task is not the same as the last task
                    maxPoints = Math.max(maxPoints, meritPoints[0][task]);
                }
            }
            return maxPoints;
        }

        // Recursive case: Try all tasks except the one performed on the last day
        int maxPoints = 0;
        for (int task = 0; task < 3; task++) {
            if (task != lastTask) { // Ensure the task is not the same as the last task
                int taskPoints = meritPoints[day][task] + recursive(day - 1, task, meritPoints); // Add points for the current task
                maxPoints = Math.max(maxPoints, taskPoints); // Update the maximum points
            }
        }
        return maxPoints;
    }

    // Wrapper function for the recursive approach
    public static int ninjaTrainingRecursive(int[][] meritPoints) {
        int days = meritPoints.length;
        // Start with no task performed on the last day (represented by 3)
        return recursive(days - 1, 3, meritPoints);
    }

    // Memoization approach to solve the problem
    // Time Complexity: O(N * 4) - Each state (day, lastTask) is computed once
    // Space Complexity: O(N * 4 + N) - Space for dp array and recursion stack
    public static int memoization(int day, int lastTask, int[][] meritPoints, int[][] dp) {
        // Base case: If it's the first day, return the maximum points for tasks not performed on the last day
        if (day == 0) {
            int maxPoints = 0;
            for (int task = 0; task < 3; task++) {
                if (task != lastTask) { // Ensure the task is not the same as the last task
                    maxPoints = Math.max(maxPoints, meritPoints[0][task]);
                }
            }
            return maxPoints;
        }

        // If the result is already computed, return it
        if (dp[day][lastTask] != -1) {
            return dp[day][lastTask];
        }

        // Recursive case: Try all tasks except the one performed on the last day
        int maxPoints = 0;
        for (int task = 0; task < 3; task++) {
            if (task != lastTask) { // Ensure the task is not the same as the last task
                int taskPoints = meritPoints[day][task] + memoization(day - 1, task, meritPoints, dp); // Add points for the current task
                maxPoints = Math.max(maxPoints, taskPoints); // Update the maximum points
            }
        }
        return dp[day][lastTask] = maxPoints; // Store the result in dp array
    }

    // Wrapper function for the memoization approach
    public static int ninjaTrainingMemoization(int[][] meritPoints) {
        int days = meritPoints.length;
        int[][] dp = new int[days][4]; // dp array to store results
        for (int[] row : dp) {
            Arrays.fill(row, -1); // Initialize dp array with -1
        }
        // Start with no task performed on the last day (represented by 3)
        return memoization(days - 1, 3, meritPoints, dp);
    }

    // Tabulation approach to solve the problem
    // Time Complexity: O(N * 4 * 3) - For each day, compute results for 4 possible lastTask values, iterating over 3 tasks
    // Space Complexity: O(N * 4) - Space for dp array
    public static int ninjaTrainingTabulation(int[][] meritPoints) {
        int days = meritPoints.length;
        int[][] dp = new int[days][4]; // dp array to store results

        // Base case: Day 0
        dp[0][0] = Math.max(meritPoints[0][1], meritPoints[0][2]); // Max points if last task was 0
        dp[0][1] = Math.max(meritPoints[0][0], meritPoints[0][2]); // Max points if last task was 1
        dp[0][2] = Math.max(meritPoints[0][0], meritPoints[0][1]); // Max points if last task was 2
        dp[0][3] = Math.max(meritPoints[0][0], Math.max(meritPoints[0][1], meritPoints[0][2])); // Max points if no restriction

        // Fill the dp table for subsequent days
        for (int day = 1; day < days; day++) {
            for (int lastTask = 0; lastTask < 4; lastTask++) {
                dp[day][lastTask] = 0; // Initialize current cell
                for (int task = 0; task < 3; task++) {
                    if (task != lastTask) { // Ensure the task is not the same as the last task
                        int taskPoints = meritPoints[day][task] + dp[day - 1][task]; // Add points for the current task
                        dp[day][lastTask] = Math.max(dp[day][lastTask], taskPoints); // Update the maximum points
                    }
                }
            }
        }

        return dp[days - 1][3]; // Maximum points on the last day with no restriction
    }

    // Space-optimized approach to solve the problem
    // Time Complexity: O(N * 4 * 3) - Similar to tabulation, but uses reduced space
    // Space Complexity: O(4) - Only stores results for the current and previous day
    public static int ninjaTrainingSpaceOptimized(int[][] meritPoints) {
        int days = meritPoints.length;
        int[] prevDay = new int[4]; // Array to store results for the previous day

        // Base case: Day 0
        prevDay[0] = Math.max(meritPoints[0][1], meritPoints[0][2]); // Max points if last task was 0
        prevDay[1] = Math.max(meritPoints[0][0], meritPoints[0][2]); // Max points if last task was 1
        prevDay[2] = Math.max(meritPoints[0][0], meritPoints[0][1]); // Max points if last task was 2
        prevDay[3] = Math.max(meritPoints[0][0], Math.max(meritPoints[0][1], meritPoints[0][2])); // Max points if no restriction

        // Update for subsequent days
        for (int day = 1; day < days; day++) {
            int[] currDay = new int[4]; // Array to store results for the current day
            for (int lastTask = 0; lastTask < 4; lastTask++) {
                currDay[lastTask] = 0; // Initialize current cell
                for (int task = 0; task < 3; task++) {
                    if (task != lastTask) { // Ensure the task is not the same as the last task
                        int taskPoints = meritPoints[day][task] + prevDay[task]; // Add points for the current task
                        currDay[lastTask] = Math.max(currDay[lastTask], taskPoints); // Update the maximum points
                    }
                }
            }
            prevDay = currDay; // Update previous day results
        }

        return prevDay[3]; // Maximum points on the last day with no restriction
    }
}

