package com.questions.strivers.graph.otheralgo;

import java.util.*;

public class ArticulationPointInGraph {

    private int timer = 1;

    // DFS function to identify articulation points
    private void dfs(int node, int parent, int[] vis, int[] tin, int[] low, int[] mark, List<List<Integer>> adj) {
        vis[node] = 1; //Make visited node as 1
        tin[node] = low[node] = timer++;
        int child = 0;

        for (int neighbor : adj.get(node)) {
            if (neighbor == parent) continue;  //If neighbour==parent then continue

            if (vis[neighbor] == 0) {
                dfs(neighbor, node, vis, tin, low, mark, adj); //if vis[neighbour]==0 then call dfs function
                low[node] = Math.min(low[node], low[neighbor]);

                // Articulation condition
                if (low[neighbor] >= tin[node] && parent != -1) {
                    mark[node] = 1;
                }

                child++;//Increment child by 1
            } else {
                // back edge
                low[node] = Math.min(low[node], tin[neighbor]);
            }
        }

        if (parent == -1 && child > 1) {
            mark[node] = 1;
        }
    }

    public List<Integer> articulationPoints(int n, List<List<Integer>> adj) {
        int[] vis = new int[n], tin = new int[n], low = new int[n], mark = new int[n];
        List<Integer> ans = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (vis[i] == 0) {
                dfs(i, -1, vis, tin, low, mark, adj);
            }
        }

        for (int i = 0; i < n; i++) {
            if (mark[i] == 1) ans.add(i);
        }

        if (ans.isEmpty()) ans.add(-1);
        return ans;  //if ans is empty add -1 else return ans
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] edges = {
                {0, 1}, {1, 4}, {2, 4}, {2, 3}, {3, 4}
        };

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] e : edges) {
            adj.get(e[0]).add(e[1]);
            adj.get(e[1]).add(e[0]);
        }

        ArticulationPointInGraph sol = new ArticulationPointInGraph();
        List<Integer> res = sol.articulationPoints(n, adj);
        for (int x : res) System.out.print(x + " ");
    }
}

