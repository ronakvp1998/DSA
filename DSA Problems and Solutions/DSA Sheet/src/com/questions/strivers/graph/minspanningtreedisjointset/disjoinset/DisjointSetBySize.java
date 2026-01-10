package com.questions.strivers.graph.minspanningtreedisjointset.disjoinset;

import java.util.ArrayList;
import java.util.List;

/**
 * =====================================================================================
 *  DISJOINT SET (UNION-FIND) IMPLEMENTATION
 *  USING UNION BY SIZE + PATH COMPRESSION
 * =====================================================================================
 *
 * ------------------------------- PROBLEM CONTEXT -------------------------------------
 * Disjoint Set (Union-Find) manages collections of disjoint sets efficiently.
 *
 * Supports:
 * 1. Find → Identify which set an element belongs to
 * 2. Union → Merge two sets
 *
 * USE CASES:
 * - Graph connectivity
 * - Cycle detection in undirected graphs
 * - Kruskal’s Minimum Spanning Tree
 * - Network connectivity problems
 *
 * ------------------------------------------------------------------------------------
 *
 * ----------------------------------- APPROACH ----------------------------------------
 *
 * Optimizations used:
 *
 * 1️⃣ Path Compression (in findUPar)
 * - Flattens the tree during find operations
 * - Each node points directly to its ultimate parent
 *
 * 2️⃣ Union by Size
 * - Attach the smaller-size tree under the larger-size tree
 * - Keeps trees balanced, improves efficiency
 *
 * WHY THIS WORKS:
 * - Avoids skewed trees
 * - Guarantees near-constant time operations (amortized)
 *
 * LIMITATIONS:
 * - Cannot easily list all elements in a set
 * - Best suited for connectivity checks
 *
 * ------------------------------------------------------------------------------------
 *
 * ---------------------------- TIME & SPACE COMPLEXITY --------------------------------
 *
 * Time Complexity:
 * - Amortized O(α(N)) per operation
 *   where α(N) is inverse Ackermann function (almost constant)
 *
 * Space Complexity:
 * - O(N) for parent[] and size[] arrays
 *
 * =====================================================================================
 */
public class DisjointSetBySize {

    // size[i] = number of nodes in the tree rooted at i
    List<Integer> size = new ArrayList<>();

    // parent[i] = immediate parent of node i
    List<Integer> parent = new ArrayList<>();

    /**
     * Constructor
     *
     * Initializes:
     * - parent[i] = i → each node is its own parent
     * - size[i] = 1 → initial size of each set is 1
     *
     * NOTE: indexing from 0 to n (inclusive) for 1-based support
     */
    public DisjointSetBySize(int n) {
        for (int i = 0; i <= n; i++) {
            parent.add(i);
            size.add(1);
        }
    }

    /**
     * FIND OPERATION WITH PATH COMPRESSION
     *
     * @param node - node whose ultimate parent is needed
     * @return ultimate parent (root of the set)
     */
    public int findUPar(int node) {
        if (node == parent.get(node)) {
            return node; // node is root
        }

        // Recursively find ultimate parent
        int ulp = findUPar(parent.get(node));

        // Path compression: point node directly to root
        parent.set(node, ulp);

        return parent.get(node);
    }

    /**
     * UNION OPERATION USING SIZE
     *
     * @param u - first node
     * @param v - second node
     *
     * LOGIC:
     * - Find ultimate parents of u and v
     * - If same → already connected
     * - Otherwise:
     *     - Attach smaller-size tree under larger-size tree
     *     - Update size of new root
     */
    public void unionBySize(int u, int v) {

        int ulp_u = findUPar(u);
        int ulp_v = findUPar(v);

        // Already in same set
        if (ulp_u == ulp_v) return;

        // Attach smaller tree under larger tree
        if (size.get(ulp_u) < size.get(ulp_v)) {
            parent.set(ulp_u, ulp_v);
            size.set(ulp_v, size.get(ulp_v) + size.get(ulp_u));
        } else {
            parent.set(ulp_v, ulp_u);
            size.set(ulp_u, size.get(ulp_u) + size.get(ulp_v));
        }
    }

    /**
     * -------------------------------- MAIN METHOD --------------------------------
     * Test union by size operations.
     */
    public static void main(String[] args) {

        DisjointSetBySize ds = new DisjointSetBySize(7);

        // First component: 1-2-3
        ds.unionBySize(1, 2);
        ds.unionBySize(2, 3);

        // Second component: 4-5-6-7
        ds.unionBySize(4, 5);
        ds.unionBySize(6, 7);
        ds.unionBySize(5, 6);

        // Check if 3 and 7 are connected
        System.out.println(ds.findUPar(3) == ds.findUPar(7) ? "Same" : "Not Same");

        // Connect both components
        ds.unionBySize(3, 7);

        // Re-check connectivity
        System.out.println(ds.findUPar(3) == ds.findUPar(7) ? "Same" : "Not Same");
    }
}
