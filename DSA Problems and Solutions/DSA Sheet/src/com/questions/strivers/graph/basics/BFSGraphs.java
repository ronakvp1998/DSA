package com.questions.strivers.graph.basics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * ================================= PROBLEM STATEMENT =================================
 * Perform Breadth First Search (BFS) Traversal of a Graph.
 * <p>
 * You are given:
 * - Number of vertices 'v' (vertices are labeled from 0 to v-1)
 * - Graph represented as an Adjacency List
 * <p>
 * Task:
 * Return the BFS Traversal starting from node 0.
 * <p>
 * BFS Definition:
 * BFS is a level-order traversal technique for Graphs.
 * It visits all neighboring nodes first before moving to the next level.
 * <p>
 * Graph Type Supported:
 * - Works for Undirected Graphs
 * - Works for Directed Graphs
 * - Works for Connected Graphs
 * <p>
 * NOTE:
 * This implementation assumes the graph is CONNECTED and starts traversal from node 0.
 * If the graph is disconnected, only the component reachable from node 0 will be traversed.
 * ======================================================================================
 * <p>
 * =============================== APPROACH EXPLANATION ================================
 * We use:
 * - A Queue --> because BFS follows FIFO (First In First Out)
 * - A Visited array --> to ensure we don't process a node more than once
 * <p>
 * Steps:
 * 1. Create a BFS result list to store traversal order.
 * 2. Maintain a boolean visited[] array to track which nodes are already visited.
 * 3. Push starting node (0) into queue and mark it visited.
 * 4. While queue is not empty:
 * - Remove the front node
 * - Add it to BFS result
 * - Traverse all its adjacent nodes
 * - If any adjacent node is NOT visited:
 * mark visited
 * push into queue
 * <p>
 * Why This Approach Works?
 * BFS explores nodes level by level.
 * The queue ensures order is strictly maintained.
 * <p>
 * When To Use BFS?
 * - To find shortest path in an unweighted graph
 * - To check graph connectivity
 * - For level-wise traversal
 * - Problems like:
 * - Rotten Oranges
 * - Word Ladder
 * - Shortest path in matrix
 * <p>
 * Limitations / Drawbacks:
 * - Uses extra space due to queue + visited array
 * - Not suitable when recursion-based exploration is preferred (DFS fits better there)
 * <p>
 * ======================================================================================
 * <p>
 * ============================ TIME & SPACE COMPLEXITY ================================
 * <p>
 * Let:
 * V = number of vertices
 * E = number of edges
 * <p>
 * Time Complexity:  O(V + E)
 * Explanation:
 * - Each node is pushed & popped from queue once → O(V)
 * - Every adjacency edge is checked once → O(E)
 * <p>
 * Space Complexity: O(V)
 * Explanation:
 * - Visited array uses O(V)
 *
 *
 *
 *
 * - Queue in worst case holds O(V)
 * - Output list stores O(V)
 * <p>
 * Best Case: Graph is small → still O(V + E)
 * Worst Case: Fully connected graph → still O(V + E)
 * <p>
 * ======================================================================================
 */

public class BFSGraphs {

    /**
     * BFS Traversal of Graph starting from node 0
     *
     * @param v   number of vertices in graph
     * @param adj adjacency list representing graph
     * @return ArrayList containing BFS traversal order
     */
    private static ArrayList<Integer> bfsGraphs(int v, ArrayList<ArrayList<Integer>> adj) {

        // Stores final BFS traversal
        ArrayList<Integer> bfs = new ArrayList<>();
        if(v==0){
            return bfs;
        }
        // Visited array to ensure a node is processed only once
        boolean[] vis = new boolean[v];

        // Queue is used because BFS follows FIFO principle
        Queue<Integer> queue = new LinkedList<>();

        // Start BFS from node 0 (as per requirement)
        queue.add(0);
        vis[0] = true;   // mark source node as visited

        // Process until queue becomes empty
        while (!queue.isEmpty()) {

            // Fetch and remove the front element of queue
            Integer node = queue.poll();

            // Add this node to BFS result
            bfs.add(node);

            // Traverse all adjacent nodes of current node
            for (Integer it : adj.get(node)) {

                // If neighbour NOT visited, then visit it
                if (!vis[it]) {
                    vis[it] = true;     // mark visited to avoid reprocessing
                    queue.add(it);      // push into queue for future exploration
                }
            }
        }

        // return BFS traversal list
        return bfs;
    }


    // =============================== MAIN METHOD FOR TESTING ===============================
    public static void main(String[] args) {
        /*
         * Example Graph (Undirected)
         *
         *      0
         *     / \
         *    1   2
         *       / \
         *      3   4
         *
         * Adjacency List representation:
         * 0 -> 1,2
         * 1 -> 0
         * 2 -> 0,3,4
         * 3 -> 2
         * 4 -> 2
         */

        int v = 5; // number of vertices

        // Creating adjacency list
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < v; i++) {
            adj.add(new ArrayList<>());
        }

        // Adding edges (Undirected Graph)
        adj.get(0).add(1);
        adj.get(0).add(2);

        adj.get(1).add(0);

        adj.get(2).add(0);
        adj.get(2).add(3);
        adj.get(2).add(4);

        adj.get(3).add(2);
        adj.get(4).add(2);

        // Call BFS
        ArrayList<Integer> result = bfsGraphs(v, adj);

        // Print BFS Result
        System.out.println("BFS Traversal Starting from Node 0:");
        System.out.println(result);
    }
}

/*
 ================================= ALTERNATIVE APPROACHES =================================

1️⃣ BFS for Disconnected Graph
--------------------------------
If graph is NOT guaranteed to be connected,
then we must run BFS for every unvisited node:

for(int i = 0; i < v; i++){
    if(!visited[i]){
         run BFS from i
    }
}

This ensures all components are covered.

2️⃣ DFS Instead of BFS
------------------------
DFS uses stack (or recursion) and goes deep first.
Better when:
 - Depth exploration matters
 - Stack memory acceptable

Tradeoff:
 - DFS does NOT guarantee shortest path in unweighted graph
 - BFS does

============================================================================================
*/
