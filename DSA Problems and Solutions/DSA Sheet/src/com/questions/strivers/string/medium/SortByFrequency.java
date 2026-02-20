package com.questions.strivers.string.medium;
import java.util.*;

/**
 * ==================================================================================================
 * APPROACH: HashMap + Max-Heap
 * ==================================================================================================
 * 1. Count frequencies of each character.
 * 2. Use a Max-Heap to store characters based on their frequency.
 * 3. Extract from the heap to build the final string.
 * ==================================================================================================
 */
public class SortByFrequency {

    public static String frequencySort(String s) {
        // Step 1: Count frequency of each character
        Map<Character, Integer> counts = new HashMap<>();
        for (char c : s.toCharArray()) {
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }

        // Step 2: Use Max-Heap to sort characters by frequency
        // b - a gives descending order
        PriorityQueue<Character> maxHeap = new PriorityQueue<>(
                (a, b) -> counts.get(b) - counts.get(a)
        );
        maxHeap.addAll(counts.keySet());

        // Step 3: Rebuild the string
        StringBuilder sb = new StringBuilder();
        while (!maxHeap.isEmpty()) {
            char c = maxHeap.poll();
            int freq = counts.get(c);
            for (int i = 0; i < freq; i++) {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(frequencySort("tree"));    // Output: "eert" or "eetr"
        System.out.println(frequencySort("cccaaa"));  // Output: "cccaaa" or "aaaccc"
        System.out.println(frequencySort("Aabb"));    // Output: "bbAa" or "bbaA"
    }
}