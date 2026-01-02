package com.questions.strivers.graph.problemsbfsdfs;

import java.util.*;

/**
 * ================================================================================================
 *  LeetCode Problem: 127. Word Ladder
 * ================================================================================================
 *
 * -------------------------------- PROBLEM STATEMENT ---------------------------------------------
 *
 * A transformation sequence from word `beginWord` to word `endWord` is a sequence of words:
 *
 *   beginWord -> word1 -> word2 -> ... -> endWord
 *
 * Such that:
 * - Only **one letter can be changed at a time**
 * - Each transformed word must exist in the given `wordList`
 * - beginWord does NOT need to be in the wordList
 *
 * Return the **length of the shortest transformation sequence** from `beginWord`
 * to `endWord`.
 *
 * If no such transformation sequence exists, return **0**.
 *
 * -------------------------------- EXAMPLE -------------------------------------------------------
 *
 * Input:
 *   beginWord = "der"
 *   endWord   = "dfs"
 *   wordList  = ["des", "der", "dfr", "dgt", "dfs"]
 *
 * Output:
 *   3
 *
 * Explanation:
 *   der → dfr → dfs
 *
 * ================================================================================================
 *  APPROACH USED: Breadth First Search (BFS)
 * ================================================================================================
 *
 * - Each word is treated as a node.
 * - An edge exists between two words if they differ by exactly one character.
 * - All edges have equal weight (1 transformation).
 * - BFS guarantees the shortest path.
 *
 * ================================================================================================
 */

public class WordLadderI {

    /**
     * Computes the length of the shortest transformation sequence.
     *
     * @param startWord Starting word
     * @param targetWord Target word
     * @param wordList List of allowed intermediate words
     * @return Length of shortest transformation sequence, or 0 if not possible
     */
    public static int wordLadderLength(String startWord,
                                       String targetWord,
                                       List<String> wordList) {

        // ----------------------------
        // STEP 1: BFS Queue
        // ----------------------------
        /*
         * Queue stores pairs of:
         *  - current word
         *  - number of transformation steps taken so far
         */
        Queue<Pair<String, Integer>> q = new LinkedList<>();

        // Start BFS with startWord, step count = 1
        q.add(new Pair<>(startWord, 1));

        // ----------------------------
        // STEP 2: Store Words in a Set
        // ----------------------------
        /*
         * HashSet allows O(1) lookup and removal.
         * Acts as both:
         * - dictionary
         * - visited set
         */
        Set<String> st = new HashSet<>(wordList);

        // Remove startWord if present to avoid revisiting
        st.remove(startWord);

        // ----------------------------
        // STEP 3: BFS Traversal
        // ----------------------------
        while (!q.isEmpty()) {

            // Extract front element
            String word = q.peek().getKey();
            int steps = q.peek().getValue();
            q.poll();

            // If target word is reached, return steps immediately
            // BFS ensures this is the shortest sequence
            if (word.equals(targetWord)) {
                return steps;
            }

            /*
             * Try changing each character of the word
             * Example: "der"
             * Change:
             *  d -> a..z
             *  e -> a..z
             *  r -> a..z
             */
            for (int i = 0; i < word.length(); i++) {

                // Convert word to character array
                char[] arr = word.toCharArray();
                char original = arr[i];

                // Try all possible lowercase characters
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    arr[i] = ch;

                    // Create new transformed word
                    String newWord = new String(arr);

                    /*
                     * If newWord exists in dictionary:
                     * - It is a valid transformation
                     * - Add it to BFS queue
                     * - Remove from set to mark as visited
                     */
                    if (st.contains(newWord)) {
                        st.remove(newWord);
                        q.add(new Pair<>(newWord, steps + 1));
                    }
                }

                // Restore original character (backtracking)
                arr[i] = original;
            }
        }

        // If BFS completes and targetWord is never reached
        return 0;
    }

    /**
     * Utility Pair class (since Java doesn't have built-in Pair)
     */
    static class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
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

        System.out.println(
                wordLadderLength(startWord, targetWord, wordList)
        ); // Expected Output: 3
    }
}
