package com.questions.strivers.graph.shortestpathAlgo;

import java.util.*;

/**
 * =====================================================================================
 *  Print Shortest Path using Dijkstra's Algorithm
 * =====================================================================================
 *
 * PROBLEM:
 * --------
 * Given a weighted graph, find the shortest path from source (src)
 * to destination (dest) and PRINT the actual path.
 *
 * GRAPH TYPE:
 * -----------
 * - Weighted
 * - Undirected (can be easily adapted to directed)
 * - Positive edge weights
 *
 * =====================================================================================
 *  APPROACH:
 * =====================================================================================
 *  1. Use Dijkstra's Algorithm to compute shortest distances
 *  2. Maintain a parent[] array to reconstruct path
 *  3. After reaching destination, backtrack using parent[]
 *
 * =====================================================================================
 *  WHY PARENT ARRAY?
 * =====================================================================================
 *  parent[v] = u means:
 *  The shortest path to v comes from u
 *
 * =====================================================================================
 */

public class PrintShortestPathSrcToDistDijkstra {

    // Pair class to store (node, distance)
    static class Pair {
        int node;
        int wt;

        Pair(int node, int wt) {
            this.node = node;
            this.wt = wt;
        }
    }

    /**
     * Prints the shortest path from src to dest
     */
    public static List<Integer> shortestPath(
            int n,
            int[][] edges,
            int src,
            int dest
    ) {

        // -----------------------------
        // Step 1: Build adjacency list
        // -----------------------------
        List<List<Pair>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        // Undirected graph
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int wt = edge[2];

            adj.get(u).add(new Pair(v, wt));
            adj.get(v).add(new Pair(u, wt));
        }

        // ------------------------------------
        // Step 2: Distance & Parent arrays
        // ------------------------------------
        int[] dist = new int[n];
        int[] parent = new int[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        dist[src] = 0;
        parent[src] = src;

        // ------------------------------------
        // Step 3: Min Heap (Dijkstra)
        // ------------------------------------
        PriorityQueue<Pair> pq =
                new PriorityQueue<>(Comparator.comparingInt(a -> a.wt));

        pq.offer(new Pair(src, 0));

        while (!pq.isEmpty()) {

            Pair curr = pq.poll();
            int node = curr.node;
            int currDist = curr.wt;

            // Skip outdated entry
            if (currDist > dist[node]) continue;

            // Relax edges
            for (Pair neighbor : adj.get(node)) {

                int nextNode = neighbor.node;
                int edgeWeight = neighbor.wt;

                if (currDist + edgeWeight < dist[nextNode]) {

                    dist[nextNode] = currDist + edgeWeight;
                    parent[nextNode] = node;   // Track path
                    pq.offer(new Pair(nextNode, dist[nextNode]));
                }
            }
        }

        // ------------------------------------
        // Step 4: Reconstruct Path
        // ------------------------------------
        List<Integer> path = new ArrayList<>();

        // If destination unreachable
        if (dist[dest] == Integer.MAX_VALUE) {
            return path;  // empty
        }

        int node = dest;
        while (node != parent[node]) {
            path.add(node);
            node = parent[node];
        }
        path.add(src);

        // Reverse to get src -> dest
        Collections.reverse(path);

        return path;
    }

    /**
     * Driver Code
     */
    public static void main(String[] args) {

        int n = 6;
        int[][] edges = {
                {0, 1, 4},
                {0, 2, 4},
                {1, 2, 2},
                {1, 3, 5},
                {2, 3, 8},
                {2, 4, 10},
                {3, 4, 2},
                {3, 5, 6},
                {4, 5, 3}
        };

        int src = 0;
        int dest = 5;

        List<Integer> path = shortestPath(n, edges, src, dest);

        if (path.isEmpty()) {
            System.out.println("No path exists");
        } else {
            System.out.println("Shortest Path: " + path);
        }
    }
}
