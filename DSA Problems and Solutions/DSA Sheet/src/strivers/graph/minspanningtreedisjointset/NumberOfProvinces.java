package com.questions.strivers.graph.minspanningtreedisjointset;

import java.util.*;

/**
 * ================================================================================================
 *  NUMBER OF PROVINCES (DISJOINT SET / UNION FIND)
 * ================================================================================================
 *
 * 📌 PROBLEM STATEMENT (LeetCode 547 - Number of Provinces):
 * --------------------------------------------------------------------------------
 * There are n cities. Some of them are connected, while some are not.
 * If city A is connected directly with city B, and city B is connected
 * directly with city C, then city A is connected indirectly with city C.
 *
 * A "province" is a group of directly or indirectly connected cities.
 *
 * You are given an n x n matrix isConnected where:
 *   - isConnected[i][j] = 1 if city i and city j are directly connected
 *   - isConnected[i][j] = 0 otherwise
 *
 * Return the total number of provinces.
 *
 * --------------------------------------------------------------------------------
 * Example:
 * Input:
 * isConnected = [
 *   [1,1,0],
 *   [1,1,0],
 *   [0,0,1]
 * ]
 *
 * Output:
 * 2
 *
 * Explanation:
 * - City 0 and 1 belong to the same province.
 * - City 2 is alone → separate province.
 *
 * ================================================================================================
 *
 * 🧠 APPROACH (Disjoint Set / Union-Find):
 * --------------------------------------------------------------------------------
 * 1. Treat each city as an individual component initially.
 * 2. If isConnected[i][j] == 1, union city i and city j.
 * 3. After processing all connections, count how many cities
 *    are their own parent → these represent distinct provinces.
 *
 * Why DSU Works?
 * - Efficiently groups connected components.
 * - Handles indirect connections automatically.
 * - Optimal for connectivity problems.
 *
 * When to use:
 * - Graph connectivity
 * - Counting connected components
 * - Dynamic union operations
 *
 * Limitations:
 * - Not suitable for path-based problems (use DFS/BFS instead)
 *
 * ================================================================================================
 *
 * ⏱ TIME COMPLEXITY:
 * --------------------------------------------------------------------------------
 * - Traversing adjacency matrix: O(N²)
 * - DSU operations (with path compression + union by rank):
 *   ~O(α(N)) per operation (almost constant)
 *
 * Overall: O(N²)
 *
 * 🧮 SPACE COMPLEXITY:
 * --------------------------------------------------------------------------------
 * - Parent & rank arrays: O(N)
 *
 * ================================================================================================
 *
 * 🔄 ALTERNATIVE APPROACHES:
 * --------------------------------------------------------------------------------
 * 1. DFS:
 *    - Traverse graph and count connected components.
 *    - Time: O(N²)
 *    - Space: O(N) recursion stack
 *
 * 2. BFS:
 *    - Similar to DFS but iterative.
 *
 * DSU is preferred when:
 * - Graph grows dynamically
 * - Multiple union operations required
 *
 * ================================================================================================
 */

public class NumberOfProvinces {

    /**
     * ---------------------------
     * DISJOINT SET (UNION FIND)
     * ---------------------------
     */
    static class DisjointSet {

        int[] parent;
        int[] rank;

        /**
         * Constructor initializes DSU structure.
         * Each node is its own parent initially.
         */
        public DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];

            for (int i = 0; i < n; i++) {
                parent[i] = i;  // each node is its own parent
                rank[i] = 0;    // initial rank is 0
            }
        }

        /**
         * FIND operation with Path Compression
         *
         * @param node the node whose ultimate parent is required
         * @return ultimate parent
         */
        public int find(int node) {
            if (parent[node] == node) {
                return node;
            }
            // Path compression for optimization
            parent[node] = find(parent[node]);
            return parent[node];
        }

        /**
         * UNION operation using Union by Rank
         *
         * @param u first node
         * @param v second node
         */
        public void union(int u, int v) {
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
     * -----------------------------------
     * MAIN LOGIC: COUNT NUMBER OF PROVINCES
     * -----------------------------------
     */
    public static int findCircleNum(int[][] isConnected) {

        int n = isConnected.length;

        // Step 1: Initialize Disjoint Set
        DisjointSet ds = new DisjointSet(n);

        // Step 2: Traverse adjacency matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                // If cities i and j are directly connected
                if (isConnected[i][j] == 1) {
                    ds.union(i, j);
                }
            }
        }

        // Step 3: Count number of unique parents
        int provinces = 0;
        for (int i = 0; i < n; i++) {
            if (ds.find(i) == i) {
                provinces++;
            }
        }

        return provinces;
    }

    /**
     * -------------------------
     * MAIN METHOD (TESTING)
     * -------------------------
     */
    public static void main(String[] args) {

        int[][] isConnected = {
                {1, 1, 0},
                {1, 1, 0},
                {0, 0, 1}
        };

        int result = findCircleNum(isConnected);
        System.out.println("Number of Provinces: " + result);
    }
}
