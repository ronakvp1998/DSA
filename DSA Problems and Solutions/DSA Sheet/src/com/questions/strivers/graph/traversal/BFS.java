package com.questions.strivers.graph.traversal;

import com.questions.strivers.graph.graphrepresentation.AdjucancyList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {
    public static void main(String[] args){
//        0 --- 1
//        |     |
//        4 --- 2
//        |
//        3
        System.out.println(bfsOfGraph(5, AdjucancyList.createAdjList()));
        //O/P  [0, 1, 4, 2, 3]
    }

    public static ArrayList<Integer> bfsOfGraph(int V, ArrayList<ArrayList<Integer>> adj){
        ArrayList<Integer> bfs = new ArrayList<>();
        boolean vis[] = new boolean[V];
        Queue<Integer> q = new LinkedList<>();
        // add starting node into the queue mark it as visited
        q.add(0);
        vis[0] = true;
        while (!q.isEmpty()){
            // poll from queue and print/add into list
            Integer node = q.poll();
            bfs.add(node);
            // get all the connected nodes and add them into the queue
            for(Integer it : adj.get(node)){
                if(vis[it] == false){
                    vis[it] = true;
                    q.add(it);
                }
            }
        }
        return bfs;
    }
}
