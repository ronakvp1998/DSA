package com.questions.strivers.graph.shortestpathAlgo;

import java.util.*;

/**
 * =====================================================================================
 *  LeetCode 787 - Cheapest Flights Within K Stops
 * =====================================================================================
 *
 * ---------------------------------- PROBLEM STATEMENT ----------------------------------
 *
 * You are given:
 *  - `n` cities numbered from 0 to n - 1
 *  - An array `flights`, where flights[i] = [from, to, price]
 *    represents a directed flight from city `from` to city `to`
 *    with cost `price`
 *  - A source city `src`
 *  - A destination city `dst`
 *  - An integer `K` representing the maximum number of allowed stops
 *
 * A stop means an intermediate city, so:
 *  - 0 stops  -> direct flight
 *  - 1 stop   -> src -> x -> dst
 *
 * ---------------------------------- TASK ----------------------------------
 *
 * Return the **cheapest price** to travel from `src` to `dst`
 * using **at most K stops**.
 *
 * If there is no such route, return -1.
 *
 * ---------------------------------- EXAMPLES ----------------------------------
 *
 * Input:
 * n = 4
 * flights = [[0,1,100],[1,2,100],[2,0,100],[1,3,600],[2,3,200]]
 * src = 0, dst = 3, K = 1
 *
 * Output:
 * 700
 *
 * Explanation:
 * 0 -> 1 -> 3 costs 100 + 600 = 700
 *
 * ---------------------------------------------------------------------------------------
 *
 * ---------------------------------- APPROACH ----------------------------------
 *
 * We use a **BFS-based graph traversal with state tracking**.
 *
 * Key idea:
 * - Normal Dijkstra does NOT work directly because:
 *   -> A cheaper path with more stops may be invalid
 *   -> A costlier path with fewer stops may be valid
 *
 * Therefore, we track:
 *  - Current city
 *  - Number of stops used
 *  - Cost so far
 *
 * We maintain a 2D distance array:
 *  dist[city][stops] = minimum cost to reach `city` using exactly `stops`
 *
 * BFS is used because:
 * - Stops are bounded (K is small)
 * - We explore layer-by-layer by number of stops
 *
 * ---------------------------------------------------------------------------------------
 *
 * ---------------------------------- WHY THIS WORKS ----------------------------------
 *
 * - We never exceed K stops
 * - We only relax edges if the new cost is cheaper for the same stop count
 * - This avoids unnecessary recomputation
 * - Ensures correctness under stop constraints
 *
 * ---------------------------------------------------------------------------------------
 *
 * ---------------------------------- EDGE CASES HANDLED ----------------------------------
 *
 * - No possible route → return -1
 * - src == dst → cost is 0
 * - Multiple paths with different stop counts
 * - Cycles in graph
 *
 * =====================================================================================
 */
public class CheapestFlightsWithinKStops {

    /**
     * ---------------- EDGE CLASS ----------------
     *
     * Represents a directed flight:
     *  - `to`   → destination city
     *  - `cost` → flight price
     */
    static class Edge {
        int to;
        int cost;

        Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    /**
     * ---------------- STATE CLASS ----------------
     *
     * Represents a BFS traversal state:
     *  - `city`  → current city
     *  - `stops` → number of stops used so far
     *  - `cost`  → total cost to reach this city
     */
    static class State {
        int city;
        int stops;
        int cost;

        State(int city, int stops, int cost) {
            this.city = city;
            this.stops = stops;
            this.cost = cost;
        }
    }

    /**
     * Finds the cheapest price from src to dst with at most K stops
     */
    public static int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {

        /* ---------------- GRAPH CONSTRUCTION ----------------
         *
         * Using adjacency list:
         * graph[u] contains all outgoing flights from city u
         */
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // Populate adjacency list
        for (int[] f : flights) {
            graph.get(f[0]).add(new Edge(f[1], f[2]));
        }

        /* ---------------- DISTANCE MATRIX ----------------
         *
         * dist[city][stops] = minimum cost to reach `city`
         *                    using exactly `stops` stops
         *
         * Size = n x (K + 2)
         * Why K + 2?
         * - K stops means K+1 edges
         */
        int[][] dist = new int[n][K + 2];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }

        // Cost to reach src with 0 stops is 0
        dist[src][0] = 0;

        /* ---------------- BFS QUEUE ---------------- */
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(src, 0, 0));

        /* ---------------- BFS TRAVERSAL ---------------- */
        while (!queue.isEmpty()) {

            State curr = queue.poll();

            int city = curr.city;
            int stops = curr.stops;
            int cost = curr.cost;

            // If stops exceed K, this path is invalid
            if (stops > K) continue;

            // Explore all outgoing flights
            for (Edge edge : graph.get(city)) {

                int nextCity = edge.to;
                int newCost = cost + edge.cost;

                /**
                 * Relaxation condition:
                 * Update only if we find a cheaper cost
                 * for the same number of stops
                 */
                if (newCost < dist[nextCity][stops + 1]) {
                    dist[nextCity][stops + 1] = newCost;
                    queue.offer(new State(nextCity, stops + 1, newCost));
                }
            }
        }

        /* ---------------- FIND FINAL ANSWER ----------------
         *
         * Destination can be reached with:
         * 0 to K+1 edges
         */
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i <= K + 1; i++) {
            ans = Math.min(ans, dist[dst][i]);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /**
     * ---------------- TESTING ----------------
     */
    public static void main(String[] args) {

        int n = 4;
        int src = 0;
        int dst = 3;
        int K = 1;

        int[][] flights = {
                {0, 1, 100},
                {1, 2, 100},
                {2, 0, 100},
                {1, 3, 600},
                {2, 3, 200}
        };

        int result = findCheapestPrice(n, flights, src, dst, K);
        System.out.println("Cheapest Price: " + result); // Expected: 700
    }
}

/**
 * =====================================================================================
 *  TIME & SPACE COMPLEXITY
 * =====================================================================================
 *
 * Time Complexity:
 * O(E * K)
 * - Each edge can be relaxed at most K+1 times
 *
 * Space Complexity:
 * O(N * K + E)
 * - Distance matrix + adjacency list + BFS queue
 *
 * =====================================================================================
 *
 * ---------------------------------- ALTERNATIVE APPROACHES ----------------------------------
 *
 * 1️⃣ Modified Dijkstra (Min-Heap)
 * - State = (cost, city, stops)
 * - Works well when graph is large
 * - Slightly more complex
 *
 * 2️⃣ Bellman-Ford (K+1 iterations)
 * - Relax all edges K+1 times
 * - Simpler but slower for large graphs
 *
 * ---------------------------------- TRADE-OFFS ----------------------------------
 *
 * BFS with state tracking:
 * ✅ Easy to understand
 * ✅ Works well for bounded K
 * ❌ Less optimal for very large graphs
 *
 * =====================================================================================
 */
