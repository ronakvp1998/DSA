package com.questions.strivers.graph.minspanningtreedisjointset;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * =====================================================================================
 *  PROBLEM STATEMENT (Minimum Spanning Tree using Prim’s Algorithm)
 * =====================================================================================
 *
 * Given a connected, undirected, weighted graph with V vertices,
 * find the Minimum Spanning Tree (MST).
 *
 * A Minimum Spanning Tree is a subset of edges such that:
 * - All vertices are connected
 * - No cycles are formed
 * - The total edge weight is minimized
 *
 * -------------------------------------------------------------------------------------
 *
 * COMMON APPLICATIONS:
 * - Network design (LAN, roads, pipelines)
 * - Minimum cost connectivity
 * - Graph optimization problems
 *
 * =====================================================================================
 *
 * APPROACH: PRIM'S ALGORITHM (Greedy)
 * -------------------------------------------------------------------------------------
 *
 * 1️⃣ Start from any node (here node 0).
 * 2️⃣ Use a Min Heap (PriorityQueue) to always pick the edge
 *    with the smallest weight.
 * 3️⃣ Add the selected edge to the MST if it connects a new vertex.
 * 4️⃣ Repeat until all vertices are included.
 *
 * OPTIMIZATIONS USED:
 * - Priority Queue (Min Heap)
 * - Visited array to avoid cycles
 *
 * IMPORTANT:
 * - Multiple entries of the same node can exist in the PQ.
 * - Only the first time a node is visited, its edge is accepted.
 *
 * =====================================================================================
 *
 * TIME & SPACE COMPLEXITY
 * -------------------------------------------------------------------------------------
 *
 * Time Complexity:
 * - O(E log E)
 *   Each edge is pushed into the priority queue at most once.
 *
 * Space Complexity:
 * - O(V + E)
 *   Adjacency list + visited array + priority queue
 *
 * =====================================================================================
 */
public class PrimsAlgorithm {

    /**
     * Computes the Minimum Spanning Tree using Prim’s Algorithm
     * and prints all edges in the MST.
     *
     * @param V   Number of vertices
     * @param adj Adjacency list where:
     *            adj.get(u) contains [v, weight]
     *
     * @return Total weight of the MST
     */
    static int spanningTree(int V,
                            ArrayList<ArrayList<ArrayList<Integer>>> adj) {

        /**
         * Min Heap storing:
         * - edge weight
         * - current node
         * - parent node (from where it was reached)
         */
        PriorityQueue<Pair> pq =
                new PriorityQueue<>((a, b) -> a.weight - b.weight);

        // Marks whether a vertex is already included in MST
        boolean[] visited = new boolean[V];

        // Stores MST edges explicitly for printing later
        List<int[]> mstEdges = new ArrayList<>();

        // Start from node 0 with weight 0
        pq.add(new Pair(0, 0, -1));

        int mstSum = 0;

        /**
         * Main loop:
         * - Extract minimum edge
         * - If node is already visited, skip
         * - Otherwise include it in MST
         */
        while (!pq.isEmpty()) {

            Pair cur = pq.poll();
            int node = cur.node;
            int weight = cur.weight;
            int parent = cur.parent;

            // Ignore already processed nodes
            if (visited[node]) continue;

            // Mark node as part of MST
            visited[node] = true;
            mstSum += weight;

            // Store MST edge (ignore starting node)
            if (parent != -1) {
                mstEdges.add(new int[]{parent, node, weight});
            }

            // Traverse adjacency list
            for (ArrayList<Integer> edge : adj.get(node)) {
                int adjNode = edge.get(0);
                int edgeWeight = edge.get(1);

                // Push only if not yet visited
                if (!visited[adjNode]) {
                    pq.add(new Pair(edgeWeight, adjNode, node));
                }
            }
        }

        /**
         * Print MST edges AFTER algorithm completes
         * (Best practice – avoids duplicate or missed edges)
         */
        System.out.println("Edges in the Minimum Spanning Tree:");
        for (int[] e : mstEdges) {
            System.out.println(e[0] + " -- " + e[1] + " (weight = " + e[2] + ")");
        }

        return mstSum;
    }

    /**
     * Helper class used in Priority Queue
     */
    static class Pair {
        int weight;
        int node;
        int parent;

        Pair(int weight, int node, int parent) {
            this.weight = weight;
            this.node = node;
            this.parent = parent;
        }
    }

    /**
     * =====================================================================================
     *  MAIN METHOD (TESTING)
     * =====================================================================================
     *
     * Graph:
     * 0 --1(2)
     * |  /
     * 2(1)
     * |
     * 3 --4
     */
    public static void main(String[] args) {

        int V = 5;

        ArrayList<ArrayList<ArrayList<Integer>>> adj = new ArrayList<>();

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

        // Undirected graph representation
        for (int[] e : edges) {
            int u = e[0];
            int v = e[1];
            int w = e[2];

            ArrayList<Integer> uv = new ArrayList<>();
            uv.add(v);
            uv.add(w);

            ArrayList<Integer> vu = new ArrayList<>();
            vu.add(u);
            vu.add(w);

            adj.get(u).add(uv);
            adj.get(v).add(vu);
        }

        int totalWeight = spanningTree(V, adj);
        System.out.println("Total weight of MST: " + totalWeight);
    }
}
