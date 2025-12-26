package com.questions.strivers.graph.shortestpathAlgo;

import java.util.*;

/**
 * ==================================================================================================
 *         Shortest Path in an Undirected Graph with Unit Weights (BFS)
 * ==================================================================================================
 *
 * ------------------------------- PROBLEM STATEMENT -----------------------------------------------
 * Given an undirected graph with N vertices (labeled 0 to N-1) and M edges of unit weight,
 * find the shortest distance from the source vertex to all other vertices.
 * - Source vertex is assumed to be 'src'.
 * - If a vertex is unreachable from the source, return -1 for that vertex.
 *
 * --------------------------------------------------------------------------------------------------
 *                                APPROACH — BFS
 * --------------------------------------------------------------------------------------------------
 * 🔑 KEY IDEA:
 * - BFS explores vertices in increasing order of distance from the source in an unweighted graph.
 * - Maintain a distance array initialized to "infinity" (a large number).
 * - Push source node into the queue and set its distance to 0.
 * - For each node dequeued, traverse neighbors:
 *     - If a shorter distance is found (current distance + 1 < neighbor distance), update it.
 *     - Push neighbor to queue for further exploration.
 * - At the end, unreachable vertices will remain at "infinity"; convert them to -1.
 *
 * COMPLEXITY ANALYSIS:
 * - Time Complexity : O(N + M)
 *      - Building adjacency list: O(M)
 *      - BFS traversal: O(N + M), since each vertex and edge is processed at most once.
 * - Space Complexity: O(N + M)
 *      - Adjacency list: O(N + M)
 *      - Queue: O(N)
 *      - Distance array: O(N)
 *
 * --------------------------------------------------------------------------------------------------
 */
public class ShortestPathUndirectedGraphUnitDist {

    /**
     * Function to compute shortest path from source using BFS
     * @param edges Edge list of the graph
     * @param N     Number of vertices
     * @param M     Number of edges
     * @param src   Source vertex
     * @return      Distance array from source to all vertices (-1 if unreachable)
     */
    private static int[] shortestPath(int[][] edges, int N, int M, int src) {

        // Create adjacency list for undirected graph
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            adj.add(new ArrayList<>());
        }

        // Fill adjacency list from edge list
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]); // undirected graph
        }

        // Initialize distance array with a large value (infinity)
        int[] dist = new int[N];
        Arrays.fill(dist, (int) 1e9);

        // Distance of source from itself is 0
        dist[src] = 0;

        // Queue for BFS
        Queue<Integer> q = new LinkedList<>();
        q.add(src);

        // BFS traversal
        while (!q.isEmpty()) {
            int node = q.poll();

            // Traverse all neighbors
            for (int neighbor : adj.get(node)) {
                // If we found a shorter distance, update and enqueue neighbor
                if (dist[node] + 1 < dist[neighbor]) {
                    dist[neighbor] = dist[node] + 1;
                    q.add(neighbor);
                }
            }
        }

        // Convert unreachable vertices from "infinity" to -1
        for (int i = 0; i < N; i++) {
            if (dist[i] == (int) 1e9) {
                dist[i] = -1;
            }
        }

        return dist;
    }

    /**
     * ============================== DRIVER CODE ===============================
     */
    public static void main(String[] args) {

        int N = 9, M = 10;
        int[][] edges = {
                {0, 1}, {0, 3}, {3, 4}, {4, 5}, {5, 6},
                {1, 2}, {2, 6}, {6, 7}, {7, 8}, {6, 8}
        };

        int src = 0; // source vertex
        int[] result = shortestPath(edges, N, M, src);

        System.out.println("Shortest distances from source " + src + ":");
        for (int i = 0; i < result.length; i++) {
            System.out.println("Vertex " + i + " -> " + result[i]);
        }
    }
}
