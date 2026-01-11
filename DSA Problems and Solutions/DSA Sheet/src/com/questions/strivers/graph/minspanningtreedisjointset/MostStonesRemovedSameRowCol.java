package com.questions.strivers.graph.minspanningtreedisjointset;

import java.util.HashSet;
import java.util.Set;

/**
 * =====================================================================================
 *  LeetCode 947 – Most Stones Removed with Same Row or Column
 * =====================================================================================
 *
 * ------------------------------- PROBLEM STATEMENT -----------------------------------
 *
 * You are given an array `stones` where:
 * - stones[i] = [xi, yi] represents the position of the ith stone.
 *
 * A stone can be removed if there exists another stone in:
 * - the same row (xi == xj), OR
 * - the same column (yi == yj).
 *
 * Return the maximum number of stones that can be removed.
 *
 * -------------------------------------------------------------------------------------
 *
 * KEY OBSERVATION:
 * - At least ONE stone must remain in each connected group.
 * - So, for each connected component:
 *     removable stones = (component size - 1)
 *
 * Final Answer:
 *     total stones - number of connected components
 *
 * -------------------------------------------------------------------------------------
 *
 * EXAMPLE:
 *
 * Input:
 * stones = [[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]
 *
 * Output:
 * 5
 *
 * -------------------------------------------------------------------------------------
 *
 * CONSTRAINTS:
 * - 1 <= stones.length <= 1000
 * - 0 <= xi, yi <= 10^4
 *
 * =====================================================================================
 *
 * ----------------------------------- APPROACH ----------------------------------------
 *
 * We model this problem as a graph problem using **Disjoint Set Union (Union-Find)**.
 *
 * 🔹 Each stone is treated as a node.
 * 🔹 If two stones share the same row OR column, they belong to the same component.
 * 🔹 Union such stones.
 *
 * After all unions:
 * - Count how many unique connected components exist.
 * - Result = total stones - number of components
 *
 * WHY THIS WORKS:
 * - In each connected component, we can remove all stones except one.
 *
 * -------------------------------------------------------------------------------------
 *
 * EDGE CASES:
 * - Single stone → cannot remove → answer = 0
 * - All stones isolated → answer = 0
 * - All stones connected → answer = n - 1
 *
 * -------------------------------------------------------------------------------------
 *
 * TIME COMPLEXITY:
 * - O(n² * α(n))
 *   Nested loop checks all pairs of stones.
 *
 * SPACE COMPLEXITY:
 * - O(n)
 *   Parent, rank arrays, and component set.
 *
 * -------------------------------------------------------------------------------------
 *
 * ⚠️ LIMITATION:
 * - O(n²) approach may be slow for large n.
 * - Optimized approach uses row-node and column-node mapping.
 *
 * =====================================================================================
 */
public class MostStonesRemovedSameRowCol {

    /**
     * ---------------------------- DISJOINT SET (DSU) ----------------------------------
     *
     * Supports:
     * - Path Compression (in find)
     * - Union by Rank
     */
    class DisjointSet {

        int[] parent;
        int[] rank;

        /**
         * Initialize DSU with n nodes.
         */
        DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];

            // Initially, every node is its own parent
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        /**
         * Find operation with path compression.
         */
        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // compress path
            }
            return parent[x];
        }

        /**
         * Union operation using rank heuristic.
         */
        void union(int x, int y) {
            int px = find(x);
            int py = find(y);

            if (px == py) return;

            if (rank[px] < rank[py]) {
                parent[px] = py;
            } else if (rank[px] > rank[py]) {
                parent[py] = px;
            } else {
                parent[py] = px;
                rank[px]++;
            }
        }
    }

    /**
     * Main function to compute maximum removable stones.
     */
    public int removeStones(int[][] stones) {

        int n = stones.length;
        DisjointSet ds = new DisjointSet(n);

        // =========================
        // STEP 1: Union stones that
        // share same row or column
        // =========================
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

                // Same row OR same column
                if (stones[i][0] == stones[j][0] ||
                        stones[i][1] == stones[j][1]) {

                    ds.union(i, j);
                }
            }
        }

        // =========================
        // STEP 2: Count components
        // =========================
        Set<Integer> components = new HashSet<>();

        for (int i = 0; i < n; i++) {
            components.add(ds.find(i));
        }

        // Removable stones = total stones - number of components
        return n - components.size();
    }

    /**
     * ------------------------------ MAIN METHOD --------------------------------
     * Used for local testing.
     */
    public static void main(String[] args) {

        MostStonesRemovedSameRowCol solution =
                new MostStonesRemovedSameRowCol();

        int[][] stones = {
                {0, 0}, {0, 1},
                {1, 0}, {1, 2},
                {2, 1}, {2, 2}
        };

        System.out.println(
                "Maximum stones removable: " +
                        solution.removeStones(stones)
        );
    }
}
