package com.questions.strivers.graph.minspanningtreedisjointset;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * =====================================================================================
 *  Prim's Algorithm - Minimum Spanning Tree (MST)
 * =====================================================================================
 *
 * PROBLEM:
 * --------
 * Given an undirected, weighted, connected graph with V vertices,
 * find the Minimum Spanning Tree (MST) and return the sum of its edge weights.
 *
 * A Minimum Spanning Tree:
 * - Connects all vertices
 * - Has exactly (V - 1) edges
 * - Has minimum possible total edge weight
 *
 * -------------------------------------------------------------------------------------
 * APPROACH (Greedy + Min Heap):
 * -------------------------------------------------------------------------------------
 * 1. Start from any node (here, node 0).
 * 2. Always pick the edge with the minimum weight that connects:
 *      - a visited node
 *      - to an unvisited node
 * 3. Use a Min Priority Queue to efficiently get the smallest edge.
 * 4. Maintain a visited[] array to avoid cycles.
 * 5. Continue until all vertices are included in the MST.
 *
 * -------------------------------------------------------------------------------------
 * DATA STRUCTURE USED:
 * -------------------------------------------------------------------------------------
 * - PriorityQueue<Pair> → Min Heap based on edge weight
 * - visited[]           → Marks nodes already included in MST
 *
 * -------------------------------------------------------------------------------------
 * TIME COMPLEXITY:
 * -------------------------------------------------------------------------------------
 * O(E log V)
 * - Each edge can be inserted into the priority queue
 * - Heap operations take O(log V)
 *
 * -------------------------------------------------------------------------------------
 * SPACE COMPLEXITY:
 * -------------------------------------------------------------------------------------
 * O(V + E)
 * - Adjacency list storage
 * - Priority Queue
 * - Visited array
 *
 * =====================================================================================
 */
public class PrimsAlgorithm {

    /**
     * Computes the sum of edge weights of the Minimum Spanning Tree.
     *
     * @param V   Number of vertices
     * @param adj Adjacency list representation where:
     *            adj.get(u) -> list of [v, weight]
     * @return Sum of MST edge weights
     */
    static int spanningTree(int V,
                            ArrayList<ArrayList<ArrayList<Integer>>> adj) {

        // Min Heap storing (edgeWeight, node)
        PriorityQueue<Pair> pq =
                new PriorityQueue<>((a, b) -> a.distance - b.distance);

        // visited[i] = 1 → node i is already included in MST
        int[] visited = new int[V];

        // Start from node 0 with edge weight 0
        pq.add(new Pair(0, 0));

        int mstSum = 0;

        // Process until heap is empty
        while (!pq.isEmpty()) {

            Pair current = pq.poll();
            int weight = current.distance;
            int node = current.node;

            // Skip if already included in MST
            if (visited[node] == 1) continue;

            // Include node in MST
            visited[node] = 1;
            mstSum += weight;

            // Traverse adjacent nodes
            for (ArrayList<Integer> edge : adj.get(node)) {

                int adjNode = edge.get(0);
                int edgeWeight = edge.get(1);

                // Add only if adjacent node is not yet visited
                if (visited[adjNode] == 0) {
                    pq.add(new Pair(edgeWeight, adjNode));
                }
            }
        }

        return mstSum;
    }

    /**
     * Helper Pair class for Priority Queue
     * Stores:
     * - node      → current node
     * - distance  → edge weight to reach this node
     */
    static class Pair {
        int node;
        int distance;

        Pair(int distance, int node) {
            this.node = node;
            this.distance = distance;
        }
    }

    /**
     * -------------------------------- MAIN METHOD --------------------------------
     * Sample graph construction and MST execution
     */
    public static void main(String[] args) {

        int V = 5;

        // Adjacency List
        ArrayList<ArrayList<ArrayList<Integer>>> adj = new ArrayList<>();

        // Edge list: {u, v, weight}
        int[][] edges = {
                {0, 1, 2},
                {0, 2, 1},
                {1, 2, 1},
                {2, 3, 2},
                {3, 4, 1},
                {4, 2, 2}
        };

        // Initialize adjacency list
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        // Undirected graph → add edges both ways
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];

            ArrayList<Integer> uv = new ArrayList<>();
            uv.add(v);
            uv.add(w);

            ArrayList<Integer> vu = new ArrayList<>();
            vu.add(u);
            vu.add(w);

            adj.get(u).add(uv);
            adj.get(v).add(vu);
        }

        int result = spanningTree(V, adj);
        System.out.println("Sum of MST edge weights: " + result);
    }
}
