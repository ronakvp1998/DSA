package com.questions.strivers.graph.problemsbfsdfs;

/**
 * ========================= PROBLEM: Number of Provinces (LeetCode 547) =========================
 *
 * We are given an 'n x n' matrix `isConnected`, where:
 *  - isConnected[i][j] = 1 → City i and City j are directly connected
 *  - isConnected[i][j] = 0 → City i and City j are NOT directly connected
 *  - City i is always connected to itself → isConnected[i][i] = 1
 *  - Matrix is symmetric → isConnected[i][j] == isConnected[j][i]
 *
 * A "Province" is defined as:
 *  A group of cities that are directly OR indirectly connected.
 *
 * Example:
 *  [ [1,1,0],
 *    [1,1,0],
 *    [0,0,1] ]
 *
 * Here:
 *  - City 0 ↔ City 1 are connected → Province 1
 *  - City 2 is alone → Province 2
 *
 * Output = 2
 *
 * ================================================================================================
 *
 * =========================== MAIN IDEA / APPROACH (DFS on Graph) ===============================
 *
 * Treat each city as a Node in a Graph.
 * Treat `isConnected` matrix as an adjacency matrix.
 *
 * We need to count how many *connected components* exist.
 *
 * Approach using DFS:
 * 1️⃣ Maintain a visited[] array to track which cities are already processed
 * 2️⃣ Loop through each city
 * 3️⃣ If a city is NOT visited:
 *        → This means we found a NEW PROVINCE
 *        → Perform DFS to mark all cities reachable from it
 *        → Increment province count
 *
 * DFS ensures:
 *  - We explore all directly + indirectly connected cities
 *
 * Why this works?
 *  Because every DFS call covers an entire connected component.
 *
 * ================================================================================================
 *
 * ================================ TIME & SPACE COMPLEXITY =======================================
 *
 * Let n = number of cities
 *
 * Time Complexity:  O(n²)
 * Reason:
 *  - We may iterate full matrix in DFS worst case
 *
 * Space Complexity: O(n)
 * Reason:
 *  - visited[] array
 *  - recursion stack worst case O(n)
 *
 * ================================================================================================
 */

public class NumberOfProvincesMatrix {

    /**
     * DFS function to explore all cities connected to 'city'
     */
    private static void dfs(int city, int[][] isConnected, boolean[] visited) {

        // Mark current city as visited
        visited[city] = true;

        // Traverse entire row of city in adjacency matrix
        // If isConnected[city][adjCity] == 1 → there is an edge
        for (int adjCity = 0; adjCity < isConnected.length; adjCity++) {

            // Condition:
            // 1️⃣ Must be connected
            // 2️⃣ Must not be visited already
            if (isConnected[city][adjCity] == 1 && !visited[adjCity]) {
                dfs(adjCity, isConnected, visited);    // Recursively explore
            }
        }
    }

    /**
     * Main function to count number of provinces
     */
    public static int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;            // number of cities
        boolean[] visited = new boolean[n];    // visited array
        int provinces = 0;                     // result

        // Check each city
        for (int city = 0; city < n; city++) {

            // If not visited → new province found
            if (!visited[city]) {
                provinces++;                    // count province
                dfs(city, isConnected, visited); // mark all connected cities
            }
        }

        return provinces;
    }

    // ======================== MAIN METHOD FOR TESTING ========================
    public static void main(String[] args) {

        int[][] ex1 = {
                {1,1,0},
                {1,1,0},
                {0,0,1}
        };

        int[][] ex2 = {
                {1,0,0},
                {0,1,0},
                {0,0,1}
        };

        System.out.println("Example 1 Provinces: " + findCircleNum(ex1)); // Expected: 2
        System.out.println("Example 2 Provinces: " + findCircleNum(ex2)); // Expected: 3
    }
}


/*
 =================================================================================================
 🔁 ALTERNATIVE APPROACHES (INTERVIEW DISCUSSION WORTH!)
 =================================================================================================

1️⃣ BFS Instead of DFS
-----------------------
Instead of recursion:
Use Queue:
 - Start from 1 city
 - Push connected neighbors
 - Visit all level by level
 - Count components

Complexity remains:
Time  : O(n²)
Space : O(n)

-----------------------------------------------------------------------------------------------

2️⃣ Disjoint Set Union (Union Find) — BEST OPTIMIZED
-------------------------------------------------------
Union connected cities into same parent group.
Count unique parents.

Steps:
 - Initialize parent[] for all cities
 - If isConnected[i][j] == 1 → union(i, j)
 - Count unique parents = provinces

Why DSU?
 ✔ Avoids recursion
 ✔ Extremely fast in dense graphs
 ✔ Used in real-world networking problems

Time:  O(n² * α(n)) ≈ O(n²)
Space: O(n)

===============================================================================================
*/
