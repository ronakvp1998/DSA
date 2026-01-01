package com.questions.practice.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class GraphTest {

    static class Pair{
        int node;
        int weight;
        Pair(int node, int weight){
            this.node = node;
            this.weight = weight;
        }
    }

    private static void topoSort(int node,List<List<Pair>>adj,boolean[]vis,Stack<Integer>stack){
        vis[node] = true;
        for(Pair it : adj.get(node)){
            if(!vis[it.node]){
                topoSort(it.node,adj,vis,stack);
            }
        }
        stack.push(node);
    }

    private static int[] shortestPath(int N, int M, int [][]edges){
        List<List<Pair>>adj = new ArrayList<>();
        for(int i=0;i<N;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<M;i++){
            int u = edges[i][0];
            int v = edges[i][1];
            int wt = edges[i][2];
            adj.get(u).add(new Pair(v,wt));
        }

        boolean[] vis = new boolean[N];
        Stack<Integer>stack = new Stack<>();
        for(int i=0;i<N;i++){
            if(!vis[i]){
                topoSort(i,adj,vis,stack);
            }
        }
        int dist[] = new int[N];
        Arrays.fill(dist,(int)1e9);
        dist[0] = 0;
        while (!stack.isEmpty()){
            int node = stack.pop();
            if(dist[node] != (int)1e9){
                for(Pair it : adj.get(node)){
                    int v = it.node;
                    int wt = it.weight;
                    if(dist[node] + wt < dist[v]){
                        dist[v] = dist[node] + wt;
                    }
                }
            }
        }
        for(int i=0;i<N;i++){
            if(dist[i] == (int)1e9){
                dist[i] = -1;
            }
        }
        return dist;
    }

}
