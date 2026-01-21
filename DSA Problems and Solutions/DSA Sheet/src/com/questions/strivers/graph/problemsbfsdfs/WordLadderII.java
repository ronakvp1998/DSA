package com.questions.strivers.graph.problemsbfsdfs;

import java.util.*;

/**
 * =====================================================================================
 *  LeetCode 126 - Word Ladder II (HARD)
 * =====================================================================================
 *
 * -------------------------------- PROBLEM STATEMENT ----------------------------------
 *
 * You are given two words:
 *   1. beginWord
 *   2. endWord
 *
 * And a dictionary of words called wordList.
 *
 * You need to find **ALL shortest transformation sequences** from beginWord to endWord.
 *
 * A transformation sequence must satisfy:
 * 1. Only one letter can be changed at a time.
 * 2. Each transformed word must exist in the wordList.
 * 3. The sequence must start with beginWord and end with endWord.
 *
 * IMPORTANT:
 * - You must return **ALL possible shortest sequences**, not just one.
 * - If no such sequence exists, return an empty list.
 *
 * -------------------------------- EXAMPLE --------------------------------------------
 *
 * Input:
 * beginWord = "hit"
 * endWord   = "cog"
 * wordList  = ["hot","dot","dog","lot","log","cog"]
 *
 * Output:
 * [
 *   ["hit","hot","dot","dog","cog"],
 *   ["hit","hot","lot","log","cog"]
 * ]
 *
 * -------------------------------- DIFFICULTY -----------------------------------------
 * Hard (Graph + BFS + DFS + Backtracking)
 *
 * =====================================================================================
 * APPROACH OVERVIEW (BFS + DFS)
 * =====================================================================================
 *
 * This problem cannot be solved using plain DFS because:
 * - DFS may go deep into longer paths before finding the shortest one.
 *
 * Instead, we use a **two-phase approach**:
 *
 * -------------------------------- PHASE 1: BFS ---------------------------------------
 * - Use Breadth-First Search to:
 *   1. Find the shortest distance from beginWord to every reachable word.
 *   2. Build a DAG (Directed Acyclic Graph) using a "parent mapping"
 *      to store ALL possible ways a word can be reached with minimum distance.
 *
 * Why BFS?
 * - BFS explores level by level.
 * - The first time we reach a word, we guarantee the shortest distance.
 *
 * -------------------------------- PHASE 2: DFS ---------------------------------------
 * - Use Depth-First Search (Backtracking) from endWord → beginWord.
 * - Use the parent mapping built during BFS.
 * - Generate ALL shortest transformation sequences.
 *
 * Why DFS here?
 * - We already know which paths are shortest.
 * - DFS efficiently enumerates all valid paths.
 *
 * =====================================================================================
 * WHY THIS APPROACH WORKS
 * =====================================================================================
 * - BFS ensures shortest paths only.
 * - Parent mapping preserves multiple shortest paths.
 * - DFS reconstructs all sequences without recomputation.
 *
 * =====================================================================================
 */

public class WordLadderII {

    /**
     * ------------------------------------------------------------------------------
     * MAIN API METHOD
     * ------------------------------------------------------------------------------
     * Returns all shortest transformation sequences from beginWord to endWord.
     */
    public List<List<String>> findLadders(String beginWord,
                                          String endWord,
                                          List<String> wordList) {

        // Final answer list
        List<List<String>> result = new ArrayList<>();

        // Convert wordList to HashSet for O(1) lookup
        Set<String> wordSet = new HashSet<>(wordList);

        /**
         * EDGE CASE:
         * If endWord does NOT exist in the dictionary,
         * no transformation is possible.
         */
        if (!wordSet.contains(endWord)) {
            return result;
        }

        /**
         * distance map:
         * Stores the shortest distance of each word from beginWord.
         *
         * Example:
         * distance["hit"] = 0
         * distance["hot"] = 1
         */
        Map<String, Integer> distance = new HashMap<>();

        /**
         * parents map:
         * For each word, stores ALL previous words
         * from which it can be reached with shortest distance.
         *
         * This allows multiple shortest paths.
         */
        Map<String, List<String>> parents = new HashMap<>();

        // Phase 1: BFS to compute distances and parents
        bfs(beginWord, endWord, wordSet, distance, parents);

        /**
         * If endWord was never reached during BFS,
         * then no valid transformation exists.
         */
        if (!distance.containsKey(endWord)) {
            return result;
        }

        // Path list used during DFS backtracking
        List<String> path = new ArrayList<>();
        path.add(endWord); // Start backtracking from endWord

        // Phase 2: DFS to build all shortest paths
        dfs(endWord, beginWord, parents, path, result);

        return result;
    }

