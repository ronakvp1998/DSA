package com.questions.strivers.graph.basics.bfs;

import java.util.*;

public class BFSAdjList {

    private static List<Integer> bfs(int V, List<List<Integer>> adj) {
        boolean[] vis = new boolean[V];
        Queue<Integer> q = new LinkedList<>();
        List<Integer> bfs = new ArrayList<>();

        q.offer(0);
        vis[0] = true;

        while (!q.isEmpty()) {
            int node = q.poll();
            bfs.add(node);

            for (int neigh : adj.get(node)) {
                if (!vis[neigh]) {
                    vis[neigh] = true;
                    q.offer(neigh);
                }
            }
        }
        return bfs;
    }

    public static void main(String[] args) {
        int V = 5;
        List<List<Integer>> adj = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        // Undirected graph
        adj.get(0).add(1);
        adj.get(1).add(0);

        adj.get(0).add(2);
        adj.get(2).add(0);

        adj.get(1).add(3);
        adj.get(3).add(1);

        adj.get(2).add(4);
        adj.get(4).add(2);

        System.out.println("BFS (Adjacency List): " + bfs(V, adj));
    }
}
