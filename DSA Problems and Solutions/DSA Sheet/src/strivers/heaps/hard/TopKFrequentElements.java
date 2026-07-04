package strivers.heaps.hard;

/**
 * ==================================================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ==================================================================================================
 * PROBLEM STATEMENT: 347. Top K Frequent Elements
 * --------------------------------------------------------------------------------------------------
 * Given an integer array nums and an integer k, return the k most frequent elements.
 * You may return the answer in any order.
 * * EXAMPLES:
 * Example 1:
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output: [1,2]
 * * Example 2:
 * Input: nums = [1], k = 1
 * Output: [1]
 * * Example 3:
 * Input: nums = [1,2,1,2,1,2,3,1,3,2], k = 2
 * Output: [1,2]
 * * CONSTRAINTS:
 * - 1 <= nums.length <= 10^5
 * - -10^4 <= nums[i] <= 10^4
 * - k is in the range [1, the number of unique elements in the array].
 * - It is guaranteed that the answer is unique.
 * * FOLLOW UP: Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
 * * NOTE: As this is an Array/Hashing/Sorting problem and not a Dynamic Programming problem,
 * DP concepts like Recursion Trees and Tabulation states are omitted in favor of the Non-DP
 * Progressive Roadmap.
 * ==================================================================================================
 */

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TopKFrequentElements {


    /**
     * ==============================================================================================
     * Phase 3: Alternative Approach 1 - Min-Heap / PriorityQueue
     * ==============================================================================================
     * Detailed Intuition:
     * Instead of sorting ALL elements, we only care about the top K. We can maintain a Min-Heap
     * (PriorityQueue) of size K. As we iterate through the frequency map entries, we add them to
     * the heap. If the heap size exceeds K, we poll (remove) the smallest element. What's left in
     * the heap at the end will be exactly the K elements with the highest frequencies.
     * * Complexity Analysis:
     * - Time Complexity: O(N log K). Building the map takes O(N). Inserting into a heap of size K
     * takes O(log K), and we do this for at most N unique elements. O(N + N log K) = O(N log K).
     * This strictly satisfies the "better than O(N log N)" constraint.
     * - Space Complexity: O(N + K). The frequency map takes O(N) heap space, and the PriorityQueue
     * takes O(K) heap space. Total space is bounded by O(N).
     */
    public static int[] topKFrequentMinHeap(int[] nums, int k) {
        // map<element,freq>
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        // Min-Heap based on frequency
        PriorityQueue<Map.Entry<Integer, Integer>> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            minHeap.offer(entry);
            if (minHeap.size() > k) {
                minHeap.poll(); // Evict the least frequent element
            }
        }

        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            // Extract from heap (gives smallest first, so populate backwards for sorted order)
            result[i] = minHeap.poll().getKey();
        }

        return result;
    }

    /**
     * ==============================================================================================
     * Phase 1: Optimal Approach - Bucket Sort (The Recommended Approach)
     * ==============================================================================================
     * Detailed Intuition:
     * To strictly beat O(N log N) time complexity, we cannot use standard sorting. Since the maximum
     * frequency of any element in an array of size N is exactly N, we can use a Bucket Sort approach.
     * We create an array of Lists (buckets) where the index represents the frequency. After mapping
     * the frequencies, we place each unique number into the bucket corresponding to its frequency.
     * Finally, we traverse the buckets from right (highest frequency) to left (lowest frequency) to
     * gather the top K elements.
     * * Complexity Analysis:
     * - Time Complexity: O(N). We iterate through the array to build the frequency map O(N), iterate
     * through the map to populate the buckets O(U) where U is unique elements, and iterate backwards
     * through the buckets O(N). Total Time = O(N).
     * - Space Complexity: O(N). The frequency map takes O(N) heap space, and the buckets array of
     * lists also takes O(N) heap space in the worst case. Auxiliary stack space is O(1).
     */
    @SuppressWarnings("unchecked")
    public static int[] topKFrequentOptimal(int[] nums, int k) {
        // 1. Build Frequency Map
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        // 2. Create buckets where index = frequency
        // Size is nums.length + 1 because the max frequency can be nums.length
        List<Integer>[] buckets = new List[nums.length + 1];
        for (int key : freqMap.keySet()) {
            int frequency = freqMap.get(key);
            if (buckets[frequency] == null) {
                buckets[frequency] = new ArrayList<>();
            }
            buckets[frequency].add(key);
        }

        // 3. Gather the top k elements by iterating from the end (highest frequency)
        int[] result = new int[k];
        int counter = 0;

        for (int i = buckets.length - 1; i >= 0 && counter < k; i--) {
            if (buckets[i] != null) {
                for (int num : buckets[i]) {
                    result[counter++] = num;
                    if (counter == k) {
                        return result; // Reached exactly k elements
                    }
                }
            }
        }

        return result;
    }

    /**
     * ==============================================================================================
     * Phase 2: Brute Force Approach - Sorting a Map (The "Think it" stage)
     * ==============================================================================================
     * Detailed Intuition:
     * The most straightforward way to solve this is to count the occurrences of every element using
     * a HashMap, load the map's entries into a List, and sort that List in descending order based
     * on the map values (frequencies). Then, we simply pluck the first K elements from the sorted List.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N). Creating the map takes O(N). Sorting the list of unique elements
     * takes O(U log U) where U is the number of unique elements. In the worst case (e.g., all unique),
     * this is bounded by O(N log N).
     * - Space Complexity: O(N). O(N) heap space to store the frequency map and the List representation
     * for sorting. Auxiliary stack space is O(1).
     */
    public static int[] topKFrequentBruteForce(int[] nums, int k) {
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        List<Map.Entry<Integer, Integer>> entryList = new ArrayList<>(freqMap.entrySet());
        // Sort descending by value (frequency)
        entryList.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = entryList.get(i).getKey();
        }

        return result;
    }

    /**
     * ==============================================================================================
     * Phase 4: Alternative Approach 2 - Java 8 Stream API
     * ==============================================================================================
     * Detailed Intuition:
     * This relies on the exact same logical pipeline as the Brute Force (Sorting) approach but utilizes
     * Java 8 Streams to achieve a declarative, concise one-liner (spread for readability). It boxes
     * primitives, groups by identity to count occurrences, sorts entries descending, limits to K,
     * and maps back to an unboxed integer array.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N). Underlying stream sorting operates similarly to the brute force method.
     * - Space Complexity: O(N). Maps and stream internal state require O(N) auxiliary space. High overhead.
     */
    public static int[] topKFrequentStreamAPI(int[] nums, int k) {
        return Arrays.stream(nums)
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .limit(k)
                .mapToInt(Map.Entry::getKey)
                .toArray();
    }

    /**
     * ==============================================================================================
     * 4. TESTING SUITE
     * ==============================================================================================
     * Main method to thoroughly test all approaches against standard and edge cases.
     */
    public static void main(String[] args) {
        // Test Cases Setup
        class TestCase {
            int[] nums;
            int k;
            String description;

            TestCase(int[] nums, int k, String description) {
                this.nums = nums;
                this.k = k;
                this.description = description;
            }
        }

        TestCase[] testCases = {
                new TestCase(new int[]{1, 1, 1, 2, 2, 3}, 2, "Standard Case 1"),
                new TestCase(new int[]{1}, 1, "Standard Case 2 (Single Element)"),
                new TestCase(new int[]{1, 2, 1, 2, 1, 2, 3, 1, 3, 2}, 2, "Standard Case 3 (Multiple duplicates)"),
                new TestCase(new int[]{-1, -1, -2}, 1, "Edge Case: Negative Numbers"),
                new TestCase(new int[]{4, 4, 4, 4, 4}, 1, "Edge Case: All Identical"),
                new TestCase(new int[]{1, 2, 3, 4, 5}, 3, "Edge Case: Flat Frequencies")
        };

        System.out.println("==========================================================");
        System.out.println("Running Testing Suite: 347. Top K Frequent Elements");
        System.out.println("==========================================================");

        for (int i = 0; i < testCases.length; i++) {
            TestCase tc = testCases[i];
            System.out.println("\nTest Case " + (i + 1) + ": " + tc.description);
            System.out.println("Nums: " + Arrays.toString(tc.nums) + " | k = " + tc.k);

            // Execute all phases
            int[] optimalResult = topKFrequentOptimal(tc.nums, tc.k);
            int[] bruteResult   = topKFrequentBruteForce(tc.nums, tc.k);
            int[] heapResult    = topKFrequentMinHeap(tc.nums, tc.k);
            int[] streamResult  = topKFrequentStreamAPI(tc.nums, tc.k);

            // Because order doesn't matter according to the problem, we use Sets to assert equality
            Set<Integer> optSet = Arrays.stream(optimalResult).boxed().collect(Collectors.toSet());
            Set<Integer> bruteSet = Arrays.stream(bruteResult).boxed().collect(Collectors.toSet());
            Set<Integer> heapSet = Arrays.stream(heapResult).boxed().collect(Collectors.toSet());
            Set<Integer> streamSet = Arrays.stream(streamResult).boxed().collect(Collectors.toSet());

            // Display Results
            System.out.println("  -> Phase 1 (Optimal/Bucket) : " + Arrays.toString(optimalResult));
            System.out.println("  -> Phase 2 (Brute Force)    : " + Arrays.toString(bruteResult));
            System.out.println("  -> Phase 3 (Min-Heap)       : " + Arrays.toString(heapResult));
            System.out.println("  -> Phase 4 (Stream API)     : " + Arrays.toString(streamResult));

            // Assert Correctness
            boolean isCorrect = optSet.equals(bruteSet) &&
                    bruteSet.equals(heapSet) &&
                    heapSet.equals(streamSet);
            System.out.println("  -> Consistency Check        : " + (isCorrect ? "PASS ✅" : "FAIL ❌"));
        }
    }
}