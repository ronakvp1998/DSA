package com.questions.strivers.graph.minspanningtreedisjointset;

import java.util.*;

/**
 * ================================================================================================
 *  KRUSKAL'S ALGORITHM – MINIMUM SPANNING TREE (MST)
 * ================================================================================================
 *
 * Problem Statement:
 * ------------------
 * Given an undirected, weighted, connected graph with V vertices and E edges,
 * find the sum of the weights of edges in its Minimum Spanning Tree (MST).
 *
 * A Minimum Spanning Tree is a subset of edges that:
 *  1. Connects all vertices.
 *  2. Has no cycles.
 *  3. Minimizes the total edge weight.
 *
 * Example:
 * Input:
 * V = 4, edges = [[0,1,1], [1,2,2], [2,3,3], [0,3,4]]
 * Output: 6
 * Explanation: The MST will include edges (0,1), (1,2), and (2,3) with total weight 1+2+3 = 6.
 *
 * Approach:
 * ---------
 * Kruskal's algorithm is a greedy algorithm that finds the MST by:
 * 1. Sorting all edges by increasing weight.
 * 2. Adding edges one by one to the MST if they don't form a cycle.
 *    Cycle detection is efficiently done using Disjoint Set Union (DSU).
 *
 * Time Complexity:
 * ----------------
 * 1. Sorting edges: O(E log E)
 * 2. DSU operations with path compression and union by size/rank: nearly O(1) per operation
 * => Overall: O(E log E + E α(V)) ≈ O(E log E), where α is the inverse Ackermann function
 *
 * Space Complexity:
 * -----------------
 * 1. Storing edges: O(E)
 * 2. DSU structures (parent, rank/size): O(V)
 * => Overall: O(V + E)
 *
 * Alternative Approaches:
 * -----------------------
 * 1. Prim’s Algorithm:
 *    - Builds MST starting from a node and grows it using a priority queue.
 *    - Better for dense graphs (V^2) with adjacency matrix.
 * 2. Borůvka’s Algorithm:
 *    - Less common, works in parallel easily.
 *
 * ================================================================================================
 */

// ================================================================================================
// DISJOINT SET (Union-Find) CLASS
// ================================================================================================
class DisjointSet {
    List<Integer> rank, parent, size;

    /**
     * Constructor initializes parent, rank, and size lists for DSU
     * Each node initially points to itself.
     * @param n number of nodes in the graph
     */
    public DisjointSet(int n) {
        rank = new ArrayList<>();
        parent = new ArrayList<>();
        size = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            rank.add(0);       // Initial rank 0
            parent.add(i);     // Each node is its own parent
            size.add(1);       // Initial size 1
        }
    }

    /**
     * Find the ultimate parent of a node with path compression
     * @param node the node whose ultimate parent is to be found
     * @return ultimate parent of the node
     */
    public int findUPar(int node) {
        if (node == parent.get(node)) return node;
        parent.set(node, findUPar(parent.get(node))); // Path compression
        return parent.get(node);
    }

    /**
     * Union by rank: attach smaller rank tree under root of higher rank tree
     * @param u node 1
     * @param v node 2
     */
    public void unionByRank(int u, int v) {
        int pu = findUPar(u);
        int pv = findUPar(v);
        if (pu == pv) return; // Already in same set

        if (rank.get(pu) < rank.get(pv)) {
            parent.set(pu, pv);
        } else if (rank.get(pv) < rank.get(pu)) {
            parent.set(pv, pu);
        } else {
            parent.set(pv, pu);
            rank.set(pu, rank.get(pu) + 1); // Increase rank if same
        }
    }

    /**
     * Union by size: attach smaller size tree under larger size tree
     * @param u node 1
     * @param v node 2
     */
    public void unionBySize(int u, int v) {
        int pu = findUPar(u);
        int pv = findUPar(v);
        if (pu == pv) return; // Already in same set

        if (size.get(pu) < size.get(pv)) {
            parent.set(pu, pv);
            size.set(pv, size.get(pv) + size.get(pu));
        } else {
            parent.set(pv, pu);
            size.set(pu, size.get(pu) + size.get(pv));
        }
    }
}

// ================================================================================================
// EDGE CLASS
// ================================================================================================
class Edge {
    int u, v, weight;

    public Edge(int u, int v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }
}

// ================================================================================================
// KRUSKAL'S ALGORITHM IMPLEMENTATION
// ================================================================================================
public class KruskalsAlgorithm {

    /**
     * Computes the sum of weights of MST using Kruskal's Algorithm
     * @param V number of vertices
     * @param adj adjacency list representation of the graph
     * @return total weight of the MST
     */
    public static int spanningTree(int V, List<List<List<Integer>>> adj) {

        // Step 1: Extract edges from adjacency list
        List<Edge> edges = new ArrayList<>();
        for (int u = 0; u < V; u++) {
            for (List<Integer> it : adj.get(u)) {
                int v = it.get(0);
                int wt = it.get(1);
                if (u < v) { // Avoid duplicate edges in undirected graph
                    edges.add(new Edge(u, v, wt));
                }
            }
        }

        // Step 2: Sort edges in ascending order by weight
        edges.sort(Comparator.comparingInt(e -> e.weight));

        // Step 3: Initialize Disjoint Set Union (DSU) for cycle detection
        DisjointSet ds = new DisjointSet(V);

        int sum = 0; // MST total weight

        // Step 4: Iterate over edges and add to MST if no cycle is formed
        for (Edge e : edges) {
            if (ds.findUPar(e.u) != ds.findUPar(e.v)) {
                sum += e.weight;
                ds.unionBySize(e.u, e.v); // Merge sets
            }
        }

        return sum;
    }

    // ----------------------------- MAIN METHOD (TESTING) -----------------------------
    public static void main(String[] args) {

        int V = 4; // Number of vertices

        // Step 1: Define edges
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 1),
                new Edge(1, 2, 2),
                new Edge(2, 3, 3),
                new Edge(0, 3, 4)
        );

        // Step 2: Build adjacency list from edges
        List<List<List<Integer>>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());
        for (Edge e : edges) {
            adj.get(e.u).add(Arrays.asList(e.v, e.weight));
            adj.get(e.v).add(Arrays.asList(e.u, e.weight)); // Undirected graph
        }

        // Step 3: Compute MST sum using Kruskal's algorithm
        int ans = spanningTree(V, adj);
        System.out.println("The sum of weights of edges in MST is: " + ans);
    }
}
