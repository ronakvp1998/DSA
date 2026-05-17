package com.questions.strivers.graph.shortestpathAlgo;

import java.util.*;

/**
 * =====================================================================================
 *  LeetCode 1976 - Number of Ways to Arrive at Destination
 * =====================================================================================
 *
 * PROBLEM STATEMENT:
 * ------------------
 * You are given a weighted, undirected graph with n nodes (0 to n-1).
 * Each road connects two nodes and takes a certain amount of time.
 *
 * Your task is to find:
 *  1) The shortest time required to travel from node 0 to node n-1
 *  2) The number of distinct paths that achieve this shortest time
 *
 * Return the count modulo (1e9 + 7).
 *
 * CONSTRAINTS:
 *  - 1 <= n <= 200
 *  - Graph is connected
 *  - All edge weights are positive
 *
 * =====================================================================================
 *  APPROACH:
 * =====================================================================================
 *  We use Dijkstra's Algorithm with a small modification:
 *
 *  Along with the shortest distance array (dist[]),
 *  we also maintain a ways[] array:
 *
 *   - dist[i]  -> shortest distance to node i
 *   - ways[i]  -> number of shortest paths to node i
 *
 *  RULES DURING DIJKSTRA:
 *  ---------------------
 *  If we find a strictly shorter path:
 *      - Update dist[]
 *      - Reset ways[] to ways of previous node
 *
 *  If we find another path with the same shortest distance:
 *      - Add ways from previous node
 *
 * =====================================================================================
 *  WHY THIS WORKS:
 * =====================================================================================
 *  Dijkstra ensures we process nodes in increasing distance order.
 *  Hence, when we relax edges, we are guaranteed correctness.
 *
 * =====================================================================================
 *  TIME & SPACE COMPLEXITY:
 * =====================================================================================
 *  Time Complexity:  O(E log V)
 *      - Priority Queue operations dominate
 *
 *  Space Complexity: O(V + E)
 *      - Graph + distance + ways + PQ
 *
 * =====================================================================================
 */

public class NumOfWaysToArriveAtDest {

    // Pair class to store (node, distance) for adjacency list and priority queue
    static class Pair {
        int node;
        long dist;

        Pair(int node, long dist) {
            this.node = node;
            this.dist = dist;
        }
    }

    private static final int MOD = (int) 1e9 + 7;

    /**
     * Finds the number of shortest paths from node 0 to node n-1
     */
    public static int countPaths(int n, int[][] roads) {

        // -----------------------------
        // Step 1: Build adjacency list
        // -----------------------------
        List<List<Pair>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        // Since roads are bi-directional, add edges both ways
        for (int[] road : roads) {
            int u = road[0];
            int v = road[1];
            int time = road[2];

            adj.get(u).add(new Pair(v, time));
            adj.get(v).add(new Pair(u, time));
        }

        // --------------------------------
        // Step 2: Distance & Ways arrays
        // --------------------------------
        long[] dist = new long[n];
        long[] ways = new long[n];

        Arrays.fill(dist, Long.MAX_VALUE);

        dist[0] = 0;   // Distance to source is 0
        ways[0] = 1;   // One way to start at source

        // --------------------------------
        // Step 3: Dijkstra using Min-Heap
        // --------------------------------
        PriorityQueue<Pair> pq =
                new PriorityQueue<>(Comparator.comparingLong(a -> a.dist));

        pq.offer(new Pair(0, 0));

        while (!pq.isEmpty()) {

            Pair curr = pq.poll();
            int node = curr.node;
            long currDist = curr.dist;

            // Skip outdated entries
            if (currDist > dist[node]) continue;

            // Relax edges
            for (Pair neighbor : adj.get(node)) {

                int nextNode = neighbor.node;
                long edgeWeight = neighbor.dist;

                long newDist = currDist + edgeWeight;

                // Case 1: Found a shorter path
                if (newDist < dist[nextNode]) {
                    dist[nextNode] = newDist;
                    ways[nextNode] = ways[node];
                    pq.offer(new Pair(nextNode, newDist));
                }
                // Case 2: Found another shortest path
                else if (newDist == dist[nextNode]) {
                    ways[nextNode] = (ways[nextNode] + ways[node]) % MOD;
                }
            }
        }

        // Final answer: number of shortest paths to destination
        return (int) (ways[n - 1] % MOD);
    }

    /**
     * Driver code for testing
     */
    public static void main(String[] args) {

        int n = 7;
        int[][] roads = {
                {0, 6, 7},
                {0, 1, 2},
                {1, 2, 3},
                {1, 3, 3},
                {6, 3, 3},
                {3, 5, 1},
                {6, 5, 1},
                {2, 5, 1},
                {0, 4, 5},
                {4, 6, 2}
        };

        System.out.println("Number of shortest paths: " + countPaths(n, roads));
        // Expected Output: 4
    }
}
