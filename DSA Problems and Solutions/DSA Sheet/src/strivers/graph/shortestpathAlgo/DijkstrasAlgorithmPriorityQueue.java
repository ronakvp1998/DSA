package com.questions.strivers.graph.shortestpathAlgo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * ==================================================================================================
 *  Dijkstra's Algorithm for Shortest Path in Weighted Graph
 *  Works for both directed and undirected graph only the adj list changes
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
    static class Pair {
        int v;
        int weight;

        Pair(int v, int weight){
            this.v = v;
            this.weight = weight;
        }
    }
    /**
     * dijkstra() function will work for both directed and undirected graphs without any change.
     * Compute shortest distances from source vertex S using Dijkstra's algorithm.
     * @param V Number of vertices
     * @param adj Weighted adjacency list
     * @param S Source vertex
     * @return Array of shortest distances from S
     */
    private static int[] dijkstra(int V, ArrayList<ArrayList<Pair>> adj, int S) {

        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> a.weight - b.weight);

        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[S] = 0;
        pq.offer(new Pair(S, 0));   // (node, distance)

        while(!pq.isEmpty()){
            Pair curr = pq.poll();
            int v = curr.v;
            int dis = curr.weight;

            for(Pair edge : adj.get(v)){
                int adjNode = edge.v;
                int weight = edge.weight;

                if(dis + weight < dist[adjNode]){
                    dist[adjNode] = dis + weight;
                    pq.offer(new Pair(adjNode, dist[adjNode]));
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
        ArrayList<ArrayList<Pair>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add( new ArrayList<>());
        }

        // Add edges {node, weight} (undirected graph)
        adj.get(0).add(new Pair(1, 1));
        adj.get(0).add(new Pair(2, 6));
        adj.get(1).add(new Pair(2, 3));
        adj.get(1).add(new Pair(0, 1));
        adj.get(2).add(new Pair(1, 3));
        adj.get(2).add(new Pair(0, 6));

        // Compute shortest distances
        int[] res = dijkstra(V, adj, S);

        // Print results
        System.out.println("Shortest distances from source vertex " + S + ":");
        for (int i = 0; i < V; i++) {
            System.out.println("Vertex " + i + " -> " + res[i]);
        }
    }
}
