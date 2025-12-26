package com.questions.strivers.graph.toposortproblems;

import java.util.*;

/**
 * ==================================================================================================
 *                                 Alien Dictionary | BFS Topological Sort
 * ==================================================================================================
 *
 * ------------------------------- PROBLEM STATEMENT -----------------------------------------------
 * You are given a sorted dictionary of an alien language having N words.
 * The language uses K distinct characters. Determine the order of characters
 * in the language. Return any valid ordering of characters.
 *
 * Example:
 * Input: dict = ["baa", "abcd", "abca", "cab", "cad"], N=5, K=4
 * Output: One possible order: b d a c
 *
 * --------------------------------------------------------------------------------------------------
 *                                APPROACH — BFS / KAHN'S ALGORITHM
 * --------------------------------------------------------------------------------------------------
 * 🔑 KEY IDEA:
 *  - Treat characters as nodes in a graph.
 *  - Compare adjacent words; for the first differing character between words s1 and s2,
 *    create a directed edge s1[i] -> s2[i], meaning s1[i] comes before s2[i].
 *  - Once the graph is built, perform topological sort using Kahn's Algorithm (BFS).
 *  - Characters with zero indegree are processed first; reduce indegree of neighbors.
 *  - Append characters to result in processing order.
 *
 * COMPLEXITY ANALYSIS:
 *  - Time Complexity : O(N * L + K + E)
 *      - N * L → comparing adjacent words of length up to L.
 *      - K → number of characters.
 *      - E → number of edges in graph for topological sort.
 *  - Space Complexity: O(K + E)
 *      - Adjacency list, indegree array, queue, and result.
 * --------------------------------------------------------------------------------------------------
 */
public class AlienDictionary {

    /**
     * Function to perform BFS-based topological sort (Kahn's Algorithm)
     * @param V   Number of vertices (characters)
     * @param adj Adjacency list representing the graph
     * @return    List of vertices in topologically sorted order
     */
    private static List<Integer> topoSort(int V, List<List<Integer>> adj) {
        int[] indegree = new int[V];

        // Compute indegree of each node
        for (int i = 0; i < V; i++) {
            for (int neighbor : adj.get(i)) {
                indegree[neighbor]++;
            }
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }

        List<Integer> topo = new ArrayList<>();

        // Process nodes with zero indegree
        while (!q.isEmpty()) {
            int node = q.poll();
            topo.add(node);

            for (int neighbor : adj.get(node)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0) {
                    q.offer(neighbor);
                }
            }
        }

        return topo;
    }

    /**
     * Function to find the order of characters in the alien language
     * @param dict Array of words in the alien dictionary
     * @param N    Number of words
     * @param K    Number of unique characters
     * @return     String representing one possible character order
     */
    private static String findOrder(String[] dict, int N, int K) {
        // Initialize adjacency list for K characters
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < K; i++) {
            adj.add(new ArrayList<>());
        }

        // Build the graph: compare adjacent words to create edges
        for (int i = 0; i < N - 1; i++) {
            String s1 = dict[i];
            String s2 = dict[i + 1];
            int len = Math.min(s1.length(), s2.length());

            for (int ptr = 0; ptr < len; ptr++) {
                if (s1.charAt(ptr) != s2.charAt(ptr)) {
                    // s1[ptr] comes before s2[ptr]
                    adj.get(s1.charAt(ptr) - 'a').add(s2.charAt(ptr) - 'a');
                    break; // Only first differing character matters
                }
            }
        }

        // Perform BFS-based topological sort
        List<Integer> topo = topoSort(K, adj);

        // Convert numeric representation back to characters
        StringBuilder ans = new StringBuilder();
        for (int node : topo) {
            ans.append((char)(node + 'a'));
        }

        return ans.toString();
    }

    public static void main(String[] args) {
        int N = 5, K = 4;
        String[] dict = {"baa", "abcd", "abca", "cab", "cad"};

        String ans = findOrder(dict, N, K);

        System.out.print("Character Order: ");
        for (char ch : ans.toCharArray()) {
            System.out.print(ch + " ");
        }
        System.out.println();
    }
}
