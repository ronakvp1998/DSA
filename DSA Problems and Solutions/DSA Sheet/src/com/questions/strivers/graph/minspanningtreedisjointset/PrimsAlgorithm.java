package com.questions.strivers.graph.minspanningtreedisjointset;

import java.util.ArrayList;
import java.util.PriorityQueue;


public class PrimsAlgorithm {

    /**
     * Function to calculate sum of weights of MST using Prim's Algorithm
     *
     * @param V   Number of vertices
     * @param adj Adjacency list where:
     *            adj.get(u) contains list of [v, weight]
     * @return Sum of weights of MST
     */
    static int spanningTree(int V,
                            ArrayList<ArrayList<ArrayList<Integer>>> adj) {

        // ----------------------------
        // Min Heap: {edgeWeight, node}
        // ----------------------------
        PriorityQueue<Pair> pq =
                new PriorityQueue<>((x, y) -> x.distance - y.distance);

        // visited array to mark nodes included in MST
        int[] vis = new int[V];

        // Start from node 0 with weight 0
        pq.add(new Pair(0, 0));

        int sum = 0;

        // ----------------------------
        // Prim's Algorithm
        // ----------------------------
        while (!pq.isEmpty()) {

            Pair curr = pq.poll();
            int wt = curr.distance;
            int node = curr.node;

            // If already included in MST, skip
            if (vis[node] == 1) continue;

            // Include node in MST
            vis[node] = 1;
            sum += wt;

            // Traverse all adjacent edges
            for (int i = 0; i < adj.get(node).size(); i++) {

                int adjNode = adj.get(node).get(i).get(0);
                int edW = adj.get(node).get(i).get(1);

                // If adjacent node is not yet part of MST
                if (vis[adjNode] == 0) {
                    pq.add(new Pair(edW, adjNode));
                }
            }
        }

        return sum;
    }

    /**
     * Pair class for PriorityQueue
     */
    static class Pair {
        int node;
        int distance;

        public Pair(int distance, int node) {
            this.node = node;
            this.distance = distance;
        }
    }

    // ---------------------------------- MAIN METHOD ----------------------------------
    public static void main(String[] args) {

        int V = 5;

        // Adjacency list representation
        ArrayList<ArrayList<ArrayList<Integer>>> adj = new ArrayList<>();

        int[][] edges = {
                {0, 1, 2},
                {0, 2, 1},
                {1, 2, 1},
                {2, 3, 2},
                {3, 4, 1},
                {4, 2, 2}
        };

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

        int sum = spanningTree(V, adj);
        System.out.println("The sum of all the edge weights: " + sum);
    }
}
