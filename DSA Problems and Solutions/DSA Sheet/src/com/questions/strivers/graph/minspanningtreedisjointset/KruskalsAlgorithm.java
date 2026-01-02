package com.questions.strivers.graph.minspanningtreedisjointset;

import java.util.*;

/**
 * ================================================================================================
 *  Kruskal's Algorithm - Minimum Spanning Tree (MST)
 * ================================================================================================
 *
 * ------------------------------ PROBLEM STATEMENT -----------------------------------------------
 *
 * Given an undirected, weighted, connected graph with V vertices,
 * find the sum of weights of edges in its Minimum Spanning Tree (MST).
 *
 * Kruskal's Algorithm:
 * - Sort edges by weight
 * - Add edges one by one
 * - Avoid cycles using Disjoint Set (Union-Find)
 *
 * ================================================================================================
 */

/**
 * Disjoint Set (Union-Find) with:
 * - Path Compression
 * - Union by Rank
 * - Union by Size
 */
class DisjointSet {

    List<Integer> rank, parent, size;

    // Constructor
    public DisjointSet(int n) {
        rank = new ArrayList<>();
        parent = new ArrayList<>();
        size = new ArrayList<>();

        // Initialize DSU
        for (int i = 0; i <= n; i++) {
            rank.add(0);
            parent.add(i);
            size.add(1);
        }
    }

    // Find with path compression
    public int findUPar(int node) {
        if (node == parent.get(node))
            return node;
        parent.set(node, findUPar(parent.get(node)));
        return parent.get(node);
    }

    // Union by Rank
    public void unionByRank(int u, int v) {
        int ulp_u = findUPar(u);
        int ulp_v = findUPar(v);
        if (ulp_u == ulp_v) return;

        if (rank.get(ulp_u) < rank.get(ulp_v)) {
            parent.set(ulp_u, ulp_v);
        } else if (rank.get(ulp_v) < rank.get(ulp_u)) {
            parent.set(ulp_v, ulp_u);
        } else {
            parent.set(ulp_v, ulp_u);
            rank.set(ulp_u, rank.get(ulp_u) + 1);
        }
    }

    // Union by Size
    public void unionBySize(int u, int v) {
        int ulp_u = findUPar(u);
        int ulp_v = findUPar(v);
        if (ulp_u == ulp_v) return;

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
     * Function to calculate MST total weight using Kruskal's Algorithm
     */
    public static int spanningTree(int V, List<List<List<Integer>>> adj) {

        // Store all edges as {weight, u, v}
        List<int[]> edges = new ArrayList<>();

        for (int u = 0; u < V; u++) {
            for (List<Integer> it : adj.get(u)) {
                int v = it.get(0);
                int wt = it.get(1);
                edges.add(new int[]{wt, u, v});
            }
        }

        // Sort edges by weight
        edges.sort(Comparator.comparingInt(a -> a[0]));

        DisjointSet ds = new DisjointSet(V);
        int sum = 0;

        // Process edges
        for (int[] e : edges) {
            int wt = e[0];
            int u = e[1];
            int v = e[2];

            // Add edge if it doesn't form a cycle
            if (ds.findUPar(u) != ds.findUPar(v)) {
                sum += wt;
                ds.unionBySize(u, v);
            }
        }

        return sum;
    }

    public static void main(String[] args) {
        int V = 4;

        List<int[]> edges = Arrays.asList(
                new int[]{0, 1, 1},
                new int[]{1, 2, 2},
                new int[]{2, 3, 3},
                new int[]{0, 3, 4}
        );

        // Build adjacency list
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
