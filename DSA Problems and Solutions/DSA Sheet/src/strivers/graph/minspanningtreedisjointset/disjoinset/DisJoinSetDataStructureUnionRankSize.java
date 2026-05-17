package com.questions.strivers.graph.minspanningtreedisjointset.disjoinset;

import java.util.ArrayList;
import java.util.List;

/**
 * =====================================================================================
 *  DISJOINT SET DATA STRUCTURE (UNION-FIND)
 *  - Union By Rank
 *  - Union By Size
 *  - Path Compression
 * =====================================================================================
 *
 * ------------------------------- PROBLEM CONTEXT -------------------------------------
 * The Disjoint Set (Union-Find) data structure is used to manage a collection
 * of disjoint (non-overlapping) sets efficiently.
 *
 * It supports two fundamental operations:
 *
 * 1️⃣ Find
 *    - Determines which set (component) a particular element belongs to.
 *
 * 2️⃣ Union
 *    - Merges two different sets into one.
 *
 * ------------------------------- COMMON USE CASES ------------------------------------
 * - Finding connected components in a graph
 * - Detecting cycles in undirected graphs
 * - Kruskal’s Minimum Spanning Tree (MST)
 * - Network connectivity problems
 * - Dynamic connectivity queries
 *
 * ------------------------------------------------------------------------------------
 *
 * ----------------------------------- APPROACH ----------------------------------------
 *
 * This implementation uses **three key optimizations**:
 *
 * 1️⃣ Path Compression (in findUPar)
 * - While finding the ultimate parent, all intermediate nodes are directly
 *   attached to the root.
 * - This flattens the tree and drastically speeds up future queries.
 *
 * 2️⃣ Union By Rank
 * - Attach the tree with smaller height under the tree with larger height.
 * - Prevents the tree from becoming tall.
 *
 * 3️⃣ Union By Size
 * - Attach the smaller-size component under the larger-size component.
 * - Keeps the overall structure balanced.
 *
 * ⚠️ IMPORTANT:
 * - In real applications, **use either Union by Rank OR Union by Size**, not both.
 * - Both give almost identical performance.
 *
 * ------------------------------------------------------------------------------------
 *
 * ---------------------------- TIME & SPACE COMPLEXITY --------------------------------
 *
 * Time Complexity:
 * - Amortized O(α(N)) per operation
 *   where α(N) is the inverse Ackermann function (almost constant).
 *
 * Space Complexity:
 * - O(N) for parent[], rank[], and size[] lists.
 *
 * ------------------------------------------------------------------------------------
 *
 * ----------------------------- EDGE CASES HANDLED ------------------------------------
 * - Union of already connected nodes
 * - Self-parent nodes
 * - Repeated find operations after unions
 *
 * =====================================================================================
 */
public class DisJoinSetDataStructureUnionRankSize {

    // rank[i] stores the approximate height of the tree rooted at i
    List<Integer> rank = new ArrayList<>();

    // parent[i] stores the immediate parent of node i
    List<Integer> parent = new ArrayList<>();

    // size[i] stores the number of nodes in the component rooted at i
    List<Integer> size = new ArrayList<>();

    /**
     * Constructor
     *
     * Initializes the Disjoint Set:
     * - parent[i] = i → every node is initially its own parent
     * - rank[i] = 0   → initial height of each tree is zero
     * - size[i] = 1   → each node starts as a component of size one
     *
     * NOTE:
     * Initialization is done from 0 to n (inclusive) so that
     * 1-based indexing (1…n) can be used naturally.
     */
    public DisJoinSetDataStructureUnionRankSize(int n) {
        for (int i = 0; i <= n; i++) {
            rank.add(0);
            parent.add(i);
            size.add(1);
        }
    }

    /**
     * FIND OPERATION WITH PATH COMPRESSION
     *
     * @param node - node whose ultimate parent is required
     * @return ultimate parent (root of the connected component)
     *
     * LOGIC:
     * - If node is its own parent → it is the root.
     * - Otherwise:
     *     - Recursively find the root.
     *     - Compress the path by directly connecting node to root.
     */
    public int findUPar(int node) {

        // Base case: node is the root of the component
        if (node == parent.get(node)) {
            return node;
        }

        // Recursively find ultimate parent
        int ulp = findUPar(parent.get(node));

        // Path compression step
        parent.set(node, ulp);

        return parent.get(node);
    }

    /**
     * UNION BY RANK
     *
     * @param u - first node
     * @param v - second node
     *
     * LOGIC:
     * - Find ultimate parents of u and v.
     * - If both are same → already in the same component.
     * - Otherwise:
     *     - Attach smaller-rank tree under larger-rank tree.
     *     - If ranks are equal, increment the rank of the new root.
     */
    public void unionByRank(int u, int v) {

        int ulp_u = findUPar(u);
        int ulp_v = findUPar(v);

        // If both nodes are already connected, no action needed
        if (ulp_u == ulp_v) return;

        // Attach smaller height tree under larger height tree
        // rank of pu < rank of pv
        if (rank.get(ulp_u) < rank.get(ulp_v)) {
            parent.set(ulp_u, ulp_v);
        }
        // rank of pv < rank of pu
        else if (rank.get(ulp_v) < rank.get(ulp_u)) {
            parent.set(ulp_v, ulp_u);
        }
        // rank of pu == rank of pv
        else {
            // Same rank: choose one root and increase its rank
            parent.set(ulp_v, ulp_u);
            rank.set(ulp_u, rank.get(ulp_u) + 1);
        }
    }

    /**
     * UNION BY SIZE
     *
     * @param u - first node
     * @param v - second node
     *
     * LOGIC:
     * - Find ultimate parents of u and v.
     * - If both are same → already connected.
     * - Otherwise:
     *     - Attach smaller-size component under larger-size component.
     *     - Update the size of the new root.
     */
    public void unionBySize(int u, int v) {

        int ulp_u = findUPar(u);
        int ulp_v = findUPar(v);

        // Already in same component
        if (ulp_u == ulp_v) return;

        // Attach smaller component under larger component
        // size of pu < size of pv
        if (size.get(ulp_u) < size.get(ulp_v)) {
            parent.set(ulp_u, ulp_v);
            size.set(ulp_v, size.get(ulp_v) + size.get(ulp_u));
        }
        // size of pv < size of pu
        else {
            parent.set(ulp_v, ulp_u);
            size.set(ulp_u, size.get(ulp_u) + size.get(ulp_v));
        }
    }

    /**
     * -------------------------------- MAIN METHOD --------------------------------
     * Demonstrates the working of Disjoint Set operations.
     *
     * TEST SCENARIO:
     * - First component: 1 - 2 - 3
     * - Second component: 4 - 5 - 6 - 7
     *
     * Then:
     * - Check connectivity between 3 and 7
     * - Connect them
     * - Check again
     */
    public static void main(String[] args) {

        DisJoinSetDataStructureUnionRankSize ds = new DisJoinSetDataStructureUnionRankSize(7);

        // Creating first component: 1-2-3
        ds.unionByRank(1, 2);
        ds.unionByRank(2, 3);

        // Creating second component: 4-5-6-7
        ds.unionByRank(4, 5);
        ds.unionByRank(6, 7);
        ds.unionByRank(5, 6);

        // Check if nodes 3 and 7 are connected
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
