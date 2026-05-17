package com.questions.ctobhaiya.heaps;

/**
 * ============================================================================
 * 🚀 MASTERCLASS: TOP K FREQUENT ELEMENTS (LeetCode #347)
 * ============================================================================
 * * Role: Senior DSA Interviewer and Competitive Programming Evaluator
 * * ----------------------------------------------------------------------------
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement:
 * Given an integer array `nums` and an integer `k`, return the `k` most frequent
 * elements. You may return the answer in any order.
 * * Constraints:
 * - 1 <= nums.length <= 10^5
 * - -10^4 <= nums[i] <= 10^4
 * - k is in the range [1, the number of unique elements in the array].
 * - It is guaranteed that the answer is unique.
 * * Input/Output Formats:
 * Input: int[] nums, int k
 * Output: int[] (containing the top K most frequent elements)
 * * Examples:
 * Example 1:
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output: [1,2]
 * * Example 2:
 * Input: nums = [1], k = 1
 * Output: [1]
 * * Conceptual Visualization (Bucket Sort Strategy for [1,1,1,2,2,3], k=2):
 * Step 1: Frequency Map
 * 1 -> 3
 * 2 -> 2
 * 3 -> 1
 * * Step 2: Bucket Array (Index = Frequency, Value = List of Elements)
 * [0] -> []
 * [1] -> [3]       (Element 3 appears 1 time)
 * [2] -> [2]       (Element 2 appears 2 times)
 * [3] -> [1]       (Element 1 appears 3 times)
 * [4] -> []
 * [5] -> []
 * [6] -> []
 * * Step 3: Iterate backwards from end of Bucket Array to collect K elements.
 * -> Index 6: empty
 * -> Index 5: empty
 * -> Index 4: empty
 * -> Index 3: contains [1]. Result = [1] (Need 1 more)
 * -> Index 2: contains [2]. Result = [1, 2] (Target K reached!)
 * * ----------------------------------------------------------------------------
 * 2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ----------------------------------------------------------------------------
 * Phase 1: Brute Force Approach (Sorting) - The "Think it" stage.
 * - Count frequencies using a HashMap. Extract the keys, sort them based on
 * their corresponding frequency in the map, and pick the top K.
 * * Phase 2: Alternative Approach (Min-Heap) - The "Refine it" stage.
 * - Use a PriorityQueue (Min-Heap) of size K to keep track of the top K
 * frequent elements. If the heap exceeds size K, poll the minimum.
 * * Phase 3: Alternative Approach (Bucket Sort) - The "Perfect it" stage.
 * - Map elements to their frequencies, then map those frequencies to an array
 * of lists where the index represents the frequency. Traverse backwards to
 * get the top K elements in O(N) time.
 */

import java.util.*;

