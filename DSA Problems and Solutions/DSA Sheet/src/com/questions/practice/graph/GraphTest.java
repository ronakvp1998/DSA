package com.questions.practice.graph;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class GraphTest{

    static class Pair{
        int effort;
        int row;
        int col;
        Pair(int effort,int row,int col){
            this.effort = effort;
            this.row = row;
            this.col = col;
        }
    }
    private static int MinimumEffort(int[][] heights) {
        int n = heights.length;
        int m = heights[0].length;
        int dist[][] = new int[n][m];
        for(int[] it : dist){
            Arrays.fill(it,Integer.MAX_VALUE);
        }
        dist[0][0] = 0;
        PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.effort));
        pq.add(new Pair(0,0,0));
        int delRow[] = {-1,0,1,0};
        int delCol[] = {0,1,0,-1};
        while (!pq.isEmpty()){
            Pair node = pq.poll();
            int effort = node.effort;
            int row = node.row;
            int col = node.col;
            if(row == n-1 && col == m-1){
                return effort;
            }
            for(int i=0;i<4;i++){
                int r = row + delRow[i];
                int c = col + delCol[i];
                if(r >= 0 && r < n && c >= 0 && c < m){
                    int newEffort = Math.max(effort,Math.abs(heights[row][col] - heights[r][c]));
                    if(newEffort < dist[r][c]){
                        dist[r][c] = newEffort;
                        pq.add(new Pair(newEffort,r,c));
                    }
                }
            }
        }
        return 0;
    }


}
