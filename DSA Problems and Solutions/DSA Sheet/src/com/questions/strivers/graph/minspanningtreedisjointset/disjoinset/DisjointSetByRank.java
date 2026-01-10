package com.questions.strivers.graph.minspanningtreedisjointset.disjoinset;

import java.util.*;

/**
 * =====================================================================================
 *  DISJOINT SET (UNION-FIND) USING UNION BY RANK + PATH COMPRESSION
 * =====================================================================================
 *
 * -------------------------------- PROBLEM CONTEXT -----------------------------------
 * Disjoint Set (also known as Union-Find) is a data structure that efficiently handles:
 * 1. Finding which set a particular element belongs to.
 * 2. Merging two different sets into one.
 *
 * This structure is widely used in:
 * - Graph connectivity problems
 * - Cycle detection
 * - Kruskal’s Minimum Spanning Tree
 * - Network connectivity problems
 *
 * ------------------------------------------------------------------------------------
 *
 * ----------------------------------- APPROACH ----------------------------------------
 *
 * We use two powerful optimizations:
 *
 * 1️⃣ Union By Rank
 * - Always attach the smaller-depth tree under the larger-depth tree.
 * - Prevents the tree from becoming skewed.
 *
 * 2️⃣ Path Compression
 * - While finding the ultimate parent, we directly attach nodes to the root.
 * - Flattens the tree drastically for future operations.
 *
 * WHY THIS WORKS:
 * - Keeps tree height minimal
 * - Ensures near-constant time operations
 *
 * WHEN TO USE:
 * - Dynamic connectivity problems
 * - When frequent union and find operations are required
 *
 * LIMITATIONS:
 * - Does not store explicit paths
 * - Cannot enumerate all elements of a set directly
 *
 * ------------------------------------------------------------------------------------
 *
 * ---------------------------- TIME & SPACE COMPLEXITY --------------------------------
 *
 * Time Complexity:
 * - Amortized O(α(N)) per operation
 *   where α(N) is the inverse Ackermann function (almost constant)
 *
 * Space Complexity:
 * - O(N) for parent and rank arrays
 *
 * ------------------------------------------------------------------------------------
 */
public class DisjointSetByRank {

    // rank[i] stores the approximate depth of the tree rooted at i
    List<Integer> rank = new ArrayList<>();

    // parent[i] stores the immediate parent of node i
    List<Integer> parent = new ArrayList<>();

    /**
     * Constructor
     *
     * Initializes:
     * - parent[i] = i (each node is its own parent initially)
     * - rank[i] = 0 (tree height starts from 0)
     *
     * NOTE:
     * We initialize from 0 to n (inclusive) to allow
     * 1-based indexing if needed.
     */
    public DisjointSetByRank(int n) {
        for (int i = 0; i <= n; i++) {
            rank.add(0);
            parent.add(i);
        }
    }

    /**
     * FIND OPERATION with PATH COMPRESSION
     *
     * @param node - the node whose ultimate parent is needed
     * @return ultimate parent (root of the set)
     *
     * LOGIC:
     * - If node is its own parent → it is the root
     * - Otherwise:
     *     - Recursively find the ultimate parent
     *     - Compress the path by pointing node directly to root
     */
    public int findUPar(int node) {

        // Base case: node is the root of the set
        if (node == parent.get(node)) {
            return node;
        }

        // Recursively find ultimate parent
        int ulp = findUPar(parent.get(node));

        // Path compression: directly connect node to root
        parent.set(node, ulp);

        return parent.get(node);
    }

    /**
     * UNION OPERATION USING RANK
     *
     * @param u - first node
     * @param v - second node
     *
     * LOGIC:
     * - Find ultimate parents of u and v
     * - If they are same → already connected
     * - Otherwise:
     *     - Attach smaller rank tree under larger rank tree
     *     - If ranks are equal, attach one and increment rank
     */
    public void unionByRank(int u, int v) {

        // Find ultimate parents
        int ulp_u = findUPar(u);
        int ulp_v = findUPar(v);

        // If both belong to the same component, do nothing
        if (ulp_u == ulp_v) return;

        // Attach smaller rank tree under larger rank tree
        if (rank.get(ulp_u) < rank.get(ulp_v)) {
            parent.set(ulp_u, ulp_v);
        }
        else if (rank.get(ulp_v) < rank.get(ulp_u)) {
            parent.set(ulp_v, ulp_u);
        }
        else {
            // If ranks are equal, attach one and increase rank
            parent.set(ulp_v, ulp_u);
            rank.set(ulp_u, rank.get(ulp_u) + 1);
        }
    }

/**
 * =====================================================================================
 *  MAIN CLASS FOR TESTING DISJOINT SET
 * =====================================================================================
 *
 * Demonstrates:
 * - Union operations
 * - Connectivity checks
 *
 * TEST SCENARIO:
 * 1-2-3   and   4-5-6-7
 *
 * Then we connect 3 and 7 and verify connectivity again.
 */

    public static void main(String[] args) {

        // Create Disjoint Set with nodes from 1 to 7
        DisjointSetByRank ds = new DisjointSetByRank(7);

        // Creating first component: 1-2-3
        ds.unionByRank(1, 2);
        ds.unionByRank(2, 3);

        // Creating second component: 4-5-6-7
        ds.unionByRank(4, 5);
        ds.unionByRank(6, 7);
        ds.unionByRank(5, 6);

        // Check if nodes 3 and 7 belong to same component
        if (ds.findUPar(3) == ds.findUPar(7)) {
            System.out.println("Same");
        } else {
            System.out.println("Not Same");
        }

        // Connect both components
        ds.unionByRank(3, 7);

        // Re-check connectivity
        if (ds.findUPar(3) == ds.findUPar(7)) {
            System.out.println("Same");
        } else {
            System.out.println("Not Same");
        }
    }
}