public class TopKFrequentMasterclass {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE (HASHMAP + FULL SORTING)
     * ========================================================================
     * Detailed Intuition:
     * The most intuitive way to solve this is to simply count how many times
     * each number appears using a Hash Map. Once we have the frequencies, we can
     * sort the unique numbers based on their frequencies in descending order.
     * Finally, we slice the first `k` elements from our sorted collection.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N). Building the frequency map takes O(N).
     * Sorting the unique elements takes O(U log U) where U is the number of
     * unique elements. In the worst case (all elements unique), this is O(N log N).
     * - Space Complexity: O(N) auxiliary heap space to store the HashMap and
     * the list of unique elements. O(1) auxiliary stack space.
     */
    public static int[] topKFrequentBruteForce(int[] nums, int k) {
        // Step 1: Build frequency map
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        // Step 2: Extract unique elements and sort them by frequency
        List<Integer> uniqueNums = new ArrayList<>(freqMap.keySet());
        uniqueNums.sort((a, b) -> freqMap.get(b) - freqMap.get(a)); // Descending order

        // Step 3: Collect top k elements
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = uniqueNums.get(i);
        }

        return result;
    }

    /**
     * ========================================================================
     * PHASE 2: OPTIMAL HEAP APPROACH (MIN-HEAP)
     * ========================================================================
     * Detailed Intuition:
     * Sorting the entire list of unique elements is overkill if we only need
     * the top K. We can optimize the sorting phase by maintaining a Min-Heap
     * of size K. We iterate through the frequency map, adding elements to the
     * heap. If the heap size exceeds K, we remove the element with the *lowest* * frequency (the root of the Min-Heap). By the end, the heap will contain
     * exactly the K most frequent elements.
     * * Complexity Analysis:
     * - Time Complexity: O(N log K). Building the map takes O(N). Inserting U
     * unique elements into a heap of size K takes O(U log K). In the worst
     * case, O(N log K). This is strictly better than O(N log N) when K < N.
     * - Space Complexity: O(N + K) auxiliary heap space. O(N) for the HashMap
     * and O(K) for the PriorityQueue.
     */
    public static int[] topKFrequentHeap(int[] nums, int k) {
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        // Min-Heap ordered by frequency (ascending)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(
                (a, b) -> freqMap.get(a) - freqMap.get(b)
        );

        for (int num : freqMap.keySet()) {
            minHeap.add(num);
            if (minHeap.size() > k) {
                minHeap.poll(); // Evict the least frequent element
            }
        }

        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = minHeap.poll();
        }

        return result;
    }

    /**
     * ========================================================================
     * PHASE 3: BUCKET SORT (THE "PERFECT IT" STAGE)
     * ========================================================================
     * Detailed Intuition:
     * Can we do better than O(N log K)? Yes. The maximum possible frequency of
     * any element is N (the length of the array). We can use an array of lists
     * (buckets) where the index of the array represents the frequency, and the
     * list contains the numbers that have that exact frequency. After populating
     * the buckets, we simply iterate from the end of the bucket array (highest
     * frequency) down to 0, collecting elements until we have K of them.
     * * Complexity Analysis:
     * - Time Complexity: O(N). Building the frequency map is O(N). Populating
     * the buckets is O(U) where U <= N. Traversing the buckets and collecting
     * the result is bounded by O(N). Overall strictly linear time!
     * - Space Complexity: O(N) auxiliary heap space for the HashMap and the
     * array of buckets.
     */
    public static int[] topKFrequentBucketSort(int[] nums, int k) {
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        // Create an array of lists to serve as buckets.
        // Size is nums.length + 1 because the max frequency can be nums.length.
        List<Integer>[] buckets = new List[nums.length + 1];
        for (int key : freqMap.keySet()) {
            int frequency = freqMap.get(key);
            if (buckets[frequency] == null) {
                buckets[frequency] = new ArrayList<>();
            }
            buckets[frequency].add(key);
        }

        int[] result = new int[k];
        int counter = 0;

        // Iterate backwards from highest possible frequency
        for (int i = buckets.length - 1; i >= 0 && counter < k; i--) {
            if (buckets[i] != null) {
                for (int num : buckets[i]) {
                    result[counter++] = num;
                    if (counter == k) {
                        return result; // Early exit once we have k elements
                    }
                }
            }
        }

        return result;
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("🚀 Executing Top K Frequent Elements Masterclass Testing Suite...\n");

        int[][][] testCases = {
                {{1, 1, 1, 2, 2, 3}, {2}},                    // Standard Case
                {{1}, {1}},                                   // Single Element
                {{-1, -1}, {1}},                              // Negative Numbers
                {{4, 1, -1, 2, -1, 2, 3}, {2}},               // Mix of elements and frequencies
                {{1, 2, 2, 3, 3, 3, 4, 4, 4, 4}, {2}},        // Strictly ascending frequencies
                {{0, 0, 0, 0, 0}, {1}}                        // Zero values edge case
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i][0];
            int k = testCases[i][1][0];

            System.out.println("Test Case " + (i + 1) + ": Input = " + Arrays.toString(nums) + ", k = " + k);

            // Execute all three phases
            int[] bruteRes = topKFrequentBruteForce(nums, k);
            int[] heapRes = topKFrequentHeap(nums, k);
            int[] bucketRes = topKFrequentBucketSort(nums, k);

            // Since the problem allows answers in any order, we sort the results
            // for clean equivalence checking in our test suite.
            Arrays.sort(bruteRes);
            Arrays.sort(heapRes);
            Arrays.sort(bucketRes);

            // Validate consistency across all approaches
            boolean passed = Arrays.equals(bruteRes, heapRes) && Arrays.equals(heapRes, bucketRes);

            System.out.println("  Phase 1 (Brute Force): " + Arrays.toString(bruteRes));
            System.out.println("  Phase 2 (Min-Heap):    " + Arrays.toString(heapRes));
            System.out.println("  Phase 3 (Bucket):      " + Arrays.toString(bucketRes));
            System.out.println("  Result Match:          " + (passed ? "✅ PASS" : "❌ FAIL"));
            System.out.println("--------------------------------------------------");
        }
    }
}