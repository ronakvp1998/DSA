package com.questions.practice.graph;

import java.util.LinkedList;
import java.util.Queue;

public class GraphTest{
    static public class Pair{
        int nRow ;
        int nCol;
        int time;
        public Pair(int nRow,int nCol,int time){
            this.nRow = nRow;
            this.nCol = nCol;
            this.time = time;
        }
    }

    private static int rottonOranges(int [][] grid){
        int n = grid.length;
        int m = grid[0].length;
        Queue<Pair> queue = new LinkedList<>();
        int vis[][] = new int[n][m];
        int freshCnt = 0,finalTime=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j] == 2){
                    queue.add(new Pair(i,j,0));
                    vis[i][j] = 2;
                } else if (grid[i][j] == 1) {
                    freshCnt++;
                }
            }
        }
        int delRow[] = {-1,0,1,0};
        int delCol[] = {0,1,0,-1};
        int cnt = 0;
        while (!queue.isEmpty()){
            int row = queue.peek().nRow;
            int col = queue.peek().nCol;
            int time = queue.peek().time;
            queue.poll();
            for(int i=0;i<4;i++){
                int nRow = row + delRow[i];
                int nCol = col + delCol[i];
                if(nRow>=0 && nRow<n && nCol>=0 && nCol<m && vis[nRow][nCol]==0 && grid[nRow][nCol]==1){
                    queue.add(new Pair(nRow,nCol,time+1));
                    vis[nRow][nCol] = 2;
                    cnt++;
                }
            }
            finalTime = Math.max(finalTime,time);
        }
        if(cnt != freshCnt){
            return -1;
        }


        return finalTime;
    }
}
