package com.questions.strivers.graph.otheralgo;

import java.util.*;

/*
================================================================================
PROBLEM STATEMENT (LeetCode / GFG Equivalent)
================================================================================
Strongly Connected Components (SCC) in a Directed Graph

You are given a directed graph with V vertices and E edges.
A Strongly Connected Component (SCC) is a maximal group of vertices such that:
- Every vertex is reachable from every other vertex in the same group.

Your task is to find the number of strongly connected components in the graph.

--------------------------------------------------------------------------------
EXAMPLE:
Input:
V = 5
Edges:
1 -> 0
0 -> 2
2 -> 1
0 -> 3
3 -> 4

Output:
3

Explanation:
SCCs are:
{0,1,2}, {3}, {4}

================================================================================
APPROACH: KOSARAJU'S ALGORITHM
================================================================================
Kosaraju's Algorithm works in 3 main steps:

1. Perform DFS on the original graph and store vertices in a stack
   according to their finishing times.

2. Reverse (transpose) the graph.

3. Pop vertices from the stack and perform DFS on the transposed graph.
   Each DFS traversal gives one SCC.

--------------------------------------------------------------------------------
WHY THIS WORKS?
- Finishing time order ensures we always start DFS from a "source SCC"
  in the condensed graph.
- Transposing the graph reverses edges so SCCs become easily discoverable.

--------------------------------------------------------------------------------
WHEN TO USE?
- Directed graphs
- When SCC count or components are needed
- Competitive programming & interviews

--------------------------------------------------------------------------------
LIMITATIONS:
- Requires two DFS passes
- Uses extra memory for transpose graph
- Recursive DFS may cause stack overflow for very deep graphs

================================================================================
TIME & SPACE COMPLEXITY
================================================================================
Time Complexity:
- DFS traversal: O(V + E)
- Transpose creation: O(V + E)
Overall: O(V + E)

Space Complexity:
- Visited array: O(V)
- Stack: O(V)
- Transposed graph: O(V + E)
Overall: O(V + E)

================================================================================
*/

public class KosarajusAlgoSCC {

    /*
     * STEP 1:
     * Perform DFS and push nodes into stack based on finishing time.
     *
     * This ensures nodes finishing later are processed first
     * during SCC discovery.
     */
    private void dfs(int node, int[] vis, List<Integer>[] adj, Stack<Integer> st) {

        // Mark the current node as visited
        vis[node] = 1;

        // Traverse all adjacent nodes
        for (int it : adj[node]) {

            // If the adjacent node is not visited, perform DFS
            if (vis[it] == 0) {
                dfs(it, vis, adj, st);
            }
        }

        // Push node after visiting all its neighbors
        // (finishing time concept)
        st.push(node);
    }

    /*
     * STEP 3:
     * DFS on the transposed graph to mark all nodes
     * belonging to the same SCC.
     */
    private void dfsTranspose(int node, int[] vis, List<Integer>[] adjT) {

        // Mark node as visited in transposed graph
        vis[node] = 1;

        // Visit all reversed edges
        for (int it : adjT[node]) {

            if (vis[it] == 0) {
                dfsTranspose(it, vis, adjT);
            }
        }
    }

    /*
     * MAIN FUNCTION:
     * Returns the number of strongly connected components.
     */
    public int kosaraju(int V, List<Integer>[] adj) {

        // Visited array to track visited nodes
        int[] vis = new int[V];

        // Stack to store nodes by finishing time
        Stack<Integer> st = new Stack<>();

        /*
         * STEP 1:
         * Perform DFS on original graph and fill stack
         */
        for (int i = 0; i < V; i++) {
            if (vis[i] == 0) {
                dfs(i, vis, adj, st);
            }
        }

        /*
         * STEP 2:
         * Create transpose (reverse) of the graph
         */
        List<Integer>[] adjT = new ArrayList[V];

        for (int i = 0; i < V; i++) {
            adjT[i] = new ArrayList<>();
        }

        // Reset visited array for second DFS
        Arrays.fill(vis, 0);

        // Reverse all edges
        for (int i = 0; i < V; i++) {
            for (int it : adj[i]) {
                adjT[it].add(i);
            }
        }

        /*
         * STEP 3:
         * Process nodes in order of stack and count SCCs
         */
        int scc = 0;

        while (!st.isEmpty()) {
            int node = st.pop();

            // If node is not visited, it starts a new SCC
            if (vis[node] == 0) {
                scc++;
                dfsTranspose(node, vis, adjT);
            }
        }

        return scc;
    }

/*
================================================================================
TESTING USING MAIN METHOD
================================================================================
*/

    public static void main(String[] args) {

        int n = 5;

        // Directed edges
        int[][] edges = {
                {1, 0}, {0, 2},
                {2, 1}, {0, 3},
                {3, 4}
        };

        // Adjacency list representation
        List<Integer>[] adj = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        // Build the graph
        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
        }

        // Run Kosaraju's Algorithm
        KosarajusAlgoSCC obj = new KosarajusAlgoSCC();
        int ans = obj.kosaraju(n, adj);

        System.out.println("The number of strongly connected components is: " + ans);
    }
}
