package com.questions.strivers.graph.problemsbfsdfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * ---------------------------------- Problem Statement ----------------------------------
 * Given an Undirected Graph with V vertices (numbered 0 to V-1) and adjacency list,
 * determine whether the graph contains a cycle or not.
 *
 * A cycle in an undirected graph exists if:
 *  - There is a path where we can start from a node
 *  - Move using edges
 *  - And come back to the same node, without using the immediate parent edge again.
 *
 * You must return:
 *  true  → if any component of the graph contains a cycle
 *  false → otherwise
 *
 *
 * ------------------------------ Approach (BFS Based) -----------------------------------
 * We use BFS to detect a cycle in an Undirected Graph.
 *
 * Key Idea:
 * 1️⃣ A visited[] array keeps track of visited nodes.
 * 2️⃣ Since graph may be disconnected, we must run BFS for every unvisited node.
 * 3️⃣ While doing BFS:
 *      - Store (node, parent) in queue.
 *      - For every adjacent node:
 *          ✔ If it is NOT visited → push to queue with current node as parent.
 *          ✔ If it is visited AND it's NOT the parent → CYCLE FOUND.
 *
 * Why Parent Tracking?
 * In undirected graphs every edge appears twice (u→v and v→u).
 * Without checking parent, we will always think an edge forms a cycle.
 *
 *
 * ----------------------------- When to Use This Approach? ------------------------------
 * ✅ Useful when graph is undirected.
 * ✅ BFS ensures level-wise checking.
 * ✅ Good when graph is large & we prefer queue-based traversal.
 *
 *
 * ------------------------------ Limitations / Drawbacks -------------------------------
 * ❌ BFS requires extra queue & pair storage.
 * ❌ DFS approach is slightly simpler to implement.
 * ❌ Not suitable for Directed graphs (requires different logic).
 *
 *
 * -------------------------- Time & Space Complexity -----------------------------------
 * Time Complexity:
 *  O(V + E)
 *  - We visit every vertex once
 *  - Traverse all adjacency edges
 *
 * Space Complexity:
 *  O(V)
 *  - Visited array
 *  - Queue in BFS worst case stores V nodes
 *
 *
 * -------------------------- Edge Cases Handled ----------------------------------------
 * ✔ Disconnected Graphs
 * ✔ Single Node Graph (No cycle)
 * ✔ Graph with multiple components
 * ✔ Graph with self loops handled naturally
 *
 *
 * ------------------------ Alternative Recommended Approaches ---------------------------
 * 1️⃣ DFS Cycle Detection (Most Common)
 *    - Maintain visited[]
 *    - Track parent
 *    - If visited and not parent → cycle exists
 *    - Simpler & recursive
 *
 * 2️⃣ Disjoint Set Union (Union-Find)
 *    - For every edge (u,v)
 *    - If both belong to same set → cycle exists
 *    - Best for edge list input based problems
 *    - Very efficient for dynamic graph connectivity
 *
 * ---------------------------------------------------------------------------------------
 * The below implementation uses BFS based cycle detection.
 * ---------------------------------------------------------------------------------------
 */
public class DetectCycleUnDirectedBFS {

    /**
     * Helper class to store:
     * node       : current node in BFS
     * parentNode : node from which we arrived here
     */
    static class Pair {
        int node;
        int parentNode;

        public Pair(int node, int parentNode) {
            this.node = node;
            this.parentNode = parentNode;
        }
    }

    /**
     * Function to check if the undirected graph contains a cycle or not.
     *
     * @param v   Number of vertices
     * @param adj Adjacency List representing the graph
     * @return true if cycle exists, else false
     */
    private static boolean isCycle(int v, ArrayList<ArrayList<Integer>> adj) {

        // visited array to mark nodes visited in any BFS traversal
        boolean[] vis = new boolean[v];

        // We must check all nodes because graph may be disconnected
        for (int i = 0; i < v; i++) {

            // If node is NOT visited, perform BFS from it
            if (!vis[i]) {

                // If any BFS finds cycle, return true
                if (checkCycle(i, adj, vis)) {
                    return true;
                }
            }
        }

        // No cycle found in any component
        return false;
    }

    /**
     * BFS function to detect cycle from a given source node.
     *
     * @param src starting node
     * @param adj adjacency list
     * @param vis visited array
     * @return true if cycle detected
     */
    private static boolean checkCycle(int src,
                                      ArrayList<ArrayList<Integer>> adj,
                                      boolean[] vis) {

        // Mark source as visited
        vis[src] = true;

        // BFS queue storing (node, parentNode)
        Queue<Pair> queue = new LinkedList<>();

        // Start BFS with parent = -1 (because source has no parent)
        queue.add(new Pair(src, -1));

        // Standard BFS loop
        while (!queue.isEmpty()) {

            // Fetch front node & remove from queue
            int node = queue.peek().node;
            int parent = queue.peek().parentNode;
            queue.remove();

            // Traverse all adjacent nodes
            for (Integer adjNode : adj.get(node)) {

                // If adjacent node is NOT visited → push in BFS
                if (vis[adjNode] == false) {
                    vis[adjNode] = true;
                    queue.add(new Pair(adjNode, node));
                }

                /**
                 * If adjacent node is already visited
                 * AND it is NOT the parent
                 *
                 * Means we reached a previously visited node
                 * via a different path → CYCLE!
                 */
                else if (parent != adjNode) {
                    return true;
                }
            }
        }

        // BFS completed without finding cycle
        return false;
    }

    /**
     * --------------------------- MAIN METHOD FOR TESTING ---------------------------
     */
    public static void main(String[] args) {

        int V = 5;

        // Creating adjacency list
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());

        /*
           Constructing Graph:

           0 -- 1
           |    |
           4 -- 2 -- 3

           This graph contains a cycle
        */
        adj.get(0).add(1);
        adj.get(1).add(0);

        adj.get(1).add(2);
        adj.get(2).add(1);

        adj.get(2).add(4);
        adj.get(4).add(2);

        adj.get(0).add(4);
        adj.get(4).add(0);

        adj.get(2).add(3);
        adj.get(3).add(2);

        boolean result = isCycle(V, adj);

        if (result)
            System.out.println("Cycle Detected in Graph");
        else
            System.out.println("No Cycle Found in Graph");
    }
}
