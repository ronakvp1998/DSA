package com.questions.strivers.graph.minspanningtreedisjointset;

import java.util.*;

/**
 * ================================================================================================
 *  KRUSKAL'S ALGORITHM – MINIMUM SPANNING TREE (MST)
 * ================================================================================================
 *
 * -------------------------------- PROBLEM STATEMENT ---------------------------------------------
 * Given an undirected, weighted, connected graph with V vertices and E edges,
 * find the sum of the weights of the edges in its Minimum Spanning Tree (MST).
 *
 * A Minimum Spanning Tree (MST):
 * - Connects all vertices
 * - Contains exactly (V - 1) edges
 * - Has the minimum possible total edge weight
 *
 * This problem is commonly asked on:
 * - LeetCode
 * - GeeksForGeeks
 * - CodeStudio
 *
 * -----------------------------------------------------------------------------------------------
 * INPUT:
 * - V   → Number of vertices
 * - adj → Adjacency list where adj.get(u) contains [v, weight]
 *
 * OUTPUT:
 * - Integer representing total weight of MST
 *
 * -----------------------------------------------------------------------------------------------
 * APPROACH (KRUSKAL'S ALGORITHM – GREEDY):
 *
 * 1. Extract all edges from the graph.
 * 2. Sort edges in ascending order of weight.
 * 3. Use Disjoint Set (Union-Find) to detect cycles.
 * 4. Add an edge only if it connects two different components.
 * 5. Continue until MST contains (V - 1) edges.
 *
 * -----------------------------------------------------------------------------------------------
 * WHY THIS WORKS:
 * - Greedy strategy ensures smallest edges are picked first.
 * - Disjoint Set prevents cycles efficiently.
 * - Guarantees minimum total cost.
 *
 * -----------------------------------------------------------------------------------------------
 * WHEN TO USE:
 * - Sparse graphs
 * - Edge list representation is convenient
 *
 * -----------------------------------------------------------------------------------------------
 * LIMITATIONS:
 * - Requires sorting all edges
 * - Slightly slower for dense graphs compared to Prim’s Algorithm
 *
 * -----------------------------------------------------------------------------------------------
 * TIME COMPLEXITY:
 * - O(E log E)
 *   Sorting edges dominates.
 *
 * SPACE COMPLEXITY:
 * - O(V)
 *   Used by Disjoint Set (parent, rank, size arrays).
 *
 * ================================================================================================
 */

/**
 * Disjoint Set (Union-Find) Data Structure
 *
 * Optimizations used:
 * - Path Compression
 * - Union by Rank
 * - Union by Size
 */
class DisjointSet {

    // rank → helps keep tree shallow
    // parent → stores ultimate parent of each node
    // size → stores size of each connected component
    List<Integer> rank, parent, size;

    /**
     * Constructor to initialize Disjoint Set
     *
     * @param n number of vertices
     */
    public DisjointSet(int n) {

        rank = new ArrayList<>();
        parent = new ArrayList<>();
        size = new ArrayList<>();

        /*
         * Initial setup:
         * - Each node is its own parent
         * - Rank = 0
         * - Size = 1
         */
        for (int i = 0; i <= n; i++) {
            rank.add(0);
            parent.add(i);
            size.add(1);
        }
    }

    /**
     * Find Ultimate Parent with Path Compression
     *
     * @param node current node
     * @return ultimate parent of the node
     */
    public int findUPar(int node) {

        // Base case: node is its own parent
        if (node == parent.get(node))
            return node;

        /*
         * Path Compression:
         * Directly connect node to its ultimate parent
         */
        parent.set(node, findUPar(parent.get(node)));
        return parent.get(node);
    }

    /**
     * Union by Rank
     */
    public void unionByRank(int u, int v) {

        int ulp_u = findUPar(u);
        int ulp_v = findUPar(v);

        // If already in same component, do nothing
        if (ulp_u == ulp_v) return;

        // Attach smaller rank tree under larger rank tree
        if (rank.get(ulp_u) < rank.get(ulp_v)) {
            parent.set(ulp_u, ulp_v);
        } else if (rank.get(ulp_v) < rank.get(ulp_u)) {
            parent.set(ulp_v, ulp_u);
        } else {
            parent.set(ulp_v, ulp_u);
            rank.set(ulp_u, rank.get(ulp_u) + 1);
        }
    }

    /**
     * Union by Size (used in this solution)
     */
    public void unionBySize(int u, int v) {

        int ulp_u = findUPar(u);
        int ulp_v = findUPar(v);

        // If both nodes already belong to same component
        if (ulp_u == ulp_v) return;

        // Attach smaller size tree under larger size tree
        if (size.get(ulp_u) < size.get(ulp_v)) {
            parent.set(ulp_u, ulp_v);
            size.set(ulp_v, size.get(ulp_v) + size.get(ulp_u));
        } else {
            parent.set(ulp_v, ulp_u);
            size.set(ulp_u, size.get(ulp_u) + size.get(ulp_v));
        }
    }
}

public class KruskalsAlgorithm {

    /**
     * Computes the sum of weights of MST using Kruskal's Algorithm
     *
     * @param V   number of vertices
     * @param adj adjacency list representation
     * @return total MST weight
     */
    public static int spanningTree(int V, List<List<List<Integer>>> adj) {

        /*
         * Step 1: Extract all edges
         * Each edge stored as {weight, u, v}
         */
        List<int[]> edges = new ArrayList<>();

        for (int u = 0; u < V; u++) {
            for (List<Integer> it : adj.get(u)) {
                int v = it.get(0);
                int wt = it.get(1);
                edges.add(new int[]{wt, u, v});
            }
        }

        /*
         * Step 2: Sort edges by weight
         */
        edges.sort(Comparator.comparingInt(a -> a[0]));

        // Initialize Disjoint Set
        DisjointSet ds = new DisjointSet(V);

        int sum = 0;

        /*
         * Step 3: Process edges in increasing weight order
         */
        for (int[] e : edges) {

            int wt = e[0];
            int u = e[1];
            int v = e[2];

            /*
             * Add edge only if it does not form a cycle
             */
            if (ds.findUPar(u) != ds.findUPar(v)) {
                sum += wt;
                ds.unionBySize(u, v);
            }
        }

        return sum;
    }

    // -------------------------------- MAIN METHOD (TESTING) --------------------------------
    public static void main(String[] args) {

        int V = 4;

        /*
         * Edge list: {u, v, weight}
         */
        List<int[]> edges = Arrays.asList(
                new int[]{0, 1, 1},
                new int[]{1, 2, 2},
                new int[]{2, 3, 3},
                new int[]{0, 3, 4}
        );

        /*
         * Build adjacency list
         */
        List<List<List<Integer>>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());

        for (int[] e : edges) {
            adj.get(e[0]).add(Arrays.asList(e[1], e[2]));
            adj.get(e[1]).add(Arrays.asList(e[0], e[2]));
        }

        int ans = spanningTree(V, adj);
        System.out.println("The sum of weights of edges in MST is: " + ans);
    }
}
