package com.questions.strivers.graph.problemsbfsdfs;

import java.util.*;

/**
 * ========================= BIPARTITE GRAPH - BFS IMPLEMENTATION =========================
 *
 * Problem Statement:
 * ------------------
 * Given an undirected graph with V vertices (0-based indexing) represented
 * using an adjacency list, determine whether the graph is BIPARTITE.
 *
 * What is a Bipartite Graph?
 * --------------------------
 * A graph is called bipartite if:
 *  - We can split its vertices into TWO sets
 *  - Such that NO two adjacent vertices belong to the same set
 *
 * Another simpler meaning:
 * If we can color the graph using ONLY TWO COLORS
 * such that NO two connected nodes have the SAME COLOR,
 * then the graph is BIPARTITE.
 *
 * Note:
 * -----
 * Graph may consist of multiple disconnected components.
 *
 * -----------------------------------------------------------------------------------------
 *
 * APPROACH — Breadth First Search (BFS)
 * -------------------------------------
 * We use BFS level coloring.
 *
 * 1️⃣ Maintain a color array of size V initialized with -1
 *     - -1 → Node is NOT colored yet
 *     - 0 / 1 → Two possible colors
 *
 * 2️⃣ For every node (because graph may be disconnected):
 *      - If not colored, assign color = 0 and start BFS
 *
 * 3️⃣ In BFS:
 *      - For current node, assign opposite color (1 - currentColor) to neighbors
 *      - If neighbor already has SAME COLOR → Not bipartite → return false
 *
 * 4️⃣ If entire traversal completes safely → Graph is bipartite
 *
 * -----------------------------------------------------------------------------------------
 *
 * Why BFS Works?
 * --------------
 * BFS explores graph level-wise.
 * Adjacent nodes always come in alternating levels,
 * hence naturally alternate colors.
 *
 * If any adjacent nodes require same color → conflict proves NOT bipartite.
 *
 * -----------------------------------------------------------------------------------------
 *
 * Edge Cases
 * ----------
 * ✔ Disconnected Graph → Must check all components
 * ✔ Single Node Graph → Always bipartite
 * ✔ Graph with Self Loop → NOT bipartite
 * ✔ Odd Cycle → NOT bipartite
 * ✔ Even Cycle → Bipartite
 *
 * -----------------------------------------------------------------------------------------
 *
 * TIME & SPACE COMPLEXITY
 * -----------------------
 * Time Complexity  : O(V + E)
 *  - Each vertex visited once
 *  - Each edge checked once
 *
 * Space Complexity : O(V)
 *  - Color array
 *  - BFS queue
 *
 * -----------------------------------------------------------------------------------------
 *
 * DFS vs BFS (Which is Better?)
 * ------------------------------
 * ✔ BFS
 *    - No recursion → SAFE for large graphs
 *    - Preferred in interviews
 *
 * ✔ DFS
 *    - Logic similar
 *    - Risk of stack overflow in deep recursion
 *
 * Both have same Time Complexity.
 *
 * -----------------------------------------------------------------------------------------
 *
 * INTERVIEW TIPS
 * --------------
 * 💡 Always mention:
 *   - Graph can be disconnected
 *   - Bipartite fails when ODD Cycle exists
 *   - Even cycle graphs are always bipartite
 *
 * =========================================================================================
 */

public class BipartiteGraphBFS {

    /**
     * Function to check if the graph is bipartite using BFS
     *
     * @param V     number of vertices
     * @param graph adjacency list representation of graph
     * @return true if bipartite, false otherwise
     */
    public static boolean isBipartite(int V, List<List<Integer>> graph) {

        int[] color = new int[V];

        // Initially mark all vertices as uncolored (-1)
        Arrays.fill(color, -1);

        // Handle multiple components
        for (int start = 0; start < V; start++) {

            // If node is not colored → start BFS from it
            if (color[start] == -1) {

                // Assign starting color
                color[start] = 0;

                // Normal BFS Queue
                Queue<Integer> queue = new LinkedList<>();
                queue.add(start);

                while (!queue.isEmpty()) {

                    int node = queue.poll();

                    // Traverse all adjacent nodes
                    for (int adjNode : graph.get(node)) {

                        // CASE 1: If not colored → give opposite color
                        if (color[adjNode] == -1) {
                            color[adjNode] = 1 - color[node];
                            queue.add(adjNode);
                        }

                        // CASE 2: Already colored but color matches → Not bipartite
                        else if (color[adjNode] == color[node]) {
                            return false;
                        }
                    }
                }
            }
        }

        return true; // No conflicts found → bipartite graph
    }

    /**
     * ============================== DRIVER CODE ===============================
     */
    public static void main(String[] args) {

        int V = 5;

        /*
         * Example Graph:
         *
         * 0 -- 1
         * |    |
         * 3 -- 2
         *
         * 4 is isolated
         *
         * This graph IS bipartite
         */

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < V; i++)
            graph.add(new ArrayList<>());

        graph.get(0).add(1);
        graph.get(1).add(0);

        graph.get(1).add(2);
        graph.get(2).add(1);

        graph.get(2).add(3);
        graph.get(3).add(2);

        graph.get(3).add(0);
        graph.get(0).add(3);

        boolean result = isBipartite(V, graph);

        System.out.println("Is Graph Bipartite? → " + result);
        // Expected Output → true
    }

    public boolean isBipartiteMatrix(int[][] graph) {

        int n = graph.length;

        // Color array: -1 = uncolored, 0 & 1 = two colors
        int[] color = new int[n];
        Arrays.fill(color, -1);

        // We must handle disconnected graph components
        for (int start = 0; start < n; start++) {

            // If already colored → already processed
            if (color[start] != -1) continue;

            // Start BFS from this node
            Queue<Integer> q = new LinkedList<>();
            q.add(start);
            color[start] = 0; // assign initial color

            while (!q.isEmpty()) {
                int node = q.poll();

                // Traverse all adjacent nodes
                for (int neighbor : graph[node]) {

                    // CASE 1: If neighbor is not colored → assign opposite color
                    if (color[neighbor] == -1) {
                        color[neighbor] = 1 - color[node];
                        q.add(neighbor);
                    }

                    // CASE 2: If neighbor already has SAME COLOR → not bipartite
                    else if (color[neighbor] == color[node]) {
                        return false;
                    }
                }
            }
        }

        // If no conflict found → graph is bipartite
        return true;
    }

}
