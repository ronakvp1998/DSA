package com.questions.strivers.graph.traversal;

import com.questions.strivers.graph.graphrepresentation.AdjucancyList;

import java.util.ArrayList;
import java.util.List;

public class DFS {

    public static void main(String[] args) {

//        0 --- 1
//        |     |
//        4 --- 2
//        |
//        3
        System.out.println(DfsOfGraph(5, AdjucancyList.createAdjList()));
        //O/P  [0, 1, 4, 2, 3]
    }

    public static List<Integer> DfsOfGraph(int V, ArrayList<ArrayList<Integer>> adjList) {
        ArrayList<Integer> ls = new ArrayList<>();
        boolean vis[] = new boolean[V+1];
        vis[0] = true;
        dfs(0,vis,adjList,ls);
        return ls;
    }

    public static void dfs(int node,boolean vis[], ArrayList<ArrayList<Integer>> adj,ArrayList<Integer>ls){
        // making current node as visited and added into the list
        vis[node] = true;
        ls.add(node);
        for(Integer it : adj.get(node)){
            if(vis[it] == false){
                dfs(it,vis,adj,ls);
            }
        }
    }
}
