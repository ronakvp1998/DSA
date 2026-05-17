package com.questions.strivers.graph.basics;

import java.util.ArrayList;

/**
 * ================================= PROBLEM STATEMENT =================================
 * Perform Depth First Search (DFS) Traversal of a Graph.
 * <p>
 * You are given:
 * - Number of vertices 'v' (vertices are labeled 0 to v-1)
 * - Graph represented using Adjacency List
 * <p>
 * Task:
 * Return DFS traversal starting from node 0.
 * <p>
 * DFS Definition:
 * DFS explores a graph by going as deep as possible along one path before
 * backtracking, unlike BFS which travels level-wise.
 * <p>
 * Graph Type Supported:
 * - Works for Directed graphs
 * - Works for Undirected graphs
 * - Works for Connected graphs (Assumption in this code)
 * <p>
 * NOTE:
 * This implementation assumes the graph is CONNECTED and traversal must
 * always start from node 0. If graph is disconnected, only one component
 * will be explored.
 * ======================================================================================
 * <p>
 * =============================== APPROACH EXPLANATION ================================
 * We use:
 * - Recursion to simulate stack behavior
 * - Visited array to ensure a node is processed only once
 * <p>
 * DFS(node):
 * 1️⃣ Mark node as visited
 * 2️⃣ Add node to traversal list
 * 3️⃣ Visit every unvisited adjacent node recursively
 * <p>
 * Why This Works?
 * DFS keeps diving deep into a path.
 * Only when there is no unvisited neighbor, it backtracks automatically.
 * <p>
 * When DFS Is Useful?
 * ✔ Detecting cycles
 * ✔ Topological sorting
 * ✔ Strongly Connected Components
 * ✔ Solving maze / backtracking problems
 * <p>
 * Limitations:
 * ❌ Uses recursion → risk of stack overflow if graph is extremely deep
 * ❌ Does NOT guarantee shortest path
 * ======================================================================================
 * <p>
 * ============================== TIME & SPACE COMPLEXITY ==============================
 * Let:
 * V = number of vertices
 * E = number of edges
 * <p>
 * Time Complexity:  O(V + E)
 * - Each node visited once → O(V)
 * - Each edge explored once → O(E)
 * <p>
 * Space Complexity: O(V)
 * - Visited array → O(V)
 * - Recursion stack (worst case chain graph) → O(V)
 * ======================================================================================
 */

public class DFSGraphs {

    /**
     * Recursive DFS function
     *
     * @param node current node being visited
     * @param vis  visited array
     * @param adj  adjacency list
     * @param ls   result list storing DFS traversal
     */
    private static void dfs(int node, boolean[] vis,
                            ArrayList<ArrayList<Integer>> adj,
                            ArrayList<Integer> ls) {

        vis[node] = true;      // mark current node as visited
        ls.add(node);          // add current node to traversal result

        // Traverse all adjacent nodes of current node
        for (Integer it : adj.get(node)) {

            // Only visit if node not already visited
            if (!vis[it]) {
                dfs(it, vis, adj, ls);  // recursive call → go deeper
            }
        }
    }

    /**
     * Driver method to start DFS traversal from node 0
     *
     * @param v   number of vertices
     * @param adj adjacency list
     * @return list containing DFS traversal
     */
    private ArrayList<Integer> dfsOfGraph(int v, ArrayList<ArrayList<Integer>> adj) {

        boolean[] vis = new boolean[v];

        // List to store DFS traversal result
        ArrayList<Integer> ls = new ArrayList<>();

        // Start DFS from node 0
        dfs(0, vis, adj, ls);

        return ls;
    }


    // =============================== MAIN FOR TESTING ===============================
    public static void main(String[] args) {

        /*
         * Example Graph:
         *
         *      0
         *     / \
         *    1   2
         *         \
         *          3
         *
         * Expected DFS starting from 0:
         *  0 -> 1 -> 2 -> 3
         */

        int v = 4;

        // Create adjacency list
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < v; i++)
            adj.add(new ArrayList<>());

        // Adding edges (Undirected Graph Example)
        adj.get(0).add(1);
        adj.get(1).add(0);

        adj.get(0).add(2);
        adj.get(2).add(0);

        adj.get(2).add(3);
        adj.get(3).add(2);

        DFSGraphs obj = new DFSGraphs();
        ArrayList<Integer> ans = obj.dfsOfGraph(v, adj);

        System.out.println("DFS Traversal starting from Node 0:");
        System.out.println(ans);
    }
}


/*
 ================================= ALTERNATIVE APPROACHES =================================

1️⃣ DFS For Disconnected Graph
--------------------------------
If the graph may NOT be connected, we must ensure all components are visited.

Pseudo Update:

boolean[] vis = new boolean[v];
ArrayList<Integer> ans = new ArrayList<>();

for(int i = 0; i < v; i++){
    if(!vis[i]){
        dfs(i, vis, adj, ans);
    }
}

This ensures DFS covers the entire graph.

--------------------------------------------------------------------------------------------

2️⃣ Iterative DFS (Using Stack)
--------------------------------
Instead of recursion, use stack:

Stack<Integer> st = new Stack<>();
st.push(0);

while(!st.isEmpty()){
    int node = st.pop();
    ...
}

✔ Avoids recursion depth limit
❌ Code becomes slightly harder to read

--------------------------------------------------------------------------------------------
*/
