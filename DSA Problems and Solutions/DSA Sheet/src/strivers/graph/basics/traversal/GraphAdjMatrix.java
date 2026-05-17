package com.questions.strivers.graph.basics.traversal;

import java.util.*;

/**
 * =====================================================================================
 *  Graph Traversal using Adjacency Matrix (DFS & BFS)
 * =====================================================================================
 *
 * ----------------------------- LEETCODE-STYLE PROBLEM ---------------------------------
 *
 * Given a graph represented using an adjacency matrix, perform:
 *
 * 1. Depth First Search (DFS) starting from node 0
 * 2. Breadth First Search (BFS) starting from node 0
 *
 * The adjacency matrix graph[][] is defined as:
 * - graph[i][j] = 1 → There is an edge between vertex i and vertex j
 * - graph[i][j] = 0 → No edge exists
 *
 * The graph may contain cycles and is undirected in this example.
 *
 * ----------------------------- INPUT --------------------------------------------------
 * graph[][] : 2D integer matrix representing the graph
 *
 * ----------------------------- OUTPUT -------------------------------------------------
 * Print the order of nodes visited during DFS and BFS traversal
 *
 * ----------------------------- CONSTRAINTS --------------------------------------------
 * 1 ≤ V ≤ 10^3
 * graph is a square matrix (V x V)
 *
 * =====================================================================================
 *  WHY ADJACENCY MATRIX?
 * =====================================================================================
 * - Useful for dense graphs
 * - Constant time edge lookup O(1)
 * - Easier to visualize graph connections
 *
 * =====================================================================================
 */

public class GraphAdjMatrix {

    // =============================================================================
    // DEPTH FIRST SEARCH (DFS)
    // =============================================================================
    /**
     * DFS recursively explores as deep as possible along each path.
     *
     * @param node  Current node being visited
     * @param graph Adjacency matrix representation of the graph
     * @param vis   Boolean array to track visited nodes
     */
    static void dfs(int node, int[][] graph, boolean[] vis) {

        // Mark the current node as visited
        // This prevents infinite recursion in cyclic graphs
        vis[node] = true;

        // Print the current node as part of DFS traversal
        System.out.print(node + " ");

        // Traverse all vertices to find adjacent nodes
        for (int v = 0; v < graph.length; v++) {

            // Check:
            // 1. graph[node][v] == 1 → edge exists
            // 2. !vis[v] → vertex not visited yet
            if (graph[node][v] == 1 && !vis[v]) {

                // Recursively visit the adjacent unvisited node
                dfs(v, graph, vis);
            }
        }
    }

    // =============================================================================
    // BREADTH FIRST SEARCH (BFS)
    // =============================================================================
    /**
     * BFS explores nodes level by level using a queue.
     *
     * @param start Starting node for BFS
     * @param graph Adjacency matrix representation of the graph
     */
    static void bfs(int start, int[][] graph) {

        // Visited array to track visited nodes
        boolean[] vis = new boolean[graph.length];

        // Queue for BFS traversal (FIFO order)
        Queue<Integer> q = new LinkedList<>();

        // Start BFS from the given start node
        q.add(start);
        vis[start] = true;

        // Continue until queue becomes empty
        while (!q.isEmpty()) {

            // Dequeue the front element
            int node = q.poll();

            // Print current node
            System.out.print(node + " ");

            // Check all possible adjacent vertices
            for (int v = 0; v < graph.length; v++) {

                // If there is an edge and the node is unvisited
                if (graph[node][v] == 1 && !vis[v]) {

                    // Mark as visited before enqueuing
                    vis[v] = true;

                    // Add to queue for future processing
                    q.add(v);
                }
            }
        }
    }

    // =============================================================================
    // MAIN METHOD (TESTING)
    // =============================================================================
    public static void main(String[] args) {

        /*
         * Graph Representation (Adjacency Matrix)
         *
         *      0 --- 1
         *      |     |
         *      |     |
         *      2 --- 3
         */
        int[][] graph = {
                {0, 1, 1, 0},
                {1, 0, 0, 1},
                {1, 0, 0, 1},
                {0, 1, 1, 0}
        };

        System.out.println("DFS Traversal:");
        dfs(0, graph, new boolean[graph.length]);

        System.out.println("\nBFS Traversal:");
        bfs(0, graph);
    }
}

/**
 * =====================================================================================
 *  APPROACH EXPLANATION (INTERVIEW FRIENDLY)
 * =====================================================================================
 *
 * 1️⃣ Depth First Search (DFS)
 * --------------------------------
 * - Start from the source node
 * - Visit one adjacent node and keep going deeper
 * - Backtrack when no unvisited neighbors are left
 *
 * WHY IT WORKS:
 * - Uses recursion and visited array to ensure every node is visited once
 *
 * WHEN TO USE:
 * - Cycle detection
 * - Connected components
 * - Path existence problems
 *
 * DRAWBACKS:
 * - Recursive DFS can cause stack overflow for very deep graphs
 *
 * -------------------------------------------------------------------------------------
 *
 * 2️⃣ Breadth First Search (BFS)
 * --------------------------------
 * - Start from the source node
 * - Visit all neighbors first (level by level)
 *
 * WHY IT WORKS:
 * - Queue ensures FIFO order, processing nodes by distance
 *
 * WHEN TO USE:
 * - Shortest path in unweighted graphs
 * - Level-order traversal
 *
 * DRAWBACKS:
 * - Uses extra memory for queue
 *
 * =====================================================================================
 *  TIME & SPACE COMPLEXITY
 * =====================================================================================
 *
 * Let V = Number of vertices
 *
 * DFS:
 * ----
 * Time Complexity: O(V²)
 * - For every node, we scan all V columns in adjacency matrix
 *
 * Space Complexity: O(V)
 * - Visited array + recursion stack
 *
 * BFS:
 * ----
 * Time Complexity: O(V²)
 * Space Complexity: O(V)
 * - Visited array + queue
 *
 * =====================================================================================
 *  ALTERNATIVE RECOMMENDED APPROACHES
 * =====================================================================================
 *
 * 1️⃣ Adjacency List Representation (Preferred)
 * - Time Complexity: O(V + E)
 * - Space efficient for sparse graphs
 *
 * 2️⃣ Iterative DFS using Stack
 * - Avoids recursion stack overflow
 *
 * =====================================================================================
 *  KEY INTERVIEW TAKEAWAY
 * =====================================================================================
 *
 * - Use adjacency matrix for dense graphs
 * - Use adjacency list for sparse graphs (most interview problems)
 * - Always mark nodes visited before pushing into queue/stack
 *
 * =====================================================================================
 */
