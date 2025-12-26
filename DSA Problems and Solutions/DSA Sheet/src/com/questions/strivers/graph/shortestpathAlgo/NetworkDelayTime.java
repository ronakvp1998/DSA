package com.questions.strivers.graph.shortestpathAlgo;

import java.util.*;

// Problem Statement:
// You are given a directed weighted graph with `n` nodes labeled 1 to n.
// Each entry in `times[i] = {ui, vi, wi}` represents a directed edge from ui → vi
// taking wi units of time.
// A signal is sent from node `k` at time 0 and travels through edges.
// When a node receives the signal, it immediately forwards it further.
// Return the minimum time required for ALL nodes to receive the signal.
// If any node cannot be reached, return -1.

public class NetworkDelayTime {

    // Function to compute minimum time required to send signal to all nodes
    private static int networkDelayTime(int[][] times, int n, int k) {

        // ----------------------------
        // Step 1: Build Adjacency List
        // ----------------------------
        // adj[u] -> list of {v, w} meaning edge u -> v with cost w
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] time : times) {
            int u = time[0];
            int v = time[1];
            int w = time[2];
            adj.get(u).add(new int[]{v, w});
        }

        // ---------------------------------------
        // Step 2: Min Heap for Dijkstra Algorithm
        // ---------------------------------------
        // Stores {time_taken, node}
        PriorityQueue<int[]> pq =
                new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        pq.offer(new int[]{0, k});  // Start from source node k with time 0

        // ---------------------------------------
        // Step 3: Distance Array
        // ---------------------------------------
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;

        // ---------------------------------------
        // Step 4: Dijkstra's Algorithm
        // ---------------------------------------
        while (!pq.isEmpty()) {

            // Extract node with minimum signal time
            int[] curr = pq.poll();
            int time = curr[0];
            int node = curr[1];

            // Traverse neighbors
            for (int[] nbr : adj.get(node)) {
                int nextNode = nbr[0];
                int weight = nbr[1];

                // Relaxation: shorter path found
                if (dist[nextNode] > time + weight) {
                    dist[nextNode] = time + weight;
                    pq.offer(new int[]{dist[nextNode], nextNode});
                }
            }
        }

        // ---------------------------------------
        // Step 5: Determine Final Answer
        // ---------------------------------------
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            // If any node is unreached → return -1
            if (dist[i] == Integer.MAX_VALUE) return -1;
            ans = Math.max(ans, dist[i]);   // Longest time among all nodes
        }

        return ans;
    }

    public static void main(String[] args) {
        int[][] times = {
                {2, 1, 1},
                {2, 3, 1},
                {3, 4, 1}
        };
        int n = 4;
        int k = 2;

        System.out.println(networkDelayTime(times, n, k));
    }
}
