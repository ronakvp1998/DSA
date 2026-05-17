package com.questions.strivers.graph.basics.traversal;

import java.util.*;

/**
 * =====================================================================================
 *  Graph Traversal using Adjacency List (DFS & BFS)
 * =====================================================================================
 *
 * ----------------------------- LEETCODE-STYLE PROBLEM ---------------------------------
 *
 * Given an undirected graph with V vertices numbered from 0 to V-1,
 * represented using an adjacency list.
 *
 * Your task is to perform:
 * 1. Depth First Search (DFS) traversal starting from node 0
 * 2. Breadth First Search (BFS) traversal starting from node 0
 *
 * Return the order of nodes visited during the traversal.
 *
 * ----------------------------- INPUT --------------------------------------------------
 * V = Number of vertices
 * adj = Adjacency list where adj.get(i) contains all vertices adjacent to i
 *
 * ----------------------------- OUTPUT -------------------------------------------------
 * DFS Traversal order
 * BFS Traversal order
 *
 * ----------------------------- ASSUMPTIONS --------------------------------------------
 * - The graph can be disconnected (but traversal starts from node 0)
 * - Graph may contain cycles
 * - Graph is undirected in this example
 *
 * =====================================================================================
 *  WHY GRAPH TRAVERSAL?
 * =====================================================================================
 * Graph traversal algorithms are fundamental and are used in:
 * - Connected components
 * - Cycle detection
 * - Topological sorting
 * - Shortest path algorithms
 * - Tree & graph-based interview problems
 *
 * =====================================================================================
 */

public class GraphAdjList {

    // =============================================================================
    // DEPTH FIRST SEARCH (DFS)
    // =============================================================================
    /**
     * DFS uses recursion to explore as deep as possible before backtracking.
     *
     * @param node Current node being visited
     * @param adj  Adjacency list representation of the graph
     * @param vis  Boolean array to track visited nodes
     * @param res  List to store DFS traversal order
     */
    static void dfs(int node,
                    ArrayList<ArrayList<Integer>> adj,
                    boolean[] vis,
                    ArrayList<Integer> res) {

        // Mark the current node as visited to avoid revisiting (cycle prevention)
        vis[node] = true;

        // Add current node to the DFS result list
        res.add(node);

        // Traverse all adjacent (neighbor) nodes
        for (int neighbor : adj.get(node)) {

            // If neighbor is not visited, recursively call DFS
            if (!vis[neighbor]) {
                dfs(neighbor, adj, vis, res);
            }
        }
    }

    // =============================================================================
    // BREADTH FIRST SEARCH (BFS)
    // =============================================================================
    /**
     * BFS explores nodes level by level using a queue.
     *
     * @param V   Number of vertices
     * @param adj Adjacency list representation of the graph
     * @return BFS traversal order
     */
    static ArrayList<Integer> bfs(int V, ArrayList<ArrayList<Integer>> adj) {

        // Stores BFS traversal result
        ArrayList<Integer> res = new ArrayList<>();

        // Visited array to avoid revisiting nodes
        boolean[] vis = new boolean[V];

        // Queue is required for BFS (FIFO)
        Queue<Integer> q = new LinkedList<>();

        // Start BFS from node 0 (as per problem statement)
        q.add(0);
        vis[0] = true;

        // Continue traversal until queue becomes empty
        while (!q.isEmpty()) {

            // Remove front element from queue
            int node = q.poll();

            // Add current node to BFS result
            res.add(node);

            // Traverse all adjacent nodes
            for (int neighbor : adj.get(node)) {

                // If neighbor is not visited, mark and enqueue it
                if (!vis[neighbor]) {
                    vis[neighbor] = true;
                    q.add(neighbor);
                }
            }
        }
        return res;
    }

    // =============================================================================
    // MAIN METHOD (TESTING)
    // =============================================================================
    public static void main(String[] args) {

        // Number of vertices in graph
        int V = 5;

        // Initialize adjacency list
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();

        // Create empty adjacency list for each vertex
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        // ------------------ GRAPH CONSTRUCTION (UNDIRECTED) ------------------
        // 0 -- 1
        adj.get(0).add(1);
        adj.get(1).add(0);

        // 0 -- 2
        adj.get(0).add(2);
        adj.get(2).add(0);

        // 1 -- 3
        adj.get(1).add(3);
        adj.get(3).add(1);

        // 2 -- 4
        adj.get(2).add(4);
        adj.get(4).add(2);

        // ------------------ DFS EXECUTION ------------------
        boolean[] vis = new boolean[V];
        ArrayList<Integer> dfsResult = new ArrayList<>();

        dfs(0, adj, vis, dfsResult);
        System.out.println("DFS Traversal: " + dfsResult);

        // ------------------ BFS EXECUTION ------------------
        System.out.println("BFS Traversal: " + bfs(V, adj));
    }
}

/**
 * =====================================================================================
 *  APPROACH EXPLANATION (INTERVIEW FRIENDLY)
 * =====================================================================================
 *
 * 1️⃣ DFS (Depth First Search)
 * --------------------------------
 * - Start from a node
 * - Go as deep as possible along one path
 * - Backtrack when no unvisited neighbors exist
 *
 * WHY IT WORKS:
 * - Recursion + visited array ensures each node is processed once
 * - Prevents infinite loops in cyclic graphs
 *
 * WHEN TO USE:
 * - Path exploration
 * - Cycle detection
 * - Connected components
 *
 * LIMITATIONS:
 * - Recursive DFS may cause StackOverflowError for very deep graphs
 *
 * -------------------------------------------------------------------------------------
 *
 * 2️⃣ BFS (Breadth First Search)
 * --------------------------------
 * - Start from a node
 * - Visit all neighbors first (level by level)
 *
 * WHY IT WORKS:
 * - Queue maintains order of traversal
 * - Ensures shortest path in unweighted graphs
 *
 * WHEN TO USE:
 * - Shortest path problems
 * - Level order traversal
 *
 * LIMITATIONS:
 * - Requires extra memory for queue
 *
 * =====================================================================================
 *  TIME & SPACE COMPLEXITY
 * =====================================================================================
 *
 * Let:
 * V = Number of vertices
 * E = Number of edges
 *
 * DFS:
 * ----
 * Time Complexity: O(V + E)
 * - Each vertex is visited once
 * - Each edge is traversed once
 *
 * Space Complexity: O(V)
 * - Visited array
 * - Recursion stack (worst case)
 *
 * BFS:
 * ----
 * Time Complexity: O(V + E)
 * Space Complexity: O(V)
 * - Queue + visited array
 *
 * =====================================================================================
 *  ALTERNATIVE APPROACHES
 * =====================================================================================
 *
 * 1️⃣ Iterative DFS using Stack
 * - Avoids recursion stack overflow
 * - Uses explicit Stack data structure
 *
 * 2️⃣ Adjacency Matrix Representation
 * - Easier to implement
 * - Space Complexity: O(V²) ❌ (inefficient for sparse graphs)
 *
 * =====================================================================================
 */
