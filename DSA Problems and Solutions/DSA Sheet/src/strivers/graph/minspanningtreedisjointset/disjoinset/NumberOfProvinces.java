package com.questions.strivers.graph.minspanningtreedisjointset.disjoinset;

import java.util.*;

/**
 * =====================================================================================
 *  NUMBER OF PROVINCES (DISJOINT SET / UNION-FIND)
 * =====================================================================================
 *
 * ------------------------------- PROBLEM STATEMENT -----------------------------------
 * (LeetCode 547 – Number of Provinces)
 *
 * You are given an `n x n` adjacency matrix `isConnected`, where:
 *  - isConnected[i][j] = 1 means city `i` and city `j` are directly connected.
 *  - isConnected[i][j] = 0 means they are not directly connected.
 *
 * A **province** is a group of directly or indirectly connected cities.
 *
 * Your task is to return the **total number of provinces**.
 *
 * ---------------------------------- EXAMPLE ------------------------------------------
 *
 * Input:
 *   isConnected = [
 *     [1,1,0],
 *     [1,1,0],
 *     [0,0,1]
 *   ]
 *
 * Output:
 *   2
 *
 * Explanation:
 *   - City 0 and 1 are connected → Province 1
 *   - City 2 is alone → Province 2
 *
 * ----------------------------------- APPROACH ----------------------------------------
 *
 * We use the **Disjoint Set (Union-Find)** data structure.
 *
 * Key Idea:
 *  - Initially, every city is its own parent (separate province).
 *  - If isConnected[i][j] == 1, we union city `i` and city `j`.
 *  - After processing all connections, the number of **unique parents**
 *    represents the number of provinces.
 *
 * -------------------------------- WHY THIS WORKS -------------------------------------
 *
 * - Disjoint Set efficiently groups connected components.
 * - Path compression + union by rank keeps operations almost constant time.
 * - Perfect for connectivity problems.
 *
 * -------------------------------- EDGE CASES -----------------------------------------
 *
 * - Single city → 1 province
 * - Fully connected graph → 1 province
 * - No connections → n provinces
 *
 * =====================================================================================
 */
public class NumberOfProvinces {

    /**
     * ------------------------- DISJOINT SET CLASS -------------------------
     */
    static class DisjointSet {

        int[] parent; // Stores parent of each node
        int[] rank;   // Helps keep tree shallow

        /**
         * Constructor initializes each node as its own parent
         */
        DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];

            // Initially, every node is its own parent
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        /**
         * Find operation with Path Compression
         *
         * @param node Current node
         * @return Ultimate parent of the node
         */
        int find(int node) {
            // If node is not its own parent, recursively find parent
            if (parent[node] != node) {
                parent[node] = find(parent[node]); // Path compression
            }
            return parent[node];
        }

        /**
         * Union operation using Rank
         *
         * @param u First node
         * @param v Second node
         */
        void union(int u, int v) {
            int pu = find(u);
            int pv = find(v);

            // If already in same set, no need to union
            if (pu == pv) return;

            // Attach smaller rank tree under larger rank tree
            if (rank[pu] < rank[pv]) {
                parent[pu] = pv;
            } else if (rank[pv] < rank[pu]) {
                parent[pv] = pu;
            } else {
                parent[pv] = pu;
                rank[pu]++;
            }
        }
    }

    /**
     * ------------------------- MAIN LOGIC -------------------------
     *
     * @param isConnected adjacency matrix
     * @return number of provinces
     */
    public int findCircleNum(int[][] isConnected) {

        int n = isConnected.length;

        // Create Disjoint Set for n cities
        DisjointSet ds = new DisjointSet(n);

        /**
         * Traverse adjacency matrix
         * If cities i and j are connected, union them
         */
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

                // Only process if direct connection exists
                if (isConnected[i][j] == 1) {
                    ds.union(i, j);
                }
            }
        }

        /**
         * Count unique parents
         * Each unique parent represents one province
         */
        Set<Integer> provinces = new HashSet<>();
        for (int i = 0; i < n; i++) {
            provinces.add(ds.find(i));
        }

        return provinces.size();
    }

    /**
     * ------------------------- TESTING -------------------------
     */
    public static void main(String[] args) {

        NumberOfProvinces sol = new NumberOfProvinces();

        int[][] isConnected = {
                {1, 1, 0},
                {1, 1, 0},
                {0, 0, 1}
        };

        int result = sol.findCircleNum(isConnected);
        System.out.println("Number of Provinces: " + result);
    }
}

/**
 * =====================================================================================
 *  TIME & SPACE COMPLEXITY
 * =====================================================================================
 *
 * Time Complexity:
 *  - O(n² * α(n))
 *    n² → adjacency matrix traversal
 *    α(n) → inverse Ackermann function (almost constant)
 *
 * Space Complexity:
 *  - O(n)
 *    Parent and rank arrays
 *
 * =====================================================================================
 *
 * ---------------------------- ALTERNATIVE APPROACHES ----------------------------------
 *
 * 1. DFS / BFS (Graph Traversal)
 *    - Convert matrix to adjacency list
 *    - Count connected components
 *    - Time: O(n²)
 *    - Space: O(n)
 *
 * 2. Disjoint Set (Current Approach)
 *    - Best for dynamic connectivity problems
 *    - Easily extensible
 *
 * =====================================================================================
 */
