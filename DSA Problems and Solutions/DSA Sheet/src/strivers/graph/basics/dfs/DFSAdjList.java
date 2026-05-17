package com.questions.strivers.graph.basics.dfs;
import java.util.*;

public class DFSAdjList {

    private static void dfs(int node, List<List<Integer>> adj, boolean[] vis, List<Integer> dfs) {
        vis[node] = true;
        dfs.add(node);

        // Explore neighbors
        for (int neigh : adj.get(node)) {
            if (!vis[neigh]) {
                dfs(neigh, adj, vis, dfs);
            }
        }
    }

    private static List<Integer> dfsOfGraph(int V, List<List<Integer>> adj) {
        boolean[] vis = new boolean[V];
        List<Integer> dfs = new ArrayList<>();

        dfs(0, adj, vis, dfs);
        return dfs;
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

        System.out.println("DFS (Adjacency List): " + dfsOfGraph(V, adj));
    }
}
