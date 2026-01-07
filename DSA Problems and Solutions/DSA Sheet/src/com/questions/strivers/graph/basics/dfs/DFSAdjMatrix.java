package com.questions.strivers.graph.basics.dfs;

import java.util.*;

public class DFSAdjMatrix {

    private static void dfs(int node, int[][] graph, boolean[] vis, List<Integer> dfs) {
        vis[node] = true;
        dfs.add(node);

        // Explore all adjacent nodes
        for (int adj = 0; adj < graph.length; adj++) {
            if (graph[node][adj] == 1 && !vis[adj]) {
                dfs(adj, graph, vis, dfs);
            }
        }
    }

    private static List<Integer> dfsOfGraph(int[][] graph) {
        int n = graph.length;
        boolean[] vis = new boolean[n];
        List<Integer> dfs = new ArrayList<>();

        dfs(0, graph, vis, dfs);
        return dfs;
    }

    public static void main(String[] args) {
        int[][] graph = {
                {0, 1, 1, 0},
                {1, 0, 0, 1},
                {1, 0, 0, 1},
                {0, 1, 1, 0}
        };

        System.out.println("DFS (Adjacency Matrix): " + dfsOfGraph(graph));
    }
}
