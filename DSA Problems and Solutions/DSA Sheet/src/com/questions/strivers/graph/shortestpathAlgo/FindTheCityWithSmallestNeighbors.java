package com.questions.strivers.graph.shortestpathAlgo;

/**
 * =====================================================================================
 *  LeetCode 1334 - Find the City With the Smallest Number of Neighbors
 * =====================================================================================
 *
 * ---------------------------------- PROBLEM STATEMENT ---------------------------------
 * There are `n` cities numbered from `0` to `n - 1`.
 *
 * You are given:
 *  - An integer `n` representing the number of cities.
 *  - A 2D array `edges`, where each edge is represented as:
 *        edges[i] = [from, to, weight]
 *    This indicates a **bidirectional weighted edge** between city `from` and city `to`
 *    with a distance (weight) equal to `weight`.
 *  - An integer `distanceThreshold`.
 *
 * TASK:
 * -----
 * For each city, determine how many **other cities** are reachable from it
 * such that the **shortest path distance** to those cities is
 * **less than or equal to `distanceThreshold`**.
 *
 * Return:
 * -------
 * - The city that can reach the **smallest number of cities** within the threshold.
 * - If multiple cities have the same minimum reachable count,
 *   return the city with the **greatest index**.
 *
 * IMPORTANT NOTES:
 * ----------------
 * - The distance between two cities is the **sum of edge weights** along the shortest path.
 * - A city is **NOT counted as its own neighbor**.
 *
 * ---------------------------------- EXAMPLE --------------------------------------------
 * Input:
 *      n = 4
 *      edges = [[0,1,3], [1,2,1], [1,3,4], [2,3,1]]
 *      distanceThreshold = 4
 *
 * Output:
 *      3
 *
 * Explanation:
 *      City 3 can reach only 2 cities within distance ≤ 4,
 *      which is the minimum among all cities.
 *
 * =====================================================================================
 *  APPROACH USED: Floyd–Warshall Algorithm (All-Pairs Shortest Path)
 * =====================================================================================
 *
 * WHY FLOYD–WARSHALL?
 * -------------------
 * - We need the **shortest distance between every pair of cities**.
 * - Constraints allow an O(n³) solution.
 * - Floyd–Warshall is simple, reliable, and perfect for dense graphs.
 *
 * STEP-BY-STEP APPROACH:
 * ----------------------
 * 1. Create a distance matrix `dist[n][n]`
 * 2. Initialize:
 *      - dist[i][i] = 0 (distance to itself)
 *      - dist[u][v] = weight (for direct edges)
 *      - all other distances = INF
 * 3. Use Floyd–Warshall to relax paths via intermediate nodes
 * 4. For each city:
 *      - Count how many cities have distance ≤ threshold
 * 5. Track the city with:
 *      - Minimum reachable count
 *      - If tie → city with greater index
 *
 * =====================================================================================
 *  TIME & SPACE COMPLEXITY
 * =====================================================================================
 *
 * Time Complexity:
 * ----------------
 * - Floyd–Warshall uses three nested loops → O(n³)
 *
 * Space Complexity:
 * -----------------
 * - Distance matrix of size n × n → O(n²)
 *
 * =====================================================================================
 *  ALTERNATIVE APPROACHES
 * =====================================================================================
 *
 * 1. Dijkstra from Every Node:
 *    - Run Dijkstra from each city.
 *    - Time: O(n * (E log V))
 *    - Better when graph is sparse and n is large.
 *
 * 2. Bellman–Ford (Not Recommended):
 *    - Too slow: O(n² * E)
 *
 * =====================================================================================
 */
public class FindTheCityWithSmallestNeighbors {

    /**
     * Computes the city with the smallest number of reachable neighbors
     * within the given distance threshold.
     */
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {

        // A very large value to represent "infinity"
        // Used to initialize distances where no path exists
        final int INF = (int) 1e9;

        // ---------------- STEP 1: Distance Matrix ----------------
        // dist[i][j] will store the shortest distance from city i to city j
        int[][] dist = new int[n][n];

        // ---------------- STEP 2: Initialization ----------------
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Initially, assume no path exists
                dist[i][j] = INF;
            }
            // Distance from a city to itself is always 0
            dist[i][i] = 0;
        }

        // ---------------- STEP 3: Fill Direct Edges ----------------
        for (int[] edge : edges) {
            int u = edge[0];   // starting city
            int v = edge[1];   // ending city
            int wt = edge[2];  // edge weight

            // Since the graph is bidirectional
            dist[u][v] = wt;
            dist[v][u] = wt;
        }

        // ---------------- STEP 4: Floyd–Warshall ----------------
        // Try improving paths using each city as an intermediate node
        for (int via = 0; via < n; via++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {

                    // If going through 'via' gives a shorter path, update it
                    if (dist[i][via] + dist[via][j] < dist[i][j]) {
                        dist[i][j] = dist[i][via] + dist[via][j];
                    }
                }
            }
        }

        // ---------------- STEP 5: Find Answer ----------------
        int city = -1;                 // Result city
        int minCount = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            int reachableCount = 0;

            for (int j = 0; j < n; j++) {
                // Exclude itself and check distance threshold
                if (i != j && dist[i][j] <= distanceThreshold) {
                    reachableCount++;
                }
            }

            // Choose city with fewer reachable cities
            // If tie → choose city with greater index
            if (reachableCount < minCount ||
                    (reachableCount == minCount && i > city)) {
                minCount = reachableCount;
                city = i;
            }
        }

        return city;
    }

    /**
     * ----------------------------- TESTING (MAIN METHOD) -----------------------------
     */
    public static void main(String[] args) {

        FindTheCityWithSmallestNeighbors solution =
                new FindTheCityWithSmallestNeighbors();

        // Test Case 1
        int n1 = 4;
        int[][] edges1 = {
                {0, 1, 3},
                {1, 2, 1},
                {1, 3, 4},
                {2, 3, 1}
        };
        int threshold1 = 4;
        System.out.println(solution.findTheCity(n1, edges1, threshold1));
        // Expected Output: 3

        // Test Case 2
        int n2 = 5;
        int[][] edges2 = {
                {0, 1, 2},
                {0, 4, 8},
                {1, 2, 3},
                {1, 4, 2},
                {2, 3, 1},
                {3, 4, 1}
        };
        int threshold2 = 2;
        System.out.println(solution.findTheCity(n2, edges2, threshold2));
        // Expected Output: 0
    }
}
