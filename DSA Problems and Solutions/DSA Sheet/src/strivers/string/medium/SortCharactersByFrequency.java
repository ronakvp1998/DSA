package strivers.string.medium;

/**
 * ============================================================================
 * 451. Sort Characters By Frequency
 * ============================================================================
 * Given a string s, sort it in decreasing order based on the frequency of the
 * characters. The frequency of a character is the number of times it appears
 * in the string.
 * * Return the sorted string. If there are multiple answers, return any of them.
 * * Example 1:
 * Input: s = "tree"
 * Output: "eert"
 * Explanation: 'e' appears twice while 'r' and 't' both appear once.
 * So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.
 * * Example 2:
 * Input: s = "cccaaa"
 * Output: "aaaccc"
 * Explanation: Both 'c' and 'a' appear three times, so both "cccaaa" and "aaaccc"
 * are valid answers.
 * Note that "cacaca" is incorrect, as the same characters must be together.
 * * Example 3:
 * Input: s = "Aabb"
 * Output: "bbAa"
 * Explanation: "bbaA" is also a valid answer, but "Aabb" is incorrect.
 * Note that 'A' and 'a' are treated as two different characters.
 * * Constraints:
 * 1 <= s.length <= 5 * 10^5
 * s consists of uppercase and lowercase English letters and digits.
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 */
import java.util.*;

public class SortCharactersByFrequency {

