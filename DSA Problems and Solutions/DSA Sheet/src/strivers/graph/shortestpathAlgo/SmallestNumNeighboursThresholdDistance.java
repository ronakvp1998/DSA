package com.questions.strivers.graph.shortestpathAlgo;

import java.util.Arrays;

/**
 * ================================================================================================
 *  LeetCode Problem: 1334. Find the City With the Smallest Number of Neighbors at a Threshold Distance
 * ================================================================================================
 *
 * -------------------------------- PROBLEM STATEMENT ---------------------------------------------
 *
 * There are `n` cities numbered from 0 to n - 1.
 * You are given an array `edges` where:
 *   edges[i] = [from, to, weight]
 * represents a bidirectional edge between `from` and `to` with distance `weight`.
 *
 * You are also given an integer `distanceThreshold`.
 *
 * The distance between two cities is the length of the shortest path between them.
 *
 * Your task is to find the city with the **smallest number of cities reachable**
 * within the distance threshold (including itself).
 *
 * If multiple cities have the same smallest number, return the city
 * with the **greatest index**.
 *
 * -------------------------------- EXAMPLE -------------------------------------------------------
 *
 * Input:
 *   n = 4
 *   edges = [[0,1,3], [1,2,1], [1,3,4], [2,3,1]]
 *   distanceThreshold = 4
 *
 * Output:
 *   3
 *
 * ================================================================================================
 *  APPROACH USED: Floyd–Warshall Algorithm (All-Pairs Shortest Path)
 * ================================================================================================
 *
 * - Convert the graph into an adjacency matrix.
 * - Use Floyd–Warshall to compute shortest distances between all city pairs.
 * - For each city, count how many cities are reachable within `distanceThreshold`.
 * - Choose the city with:
 *      1. Minimum reachable cities
 *      2. Maximum index (tie-breaker)
 *
 * ================================================================================================
 */

public class SmallestNumNeighboursThresholdDistance {

    /**
     * Finds the city with the smallest number of reachable cities within threshold distance.
     *
     * @param n                 Number of cities
     * @param m                 Number of edges (not directly used, but part of input)
     * @param edges             Edge list [from, to, weight]
     * @param distanceThreshold Maximum allowed distance
     * @return City index satisfying the condition
     */
    public static int findCity(int n, int m, int[][] edges, int distanceThreshold) {

        /*
         * dist[i][j] will store the shortest distance from city i to city j
         */
        int[][] dist = new int[n][n];

        /*
         * Initialize distances:
         * - Use Integer.MAX_VALUE to represent "infinity"
         */
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }

        /*
         * Fill the distance matrix using the given edges.
         * Since the graph is undirected, update both directions.
         */
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int wt = edge[2];

            dist[u][v] = wt;
            dist[v][u] = wt;
        }

        /*
         * Distance from a city to itself is always 0
         */
        for (int i = 0; i < n; i++) {
            dist[i][i] = 0;
        }

        // --------------------------- FLOYD–WARSHALL --------------------------------
        /*
         * Try every city as an intermediate node
         */
        for (int k = 0; k < n; k++) {

            // Pick every source city
            for (int i = 0; i < n; i++) {

                // Pick every destination city
                for (int j = 0; j < n; j++) {

                    /*
                     * If either path is unreachable, skip
                     * Prevents integer overflow
                     */
                    if (dist[i][k] == Integer.MAX_VALUE ||
                            dist[k][j] == Integer.MAX_VALUE) {
                        continue;
                    }

                    /*
                     * Update shortest distance using intermediate city k
                     */
                    dist[i][j] = Math.min(dist[i][j],
                            dist[i][k] + dist[k][j]);
                }
            }
        }

        // ------------------------ FIND REQUIRED CITY ------------------------------
        int cntCity = n;   // Minimum reachable cities found so far
        int cityNo = -1;   // Result city

        /*
         * Count reachable cities for each city
         */
        for (int city = 0; city < n; city++) {

            int cnt = 0;

            for (int adjCity = 0; adjCity < n; adjCity++) {
                if (dist[city][adjCity] <= distanceThreshold) {
                    cnt++;
                }
            }

            /*
             * Update result:
             * - Choose city with smaller reachable count
             * - If tie, choose city with greater index
             */
            if (cnt <= cntCity) {
                cntCity = cnt;
                cityNo = city;
            }
        }

        return cityNo;
    }

    /**
     * ---------------------------------- MAIN METHOD ----------------------------------
     * Used for testing the solution.
     */
    public static void main(String[] args) {

        int n = 4;
        int m = 4;
        int[][] edges = {
                {0, 1, 3},
                {1, 2, 1},
                {1, 3, 4},
                {2, 3, 1}
        };
        int distanceThreshold = 4;

        int cityNo = findCity(n, m, edges, distanceThreshold);

        System.out.println("The answer is node: " + cityNo); // Expected output: 3
    }
}
