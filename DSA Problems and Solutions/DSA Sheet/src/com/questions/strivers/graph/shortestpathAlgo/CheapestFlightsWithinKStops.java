package com.questions.strivers.graph.shortestpathAlgo;

import java.util.*;

// Problem Statement:
// There are n cities and m flights. Each flight is represented as:
// flights[i] = [from, to, price]
// You must return the cheapest cost to travel from src -> dst
// with at most K stops in between. If it's not possible, return -1.
//
// Note:
// A "stop" means an intermediate city. So if K = 1, you are allowed:
// src -> X -> dst  (1 stop)

/*
 Approach:
 ---------
 We are required to minimize cost but we also have a constraint on the
 number of stops. This means normal Dijkstra won't directly solve it
 (because shortest path might require more than K stops).

 So we use a BFS-like traversal where:
 Queue stores -> {stopsUsed, currentCity, currentCost}

 We additionally maintain a dist[] array to store
 the minimum cost found so far to reach each city.

 During BFS:
 - If stops exceed K → we do not explore further from that node.
 - If we find a cheaper cost route to a node within allowed stops,
   we update dist[] and push it in queue.

 Why BFS?
 --------
 BFS naturally processes level-by-level in terms of stops.
 So it ensures we never exceed K stops.

 Time Complexity:
 ----------------
 O(E) approx, but practically behaves similar to Dijkstra with pruning.

 Space Complexity:
 -----------------
 O(V + E)
*/
public class CheapestFlightsWithinKStops {

    // Method to find the cheapest flight within K stops using BFS
    private static int CheapestFLight(int n, int[][] flights, int src, int dst, int K) {

        // Create adjacency list: graph[u] -> {v, price}
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        // Fill adjacency list
        for (int[] flight : flights) {
            int from = flight[0];
            int to = flight[1];
            int price = flight[2];
            adj.get(from).add(new int[]{to, price});
        }

        // Queue holds -> {stopsUsed, currentNode, totalCostTillNow}
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0, src, 0});   // Start from source with 0 cost and 0 stops

        // Distance array to keep track of minimum cost to reach each node
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        // BFS traversal
        while (!q.isEmpty()) {

            int[] current = q.poll();
            int stops = current[0];  // stops taken so far
            int node = current[1];   // current city
            int cost = current[2];   // total cost so far

            // If we already used more than K stops, skip exploring further
            if (stops > K) continue;

            // Explore all connected flights from current node
            for (int[] next : adj.get(node)) {
                int nextCity = next[0];
                int ticketPrice = next[1];

                // If we can reach next city cheaper within constraint
                if (cost + ticketPrice < dist[nextCity] && stops <= K) {
                    dist[nextCity] = cost + ticketPrice;
                    q.offer(new int[]{stops + 1, nextCity, dist[nextCity]});
                }
            }
        }

        // If destination is not reachable, return -1
        return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
    }

    public static void main(String[] args) {

        int n = 4;
        int src = 0;
        int dst = 3;
        int K = 1;

        // flights[from][to][price]
        int[][] flights = {
                {0, 1, 100},
                {1, 2, 100},
                {2, 0, 100},
                {1, 3, 600},
                {2, 3, 200}
        };

        int ans = CheapestFLight(n, flights, src, dst, K);

        System.out.println(ans);   // Output: 700
    }
}
