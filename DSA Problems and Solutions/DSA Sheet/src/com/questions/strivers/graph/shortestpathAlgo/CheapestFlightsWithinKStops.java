package com.questions.strivers.graph.shortestpathAlgo;

import java.util.*;

/**
 * =================================================================================================
 *  LeetCode 787 - Cheapest Flights Within K Stops
 * =================================================================================================
 *
 * -------------------------------- PROBLEM STATEMENT (LEETCODE) ----------------------------------
 *
 * You are given:
 *  - n cities labeled from 0 to n-1
 *  - an array flights where flights[i] = [from, to, price]
 *    represents a directed flight from city `from` to city `to` with cost `price`
 *  - a source city `src`
 *  - a destination city `dst`
 *  - an integer `K` representing the maximum number of allowed stops
 *
 * A stop means an intermediate city between src and dst.
 * NOTE:
 *   - If you travel from src → dst directly, number of stops = 0
 *   - If you travel src → A → dst, number of stops = 1
 *
 * TASK:
 * Return the cheapest price to travel from src to dst with at most K stops.
 * If no such route exists, return -1.
 *
 * -------------------------------------------------------------------------------------------------
 * EXAMPLE:
 *
 * Input:
 *   n = 4
 *   flights = [[0,1,100],[1,2,100],[2,0,100],[1,3,600],[2,3,200]]
 *   src = 0
 *   dst = 3
 *   K = 1
 *
 * Output:
 *   700
 *
 * Explanation:
 *   The cheapest path with at most 1 stop is:
 *   0 → 1 → 3 with cost = 100 + 600 = 700
 *
 * =================================================================================================
 *
 * INTERVIEW CONTEXT:
 * This problem is a variation of shortest path algorithms with an added constraint
 * on the number of stops. Classic Dijkstra cannot be applied directly without modification.
 *
 * =================================================================================================
 */
public class CheapestFlightsWithinKStops {

    /**
     * =============================================================================================
     * EDGE CLASS
     * =============================================================================================
     *
     * Represents a directed edge in the graph.
     *
     * Why this class?
     * - Improves readability compared to using int[] arrays
     * - Clearly represents a flight from current city → destination city with a cost
     *
     * Fields:
     *  - to   : destination city
     *  - cost : flight price
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
     * =============================================================================================
     * STATE CLASS (USED IN BFS QUEUE)
     * =============================================================================================
     *
     * Represents a traversal state during BFS.
     *
     * Why this class?
     * - BFS needs to track BOTH:
     *     1. Current city
     *     2. Number of stops used so far
     *
     * Fields:
     *  - city  : current city in traversal
     *  - stops : number of stops used to reach this city
     */
    static class State {
        int city;
        int stops;

        State(int city, int stops) {
            this.city = city;
            this.stops = stops;
        }
    }

    /**
     * =============================================================================================
     * MAIN LOGIC: BFS WITH COST PRUNING
     * =============================================================================================
     *
     * APPROACH OVERVIEW:
     * ------------------
     * 1. Convert flights into an adjacency list graph.
     * 2. Use BFS traversal (level-order style) where:
     *      - Each level represents one additional stop.
     * 3. Maintain a distance array to store the minimum cost found so far to each city.
     * 4. Prune paths that:
     *      - Exceed K stops
     *      - Are more expensive than an already known cheaper path
     *
     * WHY BFS?
     * --------
     * - The number of stops is limited (K), which naturally fits BFS (level-based traversal).
     * - BFS ensures we explore paths with fewer stops first.
     *
     * IMPORTANT NOTE (INTERVIEW POINT):
     * --------------------------------
     * This solution uses a SINGLE distance array.
     * This works in many cases but has a limitation:
     *   - A city reached with fewer stops but higher cost might block
     *     a cheaper path that uses more stops later.
     *
     * A more robust solution would track (city, stops) as state in distance,
     * but we are intentionally NOT changing the logic as per requirement.
     */
    private static int cheapestFlight(int n, int[][] flights, int src, int dst, int K) {

        /**
         * ------------------------------- GRAPH CONSTRUCTION ----------------------------------------
         *
         * Graph is represented using an adjacency list:
         *
         * graph[u] = list of all flights going out from city u
         *
         * Time Complexity:
         *   O(n + m), where m = number of flights
         */
        List<List<Edge>> graph = new ArrayList<>();

        // Initialize adjacency list for each city
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // Populate graph using flight data
        for (int[] flight : flights) {
            int from = flight[0];
            int to = flight[1];
            int price = flight[2];

            graph.get(from).add(new Edge(to, price));
        }

        /**
         * ------------------------------- DISTANCE ARRAY --------------------------------------------
         *
         * dist[i] = minimum cost found so far to reach city i
         *
         * Why initialize with MAX_VALUE?
         * - Represents that initially all cities are unreachable.
         *
         * dist[src] = 0 because:
         * - Cost to reach source from itself is zero.
         */
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        /**
         * ------------------------------- BFS QUEUE -------------------------------------------------
         *
         * Queue stores State objects:
         *   (currentCity, stopsUsed)
         *
         * BFS guarantees that we process states in increasing order of stops.
         */
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(src, 0));

