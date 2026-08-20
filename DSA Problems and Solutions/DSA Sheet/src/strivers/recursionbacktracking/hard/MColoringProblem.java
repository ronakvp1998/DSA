package strivers.recursionbacktracking.hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * # M - Coloring Problem
 *
 * ## 1. Header & Problem Context
 * **Problem Statement:**
 * Given an undirected graph and a number M, determine if the graph can be colored
 * with at most M colors such that no two adjacent vertices of the graph are colored
 * with the same color.
 *
 * **Constraints:**
 * - 1 <= N <= 20 (Number of vertices)
 * - 1 <= M <= N (Number of colors)
 * - 0 <= E <= (N * (N - 1)) / 2 (Number of edges)
 *
 * **Examples:**
 *
 * Example 1:
 * Input: N = 4, M = 3, E = 5
 * Edges = {(0, 1), (1, 2), (2, 3), (3, 0), (0, 2)}
 * Output: true (1)
 * Explanation: It is possible to color the given graph using 3 colors.
 * (e.g., 0->Color1, 1->Color2, 2->Color3, 3->Color2)
 *
 * Example 2:
 * Input: N = 3, M = 2, E = 3
 * Edges = {(0, 1), (1, 2), (0, 2)}
 * Output: false (0)
 * Explanation: A triangle requires 3 colors. With only 2 colors, it is impossible.
 *
 * ---
 *
 * ## 2.2 Progressive Implementation Roadmap (Non-DP)
 *
 * * **Phase 1: Optimal Approach** - Backtracking with Early Pruning (Standard Graph Coloring).
 * * **Phase 2: Brute Force Approach** - Generate all M^N color assignments, then validate.
 * * **Phase 3: Alternative Approaches** - Greedy approach (Welsh-Powell algorithm), which
 *   finds an upper bound for the chromatic number but does not solve the strict M-decision
 *   problem optimally for arbitrary graphs.
 */
public class MColoringProblem {

    /**
     * Helper class to represent the Graph cleanly.
     */
    static class Graph {
        int V;
        List<List<Integer>> adjList;

        Graph(int V) {
            this.V = V;
            adjList = new ArrayList<>();
            for (int i = 0; i < V; i++) {
                adjList.add(new ArrayList<>());
            }
        }

        void addEdge(int u, int v) {
            adjList.get(u).add(v);
            adjList.get(v).add(u);
        }
    }

    /**
     * ## Phase 1: Optimal Approach - Backtracking with Early Pruning
     *
     * **Detailed Intuition:**
     * Instead of generating all color combinations blindly, we assign colors vertex
     * by vertex (from 0 to N-1). Before assigning a color to a vertex, we check if
     * it is "safe" (i.e., no adjacent vertex currently holds the same color). If it
     * is safe, we recurse to the next vertex. If we hit a dead end, we backtrack
     * by removing the color and trying the next available one.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** $O(M^N)$ in the worst-case, but significantly faster on
     *   average due to aggressive early pruning of invalid branches. Checking safety
     *   takes $O(V)$ in the worst case. Total time is loosely bounded by $O(M^N)$.
     * - **Space Complexity:** $O(V + E)$ auxiliary heap space for the graph representation,
     *   plus $O(V)$ auxiliary stack space for the recursion depth and the color array.
     */
    public boolean graphColoringOptimal(Graph graph, int m) {
        int[] color = new int[graph.V]; // 0 represents uncolored
        return solveOptimal(0, graph, color, m);
    }

    private boolean solveOptimal(int node, Graph graph, int[] color, int m) {
        // Base case: If all vertices are processed, a valid coloring is found.
        if (node == graph.V) {
            return true;
        }

        // Try every color from 1 to M for the current node.
        for (int i = 1; i <= m; i++) {
            if (isSafe(node, graph, color, i)) {
                color[node] = i; // Assign color

                // Recurse for the next node
                if (solveOptimal(node + 1, graph, color, m)) {
                    return true;
                }

                color[node] = 0; // Backtrack
            }
        }
        return false; // No valid color could be assigned to this node
    }

    private boolean isSafe(int node, Graph graph, int[] color, int currentColor) {
        for (int neighbor : graph.adjList.get(node)) {
            if (color[neighbor] == currentColor) {
                return false;
            }
        }
        return true;
    }

    /**
     * ## Phase 2: Brute Force Approach - Generate and Validate
     *
     * **Detailed Intuition:**
     * This represents the raw "Think it" phase. We generate absolutely every possible
     * color assignment for the $N$ vertices (creating a massive tree of $M^N$ leaves).
     * Only when a full combination is formed do we iterate through all edges to check
     * if the configuration is valid.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** $O(M^N \times E)$. We generate exactly $M^N$ configurations.
     *   Validating each configuration requires checking all $E$ edges.
     * - **Space Complexity:** $O(V)$ auxiliary stack space for the recursion tree depth,
     *   plus $O(V + E)$ heap space for the graph representation.
     */
    public boolean graphColoringBruteForce(Graph graph, int m) {
        int[] color = new int[graph.V];
        return solveBruteForce(0, graph, color, m);
    }

    private boolean solveBruteForce(int node, Graph graph, int[] color, int m) {
        // Base case: check validity only when all nodes are colored
        if (node == graph.V) {
            return isValidConfiguration(graph, color);
        }

        for (int i = 1; i <= m; i++) {
            color[node] = i;
            if (solveBruteForce(node + 1, graph, color, m)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidConfiguration(Graph graph, int[] color) {
        for (int u = 0; u < graph.V; u++) {
            for (int v : graph.adjList.get(u)) {
                if (color[u] == color[v]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * ## 4. Testing Suite
     */
    public static void main(String[] args) {
        MColoringProblem solver = new MColoringProblem();

        // Custom wrapper for tests
        class TestCase {
            Graph graph;
            int m;
            boolean expected;

            TestCase(int v, int[][] edges, int m, boolean expected) {
                this.graph = new Graph(v);
                for (int[] edge : edges) {
                    this.graph.addEdge(edge[0], edge[1]);
                }
                this.m = m;
                this.expected = expected;
            }
        }

        // Initialize test cases
        TestCase[] tests = {
                // Example 1: 4 nodes, 3 colors, expected True
                new TestCase(4, new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 0}, {0, 2}}, 3, true),

                // Example 2: 3 nodes (Triangle), 2 colors, expected False
                new TestCase(3, new int[][]{{0, 1}, {1, 2}, {0, 2}}, 2, false),

                // Bipartite Graph: 4 nodes, 2 colors, expected True
                new TestCase(4, new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 0}}, 2, true),

                // Disconnected Graph: 5 nodes, 1 color, no edges, expected True
                new TestCase(5, new int[][]{}, 1, true)
        };

        System.out.println("--- Running M-Coloring Tests ---");

        // Use Java 8 Stream API to process test cases
        IntStream.range(0, tests.length).forEach(i -> {
            TestCase test = tests[i];
            System.out.println("\nTesting Case " + (i + 1) + " (V=" + test.graph.V + ", M=" + test.m + ")");

            boolean optRes = solver.graphColoringOptimal(test.graph, test.m);
            boolean bfRes = solver.graphColoringBruteForce(test.graph, test.m);

            System.out.println("Optimal:    " + optRes + " -> " + (optRes == test.expected ? "PASS" : "FAIL"));
            System.out.println("BruteForce: " + bfRes + " -> " + (bfRes == test.expected ? "PASS" : "FAIL"));
            System.out.println("---------------------------------");
        });
    }
}