package com.questions.strivers.graph.toposortproblems;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * ==============================================
 *      Detect Cycle in Directed Graph (BFS) (Kahn's Algorithm)
 * ==============================================
 *
 * Problem Statement:
 * Given a directed graph with V vertices and adjacency list adj,
 * determine if the graph contains a cycle.
 *
 * Approach:
 *  - We use Kahn's Algorithm (Topological Sort) which leverages BFS.
 *  - Idea:
 *      1️⃣ Compute in-degree of all vertices.
 *      2️⃣ Push all vertices with in-degree 0 into a queue.
 *      3️⃣ Process the queue:
 *           - Remove vertex, reduce in-degree of neighbors
 *           - If neighbor's in-degree becomes 0 → push to queue
 *      4️⃣ Count number of nodes processed.
 *  - If the count != V → cycle exists.
 *
 * Why it works:
 *  - A valid topological sort exists only if graph has no cycles.
 *  - Nodes part of a cycle will never reach in-degree 0.
 *
 * Time Complexity  : O(V + E)
 * Space Complexity : O(V + E) → adjacency list + queue + in-degree array
 */

public class DetectCycleDirectedBFSKahnAlgo {

    /**
     * Function to detect cycle using BFS (Kahn's Algorithm)
     * @param V   Number of vertices
     * @param adj Adjacency list
     * @return true if cycle exists, false otherwise
     */
    private static boolean isCyclic(int V, ArrayList<ArrayList<Integer>> adj) {

        int[] indegree = new int[V]; // stores in-degree of each vertex

        // STEP 1: Calculate in-degree of all vertices
        for (int i = 0; i < V; i++) {
            for (int neighbor : adj.get(i)) {
                indegree[neighbor]++;
            }
        }

        // STEP 2: Queue of all vertices with in-degree 0
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            if (indegree[i] == 0) q.add(i);
        }

        int count = 0; // number of nodes processed

        // STEP 3: BFS
        while (!q.isEmpty()) {
            int node = q.poll();
            count++;

            // Reduce in-degree of neighbors
            for (int neighbor : adj.get(node)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0) q.add(neighbor);
            }
        }

        // STEP 4: If all nodes processed → no cycle, else cycle exists
        return count != V;
    }

    /**
     * Main method to test BFS cycle detection
     */
    public static void main(String[] args) {

        int V = 11;   // number of vertices
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();

        // Initialize adjacency list
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        // Example directed graph edges
        adj.get(1).add(2);
        adj.get(2).add(3);
        adj.get(3).add(4);
        adj.get(3).add(7);
        adj.get(4).add(5);
        adj.get(5).add(6);
        adj.get(7).add(5);
        adj.get(8).add(9);
        adj.get(9).add(10);
        adj.get(10).add(8);  // creates a cycle

        boolean ans = isCyclic(V, adj);

        if (ans)
            System.out.println("Graph contains a cycle: True");
        else
            System.out.println("Graph does not contain a cycle: False");
    }
}
