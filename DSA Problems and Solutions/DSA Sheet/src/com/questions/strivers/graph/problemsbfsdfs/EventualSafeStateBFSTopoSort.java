package com.questions.strivers.graph.problemsbfsdfs;

import java.util.*;

/**
 * ===============================================
 *       Eventual Safe States - BFS / Topological Sort
 * ===============================================
 *
 * Problem Statement:
 * Given a directed graph with V vertices (0-based index) in adjacency list form,
 * find all the "safe nodes" of the graph.
 *
 * Definitions:
 *  - Terminal Node: Node with no outgoing edges
 *  - Safe Node: Node from which every possible path leads to a terminal node
 *
 * Task:
 *  Return a sorted list of all safe nodes.
 *
 * Approach:
 *  1️⃣ Reverse the graph:
 *      - If u -> v exists, in the reversed graph we add v -> u
 *      - We also track indegree of nodes (number of outgoing edges in original graph)
 *
 *  2️⃣ BFS (Kahn's Algorithm / Topological Sort):
 *      - Start with nodes having indegree 0 (terminal nodes)
 *      - Remove node from queue and reduce indegree of its parents in reversed graph
 *      - If indegree of a parent becomes 0 → it is also safe → push into queue
 *
 *  3️⃣ Collect all nodes processed → safe nodes
 *      - Sort before returning
 *
 * Why it works:
 *  - Nodes involved in cycles will never reach indegree 0 in BFS
 *  - Only nodes that eventually lead to terminal nodes will be included
 *
 * Complexity:
 *  - Time: O(V + E)
 *    - Build reverse graph: O(E)
 *    - BFS: Each node and edge processed once
 *  - Space: O(V + E)
 *    - Reverse adjacency list + indegree array + queue + result list
 */

public class EventualSafeStateBFSTopoSort {

    /**
     * Function to find all eventual safe nodes
     * @param V   Number of vertices
     * @param adj Original adjacency list of the graph
     * @return Sorted list of safe nodes
     */
    private static List<Integer> eventualSafeNodes(int V, List<Integer>[] adj) {

        List<Integer>[] adjRev = new List[V];  // Reverse adjacency list
        int[] indegree = new int[V];           // Track outgoing edges for original graph

        // Initialize reverse adjacency list
        for (int i = 0; i < V; i++) {
            adjRev[i] = new ArrayList<>();
        }

        // Build reverse graph and calculate indegree
        for (int i = 0; i < V; i++) {
            for (int neighbor : adj[i]) {
                adjRev[neighbor].add(i);  // Reverse edge
                indegree[i]++;             // Count outgoing edges in original graph
            }
        }

        Queue<Integer> q = new LinkedList<>();  // BFS queue for safe nodes
        List<Integer> safeNodes = new ArrayList<>();

        // STEP 1: Add all terminal nodes (nodes with indegree 0) to the queue
        for (int i = 0; i < V; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }

        // STEP 2: BFS to propagate "safeness" through reversed graph
        while (!q.isEmpty()) {
            int node = q.poll();
            safeNodes.add(node);  // Node is safe

            for (int parent : adjRev[node]) {
                indegree[parent]--;       // Reduce indegree (simulate removing outgoing edge)
                if (indegree[parent] == 0) {
                    q.offer(parent);      // Parent becomes safe
                }
            }
        }

        // STEP 3: Return safe nodes sorted in ascending order
        Collections.sort(safeNodes);
        return safeNodes;
    }

    /**
     * Driver code to test eventualSafeNodes function
     */
    public static void main(String[] args) {

        int V = 12;  // Number of vertices

        // Adjacency list for the graph
        List<Integer>[] adj = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }

        // Adding edges to the graph
        adj[0].add(1);
        adj[1].add(2);
        adj[2].add(3);
        adj[2].add(4);
        adj[3].add(4);
        adj[4].add(5);
        adj[5].add(6);
        adj[6].add(7);
        adj[8].add(1);
        adj[8].add(9);
        adj[9].add(10);
        adj[10].add(8);   // Cycle
        adj[11].add(9);

        // Compute safe nodes
        List<Integer> safeNodes = eventualSafeNodes(V, adj);

        // Print result
        System.out.println("Safe Nodes in the graph: " + safeNodes);
    }



    /**
     * Function to find all eventual safe nodes using BFS.
     *
     * @param graph Directed graph as adjacency list (graph[i] contains neighbors of node i)
     * @return List of safe nodes sorted in ascending order
     */
    public List<Integer> eventualSafeNodesLeetcode(int[][] graph) {
        int V = graph.length;

        // Step 1: Build the reverse graph
        List<Integer>[] revGraph = new ArrayList[V];
        int[] indegree = new int[V]; // Count of outgoing edges in original graph

        for (int i = 0; i < V; i++) {
            revGraph[i] = new ArrayList<>();
        }

        for (int i = 0; i < V; i++) {
            for (int nei : graph[i]) {
                revGraph[nei].add(i); // Reverse edge: neighbor -> i
            }
            indegree[i] = graph[i].length; // Original outdegree
        }

        // Step 2: Initialize queue with terminal nodes (nodes with 0 outdegree)
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }

        // Step 3: BFS - propagate safe nodes backward using reverse graph
        boolean[] safe = new boolean[V];
        while (!q.isEmpty()) {
            int node = q.poll();
            safe[node] = true; // Node is safe
            for (int parent : revGraph[node]) {
                indegree[parent]--; // Reduce outdegree
                if (indegree[parent] == 0) {
                    q.offer(parent); // Node becomes safe
                }
            }
        }

        // Step 4: Collect safe nodes
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if (safe[i]) result.add(i);
        }

        return result; // Already sorted due to 0-based traversal
    }
}
