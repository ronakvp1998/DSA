package com.questions.strivers.graph.problemsbfsdfs;

import java.util.*;

/**
 * ========================= BIPARTITE GRAPH - DFS IMPLEMENTATION =========================
 *
 * Problem Statement:
 * ------------------
 * You are given an undirected graph represented as an adjacency list
 * for V vertices (0-based indexing). Determine whether the graph is BIPARTITE.
 *
 * What is a Bipartite Graph?
 * --------------------------
 * A graph is said to be bipartite if:
 *  - We can divide its vertices into TWO disjoint sets
 *  - Such that NO two adjacent vertices share the same set
 *
 * Another way to understand:
 * If we can color the graph using ONLY 2 COLORS so that
 * NO two adjacent nodes have the same color → Graph is Bipartite.
 *
 * Important:
 * ----------
 * Graph may consist of multiple components → must check all.
 *
 * -----------------------------------------------------------------------------------------
 *
 * APPROACH — Depth First Search (DFS)
 * -----------------------------------
 * 1️⃣ Maintain a color array `color[V]`
 *      - Initially filled with -1 (means uncolored)
 *
 * 2️⃣ For every unvisited node:
 *      - Assign a starting color (0)
 *      - Perform DFS
 *
 * 3️⃣ During DFS:
 *      - Assign alternate color to neighbors: (1 - currentColor)
 *      - If a neighbor already has SAME COLOR → NOT bipartite → return false
 *
 * 4️⃣ If all nodes are colored properly without conflict → Bipartite
 *
 * -----------------------------------------------------------------------------------------
 *
 * Why DFS Works?
 * --------------
 * DFS tries to color depth-wise.
 * If at any point we are forced to give same color to adjacent nodes,
 * conflict proves graph is NOT bipartite.
 *
 * -----------------------------------------------------------------------------------------
 *
 * Edge Cases
 * ----------
 * ✔ Disconnected Graph → Must check every component
 * ✔ Single Node → Always bipartite
 * ✔ Graph with self loop → NOT bipartite
 * ✔ Graph with odd length cycle → NOT bipartite
 * ✔ Graph with even length cycle → Bipartite
 *
 * -----------------------------------------------------------------------------------------
 *
 * TIME & SPACE COMPLEXITY
 * -----------------------
 * Time Complexity  →  O(V + E)
 *   Every vertex and edge processed once
 *
 * Space Complexity →  O(V)
 *   color[] + recursion stack
 *
 * Worst Case Recursion Depth → O(V)
 *
 * -----------------------------------------------------------------------------------------
 *
 * Drawbacks of DFS
 * ----------------
 * ❌ Stack overflow risk in very deep graphs
 *
 * Alternative (Recommended for large graphs):
 * -------------------------------------------
 * BFS Bipartite Check
 *  - Uses queue
 *  - No recursion depth issues
 *  - Same time complexity
 *
 * -----------------------------------------------------------------------------------------
 *
 * Interview Tip
 * -------------
 * ALWAYS mention:
 *  ✔ Graph must be undirected
 *  ✔ Need to check ALL components
 *  ✔ Odd cycle ⇒ Not Bipartite
 *  ✔ Even cycle ⇒ Bipartite
 *
 * =========================================================================================
 */

public class BipartiteGraphDFS {

    /**
     * DFS function to attempt coloring the graph
     *
     * @param node  Current node being processed
     * @param color Array storing assigned colors for each node
     * @param graph Adjacency List representation of graph
     * @return false if conflict (same color adjacent) occurs, else true
     */
    private static boolean dfs(int node, int[] color, List<List<Integer>> graph) {

        // Traverse all adjacent nodes
        for (int adjNode : graph.get(node)) {

            // CASE 1: If adjacent node is NOT colored → color it with opposite color
            if (color[adjNode] == -1) {
                color[adjNode] = 1 - color[node];   // alternate color

                // Continue DFS; if fails return false
                if (!dfs(adjNode, color, graph)) {
                    return false;
                }
            }
            // CASE 2: If adjacent node already has SAME color → NOT bipartite
            else if (color[adjNode] == color[node]) {
                return false;
            }
        }
        return true;    // No conflicts
    }

    /**
     * Function to check whether graph is bipartite
     */
    public static boolean isBipartite(int V, int[][] graph) {

        List<List<Integer>>adj = new ArrayList<>();
        for(int i=0;i<V;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<V;i++){
            for(int j : graph[i]){
                adj.get(i).add(j);
            }
        }
        int[] color = new int[V];

        // Initially mark all vertices uncolored
        Arrays.fill(color, -1);

        // Graph may have multiple components
        for (int i = 0; i < V; i++) {

            // If node is not colored, color it and start DFS
            if (color[i] == -1) {
                color[i] = 0;   // assign first color

                if (!dfs(i, color, adj)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * ============================== DRIVER CODE ===============================
     */
    public static void main(String[] args) {

        int V = 5;

        /*
         * Example Graph
         * 0 -- 1
         * |    |
         * 3 -- 2
         *
         * 4 is a standalone node
         *
         * This is bipartite
         */

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < V; i++) graph.add(new ArrayList<>());

        graph.get(0).add(1);
        graph.get(1).add(0);

        graph.get(1).add(2);
        graph.get(2).add(1);

        graph.get(2).add(3);
        graph.get(3).add(2);

        graph.get(3).add(0);
        graph.get(0).add(3);

        // Node 4 → isolated

//        boolean result = isBipartite(V, graph);

//        System.out.println("Is Graph Bipartite? → " + result);
        // Expected Output → true
    }
}
