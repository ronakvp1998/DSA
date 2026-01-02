package com.questions.strivers.graph.shortestpathAlgo;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * ==================================================================================================
 *  LeetCode / Striver Problem: Minimum Multiplications to Reach End
 * ==================================================================================================
 *
 * ------------------------------- PROBLEM STATEMENT -----------------------------------------------
 *
 * You are given:
 * - An integer array `arr` of size n (allowed multipliers)
 * - Two integers `start` and `end`
 *
 * You can perform the following operation any number of times:
 * - Multiply the current number with any element from `arr`
 * - Take modulo 100000 after multiplication
 *
 * Your task is to find the **minimum number of multiplications** required
 * to transform `start` into `end`.
 *
 * If it is not possible to reach `end`, return **-1**.
 *
 * ------------------------------- EXAMPLE ----------------------------------------------------------
 *
 * Input:
 *   arr = {2, 5, 7}, start = 3, end = 30
 *
 * Output:
 *   2
 *
 * Explanation:
 *   3 -> 3 * 2 = 6
 *   6 -> 6 * 5 = 30
 *
 * ------------------------------- IMPORTANT CONSTRAINTS --------------------------------------------
 *
 * - 0 <= start, end < 100000
 * - 1 <= arr[i] <= 100000
 * - All operations are done modulo 100000
 *
 * ==================================================================================================
 *  APPROACH USED: Breadth First Search (BFS) on State Space Graph
 * ==================================================================================================
 *
 * - Each number (0 to 99999) is treated as a node.
 * - An edge exists from `x` to `(x * arr[i]) % 100000`.
 * - Each edge has equal weight (1 multiplication).
 *
 * Since all transitions have equal cost, **BFS guarantees the shortest path**.
 *
 * ==================================================================================================
 */

public class MinMultiplicationsReachEnd {

    /**
     * Finds the minimum number of multiplications needed to reach `end` from `start`.
     *
     * @param arr   Array of allowed multipliers
     * @param start Starting number
     * @param end   Target number
     * @return Minimum multiplications required, or -1 if unreachable
     */
    public static int minimumMultiplications(int[] arr, int start, int end) {

        /*
         * Queue stores pairs of:
         * [0] -> current number (node)
         * [1] -> number of steps (multiplications) taken to reach this node
         *
         * BFS ensures we always process states with the minimum steps first.
         */
        Queue<int[]> q = new LinkedList<>();

        // Start BFS from the initial number with 0 multiplications
        q.offer(new int[]{start, 0});

        /*
         * Distance array:
         * dist[x] = minimum number of multiplications needed to reach number x
         *
         * Size is 100000 because modulo operation restricts values to [0, 99999]
         */
        int[] dist = new int[100000];

        // Initialize distances with a very large value (acts as infinity)
        Arrays.fill(dist, (int) 1e9);

        // Distance to reach start from start is 0
        dist[start] = 0;

        // Modulo constraint
        int mod = 100000;

        // ---------------------------- BFS TRAVERSAL --------------------------------
        while (!q.isEmpty()) {

            // Remove front element from queue
            int[] current = q.poll();
            int node = current[0];
            int steps = current[1];

            /*
             * Try multiplying current number with each allowed multiplier
             * This generates all adjacent states.
             */
            for (int factor : arr) {

                // Compute next number using modulo
                int num = (factor * node) % mod;

                /*
                 * If this path gives fewer multiplications,
                 * update the distance and push into queue.
                 */
                if (steps + 1 < dist[num]) {

                    dist[num] = steps + 1;

                    // If target is reached, return immediately
                    // BFS guarantees this is the minimum step count
                    if (num == end) {
                        return steps + 1;
                    }

                    // Add the new state to the BFS queue
                    q.offer(new int[]{num, steps + 1});
                }
            }
        }

        // If BFS completes and end is never reached
        return -1;
    }

    /**
     * ---------------------------------- MAIN METHOD ----------------------------------
     * Used for local testing and verification.
     */
    public static void main(String[] args) {

        int start = 3;
        int end = 30;
        int[] arr = {2, 5, 7};

        int ans = minimumMultiplications(arr, start, end);

        System.out.println(ans); // Expected Output: 2
    }
}
