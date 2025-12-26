package com.questions.strivers.graph.problemsbfsdfs;

import java.util.ArrayList;
import java.util.List;

/**
 * ===================== NUMBER OF PROVINCES (CONNECTED COMPONENTS IN GRAPH) =====================
 *
 * PROBLEM STATEMENT:
 * You are given an adjacency matrix 'isConnected' of size N x N, where:
 *
 *  isConnected[i][j] = 1  ---> City i and City j are directly connected
 *  isConnected[i][j] = 0  ---> No direct connection
 *
 * A "Province" means a group of cities that are directly or indirectly connected.
 * We must return the total number of provinces.
 *
 * Example:
 *   1 1 0
 *   1 1 0     --> Provinces = 2 ( {0,1} and {2} )
 *   0 0 1
 *
 *   1 0 0
 *   0 1 0     --> Provinces = 3 (each node is isolated)
 *   0 0 1
 *
 * =================================================================================================
 * APPROACH USED:  (Adjacency Matrix → Adjacency List) + DFS
 * -------------------------------------------------------------------------------------------------
 * 1️⃣ Convert adjacency matrix to adjacency list for easier traversal.
 *    - Matrix traversal each time costs O(N^2)
 *    - Adjacency list is friendlier for graph problems
 *
 * 2️⃣ Maintain a visited[] array
 *    - If a node is not visited, it means a NEW PROVINCE starts there
 *
 * 3️⃣ Perform DFS from every unvisited node
 *    - Mark all reachable cities in that DFS as part of SAME PROVINCE
 *
 * 4️⃣ Count how many times DFS starts → This equals number of provinces
 *
 * -------------------------------------------------------------------------------------------------
 * WHY THIS WORKS?
 * Graph theory:
 * A "province" is simply a "Connected Component" in an undirected graph.
 * DFS marks all nodes reachable from a starting node → meaning they belong to same component.
 *
 * -------------------------------------------------------------------------------------------------
 * WHEN TO USE THIS?
 * ✔ When graph is dense or adjacency matrix is given
 * ✔ When we want to count connected components
 * ✔ When recursive DFS usage is acceptable
 *
 * -------------------------------------------------------------------------------------------------
 * LIMITATIONS:
 * ❌ Uses recursion → may cause stack overflow if N is extremely large
 * ❌ Still O(N^2) because input matrix is N^2
 *
 * =================================================================================================
 * TIME COMPLEXITY:
 * Building Adjacency List  :  O(N^2)
 * DFS Traversal            :  O(N + E)  ≈ O(N^2)  (because graph may be dense)
 * TOTAL                    :  O(N^2)
 *
 * SPACE COMPLEXITY:
 * Adjacency List           :  O(N^2) worst case (fully connected graph)
 * Visited Array            :  O(N)
 * Recursion Stack          :  O(N) worst case
 *
 * =================================================================================================
 */
public class NumberOfProvincesAdjList {

    /**
     * ======================
     * STEP 1: Build adjacency list from adjacency matrix
     * ======================
     *
     * Why convert?
     * - Adjacency matrix: Harder to loop neighbors efficiently
     * - Adjacency list  : Direct neighbor access
     */
    private static List<List<Integer>> buildAdjList(int[][] isConnected) {

        int n = isConnected.length;

        // Create an empty adjacency list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        // Fill adjacency list
        // NOTE: Graph is UNDIRECTED
        // So if matrix[i][j] == 1 --> i connected to j
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                // Ignore self loops (i != j)
                if (isConnected[i][j] == 1 && i != j) {
                    adj.get(i).add(j);
                }
            }
        }

        return adj;
    }

    /**
     * ======================
     * STEP 2: Perform DFS
     * ======================
     * Marks all nodes reachable from "node" as visited
     */
    private static void dfs(int node, List<List<Integer>> adj, boolean[] visited) {

        visited[node] = true; // mark current node visited

        // Explore all connected neighbors
        for (int neighbour : adj.get(node)) {
            if (!visited[neighbour]) {
                dfs(neighbour, adj, visited);
            }
        }
    }

    /**
     * ======================
     * STEP 3: Count Provinces
     * ======================
     */
    public static int findCircleNum(int[][] isConnected) {

        int n = isConnected.length;

        // Convert matrix to adjacency list
        List<List<Integer>> adj = buildAdjList(isConnected);

        boolean[] visited = new boolean[n];
        int provinces = 0;

        // Visit every city
        for (int i = 0; i < n; i++) {

            // If city not visited → new province
            if (!visited[i]) {
                provinces++;
                dfs(i, adj, visited);
            }
        }
        return provinces;
    }

    /**
     * ======================
     * MAIN METHOD FOR TESTING
     * ======================
     */
    public static void main(String[] args) {

        int[][] ex1 = {
                {1,1,0},
                {1,1,0},
                {0,0,1}
        };

        int[][] ex2 = {
                {1,0,0},
                {0,1,0},
                {0,0,1}
        };

        System.out.println("Example 1 Provinces (AdjList): " + findCircleNum(ex1)); // Expected: 2
        System.out.println("Example 2 Provinces (AdjList): " + findCircleNum(ex2)); // Expected: 3
    }
}
