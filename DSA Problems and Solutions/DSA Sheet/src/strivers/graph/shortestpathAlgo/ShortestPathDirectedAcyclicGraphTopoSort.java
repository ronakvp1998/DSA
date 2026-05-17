package com.questions.strivers.graph.shortestpathAlgo;

import java.util.*;

/**
 * ==================================================================================================
 *  Shortest Path in a Directed Acyclic Graph (DAG) using Topological Sort
 * ==================================================================================================
 *
 * ------------------------------- PROBLEM STATEMENT -----------------------------------------------
 * Given a DAG with N vertices (0 to N-1) and M directed edges with weights,
 * find the shortest path from the source vertex (0) to all other vertices.
 * - edges[i] = [u, v, w] indicates a directed edge from u to v with weight w.
 * - If a vertex is unreachable, return -1 for that vertex.
 *
 * --------------------------------------------------------------------------------------------------
 *                                APPROACH — TOPOLOGICAL SORT
 * --------------------------------------------------------------------------------------------------
 * 🔑 KEY IDEA:
 * - DAG allows shortest path calculation using **topological ordering**.
 * - Steps:
 *   1. Perform Topological Sort using DFS.
 *   2. Initialize distance array with infinity; set source distance = 0.
 *   3. Process nodes in topological order:
 *       - For each node, relax edges to neighbors.
 *       - Update neighbor distances if a shorter path is found.
 *   4. Replace unreachable nodes (distance = infinity) with -1.
 *
 * - This approach is more efficient than Dijkstra for DAGs (O(N+M) vs O((N+M)logN)).
 *
 * COMPLEXITY ANALYSIS:
 * - Time Complexity: O(N + M)
 *      - Topological Sort (DFS): O(N + M)
 *      - Edge relaxation: O(M)
 * - Space Complexity: O(N + M)
 *      - Adjacency list: O(N + M)
 *      - Stack for topological sort: O(N)
 *      - Distance array: O(N)
 *
 * --------------------------------------------------------------------------------------------------
 */
public class ShortestPathDirectedAcyclicGraphTopoSort {
    static class Pair{
        int node;
        int wt;
        Pair(int node, int wt){
            this.node = node;
            this.wt = wt;
        }
    }

    /**
     * Perform DFS to build topological order.
     * @param node Current node
     * @param adj  Weighted adjacency list
     * @param visited Array to track visited nodes
     * @param stack Stack to store topological order
     */
    private static void topoSortDFS(int node, List<List<Pair>> adj,
                                 boolean[] visited, Stack<Integer> stack) {
        visited[node] = true;

        for (Pair neighbor : adj.get(node)) {
            if (!visited[neighbor.node]) {
                topoSortDFS(neighbor.node, adj, visited, stack);
            }
        }

        // Push current node after visiting all neighbors
        stack.push(node);
    }

    /**
     * Compute shortest paths from source vertex (0) in a DAG.
     * @param N Number of vertices
     * @param M Number of edges
     * @param edges Edge list: [u, v, w]
     * @return Distance array from source to all vertices
     */
    private static int[] shortestPath(int N, int M, int[][] edges) {

        // Create adjacency list with weights
        List<List<Pair>> adj = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            adj.add(new ArrayList<>());
        }
        for (int i = 0; i < M; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            int wt = edges[i][2];
            adj.get(u).add(new Pair(v, wt));
        }

        // Topological sort
        boolean[] visited = new boolean[N];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < N; i++) {
            if (!visited[i]) {
                topoSortDFS(i, adj, visited, stack);
            }
        }

        // Initialize distances
        int[] dist = new int[N];
        Arrays.fill(dist, (int)1e9);
        // Distance to source(0) will be 0
        dist[0] = 0;

        // Process nodes in topological order
        while (!stack.isEmpty()) {
            int u = stack.pop();
            // If the node is reachable
            if (dist[u] != (int)1e9) {
                // Traverse all neighbors and update their distances
                for (Pair neighbor : adj.get(u)) {
                    int v = neighbor.node;
                    int wt = neighbor.wt;
                    // Relax the edge
                    if (dist[u] + wt < dist[v]) {
                        dist[v] = dist[u] + wt;
                    }
                }
            }
        }

        // Replace unreachable nodes with -1
        for (int i = 0; i < N; i++) {
            if (dist[i] == (int)1e9) {
                dist[i] = -1;
            }
        }

        return dist;
    }

    /**
     * ============================== DRIVER CODE ===============================
     */
    public static void main(String[] args) {

        int N = 6, M = 7;
        int[][] edges = {
                {0,1,2}, {0,4,1}, {4,5,4},
                {4,2,2}, {1,2,3}, {2,3,6}, {5,3,1}
        };

        int[] result = shortestPath(N, M, edges);

        System.out.println("Shortest distances from source (0):");
        for (int i = 0; i < result.length; i++) {
            System.out.println("Vertex " + i + " -> " + result[i]);
        }
    }
}
