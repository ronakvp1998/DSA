package com.questions.strivers.graph.toposortproblems;

import java.util.ArrayList;
import java.util.Stack;

/**
 * ==================================================================================================
 *                                 Topological Sort Using DFS
 * ==================================================================================================
 *
 * ------------------------------- PROBLEM STATEMENT -----------------------------------------------
 * Given a Directed Acyclic Graph (DAG) with V vertices labeled from 0 to V-1.
 * The graph is represented using an adjacency list where adj[i] contains all nodes
 * that are directly reachable from node i.
 *
 * Task:
 *  - Find any valid topological ordering of the DAG.
 *  - In topological sort, if there is an edge u -> v, u must appear before v in the ordering.
 *
 * Example:
 *  Input:
 *      V = 6, edges = [[5,0],[5,2],[4,0],[4,1],[2,3],[3,1]]
 *  Output:
 *      One possible topological order: [5,4,2,3,1,0]
 *
 * --------------------------------------------------------------------------------------------------
 *                                APPROACH — DFS BASED TOPOLOGICAL SORT
 * --------------------------------------------------------------------------------------------------
 * 🔑 KEY IDEA:
 *  - Perform DFS on unvisited nodes.
 *  - Once all descendants of a node are processed, push the node to a stack.
 *  - Finally, popping nodes from the stack gives the topological order.
 *
 * STEPS:
 * 1️⃣ Initialize visited array of size V to track visited nodes.
 * 2️⃣ Initialize an empty stack to store the topological order in reverse.
 * 3️⃣ For each node:
 *      - If it is unvisited, perform DFS from that node.
 * 4️⃣ In DFS:
 *      - Mark the current node as visited.
 *      - Recursively DFS all unvisited neighbors.
 *      - After visiting all neighbors, push the node onto the stack.
 * 5️⃣ Pop all nodes from the stack to get the topological sort.
 *
 * WHY THIS WORKS:
 *  - DFS ensures that all descendants of a node are processed before the node itself.
 *  - Thus, when we push the node to stack, all nodes that should come after it are already in the stack.
 *
 * ALTERNATIVE APPROACHES:
 * 1️⃣ Kahn's Algorithm (BFS based topological sort)
 *      - Use indegree array.
 *      - Start from nodes with indegree 0 and remove edges iteratively.
 *      - Useful when cycle detection is also needed.
 *
 * COMPLEXITY ANALYSIS:
 *  - Time Complexity : O(V + E)
 *       Each vertex is visited once and each edge is traversed once in DFS.
 *  - Space Complexity: O(V + E)
 *       Stack + adjacency list + visited array.
 *
 * INTERVIEW NOTE:
 *  - DFS based topological sort is simple and intuitive.
 *  - Useful for DAG scheduling problems, task ordering, course prerequisites, etc.
 * --------------------------------------------------------------------------------------------------
 */
public class TopologicalSortDFS {

    /**
     * Performs DFS from a given node and fills the stack in reverse topological order.
     *
     * @param node current node
     * @param adj adjacency list
     * @param vis visited array
     * @param st stack to store nodes in finishing order
     */
    private static void dfs(int node, ArrayList<ArrayList<Integer>> adj, int[] vis, Stack<Integer> st) {
        // Mark current node as visited
        vis[node] = 1;

        // Explore all neighbors
        for (int neighbor : adj.get(node)) {
            if (vis[neighbor] == 0) {
                dfs(neighbor, adj, vis, st);
            }
        }

        // After visiting all neighbors, push current node to stack
        st.push(node);
    }

    /**
     * Returns a valid topological ordering of the DAG.
     *
     * @param V number of vertices
     * @param adj adjacency list of the graph
     * @return list representing topological order
     */
    private static ArrayList<Integer> topoSort(int V, ArrayList<ArrayList<Integer>> adj) {
        int[] vis = new int[V];          // Visited array
        Stack<Integer> st = new Stack<>(); // Stack to store finishing order

        // Perform DFS from each unvisited vertex
        for (int i = 0; i < V; i++) {
            if (vis[i] == 0) {
                dfs(i, adj, vis, st);
            }
        }

        // Prepare result from stack
        ArrayList<Integer> ans = new ArrayList<>();
        while (!st.isEmpty()) {
            ans.add(st.pop());
        }

        return ans;
    }

    public static void main(String[] args) {
        // Number of vertices
        int V = 6;

        // Initialize adjacency list
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        // Add directed edges
        adj.get(5).add(0);
        adj.get(5).add(2);
        adj.get(4).add(0);
        adj.get(4).add(1);
        adj.get(2).add(3);
        adj.get(3).add(1);

        // Get topological sort
        ArrayList<Integer> topoOrder = topoSort(V, adj);

        // Print topological order
        System.out.print("Topological Sort: ");
        for (int node : topoOrder) {
            System.out.print(node + " ");
        }
    }
}
