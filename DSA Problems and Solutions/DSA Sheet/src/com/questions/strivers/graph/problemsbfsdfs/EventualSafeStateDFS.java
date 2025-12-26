package com.questions.strivers.graph.problemsbfsdfs;

import java.util.*;

/**
 * ===============================================
 *       Eventual Safe States - DFS Implementation
 * ===============================================
 *
 * Problem Statement:
 * Given a directed graph with V vertices (0-based index) in adjacency list form,
 * find all the "safe nodes" of the graph.
 *
 * Definitions:
 *  - Terminal Node: Node with no outgoing edges
 *  - Safe Node: Node from which every possible path leads to a terminal node
 *
 * Task:
 *  Return a sorted list of all safe nodes.
 *
 * Approach - DFS:
 * 1️⃣ For each node, use DFS to check if it leads to a cycle:
 *      - visited[]  → to track nodes visited globally
 *      - pathVis[]  → to track nodes in the current DFS path (for cycle detection)
 *
 * 2️⃣ If DFS returns true (cycle found) → node is unsafe
 *    Else → node is safe
 *
 * 3️⃣ Collect all safe nodes and sort before returning.
 *
 * Why it works:
 *  - Nodes in cycles are unsafe because they can reach themselves infinitely
 *  - Nodes leading only to terminal nodes (no outgoing edges or paths ending in terminal nodes) are safe
 *
 * Complexity:
 *  - Time: O(V + E)
 *    Each node and edge is visited at most once
 *  - Space: O(V + recursion stack)
 *    For visited arrays and DFS recursion
 */

public class EventualSafeStateDFS {

    /**
     * Helper DFS function to detect cycles
     * @param node Current node being processed
     * @param adj  Original adjacency list
     * @param vis  Visited array for global visit tracking
     * @param pathVis Array for cycle detection along current path
     * @param safeNodes Array to mark safe nodes
     * @return true if a cycle is detected from this node
     */
    private static boolean dfs(int node, List<Integer>[] adj, boolean[] vis, boolean[] pathVis, boolean[] safeNodes) {
        vis[node] = true;       // Mark node globally visited
        pathVis[node] = true;   // Mark node in current DFS path

        for (int neighbor : adj[node]) {
            if (!vis[neighbor]) {
                // Recurse if neighbor not visited
                if (dfs(neighbor, adj, vis, pathVis, safeNodes)) {
                    return true; // Cycle detected in recursion
                }
            } else if (pathVis[neighbor]) {
                return true; // Cycle detected via pathVis
            }
        }

        // Node is safe if no cycle detected in DFS
        safeNodes[node] = true;

        pathVis[node] = false; // Remove node from current path
        return false;
    }

    /**
     * Function to find all eventual safe nodes using DFS
     * @param V Number of vertices
     * @param adj Adjacency list of the graph
     * @return Sorted list of safe nodes
     */
    public static List<Integer> eventualSafeNodes(int V, List<Integer>[] adj) {
        boolean[] vis = new boolean[V];        // Tracks visited nodes
        boolean[] pathVis = new boolean[V];    // Tracks nodes in current DFS path
        boolean[] safeNodes = new boolean[V];  // Marks nodes that are safe

        // Run DFS for each node
        for (int i = 0; i < V; i++) {
            if (!vis[i]) {
                dfs(i, adj, vis, pathVis, safeNodes);
            }
        }

        // Collect all safe nodes
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if (safeNodes[i]) result.add(i);
        }

        // Sort for ascending order as required
        Collections.sort(result);
        return result;
    }

    /**
     * Driver code to test DFS implementation
     */
    public static void main(String[] args) {

        int V = 12;  // Number of nodes

        // Initialize adjacency list
        List<Integer>[] adj = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }

        // Add edges
        adj[0].add(1);
        adj[1].add(2);
        adj[2].add(3);
        adj[2].add(4);
        adj[3].add(4);
        adj[4].add(5);
        adj[5].add(6);
        adj[6].add(7);
        adj[8].add(1);
        adj[8].add(9);
        adj[9].add(10);
        adj[10].add(8); // Cycle
        adj[11].add(9);

        // Compute safe nodes
        List<Integer> safeNodes = eventualSafeNodes(V, adj);

        System.out.println("Safe Nodes in the graph: " + safeNodes);
    }
}
