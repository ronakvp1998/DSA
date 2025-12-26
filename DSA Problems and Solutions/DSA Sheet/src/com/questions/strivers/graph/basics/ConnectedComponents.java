package com.questions.strivers.graph.basics;

import java.util.*;

public class ConnectedComponents {

    public static List<List<Integer>> connectedComponents(int V, List<List<Integer>> adj) {
        boolean[] visited = new boolean[V];      // visited array
        List<List<Integer>> components = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                List<Integer> component = new ArrayList<>();
                dfs(i, adj, visited, component);
                components.add(component);
            }
        }
        return components;
    }

    private static void dfs(int node, List<List<Integer>> adj,
                            boolean[] visited, List<Integer> component) {

        visited[node] = true;         // mark visited
        component.add(node);

        for (int neighbor : adj.get(node)) {
            if (!visited[neighbor]) {
                dfs(neighbor, adj, visited, component);
            }
        }
    }

    public static void main(String[] args) {

        int V = 7;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());

        // Undirected graph edges
        adj.get(0).add(1);
        adj.get(1).add(0);

        adj.get(2).add(3);
        adj.get(3).add(2);

        adj.get(4).add(5);
        adj.get(5).add(4);
        adj.get(5).add(6);
        adj.get(6).add(5);

        System.out.println(connectedComponents(V, adj));
    }
}