    /**
     * =================================================================================
     * BFS METHOD
     * =================================================================================
     * Purpose:
     * - Compute shortest distance from beginWord to every reachable word.
     * - Build parent relationships for path reconstruction.
     */
    private void bfs(String beginWord,
                     String endWord,
                     Set<String> wordSet,
                     Map<String, Integer> distance,
                     Map<String, List<String>> parents) {

        // Standard BFS queue
        Queue<String> queue = new LinkedList<>();

        // Initialize BFS
        queue.offer(beginWord);
        distance.put(beginWord, 0);

        // BFS traversal
        while (!queue.isEmpty()) {

            String currWord = queue.poll();
            int currDist = distance.get(currWord);

            // Convert word to char array for mutation
            char[] chars = currWord.toCharArray();

            /**
             * Try changing every character position
             */
            for (int i = 0; i < chars.length; i++) {

                char originalChar = chars[i];

                /**
                 * Replace current character with 'a' to 'z'
                 */
                for (char c = 'a'; c <= 'z'; c++) {

                    // Skip same character replacement
                    if (c == originalChar) continue;

                    chars[i] = c;
                    String nextWord = new String(chars);

                    // Skip if word is not in dictionary
                    if (!wordSet.contains(nextWord)) continue;

                    /**
                     * Case 1: First time visiting this word
                     */
                    if (!distance.containsKey(nextWord)) {
                        distance.put(nextWord, currDist + 1);
                        queue.offer(nextWord);

                        parents.putIfAbsent(nextWord, new ArrayList<>());
                        parents.get(nextWord).add(currWord);
                    }

                    /**
                     * Case 2: Another shortest path found
                     * (Same level distance)
                     */
                    else if (distance.get(nextWord) == currDist + 1) {
                        parents.get(nextWord).add(currWord);
                    }
                }

                // Restore original character
                chars[i] = originalChar;
            }
        }
    }

    /**
     * =================================================================================
     * DFS BACKTRACKING METHOD
     * =================================================================================
     * Purpose:
     * - Generate all shortest transformation sequences
     *   using parent relationships from BFS.
     */
    private void dfs(String current,
                     String beginWord,
                     Map<String, List<String>> parents,
                     List<String> path,
                     List<List<String>> result) {

        /**
         * BASE CASE:
         * If we reached beginWord, a valid shortest path is formed.
         */
        if (current.equals(beginWord)) {
            List<String> validPath = new ArrayList<>(path);
            Collections.reverse(validPath); // Reverse because we built it backward
            result.add(validPath);
            return;
        }

        // If no parent exists, stop recursion
        if (!parents.containsKey(current)) return;

        /**
         * Try all possible parents (multiple shortest paths)
         */
        for (String parent : parents.get(current)) {
            path.add(parent);
            dfs(parent, beginWord, parents, path, result);
            path.remove(path.size() - 1); // Backtrack
        }
    }

    /**
     * =================================================================================
     * MAIN METHOD (TESTING)
     * =================================================================================
     */
    public static void main(String[] args) {

        WordLadderII solver = new WordLadderII();

        String begin = "hit";
        String end = "cog";

        List<String> words = Arrays.asList(
                "hot", "dot", "dog", "lot", "log", "cog"
        );

        List<List<String>> ladders = solver.findLadders(begin, end, words);

        for (List<String> path : ladders) {
            System.out.println(path);
        }
    }
}
