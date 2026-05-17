package com.questions.strivers.recursionbacktracking.hard;

import java.util.ArrayList;
import java.util.List;

/*
Problem Statement:
Given an undirected graph and a number m, determine if the graph can be colored with at most m colors
such that no two adjacent vertices of the graph are colored with the same color.

Examples:

Example 1:
Input:
N = 4
M = 3
E = 5
Edges[] = {
  (0, 1),
  (1, 2),
  (2, 3),
  (3, 0),
  (0, 2)
}
Output: 1
Explanation: It is possible to color the given graph using 3 colors.

Example 2:
Input:
N = 3
M = 2
E = 3
Edges[] = {
  (0, 1),
  (1, 2),
  (0, 2)
}
Output: 0
Explanation: It is not possible to color.
 */

public class MColoringProblem {

    // Function to check if the graph can be colored with at most 'C' colors
    private static boolean graphColoring(List<Integer>[] G, int[] color, int i, int C) {
        int n = G.length;
        // Start recursive backtracking from node 0
        return solve(i, G, color, n, C);
    }

    // Utility function to check if it's safe to color 'node' with 'col'
    private static boolean isSafe(int node, List<Integer>[] G, int[] color, int n, int col) {
        for (int it : G[node]) {
            // If adjacent node already has the same color, return false
            if (color[it] == col) return false;
        }
        return true;
    }

    // Recursive function that tries to assign colors to nodes
    private static boolean solve(int node, List<Integer>[] G, int[] color, int n, int m) {
        // Base case: If all nodes are colored, return true
        if (node == n) return true;

        // Try all possible colors (from 1 to m)
        for (int i = 1; i <= m; i++) {
            // Check if assigning color 'i' to 'node' is safe
            if (isSafe(node, G, color, n, i)) {
                color[node] = i; // Assign the color
                // Recursively assign colors to the next node
                if (solve(node + 1, G, color, n, m)) return true;
                // Backtrack if not successful
                color[node] = 0;
            }
        }
        return false; // If no color assignment is possible, return false
    }

    public static void main(String[] args) {
        int N = 4, M = 3;

        // Create adjacency list representation of graph
        List<Integer>[] G = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            G[i] = new ArrayList<>();
        }

        // Adding edges (undirected graph)
        G[0].add(1);
        G[1].add(0);

        G[1].add(2);
        G[2].add(1);

        G[2].add(3);
        G[3].add(2);

        G[3].add(0);
        G[0].add(3);

        G[0].add(2);
        G[2].add(0);

        // Array to store color assigned to each vertex
        int[] color = new int[N];

        // Check if coloring is possible
        boolean ans = graphColoring(G, color, 0, M);

        if (ans)
            System.out.println("1"); // Coloring possible
        else
            System.out.println("0"); // Coloring not possible
    }
}

/*
----------------------------
Time Complexity Analysis:
----------------------------
- In the worst case, we try all 'm' colors for each of the 'n' nodes.
- For each node, we check adjacency list in O(degree(node)) time.
- Worst case: O(m^n), since we may explore all color combinations recursively.
- Checking adjacency (isSafe) takes O(n) in the worst case.
=> Overall Time Complexity: O(m^n * n) in the worst case.

----------------------------
Space Complexity Analysis:
----------------------------
- Adjacency list storage: O(N + E) where E = number of edges.
- Color array: O(N).
- Recursive call stack: O(N) in worst case (depth of recursion).
=> Overall Space Complexity: O(N + E).
*/
