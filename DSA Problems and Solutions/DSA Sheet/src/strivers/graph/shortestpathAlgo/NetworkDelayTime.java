package com.questions.strivers.graph.shortestpathAlgo;

import java.util.*;

/**
 * ================================================================================================
 *  LeetCode Problem: 743. Network Delay Time
 * ================================================================================================
 *
 * -------------------------------- PROBLEM STATEMENT ---------------------------------------------
 *
 * You are given a directed weighted graph with `n` nodes labeled from 1 to n.
 *
 * Each element in the array `times` is of the form:
 *      times[i] = {ui, vi, wi}
 * which represents a directed edge from node `ui` to node `vi`
 * taking `wi` units of time.
 *
 * A signal is sent from a starting node `k` at time = 0.
 * When a node receives the signal, it immediately forwards it to all
 * its outgoing neighbors.
 *
 * Your task is to return the **minimum time** required for **all nodes**
 * to receive the signal.
 *
 * If it is impossible for all nodes to receive the signal, return **-1**.
 *
 * -------------------------------- EXAMPLE -------------------------------------------------------
 *
 * Input:
 *   times = [[2,1,1], [2,3,1], [3,4,1]]
 *   n = 4
 *   k = 2
 *
 * Output:
 *   2
 *
 * Explanation:
 *   Signal travels:
 *   2 → 1 (time 1)
 *   2 → 3 (time 1)
 *   3 → 4 (time 2 total)
 *
 * ================================================================================================
 *  APPROACH USED: Dijkstra’s Algorithm (Single Source Shortest Path)
 * ================================================================================================
 *
 * - We want the shortest time from source `k` to all nodes.
 * - Graph has **non-negative edge weights**, so Dijkstra is optimal.
 * - After computing shortest distances, the answer is the **maximum**
 *   distance among all nodes.
 *
 * ================================================================================================
 */

public class NetworkDelayTime {

    /**
     * Computes the minimum time required for all nodes to receive the signal.
     *
     * @param times Directed edges with weights
     * @param n     Number of nodes
     * @param k     Source node
     * @return Minimum time for all nodes to receive signal, or -1 if impossible
     */
    private static int networkDelayTime(int[][] times, int n, int k) {

        // ----------------------------
        // STEP 1: Build Adjacency List
        // ----------------------------
        /*
         * adj[u] = list of {v, w}
         * meaning there is an edge u -> v with travel time w
         *
         * Size is (n + 1) because nodes are labeled from 1 to n
         */
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        // Populate adjacency list from input edges
        for (int[] time : times) {
            int u = time[0];
            int v = time[1];
            int w = time[2];
            adj.get(u).add(new int[]{v, w});
        }

        // ---------------------------------------
        // STEP 2: Min-Heap (Priority Queue)
        // ---------------------------------------
        /*
         * PriorityQueue stores pairs:
         * {current_time, node}
         *
         * Always processes the node with the minimum signal time first
         */
        PriorityQueue<int[]> pq =
                new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        // Start from source node k with time 0
        pq.offer(new int[]{0, k});

        // ---------------------------------------
        // STEP 3: Distance Array
        // ---------------------------------------
        /*
         * dist[i] = shortest time to reach node i from source k
         */
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        // Distance to source itself is 0
        dist[k] = 0;

        // ---------------------------------------
        // STEP 4: Dijkstra’s Algorithm
        // ---------------------------------------
        while (!pq.isEmpty()) {

            // Extract node with minimum known signal time
            int[] curr = pq.poll();
            int time = curr[0];
            int node = curr[1];

            /*
             * Traverse all outgoing edges from current node
             */
            for (int[] nbr : adj.get(node)) {

                int nextNode = nbr[0];
                int weight = nbr[1];

                /*
                 * Relaxation step:
                 * If a shorter path to nextNode is found, update it
                 */
                if (dist[nextNode] > time + weight) {
                    dist[nextNode] = time + weight;
                    pq.offer(new int[]{dist[nextNode], nextNode});
                }
            }
        }

        // ---------------------------------------
        // STEP 5: Compute Final Answer
        // ---------------------------------------
        int ans = 0;

        for (int i = 1; i <= n; i++) {

            // If any node is unreachable, return -1
            if (dist[i] == Integer.MAX_VALUE) {
                return -1;
            }

            // Track maximum signal time
            ans = Math.max(ans, dist[i]);
        }

        return ans;
    }

    /**
     * ---------------------------------- MAIN METHOD ----------------------------------
     * Used for local testing.
     */
    public static void main(String[] args) {

        int[][] times = {
                {2, 1, 1},
                {2, 3, 1},
                {3, 4, 1}
        };

        int n = 4;
        int k = 2;

        System.out.println(networkDelayTime(times, n, k)); // Expected Output: 2
    }
}
