package com.questions.strivers.graph.problemsbfsdfs;

import java.util.ArrayList;

/**
 * ---------------------------------- Problem Statement ----------------------------------
 * Given an Undirected Graph with V vertices (0 to V-1) represented using an adjacency
 * list, determine whether the graph contains a cycle.
 *
 * A cycle exists in an undirected graph if:
 *   - We can start from a node
 *   - Move through connected edges
 *   - And come back to the same node again without using the same immediate edge back
 *
 * Return:
 *   true  → If ANY connected component contains a cycle
 *   false → Otherwise
 *
 *
 * ------------------------------ Approach (DFS Based) -----------------------------------
 * We use Depth First Search (DFS) to detect a cycle.
 *
 * Concept:
 * 1️⃣ Maintain a visited array
 * 2️⃣ Since graph may contain multiple components, DFS must be applied from every node
 *    that has not been visited.
 * 3️⃣ While performing DFS:
 *      - Mark the current node as visited
 *      - Traverse all its adjacent nodes
 *
 *          ✔ If the adjacent node is NOT visited,
 *            recursively call DFS on it while passing the current node as parent
 *
 *          ✔ If the adjacent node is already visited
 *            AND it is NOT the parent
 *            → Cycle found
 *
 * Why do we check parent?
 * Because in an undirected graph, each edge appears twice (u → v and v → u).
 * The parent check prevents falsely detecting a cycle from the back edge to parent.
 *
 *
 * ------------------------------ When to Use This Approach ------------------------------
 * ✅ When graph is undirected
 * ✅ DFS recursion is acceptable
 * ✅ When simpler implementation is preferred (compared to BFS)
 *
 *
 * ------------------------------ Edge Cases Handled -------------------------------------
 * ✔ Disconnected graphs
 * ✔ Graph with only 1 node
 * ✔ Graph with multiple components
 * ✔ Simple chain graph (no cycle)
 *
 *
 * ------------------------------ Time & Space Complexity --------------------------------
 * Time Complexity:  O(V + E)
 * - Each vertex visited once
 * - Every adjacency edge traversed once
 *
 * Space Complexity: O(V)
 * - Recursion stack
 * - Visited array
 *
 *
 * ------------------------------ Alternative Approaches --------------------------------
 *
 * 1️⃣ BFS Cycle Detection (Using parent tracking queue)
 *    - Similar logic
 *    - Uses queue instead of recursion
 *
 * 2️⃣ Disjoint Set Union (Union-Find)
 *    - For each edge (u, v)
 *    - If both nodes belong to same set → cycle exists
 *    - Very efficient for edge list based graph inputs
 *
 * ----------------------------------------------------------------------------------------
 * Below is the DFS Cycle Detection Implementation
 * ----------------------------------------------------------------------------------------
 */
public class DetectCycleUnDirectedDFS {

    /**
     * Main function to check if graph contains cycle.
     *
     * @param V   number of vertices
     * @param adj adjacency list
     * @return true if cycle present else false
     */
    private boolean isCycle(int V, ArrayList<ArrayList<Integer>> adj) {

        // visited array initialized to 0 (0 -> not visited, 1 -> visited)
        int[] vis = new int[V];

        // Must check all nodes since graph may be disconnected
        for (int i = 0; i < V; i++) {

            // If node is NOT visited, start DFS
            if (vis[i] == 0) {

                // If DFS returns true from any component => cycle exists
                if (dfs(i, -1, vis, adj)) {
                    return true;
                }
            }
        }

        // No component contained cycle
        return false;
    }

    /**
     * DFS function to detect cycle.
     *
     * @param node   Current node
     * @param parent Parent of current node
     * @param vis    visited array
     * @param adj    adjacency list
     * @return true if cycle detected else false
     */
    private static boolean dfs(int node, int parent,
                               int[] vis, ArrayList<ArrayList<Integer>> adj) {

        // Mark current node visited
        vis[node] = 1;

        // Traverse all adjacent nodes
        for (Integer adjNode : adj.get(node)) {

            // Case 1: Adjacent node NOT visited -> move deeper
            if (vis[adjNode] == 0) {

                // If any recursive DFS returns true => cycle exists
                if (dfs(adjNode, node, vis, adj)) {
                    return true;
                }
            }

            /**
             * Case 2: Adjacent node is visited AND NOT the parent
             * This means we reached a previously visited node through a different path
             * So cycle exists.
             */
            else if (adjNode != parent) {
                return true;
            }
        }

        // No cycle found in this DFS path
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

        DetectCycleUnDirectedDFS obj = new DetectCycleUnDirectedDFS();
        boolean result = obj.isCycle(V, adj);

        if (result)
            System.out.println("Cycle Detected in Graph");
        else
            System.out.println("No Cycle Found in Graph");
    }
}
