package com.questions.strivers.graph.problemsbfsdfs;

import java.util.ArrayList;

/**
 * ==============================================
 *          Detect Cycle in Directed Graph
 * ==============================================
 *
 * Problem Statement:
 * Given a directed graph with V vertices and E edges, determine whether
 * the graph contains a cycle.
 *
 * Example:
 * Input: N = 10, E = 11
 * Graph represented as adjacency list.
 *
 * Approach:
 *  - We use DFS to detect cycles in a directed graph.
 *  - Maintain two arrays:
 *      1️⃣ vis[]     → marks whether a node has been visited at all
 *      2️⃣ pathVis[] → marks nodes in the current DFS path
 *
 *  - For each unvisited node, start DFS:
 *      - Mark node as visited and add to current path
 *      - Recur for all neighbors:
 *          - If neighbor not visited → DFS on neighbor
 *          - If neighbor visited AND present in current path → cycle detected
 *      - Backtrack: remove node from current path
 *
 * Complexity:
 *  - Time:  O(V + E) → visit every vertex & edge once
 *  - Space: O(V) → recursion stack + visited arrays
 *
 * Use Case:
 *  - Detecting cycles in directed graphs (like prerequisite courses, dependencies)
 *
 * Alternative Approaches:
 *  - Kahn's algorithm (Topological Sort based) can also detect cycles.
 *    If topological sort is not possible → cycle exists.
 */

public class DetectCycleDirectedDFS {

    /**
     * DFS helper to detect cycle from current node
     * @param node      Current node
     * @param adj       Adjacency list of graph
     * @param vis       Visited array for all nodes
     * @param pathVis   Tracks nodes in current DFS recursion path
     * @return true if cycle detected, false otherwise
     */
    private static boolean dfsCheck(int node, ArrayList<ArrayList<Integer>> adj,
                                    int vis[], int pathVis[]) {
        vis[node] = 1;         // mark node as visited
        pathVis[node] = 1;     // add node to current DFS path

        // Traverse all neighbors of current node
        for(int neighbor : adj.get(node)) {

            // If neighbor not visited → DFS on neighbor
            if(vis[neighbor] == 0) {
                if(dfsCheck(neighbor, adj, vis, pathVis)) {
                    return true;  // cycle detected in recursion
                }
            }
            // If neighbor visited AND in current DFS path → cycle
            else if(pathVis[neighbor] == 1) {
                return true;
            }
        }

        pathVis[node] = 0;  // backtrack: remove from current path
        return false;
    }

    /**
     * Function to detect if a directed graph contains a cycle
     * @param V    Number of vertices
     * @param adj  Adjacency list of the graph
     * @return true if cycle exists, false otherwise
     */
    private static boolean isCyclic(int V, ArrayList<ArrayList<Integer>> adj) {
        int vis[] = new int[V];       // visited array
        int pathVis[] = new int[V];   // current DFS path tracker

        // Run DFS from every unvisited vertex
        for(int i = 0; i < V; i++) {
            if(vis[i] == 0) {
                if(dfsCheck(i, adj, vis, pathVis)) return true;
            }
        }

        return false; // no cycle found
    }

    /**
     * Main method to test cycle detection
     */
    public static void main(String[] args) {

        int V = 11;   // number of vertices
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();

        // Initialize adjacency list
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        // Example directed graph edges
        adj.get(1).add(2);
        adj.get(2).add(3);
        adj.get(3).add(4);
        adj.get(3).add(7);
        adj.get(4).add(5);
        adj.get(5).add(6);
        adj.get(7).add(5);
        adj.get(8).add(9);
        adj.get(9).add(10);
        adj.get(10).add(8);  // creates a cycle

        boolean ans = isCyclic(V, adj);

        if (ans)
            System.out.println("Graph contains a cycle: True");
        else
            System.out.println("Graph does not contain a cycle: False");
    }
}
