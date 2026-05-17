package com.questions.strivers.graph.toposortproblems;

import java.util.*;

/**
 * ==================================================================================================
 *                                 Topological Sort Using BFS (Kahn's Algorithm)
 * ==================================================================================================
 *
 * ------------------------------- PROBLEM STATEMENT -----------------------------------------------
 * Given a Directed Acyclic Graph (DAG) with V vertices labeled from 0 to V-1.
 * The graph is represented using an adjacency list where adj[i] contains all nodes
 * that are directly reachable from node i.
 *
 * Task:
 *  - Return any valid topological ordering of the DAG.
 *  - In topological sort, if there is an edge u -> v, u must appear before v in the ordering.
 *
 * --------------------------------------------------------------------------------------------------
 *                                APPROACH — BFS / KAHN'S ALGORITHM
 * --------------------------------------------------------------------------------------------------
 * 🔑 KEY IDEA:
 *  - Use indegree array to count the number of incoming edges for each node.
 *  - Nodes with indegree 0 have no dependencies and can be added to the topological order.
 *  - Remove the node and reduce indegree of its neighbors.
 *  - Repeat until all nodes are processed.
 *
 * STEPS:
 * 1️⃣ Initialize indegree array of size V to track incoming edges.
 * 2️⃣ Loop through adjacency list to compute indegree for each vertex.
 * 3️⃣ Initialize a queue and enqueue all nodes with indegree 0.
 * 4️⃣ While the queue is not empty:
 *      - Remove a node from the queue and add it to the topological order.
 *      - For each neighbor:
 *          - Reduce indegree by 1.
 *          - If indegree becomes 0, add it to the queue.
 * 5️⃣ Return the topological order array.
 *
 * WHY THIS WORKS:
 *  - Only nodes with no remaining dependencies are added to the order.
 *  - Ensures that all prerequisites are placed before dependent nodes.
 *
 * ALTERNATIVE APPROACHES:
 * 1️⃣ DFS based topological sort
 *      - Perform DFS, push nodes to stack after all neighbors are visited.
 *      - Pop nodes from stack to get topological order.
 *      - Simpler recursion-based approach.
 *
 * COMPLEXITY ANALYSIS:
 *  - Time Complexity : O(V + E)
 *       - Each vertex is processed once.
 *       - Each edge is traversed once while reducing indegrees.
 *  - Space Complexity: O(V + E)
 *       - Queue + indegree array + adjacency list storage.
 *
 * INTERVIEW NOTES:
 *  - BFS (Kahn's Algorithm) is helpful for detecting cycles as well.
 *  - DFS is more recursive and intuitive for DAGs without cycles.
 * --------------------------------------------------------------------------------------------------
 */
public class TopologicalSortBFSKahnsAlgo {

    /**
     * Function to perform BFS-based topological sort
     *
     * @param V   Number of vertices
     * @param adj Adjacency list representing the graph
     * @return    Array representing topological order
     */
    private static int[] topologicalSort(int V, ArrayList<ArrayList<Integer>> adj) {
        int[] indegree = new int[V]; // To store number of incoming edges for each node

        // Calculate indegree for each node
        for (int i = 0; i < V; i++) {
            for (int neighbor : adj.get(i)) {
                indegree[neighbor]++;
            }
        }

        Queue<Integer> q = new LinkedList<>(); // Queue to process nodes with indegree 0

        // Enqueue nodes with indegree 0 (no dependencies)
        for (int i = 0; i < V; i++) {
            if (indegree[i] == 0) {
                q.add(i);
            }
        }

        int[] topo = new int[V]; // Array to store topological order
        int idx = 0;             // Index for topological array

        // Process the graph
        while (!q.isEmpty()) {
            int node = q.poll();
            topo[idx++] = node;

            // Reduce indegree of all neighbors
            for (int neighbor : adj.get(node)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0) {
                    q.add(neighbor); // Node is now ready to be processed
                }
            }
        }

        return topo; // Return topological order
    }

    public static void main(String[] args) {
        int V = 6; // Number of vertices

        // Create adjacency list
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        // Add edges
        adj.get(5).add(0);
        adj.get(5).add(2);
        adj.get(4).add(0);
        adj.get(4).add(1);
        adj.get(2).add(3);
        adj.get(3).add(1);

        // Get topological order
        int[] topoOrder = topologicalSort(V, adj);

        // Print topological order
        System.out.print("Topological Sort (BFS / Kahn's Algorithm): ");
        for (int val : topoOrder) {
            System.out.print(val + " ");
        }
    }
}