        /**
         * ------------------------------- BFS TRAVERSAL ---------------------------------------------
         *
         * We continue BFS until:
         * - Queue becomes empty
         * - OR all valid paths up to K stops are explored
         */
        while (!queue.isEmpty()) {

            // Dequeue the current state
            State current = queue.poll();
            int city = current.city;
            int stopsUsed = current.stops;

            /**
             * EDGE CASE:
             * If number of stops already exceeds K,
             * we cannot explore further from this state.
             */
            if (stopsUsed > K) {
                continue;
            }

            /**
             * Explore all outgoing flights from current city
             */
            for (Edge edge : graph.get(city)) {
                int nextCity = edge.to;
                /**
                 * Calculate new cost to reach nextCity via current city
                 */
                int newCost = dist[city] + edge.cost;
                /**
                 * RELAXATION STEP:
                 * ----------------
                 * Update distance only if:
                 *   - This path gives a cheaper cost to reach nextCity
                 *
                 * Why this check?
                 * - Prevents unnecessary exploration of more expensive paths
                 * - Acts as pruning to improve performance
                 */
                if (newCost < dist[nextCity]) {
                    dist[nextCity] = newCost;
                    /**
                     * Push next state into queue with incremented stop count
                     */
                    queue.offer(new State(nextCity, stopsUsed + 1));
                }
            }
        }
        /**
         * FINAL RESULT:
         * -------------
         * If destination was never reached, dist[dst] will still be MAX_VALUE.
         */
        return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
    }
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

        int result = cheapestFlight(n, flights, src, dst, K);

        System.out.println("Cheapest Price: " + result); // Expected Output: 700
    }
}

/**
 * =================================================================================================
 * TIME AND SPACE COMPLEXITY ANALYSIS
 * =================================================================================================
 *
 * Let:
 *  - n = number of cities
 *  - m = number of flights
 *  - K = maximum allowed stops
 *
 * TIME COMPLEXITY:
 * ----------------
 * O(K * m)
 *
 * Explanation:
 * - BFS can process each edge at most K times (once per stop level).
 * - In worst case, all edges are explored for each stop.
 *
 * SPACE COMPLEXITY:
 * -----------------
 * O(n + m)
 *
 * Explanation:
 * - Adjacency list uses O(n + m)
 * - Distance array uses O(n)
 * - BFS queue can hold up to O(n) states
 *
 * =================================================================================================
 * ALTERNATIVE RECOMMENDED APPROACHES (INTERVIEW DISCUSSION)
 * =================================================================================================
 *
 * 1. Bellman-Ford Algorithm (MOST RECOMMENDED)
 * --------------------------------------------
 * - Run relaxation for K+1 iterations.
 * - Guarantees correctness for this problem.
 *
 * Time:  O(K * m)
 * Space: O(n)
 *
 * PROS:
 * - Simple
 * - Always correct
 *
 * CONS:
 * - Slightly slower in practice
 *
 * 2. Modified Dijkstra (with (city, stops) state)
 * -----------------------------------------------
 * - Use priority queue
 * - Track cost per (city, stops)
 *
 * PROS:
 * - Faster for sparse graphs
 *
 * CONS:
 * - More complex implementation
 *
 * =================================================================================================
 * FINAL INTERVIEW TIP:
 * --------------------
 * Always mention:
 * - Why simple Dijkstra fails
 * - Why stop constraint changes the problem
 * - Trade-offs between BFS, Bellman-Ford, and Dijkstra
 *
 * =================================================================================================
 */
