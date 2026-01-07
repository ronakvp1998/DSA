package com.questions.practice.graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class GraphTest {


    private static boolean checkBipartitle(int [][] graph) {
        int n = graph.length;
        int[] color = new int[n];
        Arrays.fill(color, -1);
        for (int i = 0; i < n; i++) {
            if (color[i] == -1) {
                if (!checkColor(i, n, graph, color)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkColor(int start,int V,int [][]graph,int color[]){
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        color[start] = 0;
        while (!queue.isEmpty()){
            int node = queue.poll();
            for(int i=0;i<graph[node].length;i++){
                if(color[graph[node][i]] == -1){
                    color[graph[node][i]] = 1 - color[node];
                    queue.add(graph[node][i]);
                } else if (color[graph[node][i]] == color[node]) {
                    return false;
                }
            }
        }
        return true;
    }
}
