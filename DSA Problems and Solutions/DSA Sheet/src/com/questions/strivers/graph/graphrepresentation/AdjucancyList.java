package com.questions.strivers.graph.graphrepresentation;

import java.util.ArrayList;

public class AdjucancyList {
    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> adjList = createAdjList();
        // Print adjacency list
        printGraph(adjList);
    }

    public static ArrayList<ArrayList<Integer>> createAdjList(){
        int V = 5; // Number of vertices
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();

        // Initialize adjacency list
        for (int i = 0; i < V; i++) {
            adjList.add(new ArrayList<>());
        }

//        0 --- 1
//        |     |
//        4 --- 2
//        |
//        3


        // Add edges (undirected graph)
        addEdge(adjList, 0, 1);
        addEdge(adjList, 0, 4);
        addEdge(adjList, 1, 2);
        addEdge(adjList, 2, 4);
        addEdge(adjList, 3, 4);
        return adjList;
    }

    private static void addEdge(ArrayList<ArrayList<Integer>> adj, int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u); // Because the graph is undirected
    }

    private static void printGraph(ArrayList<ArrayList<Integer>> adj) {
        for (int i = 0; i < adj.size(); i++) {
            System.out.print(i + " → ");
            for (int node : adj.get(i)) {
                System.out.print(node + " ");
            }
            System.out.println();
        }
    }
}

