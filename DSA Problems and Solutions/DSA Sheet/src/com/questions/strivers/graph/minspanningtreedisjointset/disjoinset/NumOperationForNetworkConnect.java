package com.questions.strivers.graph.minspanningtreedisjointset.disjoinset;

/**
 * =====================================================================================
 *  LEETCODE PROBLEM: 1319. Number of Operations to Make Network Connected
 * =====================================================================================
 *
 * ------------------------------- PROBLEM STATEMENT -----------------------------------
 * There are `n` computers numbered from `0` to `n - 1` connected by ethernet cables.
 * Each cable connects two different computers.
 *
 * You are given an array `connections` where:
 *   connections[i] = [a, b] represents a cable connecting computer `a` and computer `b`.
 *
 * Any computer can reach any other computer directly or indirectly through the network.
 *
 * You are allowed to remove any cable and reconnect it between any two computers.
 *
 * Return the minimum number of operations required to make all computers connected.
 * If it is not possible, return `-1`.
 *
 * ---------------------------------- EXAMPLE ------------------------------------------
 * Input:
 *   n = 4
 *   connections = [[0,1], [0,2], [1,2]]
 *
 * Output:
 *   1
 *
 * Explanation:
 *   There is one extra cable that can be reused to connect computer 3.
 *
 * -------------------------------------------------------------------------------------
 *
 * ----------------------------------- APPROACH ----------------------------------------
 *
 * We use the **Disjoint Set (Union-Find)** data structure to:
 * 1. Group computers into connected components.
 * 2. Count how many independent components exist.
 *
 * KEY OBSERVATIONS:
 * - To connect `n` nodes fully, we need **at least (n - 1) cables**.
 * - If we have fewer than (n - 1) cables, it's impossible → return `-1`.
 * - If there are `k` connected components, we need **(k - 1)** operations to connect them.
 *
 * WHY UNION-FIND?
 * - Efficiently checks whether two nodes are already connected.
 * - Supports fast merging of components.
 * - Very suitable for connectivity problems.
 *
 * -------------------------------------------------------------------------------------
 *
 * ----------------------------- TIME & SPACE COMPLEXITY --------------------------------
 *
 * Time Complexity:
 *   - Union-Find with path compression and rank:
 *     O(n + m * α(n)), where:
 *       n = number of nodes
 *       m = number of edges
 *       α(n) = inverse Ackermann function (almost constant)
 *
 * Space Complexity:
 *   - O(n) for parent[] and rank[] arrays
 *
 * -------------------------------------------------------------------------------------
 *
 * ----------------------------- EDGE CASES HANDLED -------------------------------------
 * - Not enough cables (connections.length < n - 1)
 * - Already fully connected network
 * - Multiple disconnected components
 *
 * =====================================================================================
 */
public class NumOperationForNetworkConnect {

    /**
     * This method returns the minimum number of operations required
     * to connect all computers in the network.
     */
    public int makeConnected(int n, int[][] connections) {

        // ---------------- STEP 1: BASIC VALIDATION ----------------
        // A fully connected graph with n nodes requires at least (n - 1) edges.
        // If we have fewer cables, it's impossible to connect all computers.
        if (connections.length < n - 1) {
            return -1;
        }

        // ---------------- STEP 2: INITIALIZE DISJOINT SET ----------------
        // Each computer is initially its own parent (independent component).
        DisjointSet ds = new DisjointSet(n);

        // ---------------- STEP 3: UNION ALL CONNECTIONS ----------------
        // For each cable, connect the two computers.
        for (int[] edge : connections) {
            ds.union(edge[0], edge[1]);
        }

        // ---------------- STEP 4: COUNT CONNECTED COMPONENTS ----------------
        // A node is a representative (root) if parent[node] == node.
        int components = 0;
        for (int i = 0; i < n; i++) {
            if (ds.find(i) == i) {
                components++;
            }
        }

        // ---------------- STEP 5: CALCULATE RESULT ----------------
        // To connect `components` separate networks,
        // we need (components - 1) operations.
        return components - 1;
    }

    /**
     * ----------------------------- MAIN METHOD --------------------------------
     * Used for testing and demonstration purposes.
     */
    public static void main(String[] args) {
        NumOperationForNetworkConnect solution = new NumOperationForNetworkConnect();

        int n = 4;
        int[][] connections = {
                {0, 1},
                {0, 2},
                {1, 2}
        };

        int result = solution.makeConnected(n, connections);
        System.out.println("Minimum operations required: " + result);
        // Expected Output: 1
    }
}

/**
 * =====================================================================================
 *  DISJOINT SET (UNION-FIND) IMPLEMENTATION
 * =====================================================================================
 *
 * PURPOSE:
 * - Efficiently manage connected components
 * - Support:
 *   1. find() → determine which component a node belongs to
 *   2. union() → merge two components
 *
 * OPTIMIZATIONS USED:
 * - Path Compression (in find)
 * - Union by Rank
 *
 * These optimizations ensure nearly constant time operations.
 * =====================================================================================
 */
class DisjointSet {

    // parent[i] stores the parent of node i
    int[] parent;

    // rank[i] stores the depth (approx) of the tree rooted at i
    int[] rank;

    /**
     * Constructor initializes each node as its own parent.
     */
    public DisjointSet(int n) {
        parent = new int[n];
        rank = new int[n];

        // Initially, every node is its own parent (self-loop)
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    /**
     * FIND operation with Path Compression.
     *
     * What it does:
     * - Finds the ultimate parent (root) of a node.
     *
     * Optimization:
     * - Path compression flattens the tree,
     *   making future operations faster.
     */
    public int find(int node) {
        // If node is not its own parent, recursively find root
        if (parent[node] != node) {
            parent[node] = find(parent[node]);
        }
        return parent[node];
    }

    /**
     * UNION operation using Rank.
     *
     * What it does:
     * - Connects two components if they are different.
     *
     * Optimization:
     * - Smaller rank tree is attached under larger rank tree
     *   to keep tree height minimal.
     */
    public void union(int u, int v) {
        int pu = find(u); // parent of u
        int pv = find(v); // parent of v

        // If both nodes already belong to the same component
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
