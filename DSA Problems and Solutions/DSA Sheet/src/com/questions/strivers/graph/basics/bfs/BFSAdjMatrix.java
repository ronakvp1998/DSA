package com.questions.strivers.graph.basics.bfs;

import java.util.*;

public class BFSAdjMatrix {

    private static List<Integer> bfs(int[][] graph) {
        int n = graph.length;
        boolean[] vis = new boolean[n];
        Queue<Integer> q = new LinkedList<>();
        List<Integer> bfs = new ArrayList<>();

        q.offer(0);
        vis[0] = true;

        while (!q.isEmpty()) {
            int node = q.poll();
            bfs.add(node);

            for (int adj = 0; adj < n; adj++) {
                if (graph[node][adj] == 1 && !vis[adj]) {
                    vis[adj] = true;
                    q.offer(adj);
                }
            }
        }
        return bfs;
    }

    public static void main(String[] args) {
        int[][] graph = {
                {0, 1, 1, 0},
                {1, 0, 0, 1},
                {1, 0, 0, 1},
                {0, 1, 1, 0}
        };

        System.out.println("BFS (Adjacency Matrix): " + bfs(graph));
    }
}
