package com.questions.strivers.graph.shortestpathAlgo;

import java.util.*;

// Problem Statement:
// You are in a city with n intersections (0 to n-1) connected by bidirectional roads.
// Each road takes certain time to travel. You need to find in how many different
// ways you can travel from node 0 to node n-1 *in the minimum possible time*.
// Return number of shortest paths modulo 1e9+7.

public class NumOfWaysToArriveAtDest {

    /**
     * Method to calculate number of distinct shortest paths from src to dst
     * using Dijkstra's Algorithm + Path Counting.
     *
     * @param n     Total number of intersections (nodes)
     * @param roads Each entry = [u, v, time] means bidirectional road between u & v with cost time
     * @param src   Starting intersection
     * @param dst   Destination intersection
     * @param K     (Not used here, present only because of template name)
     *
     * Algorithm Used:
     * ----------------
     * We use a modified Dijkstra algorithm.
     * Along with maintaining shortest distance, we also maintain:
     * ways[i] → number of shortest paths to reach node i.
     *
     * For every edge (u → v):
     * Case 1: Found a strictly shorter path
     *      dist[v] = dist[u] + weight
     *      ways[v] = ways[u]
     *
     * Case 2: Found another path with same shortest distance
     *      ways[v] += ways[u]
     *
     * Finally, return ways[dst].
     *
     * Time Complexity  : O(E log V)
     * Space Complexity : O(V + E)
     */
    private static int CheapestFLight(int n, int[][] roads, int src, int dst, int K) {

        // Step 1: Create adjacency list
        // Each list entry stores {neighborNode, travelTime}
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (int[] road : roads) {
            adj.get(road[0]).add(new int[]{road[1], road[2]});
            adj.get(road[1]).add(new int[]{road[0], road[2]});  // because roads are bidirectional
        }

        // Step 2: Priority Queue (Min Heap)
        // Stores {timeTaken, node}
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        // Step 3: Distance array stores shortest time to reach each node
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);

        // Step 4: Ways array stores number of shortest paths to each node
        int[] ways = new int[n];

        dist[src] = 0;     // distance to source is 0
        ways[src] = 1;     // one way to reach source
        pq.add(new int[]{0, src});

        int mod = (int) (1e9 + 7);

        // Step 5: Run Dijkstra
        while (!pq.isEmpty()) {

            int[] current = pq.poll();
            int dis = current[0];
            int node = current[1];

            // Traverse all neighbors
            for (int[] neighbor : adj.get(node)) {
                int adjNode = neighbor[0]; // neighbor node
                int edW = neighbor[1];     // edge weight

                // Case 1 → Found a shorter path
                if (dis + edW < dist[adjNode]) {
                    dist[adjNode] = dis + edW;

                    pq.add(new int[]{dis + edW, adjNode});

                    // Copy ways count from current node
                    ways[adjNode] = ways[node];
                }

                // Case 2 → Found another shortest path
                else if (dis + edW == dist[adjNode]) {
                    ways[adjNode] = (ways[adjNode] + ways[node]) % mod;
                }
            }
        }

        // Return total number of shortest ways to reach destination
        return ways[dst] % mod;
    }

    public static void main(String[] args) {

        int n = 7;

        // road[i] = {u, v, travelTime}
        int[][] roads = {
                {0, 6, 7}, {0, 1, 2}, {1, 2, 3},
                {1, 3, 3}, {6, 3, 3}, {3, 5, 1},
                {6, 5, 1}, {2, 5, 1}, {0, 4, 5},
                {4, 6, 2}
        };

        // Find number of shortest paths from 0 to 3
        int ans = CheapestFLight(n, roads, 0, 3, 1);

        System.out.println(ans);
    }
}
