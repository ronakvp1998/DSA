package com.questions.strivers.graph.problemsbfsdfs;

import java.util.*;

/**
 * ================================================================================================
 *  LeetCode Problem: 126. Word Ladder II
 * ================================================================================================
 *
 * -------------------------------- PROBLEM STATEMENT ---------------------------------------------
 *
 * Given two words `beginWord` and `endWord`, and a dictionary `wordList`,
 * return **all the shortest transformation sequences** from `beginWord`
 * to `endWord`.
 *
 * Rules:
 * - Only **one letter can be changed at a time**
 * - Each transformed word must exist in `wordList`
 * - `beginWord` does NOT need to be in `wordList`
 *
 * A transformation sequence is:
 *   beginWord -> word1 -> word2 -> ... -> endWord
 *
 * Return **all shortest such sequences**.
 * If no transformation is possible, return an empty list.
 *
 * -------------------------------- EXAMPLE -------------------------------------------------------
 *
 * Input:
 *   beginWord = "der"
 *   endWord   = "dfs"
 *   wordList  = ["des", "der", "dfr", "dgt", "dfs"]
 *
 * Output:
 *   ["der dfr dfs"]
 *
 * ================================================================================================
 *  APPROACH USED: Breadth First Search (BFS) with Level Control
 * ================================================================================================
 *
 * - BFS guarantees the **shortest transformation length**.
 * - Unlike Word Ladder I, here we must return **ALL shortest paths**.
 * - Queue stores **entire paths**, not just words.
 * - Words are removed from dictionary **level by level**, not immediately.
 *
 * This ensures:
 * - All shortest sequences are collected
 * - Longer sequences are ignored
 *
 * ================================================================================================
 */

public class WordLadderII {

    /**
     * Finds all shortest transformation sequences from beginWord to endWord.
     *
     * @param beginWord Starting word
     * @param endWord   Target word
     * @param wordList  List of allowed dictionary words
     * @return List of all shortest transformation sequences
     */
    public static List<List<String>> findSequences(String beginWord,
                                                   String endWord,
                                                   List<String> wordList) {

        // ----------------------------
        // STEP 1: Dictionary Set
        // ----------------------------
        /*
         * HashSet allows:
         * - O(1) lookup
         * - Easy removal of visited words
         */
        Set<String> st = new HashSet<>(wordList);

        // ----------------------------
        // STEP 2: BFS Queue
        // ----------------------------
        /*
         * Queue stores entire transformation paths.
         * Each element is a list of words.
         */
        Queue<List<String>> q = new LinkedList<>();

        // Start BFS with the beginWord as the first path
        q.offer(new ArrayList<>(Arrays.asList(beginWord)));

        // ----------------------------
        // STEP 3: Level Tracking
        // ----------------------------
        /*
         * usedOnLevel keeps track of words used in the current BFS level.
         * These words are removed from the dictionary ONLY
         * when moving to the next level.
         */
        List<String> usedOnLevel = new ArrayList<>();
        usedOnLevel.add(beginWord);

        int level = 0;

        // ----------------------------
        // STEP 4: Result Storage
        // ----------------------------
        List<List<String>> ans = new ArrayList<>();

        // ----------------------------
        // STEP 5: BFS Traversal
        // ----------------------------
        while (!q.isEmpty()) {

            // Get current transformation sequence
            List<String> vec = q.poll();

            /*
             * If we move to a deeper BFS level:
             * - Increase level
             * - Remove all words used in previous level
             *
             * This is the KEY difference from Word Ladder I
             */
            if (vec.size() > level) {
                level++;
                for (String used : usedOnLevel) {
                    st.remove(used);
                }
                usedOnLevel.clear();
            }

            // Last word in the current path
            String word = vec.get(vec.size() - 1);

            // ----------------------------
            // CHECK IF END WORD REACHED
            // ----------------------------
            if (word.equals(endWord)) {

                /*
                 * First valid shortest sequence found
                 */
                if (ans.isEmpty()) {
                    ans.add(new ArrayList<>(vec));
                }
                /*
                 * Another sequence with same minimum length
                 */
                else if (ans.get(0).size() == vec.size()) {
                    ans.add(new ArrayList<>(vec));
                }
            }

            // ----------------------------
            // TRY ALL ONE-LETTER TRANSFORMATIONS
            // ----------------------------
            char[] wordArr = word.toCharArray();

            for (int i = 0; i < wordArr.length; i++) {

                char original = wordArr[i];

                // Try all lowercase letters
                for (char c = 'a'; c <= 'z'; c++) {
                    wordArr[i] = c;
                    String newWord = new String(wordArr);

                    /*
                     * If newWord exists in dictionary,
                     * it is a valid next step
                     */
                    if (st.contains(newWord)) {

                        vec.add(newWord);
                        q.offer(new ArrayList<>(vec));

                        // Mark word as used in this level
                        usedOnLevel.add(newWord);

                        // Backtrack
                        vec.remove(vec.size() - 1);
                    }
                }

                // Restore original character
                wordArr[i] = original;
            }
        }

        return ans;
    }

    /**
     * ---------------------------------- MAIN METHOD ----------------------------------
     * Used for local testing.
     */
    public static void main(String[] args) {

        List<String> wordList =
                Arrays.asList("des", "der", "dfr", "dgt", "dfs");

        String startWord = "der";
        String targetWord = "dfs";

        List<List<String>> ans =
                findSequences(startWord, targetWord, new ArrayList<>(wordList));

        if (ans.isEmpty()) {
            System.out.println(-1);
        } else {

            // Sort for consistent output
            ans.sort((a, b) ->
                    String.join("", a).compareTo(String.join("", b)));

            for (List<String> sequence : ans) {
                System.out.println(String.join(" ", sequence));
            }
        }
    }
}
