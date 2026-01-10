package com.questions.strivers.graph.otheralgo;

import java.util.*;

/*
================================================================================
PROBLEM STATEMENT
================================================================================
Critical Connections (Bridges) in a Network

Given a connected undirected graph with n nodes labeled from 0 to n-1 and a list
of connections where each connection [u, v] represents an undirected edge,
find all the **critical connections** (bridges) in the network.

A bridge is an edge that, if removed, will increase the number of connected
components in the graph (i.e., it disconnects part of the network).

--------------------------------------------------------------------------------
EXAMPLE:
Input:
n = 4
connections = [[0,1],[1,2],[2,0],[1,3]]

Output:
[[1,3]]

Explanation:
Removing edge [1,3] disconnects the graph, so it's a bridge.
Other edges are part of cycles and are not bridges.

================================================================================
APPROACH: TARJAN'S ALGORITHM FOR BRIDGES
================================================================================
Tarjan's algorithm uses DFS and timestamps to find bridges efficiently.

1. **DFS Traversal**:
   - Each node is assigned:
     - **tin[node]**: discovery time
     - **low[node]**: lowest discovery time reachable from the node (including back edges)

2. **Bridge Condition**:
   - For an edge u-v (u -> v in DFS tree):
     - If low[v] > tin[u], then edge (u,v) is a bridge.
   - Intuition: If v cannot reach u or any ancestor of u via back edges, removing (u,v) disconnects the graph.

3. **Back Edges**:
   - If a neighbor is already visited and is not the parent, update low[node] using low-link values.

--------------------------------------------------------------------------------
TIME & SPACE COMPLEXITY
================================================================================
Time Complexity: O(V + E)
- DFS traverses every node and edge once.

Space Complexity: O(V + E)
- Adjacency list: O(V + E)
- Arrays vis, tin, low: O(V)
- Bridges list: O(E) in worst case
- Call stack: O(V) for recursion
*/

public class BridgesInGraphTarjansAlgorithm {

    private int timer = 1; // Global timer for discovery times

    /**
     * DFS helper function to find bridges in the graph
     *
     * @param node     Current node in DFS
     * @param parent   Parent node in DFS tree
     * @param vis      Visited array
     * @param adj      Adjacency list of the graph
     * @param tin      Discovery times
     * @param low      Low-link values
     * @param bridges  List to store found bridges
     */
    private void dfs(int node, int parent, boolean[] vis,
                     List<List<Integer>> adj, int[] tin, int[] low,
                     List<List<Integer>> bridges) {

        vis[node] = true;                  // Mark node as visited
        tin[node] = low[node] = timer++;   // Set discovery and low-link time

        // Explore all neighbors
        for (int neighbor : adj.get(node)) {

            // Skip the edge to parent node
            if (neighbor == parent) continue;

            if (!vis[neighbor]) {
                // Recurse on unvisited neighbor
                dfs(neighbor, node, vis, adj, tin, low, bridges);

                // Update current node's low-link value
                low[node] = Math.min(low[node], low[neighbor]);

                // Check bridge condition
                if (low[neighbor] > tin[node]) {
                    bridges.add(Arrays.asList(node, neighbor));
                }

            } else {
                // Neighbor already visited => back edge
                // Update low-link value using neighbor's discovery time
                low[node] = Math.min(low[node], tin[neighbor]);
            }
        }
    }

    /**
     * Main function to find all bridges in the graph
     *
     * @param n           Number of nodes
     * @param connections Edge list representing undirected graph
     * @return List of bridges
     */
    public List<List<Integer>> criticalConnections(int n, int[][] connections) {

        // Step 1: Build adjacency list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (int[] conn : connections) {
            int u = conn[0], v = conn[1];
            adj.get(u).add(v);
            adj.get(v).add(u); // Undirected edge
        }

        // Step 2: Initialize helper arrays
        boolean[] vis = new boolean[n]; // Track visited nodes
        int[] tin = new int[n];         // Discovery times
        int[] low = new int[n];         // Low-link values
        List<List<Integer>> bridges = new ArrayList<>();

        // Step 3: Run DFS starting from node 0
        // Note: For disconnected graphs, iterate over all nodes
        dfs(0, -1, vis, adj, tin, low, bridges);

        return bridges;
    }

    // --------------------- MAIN METHOD FOR TESTING ---------------------
    public static void main(String[] args) {
        int n = 4;
        int[][] connections = {{0, 1}, {1, 2}, {2, 0}, {1, 3}};

        BridgesInGraphTarjansAlgorithm obj = new BridgesInGraphTarjansAlgorithm();
        List<List<Integer>> bridges = obj.criticalConnections(n, connections);

        System.out.println("Critical Connections (Bridges): " + bridges);
    }
}
