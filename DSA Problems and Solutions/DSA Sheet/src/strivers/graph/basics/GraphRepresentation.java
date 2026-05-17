package com.questions.strivers.graph.basics;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * =====================================================================
 *                      GRAPH REPRESENTATION IN JAVA
 * =====================================================================
 *
 * -----------------------------
 * PROBLEM STATEMENT
 * -----------------------------
 * Given number of nodes (N) and edges (M), represent a graph in:
 *
 * 1️⃣ Adjacency Matrix
 * 2️⃣ Adjacency List (Directed Graph)
 * 3️⃣ Adjacency List (Undirected Graph)
 *
 * Your tasks are to:
 * - Take graph input
 * - Store it efficiently
 * - Understand when to use which representation
 *
 * -----------------------------
 * INPUT FORMAT (Common for all)
 * -----------------------------
 * N M
 * u1 v1
 * u2 v2
 * ...
 * uM vM
 *
 * Example:
 * Input:
 * 5 6
 * 1 2
 * 1 3
 * 2 4
 * 3 4
 * 2 5
 * 4 5
 *
 * Meaning:
 * - 5 Nodes
 * - 6 Edges
 *
 * -----------------------------
 * GRAPH ASSUMPTIONS
 * -----------------------------
 * - Nodes are 1-based indexed (1 to N)
 * - No parallel edges assumed (but adjacency list supports it)
 * - No self-loops assumed unless entered
 *
 * -----------------------------
 * INTERVIEW USEFULNESS
 * -----------------------------
 * Graph representation is the **first building block**
 * before solving BFS, DFS, Cycle Detection, Topological Sort, MST etc.
 *
 * If you cannot build the structure correctly,
 * you cannot solve graph problems.
 *
 * =====================================================================
 * APPROACH SUMMARY
 * =====================================================================
 *
 * 1️⃣ ADJACENCY MATRIX (2D Array)
 * - Uses NxN matrix
 * - adj[u][v] = 1 means edge exists
 * - Best for dense graphs
 *
 * 2️⃣ ADJACENCY LIST - DIRECTED
 * - Stores only outgoing connections from each node
 * - adj[u] contains list of all v such that u → v
 *
 * 3️⃣ ADJACENCY LIST - UNDIRECTED
 * - Stores bidirectional edges
 * - adj[u] contains v
 * - adj[v] contains u
 *
 * =====================================================================
 * COMPLEXITY ANALYSIS
 * =====================================================================
 *
 * -----------------------------
 * ADJACENCY MATRIX
 * -----------------------------
 * Space Complexity  : O(N²)
 * - Always stores full matrix even if very few edges
 *
 * Time Complexity to:
 * - Add Edge:  O(1)
 * - Check Edge: O(1)
 * - Traverse Neighbors: O(N)
 *
 * Best When:
 * - Dense Graph (M ≈ N²)
 * - Need constant-time edge lookup
 *
 * Drawback:
 * - Huge memory for large N
 *
 * -----------------------------
 * ADJACENCY LIST
 * -----------------------------
 * Space Complexity: O(N + M)
 * - Only stores existing edges
 *
 * Time Complexity to:
 * - Add Edge: O(1)
 * - Check Edge: O(degree(node))
 * - Traverse Graph: O(N + M)
 *
 * Best When:
 * - Sparse Graph (few edges)
 * - Used in real life problems
 *
 * Drawback:
 * - No O(1) edge existence check
 *
 * =====================================================================
 * ALTERNATIVE REPRESENTATIONS
 * =====================================================================
 * 1️⃣ Edge List
 * - Simply store pairs (u, v)
 * - Best when only iterating edges
 *
 * 2️⃣ Weighted Graph Representation
 * - Store pairs (neighbor, weight)
 *
 * 3️⃣ Using HashMap (for unknown node range)
 *
 * =====================================================================
 */

public class GraphRepresentation {

    /**
     * ---------------------------------------------------------------
     * MAIN METHOD FOR TESTING
     * ---------------------------------------------------------------
     * You can run and test each method by providing input.
     */
    public static void main(String[] args) {
        System.out.println("Choose Representation:");
        System.out.println("1 -> Adjacency Matrix");
        System.out.println("2 -> Adjacency List (Directed)");
        System.out.println("3 -> Adjacency List (Undirected)");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        switch (choice) {
            case 1 -> using2dArray();
            case 2 -> adjacencyListDirected();
            case 3 -> adjacencyListUndirected();
            default -> System.out.println("Invalid Choice");
        }

        sc.close();
    }

    /**
     * =================================================================
     * 1️⃣ GRAPH USING ADJACENCY MATRIX (2D ARRAY)
     * =================================================================
     */
    public static void using2dArray() {

        Scanner sc = new Scanner(System.in);

        // Read number of nodes
        int n = sc.nextInt();

        // Read number of edges
        int m = sc.nextInt();

        // Create adjacency matrix (1-based indexing support)
        int[][] adj = new int[n + 1][n + 1];

        /*
         * Read each edge:
         * u v means an edge between u and v
         */
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();

            adj[u][v] = 1;   // Mark edge u → v

            // For UNDIRECTED graph, also mark reverse
            adj[v][u] = 1;
        }

        // Print matrix for verification
        System.out.println("Adjacency Matrix:");
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                System.out.print(adj[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * =================================================================
     * 2️⃣ GRAPH USING ADJACENCY LIST - DIRECTED
     * =================================================================
     */
    private static void adjacencyListDirected() {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();   // number of nodes
        int m = sc.nextInt();   // number of edges

        // Create adjacency list
        List<List<Integer>> adj = new ArrayList<>();

        // Initialize empty lists
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        /*
         * For each edge u → v
         * Only store ONE direction
         */
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();

            adj.get(u).add(v);
        }

        // Print adjacency list
        System.out.println("Directed Graph Adjacency List:");
        for (int i = 1; i <= n; i++) {
            System.out.print(i + " -> ");
            System.out.println(adj.get(i));
        }
    }

    /**
     * =================================================================
     * 3️⃣ GRAPH USING ADJACENCY LIST - UNDIRECTED
     * =================================================================
     */
    private static void adjacencyListUndirected() {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();   // number of nodes
        int m = sc.nextInt();   // number of edges

        // Create adjacency list
        List<List<Integer>> adj = new ArrayList<>();

        // Initialize empty lists
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        /*
         * For each edge u -- v
         * Store both directions
         */
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();

            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        // Print adjacency list
        System.out.println("Undirected Graph Adjacency List:");
        for (int i = 1; i <= n; i++) {
            System.out.print(i + " -> ");
            System.out.println(adj.get(i));
        }
    }
}
