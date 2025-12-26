package com.questions.strivers.graph.shortestpathAlgo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * ==================================================================================================
 *  Dijkstra's Algorithm for Shortest Path in Weighted Graph
 * ==================================================================================================
 *
 * ------------------------------- PROBLEM STATEMENT -----------------------------------------------
 * Given a weighted, undirected, and connected graph with V vertices and an adjacency list adj:
 * - adj[i] contains lists of two integers: [j, w] meaning an edge from i -> j with weight w.
 * - Source vertex S is given.
 * - Task: Find shortest distance from S to all vertices.
 * - Graph does not contain negative weight edges.
 *
 * --------------------------------------------------------------------------------------------------
 *                                APPROACH — DIJKSTRA
 * --------------------------------------------------------------------------------------------------
 * 🔑 KEY IDEA:
 * - Use a **priority queue** (min-heap) to always pick the node with the smallest known distance.
 * - Steps:
 *   1. Initialize distance array with infinity; distance of source = 0.
 *   2. Push source node into priority queue as [distance, node].
 *   3. While queue is not empty:
 *       - Pop the node with smallest distance.
 *       - For each neighbor, calculate potential new distance.
 *       - If smaller, update distance and push neighbor into the queue.
 *   4. After processing all nodes, distance array contains shortest paths.
 *
 * COMPLEXITY ANALYSIS:
 * - Time Complexity: O((V + E) log V)
 *      - Each node can be pushed into the priority queue multiple times.
 *      - Priority queue operations take log V time.
 * - Space Complexity: O(V + E)
 *      - Adjacency list: O(V + E)
 *      - Distance array: O(V)
 *      - Priority queue: O(V)
 *
 * --------------------------------------------------------------------------------------------------
 */
public class DijkstrasAlgorithmPriorityQueue {

    /**
     * Compute shortest distances from source vertex S using Dijkstra's algorithm.
     * @param V Number of vertices
     * @param adj Weighted adjacency list
     * @param S Source vertex
     * @return Array of shortest distances from S
     */
    private static int[] dijkstra(int V, ArrayList<int[]>[] adj, int S) {
        // Priority queue to select the node with minimum distance
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        // Initialize distances with infinity
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);

        // Distance to source = 0
        dist[S] = 0;
        pq.offer(new int[]{0, S}); // {distance, node}

        // Process the queue
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int dis = curr[0];  // Distance of current node
            int node = curr[1]; // Current node

            // Check all neighbors
            for (int[] edge : adj[node]) {
                int adjNode = edge[0];  // Neighbor vertex
                int weight = edge[1];   // Edge weight

                // Relax edge if new distance is smaller
                if (dis + weight < dist[adjNode]) {
                    dist[adjNode] = dis + weight;
                    pq.offer(new int[]{dist[adjNode], adjNode});
                }
            }
        }

        return dist;
    }

    /**
     * ============================== DRIVER CODE ===============================
     */
    public static void main(String[] args) {
        int V = 3, E = 3, S = 2;

        // Create adjacency list
        ArrayList<int[]>[] adj = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }

        // Add edges {node, weight} (undirected graph)
        adj[0].add(new int[]{1, 1});
        adj[0].add(new int[]{2, 6});
        adj[1].add(new int[]{2, 3});
        adj[1].add(new int[]{0, 1});
        adj[2].add(new int[]{1, 3});
        adj[2].add(new int[]{0, 6});

        // Compute shortest distances
        int[] res = dijkstra(V, adj, S);

        // Print results
        System.out.println("Shortest distances from source vertex " + S + ":");
        for (int i = 0; i < V; i++) {
            System.out.println("Vertex " + i + " -> " + res[i]);
        }
    }
}