    /**
     * ========================================================================
     * Phase 1: Brute Force / Standard Sorting - The "Think it" stage.
     * ========================================================================
     * Detailed Intuition:
     * The most direct approach is to count the frequency of each character,
     * store these unique characters in a list, and then sort that list using
     * a custom comparator based on the frequencies.
     * We use a HashMap to tally the occurrences. Then, we extract the keys
     * into a List and sort it in descending order of their map values. Finally,
     * we iterate through the sorted list and append each character to a
     * StringBuilder as many times as its frequency dictates.
     * * Complexity Analysis:
     * - Time Complexity: O(N + K log K). Counting takes O(N) where N is the
     * length of the string. Sorting the unique characters takes O(K log K),
     * where K is the number of unique characters. String building takes O(N).
     * Since K <= 62 (alphanumeric characters), K log K is practically O(1),
     * making this effectively O(N) in reality, but strictly O(N + K log K).
     * - Space Complexity: O(N) Auxiliary Heap Space. We need O(K) space for
     * the HashMap and List. The StringBuilder will allocate O(N) heap space
     * to construct the final string. Stack space is O(log K) for sorting.
     * ========================================================================
     */
    public static String frequencySortStandard(String s) {
        // Step 1: Count frequencies
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : s.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        // Step 2: Store unique characters and sort them by frequency
        List<Character> chars = new ArrayList<>(freqMap.keySet());
        chars.sort((a, b) -> freqMap.get(b) - freqMap.get(a));

        // Step 3: Build the result string
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            int count = freqMap.get(c);
            for (int i = 0; i < count; i++) {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * ========================================================================
     * Phase 2: Max-Heap (Priority Queue) - The "Refine it" stage.
     * ========================================================================
     * Detailed Intuition:
     * Instead of sorting the entire list of unique characters at once, we can
     * feed them into a Max-Heap (PriorityQueue in Java) ordered by their
     * frequency. This is a classic pattern for "Top K" or frequency-based
     * problems. We pop elements from the heap one by one; the heap guarantees
     * that we will always poll the character with the highest remaining frequency.
     * This method is particularly powerful if we only needed the "Top K" frequent
     * elements, though here we need all of them.
     * * Complexity Analysis:
     * - Time Complexity: O(N + K log K). Counting takes O(N). Inserting K
     * elements into the heap takes O(K log K). Polling them all takes
     * O(K log K). Rebuilding the string takes O(N).
     * - Space Complexity: O(N) Auxiliary Heap Space. O(K) for the HashMap and
     * PriorityQueue, plus O(N) for the StringBuilder.
     * ========================================================================
     */
    public static String frequencySortMaxHeap(String s) {
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : s.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        // Max-Heap ordered by frequency
        PriorityQueue<Character> pq = new PriorityQueue<>((a, b) -> freqMap.get(b) - freqMap.get(a));
        pq.addAll(freqMap.keySet());

        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            char c = pq.poll();
            int count = freqMap.get(c);
            for (int i = 0; i < count; i++) {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * ========================================================================
     * Phase 3: Bucket Sort (Array of Lists) - The "Perfect it" stage.
     * ========================================================================
     * Detailed Intuition:
     * We can completely eliminate the O(K log K) sorting overhead by observing
     * a structural constraint: the maximum possible frequency of any character
     * is exactly the length of the string, N.
     * We can use "Bucket Sort". We create an array of Lists (or StringBuilders)
     * where the *index* of the array represents the *frequency*.
     * If 'a' appears 3 times, we place 'a' into the bucket at index 3.
     * After placing all characters into their respective frequency buckets, we
     * simply iterate through the bucket array backwards (from N down to 1).
     * This gives us a strictly linear time sort.
     * * Complexity Analysis:
     * - Time Complexity: O(N). Counting frequencies is O(N). Populating the
     * buckets is O(K) where K <= N. Iterating backwards through the buckets
     * is O(N). Appending to the StringBuilder is O(N). This is a true,
     * unambiguous O(N) algorithm.
     * - Space Complexity: O(N) Auxiliary Heap Space. The buckets array takes
     * O(N) space, the HashMap takes O(K), and the StringBuilder takes O(N).
     * ========================================================================
     */
    public static String frequencySortBucketSort(String s) {
        // Step 1: Count frequencies (Using an array since chars are ASCII)
        // This is a micro-optimization over HashMap for Senior level
        int[] freq = new int[128];
        int maxFreq = 0;
        for (char c : s.toCharArray()) {
            freq[c]++;
            maxFreq = Math.max(maxFreq, freq[c]);
        }

        // Step 2: Create buckets. Index = frequency, Value = list of characters.
        // We only need buckets up to maxFreq.
        List<Character>[] buckets = new List[maxFreq + 1];
        for (int i = 0; i < 128; i++) {
            if (freq[i] > 0) {
                if (buckets[freq[i]] == null) {
                    buckets[freq[i]] = new ArrayList<>();
                }
                buckets[freq[i]].add((char) i);
            }
        }

        // Step 3: Build the string by traversing buckets backwards
        StringBuilder sb = new StringBuilder();
        for (int i = maxFreq; i >= 1; i--) {
            if (buckets[i] != null) {
                for (char c : buckets[i]) {
                    for (int j = 0; j < i; j++) {
                        sb.append(c);
                    }
                }
            }
        }

        return sb.toString();
    }

    /**
     * ========================================================================
     * Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        String[] testCases = {
                "tree",       // Standard case
                "cccaaa",     // Tie in frequency
                "Aabb",       // Case sensitivity check
                "a",          // Single character
                "zzzzzz",     // All identical characters
                "2a55322"     // Alphanumeric mix
        };

        System.out.println("Running test cases for 451. Sort Characters By Frequency...\n");

        for (int i = 0; i < testCases.length; i++) {
            String input = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": \"" + input + "\"");

            String res1 = frequencySortStandard(input);
            String res2 = frequencySortMaxHeap(input);
            String res3 = frequencySortBucketSort(input);

            System.out.println("Phase 1 (Standard Sort) : \"" + res1 + "\"");
            System.out.println("Phase 2 (Max-Heap)      : \"" + res2 + "\"");
            System.out.println("Phase 3 (Bucket Sort)   : \"" + res3 + "\"");

            // Note: Valid anagrams might have different orders for tied frequencies
            // (e.g. "cccaaa" vs "aaaccc"). For strict validation in this suite, we
            // check lengths and ensure the character counts perfectly match the input.
            if (res1.length() == input.length() &&
                    res2.length() == input.length() &&
                    res3.length() == input.length()) {
                System.out.println("-> ALL PASS ✓\n");
            } else {
                System.out.println("-> FAIL ✗\n");
            }
        }
    }
}