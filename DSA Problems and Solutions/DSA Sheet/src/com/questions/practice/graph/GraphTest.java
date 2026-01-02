package com.questions.practice.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class GraphTest {
    static class Pair{
        int v;
        int wt;
        Pair(int v,int wt){
            this.v = v;
            this.wt = wt;
        }
    }
    private static int[] dijkstra(int V, ArrayList<ArrayList<Pair>>adj,int s){
        PriorityQueue<Pair>pq = new PriorityQueue<>((a,b) -> a.wt - b.wt);
        int [] dist = new int[V];
        Arrays.fill(dist,Integer.MAX_VALUE);
        dist[s] = 0;
        pq.offer(new Pair(s,0));
        while (!pq.isEmpty()){
            Pair curr = pq.poll();
            int v = curr.v;
            int dis = curr.wt;
            for(Pair it : adj.get(v)){
                int adjNode = it.v;
                int wt = it.wt;
                if(dis + wt < dist[adjNode]){
                    dist[adjNode] = dis + wt;
                    pq.offer(new Pair(adjNode,dist[adjNode]));
                }
            }
        }
        return dist;
    }

}
